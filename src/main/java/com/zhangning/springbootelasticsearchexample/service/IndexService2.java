package com.zhangning.springbootelasticsearchexample.service;

import com.alibaba.fastjson.JSON;
import com.zhangning.springbootelasticsearchexample.entity.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

/**
 * @author zhangning
 * @date 2020/3/31
 */

@Slf4j
@Service
public class IndexService2 {
    @Autowired
    private RestHighLevelClient restHighLevelClient;

    public void createIndex() {

        // 创建 Mapping
        try {
            XContentBuilder mapping = XContentFactory.jsonBuilder()
                    .startObject()
                    .field("dynamic", true)
                    .startObject("properties")
                    .startObject("name")
                    .field("type", "text")
                    .startObject("fields")
                    .startObject("keyword")
                    .field("type", "keyword")
                    .endObject()
                    .endObject()
                    .endObject()
                    .startObject("address")
                    .field("type", "text")
                    .startObject("fields")
                    .startObject("keyword")
                    .field("type", "keyword")
                    .endObject()
                    .endObject()
                    .endObject()
                    .startObject("remark")
                    .field("type", "text")
                    .startObject("fields")
                    .startObject("keyword")
                    .field("type", "keyword")
                    .endObject()
                    .endObject()
                    .endObject()
                    .startObject("age")
                    .field("type", "integer")
                    .endObject()
                    .startObject("salary")
                    .field("type", "float")
                    .endObject()
                    .startObject("birthDate")
                    .field("type", "date")
                    .field("format", "yyyy-MM-dd")
                    .endObject()
                    .startObject("createTime")
                    .field("type", "date")
                    .endObject()
                    .endObject()
                    .endObject();
            // 创建索引配置信息，配置
            Settings settings =  Settings.builder()
                    .put("index.number_of_shards", 1)
                    .put("index.number_of_replicas", 0)
                    .build();
            // 新建创建索引请求对象，然后设置索引类型（ES 7.0 将不存在索引类型）和 mapping 与 index 配置
            CreateIndexRequest request = new CreateIndexRequest("mydlq-user", settings);
//            request.mapping("doc", mapping);
            // RestHighLevelClient 执行创建索引
            CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
            // 判断是否创建成功
            boolean isCreated = createIndexResponse.isAcknowledged();
            log.info("是否创建成功：{}", isCreated);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**
     * 删除索引
     */
    public void deleteIndex() {
        try {
            // 新建删除索引请求对象
            DeleteIndexRequest request = new DeleteIndexRequest("mydlq-user");
            // 执行删除索引
            AcknowledgedResponse acknowledgedResponse = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
            // 判断是否删除成功
            boolean siDeleted = acknowledgedResponse.isAcknowledged();
            log.info("是否删除成功：{}", siDeleted);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /**
     * 增加文档信息
     */
    public void addDocument() {
        try {
            // 创建索引请求对象
            IndexRequest indexRequest = new IndexRequest("mydlq-user");
            indexRequest.id(UUID.randomUUID().toString());
            // 创建员工信息
            UserInfo userInfo = new UserInfo();
            userInfo.setName("张海宁");
            userInfo.setAge(29);
            userInfo.setSalary(100.00f);
            userInfo.setAddress("上海");
            userInfo.setRemark("来自上海市的张先生");
            userInfo.setCreateTime(new Date());
            userInfo.setBirthDate("2019-09-09");
            // 将对象转换为 byte 数组
            byte[] json = JSON.toJSONBytes(userInfo);
            // 设置文档内容
            indexRequest.source(json, XContentType.JSON);
            // 执行增加文档
            IndexResponse response = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
            log.info("创建状态：{}", response.status());
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 获取文档信息
     */
    public void getDocument(String id) {
        try {
            // 获取请求对象
            GetRequest getRequest = new GetRequest("mydlq-user");
            getRequest.id(id);
            // 获取文档信息
            GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
            // 将 JSON 转换成对象
            if (getResponse.isExists()) {
                UserInfo userInfo = JSON.parseObject(getResponse.getSourceAsBytes(), UserInfo.class);
                log.info("员工信息：{}", userInfo);
            }
        } catch (IOException e) {
            log.error("", e);
        }
    }

    /**
     * 更新文档信息
     */
    public void updateDocument(String id) {
        try {
            // 创建索引请求对象
            UpdateRequest updateRequest = new UpdateRequest("mydlq-user",id);
            // 设置员工更新信息
            UserInfo userInfo = new UserInfo();
            userInfo.setSalary(9999.99f);
            userInfo.setAddress("xxxxxx");
            // 将对象转换为 byte 数组
            byte[] json = JSON.toJSONBytes(userInfo);
            // 设置更新文档内容
            updateRequest.doc(json, XContentType.JSON);
            // 执行更新文档
            UpdateResponse response = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
            log.info("创建状态：{}", response.status());
        } catch (Exception e) {
            log.error("", e);
        }
    }

    /**
     * 删除文档信息
     */
    public void deleteDocument(String id) {
        try {
            // 创建删除请求对象
            DeleteRequest deleteRequest = new DeleteRequest("mydlq-user",id);
            // 执行删除文档
            DeleteResponse response = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
            log.info("删除状态：{}", response.status());
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
