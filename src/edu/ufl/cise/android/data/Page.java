package edu.ufl.cise.android.data;

import java.util.ArrayList;
import java.util.List;

public class Page<T> {
	String topic;
	String link;
	int currentPage;
	int totalPage;
	final List<T> links;
	public Page(int cp, int tp, List<T> p){
		this.currentPage = cp;
		this.totalPage = tp;
		this.links = p;
	}
	
	public Page(String topic, String link){
		currentPage = 1;
		totalPage = 1;
		links = new ArrayList<T>();
		this.topic = topic;
		this.link = link;
	}
	
	public int getCurrentPage(){
		return currentPage;
	}
	
	public int getTotalPage(){
		return totalPage;
	}

	public String getTopic(){
		return topic;
	}
	
	public String getLink(){
		return link;
	}
	
	public void update(Page newPage){
		setLinks(newPage.links);
		setCurrentPage(newPage.currentPage);
		setTotalPage(newPage.totalPage);
	}
	
	
	public final List<T> getLinks(){
		return links;
	}
	
	public void setLinks(final List<T> newPosts){
		links.clear();
		links.addAll(newPosts);
	}
	
	public void setCurrentPage(int cp){
		currentPage = cp;
	}
	
	public void setTotalPage(int tp){
		totalPage = tp;
	}
	
	public void setTopic(String tp){
		topic = tp;
	}
	
	public void setLink(String lk){
		link = lk;
	}
}
