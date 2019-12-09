package org.lanqiao.bean;

import java.util.List;

public class PageBean {
    private int totalCount;//总记录数
    private List<rominfo> data;//查询到的数据

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public List<rominfo> getData() {
        return data;
    }

    public void setData(List<rominfo> data) {
        this.data = data;
    }
}
