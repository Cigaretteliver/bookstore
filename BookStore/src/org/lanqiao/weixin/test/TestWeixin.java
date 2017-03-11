package org.lanqiao.weixin.test;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;
import org.lanqiao.weixin.entity.Article;
import org.lanqiao.weixin.entity.ArticleToAll;
import org.lanqiao.weixin.entity.NewsMessage;
import org.lanqiao.weixin.entity.NewsMessageToAll;
import org.lanqiao.weixin.entity.TextMessage;
import org.lanqiao.weixin.utils.WxUtils;

import net.sf.json.JSONObject;

public class TestWeixin {
	
	@Test
	public void getAt(){
		System.out.println(WxUtils.getAccessToken());
	}
	
	@Test
	public void testJsonStr(){
		System.out.println(JSONObject.fromObject("{\"errcode\":\"文件不存在\"}").toString());
	}
	
	@Test
	public void testNewsMessageJson(){
		NewsMessageToAll n = new NewsMessageToAll();
		ArticleToAll a1 = new ArticleToAll();
		a1.setAuthor("作者");
		a1.setContent("内容");
		a1.setContent_source_url("content_source_url");
		a1.setDigest("图文消息的描述");
		a1.setShow_cover_pic(1);
		a1.setThumb_media_id("thumb_media_id");
		a1.setTitle("标题");
		ArticleToAll a2 = new ArticleToAll();
		a2.setAuthor("作者");
		a2.setContent("内容");
		a2.setContent_source_url("content_source_url");
		a2.setDigest("图文消息的描述");
		a2.setShow_cover_pic(1);
		a2.setThumb_media_id("thumb_media_id");
		a2.setTitle("标题");
		n.getArticles().add(a1);
		n.getArticles().add(a2);
		
		JSONObject jsonObject = JSONObject.fromObject(n);
		System.out.println(jsonObject.toString());
		
	}
	
	@Test
	public void testJson(){
		NewsMessage nm = new NewsMessage();
		nm.setToUserName("to");
		nm.setFromUserName("from");
		nm.setCreateTime("time");
		List<Article> articles = new ArrayList<Article>();
		Article a1 = new Article();
		a1.setDescription("description");
		a1.setPicUrl("PinUrl");
		a1.setTitle("title");
		a1.setUrl("url");
		articles.add(a1);
		Article a2 = new Article();
		a2.setDescription("description");
		a2.setPicUrl("PinUrl");
		a2.setTitle("title");
		a2.setUrl("url");
		articles.add(a2);
		nm.setArticles(articles);
		
		JSONObject fromObject = JSONObject.fromObject(nm);
		System.out.println(fromObject.toString());
	}
	
	@Test
	public void testHttpRequest(){
		String at = WxUtils.getAccessToken();
		System.out.println("access token:"+at);
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+at;
		String data= WxUtils.getMenu();
		System.out.println("menu:"+data);
		JSONObject jsonObject;
		try {
			jsonObject = WxUtils.httpRequest(url, data);
			System.out.println(jsonObject.toString());
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAccessToken() throws Exception{
		String at = WxUtils.getAccessToken();
		System.out.println(at);
		
		
		
//		String urlStr = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
//		urlStr = urlStr.replace("APPID", "wxb6777fffdf5b64a4").replace("APPSECRET", "6b55d3bb4d9c5373c8a30915d900ca13");
//		URL url = new URL(urlStr);
//		URLConnection urlConnection = url.openConnection();
//		InputStream is = urlConnection.getInputStream();
//		InputStreamReader isr = new InputStreamReader(is);
//		BufferedReader br = new BufferedReader(isr);
//		String str = null;
//		StringBuilder sb = new StringBuilder();
//		while((str=br.readLine())!=null){
//			sb.append(str);
//		}
//		System.out.println(sb.toString());
//		br.close();
//		isr.close();
//		is.close();
	}
	
	@Test
	public void testNewsXml(){
		NewsMessage nm = new NewsMessage();
		nm.setToUserName("to");
		nm.setFromUserName("from");
		nm.setCreateTime("time");
		List<Article> articles = new ArrayList<Article>();
		Article a1 = new Article();
		a1.setDescription("description");
		a1.setPicUrl("PinUrl");
		a1.setTitle("title");
		a1.setUrl("url");
		articles.add(a1);
		Article a2 = new Article();
		a2.setDescription("description");
		a2.setPicUrl("PinUrl");
		a2.setTitle("title");
		a2.setUrl("url");
		articles.add(a2);
		nm.setArticles(articles);
		
		String xml = WxUtils.convertToXml(nm);
		System.out.println(xml);
		
	}
	
	@Test
	public void testParseLt(){
		String src = "<xml>"+
					    "<ToUserName>&lt;![CDATA[oOI9Lw8GIP_7u71L1jr0_OAjJChE]]&gt;</ToUserName>"+
					    "<FromUserName>&lt;![CDATA[gh_48aa5a3c024d]]&gt;</FromUserName>"+
					    "<CreateTime>1482657342</CreateTime>"+
					    "<MsgType>&lt;![CDATA[text]]&gt;</MsgType>"+
					    "<Content>&lt;![CDATA[还没写完呢。。。。]]&gt;</Content>"+
					"</xml>";
		src=src.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
		System.out.println(src);
	}

	@Test
	public void testMsg() {
		TextMessage tm = new TextMessage("oOI9Lw8GIP_7u71L1jr0_OAjJChE", 
				"gh_48aa5a3c024d", "1482654795", "text", "不说知道说啥。");
		String xml = WxUtils.convertToXml(tm);
		System.out.println(xml);
	}

	@Test
	public void test() {
		System.out.println(System.currentTimeMillis());
	}

	@Test
	public void testDom4j() throws Exception {
		SAXReader reader = new SAXReader();
		String xml = "<xml>" + "<ToUserName><![CDATA[gh_48aa5a3c024d]]></ToUserName>"
				+ "<FromUserName><![CDATA[oOI9Lw3SyyjoeE1bvhfA1kZnoS14]]>"
				+ "</FromUserName><CreateTime>1482647592</CreateTime>" + "<MsgType><![CDATA[text]]></MsgType>"
				+ "<Content><![CDATA[哈哈]]></Content>" + "<MsgId>6367922919550295888</MsgId>" + "</xml>";
		Document doc = reader.read(new ByteArrayInputStream(xml.getBytes("UTF-8")));
		Element root = doc.getRootElement();
		List<Element> elements = root.elements();
		for (Element e : elements) {
			System.out.println(e.getName());
			System.out.println(e.getText());
		}
	}

	@Test
	public void testArraySort() {
		String signature = "4e3b1895a794cdcb36d7a2a27feaf42f307b58ac";
		String timestamp = "1482633372";
		String nonce = "1186709139";
		boolean verify = WxUtils.verify(signature, timestamp, nonce);
		System.out.println(verify);
	}

}
