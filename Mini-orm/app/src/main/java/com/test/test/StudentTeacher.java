package com.test.test;

import com.miniorm.android.ColumnType;
import com.miniorm.annotation.Table;
import com.miniorm.annotation.TableColumn;
import com.miniorm.annotation.TableID;
import com.miniorm.enumtype.Parmary;

/**
 * Created by admin on 2017-03-01.
 */

@Table(name="StudentTeacher")
public class StudentTeacher {

    @TableID(isPrimaryKey=true,name="utid",defaultVal=0,type= Parmary.AutoIncrement,columnType= ColumnType.INTEGER)
    private long id;

    @TableColumn(name="sid",isForeignkey=true,columnType=ColumnType.INTEGER,isPrimaryKey = true)
    private Student student;


    @TableColumn(name="tid",isForeignkey=true,columnType=ColumnType.INTEGER,isPrimaryKey = true)
    private Teacher teacher;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}
