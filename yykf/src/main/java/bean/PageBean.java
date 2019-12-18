package bean;

import java.util.List;

public class PageBean {
    private int totalCount;//总记录数量
    private List<Product> data;//查询到的数据

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }
}
