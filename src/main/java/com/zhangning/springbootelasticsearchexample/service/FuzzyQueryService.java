package com.zhangning.springbootelasticsearchexample.service;

/**
 * @author zhangning
 * @date 2020/4/1
 */

import com.alibaba.fastjson.JSON;
import com.zhangning.springbootelasticsearchexample.dto.ResultData;
import com.zhangning.springbootelasticsearchexample.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;

@Slf4j
@Service
public class FuzzyQueryService {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    /**
     * 模糊查询所有以 “三” 结尾的姓名
     */
    public ResultData fuzzyQuery(String index,String field, String value, Integer from, Integer size) {
        ResultData rd = new ResultData();
        if (from == null) {
            from = 0;
        }
        if (size == null) {
            size = 10;
        }
        try {
            // 构建查询条件
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
            searchSourceBuilder.query(QueryBuilders.fuzzyQuery(field, value).fuzziness(Fuzziness.AUTO));
            searchSourceBuilder.from(from);
            searchSourceBuilder.size(size);
            // 创建查询请求对象，将查询对象配置到其中
            String indexYong  = index==null ? "mydlq-user":index;
            SearchRequest searchRequest = new SearchRequest(indexYong);
            searchRequest.source(searchSourceBuilder);
            // 执行查询，然后处理响应结果
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            // 根据状态和数据条数验证是否返回了数据
            ArrayList<UserInfo> al = new ArrayList<UserInfo>();
            int resLength = searchResponse.getHits().getHits().length;
            if (RestStatus.OK.equals(searchResponse.status()) && resLength> 0) {
                SearchHits hits = searchResponse.getHits();
                for (SearchHit hit : hits) {
                    // 将 JSON 转换成对象
                    UserInfo userInfo = JSON.parseObject(hit.getSourceAsString(), UserInfo.class);
                    al.add(userInfo);
                    // 输出查询信息
                    log.info(userInfo.toString());
                }
            }
            rd.setData(al);
            rd.setDataSize(resLength);
            return rd;
        } catch (IOException e) {
            log.error("", e);
        }
        return  null;
    }

}