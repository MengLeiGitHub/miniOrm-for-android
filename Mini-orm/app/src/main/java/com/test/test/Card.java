package com.test.test;

import com.miniorm.android.ColumnType;
import com.miniorm.annotation.Table;
import com.miniorm.annotation.TableColumn;
import com.miniorm.annotation.TableID;
import com.miniorm.enumtype.Parmary;

/**
 * Created by admin on 2016/9/26.
 */
@Table(name = "card")
public class Card {

    @TableID(type = Parmary.AutoIncrement,columnType = ColumnType.NVARCHAR,name = "cid",defaultVal = 0,isPrimaryKey = false)
    private  int id;
    @TableColumn(name = "address",columnType = ColumnType.NVARCHAR,isForeignkey = false,isPrimaryKey = false)
    private  String  address;

    @TableColumn(name = "alies",columnType = ColumnType.NVARCHAR,isForeignkey = false,isPrimaryKey = false)
    private  String  alies;

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAlies(String alies) {
        this.alies = alies;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getAlies() {
        return alies;
    }

}
