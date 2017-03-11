package org.lanqiao.admin.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.lanqiao.admin.dao.BookDao;
import org.lanqiao.admin.dao.KeyDao;
import org.lanqiao.admin.entity.Book;
import org.lanqiao.admin.entity.Keyword;
import org.lanqiao.admin.util.MyUtils;
import org.lanqiao.base.bean.BookCretiera;
import org.lanqiao.base.bean.Page;
import org.lanqiao.shop.entity.Comment;
import org.lanqiao.shop.entity.Consult;
import org.lanqiao.shop.service.CommentService;
import org.lanqiao.shop.service.ConsultService;

/**
 * 实现事物的前提是：同一个连接 方案一:纯手动实现。 问题：需要在每一个Service中获取连接 方案二：使用Filter来实现事务， 问题：连接存在哪？
 * 1.存在request中。 a,每一个Service方法都需要传入request
 * b,一个Service就是一个功能。如果连接传入Service，那么Service中的方法会很乱。 有些方法是传入连接，有些方法是获取连接。
 * 方案三：把连接存入第三方的一个容器中，每一个Dao需要的连接都去容器中获取，则一定是同一个连接。
 * 问题：在并发的情况下，可能出现连接被替换或被其它人关闭的情部。 需要明确一个问题：多个并发的请求是什么关系？并发的线程。 需要为每一个线程指定一个连接。
 * 方案四：
 *
 */
public class BookService {

	private List<Book> all;

	/*
	 * 获取一波书
	 */
	public List<Book> getAll(Object... args) {
		return new BookDao().getAll(args);
	}

	public int add(Book book) {
		return new BookDao().add(book);
	}

	/**
	 * 
	 * @param id
	 * @param getCommentsAndConsult
	 *            是否获取咨询和评论
	 * @return
	 */
	public Book getById(int id, boolean getCommentsAndConsult) {
		// 获取连接

		// 执行事务的各种方法
		BookDao bdao = new BookDao();
		Book book = bdao.getT(id);
		if (getCommentsAndConsult) {
			CommentService cs = new CommentService();
			List<Comment> comments = cs.getAll(id);
			book.setComments(comments);

			ConsultService conSer = new ConsultService();
			List<Consult> consults = conSer.getAll(id);
			book.setConsults(consults);
		}
		return book;
	}

	/**
	 * 下架
	 * 
	 * @param id
	 * @return
	 */
	public int down(int id) {
		Book book = this.getById(id, false);
		book.setIsOnSale(0);
		int i = this.update(book);
		return i;
	}

	public int update(Book book) {
		BookDao dao = new BookDao();
		int i = dao.update(book);
		return i;
	}

	public int up(int id) {
		Book book = this.getById(id, false);
		book.setIsOnSale(1);
		int i = this.update(book);
		return i;
	}

	public Page<Book> getAll(BookCretiera bc) {
		Page<Book> books = null;
		BookDao dao = new BookDao();
		books = dao.getPage(bc);
		return books;
	}

	public Page<Book> getWeiXinBook(String key) {
		BookCretiera bc = new BookCretiera();
		bc.setPageSize(Integer.parseInt(MyUtils.config.getProperty("weixinMax")));
		bc.setMinPrice(0);
		bc.setMaxPrice(999999);
		bc.setPageNo(1);
		bc.setOrderBy(4);
		bc.setKeyword(key);
		return this.getAll(bc);
	}

	//增加搜索关键字的搜索次数
	public void addKeywords(String keyword) {
		KeyDao dao = new KeyDao();
		Keyword key = dao.getByKeyword(keyword);
		if(key==null){
			Keyword newKey = new Keyword();
			newKey.setKey(keyword);
			newKey.setCount(1);
			dao.add(newKey);
		}else{
			key.setCount(key.getCount()+1);
			dao.update(key);
		}
	}

}
