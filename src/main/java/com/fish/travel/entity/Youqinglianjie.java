package com.fish.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 友情链接
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
public class Youqinglianjie implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 网站名称
     */
    private String wangzhanmingcheng;

    /**
     * 网址
     */
    private String wangzhi;

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

    public String getWangzhanmingcheng() {
        return wangzhanmingcheng;
    }

    public void setWangzhanmingcheng(String wangzhanmingcheng) {
        this.wangzhanmingcheng = wangzhanmingcheng;
    }

    public String getWangzhi() {
        return wangzhi;
    }

    public void setWangzhi(String wangzhi) {
        this.wangzhi = wangzhi;
    }

    public LocalDateTime getAddtime() {
        return addtime;
    }

    public void setAddtime(LocalDateTime addtime) {
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "Youqinglianjie{" +
        "id=" + id +
        ", wangzhanmingcheng=" + wangzhanmingcheng +
        ", wangzhi=" + wangzhi +
        ", addtime=" + addtime +
        "}";
    }
}
