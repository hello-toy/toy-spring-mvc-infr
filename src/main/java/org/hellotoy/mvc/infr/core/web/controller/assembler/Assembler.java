package org.hellotoy.mvc.infr.core.web.controller.assembler;

/**
 * 
 * @author admin
 *
 * @param <I>
 * @param <O>
 * @param <M>
 */
public interface Assembler<I, O, M> {
	/**
	 * 组装模型
	 * 
	 * @param param
	 * @return
	 */
	public M assemblerModel(I param);

	/**
	 * 组装返回值
	 * 
	 * @param model
	 * @return
	 */
	public O assemblerInfo(M model);

	/**
	 * 
	 * @param entity
	 * @return
	 *//*
		 * public M assemblerModel(E entity);
		 */

}
