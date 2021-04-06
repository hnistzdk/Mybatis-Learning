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
}
