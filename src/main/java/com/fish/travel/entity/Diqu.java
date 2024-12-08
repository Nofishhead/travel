package com.fish.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 地区
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
public class Diqu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 地区名称
     */
    private String diqumingcheng;

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

    public String getDiqumingcheng() {
        return diqumingcheng;
    }

    public void setDiqumingcheng(String diqumingcheng) {
        this.diqumingcheng = diqumingcheng;
    }

    public LocalDateTime getAddtime() {
        return addtime;
    }

    public void setAddtime(LocalDateTime addtime) {
        this.addtime = addtime;
    }

    @Override
    public String toString() {
        return "Diqu{" +
        "id=" + id +
        ", diqumingcheng=" + diqumingcheng +
        ", addtime=" + addtime +
        "}";
    }
}
