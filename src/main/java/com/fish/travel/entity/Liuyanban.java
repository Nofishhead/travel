package com.fish.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 留言板
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
public class Liuyanban implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    private String xingming;

    /**
     * 联系电话
     */
    private String lianxidianhua;

    /**
     * 留言内容
     */
    private String liuyanneirong;

    /**
     * 留言人
     */
    private String liuyanren;

    /**
     * 回复内容
     */
    private String huifuneirong;

    /**
     * 添加时间
     */
    private LocalDateTime addtime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getXingming() {
        return xingming;
    }

    public void setXingming(String xingming) {
        this.xingming = xingming;
    }

    public String getLianxidianhua() {
        return lianxidianhua;
    }

    public void setLianxidianhua(String lianxidianhua) {
        this.lianxidianhua = lianxidianhua;
    }

    public String getLiuyanneirong() {
        return liuyanneirong;
    }

    public void setLiuyanneirong(String liuyanneirong) {
        this.liuyanneirong = liuyanneirong;
    }

    public String getLiuyanren() {
        return liuyanren;
    }

    public void setLiuyanren(String liuyanren) {
        this.liuyanren = liuyanren;
    }

    public String getHuifuneirong() {
        return huifuneirong;
    }

    public void setHuifuneirong(String huifuneirong) {
        this.huifuneirong = huifuneirong;
    }

    public LocalDateTime getAddtime() {
        return addtime;
    }

    public void setAddtime(LocalDateTime addtime) {
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "Liuyanban{" +
        "id=" + id +
        ", xingming=" + xingming +
        ", lianxidianhua=" + lianxidianhua +
        ", liuyanneirong=" + liuyanneirong +
        ", liuyanren=" + liuyanren +
        ", huifuneirong=" + huifuneirong +
        ", addtime=" + addtime +
        "}";
    }
}
