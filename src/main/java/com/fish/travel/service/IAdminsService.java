package com.fish.travel.service;

import com.fish.travel.Result.Result;
import com.fish.travel.dto.AdminLoginDTO;
import com.fish.travel.entity.Admins;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 管理员 服务类
 * </p>
 *
 * @author fish
 * @since 2024-12-08
 */
public interface IAdminsService extends IService<Admins> {

    Result login(AdminLoginDTO admin);
}
