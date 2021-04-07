package com.zdk.dao;

import com.zdk.pojo.User;
import com.zdk.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;


/**
 * @author zdk
 * @date 2021/3/28 15:57
 */
public class UserDaoTest {

    //日志使用  导Apache log4j的包才行
    //获取Logger对象

    @Test
    public void test() {
        //获取对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = mapper.getUsers();
        for (User user : userList) {
            System.out.println(user);
        }
        sqlSession.close();
    }
    @Test
    public void deleteUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int count=mapper.deleteUser(1);
        sqlSession.commit();
        System.out.println(count);
        sqlSession.close();
    }
    @Test
    public void addUser(){
        SqlSession sqlSession=MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int count=mapper.addUser(new User(7, "张", "123145"));
        sqlSession.commit();
        System.out.println(count);
        sqlSession.close();
    }
    @Test
    public void updateUser(){
        SqlSession sqlSession=MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int count=mapper.updateUser(new User(1, "张三", "888888"));
        sqlSession.commit();
        System.out.println(count);
        sqlSession.close();
    }
}
