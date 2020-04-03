package com.zhangning.springbootelasticsearchexample.service;


import com.alibaba.fastjson.JSON;
import com.zhangning.springbootelasticsearchexample.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;

/**
 * @author zhangning
 * @date 2020/4/1
 */
@Slf4j
@Service
public class RangeQueryService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 查询岁数 ≥ 30 岁的员工数据
     */
    public void rangeQuery() {
        try {
            // 构建查询条件
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.rangeQuery("age").gte(30));
            // 创建查询请求对象，将查询对象配置到其中
            SearchRequest searchRequest = new SearchRequest("mydlq-user");
            searchRequest.source(searchSourceBuilder);
            // 执行查询，然后处理响应结果
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            // 根据状态和数据条数验证是否返回了数据
            int resLength = searchResponse.getHits().getHits().length;
            System.out.println("查询出的结果："+resLength+"个！");
            if (RestStatus.OK.equals(searchResponse.status()) && resLength> 0) {
                SearchHits hits = searchResponse.getHits();
                for (SearchHit hit : hits) {
                    // 将 JSON 转换成对象
                    UserInfo userInfo = JSON.parseObject(hit.getSourceAsString(), UserInfo.class);
                    // 输出查询信息
                    log.info(userInfo.toString());
                }
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /**
     * 查询距离现在 30 年间的员工数据
     * [年(y)、月(M)、星期(w)、天(d)、小时(h)、分钟(m)、秒(s)]
     * 例如：
     * now-1h 查询一小时内范围
     * now-1d 查询一天内时间范围
     * now-1y 查询最近一年内的时间范围
     */
    public void dateRangeQuery() {
        try {
            // 构建查询条件
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            // includeLower（是否包含下边界）、includeUpper（是否包含上边界）
            searchSourceBuilder.query(QueryBuilders.rangeQuery("birthDate")
                    .gte("now-30y").includeLower(true).includeUpper(true));
            // 创建查询请求对象，将查询对象配置到其中
            SearchRequest searchRequest = new SearchRequest("mydlq-user");
            searchRequest.source(searchSourceBuilder);
            // 执行查询，然后处理响应结果
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            // 根据状态和数据条数验证是否返回了数据
            int resLength = searchResponse.getHits().getHits().length;
            if (RestStatus.OK.equals(searchResponse.status()) && resLength> 0) {
                SearchHits hits = searchResponse.getHits();
                for (SearchHit hit : hits) {
                    // 将 JSON 转换成对象
                    UserInfo userInfo = JSON.parseObject(hit.getSourceAsString(), UserInfo.class);
                    // 输出查询信息
                    log.info(userInfo.toString());
                }
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }

}