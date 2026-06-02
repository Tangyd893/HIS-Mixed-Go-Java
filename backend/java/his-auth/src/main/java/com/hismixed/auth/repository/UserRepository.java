package com.hismixed.auth.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hismixed.auth.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository extends BaseMapper<User> {
}