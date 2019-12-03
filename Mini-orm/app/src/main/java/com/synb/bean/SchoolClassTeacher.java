package com.synb.bean;

import com.miniorm.android.ColumnType;
import com.miniorm.annotation.Table;
import com.miniorm.annotation.TableColumn;
import com.miniorm.annotation.TableID;
import com.miniorm.enumtype.Parmary;
import com.miniorm.annotation.Sqlcipher;

/**
 * Created by admin on 2017-04-07.
 */
//@Sqlcipher
@Table(name = "SchoolClassTeacher")
public class SchoolClassTeacher {

    @TableID(name = "stid",columnType = ColumnType.INTEGER,type = Parmary.AutoIncrement,defaultVal = 0)
    private long stid;

    @TableColumn(name = "cid",columnType = ColumnType.INTEGER,isForeignkey = true)
    private Teacher teacher;

    @TableColumn(name = "sid",columnType = ColumnType.INTEGER,isForeignkey = true)
    private SchoolClass schoolClass;


    public long getStid() {
        return stid;
    }

    public void setStid(long stid) {
        this.stid = stid;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }


    public SchoolClass getSchoolClass() {
        return schoolClass;
    }

    public void setSchoolClass(SchoolClass schoolClass) {
        this.schoolClass = schoolClass;
    }
}
