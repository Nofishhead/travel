package com.fish.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 旅游线路
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
public class Lvyouxianlu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 线路编号
     */
    private String xianlubianhao;

    /**
     * 线路名称
     */
    private String xianlumingcheng;

    /**
     * 图片
     */
    private String tupian;

    /**
     * 出发地
     */
    private String chufadi;

    /**
     * 途经地
     */
    private String tujingdi;

    /**
     * 终点
     */
    private String zhongdian;

    /**
     * 价格
     */
    private BigDecimal jiage;

    /**
     * 浏览量
     */
    private Integer liulanliang;

    /**
     * 线路特色
     */
    private String xianlutese;

    /**
     * 线路简介
     */
    private String xianlujianjie;

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

    public String getXianlubianhao() {
        return xianlubianhao;
    }

    public void setXianlubianhao(String xianlubianhao) {
        this.xianlubianhao = xianlubianhao;
    }

    public String getXianlumingcheng() {
        return xianlumingcheng;
    }

    public void setXianlumingcheng(String xianlumingcheng) {
        this.xianlumingcheng = xianlumingcheng;
    }

    public String getTupian() {
        return tupian;
    }

    public void setTupian(String tupian) {
        this.tupian = tupian;
    }

    public String getChufadi() {
        return chufadi;
    }

    public void setChufadi(String chufadi) {
        this.chufadi = chufadi;
    }

    public String getTujingdi() {
        return tujingdi;
    }

    public void setTujingdi(String tujingdi) {
        this.tujingdi = tujingdi;
    }

    public String getZhongdian() {
        return zhongdian;
    }

    public void setZhongdian(String zhongdian) {
        this.zhongdian = zhongdian;
    }

    public BigDecimal getJiage() {
        return jiage;
    }

    public void setJiage(BigDecimal jiage) {
        this.jiage = jiage;
    }

    public Integer getLiulanliang() {
        return liulanliang;
    }

    public void setLiulanliang(Integer liulanliang) {
        this.liulanliang = liulanliang;
    }

    public String getXianlutese() {
        return xianlutese;
    }

    public void setXianlutese(String xianlutese) {
        this.xianlutese = xianlutese;
    }

    public String getXianlujianjie() {
        return xianlujianjie;
    }

    public void setXianlujianjie(String xianlujianjie) {
        this.xianlujianjie = xianlujianjie;
    }

    public LocalDateTime getAddtime() {
        return addtime;
    }

    public void setAddtime(LocalDateTime addtime) {
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "Lvyouxianlu{" +
        "id=" + id +
        ", xianlubianhao=" + xianlubianhao +
        ", xianlumingcheng=" + xianlumingcheng +
        ", tupian=" + tupian +
        ", chufadi=" + chufadi +
        ", tujingdi=" + tujingdi +
        ", zhongdian=" + zhongdian +
        ", jiage=" + jiage +
        ", liulanliang=" + liulanliang +
        ", xianlutese=" + xianlutese +
        ", xianlujianjie=" + xianlujianjie +
        ", addtime=" + addtime +
        "}";
    }
}
