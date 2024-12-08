package com.fish.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 地方美食
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
public class Difangmeishi implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 美食编号
     */
    private String meishibianhao;

    /**
     * 名称
     */
    private String mingcheng;

    /**
     * 附近景点
     */
    private String fujinjingdian;

    /**
     * 分类
     */
    private Integer fenlei;

    /**
     * 图片
     */
    private String tupian;

    /**
     * 价格
     */
    private BigDecimal jiage;

    /**
     * 美食简介
     */
    private String meishijianjie;

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

    public String getMeishibianhao() {
        return meishibianhao;
    }

    public void setMeishibianhao(String meishibianhao) {
        this.meishibianhao = meishibianhao;
    }

    public String getMingcheng() {
        return mingcheng;
    }

    public void setMingcheng(String mingcheng) {
        this.mingcheng = mingcheng;
    }

    public String getFujinjingdian() {
        return fujinjingdian;
    }

    public void setFujinjingdian(String fujinjingdian) {
        this.fujinjingdian = fujinjingdian;
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

    public BigDecimal getJiage() {
        return jiage;
    }

    public void setJiage(BigDecimal jiage) {
        this.jiage = jiage;
    }

    public String getMeishijianjie() {
        return meishijianjie;
    }

    public void setMeishijianjie(String meishijianjie) {
        this.meishijianjie = meishijianjie;
    }

    public LocalDateTime getAddtime() {
        return addtime;
    }

    public void setAddtime(LocalDateTime addtime) {
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "Difangmeishi{" +
        "id=" + id +
        ", meishibianhao=" + meishibianhao +
        ", mingcheng=" + mingcheng +
        ", fujinjingdian=" + fujinjingdian +
        ", fenlei=" + fenlei +
        ", tupian=" + tupian +
        ", jiage=" + jiage +
        ", meishijianjie=" + meishijianjie +
        ", addtime=" + addtime +
        "}";
    }
}
