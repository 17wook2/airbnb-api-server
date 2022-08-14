package com.example.demo.Repository;

import com.example.demo.Domain.User;
import com.example.demo.dto.User.UserRegisterRequestDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {

    public int registerUser(UserRegisterRequestDto userRegisterRequestDto);
//    public User findUserByEmail(String userEmail);
    public int checkEmail(String userEmail);



}
