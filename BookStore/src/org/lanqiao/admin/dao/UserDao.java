package org.lanqiao.admin.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.lanqiao.admin.entity.User;
import org.lanqiao.admin.util.MyUtils;
import org.lanqiao.base.dao.BaseDao;
import org.lanqiao.base.dao.Dao;

public class UserDao extends BaseDao<User> implements Dao<User>{

	/**
	 * 获取用户，不带购物车
	 */
	@Override
	public User getT(int id) {
		try {
			String sql="select id,name,password,balance,profile,gender,age,email from users where id=?";
			User user = this.get(sql, id);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int update(User t) {
		Connection conn = MyUtils.conns.get();
		int i;
		try {
			String sql = "update users set name=?,password=?,balance=?,"
					+ "profile=?,gender=?,age=?,cart_id=?,email=? where id = ?";
			i = MyUtils.qr.update(conn, sql,t.getName(),t.getPassword(),t.getBalance(),t.getProfile(),
					t.getGender(),t.getAge(),t.getCart().getId(),t.getEmail(),t.getId());
			return i;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public int add(User t) {
		Connection conn = MyUtils.conns.get();
		try {
			//获取id
			String getId = "select user_seq.nextval from dual";
			Object e = this.getE(getId);
			int id = ((BigDecimal)e).intValue();
			t.setId(id);
			//插入记录
			String sql = "insert into users(id,name,password,balance,profile,gender,age,email,cart_id) values (?,?,?,?,?,?,?,?,null)";
			int result = MyUtils.qr.update(conn, sql,t.getId(),t.getName(),t.getPassword(),t.getBalance(),t.getProfile(),t.getGender(),t.getAge(),t.getEmail());
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public User getT(String name, String pwd) {
		Connection conn = MyUtils.conns.get();
		try {
			String sql = "select id,name,password,balance,profile,gender,age,email from users where name=? and password = ?";
			BeanHandler<User> rsh = new BeanHandler<User>(User.class);
			User u = MyUtils.qr.query(conn, sql, rsh, name,pwd);
			return u;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 去数据库查看这个用户名是否存在
	 * @param conn
	 * @param name
	 * @return
	 */
	public String getName(String name) {
		try {
			String sql = "select name from users where name=?";
			Object e = this.getE(sql, name);
			String n = (String)e;
			return n;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<User> getAll(Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(User t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	public User getByOrderId(int orderId) {
		User user;
		try {
			String sql = "select id,name,password,balance,profile,gender,age,email from users where id = (select user_id from orders where id=?)";
			user = this.get(sql, orderId);
			return user;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public String getOpenId(String openid) {
		String sql = "select openid from useropenid where openid = ?";
		Object e;
		try {
			e = this.getE(sql, openid);
			if(e==null){
				return null;
			}
			return (String)e;
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new RuntimeException(e1);
		}
	}

	public int bind(int id, String openid) {
		try {
			String sql = "insert into useropenid values (?,?)";
			Connection conn = MyUtils.conns.get();
			int i = MyUtils.qr.update(conn, sql, id,openid);
			return i;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public Integer getBindId(int userId) {
		try {
			String sql = "select user_id from useropenid where user_id=?";
			Object e = this.getE(sql, userId);
			if(e==null){
				return null;
			}else{
				BigDecimal b = (BigDecimal) e;
				return b.intValue();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public User getByOpenId(String openId) {
		try {
			String sql ="select user_id from useropenid where openid = ?";
			Object e = this.getE(sql, openId);
			if(e!=null){
				int id = ((BigDecimal)e).intValue();
				User user = this.getT(id);
				return user;
			}else{
				return null;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public int unbind(String fromUserName) {
		int i;
		try {
			Connection conn = MyUtils.conns.get();
			String sql = "delete from useropenid where openid = ?";
			i = MyUtils.qr.update(conn, sql, fromUserName);
			return i;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
