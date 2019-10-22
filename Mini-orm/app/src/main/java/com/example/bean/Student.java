package com.example.bean;

import com.miniorm.android.ColumnType;
import com.miniorm.annotation.ManyToMany;
import com.miniorm.annotation.ManyToOne;
import com.miniorm.annotation.Sqlcipher;
import com.miniorm.annotation.Table;
import com.miniorm.annotation.TableColumn;
import com.miniorm.annotation.TableID;
import com.miniorm.enumtype.Parmary;


/**
 * Created by admin on 2017-04-05.
 */
@Sqlcipher
@Table(name = "student")
public class Student {
    @TableID(name = "sid",columnType = ColumnType.INTEGER,type = Parmary.AutoIncrement,defaultVal = -1)
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

   // @ManyToOne
    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }
}
