package org.lanqiao.admin.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.lanqiao.admin.entity.Reply;
import org.lanqiao.admin.util.MyUtils;
import org.lanqiao.base.dao.BaseDao;
import org.lanqiao.base.dao.Dao;

public class ReplyDao extends BaseDao<Reply> implements Dao<Reply> {

	/**
	 * 获取一个回复对象，这个对象不带管理员属性
	 */
	@Override
	public Reply getT(int id) {
		Connection conn = MyUtils.conns.get();

		try {
			String sql = "select id,time,content from replys where id=?";
			Reply reply = this.get(sql, id);
			return reply;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<Reply> getAll(Object... args) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Reply t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(Reply t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int add(Reply t) {
		Connection conn = MyUtils.conns.get();
		int id = this.getId();
		t.setId(id);
		try {
			String sql = "insert into replys values (?,?,?,?,?)";
			int i = MyUtils.qr.update(conn, sql,t.getId(),new java.sql.Date(t.getTime().getTime()),t.getContent(),t.getAdmin().getId(),t.getConsult().getId());
			return i;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public int getId() {
		Connection conn = MyUtils.conns.get();
		BigDecimal b;
		try {
			String sql = "select reply_seq.nextval from dual";
			Object e = this.getE(sql);
			b = (BigDecimal) e;
			return b.intValue();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}


}
