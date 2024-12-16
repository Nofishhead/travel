package com.fish.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fish.travel.entity.Lunbotu;
import com.fish.travel.mapper.LunbotuMapper;
import com.fish.travel.service.ILunbotuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 轮播图 服务实现类
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
@Service
public class LunbotuServiceImpl extends ServiceImpl<LunbotuMapper, Lunbotu> implements ILunbotuService {

    // 获取轮播图列表,按添加时间降序排序
    @Override
    public List<Lunbotu> list() {
        LambdaQueryWrapper<Lunbotu> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByDesc(Lunbotu::getAddtime);
        return super.list(queryWrapper);
    }
}
