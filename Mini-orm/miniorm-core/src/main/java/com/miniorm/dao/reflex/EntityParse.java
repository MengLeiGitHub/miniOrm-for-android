package com.miniorm.dao.reflex;

import com.miniorm.annotation.ManyToMany;
import com.miniorm.annotation.ManyToOne;
import com.miniorm.annotation.OneToMany;
import com.miniorm.annotation.OneToOne;
import com.miniorm.annotation.Table;
import com.miniorm.annotation.TableColumn;
import com.miniorm.annotation.TableID;
import com.miniorm.dao.utils.Content;
import com.miniorm.entity.TableColumnEntity;
import com.miniorm.entity.TableEntity;
import com.miniorm.entity.TableIdEntity;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

public class EntityParse<T> {

    private Class<? extends Object> c;

    private Table table;
    TableID tableID;

    public EntityParse(Class<T> t) {
        c = t;
    }

    public Table getTable() {
        try {

            if (table == null)
                table = c.getAnnotation(Table.class);

            return table;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }

    public TableID getTableID() {
        Field[] fields = c.getDeclaredFields();
        for (Field filed : fields) {
            filed.setAccessible(true);
            TableID id = filed.getAnnotation(TableID.class);
            if (id != null)
                return id;
        }
        return null;
    }

    public TableID[] getTableIDs() {
        ArrayList<TableID> list = new ArrayList<TableID>();
        Field[] fields = c.getFields();
        for (Field filed : fields) {
            filed.setAccessible(true);
            TableID id = filed.getAnnotation(TableID.class);
            if (id != null)
                list.add(id);
        }

        return list.toArray(new TableID[list.size()]);
    }

    public Method[] getMethods() {
        return c.getMethods();
    }


    public ReflexEntity getFieldValueFromT(Object t) {

        ReflexEntity reflexEntity = new ReflexEntity();


        Class<? extends Object> c = t.getClass();
        if (c.getName().endsWith(Content.NEW_CLASS_NAME)) {

            Class<? extends Object> superClass = c.getSuperclass();
            try {
                return getFieldValueFromT(superClass.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        c.getSuperclass();
        boolean flag = c.isAnnotationPresent(Table.class);
        if (flag) {

            Table table = c.getAnnotation(Table.class);

            reflexEntity.setTableName(table.name());

            reflexEntity.setTableEntity(new TableEntity(table.name(), c));

            Field[] fields = c.getDeclaredFields();
            try {
                for (Field field : fields) {

                    TableColumn column = field.getAnnotation(TableColumn.class);
                    if (column != null) {
                        reflexEntity.addKey(column.name());
                        TableColumnEntity tableColumnEntity = new TableColumnEntity();
                        tableColumnEntity.setColumnName(column.name());
                        tableColumnEntity.setColumnType(column.columnType());
                        tableColumnEntity.setForeignkey(column.isForeignkey());
                        tableColumnEntity.setField(field);
                        tableColumnEntity.setIsHierarchicalQueries(column.HierarchicalQueries());
                        tableColumnEntity.setIgnoreBooleanParam(column.IgnoreBooleanParam());

                        if (tableColumnEntity.isForeignkey()) {
                            reflexEntity.getForeignkeyColumnMap().put(field.getName(), tableColumnEntity);
                        }
                        reflexEntity.getTableColumnMap().put(field.getName(), tableColumnEntity);


                    } else {

                        TableID id = field.getAnnotation(TableID.class);

                        if (id != null) {
                            reflexEntity.addKey(id.name());
                            TableIdEntity tableIdEntity = new TableIdEntity();
                            tableIdEntity.setDefaultVal(id.defaultVal());
                            tableIdEntity.setFieldName(id.name());
                            tableIdEntity.setPrimaryKey(true);
                            tableIdEntity.setKeytype(id.type());
                            tableIdEntity.setColumnName(id.name());
                            tableIdEntity.setColumnVal(getObjFromField(field, t));
                            tableIdEntity.setColumnType(id.columnType());
                            tableIdEntity.setField(field);
                            reflexEntity.setTableIdEntity(tableIdEntity);

                        } else {

                            continue;
                        }

                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        return reflexEntity;


    }

    public static Object getFieldObjectVal(Object object, Field field, TableColumn tableColumn) throws IllegalAccessException {
        field.setAccessible(true);
        Object val = field.get(object);
        if (val == null) {
            return tableColumn.defaultVal();
        }
        return val;
    }

    public static Object getFieldObjectVal(Object object, Field field, TableID tableID) throws IllegalAccessException {
        field.setAccessible(true);
        Object val = field.get(object);

        return val;
    }


    public static Object getFieldObjectVal(Object object, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        Object val = field.get(object);

        return val;
    }

    public String getTableColumn(Field field) {
        field.setAccessible(true);
        TableColumn column = field.getAnnotation(TableColumn.class);
        if (column != null) {

            return column.name();
        } else {

            TableID id = field.getAnnotation(TableID.class);
            if (id != null) {
                return id.name();
            }
        }
        return null;
    }

    public Table getTable(T t) {
        try {

            Class<? extends Object> c = t.getClass();

            Table table = c.getAnnotation(Table.class);


            return table;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }
    }


    public TableIdEntity getTableIDEntity(T classs) {

        Class<? extends Object> c = classs.getClass();

        Field[] fields = c.getDeclaredFields();
        for (Field filed : fields) {
            TableID id = filed.getAnnotation(TableID.class);

            if (id != null) {
                TableIdEntity tableIdEntity = new TableIdEntity();
                tableIdEntity.setColumnName(id.name());
                tableIdEntity.setColumnType(id.columnType());
                tableIdEntity.setDefaultVal(id.defaultVal());
                tableIdEntity.setField(filed);
                tableIdEntity.setFieldName(filed.getName());

                return tableIdEntity;
            }

        }

        return null;
    }

    public TableID getTableID(T classs) {

        Class<? extends Object> c = classs.getClass();
        Field[] fields = c.getFields();
        for (Field filed : fields) {
            filed.setAccessible(true);
            TableID id = filed.getAnnotation(TableID.class);
            if (id != null)
                return id;
        }

        return null;
    }


    public Object getObjFromField(Field field, Object t) {


        try {
            field.setAccessible(true);
            return field.get(t);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }

    }


    public class FieldColumnAndVal {
        public String fileColumn;
        public Object val;

        public FieldColumnAndVal(String fileColumn, Object val) {
            this.fileColumn = fileColumn;
            this.val = val;
        }
    }

    public FieldColumnAndVal getColumnAndVal(Object obj1) {
        Field[] fields = obj1.getClass().getDeclaredFields();
        for (Field filed : fields) {
            TableID id = filed.getAnnotation(TableID.class);
            if (id != null) {

                String firstLetter = filed.getName().substring(0, 1).toUpperCase();
                String getMethodName = "get" + firstLetter + filed.getName().substring(1);
                Object obj = null;
                try {
                    Method m = obj1.getClass().getMethod(getMethodName);
                    m.setAccessible(true);
                    obj = m.invoke(obj1);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                FieldColumnAndVal fieldColumnAndVal = new FieldColumnAndVal(id.name(), obj);

                return fieldColumnAndVal;
            }
        }

        return null;
    }


    public HashMap<String, Field> getColumnAndField(Class<T> t) {
        HashMap<String, Field> hashMap = new HashMap<String, Field>();
        Field[] fields = t.getDeclaredFields();
        for (Field field : fields) {
            String columnName = getColumnNameFormField(field);
            if (columnName != null)
                hashMap.put(columnName, field);
        }


        return hashMap;

    }

    public T setEntityValue(T t, Object val, Field field) throws IllegalAccessException {

        field.setAccessible(true);
        field.set(t, val);

        return t;
    }


    public String getColumnNameFormField(Field field) {
        field.setAccessible(true);
        TableColumn column = field.getAnnotation(TableColumn.class);

        if (column != null) {

            return column.name();

        } else {
            TableID id = field.getAnnotation(TableID.class);
            if (id != null) {

                return id.name();

            } else {

            }
        }
        return null;

    }


    public String isMySelfAnnotion(Method method) {
        String methodName = null;
        TableColumn tableColumn = method.getAnnotation(TableColumn.class);
        TableID tableID = method.getAnnotation(TableID.class);

        if (tableColumn == null && tableID == null) {


        } else {
            methodName = method.getName();
        }

        if (methodName == null) return null;

        if (methodName.startsWith("set")) {
            //methodName.sub
        }

        return null;

    }


    public static boolean isExistProxySubClass(Class cls) {
        Method[] methods = cls.getDeclaredMethods();
        Class[] classes = {ManyToMany.class, ManyToOne.class, OneToMany.class, OneToOne.class};

        if (methods != null) {
            for (Method method : methods) {
                method.setAccessible(true);
                if (method.getReturnType().equals(Void.TYPE)) {
                    continue;
                }
                for (Class cl : classes) {
                    Annotation annotation = method.getAnnotation(cl);
                    if (annotation != null) {
                        return true;
                    }
                }

            }
        }
        return false;
    }


}
