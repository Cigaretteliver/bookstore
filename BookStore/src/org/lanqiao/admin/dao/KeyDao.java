package org.lanqiao.admin.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.lanqiao.admin.entity.Keyword;
import org.lanqiao.admin.util.MyUtils;
import org.lanqiao.base.dao.BaseDao;
import org.lanqiao.base.dao.Dao;

public class KeyDao extends BaseDao<Keyword> implements Dao<Keyword>{

	@Override
	public Keyword getT(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Keyword> getAll(Object... args) {
		return null;
	}

	@Override
	public int update(Keyword t) {
		String sql = "update keywords set count=? where key=?";
		Connection conn = MyUtils.conns.get();
		try {
			return MyUtils.qr.update(conn, sql, t.getCount(),t.getKey());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int delete(Keyword t) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int delete(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int add(Keyword t) {
		String sql = "insert into keywords values (?,?)";
		Connection conn = MyUtils.conns.get();
		try {
			return MyUtils.qr.update(conn, sql, t.getKey(),t.getCount());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public Keyword getByKeyword(String keyword) {
		String sql = "select * from keywords where key=?";
		Connection conn = MyUtils.conns.get();
		BeanHandler<Keyword> rsh = new BeanHandler<Keyword>(Keyword.class);
		try {
			Keyword getKey = MyUtils.qr.query(conn, sql, rsh, keyword);
			return getKey;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<Keyword> getAllByKey(String key) {
		String sql="select key,count from ("+
						" select rownum,key,count from keywords where key like '"+key+"%'order by count desc)"+
					" where rownum <="+MyUtils.config.getProperty("hintCount");
		try {
			List<Keyword> keys = this.getAll(sql);
			return keys;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
