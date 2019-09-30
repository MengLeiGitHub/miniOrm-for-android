package com.example.bean;


import com.miniorm.android.ColumnType;
import com.miniorm.annotation.Sqlcipher;
import com.miniorm.annotation.Table;
import com.miniorm.annotation.TableColumn;
import com.miniorm.annotation.TableID;
import com.miniorm.enumtype.Parmary;

//@Sqlcipher
@Table(name = "testbeam")
public class TestBean {
    @TableID(name = "sid",columnType = ColumnType.INTEGER,type = Parmary.AutoIncrement)
    private long  sid;

    @TableColumn(name = "name",columnType = ColumnType.VARCHAR)
    private String  name;

    @TableColumn(name = "scid",isForeignkey = true,columnType = ColumnType.VARCHAR)
    private SchoolClass schoolClass;


    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
