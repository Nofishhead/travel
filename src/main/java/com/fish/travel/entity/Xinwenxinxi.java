package com.fish.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 新闻信息
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
public class Xinwenxinxi implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 标题
     */
    private String biaoti;

    /**
     * 分类
     */
    private Integer fenlei;

    /**
     * 图片
     */
    private String tupian;

    /**
     * 添加人
     */
    private String tianjiaren;

    /**
     * 点击率
     */
    private Integer dianjilv;

    /**
     * 内容
     */
    private String neirong;

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

    public String getBiaoti() {
        return biaoti;
    }

    public void setBiaoti(String biaoti) {
        this.biaoti = biaoti;
    }

    public Integer getFenlei() {
        return fenlei;
    }

    public void setFenlei(Integer fenlei) {
        this.fenlei = fenlei;
    }

    public String getTupian() {
        return tupian;
    }

    public void setTupian(String tupian) {
        this.tupian = tupian;
    }

    public String getTianjiaren() {
        return tianjiaren;
    }

    public void setTianjiaren(String tianjiaren) {
        this.tianjiaren = tianjiaren;
    }

    public Integer getDianjilv() {
        return dianjilv;
    }

    public void setDianjilv(Integer dianjilv) {
        this.dianjilv = dianjilv;
    }

    public String getNeirong() {
        return neirong;
    }

    public void setNeirong(String neirong) {
        this.neirong = neirong;
    }

    public LocalDateTime getAddtime() {
        return addtime;
    }

    public void setAddtime(LocalDateTime addtime) {
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "Xinwenxinxi{" +
        "id=" + id +
        ", biaoti=" + biaoti +
        ", fenlei=" + fenlei +
        ", tupian=" + tupian +
        ", tianjiaren=" + tianjiaren +
        ", dianjilv=" + dianjilv +
        ", neirong=" + neirong +
        ", addtime=" + addtime +
        "}";
    }
}
