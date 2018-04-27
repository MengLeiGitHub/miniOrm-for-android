package com.miniorm.compiler;

import com.google.auto.service.AutoService;
import com.miniorm.annotation.ManyToMany;
import com.miniorm.annotation.ManyToOne;
import com.miniorm.annotation.OneToMany;
import com.miniorm.annotation.OneToOne;
import com.miniorm.annotation.TableDao;
import com.miniorm.compiler.utils.CollectionUtils;
import com.miniorm.compiler.utils.Content;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.lang.annotation.Annotation;
import java.util.HashSet;
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
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
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
import static com.miniorm.compiler.utils.Content.TABLE_DAO;

/**
 * Created by admin on 2017-03-25.
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({TABLE_DAO})
public class TableDaoClass extends AbstractProcessor {

    Filer filer;

    Map<TypeMirror, List<Element>> mirrorListMap;
    Types types;
    Elements elementUtills;
    HashSet<TypeMirror>  includeDataRelation;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnv.getFiler();
        elementUtills = processingEnvironment.getElementUtils();
        types = processingEnvironment.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (CollectionUtils.isEmpty(set)) return false;
        List<Set<? extends Element>> list = new LinkedList<>();
        includeDataRelation(roundEnvironment);
        Class[] classes = {TableDao.class};
        for (Class cls : classes) {
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
        ClassName newClass = ClassName.get(Content.MAP_QUERY, Content.TABLE_DAO_MAP_CHILD);
        ClassName superClassName = ClassName.get(Content.MAP_QUERY, Content.TABLE_DAO_MAP);

        ClassName androidBaseDaoName = ClassName.get(Content.DAO_PACKAGE_NAME, Content.DAO_NAME);
        ClassName dapMap = ClassName.get(Map.class);
        ClassName stringClassName = ClassName.get(String.class);

        TypeName className = ParameterizedTypeName.get(ClassName.get(Class.class), WildcardTypeName.subtypeOf(androidBaseDaoName));

        TypeName fildtype = ParameterizedTypeName.get(dapMap, stringClassName, className);


        TypeSpec.Builder builder = TypeSpec.classBuilder(newClass)
                .superclass(superClassName)
                .addField(fildtype, "daoMap", Modifier.PRIVATE)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        MethodSpec.Builder methoBuilder = MethodSpec.methodBuilder("putDaoClass")
                .addModifiers(Modifier.PRIVATE);
        methoBuilder.addStatement("if(daoMap==null){\n" +
                "            daoMap=new $T<>();\n" +
                "        }", LinkedHashMap.class);
        for (TypeMirror typeMirror : mirrorSet) {
            List<Element> elementList = mirrorListMap.get(typeMirror);
            for (Element element : elementList) {
                TypeElement typeElement = (TypeElement) element;
                TypeMirror superclassTypeMirror = typeElement.getSuperclass();
                TypeMirror typeParamMirror = getGenericType(superclassTypeMirror);
                //  String key=typeParamMirror.toString();

                TypeName entityTypeName = ClassName.get(typeParamMirror);
                ClassName daoClass = ClassName.get(elementUtills.getPackageOf(typeElement).toString(), typeElement.getSimpleName().toString());
                //  methoBuilder.addStatement(" daoMap.put( \""+key+"\",$T.class);",daoClass);

                methoBuilder.addStatement(" daoMap.put( $T.class.getName(),$T.class);", entityTypeName, daoClass);
            }

        }
        builder.addMethod(methoBuilder.build());


        TypeName stringclassName = ParameterizedTypeName.get(dapMap, ClassName.get(String.class), ClassName.get(String.class));
        builder.addField(stringclassName, "entityMap", Modifier.PRIVATE)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        MethodSpec.Builder methoentityMappingBuilder = MethodSpec.methodBuilder("putEntityMapping")
                .addModifiers(Modifier.PRIVATE);
        methoentityMappingBuilder.addStatement("if(entityMap==null){\n" +
                "            entityMap=new $T<>();\n" +
                "        }", LinkedHashMap.class);

        for (TypeMirror typeMirror : mirrorSet) {
            List<Element> elementList = mirrorListMap.get(typeMirror);
            for (Element element : elementList) {
                TypeElement typeElement = (TypeElement) element;
                TypeMirror superclassTypeMirror = typeElement.getSuperclass();
                TypeMirror typeParamMirror = getGenericType(superclassTypeMirror);
                TypeName entityTypeName = ClassName.get(typeParamMirror);
                String key = entityTypeName.toString();
                if(includeDataRelation.contains(typeParamMirror)){
                    ClassName proxyclass = ClassName.get(elementUtills.getPackageOf(typeElement).toString(), key.substring(key.lastIndexOf(".") + 1) + Content.NEW_CLASS_NAME);
                    //  methoBuilder.addStatement(" daoMap.put( \""+key+"\",$T.class);",daoClass);
                    methoentityMappingBuilder.addStatement(" entityMap.put( $T.class.getName(),$T.class.getName());", proxyclass, entityTypeName);

                }
              }

        }
        builder.addMethod(methoentityMappingBuilder.build());


        MethodSpec.Builder getClassMethod = MethodSpec.methodBuilder("getDaoByName")
                .addParameter(String.class, "name", Modifier.FINAL)
                .addAnnotation(Override.class)
                .returns(className)
                .addModifiers(Modifier.PUBLIC);

        getClassMethod.addStatement("if(daoMap==null)  putDaoClass() ");
        getClassMethod.addStatement("if(entityMap==null)  putEntityMapping()");
        getClassMethod.addStatement(" Class<? extends androidBaseDao>   cls=daoMap.get(name)");
        getClassMethod.addStatement(" if(cls!=null) return cls");
        getClassMethod.addStatement(" return daoMap.get(entityMap.get(name))");

        builder.addMethod(getClassMethod.build());


        TypeSpec typeSpec = builder.build();
        JavaFile javaFile = JavaFile.builder(Content.MAP_QUERY, typeSpec)
                .build();
        javaFile.writeTo(filer);
    }


    public TypeMirror getGenericType(final TypeMirror type) {
        final TypeMirror[] result = {null};

        type.accept(new SimpleTypeVisitor6<Void, Void>() {
            @Override
            public Void visitDeclared(DeclaredType declaredType, Void v) {
                List<? extends TypeMirror> typeArguments = declaredType.getTypeArguments();
                if (!typeArguments.isEmpty()) {
                    result[0] = typeArguments.get(0);
                }
                return null;
            }

            @Override
            public Void visitPrimitive(PrimitiveType primitiveType, Void v) {
                return null;
            }

            @Override
            public Void visitArray(ArrayType arrayType, Void v) {
                return null;
            }

            @Override
            public Void visitTypeVariable(TypeVariable typeVariable, Void v) {
                return null;
            }

            @Override
            public Void visitError(ErrorType errorType, Void v) {
                return null;
            }

            @Override
            protected Void defaultAction(TypeMirror typeMirror, Void v) {
                throw new UnsupportedOperationException();
            }
        }, null);

        return result[0];
    }

    private void   includeDataRelation(RoundEnvironment roundEnvironment){
        List<Set<? extends Element>> list = new LinkedList<>();
        Class[] classes={ManyToMany.class,ManyToOne.class,OneToMany.class,OneToOne.class};
        for (Class cls:classes){
            Set<? extends Element> set1 = roundEnvironment.getElementsAnnotatedWith(cls);
            list.add(set1);

        }
        if (CollectionUtils.notEmpty(list)) {
            includeDataRelation=new HashSet<>();
            for (Set<? extends Element> elements : list) {
                for (Element element : elements) {
                    TypeMirror mirror = element.getEnclosingElement().asType();

                    includeDataRelation.add(mirror);
                }
            }
        }
    }



}
