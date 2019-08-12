package com.miniorm.compiler;

import com.google.auto.service.AutoService;
import com.miniorm.annotation.ManyToMany;
import com.miniorm.annotation.ManyToOne;
import com.miniorm.annotation.OneToMany;
import com.miniorm.annotation.OneToOne;
import com.miniorm.compiler.utils.CollectionUtils;
import com.miniorm.compiler.utils.Content;


import com.miniorm.compiler.utils.TextUtils;
import com.miniorm.dao.reflex.ReflexCache;
import com.miniorm.dao.reflex.ReflexEntity;
import com.miniorm.entity.TableColumnEntity;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ErrorType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.PrimitiveType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVariable;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleTypeVisitor6;
import javax.lang.model.util.Types;

import static com.miniorm.compiler.utils.Content.MANY_TO_MANY;
import static com.miniorm.compiler.utils.Content.MANY_TO_ONE;
import static com.miniorm.compiler.utils.Content.ONE_TO_MANY;
import static com.miniorm.compiler.utils.Content.ONE_TO_ONE;

/**
 * Created by admin on 2017-03-25.
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({ONE_TO_ONE, ONE_TO_MANY, MANY_TO_MANY, MANY_TO_ONE})
public class EntityCreaterClass extends AbstractProcessor {

    Filer filer;
    public static volatile boolean isRunned=false;
    Map<TypeMirror, List<Element>> mirrorListMap;
    Types types;
    Elements elementUtills;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnv.getFiler();
        elementUtills = processingEnvironment.getElementUtils();
        types = processingEnvironment.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if(CollectionUtils.isEmpty(set)){
            return false;
        }
        List<Set<? extends Element>> list = new LinkedList<>();
        Class[] classes={ManyToMany.class,ManyToOne.class,OneToMany.class,OneToOne.class};
        for (Class cls:classes){
            Set<? extends Element> set1 = roundEnvironment.getElementsAnnotatedWith(cls);
            list.add(set1);

        }
        if (CollectionUtils.notEmpty(list)) {
            for (Set<? extends Element> elements : list) {
                for (Element element : elements) {
                    classify(element);
                }
            }
            try {
                createHelper();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        }

        return false;
    }

    private void classify(Element element) {
        isRunned=true;
        TypeMirror mirror = element.getEnclosingElement().asType();
        if (mirror != null) {
            if (mirrorListMap == null) {
                mirrorListMap = new LinkedHashMap<>();
            }
            boolean exist = mirrorListMap.containsKey(mirror);
            if (exist) {
                List<Element> elements = mirrorListMap.get(mirror);
                elements.add(element);
            } else {
                List<Element> elements = new LinkedList<>();
                elements.add(element);
                mirrorListMap.put(mirror, elements);
            }
        }
    }

    private void createHelper() throws Exception {
        Set<TypeMirror> mirrorSet = mirrorListMap.keySet();

        /*保存 当前所有又代理子类的 类*/
        HashMap<ClassName,TypeName>  proxyClass=new HashMap<>();

        for (TypeMirror typeMirror : mirrorSet) {
            TypeElement typeElement = (TypeElement) types.asElement(typeMirror);
            String Package_name = elementUtills.getPackageOf(typeElement).toString();
            String parentClassName = typeElement.getSimpleName().toString();
            String className = parentClassName + Content.NEW_CLASS_NAME;
            TypeName typeName = ParameterizedTypeName.get(typeMirror);
            proxyClass.put(ClassName.get(Package_name,className),typeName);
            TypeSpec.Builder builder = TypeSpec.classBuilder(className)
                    .superclass(typeName)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

            List<Element> elementList = mirrorListMap.get(typeMirror);
            for (Element element : elementList) {
                OneToOne oneToOne = element.getAnnotation(OneToOne.class);
                if (oneToOne != null) {
                    ClassName on2oneMapping=ClassName.get(Content.MAP_QUERY,Content.ONE_TO_ONE_MAPPING);
                    builder.addMethod(ToOneMappingCreateMethodSpec(element,on2oneMapping));
                } else {
                    ManyToOne manyToOne = element.getAnnotation(ManyToOne.class);
                    if (manyToOne != null) {
                        ClassName many2oneMapping=ClassName.get(Content.MAP_QUERY,Content.MANY_TO_ONE_MAPPING);
                        builder.addMethod(ToOneMappingCreateMethodSpec(element,many2oneMapping));
                    } else {
                        OneToMany oneToMany = element.getAnnotation(OneToMany.class);
                        if (oneToMany != null) {
                            builder.addMethod(OneToManyMappingCreateMethodSpec(element));
                        } else {
                            ManyToMany manyToMany = element.getAnnotation(ManyToMany.class);
                            if (manyToMany != null) {
                                builder.addMethod(ManyToManyMappingCreateMethodSpec(element));
                            }
                        }

                    }
                }
            }
            TypeSpec typeSpec = builder.build();
            JavaFile javaFile = JavaFile.builder(Package_name, typeSpec)
                    .build();
            javaFile.writeTo(filer);
        }

        TypeSpec.Builder builder = TypeSpec.classBuilder(Content.PROXYUTILSCLASSNAME)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
   /*     ClassName dapMap = ClassName.get(Map.class);
        ClassName stringClassName = ClassName.get(String.class);
        TypeName className = ParameterizedTypeName.get(ClassName.get(Class.class), WildcardTypeName.subtypeOf(androidBaseDaoName));

        TypeName fildtype = ParameterizedTypeName.get(dapMap, stringClassName, className);*/
        MethodSpec.Builder   meyhodbuilder = MethodSpec.methodBuilder(Content.INITPPROXYUTILSMETHOD)
                .addModifiers(Modifier.PUBLIC,Modifier.FINAL,Modifier.STATIC)
                .addParameter(Map.class,"map")
                ;

        for (ClassName className:proxyClass.keySet()) {
            StringBuilder methods = new StringBuilder();
            methods.append(" map.put($T.class.getName(),$T.class.getName());");
            //  ClassName debug = ClassName.get(Content.DEBUG_LOG_PACKAGE, Content.DEBUG);
            meyhodbuilder.addStatement(methods.toString(),className ,proxyClass.get(className));
        }
        MethodSpec beyond =meyhodbuilder.build();
        builder.addMethod(beyond);
        TypeSpec typeSpec = builder.build();
        JavaFile javaFile = JavaFile.builder(Content.MAP_QUERY, typeSpec)
                .build();
        javaFile.writeTo(filer);






    }




    /*one 2  one  */
    private MethodSpec ToOneMappingCreateMethodSpec(Element element,ClassName mapping) {
        ExecutableElement executableElement = (ExecutableElement) element;
        TypeMirror returnTypeMirror = executableElement.getReturnType();
        String methodName = element.getSimpleName().toString();
        ClassName returnClassName = getClassNameFromTypeMirror(returnTypeMirror);

        MethodSpec.Builder builder = MethodSpec.methodBuilder(methodName).returns(returnClassName)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC);

        ToOneMapping(builder, returnClassName,mapping,methodName);

        MethodSpec beyond =
                builder.build();

        return beyond;
    }

    private void ToOneMapping(MethodSpec.Builder builder, ClassName returnClass,ClassName mapping,String methodName) {
        StringBuilder methods = new StringBuilder();
        ClassName ReflexEntityClass = ClassName.get(ReflexEntity.class.getPackage().getName(), ReflexEntity.class.getSimpleName());
        ClassName ReflexCacheClass = ClassName.get(ReflexCache.class.getPackage().getName(), ReflexCache.class.getSimpleName());
        ClassName TableColumnEntityClass = ClassName.get(TableColumnEntity.class.getPackage().getName(), TableColumnEntity.class.getSimpleName());

        ClassName dapMap = ClassName.get(Map.class);
        ClassName stringClassName = ClassName.get(String.class);
        TypeName fildtype = ParameterizedTypeName.get(dapMap, stringClassName, TableColumnEntityClass);
        String returnClassNames="this.getClass().getName()";
        methods.append("$T reflexEntity= $T.getReflexEntity("+returnClassNames+");\n");
        methods.append("if (reflexEntity!=null){\n");
        methods.append(" \t$T  h= reflexEntity.getForeignkeyColumnMap();\n");
        methods.append("\t  if(\""+methodName+"\".startsWith(\"get\")){\n ");
        String  methodName2=methodName.substring(3,methodName.length());
        String key=methodName2.charAt(0)+"";
        String first=  key.toLowerCase();
        String me=methodName2.replaceFirst(key,first);
        methods.append("\t\t  if (h!=null){\n");
        methods.append(" \t\t\t $T tableColumnEntity= h.get(\""+me+"\");\n");
        methods.append(" \t\t\t\t if (tableColumnEntity!=null&&tableColumnEntity.isHierarchicalQueries()){\n");
        methods.append("\t\t\t\t\tif (super."+methodName+"()!=null){return super."+methodName+"();};\n");
        methods.append("\t\t\t}\n");
        methods.append("\t\t}\n");
        methods.append("\t}\n");
        methods.append("}\n");
        methods.append(" try {\n" +
                " \treturn   ($T)  new    $T().proceedFilterToQuery(this,$T.class);\n" +
                " } catch (Exception e) {\n" +
                " $T.e(e);\n" +
                "\n" +
                "}\n" +
                " return   null");
        ClassName debug = ClassName.get(Content.DEBUG_LOG_PACKAGE, Content.DEBUG);
        builder.addStatement(methods.toString(),ReflexEntityClass,ReflexCacheClass,fildtype,TableColumnEntityClass,returnClass ,mapping, returnClass, debug);
    }







    /*one 2  many*/
    private MethodSpec OneToManyMappingCreateMethodSpec(Element element) {
        String methodName = element.getSimpleName().toString();
        ExecutableElement executableElement = (ExecutableElement) element;
        TypeMirror typeMirror = executableElement.getReturnType();
        MethodSpec.Builder builder = MethodSpec.methodBuilder(methodName).returns(ClassName.get(typeMirror))
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC);


        ClassName collectionName = getClassNameFromTypeMirror(typeMirror);
        TypeMirror returnClassTypeMirror= getGenericType(typeMirror);
        TypeName returnVariableType=ParameterizedTypeName.get(returnClassTypeMirror);

        OneToManyMapping(builder, returnVariableType, collectionName);
        MethodSpec beyond = builder.build();
        return beyond;

    }

    private void OneToManyMapping(MethodSpec.Builder builder, TypeName returnClass, ClassName collectionName) {
        StringBuilder methods = new StringBuilder();
        methods.append("try {\n");
        methods.append("\treturn  ");
        if (collectionName.toString().contains("ArrayList"))
            methods.append("  (ArrayList)  ");
        else if (collectionName.toString().contains("List"))
            methods.append("  (List)  ");

        methods.append("  new    $T().proceedFilterToQuery(this,$T.class,$T.class);\n" +
                " } catch (Exception e) {\n" +
                "\t $T.e(e);\n" +
                "\n" +
                "}\n" +
                "return   null");
        ClassName mapping = ClassName.get(Content.MAP_QUERY, Content.ONE_TO_MANY_MAPPING);
        ClassName debug = ClassName.get(Content.DEBUG_LOG_PACKAGE, Content.DEBUG);
        builder.addStatement(methods.toString(), mapping, returnClass, collectionName, debug);

    }

    ;


    /*many 2 many*/

    private MethodSpec ManyToManyMappingCreateMethodSpec(Element element) {
        String methodName = element.getSimpleName().toString();
        ExecutableElement executableElement = (ExecutableElement) element;
        TypeMirror typeMirror = executableElement.getReturnType();
        MethodSpec.Builder builder = MethodSpec.methodBuilder(methodName).returns(ClassName.get(typeMirror))
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC);

        ClassName collectionName = getClassNameFromTypeMirror(typeMirror);
        TypeMirror returnClassTypeMirror= getGenericType(typeMirror);
        TypeName returnVariableType=ParameterizedTypeName.get(returnClassTypeMirror);

        ManyToMany manyToMany = element.getAnnotation(ManyToMany.class);
        TypeMirror bridgingTableTypeMirror = getBrindgingTable(manyToMany);
        ManyToManyMapping(builder, returnVariableType, collectionName, ClassName.get(bridgingTableTypeMirror));
        MethodSpec beyond = builder.build();
        return beyond;

    }

    private void ManyToManyMapping(MethodSpec.Builder builder, TypeName returnClass, ClassName collectionName, TypeName b) {
        StringBuilder methods = new StringBuilder();
        methods.append("try {\n");
        methods.append("\treturn  ");
        if (collectionName.toString().contains("Array"))
            methods.append("  (ArrayList)  ");
        else if (collectionName.toString().contains("List"))
            methods.append("  (List)  ");
        methods.append("  new    $T().proceedFilterToQuery(this,$T.class,$T.class,$T.class);\n" +
                " } catch (Exception e) {\n" +
                "\t $T.e(e);\n" +
                "\n" +
                "}\n" +
                "return   null");
        ClassName mapping = ClassName.get(Content.MAP_QUERY, Content.MANY_TO_MANY_MAPPING);
        ClassName debug = ClassName.get(Content.DEBUG_LOG_PACKAGE, Content.DEBUG);
        builder.addStatement(methods.toString(), mapping, returnClass, b, collectionName, debug);

    }



    private ClassName getClassNameFromTypeMirror(TypeMirror returnTypeMirror) {
        TypeElement typeElement = (TypeElement) types.asElement(returnTypeMirror);
        String Package_name = elementUtills.getPackageOf(typeElement).toString();
        String parentClassName = typeElement.getSimpleName().toString();
        ClassName className = ClassName.get(Package_name, parentClassName);
        return className;
    }

    private  TypeMirror getBrindgingTable(ManyToMany annotation) {
        try {
            annotation.bridgingTable(); // this should throw
        } catch (MirroredTypeException mte) {
            return mte.getTypeMirror();
        }
        return null; // can this ever happen ??
    }


    public   TypeMirror getGenericType(final TypeMirror type)
    {
        final TypeMirror[] result = { null };

        type.accept(new SimpleTypeVisitor6<Void, Void>()
        {
            @Override
            public Void visitDeclared(DeclaredType declaredType, Void v)
            {
                List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
                if (!typeArguments.isEmpty())
                {
                    result[0] = typeArguments.get(0);
                }
                return null;
            }
            @Override
            public Void visitPrimitive(PrimitiveType primitiveType, Void v)
            {
                return null;
            }
            @Override
            public Void visitArray(ArrayType arrayType, Void v)
            {
                return null;
            }
            @Override
            public Void visitTypeVariable(TypeVariable typeVariable, Void v)
            {
                return null;
            }
            @Override
            public Void visitError(ErrorType errorType, Void v)
            {
                return null;
            }
            @Override
            protected Void defaultAction(TypeMirror typeMirror, Void v)
            {
                throw new UnsupportedOperationException();
            }
        }, null);

        return result[0];
    }
}
