package com.zdk.dao;

import com.zdk.pojo.User;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zdk
 * @date 2021/3/28 15:43
 */
public interface UserMapper {

    @Select("select * from user")
    List<User> getUsers();
}
