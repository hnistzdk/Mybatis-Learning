##mybatis常用配置
```xml
<?xml version="1.0" encoding="UTF8" ?>
<!--究极之恶心的  如果xml文件的第一行的 encoding=UTF-8就会报错   改成UTF8才不会报错-->
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<!--configuration为核心配置文件-->
<configuration>
<!--    引入外部配置文件-->
    <properties resource="db.properties">
<!--        在db.properties文件中不写以下两个熟悉也是可以的   只需在properties中加入property标签添加相应属性即可-->
<!--        <property name="username" value="root"/>-->
<!--        <property name="password" value="root"/>-->
<!--        对比可得出  若属性名相同,会优先使用外部配置文件db.properties中的属性-->

    </properties>
    
<!--    配置日志-->
    <settings>
<!--        标准日志工厂实现-->
<!--        <setting name="logImpl" value="STDOUT_LOGGING"/>-->


<!--        LOG4J需要先导包 然后需要log4j.properties配置文件-->
        <setting name="logImpl" value="LOG4J"/>

    </settings>
    

<!--   1 可以给实体类起别名-->
    <typeAliases>
        <typeAlias type="com.zdk.pojo.User" alias="User"/>
<!--       2 扫描实体类的包  它的默认别名就为这个类的类名的小写字母形式-->
<!--        实测  全小写或全大写都能识别出  官方建议 全小写-->
<!--        实体类较少时 建议使用起别名方式;实体类较少时，建议使用扫描包的方式-->
<!--还可通过 @Alias() 注解的方式给实体类起别名，但注解方式失败  原因未知！！！！！！！-->
        <package name="com.zdk.pojo.User"/>
    </typeAliases>

    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="${driver}"/>
                <property name="url" value="${url}"/>
                <property name="username" value="${username}"/>
                <property name="password" value="${password}"/>
            </dataSource>
        </environment>
    </environments>
<!--    每一个Mapper.xml都需要在mybatis核心配置文件中注册!-->
    <mappers>
        <mapper resource="com/zdk/dao/UserMapper.xml"/>
<!--        通过接口路径进行映射时 接口和xml文件必须和类在同一个包下 且必须同名-->
<!--        <mapper class="com.zdk.dao.UserMapper"></mapper>-->

<!--        包扫描绑定 将包下的所有xml注册   注意点同上-->
<!--        <package name="com.zdk.dao"/>-->
    </mappers>
</configuration>
```
##资源过滤器
使用此种方法时 必须配置资源过滤
![img.png](img.png)
```xml
<!--    配置过滤 防止资源导出失败-->
    <build>
        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
            <resource>
                <directory>src/main/java</directory>
                <includes>
                    <include>**/*.properties</include>
                    <include>**/*.xml</include>
                </includes>
                <filtering>true</filtering>
            </resource>
        </resources>
    </build>
```


##常用增删改查、分页xml配置文件
```xml
<?xml version="1.0" encoding="UTF8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<!--namespace绑定一个对应的Dao/Mapper接口-->
<mapper namespace="com.zdk.dao.UserMapper">

    <!--    查询语句-->
    <!--    id对应接口方法名称-->

    <!--    结果集映射  将select中的返回类型改成resultMap 值即为以下的id  然后其中的column为数据库中的字段名 property即为实体类中的属性名-->
    <resultMap id="UserMap" type="User">
        <!--        <result column="id" property="id"></result>-->
        <!--        <result column="name" property="name"></result>-->
        <!--        哪条字段不一致就映射哪一条-->
        <result column="password" property="password"></result>
    </resultMap>

    <select id="getUserList" resultMap="UserMap">
        select * from user ;
    </select>


    <select id="getUserById" resultType="User" parameterType="int">
        select * from user where id=#{id};
    </select>
    <insert id="addUser" parameterType="User">
        insert into user (id, name, password) values (#{id},#{name},#{password});
    </insert>
    <update id="modifyUser" parameterType="User">
        update user set name=#{name},password=#{password} where id=#{id};
    </update>
    <delete id="deleteUser" parameterType="int">
        delete from user where id=#{id};
    </delete>

    <!--    分页1-->
    <select id="getUserListByLimit" parameterType="map" resultType="user">
        select * from user limit #{startIndex},#{pageSize};
    </select>

    <!--    分页2-->
    <select id="getUserListByRowBounds" resultType="user">
        select * from user;
    </select>
</mapper>
```

##简单注解的使用
```java
public interface UserMapper {

    @Select("select * from user")
    List<User> getUsers();

    @Delete("delete from user where id=#{id}")
    int deleteUser(@Param("id") int id);

    @Insert("insert into user(id,name,password) values (#{id},#{name},#{password})")
    int addUser(User user);

    @Update("update user set name=#{name},password=#{password} where id=#{id}")
    int updateUser(User user);
}
```

##多对一的处理
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private int id;
    private String name;
    private Teacher teacher;
}
```
因为每个Student对象中都有一个Teacher，使用常规查询无法得到teacher，需要嵌套
```xml
<?xml version="1.0" encoding="UTF8" ?>
<!--究极之恶心的  如果xml文件的第一行的 encoding=UTF-8就会报错   改成UTF8才不会报错-->
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.zdk.dao.StudentMapper">
    <!--    一.按照查询进行嵌套处理
            1.查询所有学生信息
            2.按照查询出的tid，查询对应的老师信息
    -->
    <select id="getStudentList" resultMap="StudentAndTeacher">
        select * from student;
    </select>
    <resultMap id="StudentAndTeacher" type="Student">
        <result property="id" column="id"/>
        <result property="name" column="name"/>
        <!--  复杂属性 单独处理      association处理对象  collection用于处理集合-->
        <association property="teacher" column="tid" javaType="Teacher" select="getTeacher"/>
    </resultMap>
    <select id="getTeacher" resultType="Teacher">
        select * from teacher where id=#{id};
    </select>


    <!--        二.按照结果嵌套处理-->
    <select id="getStudentList2" resultMap="StudentAndTeacher2">
        select s.id sid,s.name sname,t.id ttid,t.name tname
        from student s,teacher t
        where s.tid=t.id;
    </select>
    <resultMap id="StudentAndTeacher2" type="Student">
        <result property="id" column="sid"/>
        <result property="name" column="sname"/>
        <association property="teacher" javaType="Teacher">
            <result property="id" column="ttid"/>
            <result property="name" column="tname"/>
        </association>
    </resultMap>
