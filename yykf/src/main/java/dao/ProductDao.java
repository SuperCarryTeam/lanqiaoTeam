package dao;

import bean.Product;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.JDBCUtils;

import java.sql.SQLException;
import java.util.List;

public class ProductDao {
        QueryRunner qr = new QueryRunner(JDBCUtils.getDS());

        public int getTotalCount() throws SQLException {
            String sql = "select count(*) from user";
            long totalCount = (long) qr.query(sql, new ScalarHandler<>());
            return (int)totalCount;
        }
    public List<Product> findProductsByCurrPage(int currPage, int pageSize) throws SQLException {
        String sql  = "select * from user limit ? , ? ";
        int begin = (currPage-1)*pageSize; //第几条记录开始（begin）
        Object[] params = {begin , pageSize};
        List<Product> products = qr.query(sql, new BeanListHandler<Product>(Product.class), params);

        return products;
    }
}
