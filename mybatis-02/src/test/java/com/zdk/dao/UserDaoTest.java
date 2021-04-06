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

        //模糊查询的写法
        //1.在Java代码执行的时候 传递通配符
//        List<User> userList1=mapper.getUserList("%李%");

        //2.在sql语句中拼接通配符
//        select *from user where name like "%"#{value}"%"
    }
}
