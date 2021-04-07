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
##常用增删改查、分页xml配置文件
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

