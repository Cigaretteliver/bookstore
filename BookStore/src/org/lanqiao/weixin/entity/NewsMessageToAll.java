package org.lanqiao.weixin.entity;

import java.util.ArrayList;
import java.util.List;

public class NewsMessageToAll {

	private List<ArticleToAll> articles = new ArrayList<ArticleToAll>();

	public List<ArticleToAll> getArticles() {
		return articles;
	}

	public void setArticles(List<ArticleToAll> articles) {
		this.articles = articles;
	}

	public NewsMessageToAll(List<ArticleToAll> articles) {
		super();
		this.articles = articles;
	}

	public NewsMessageToAll() {
		super();
	}

}