</mapper>
```

##一对多的处理
###一个老师对应多个学生：实体类

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private int id;
    private String name;
    private int tid;
}
```
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher {
    private int id;
    private String name;
    //一个老师拥有多个学生
    
    private List<Student> students;
}
```

###老师的mapper
```java
public interface TeacherMapper {
    //获取所有老师
    List<Teacher> getTeacher();

    //获取一个老师下的所有学生以及这个老师的信息
    Teacher getTeacherS(@Param("tid") int id);

    //获取一个老师下的所有学生以及这个老师的信息
    Teacher getTeacherS2(@Param("tid") int id);
}
```

###TeacherMapper.xml文件配置
####方法一 按结果嵌套查询

#####xml
```xml
<!--    方法一  按结果嵌套查询-->
<!--    先将所有要查的字段全部查出来-->
    <select id="getTeacherS" resultMap="getTeacherSMap">
        select s.id sid,s.name sname,t.name tname,t.id tid
        from student s,teacher t
        where s.tid=t.id and t.id=#{tid};
    </select>
<!--    再将查出来的字段映射到老师对应的属性中去  collection表示集合   association表示对象-->
    <resultMap id="getTeacherSMap" type="Teacher">
        <result property="id" column="tid"/>
        <result property="name" column="tname"/>
        <collection property="students" ofType="Student">
            <result property="id" column="sid"/>
            <result property="name" column="sname"/>
            <result property="tid" column="tid"/>
        </collection>
    </resultMap>
```
#####测试
```java
public class MyTest {
    @Test
    public void test1(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher= mapper.getTeacherS(1);
        System.out.println(teacher);
    }
}
```

####利用子查询 然后映射

#####xml
```xml
<!--    方法2 子查询-->
<!--    先直接查老师的所有信息-->
    <select id="getTeacherS2" resultMap="getTeacherSMap2">
        select *
        from teacher
        where id=#{tid};
    </select>
<!--    子查询语句-->
    <select id="getStudentByTeacherId" resultType="Student">
        select *
        from student where tid=#{tid};
    </select>
<!--    对Teacher中的List<Student> students进行映射-->
    <resultMap id="getTeacherSMap2" type="Teacher">
<!--        这句话得加上  不然老师查出的id为0-->
        <result property="id" column="id"/>
<!--        Javatype是表示students属性的类型，oftype表示这个类型的泛型类型-->
        <collection property="students" javaType="ArrayList" ofType="Student" select="getStudentByTeacherId" column="id"/>
    </resultMap>
```

#####测试
```java
public class MyTest {
    @Test
    public void test2(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        Teacher teacher= mapper.getTeacherS2(1);
        System.out.println(teacher);
    }
}
```
#####结果均可查出
```text
Teacher(id=1, name=秦老师, students=[Student(id=1, name=小明, tid=1), 
Student(id=2, name=小红, tid=1), Student(id=3, name=小张, tid=1), 
Student(id=4, name=小李, tid=1), Student(id=5, name=小王, tid=1)])
```


##动态SQL(重点)
###环境搭建
```java
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Blog {
    private String id;
    private String title;
    private String author;
    private Date createTime;
    private int views;
}
```
```java
public interface BlogMapper {
    int addBlog(Blog blog);

    //查询博客 通过if
    List<Blog> queryBlogIf(Map map);
}
```
```java
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
}
```

###if语句使用

```xml
<?xml version="1.0" encoding="UTF8" ?>
<!--究极之恶心的  如果xml文件的第一行的 encoding=UTF-8就会报错   改成UTF8才不会报错-->
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<mapper namespace="com.zdk.mapper.BlogMapper">

    <insert id="addBlog" parameterType="Blog">
        insert into blog (id, title, author, create_time, views)
        values (#{id},#{title},#{author},#{createTime},#{views});
    </insert>
    <select id="queryBlogIf" parameterType="map" resultType="Blog">
        select * from blog where 1=1
        <if test="title != null">
            and title=#{title}
        </if>
        <if test="author != null">
            and author=#{author}
        </if>
    </select>
</mapper>
```
####测试结果
```java
public class MyTest {
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
```
```text
Blog(id=5d18217da51c46f981909c65a5304b62, title=Mybatis如此简单, author=狂神说, createTime=Mon Apr 26 20:59:02 CST 2021, views=3000)
Blog(id=aa5ea0c0b60e4755897007da040a5901, title=Java如此简单, author=狂神说, createTime=Mon Apr 26 20:59:02 CST 2021, views=1000)
Blog(id=9e497893427141469a6a0b041ad0b34d, title=Spring如此简单, author=狂神说, createTime=Mon Apr 26 20:59:02 CST 2021, views=9999)
Blog(id=e7a2240692a44d7bba299db170b14cf0, title=微服务如此简单, author=狂神说, createTime=Mon Apr 26 20:59:02 CST 2021, views=9999)
```


