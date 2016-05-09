package com.ks.model;

import java.util.List;

/**
 * 分页对象
 * @author <a href="mailto:2440403401@qq.com">zhoujf</a>
 * @date 2015年8月21日
 */
public class PageResult<T> {

    private long total;
    private List<T> rows;
    
    public PageResult() {
    }

    public PageResult(long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Object getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
