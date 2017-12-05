package com.q.pojo;

public class QueryVo {
/*
 * <div class="form">
					<input type="text" class="text" accesskey="s" name="queryString" id="key" value="${queryString }"
						autocomplete="off" onkeydown="javascript:if(event.keyCode==13) {query()}">
					<input type="button" value="搜索" class="button" onclick="query()">
				</div>
				<input type="hidden" name="catalog_name" id="catalog_name" value="${catalog_name }"/> 
				<input type="hidden" name="price" id="price" value="${price }"/> 
				<input type="hidden" name="page" id="page" value="${result.curPage }"/> 
				<input type="hidden" name="sort" id="sort" value="${sort }"/>
 * */
	private String queryString;		//搜索内容
	private String catalog_name;	//商品种类
	private String price;
	private Integer page;
	private Integer sort;
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	public String getCatalog_name() {
		return catalog_name;
	}
	public void setCatalog_name(String catalog_name) {
		this.catalog_name = catalog_name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	@Override
	public String toString() {
		return "QueryVo [queryString=" + queryString + ", catalog_name=" + catalog_name + ", price=" + price + ", page="
				+ page + ", sort=" + sort + "]";
	}
	
}
