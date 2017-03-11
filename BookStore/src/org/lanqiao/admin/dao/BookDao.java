package org.lanqiao.admin.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.lanqiao.admin.entity.Book;
import org.lanqiao.admin.util.MyUtils;
import org.lanqiao.base.bean.BookCretiera;
import org.lanqiao.base.bean.Page;
import org.lanqiao.base.dao.BaseDao;
import org.lanqiao.base.dao.Dao;

public class BookDao extends BaseDao<Book> implements Dao<Book> {

	/**
	 * 使用ID获取一本书
	 */
	@Override
	public Book getT(int id) {
		Connection conn = MyUtils.conns.get();
		String sql = "select id,name,price,publishcorp,stock,sold,isonsale,details,surface,type,onsaletime from books where id=?";
		try {
			return this.get(sql, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 获取所有的书
	 */
	public List<Book> getAll(Object... args) {
		try {
			Connection conn = MyUtils.conns.get();
			List<Book> books = new ArrayList<Book>();
			String sql = "select * from books";
				return this.getAll(sql, args);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public int update(Book t) {
		Connection conn = MyUtils.conns.get();
		int i = 0;
		String sql = "update books set name=?,price=?," + "publishcorp=?, stock=? , sold=?,"
				+ "isonsale=?,details=?,surface=?," + "type=?,onsaletime=? where id=?";
		try {
			i = MyUtils.qr.update(conn, sql, t.getName(), t.getPrice(), t.getPublishCorp(), t.getStock(), t.getSold(),
					t.getIsOnSale(), t.getDetails(), t.getSurface(), t.getType(),
					new java.sql.Date(t.getOnSaleTime().getTime()), t.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public int delete(Book t) {
		return delete(t.getId());
	}

	/**
	 * 删除一本书
	 */
	@Override
	public int delete(int id) {
		Connection conn = MyUtils.conns.get();
		int i = 0;
		String sql = "delete from books where id=?";
		try {
			i = MyUtils.qr.update(conn, sql, id);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * 增加一本书
	 */
	@Override
	public int add(Book t) {
		Connection conn = MyUtils.conns.get();
		int key = 0;
		// 先通过序列获取id
		String sql = "select book_seq.nextval from dual";
		String sql2 = "insert into books values (?,?,?,?,?,?,?,?,?,?,?)";
		try {
			ScalarHandler<BigDecimal> rsh = new ScalarHandler<BigDecimal>();
			BigDecimal l = MyUtils.qr.query(conn, sql, rsh);
			// 主键
			key = l.intValue();
			t.setId(key);
			// 插入一条数据
			int result = MyUtils.qr.update(conn, sql2, t.getId(), t.getName(), t.getPrice(), t.getPublishCorp(),
					t.getStock(), t.getSold(), t.getIsOnSale(), t.getDetails(), t.getSurface(), t.getType(),
					new java.sql.Date(t.getOnSaleTime().getTime()));
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public Page<Book> getPage(BookCretiera bc) {
		Page<Book> page = null;
		String sql;
		try {
			String key = bc.getKeyword();
			int orderbyInt = bc.getOrderBy();
			String orderby = "id asc";
			switch (orderbyInt) {
			case 1:
				orderby = "price asc";
				break;
			case 2:
				orderby = "price desc";
				break;
			case 3:
				orderby = "sold asc";
				break;
			case 4:
				orderby = "sold desc";
				break;
			default:
				orderby = "id desc";
			}
			sql = "select id,name,price,publishcorp,stock,sold,isonsale,details,surface,type,onsaletime" + " from ("
					+ " select rownum r,b.*" + " from (" +
					// 以下是查询的Sql
					" select id,name,price,publishcorp,stock,sold,isonsale,details,surface,type,onsaletime"
					+ " from books" + " where price between ? and ?" + " and isonsale = 1"
					+ " and name||publishcorp||details||type like '%" + key + "%'" + " order by " + orderby +
					// 以上是查询的Sql
					" ) b" + " where rownum <?)" + " where r>=?";
			// 查询当前条件下有多少条数据
			String countSql = " select count(id)" + " from books" + " where price between ? and ?"
					+ " and name||publishcorp||details||type like '%" + key + "%'";
			double min = bc.getMinPrice();
			double max = bc.getMaxPrice();
			int pageNo = bc.getPageNo();
			int pageSize = bc.getPageSize();
			int from = (pageNo - 1) * pageSize + 1;
			int end = pageNo * pageSize + 1;

			Object e = this.getE(countSql, min, max);
			int count = ((BigDecimal) e).intValue();
			bc.setCount(count);
			// 获取当前页的所有的书的集合。
			List<Book> books = this.getAll(sql, min, max, end, from);
			// 封装一个page对象
			Page<Book> bookPage = new Page(books, bc);
			return bookPage;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return page;
	}

}
