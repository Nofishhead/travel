package com.fish.travel.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fish.travel.entity.Jingdianxinxi;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;
import java.util.Map;

@Mapper
public interface JingdianxinxiMapper extends BaseMapper<Jingdianxinxi> {
    
    @Select("SELECT j.*, d.diqumingcheng as suoshudiquName " +
            "FROM jingdianxinxi j " +
            "LEFT JOIN diqu d ON j.suoshudiqu = d.id")
    List<Map<String, Object>> selectListWithDiqu();
}
