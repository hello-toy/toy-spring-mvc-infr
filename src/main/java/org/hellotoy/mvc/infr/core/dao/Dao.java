package org.hellotoy.mvc.infr.core.dao;

import org.hellotoy.mvc.infr.api.criteria.ExchangeCriteria;
import org.hellotoy.mvc.infr.api.out.PageData;

import java.io.Serializable;
import java.util.List;

public interface Dao<M,ID extends Serializable> {
	/**
	 * 增加
	 * 
	 * @param param
	 */

	Boolean insert(M param);

	/**
	 * 修改
	 * 
	 * @param param
	 */
	Boolean updateByID(M param);

	/**
	 * 通过主键删除
	 * 
	 * @param pk
	 */
	Boolean deleteByID(ID id);

	/**
	 * 删除
	 * 
	 * @param exchangeCriteria
	 */
	Boolean delete(ExchangeCriteria exchangeCriteria);

	/**
	 * 通过主键查询
	 * 
	 * @param pk
	 * @return
	 */

	M getByID(ID id);

	/**
	 * 查询全部
	 * 
	 * @return
	 */
	List<M> findAll();

	/**
	 * 根据条件查询一条
	 *
	 * @param exchangeCriteria
	 * @return
	 */
	M findOneByCriteria(ExchangeCriteria exchangeCriteria);

	/**
	 * 根据条件查询全部
	 * 
	 * @param exchangeCriteria
	 * @return
	 */

	PageData<M> findByCriteria(ExchangeCriteria exchangeCriteria);

	
	/**
	 * 根据条件获取第一个
	 * @param exchangeCriteria
	 * @return
	 */
	List<M> findAllByCriteria(ExchangeCriteria exchangeCriteria);
}
