package edu.ufl.cise.android.data;

import java.util.ArrayList;
import java.util.List;

public class TopicList<T> {

	String curPage = "";
	String firstPage = "";
	String prePage = "";
	String nextPage = "";
	String lastPage = "";

	ArrayList<T> links = new ArrayList<T>();
	
	public TopicList(String link){
		curPage = link;
	}

	
	public void update(TopicList<T> newLists){
		setLinks(newLists.links);
		setCurrentPage(newLists.curPage);
		setFirstPage(newLists.firstPage);
		setNextPage(newLists.nextPage);
		setPreviousPage(newLists.prePage);
		setLastPage(newLists.lastPage);
	}
	
	
	public final List<T> getLinks(){
		return links;
	}
	
	public void setLinks(final List<T> newPosts){
		links.clear();
		links.addAll(newPosts);
	}
	
	public void setCurrentPage(String page){
		curPage = page;
	}

	public String getCurrentPage(){
		return curPage;
	}
	
	public String getFirstPage(){
		return firstPage;
	}
	
	public void setFirstPage(String page){
		firstPage = page;
	}
	
	
	public String getPreviousPage(){
		return prePage;
	}
	
	public void setPreviousPage(String page){
		prePage = page;
	}
	
	
	public String getNextPage(){
		return nextPage;
	}
	
	public void setNextPage(String page){
		nextPage = page;
	}
	
	
	public String getLastPage(){
		return lastPage;
	}
	
	public void setLastPage(String page){
		lastPage = page;
	}
}
