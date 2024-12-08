package com.fish.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 景点信息
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
public class Jingdianxinxi implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 景点编号
     */
    private String jingdianbianhao;

    /**
     * 景点名称
     */
    private String jingdianmingcheng;

    /**
     * 所属地区
     */
    private Integer suoshudiqu;

    /**
     * 图片
     */
    private String tupian;

    /**
     * 开放时间
     */
    private String kaifangshijian;

    /**
     * 附近美食
     */
    private String fujinmeishi;

    /**
     * 地址
     */
    private String dizhi;

    /**
     * 票价
     */
    private BigDecimal piaojia;

    /**
     * 浏览量
     */
    private Integer liulanliang;

    /**
     * 描述
     */
    private String miaoshu;

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

    public String getJingdianbianhao() {
        return jingdianbianhao;
    }

    public void setJingdianbianhao(String jingdianbianhao) {
        this.jingdianbianhao = jingdianbianhao;
    }

    public String getJingdianmingcheng() {
        return jingdianmingcheng;
    }

    public void setJingdianmingcheng(String jingdianmingcheng) {
        this.jingdianmingcheng = jingdianmingcheng;
    }

    public Integer getSuoshudiqu() {
        return suoshudiqu;
    }

    public void setSuoshudiqu(Integer suoshudiqu) {
        this.suoshudiqu = suoshudiqu;
    }

    public String getTupian() {
        return tupian;
    }

    public void setTupian(String tupian) {
        this.tupian = tupian;
    }

    public String getKaifangshijian() {
        return kaifangshijian;
    }

    public void setKaifangshijian(String kaifangshijian) {
        this.kaifangshijian = kaifangshijian;
    }

    public String getFujinmeishi() {
        return fujinmeishi;
    }

    public void setFujinmeishi(String fujinmeishi) {
        this.fujinmeishi = fujinmeishi;
    }

    public String getDizhi() {
        return dizhi;
    }

    public void setDizhi(String dizhi) {
        this.dizhi = dizhi;
    }

    public BigDecimal getPiaojia() {
        return piaojia;
    }

    public void setPiaojia(BigDecimal piaojia) {
        this.piaojia = piaojia;
    }

    public Integer getLiulanliang() {
        return liulanliang;
    }

    public void setLiulanliang(Integer liulanliang) {
        this.liulanliang = liulanliang;
    }

    public String getMiaoshu() {
        return miaoshu;
    }

    public void setMiaoshu(String miaoshu) {
        this.miaoshu = miaoshu;
    }

    public LocalDateTime getAddtime() {
        return addtime;
    }

    public void setAddtime(LocalDateTime addtime) {
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "Jingdianxinxi{" +
        "id=" + id +
        ", jingdianbianhao=" + jingdianbianhao +
        ", jingdianmingcheng=" + jingdianmingcheng +
        ", suoshudiqu=" + suoshudiqu +
        ", tupian=" + tupian +
        ", kaifangshijian=" + kaifangshijian +
        ", fujinmeishi=" + fujinmeishi +
        ", dizhi=" + dizhi +
        ", piaojia=" + piaojia +
        ", liulanliang=" + liulanliang +
        ", miaoshu=" + miaoshu +
        ", addtime=" + addtime +
        "}";
    }
}
