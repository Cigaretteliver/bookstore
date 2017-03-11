package org.lanqiao.admin.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@SuppressWarnings("rawtypes")
public class MyUtils {
	// 配置信息
	public static final Properties config = new Properties();

	// 数据源
	public static final ComboPooledDataSource ds = new ComboPooledDataSource();

	// Dbutils工具入口
	public static final QueryRunner qr = new QueryRunner();
	
	public static final ThreadLocal<Connection> conns = new ThreadLocal<Connection>();

	/**
	 * 获取父类的泛型类型
	 * 
	 * @param c
	 * @return
	 */
	public static Class getGenericClass(Class c) {
		ParameterizedType pt = (ParameterizedType) c.getGenericSuperclass();
		Type t = (Type) pt.getActualTypeArguments()[0];
		Class clazz = (Class) t;
		return clazz;
	}

	public static String sha1(String pwd) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("sha1");
			byte[] digest = md5.digest(pwd.getBytes());
			String str = byteToHexStr(digest);
			return str;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String md5(String pwd) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("md5");
			byte[] digest = md5.digest(pwd.getBytes());
			String str = byteToHexStr(digest);
			return str;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String byteToHexStr(byte[] digest) {
		char[] c = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		StringBuffer sb = new StringBuffer();
		for(byte b:digest){
			sb.append(c[(b>>4)&15]);
			sb.append(c[b&15]);
		}
		return sb.toString();
	}

	public static String dateToStr(Date time) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = sdf.format(time);
		return strDate;
	}
	
	//获取随机数
	public static int getRandom(){
		return (int)(Math.random()*1000000);
	}
	
}
