package com.sucl.sbjms.core.orm;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author sucl
 * @date 2019/4/1
 */
@Data
@NoArgsConstructor
public class Pager<T> implements Serializable {
    public static final int DEFAULT_PAGESIZE = 15;
    public static final int DEFAULT_PAGEINDEX = 1;

    private int pageType = 1;//1:count、size
    private int total;
    private int pageIndex;
    private int pageSize;
    private int pageSart;
    private int pageEnd;
    private int maxPage;
    private List<T> result;

    public Pager(String pageIndex,String pageSize){
        try {
            this.pageIndex = new Integer(pageIndex).intValue();
        } catch (NumberFormatException e) {
            this.pageIndex = DEFAULT_PAGEINDEX;
        }

        try {
            this.pageSize = new Integer(pageSize).intValue();
        } catch (NumberFormatException e) {
            this.pageSize = DEFAULT_PAGESIZE;
        }
        initPage();
    }

    private void initPage() {
        this.pageIndex = pageIndex<1?1:pageIndex;
        this.pageSart = (pageIndex-1)*pageSize;
        this.pageEnd = pageIndex*pageSize;
    }

    public void setTotal(int total) {
        int _maxPage = getMaxPage();
        if(_maxPage>0 && _maxPage<pageIndex){
            pageIndex = _maxPage;
        }
        this.total = total;
        initPage();
    }

    public int getMaxPage() {
        double max = Math.ceil(new Double(total).doubleValue() / pageSart);;
        return new Double(max).intValue();
    }
}
