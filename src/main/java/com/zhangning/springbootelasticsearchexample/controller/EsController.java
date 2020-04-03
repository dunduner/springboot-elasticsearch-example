package com.zhangning.springbootelasticsearchexample.controller;

import com.zhangning.springbootelasticsearchexample.service.IndexService2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author zhangning
 * @date 2020/3/31
 */

@Controller
@RequestMapping(value = "es")
public class EsController {
    @Autowired
    private IndexService2 indexService2;


    @RequestMapping(value = "create")
    @ResponseBody
    public String createIndex(){
        System.out.println("create-controller");
        indexService2.createIndex();
        return "create-success";
    }
    @RequestMapping(value = "delete")
    @ResponseBody
    public String deleteIndex(){
        indexService2.deleteIndex();
        return "delete-success";
    }

    @RequestMapping(value = "add")
    @ResponseBody
    public String addDocument(){
        indexService2.addDocument();
        return "addDocument-success";
    }

    //获取文档信息
    @RequestMapping(value = "queryById/{id}")
    @ResponseBody
    public String addDocument(@PathVariable("id") String id ){
        indexService2.getDocument(id);
        return "getDocument-success";
    }
    //更新文档信息
    @RequestMapping(value = "updateById/{id}")
    @ResponseBody
    public String updateDocument(@PathVariable("id") String id ){
        indexService2.updateDocument(id);
        return "更新-success";
    }
    //删除文档信息
    @RequestMapping(value = "/deleteById/{id}")
    @ResponseBody
    public String deleteDocument(@PathVariable("id") String id ){
        indexService2.deleteDocument(id);
        return "删除-success";
    }

}
