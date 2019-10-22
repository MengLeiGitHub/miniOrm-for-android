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
 * 课程
 * Created by admin on 2017-04-06.
 */
@Sqlcipher
@Table(name = "course")
public class Course {

    @TableID(name = "cid",type = Parmary.CUSTOM,columnType = ColumnType.INTEGER)
    private long id;

    /*
       课程名称
     */

    @TableColumn(name = "cname",columnType = ColumnType.VARCHAR)
    private String cName;

    private List<Teacher> teachers;



    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }
  //  @OneToMany
    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
