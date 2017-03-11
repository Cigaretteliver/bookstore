package org.lanqiao.base.test;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.junit.Test;
import org.lanqiao.admin.util.MyUtils;

public class TestShop {
	
	@Test
	public void testGetRandom(){
		System.out.println(MyUtils.getRandom());
	}
	
	@Test
	public void testSendEmail() throws EmailException{
		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.163.com");
		email.setSSLOnConnect(true);
		email.setSmtpPort(465);
		//设置用户
		email.setAuthentication("cigaretteliver", "43224315yx");
		
		//设置从哪个用户发出
		email.setFrom("cigaretteliver@163.com", "蓝桥软件学院", "utf-8");
		
		//设置接收邮件的邮箱
		email.addTo("luozy@lanqiao.org");
		
		//邮件的主题
		email.setSubject("测试邮件");
		
		//邮件的内容
		email.setMsg("邮件内容");
		
		email.send();
		
		
		
	}
	
	@Test
	public void testIsEmail (){
		String reg = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";
		String email = "luoz@com";
		boolean b = email.matches(reg);
		System.out.println(b);
		
		
		
		
		
	}
	
	@Test
	public void testEmail() throws EmailException{
		SimpleEmail email = new SimpleEmail();
		email.setHostName("smtp.163.com");
		email.setSmtpPort(465);
		email.setSSLOnConnect(true);
		email.setAuthentication("cigaretteliver", "43224315yx");
		email.setFrom("cigaretteliver@163.com", "香烟", "utf-8");
		email.addTo("luozy@lanqiao.org");
		email.setSubject("试一下呢。444444444。");
		email.setMsg("呵呵。ssssss。。。。");
		String send = email.send();
		System.out.println(send);
	}
	
}
