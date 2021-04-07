package com.zdk.dao;

import com.zdk.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zdk
 * @date 2021/3/28 15:43
 */
public interface UserMapper {

    @Select("select * from user")
    List<User> getUsers();

    @Delete("delete from user where id=#{id}")
    int deleteUser(@Param("id") int id);

    @Insert("insert into user(id,name,password) values (#{id},#{name},#{password})")
    int addUser(User user);

    @Update("update user set name=#{name},password=#{password} where id=#{id}")
    int updateUser(User user);
}
