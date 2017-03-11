package org.lanqiao.weixin.utils;

import java.net.MalformedURLException;

import org.junit.Test;

import net.sf.json.JSONObject;

public class KefuManager {

	public static final String ADD_URL="https://api.weixin.qq.com/customservice/kfaccount/add?access_token=ACCESS_TOKEN";
	
	@Test
	public void add() throws MalformedURLException{
		String at = WxUtils.getAccessToken();
		String url = ADD_URL.replace("ACCESS_TOKEN", at);
		String data = " {"+
						    "\"kf_account\" : \"Richard-low@gh_48aa5a3c024d\","+
						    "\"nickname\" : \"大老板\","+
						    "\"password\" : \""+"7AC655CEC4C155409FBB5E8B265E88CC".toLowerCase()+"\""+
						 "}";
		System.out.println(data);
		JSONObject addResult = WxUtils.httpRequest(url, data);
		System.out.println(addResult.toString());
	}
	
}
