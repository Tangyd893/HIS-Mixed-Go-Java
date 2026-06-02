package com.hismixed.auth.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hismixed.auth.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface RoleRepository extends BaseMapper<Role> {
    @Select("SELECT r.* FROM roles r JOIN user_roles ur ON r.id = ur.role_id WHERE ur.user_id = #{userId}")
    List<Role> selectRolesByUserId(Long userId);
}