package com.fish.travel.service.impl;

import com.fish.travel.entity.Admins;
import com.fish.travel.mapper.AdminsMapper;
import com.fish.travel.service.IAdminsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 管理员 服务实现类
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
@Service
public class AdminsServiceImpl extends ServiceImpl<AdminsMapper, Admins> implements IAdminsService {

}
