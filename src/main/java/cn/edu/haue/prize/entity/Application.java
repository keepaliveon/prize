package cn.edu.haue.prize.entity;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

/**
 * 申请实体类
 * Created by 杨晋升 on 2018-07-03.
 */
@Entity
public class Application {

    //申请编号
    @Id
    private String id;
    //项目名称
    private String name;
    //申请类型
    private String type;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(updatable = false)
    @org.hibernate.annotations.CreationTimestamp
    @CreatedDate
    //申请时间
    private Date createTime;
    //最后修改时间
    private Date updateTime;
    //申请材料
    private String material;
    //申请人
    @ManyToOne
    private User user;
    //审核人
    private String verifyName;
    //状态
    private String status;
    //等级
    private Integer grade;

    protected Application() {
    }

    public Application(String name, String type, String material) {
        this.name = name;
        this.type = type;
        this.material = material;
        this.status = "WAITING";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public String getVerifyName() {
        return verifyName;
    }

    public void setVerifyName(String verifyName) {
        this.verifyName = verifyName;
    }
}
