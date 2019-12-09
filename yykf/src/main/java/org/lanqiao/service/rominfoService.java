package org.lanqiao.service;

import org.lanqiao.bean.PageBean;
import org.lanqiao.bean.rominfo;
import org.lanqiao.dao.rominfoDao;

import java.sql.SQLException;
import java.util.List;

public class rominfoService {
    public PageBean productsByCurrPage(int currPage, int pageSize) throws SQLException {
        rominfoDao dao = new rominfoDao();
        int totalCount = dao.getTotalCount();

        List<rominfo> list = dao.findrominfosByCurrPage(currPage , pageSize);

        PageBean pageBean = new PageBean();
        pageBean.setTotalCount(totalCount);
        pageBean.setData(list);

        return pageBean;

    }
}
