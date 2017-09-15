package com.itheima.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrManagerIndex {
	
	//添加文档
	@Test
	public void addDocument() throws Exception{
		// 1.创建一个SolrServer对象，使用HttpSolrServer，参数：Solr服务的url
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");
		// 2.创建一个SolrInputDocument对象
		SolrInputDocument document = new SolrInputDocument();
		// 3.向文档对象中添加域，每个文档必须有id域，每个域的名称必须在schema.xml中定义
		document.addField("id", "1");
		document.addField("title", "测试文档1");
		document.addField("content", "aaaaaaaa");
		// 4.把文档对象写入索引库
		solrServer.add(document);
		// 5.提交
		solrServer.commit();
	}
	//删除
	@Test
	public void deleteDocument() throws Exception{
		SolrServer solrServer = new HttpSolrServer("http://localhost:8080/solr/collection1");
//		solrServer.deleteById("1");
		solrServer.deleteByQuery("*:*");
		solrServer.commit();
	}
	
	
}
