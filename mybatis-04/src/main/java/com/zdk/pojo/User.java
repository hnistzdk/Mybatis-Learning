package com.zdk.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.ibatis.type.Alias;

/**
 * @author zdk
 * @date 2021/3/28 15:30
 */
@Data
@AllArgsConstructor
@Alias("hello")
public class User {
    private int id;
    private String name;
    private String pwd;
}
