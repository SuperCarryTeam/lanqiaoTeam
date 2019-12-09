package org.lanqiao.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.lanqiao.bean.rominfo;
import org.lanqiao.utils.JDBCUtils;

import java.sql.SQLException;
import java.util.List;

public class userDao {
    QueryRunner qr = new QueryRunner(JDBCUtils.getDS());

    public List<rominfo> finduserinfo(int currPage, int pageSize) throws SQLException {
        String sql  = "select * from user";

        List<rominfo> user = qr.query(sql, new BeanListHandler<rominfo>(rominfo.class));

        return user;
    }
}
