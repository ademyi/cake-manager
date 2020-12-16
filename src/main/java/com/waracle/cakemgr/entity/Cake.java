package com.waracle.cakemgr.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Cake",
        uniqueConstraints = {@UniqueConstraint(columnNames = "ID"), @UniqueConstraint(columnNames = "TITLE")})
public class Cake implements Serializable {

    private static final long serialVersionUID = 4L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", unique = true, nullable = false)
    private Integer cakeId;

    @Column(name = "TITLE", unique = true, nullable = false, length = 100)
    private String title;

    @Column(name = "DESC", unique = false, nullable = false, length = 100)
    private String desc;

    @Column(name = "IMAGE", unique = false, nullable = false, length = 300)
    private String image;

    public Integer getCakeId() {
        return cakeId;
    }

    public void setCakeId(Integer cakeId) {
        this.cakeId = cakeId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String description) {
        this.desc = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
