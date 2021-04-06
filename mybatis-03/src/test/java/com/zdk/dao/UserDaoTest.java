package com.zdk.dao;

import com.sun.javafx.collections.MappingChange;
import com.zdk.pojo.User;
import com.zdk.utils.MybatisUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author zdk
 * @date 2021/3/28 15:57
 */
public class UserDaoTest {

    //日志使用  导Apache log4j的包才行
    //获取Logger对象
    static Logger logger = Logger.getLogger(UserDaoTest.class);

    @Test
    public void test() {
        //获取对象
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = mapper.getUserById(1);
        System.out.println(user);
        sqlSession.close();
    }

    //测试SQL语句的limit 起始,一页几个 (两个参数)    分页
    @Test
    public void limit(){
        SqlSession sqlSession=MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Map<String, Integer> map=new HashMap<>();
        map.put("startIndex", 0);
        map.put("pageSize", 2);
        List<User> userList=mapper.getUserListByLimit(map);
        for (User user : userList) {
            System.out.println(user);
        }
        sqlSession.close();
    }

    @Test
    public void rowBounds(){
        SqlSession sqlSession=MybatisUtils.getSqlSession();
        RowBounds rowBounds = new RowBounds(1,2);
        List<User> userList= sqlSession.selectList("com.zdk.dao.UserMapper.getUserListByRowBounds",null ,rowBounds);
        for (User user : userList) {
            System.out.println(user);
        }
        sqlSession.close();
    }

    @Test
    public void testLOG4J(){
        logger.info("info:进入了testLOG4J方法");
        logger.debug("debug:进入了testLOG4J方法");
        logger.error("error:进入了testLOG4J方法");
    }
}
