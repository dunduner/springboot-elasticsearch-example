package com.zhangning.springbootelasticsearchexample.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zhangning
 * @date 2020/3/31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    private String name;
    private Integer age;
    private Float salary;
    private String address;
    private String remark;
    private Date createTime;
    private String birthDate;
}
