package service;

import bean.PageBean;
import bean.Product;
import dao.ProductDao;

import java.sql.SQLException;
import java.util.List;

public class ProductService {
    public PageBean productsByCurrPage(int currPage, int pageSize) throws SQLException {
        ProductDao dao = new ProductDao();
        int totalCount = dao.getTotalCount();

        List<Product> list = dao.findProductsByCurrPage(currPage , pageSize);

        PageBean pageBean = new PageBean();
        pageBean.setTotalCount(totalCount);
        pageBean.setData(list);

        return pageBean;

    }
}
