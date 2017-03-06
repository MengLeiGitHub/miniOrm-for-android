package com.test.test;

import com.miniorm.android.ColumnType;
import com.miniorm.annotation.Table;
import com.miniorm.annotation.TableColumn;
import com.miniorm.annotation.TableID;
import com.miniorm.enumtype.Parmary;

/**
 * Created by admin on 2016-11-03.
 */
@Table(name = "a22231")
public class TestBean {


    /**
     * userId : 23
     * username : 18258190600
     * nickname : 四小龙
     * realname : 张三
     * avatar : http://www.u-yin.cn/images/zhangsan.png
     * sex : 1
     * company : 优印科技
     * companyAddress : 中国智慧信息产业园
     * info : 我是一个优秀的业务员
     */


    @TableID(name = "id", type = Parmary.CUSTOM, isPrimaryKey = true, columnType = ColumnType.INTEGER, defaultVal = 0)
    private int id;

    @TableColumn(name = "username", columnType = ColumnType.VARCHAR)
    private String username;

    @TableColumn(name = "nickname", columnType = ColumnType.VARCHAR)
    private String nickname;

    @TableColumn(name = "realname", columnType = ColumnType.VARCHAR)
    private String realname;

    @TableColumn(name = "avatar", columnType = ColumnType.VARCHAR)
    private String avatar;

    @TableColumn(name = "sex", columnType = ColumnType.VARCHAR)
    private String sex;

    @TableColumn(name = "company", columnType = ColumnType.VARCHAR)
    private String company;

    @TableColumn(name = "companyAddress", columnType = ColumnType.VARCHAR)
    private String companyAddress;

    @TableColumn(name = "info", columnType = ColumnType.VARCHAR)
    private String info;

    @TableColumn(name = "content", columnType = ColumnType.VARCHAR)
    private String content;

    @TableColumn(name = "type", columnType = ColumnType.INTEGER)
    private int type;

    @TableColumn(name = "status", columnType = ColumnType.INTEGER)
    private int status;

    @TableColumn(name = "gmtCreate", columnType = ColumnType.VARCHAR)
    private long gmtCreate;

    @TableColumn(name = "gmtModified", columnType = ColumnType.VARCHAR)
    private long gmtModified;

    @TableColumn(name = "contactNickname", columnType = ColumnType.VARCHAR)
    private String contactNickname;


    @TableColumn(name = "groupId", columnType = ColumnType.INTEGER)
    private int groupId;

    @TableColumn(name = "groupName", columnType = ColumnType.VARCHAR)
    private String groupName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public long getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(long gmtModified) {
        this.gmtModified = gmtModified;
    }

    public String getContactNickname() {
        return contactNickname;
    }

    public void setContactNickname(String contactNickname) {
        this.contactNickname = contactNickname;
    }




    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }



}
