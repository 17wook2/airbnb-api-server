package com.example.demo.Repository;

import com.example.demo.Domain.User;
import com.example.demo.dto.User.UserRegisterRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRepository {

    public long registerUser(@Param("userEmail") String userEmail, @Param("userPassword") String userPassword);
    public long registerUserFull(User user);
    public User findUserByEmail(String userEmail);
    public int checkEmail(String userEmail);
    public int updateRefreshToken(@Param("userId") Long userId, @Param("refreshToken") String refreshToken);
}
