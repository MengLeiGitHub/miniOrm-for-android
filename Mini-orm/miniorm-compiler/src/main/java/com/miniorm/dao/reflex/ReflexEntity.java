package com.miniorm.dao.reflex;




import com.miniorm.entity.TableColumnEntity;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ReflexEntity {
    private HashMap<String, TableColumnEntity>   foreignkeyColumnMap;

    public void setForeignkeyColumnMap(HashMap<String, TableColumnEntity> foreignkeyColumnMap) {
        this.foreignkeyColumnMap = foreignkeyColumnMap;
    }

    public HashMap<String, TableColumnEntity> getForeignkeyColumnMap() {
        return foreignkeyColumnMap;
    }
}
