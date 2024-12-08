package com.fish.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
public class Yonghu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String yonghuming;

    /**
     * 密码
     */
    private String mima;

    /**
     * 姓名
     */
    private String xingming;

    /**
     * 性别
     */
    private String xingbie;

    /**
     * 手机
     */
    private String shouji;

    /**
     * 邮箱
     */
    private String youxiang;

    /**
     * 身份证
     */
    private String shenfenzheng;

    /**
     * 头像
     */
    private String touxiang;

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

    public String getYonghuming() {
        return yonghuming;
    }

    public void setYonghuming(String yonghuming) {
        this.yonghuming = yonghuming;
    }

    public String getMima() {
        return mima;
    }

    public void setMima(String mima) {
        this.mima = mima;
    }

    public String getXingming() {
        return xingming;
    }

    public void setXingming(String xingming) {
        this.xingming = xingming;
    }

    public String getXingbie() {
        return xingbie;
    }

    public void setXingbie(String xingbie) {
        this.xingbie = xingbie;
    }

    public String getShouji() {
        return shouji;
    }

    public void setShouji(String shouji) {
        this.shouji = shouji;
    }

    public String getYouxiang() {
        return youxiang;
    }

    public void setYouxiang(String youxiang) {
        this.youxiang = youxiang;
    }

    public String getShenfenzheng() {
        return shenfenzheng;
    }

    public void setShenfenzheng(String shenfenzheng) {
        this.shenfenzheng = shenfenzheng;
    }

    public String getTouxiang() {
        return touxiang;
    }

    public void setTouxiang(String touxiang) {
        this.touxiang = touxiang;
    }

    public LocalDateTime getAddtime() {
        return addtime;
    }

    public void setAddtime(LocalDateTime addtime) {
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "Yonghu{" +
        "id=" + id +
        ", yonghuming=" + yonghuming +
        ", mima=" + mima +
        ", xingming=" + xingming +
        ", xingbie=" + xingbie +
        ", shouji=" + shouji +
        ", youxiang=" + youxiang +
        ", shenfenzheng=" + shenfenzheng +
        ", touxiang=" + touxiang +
        ", addtime=" + addtime +
        "}";
    }
}
