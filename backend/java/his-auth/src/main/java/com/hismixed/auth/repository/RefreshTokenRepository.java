package com.hismixed.auth.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hismixed.auth.entity.RefreshToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RefreshTokenRepository extends BaseMapper<RefreshToken> {
}