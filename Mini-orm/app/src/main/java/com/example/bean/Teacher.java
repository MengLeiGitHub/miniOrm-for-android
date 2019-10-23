package com.example.bean;

import com.miniorm.android.ColumnType;
import com.miniorm.annotation.ManyToMany;
import com.miniorm.annotation.OneToOne;
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
@Table(name = "teacher")
public class Teacher {

    @TableID(name = "tid",columnType = ColumnType.INTEGER,type = Parmary.CUSTOM)
    private long  tid;

    @TableColumn(name = "name",columnType = ColumnType.VARCHAR)
    private String name;



    @TableColumn(name = "cid",isForeignkey = true,columnType = ColumnType.INTEGER)
    Course course;

    List<SchoolClass>  schoolClassList;


    @ManyToMany(bridgingTable = SchoolClassTeacher.class)
    public List<SchoolClass> getSchoolClassList() {
        return schoolClassList;
    }


    @OneToOne
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public void setSchoolClassList(List<SchoolClass> schoolClassList) {
        this.schoolClassList = schoolClassList;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
