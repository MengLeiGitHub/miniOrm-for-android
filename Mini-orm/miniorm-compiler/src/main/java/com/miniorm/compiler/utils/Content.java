package com.miniorm.compiler.utils;

import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * Created by admin on 2017-03-29.
 */

public   class Content {

    public static String NEW_CLASS_NAME = "_miniorm_query_agent_subclass";

    public static final String MAINPACKAGE_NAME = "com.miniorm";

    public static String PROXY_PACKAGE_NAME = MAINPACKAGE_NAME + ".proxy.bean";

    public static String MAP_QUERY = MAINPACKAGE_NAME + ".query.map";

    public static String ONE_TO_ONE_MAPPING = "OneToOneMapping";

    public static String MANY_TO_ONE_MAPPING = "ManyToOneMapping";

    public static String MANY_TO_MANY_MAPPING = "ManyToManyMapping";
    public static String ONE_TO_MANY_MAPPING = "OneToManyMapping";


    public static String DAO_PACKAGE_NAME = "com.miniorm.android";
    public static String DAO_NAME = "androidBaseDao2";
    public static String NEW_DAO_NAME = "Dao";
    public static final String TABLE = MAINPACKAGE_NAME + ".annotation.Table";


    public static String DEBUG_LOG_PACKAGE = "com.miniorm.debug";
    public static String DEBUG = "DebugLog";

    public static final String ONE_TO_ONE = MAINPACKAGE_NAME + ".annotation.OneToOne";
    public static final String ONE_TO_MANY = MAINPACKAGE_NAME + ".annotation.OneToMany";
    public static final String MANY_TO_MANY = MAINPACKAGE_NAME + ".annotation.ManyToMany";
    public static final String MANY_TO_ONE = MAINPACKAGE_NAME + ".annotation.ManyToOne";


}
