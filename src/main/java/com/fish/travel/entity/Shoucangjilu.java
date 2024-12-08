package com.fish.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 收藏记录
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
public class Shoucangjilu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 收藏用户
     */
    private String username;

    /**
     * 对应模块id
     */
    private Integer xwid;

    /**
     * 收藏得模块
     */
    private String biao;

    /**
     * 显示的标题
     */
    private String biaoti;

    /**
     * 收藏URL
     */
    private String url;

    /**
     * 对应模块字段
     */
    private String ziduan;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getXwid() {
        return xwid;
    }

    public void setXwid(Integer xwid) {
        this.xwid = xwid;
    }

    public String getBiao() {
        return biao;
    }

    public void setBiao(String biao) {
        this.biao = biao;
    }

    public String getBiaoti() {
        return biaoti;
    }

    public void setBiaoti(String biaoti) {
        this.biaoti = biaoti;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getZiduan() {
        return ziduan;
    }

    public void setZiduan(String ziduan) {
        this.ziduan = ziduan;
    }

    public LocalDateTime getAddtime() {
        return addtime;
    }

    public void setAddtime(LocalDateTime addtime) {
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "Shoucangjilu{" +
        "id=" + id +
        ", username=" + username +
        ", xwid=" + xwid +
        ", biao=" + biao +
        ", biaoti=" + biaoti +
        ", url=" + url +
        ", ziduan=" + ziduan +
        ", addtime=" + addtime +
        "}";
    }
}
