package edu.ufl.cise.android.util;

import java.util.ArrayList;
import java.util.List;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import edu.ufl.cise.android.data.Board;
import edu.ufl.cise.android.data.Link;
import edu.ufl.cise.android.data.Page;
import edu.ufl.cise.android.data.Post;
import edu.ufl.cise.android.data.Topic;
import edu.ufl.cise.android.data.TopicList;
import edu.ufl.cise.android.dataModel.GuidanceDataModel;
import edu.ufl.cise.android.dataModel.PostDataModel;
import edu.ufl.cise.android.dataModel.TopicListDataModel;

import android.util.Log;

public class WebCrawler {
	private static final String encoding = "GB2312";
	private static WebCrawler crawler;

	private WebCrawler() {

	}

	public static WebCrawler getInstance() {
		if (crawler == null)
			crawler = new WebCrawler();
		return crawler;
	}

	private String subPage(String url, int pageNum) {
		String pre = url.substring(0, url.length() - ".html".length());
		String sur = url.substring(url.length() - ".html".length());
		return pre + "_0_" + pageNum + sur;
	}

	public Page parsePage(PostDataModel model) throws ParserException {

		Page page = new Page(model.getPage().getTopic(), model.getPage()
				.getLink());

		page.setCurrentPage(model.getPage().getCurrentPage());

		String url = subPage(page.getLink(), page.getCurrentPage());
		Parser htmlParser = new Parser(url);
		htmlParser.setEncoding(encoding);

		NodeList divOfTab1 = htmlParser
				.extractAllNodesThatMatch(new TagNameFilter("u"));
		if (divOfTab1 == null || divOfTab1.size() == 0) {
			page.setTotalPage(1);
		} else {
			page.setTotalPage(Integer.valueOf(divOfTab1
					.elementAt(divOfTab1.size() - 1).getNextSibling().getText()));
		}
		if (page.getCurrentPage() > page.getTotalPage())
			return null;

		htmlParser = new Parser(url);
		htmlParser.setEncoding(encoding);
		divOfTab1 = htmlParser.extractAllNodesThatMatch(new TagNameFilter("p"));
		if (divOfTab1 != null && divOfTab1.size() > 1) {
			for (int i = 0; i < divOfTab1.size() - 1; i++) {
				Node n = divOfTab1.elementAt(i).getFirstChild();
				String topic = "";
				String contents = "";
				int j = 0;
				while (n != null) {
					if (j < 3) {
						topic = topic + n.getText().replaceAll("&nbsp;", "");
						if (j < 2)
							topic += "\n";
					} else {
						contents = contents
								+ n.getText().replaceAll("&nbsp;", "") + "\n";
					}
					j++;
					n = n.getNextSibling();
					if (n != null)
						n = n.getNextSibling();
				}
				Post post = new Post(topic, contents);
				page.getLinks().add(post);
			}
		}
		Log.i("parsePage", page.getLinks().size() + "");
		return page;
	}
	
	public TopicList<Link> parseBBSSec(TopicListDataModel model){
		TopicList<Link> newLinks = new TopicList<Link>(model.getList()
				.getCurrentPage());
		
		return newLinks;
	}

	public TopicList<Link> parseTopicList(TopicListDataModel model)
			throws ParserException {

		TopicList<Link> newLinks = new TopicList<Link>(model.getList()
				.getCurrentPage());

		String url = newLinks.getCurrentPage();
		Parser htmlParser = new Parser(url);
		htmlParser.setEncoding(encoding);

		NodeList body = htmlParser.extractAllNodesThatMatch(new TagNameFilter("body"));

		NodeList forms = body.extractAllNodesThatMatch(new AndFilter(new TagNameFilter("form"),
				new HasAttributeFilter("name", "form1")), true);
		
        if(forms != null && forms.size() > 0) { 
    		Node n = forms.elementAt(0);
    		System.out.println(n.toHtml());
    		
    		NodeList nl = n.getChildren().extractAllNodesThatMatch(new TagNameFilter("a"),  true);
			for(int j=0; j<nl.size(); j++){
				String curText = ((LinkTag)nl.elementAt(j)).getLinkText();
				String curLink = ((LinkTag)nl.elementAt(j)).getLink();
				if(curText.contains("首页")){
					newLinks.setFirstPage(curLink);
				}else if(curText.contains("上页")){
					newLinks.setPreviousPage(curLink);
				}else if(curText.contains("下页")){
					newLinks.setNextPage(curLink);
				}else if(curText.contains("末页")){
					newLinks.setLastPage(curLink);
				}
				//link = ((LinkTag)nl.elementAt(j)).getLink();				
			}
        } 

        NodeList td = body.extractAllNodesThatMatch(new AndFilter(new TagNameFilter("td"),
				new HasAttributeFilter("class", "taolun_leftright")), true);
        
        if(td != null && td.size() > 0){
    		NodeList table = td.extractAllNodesThatMatch(new TagNameFilter("table"), true);
    		if(table != null && table.size() > 0){
	    		NodeList nla = table.extractAllNodesThatMatch(new AndFilter(new TagNameFilter("a"),
	    				new HasAttributeFilter("class", "news1")), true);
				for(int j=0; j<nla.size(); j++){
					String curText = ((LinkTag)nla.elementAt(j)).getLinkText().trim();
					String curLink = ((LinkTag)nla.elementAt(j)).getLink();
					newLinks.getLinks().add(new Link(curText, curLink));
					
				}
    		}
        }
        
		return newLinks;
	}
	
	
	
