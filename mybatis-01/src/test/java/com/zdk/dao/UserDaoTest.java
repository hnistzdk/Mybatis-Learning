package com.zdk.dao;

import com.zdk.pojo.User;
import com.zdk.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import java.util.List;

/**
 * @author zdk
 * @date 2021/3/28 15:57
 */
public class UserDaoTest {
    @Test
    public void test() {
        //获取对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();

        //执行sql
        //方式1  getMapper获取对应的接口类  再调用方法
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> userList = mapper.getUserList();

        //方式2  不推荐使用
        //List<User> userList = sqlSession.selectList("com.zdk.dao.UserMapper.getUserList");


        for (User user:userList){
            System.out.println(user);
        }
        sqlSession.close();
    }

    @Test
    public void testGetUserById(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper=sqlSession.getMapper(UserMapper.class);
        User user= mapper.getUserById(1);
        System.out.println(user);
        sqlSession.close();
    }
    @Test
    public void testAddUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper=sqlSession.getMapper(UserMapper.class);
        int count=mapper.addUser(new User(5,"田七","123456"));
        if(count>0){
            System.out.println("插入成功");
        }
        //执行增删改操作必须提交事务  才能改变到数据库中 否则数据库数据不会发生更新
        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void testModifyUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper=sqlSession.getMapper(UserMapper.class);
        int count=mapper.modifyUser(new User(5,"田七","000000"));
        if(count>0){
            System.out.println("修改成功");
        }
        //执行增删改操作必须提交事务  才能改变到数据库中 否则数据库数据不会发生更新
        sqlSession.commit();
        sqlSession.close();
    }
    @Test
    public void testDeleteUser(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper=sqlSession.getMapper(UserMapper.class);
        int count=mapper.deleteUser(5);
        if(count>0){
            System.out.println("删除成功");
        }
        //执行增删改操作必须提交事务  才能改变到数据库中 否则数据库数据不会发生更新
        sqlSession.commit();
        sqlSession.close();
    }
}
