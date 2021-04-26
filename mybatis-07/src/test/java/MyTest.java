import com.zdk.mapper.BlogMapper;
import com.zdk.pojo.Blog;
import com.zdk.utils.IdUtils;
import com.zdk.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author zdk
 * @date 2021/4/26 20:52
 */
public class MyTest {
    @Test
    public void addInitBlog(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);

        Blog blog=new Blog();
        blog.setId(IdUtils.getUUID());
        blog.setTitle("Mybatis如此简单");
        blog.setAuthor("狂神说");
        blog.setCreateTime(new Date());
        blog.setViews(9999);
        mapper.addBlog(blog);

        blog.setId(IdUtils.getUUID());
        blog.setTitle("Java如此简单");
        mapper.addBlog(blog);

        blog.setId(IdUtils.getUUID());
        blog.setTitle("Spring如此简单");
        mapper.addBlog(blog);

        blog.setId(IdUtils.getUUID());
        blog.setTitle("微服务如此简单");
        mapper.addBlog(blog);

        sqlSession.close();
    }
    @Test
    public void queryBlogIf(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        HashMap map = new HashMap<>();

        //map.put("title", "Java如此简单");
        map.put("author", "狂神说");
        List<Blog> blogs = mapper.queryBlogIf(map);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
    }
}