	public List<GuidanceDataModel.Guidances> parseGuidance(
			GuidanceDataModel model) throws ParserException {
		ArrayList<GuidanceDataModel.Guidances> guidances = new ArrayList<GuidanceDataModel.Guidances>();
		for (int i = 0; i < GuidanceDataModel.ENUM_END; i++) {
			guidances.add(new GuidanceDataModel.Guidances(model.getGuidances()
					.get(i).getName()));
		}

		Parser htmlParser = new Parser(DW_HOME_PAGE_URL);
		htmlParser.setEncoding(encoding);
		String title = "";
		String link = "";
		NodeList all = htmlParser.extractAllNodesThatMatch(new TagNameFilter("body"));
		NodeList tables = all.extractAllNodesThatMatch(new AndFilter(new TagNameFilter("div"), 
						new HasAttributeFilter("class", "index_other_news")), true);

        if(tables != null && tables.size() > 0) { 
        	for(int i=0; i<tables.size(); i++){
        		Node n = tables.elementAt(i);
    			NodeList nl = n.getChildren().extractAllNodesThatMatch(new AndFilter(new TagNameFilter("a"),
    					new HasAttributeFilter("class", "a2")),  true);
    			for(int j=0; j<nl.size(); j++){
						title = ((LinkTag) nl.elementAt(j)).getLinkText()
								.replaceAll("&amp;", "")
								.replaceAll("&nbsp;", "");
						link = ((LinkTag) nl.elementAt(j)).getLink();
						System.out.println(title);
						guidances.get(GuidanceDataModel.ENUM_TOPARTICLES)
								.getContents().add(new Link(title, link));
					}

			}
		}
        
        
		tables = all.extractAllNodesThatMatch(new AndFilter(new TagNameFilter("div"), 
				new HasAttributeFilter("class", "index_hotspace")), true);
		
		for(int i=0; i<tables.size(); i++){
			Node n = tables.elementAt(i);
			if(n.toHtml().contains("当前热门版面")){
				NodeList nl = n.getChildren().extractAllNodesThatMatch(new AndFilter(new TagNameFilter("div"),
    					new HasAttributeFilter("class", "index_hotspace_table")),  true);
				NodeList li = nl.extractAllNodesThatMatch(new TagNameFilter("a"),true);
				for(int j=0; j<li.size(); j++){
					title = ((LinkTag) li.elementAt(j)).getLinkText()
							.replaceAll("&amp;", "")
							.replaceAll("&nbsp;", "");
					link = ((LinkTag) li.elementAt(j)).getLink();
					guidances.get(GuidanceDataModel.ENUM_HOTBOARDS)
							.getContents().add(new Link(title, link));

    			}
			}else if(n.toHtml().contains("当前热门俱乐部")){
				NodeList nl = n.getChildren().extractAllNodesThatMatch(new AndFilter(new TagNameFilter("div"),
    					new HasAttributeFilter("class", "index_hotspace_table")),  true);
				NodeList li = nl.extractAllNodesThatMatch(new TagNameFilter("a"),true);
				for(int j=0; j<li.size(); j++){
					title = ((LinkTag) li.elementAt(j)).getLinkText()
							.replaceAll("&amp;", "")
							.replaceAll("&nbsp;", "");
					link = ((LinkTag) li.elementAt(j)).getLink();
					guidances.get(GuidanceDataModel.ENUM_HOTCLUBS)
							.getContents().add(new Link(title, link));
System.out.println(title);
    			}			
			}
		}
   
		return guidances;
	}

	final String DW_HOME_PAGE_URL = "http://www.mitbbs.com";
}
