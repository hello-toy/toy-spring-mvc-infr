package org.hellotoy.mvc.infr.core.service;

import org.hellotoy.mvc.infr.api.criteria.ExchangeCriteria;
import org.hellotoy.mvc.infr.api.out.PageData;

import java.io.Serializable;
import java.util.List;

public interface IService<M, ID extends Serializable> {
	/**
	 * 增加
	 * 
	 * @param param
	 */

	default M create(M param) {
		return null;
	}

	/**
	 * 修改
	 * 
	 * @param param
	 */
	default Boolean updateByID(M param) {
		return null;
	}

	/**
	 * 通过主键删除
	 * 
	 * @param pk
	 */
	default Boolean deleteByID(ID pk) {
		return null;
	}

	/**
	 * 删除
	 * 
	 * @param exchangeCriteria
	 */
	default Boolean delete(ExchangeCriteria exchangeCriteria) {
		return null;
	}

	/**
	 * 通过主键查询
	 * 
	 * @param pk
	 * @return
	 */

	default M getByID(ID id) {
		return null;
	}

	/**
	 * 查询全部
	 * 
	 * @return
	 */
	default List<M> findAll() {
		return null;
	}

	/**
	 * 根据条件查询全部
	 * 
	 * @param exchangeCriteria
	 * @return
	 */

	default PageData<M> findByCriteria(ExchangeCriteria exchangeCriteria) {
		return null;
	}
	/**
	 * 根据条件获取第一个
	 * @param exchangeCriteria
	 * @return
	 */
	M findOneByCriteria(ExchangeCriteria exchangeCriteria);
	/**
	 * 根据条件查询出所有
	 * @param exchangeCriteria
	 * @return
	 */
	List<M> findAllByCriteria(ExchangeCriteria exchangeCriteria);
}
