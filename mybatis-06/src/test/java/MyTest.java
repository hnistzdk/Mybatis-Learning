import com.zdk.dao.TeacherMapper;
import com.zdk.pojo.Teacher;
import com.zdk.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

/**
 * @author zdk
 * @date 2021/4/21 17:42
 */
public class MyTest {
    @Test
    public void test(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        List<Teacher> teacherList = mapper.getTeacher();
        for (Teacher teacher : teacherList) {
            System.out.println(teacher);
        }
    }
    @Test
    public void test1(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher= mapper.getTeacherS(1);
        System.out.println(teacher);
    }
    @Test
    public void test2(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher= mapper.getTeacherS2(1);
        System.out.println(teacher);
    }
}
