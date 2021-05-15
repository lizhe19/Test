package com.test.es;

import org.apache.http.HttpHost;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.io.IOException;

/**
 * es客户端  有三种：transport Client    rest low level client           rest high level client
 *
 * @author lz-119612
 * @version 1.0
 * @date 2021/1/13 11:20
 *  
 **/
public class ESClientTest {

    public static void main(String[] args) throws IOException {
        //Low Level Client init
        RestClient lowLevelRestClient = RestClient.builder(
                new HttpHost("localhost", 9200, "http")).build();
        //High Level Client init
        RestHighLevelClient client = new RestHighLevelClient(lowLevelRestClient);

        /*****************************************************************************************************************/
        //索引
        String jsonString = "{" +
                "\"user\":\"kimchy\"," +
                "\"postDate\":\"2013-01-30\"," +
                "\"message\":\"trying out Elasticsearch\"" +
                "}";
        IndexRequest indexRequest = new IndexRequest().index("index").type("type");
        indexRequest.source(jsonString, XContentType.JSON);
        IndexResponse indexResponse = client.index(indexRequest);

        /***************************************************************************************************************/
        //get update delete
        GetRequest getRequest = new GetRequest().index("index").type("type");
        GetResponse getResponse = client.get(getRequest);

        /***************************************************************************************************************/
        //按条件查询
        SearchRequest searchRequest = new SearchRequest().indices("index").types("type");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //不分词
        searchSourceBuilder.query(QueryBuilders.termQuery("user", "kimchy"));
        //分词后查询
        searchSourceBuilder.query(QueryBuilders.matchQuery("content", "test u have"));
        //按照score倒序排列
        searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));
        //并且按照uid正序排列
        searchSourceBuilder.sort(new FieldSortBuilder("_uid").order(SortOrder.ASC));

        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest);
    }

}
