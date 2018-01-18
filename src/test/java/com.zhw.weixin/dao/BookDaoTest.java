package com.zhw.weixin.dao;

/**
 * Created by zhanghongwei on 17/12/29.
 */
import java.util.List;

import com.zhw.weixin.BaseTest;
import com.zhw.weixin.entity.Book;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


public class BookDaoTest extends BaseTest {

    @Autowired
    private BookDao bookDao;

    @Test
    public void testQueryById() throws Exception {
        long bookId = 1000;
        Book book = bookDao.queryById(bookId);
        System.out.println(book);
    }

    @Test
    public void testQueryAll() throws Exception {
        List<Book> books = bookDao.queryAll(0, 4);
        for (Book book : books) {
            System.out.println(book);
        }
    }

    @Test
    public void testReduceNumber() throws Exception {
        long bookId = 1000;
        int update = bookDao.reduceNumber(bookId);
        System.out.println("update=" + update);
    }

}