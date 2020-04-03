package com.zhangning.springbootelasticsearchexample.controller;

import com.zhangning.springbootelasticsearchexample.dto.ResultData;
import com.zhangning.springbootelasticsearchexample.service.FuzzyQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangning
 * @date 2020/4/1
 */
@RestController
@RequestMapping(value = "/esFuzzytQuery")
public class FuzzyQueryController {

    @Autowired
    private FuzzyQueryService fuzzyQueryService;

    /**
     * @param index
     * @param field
     * @param value
     * @param from
     * @param size
     * @return
     * http://localhost:8080/esFuzzytQuery/fuzzy?field=name&value=三
     */
    @GetMapping("/fuzzy")
    public ResultData fuzzyQuery(String index, String field, String value, Integer from, Integer size) {
        ResultData resultData = fuzzyQueryService.fuzzyQuery(index, field, value, from, size);
        return resultData;
    }
}
