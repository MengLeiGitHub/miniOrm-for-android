package com.miniorm.compiler.utils;



/**
 * Created by admin on 2017-03-29.
 */

public   class Content {

    public static final String NEW_CLASS_NAME = "_miniorm_query_agent_subclass";
    public static final String MAINPACKAGE_NAME = "com.miniorm";

    public static final String PROXY_PACKAGE_NAME = MAINPACKAGE_NAME + ".proxy.bean";

    public static final String MAP_QUERY = MAINPACKAGE_NAME + ".query.map";
    public static final String PROXYUTILSCLASSNAME="QueryAgentBeanUtils";
    public static final String INITPPROXYUTILSMETHOD = "initSubBean";
    public static final String INITPROXYMAPCLASS = "initProxyClass";


    public static String ONE_TO_ONE_MAPPING = "OneToOneMapping";

    public static String MANY_TO_ONE_MAPPING = "ManyToOneMapping";

    public static String MANY_TO_MANY_MAPPING = "ManyToManyMapping";
    public static String ONE_TO_MANY_MAPPING = "OneToManyMapping";

    public static String TABLE_DAO_MAP="TableDaoMapping";
    public static String TABLE_DAO_MAP_CHILD=TABLE_DAO_MAP+"_Child";

    public static String DAO_PACKAGE_NAME = "com.miniorm.dao";
    public static String DAO_NAME = "BaseDao";
    public static String ENCRYPTION_PACKAGE_NAME=MAINPACKAGE_NAME+".sqlcipher";
    public static String ENCRYPTION_DAO_NAME =   "androidSqlcipherDao";
    public static String UNENCRYPTION_PACKAGE_NAME=MAINPACKAGE_NAME+".android";
    public static String UNENCRYPTION_DAO_NAME = "androidBaseDao";

    public static String NEW_DAO_NAME = "Dao";
    public static final String TABLE = MAINPACKAGE_NAME + ".annotation.Table";


    public static String DEBUG_LOG_PACKAGE = "com.miniorm.debug";
    public static String DEBUG = "DebugLog";

    public static final String ONE_TO_ONE = MAINPACKAGE_NAME + ".annotation.OneToOne";
    public static final String ONE_TO_MANY = MAINPACKAGE_NAME + ".annotation.OneToMany";
    public static final String MANY_TO_MANY = MAINPACKAGE_NAME + ".annotation.ManyToMany";
    public static final String MANY_TO_ONE = MAINPACKAGE_NAME + ".annotation.ManyToOne";

    public static final String TABLE_DAO=MAINPACKAGE_NAME+".annotation.TableDao";
}
