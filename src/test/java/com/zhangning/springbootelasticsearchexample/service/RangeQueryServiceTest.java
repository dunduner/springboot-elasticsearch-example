package com.zhangning.springbootelasticsearchexample.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RangeQueryServiceTest {


    @Autowired
    private RangeQueryService rangeQueryService;
    @Autowired
    private AggrBucketMetricService aggrBucketMetricService;

    @Test
    public void rangeQuery() {
        rangeQueryService.rangeQuery();
    }

    @Test
    public void dateRangeQuery() {


        aggrBucketMetricService.aggregationTopHits();
    }
}