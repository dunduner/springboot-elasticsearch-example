package com.zhangning.springbootelasticsearchexample.controller;

import com.zhangning.springbootelasticsearchexample.dto.ResultData;
import com.zhangning.springbootelasticsearchexample.service.MatchQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/esMatchQuery")
public class MatchQueryController {
    @Autowired
    private MatchQueryService matchQueryService;


    /**
     * 查询所有并分页
     * http://localhost:8080/esMatchQuery/matchAllQuery?from=0&size=5
     * @param from 初始位置
     * @param size 查几条
     *             salary  进行升序排列
     * @return
     */
    @RequestMapping(value = "/matchAllQuery")
    @ResponseBody
    public ResultData termQuery(Integer from ,Integer size) {
        ResultData resultData = matchQueryService.matchAllQuery(from, size);
        return resultData;
    }
//    matchQuery
//http://localhost:8080/esMatchQuery/matchQuery?queryCondition=remark&condition=层&from=0&size=1000
//    模糊 匹配查询地址含有  “通”   “州”    “区” 这3个字的数据：  有点分词的感觉
    @RequestMapping(value = "/matchQuery")
    @ResponseBody
    public ResultData matchQuery(String queryCondition,String condition,Integer from ,Integer size) {
        ResultData address = matchQueryService.matchQuery(queryCondition, condition,from,size);
        return address;
    }


}
