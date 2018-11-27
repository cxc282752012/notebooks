package com.books.notebasecore.util;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.Page;

/**
 * 分页信息。
 * <p>
 * Page index从1开始递增，第1页的page index为1，第2页的page index为2，以此类推第n页的page index为n。
 * 
 * @param <T>
 * 
 * 
 * @Filename: PagerInfo.java
 * @Version: 1.0
 * @Author: fenghu
 * @Email: liulei@mightcloud.com
 * 
 */
public class PagerInfo<T> implements Serializable {
    /**
     * Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 506758020097476778L;

    public PagerInfo() {
    }

    public void analyzePage(List<T> list) {
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            this.pageIndex = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.rowsCount = page.getTotal();
            this.pageTotal = page.getPages();
        }
    }

    /**
     * 创建分页信息对象。
     * 
     * @param pageSize
     *            每页记录数。必须大于0。
     * @param pageIndex
     *            第几页。Page index从1开始递增，第1页的page index为1，第2页的page
     *            index为2，以此类推第n页的page index为n。
     */
    public PagerInfo(int pageSize, int pageIndex) {
        if (pageIndex <= 0)
            throw new ArgumentException("分页信息错误，page index必须从1开始递增");
        if (pageSize <= 0)
            throw new ArgumentException("分页信息错误，page size必须大于0");
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    private int pageIndex = 1;

    /**
     * 获取第几页。
     * <p>
     * Page index从1开始递增，第1页的page index为1，第2页的page index为2，以此类推第n页的page index为n。
     * 
     * @return
     */
    public int getPageIndex() {
        return this.pageIndex;
    }

    /**
     * 取MySQL数据库 limit m,n 语句的开始索引值m。
     * 
     * @return
     */
    public int getStart() {
        return (this.pageIndex - 1) * this.pageSize;
    }

    private int pageSize = 20;

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 获取每页记录数。
     * 
     * @return
     */
    public int getPageSize() {
        return this.pageSize;
    }

    private long rowsCount = 0;

    /**
     * 获取符合条件的总记录数。
     * 
     * @return
     */
    public long getRowsCount() {
        return this.rowsCount;
    }

    public void setRowsCount(long rowsCount) {
        this.rowsCount = rowsCount;
    }

    private int pageTotal = 0;

    public int getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(int pageTotal) {
        this.pageTotal = pageTotal;
    }

    /**
     * 分析分页信息
     * 
     * @Description
     * @author Administrator
     * @param list
     */

    @Override
    public String toString() {
        return "{ pageIndex:" + this.pageIndex + ", pageSize:" + this.pageSize + ", rowsCount:"
               + this.rowsCount + " }";
    }
}