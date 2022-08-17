package org.hellotoy.mvc.infr.core.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BaseEntity {
	/** 
	 * 删除标记
	 **/
	private String isDeleted ="N";
	/** 
	 * 创建时间
	 **/
	private Date gmtCreated = new Date();
	/** 
	 * 创建人
	 **/
	private String creator="default";
	/** 
	 * 修改时间
	 **/
	private Date gmtModified = new Date();
	/** 
	 * 修改人
	 **/
	private String modifier ="";
	
	public BaseEntity() {
		this.isDeleted = "N";
		this.gmtModified = new Date();
	}
}
