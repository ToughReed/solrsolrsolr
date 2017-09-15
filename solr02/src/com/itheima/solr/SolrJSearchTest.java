package com.itheima.solr;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

public class SolrJSearchTest {
	@Test
	public void testSolrJQuery() throws Exception{
		// 1.创建一个SolrServer对象，HttpSolrServer对象
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");
		// 2.创建SolrQuery对象
		SolrQuery solrQuery = new SolrQuery();
		// 3.需要向SolrQuery中设置查询条件
		solrQuery.set("q", "小黄人");
		solrQuery.addFilterQuery("product_price:[0 TO 12]");
		solrQuery.setSort("product_price", ORDER.asc);
		solrQuery.setRows(8);
		solrQuery.set("df", "product_keywords");
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("product_name");
		solrQuery.setHighlightSimplePre("<em>");
		solrQuery.setHighlightSimplePost("</em>");
		// 4.执行查询
		QueryResponse queryResponse = solrServer.query(solrQuery);
		// 5.去查询结果
		SolrDocumentList solrDocumentList = queryResponse.getResults();
		// 6.去查询结果总记录数
		long numFound = solrDocumentList.getNumFound();
		System.out.println("总记录数"+numFound);
		// 7.取到商品列表
		//取高亮
		Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
		
		for (SolrDocument solrDocument : solrDocumentList) {
			List<String> list = highlighting.get(solrDocument.get("id")).get("product_name");
			String name="";
			if(list!=null && list.size()>0){
				name=list.get(0);
			}else{
				name=solrDocument.get("product_name").toString();
			}
			System.out.println(solrDocument.get("id"));
			System.out.println(name);
			System.out.println(solrDocument.get("product_price"));
		}
		// 8.取高亮显示的结果
		
	}
}
