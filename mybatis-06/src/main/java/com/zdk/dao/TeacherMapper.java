package com.zdk.dao;

import com.zdk.pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author zdk
 * @date 2021/4/1 19:42
 */
public interface TeacherMapper {
    //获取所有老师
    List<Teacher> getTeacher();

    //获取一个老师下的所有学生以及这个老师的信息
    Teacher getTeacherS(@Param("tid") int id);

    //获取一个老师下的所有学生以及这个老师的信息
    Teacher getTeacherS2(@Param("tid") int id);
}
