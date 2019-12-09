package org.lanqiao.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import org.lanqiao.bean.rominfo;
import org.lanqiao.utils.JDBCUtils;

import java.security.PublicKey;
import java.sql.SQLException;
import java.util.List;

public class rominfoDao {
    QueryRunner qr = new QueryRunner(JDBCUtils.getDS());

    public int getTotalCount() throws SQLException {
        String sql = "select count(*) from rominfo";
        long totalCount = (long) qr.query(sql, new ScalarHandler<>());
        return (int)totalCount;
    }

    public List<rominfo> findrominfosByCurrPage(int currPage, int pageSize) throws SQLException {
        String sql  = "select * from rominfo limit ? , ? ";
        int begin = (currPage-1)*pageSize; //第几条记录开始（begin）
        Object[] params = {begin , pageSize};
        List<rominfo> rominfos = qr.query(sql, new BeanListHandler<rominfo>(rominfo.class), params);

        return rominfos;
    }

}
