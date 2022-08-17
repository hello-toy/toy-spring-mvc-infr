package org.hellotoy.mvc.infr.api.out;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PageData<T>{
	/**
	 * 总条数
	 */
    private long total;
    /**
     * 总页数
     */
    private long totalPages;
    /**
     * 每页数量
     */
    private long pageSize;
    /**
     * 当前页数
     */
    private long pageNum;
    
    
    private List<T> datas;
    
    public static final long TEN = 10;
    
    public PageData() {
    	datas = new ArrayList<T>();
    }
    public PageData<T> addData(T data){
    	datas.add(data);
    	return this;
    }
    public static <T> PageData<T> builder(long total,long pageSize,long pageNum,List<T> datas){
    	PageData<T> response = new PageData<T>();
    	response.setPageNum(pageNum);
    	response.setTotal(total);
    	response.setPageSize(pageSize);
    	response.setDatas(datas);
    	response.setTotalPages((total+pageSize-1)/pageSize);
    	return  response;
    }
    public static <T> PageData<T> builder(List<T> datas){
    	return builder(datas.size(),TEN,1);
    }
    public static <T> PageData<T> builder(long total,long pageSize,long pageNum){
    	return builder(total,pageSize,pageNum,new ArrayList<T>());
    }
    
}
