package com.zdk.dao;

import com.zdk.pojo.User;

import java.util.List;
import java.util.Map;

/**
 * @author zdk
 * @date 2021/3/28 15:43
 */
public interface UserMapper {
    List<User> getUserList();
    User getUserById(int id);
    int addUser(User user);
    int modifyUser(User user);
    int deleteUser(int id);
    List<User> getUserListByLimit(Map<String,Integer> map);
    List<User> getUserListByRowBounds(Map<String,Integer> map);

}
