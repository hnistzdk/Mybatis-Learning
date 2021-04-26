package com.zdk.mapper;

import com.zdk.pojo.Blog;

import java.util.List;
import java.util.Map;

/**
 * @author zdk
 * @date 2021/4/26 20:41
 */
public interface BlogMapper {
    int addBlog(Blog blog);

    //查询博客 通过if
    List<Blog> queryBlogIf(Map map);
}
