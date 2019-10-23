package com.example.bean;

import com.miniorm.android.ColumnType;
import com.miniorm.annotation.OneToMany;
import com.miniorm.annotation.Table;
import com.miniorm.annotation.TableColumn;
import com.miniorm.annotation.TableID;
import com.miniorm.enumtype.Parmary;

import java.util.List;

public class NeiBuLeiTest {

    @Table(name = "haha")
    public static class HAHA{
        @TableID(name = "cid",type = Parmary.CUSTOM,columnType = ColumnType.INTEGER)
        private long id;

        @TableColumn(name = "cname",columnType = ColumnType.VARCHAR)
        private String cName;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getcName() {
            return cName;
        }

        public void setcName(String cName) {
            this.cName = cName;
        }

        private List<Teacher> teachers;

        @OneToMany
        public List<Teacher> getTeachers() {
            return teachers;
        }

    }

}
