package org.lanqiao.weixin.utils;

import java.io.File;
import java.net.MalformedURLException;

import org.junit.Test;
import org.lanqiao.weixin.entity.ArticleToAll;
import org.lanqiao.weixin.entity.NewsMessageToAll;

import net.sf.json.JSONObject;

public class MessageUtil {

	// 上传图文消息的地址
	public static final String UPLOAD_NEWSMESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/media/uploadnews?access_token=ACCESS_TOKEN";
	// 发送消息
	public static final String SEND_MESSAGE_URL = "https://api.weixin.qq.com/cgi-bin/message/mass/send?access_token=ACCESS_TOKEN";
	// 上传素材
	public static final String UPLOAD = "https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

	@Test
	public void uploadPic() throws MalformedURLException {
		String at = WxUtils.getAccessToken();
		// 媒体文件类型，分别有图片（image）、语音（voice）、视频（video）和缩略图（thumb）
		String url = UPLOAD.replace("ACCESS_TOKEN", at).replace("TYPE", "image");
		File file = new File("d:/1.jpg");
		JSONObject respJson = WxUtils.httpRequestSendFile(url, file);
		System.out.println(respJson.toString());

	}

	@Test
	public void sendNewsMessageToAll() throws MalformedURLException {
		// 创建图文消息实体
		NewsMessageToAll n = new NewsMessageToAll();
		ArticleToAll a1 = new ArticleToAll();
		a1.setAuthor("作者1");
		a1.setContent("内容1");
		a1.setContent_source_url("http://www.baidu.com");
		a1.setDigest("图文消息的描述1");
		a1.setShow_cover_pic(1);
		a1.setThumb_media_id("jsMQvgOs6-1oXdT8f84DyfPqzpzpV0ipjqlu8AEYlwwzj_E7ZIcfY1bewpY8MWyB");
		a1.setTitle("标题1");
		ArticleToAll a2 = new ArticleToAll();
		a2.setAuthor("作者2");
		a2.setContent("内容2");
		a2.setContent_source_url("http://www.baidu.com");
		a2.setDigest("图文消息的描述2");
		a2.setShow_cover_pic(1);
		a2.setThumb_media_id("jsMQvgOs6-1oXdT8f84DyfPqzpzpV0ipjqlu8AEYlwwzj_E7ZIcfY1bewpY8MWyB");
		a2.setTitle("标题2");
		n.getArticles().add(a1);
		n.getArticles().add(a2);
		// 转为Json字符
		JSONObject jsonObject = JSONObject.fromObject(n);
		String jsonStr = jsonObject.toString();
		// 获取Access Token，
		String at = WxUtils.getAccessToken();
		System.out.println("AccessToken:" + at);
		// 替换url
		String url = UPLOAD_NEWSMESSAGE_URL.replace("ACCESS_TOKEN", at);
		// 上传
		JSONObject jsonResult = WxUtils.httpRequest(url, jsonStr);
		System.out.println(jsonResult);
		// 获取id
		String id = jsonResult.getString("media_id");
		if (id != null) {
			System.out.println("id:" + id);
			// 再通过id发送消息
			// 替换url
			String send_url = SEND_MESSAGE_URL.replace("ACCESS_TOKEN", at);
			// 准备要发送的json
			String data = "{" + "\"touser\":[" + "\"oOI9Lw8GIP_7u71L1jr0_OAjJChE\"" + ","
					+ "\"oOI9Lw3SyyjoeE1bvhfA1kZnoS14\"" + "," + "\"oOI9LwxGppd7ljAYEDLhLfhkhYc8\"" + "],"
					+ "\"mpnews\":{" + "\"media_id\":\"" + id + "\"" + "}," + "\"msgtype\":\"mpnews\","
					+ "\"send_ignore_reprint\":1" + "}";
			System.out.println("data" + data);
			// 发送
			JSONObject send_Result = WxUtils.httpRequest(send_url, data);
			System.out.println(send_Result.toString());
			int code = send_Result.getInt("errcode");
			if (code == 0) {
				System.out.println("发送成功。");
			} else {
				System.out.println("发送失败。");
			}
		} else {
			System.out.println("未获取到id,上传失败。");
		}
	}

	@Test
	public void sendTextToAll() throws MalformedURLException {
		// 获取Access Token，
		String at = WxUtils.getAccessToken();
		// 替换url
		String send_url = SEND_MESSAGE_URL.replace("ACCESS_TOKEN", at);
		// 准备要发送的json
		String data = "{"+
						   "\"touser\":["+
						    "\"oOI9Lw3SyyjoeE1bvhfA1kZnoS14\","+
						    "\"oOI9LwxGppd7ljAYEDLhLfhkhYc8\","+
						    "\"oOI9Lw8GIP_7u71L1jr0_OAjJChE\""+
						   "],"+
						    "\"msgtype\": \"text\","+
						    "\"text\": { \"content\": \"测试群发文本消息。\"}"+
						"}";
		System.out.println("data" + data);
		// 发送
		JSONObject send_Result = WxUtils.httpRequest(send_url, data);
		System.out.println(send_Result.toString());
		int code = send_Result.getInt("errcode");
		if (code == 0) {
			System.out.println("发送成功。");
		} else {
			System.out.println("发送失败。");
		}
	}

}
