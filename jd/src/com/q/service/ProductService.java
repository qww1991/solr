package com.q.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;

import com.q.pojo.ProductModel;
import com.q.pojo.QueryVo;
import com.q.pojo.ResultModel;

@Service
public class ProductService {
	@Resource
	private SolrServer solrServer;
	public static final Integer page_size = 30;
	
	public ResultModel findProductList(QueryVo vo) throws Exception {
		String queryString = vo.getQueryString();
		String catalog_name = vo.getCatalog_name();
		String price = vo.getPrice();
		Integer sort = vo.getSort();
		Integer page = vo.getPage();
		
		SolrQuery query = new SolrQuery();
		query.set("df", "product_keywords");
		if (queryString != null && !queryString.equals("")) {
			query.setQuery(queryString);
		}
		if (catalog_name != null && !catalog_name.equals("")) {
			query.addFilterQuery("product_catalog_name:" + catalog_name);
		}
		if (price != null && !price.equals("")) {
			String[] split = price.split("-");
			query.addFilterQuery("product_price:[" + split[0] + " TO " + split[1] + "]");
		}
		if (sort != null) {
			query.setSort("product_price", (sort == 1)?ORDER.asc:ORDER.desc);
		}
		if (page == null) {
			page = 1;
		}
		query.setStart((page - 1) * page_size);
		query.setRows(page_size);
		
		query.setHighlight(true);
		query.addHighlightField("product_name");
		query.setHighlightSimplePre("<font color=\"green\">");
		query.setHighlightSimplePost("</font>");
		
		QueryResponse response = solrServer.query(query);
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		SolrDocumentList results = response.getResults();
		System.out.println( results.getNumFound());
		
		ResultModel resultModel = new ResultModel();
		List<ProductModel> productModelList = new ArrayList<>();
		for (SolrDocument doc : results) {
			ProductModel productModel = new ProductModel();
			
			String pid = String.valueOf(doc.get("id"));
			productModel.setPid(pid);
			
			String product_catalog_name = String.valueOf(doc.get("product_catalog_name"));
			productModel.setCatalog_name(product_catalog_name);
			
			String product_picture = String.valueOf(doc.get("product_picture"));
			productModel.setPicture(product_picture);
			
			Float product_price = Float.valueOf(String.valueOf(doc.get("product_price")));
			productModel.setPrice(product_price);
			
			List<String> list = highlighting.get(pid).get("product_name");
			if (list != null && list.size() > 0) {
				String product_name = list.get(0);
				productModel.setName(product_name);
			} else {
				String product_name = String.valueOf(doc.get("product_name"));
				productModel.setName(product_name);
			}
			productModelList.add(productModel);
		}
		resultModel.setProductList(productModelList);
		long numFound = results.getNumFound();
		long pageCount = (numFound % page_size == 0) ? numFound / page_size : (numFound / page_size + 1);
		resultModel.setCurPage(page);
		resultModel.setRecordCount(numFound);
		resultModel.setPageCount(pageCount);
		return resultModel;
	}

}
