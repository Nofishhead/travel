package com.fish.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 预定
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
public class Yuding implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 旅游线路id
     */
    private Integer lvyouxianluid;

    /**
     * 线路编号
     */
    private String xianlubianhao;

    /**
     * 线路名称
     */
    private String xianlumingcheng;

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
     * 订单号
     */
    private String dingdanhao;

    /**
     * 预订时间
     */
    private String yudingshijian;

    /**
     * 预订人姓名
     */
    private String yudingrenxingming;

    /**
     * 联系方式
     */
    private String lianxifangshi;

    /**
     * 状态
     */
    private String zhuangtai;

    /**
     * 备注
     */
    private String beizhu;

    /**
     * 预订人
     */
    private String yudingren;

    /**
     * 添加时间
     */
    private LocalDateTime addtime;

    /**
     * 是否支付
     */
    private String iszf;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLvyouxianluid() {
        return lvyouxianluid;
    }

    public void setLvyouxianluid(Integer lvyouxianluid) {
        this.lvyouxianluid = lvyouxianluid;
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

    public String getDingdanhao() {
        return dingdanhao;
    }

    public void setDingdanhao(String dingdanhao) {
        this.dingdanhao = dingdanhao;
    }

    public String getYudingshijian() {
        return yudingshijian;
    }

    public void setYudingshijian(String yudingshijian) {
        this.yudingshijian = yudingshijian;
    }

    public String getYudingrenxingming() {
        return yudingrenxingming;
    }

    public void setYudingrenxingming(String yudingrenxingming) {
        this.yudingrenxingming = yudingrenxingming;
    }

    public String getLianxifangshi() {
        return lianxifangshi;
    }

    public void setLianxifangshi(String lianxifangshi) {
        this.lianxifangshi = lianxifangshi;
    }

    public String getZhuangtai() {
        return zhuangtai;
    }

    public void setZhuangtai(String zhuangtai) {
        this.zhuangtai = zhuangtai;
    }

    public String getBeizhu() {
        return beizhu;
    }

    public void setBeizhu(String beizhu) {
        this.beizhu = beizhu;
    }

    public String getYudingren() {
        return yudingren;
    }

    public void setYudingren(String yudingren) {
        this.yudingren = yudingren;
    }

    public LocalDateTime getAddtime() {
        return addtime;
    }

    public void setAddtime(LocalDateTime addtime) {
        this.addtime = addtime;
    }

    public String getIszf() {
        return iszf;
    }

    public void setIszf(String iszf) {
        this.iszf = iszf;
    }

    @Override
    public String toString() {
        return "Yuding{" +
        "id=" + id +
        ", lvyouxianluid=" + lvyouxianluid +
        ", xianlubianhao=" + xianlubianhao +
        ", xianlumingcheng=" + xianlumingcheng +
        ", chufadi=" + chufadi +
        ", tujingdi=" + tujingdi +
        ", zhongdian=" + zhongdian +
        ", jiage=" + jiage +
        ", dingdanhao=" + dingdanhao +
        ", yudingshijian=" + yudingshijian +
        ", yudingrenxingming=" + yudingrenxingming +
        ", lianxifangshi=" + lianxifangshi +
        ", zhuangtai=" + zhuangtai +
        ", beizhu=" + beizhu +
        ", yudingren=" + yudingren +
        ", addtime=" + addtime +
        ", iszf=" + iszf +
        "}";
    }
}
