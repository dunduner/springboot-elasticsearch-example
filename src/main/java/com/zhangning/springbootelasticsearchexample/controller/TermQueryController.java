package com.zhangning.springbootelasticsearchexample.controller;

import com.zhangning.springbootelasticsearchexample.service.TermQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhangning
 * @date 2020/3/31
 */

@Controller
@RequestMapping(value = "/esQuery")
public class TermQueryController {
    @Autowired
    private TermQueryService termQueryService;


    /**
     * 单个查询
     * @return
     */
    @RequestMapping(value = "/termQuery")
    @ResponseBody
    public String termQuery(){
        termQueryService.termQuery();
        return "termQuery-success";
    }

    /**
     * 多地址查询
     * @return
     */
    @RequestMapping(value = "/termsQuery")
    @ResponseBody
    public String termsQuery(){
        termQueryService.termsQuery();
        return "多地址查询Query-success";
    }




}
