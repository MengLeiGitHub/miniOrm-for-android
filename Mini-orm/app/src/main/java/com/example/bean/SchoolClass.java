package com.example.bean;

import com.miniorm.android.ColumnType;
import com.miniorm.annotation.ManyToMany;
import com.miniorm.annotation.OneToMany;
import com.miniorm.annotation.Sqlcipher;
import com.miniorm.annotation.Table;
import com.miniorm.annotation.TableColumn;
import com.miniorm.annotation.TableID;
import com.miniorm.enumtype.Parmary;


import java.util.List;

/**
 * Created by admin on 2017-04-05.
 */
@Sqlcipher
@Table(name = "sclass")
public class SchoolClass {

    @TableID(name = "sid",columnType = ColumnType.INTEGER,type = Parmary.CUSTOM,defaultVal = -1)
    private int  sid;

    @TableColumn(name = "sClassName",isForeignkey = false,columnType = ColumnType.VARCHAR)
    private String   sClassName;

    private List<Teacher> teachers;
    private List<Student> studentList;


    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getsClassName() {
        return sClassName;
    }

    public void setsClassName(String sClassName) {
        this.sClassName = sClassName;
    }

    //@ManyToMany(bridgingTable = SchoolClassTeacher.class)
    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    //@OneToMany
    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }
}
