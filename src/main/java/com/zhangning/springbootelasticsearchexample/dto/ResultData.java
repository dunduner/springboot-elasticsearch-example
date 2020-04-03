package com.zhangning.springbootelasticsearchexample.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultData {
    private boolean status=true;
    private String code ="200";
    private int dataSize=0;
    private Object data;

}
