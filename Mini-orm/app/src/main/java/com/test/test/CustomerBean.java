package com.test.test;

import com.miniorm.android.ColumnType;
import com.miniorm.annotation.Table;
import com.miniorm.annotation.TableColumn;
import com.miniorm.annotation.TableID;
import com.miniorm.enumtype.Parmary;

/**
 * Created by admin on 2016-11-28.
 */
@Table(name = "CustomerBean")

public class CustomerBean {

    /**
     * id : 58
     * userName : 15305712665
     * nickname : nickname
     * avatar : http
     * company : 优印公司
     * status : 0
     */
    @TableID(name = "id",type= Parmary.CUSTOM  ,isPrimaryKey = true ,columnType= ColumnType.INTEGER ,defaultVal=0)
    private int id;

    @TableColumn(name="userName",columnType= ColumnType.VARCHAR)

    private String userName;

    @TableColumn(name="comment",columnType= ColumnType.VARCHAR)

    private String comment;

    @TableColumn(name="nickname",columnType= ColumnType.VARCHAR)

    private String nickname;

    @TableColumn(name="avatar",columnType= ColumnType.VARCHAR)

    private String avatar;

    @TableColumn(name="company",columnType= ColumnType.VARCHAR)

    private String company;

    @TableColumn(name="status",columnType= ColumnType.INTEGER)

    private int status;
    @TableColumn(name="factoryId",columnType= ColumnType.INTEGER)
    private int factoryId;

    @TableColumn(name="userId",columnType= ColumnType.INTEGER)
    private int userId;

    public void setFactoryId(int factoryId) {
        this.factoryId = factoryId;
    }

    public int getFactoryId() {
        return factoryId;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
