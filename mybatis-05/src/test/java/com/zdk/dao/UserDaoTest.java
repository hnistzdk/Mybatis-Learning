package com.zdk.dao;

import com.zdk.pojo.Student;
import com.zdk.pojo.Teacher;
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
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher=mapper.getTeacher(1);
        System.out.println(teacher);
        sqlSession.close();
    }
    @Test
    public void testStudent(){
        SqlSession sqlSession=MybatisUtils.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> studentList = mapper.getStudentList();
        for (Student student : studentList) {
            System.out.println(student);
        }
    }
    @Test
    public void testStudent2(){
        SqlSession sqlSession=MybatisUtils.getSqlSession();
        StudentMapper mapper = sqlSession.getMapper(StudentMapper.class);
        List<Student> studentList = mapper.getStudentList2();
        for (Student student : studentList) {
            System.out.println(student);
        }
    }
}
