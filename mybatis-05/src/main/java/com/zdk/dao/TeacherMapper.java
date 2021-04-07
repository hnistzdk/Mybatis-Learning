package com.zdk.dao;

import com.zdk.pojo.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author zdk
 * @date 2021/4/1 19:42
 */
public interface TeacherMapper {
    @Select("select * from teacher where id=#{tid}")
    Teacher getTeacher(@Param("tid") int id);
}
