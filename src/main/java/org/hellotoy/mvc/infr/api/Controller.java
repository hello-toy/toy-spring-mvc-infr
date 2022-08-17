package org.hellotoy.mvc.infr.api;

import org.hellotoy.mvc.infr.api.criteria.ExchangeCriteria;
import org.hellotoy.mvc.infr.api.out.PageData;
import org.hellotoy.mvc.infr.api.out.ResultResponse;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.List;

@Validated
public interface Controller<I, ID extends Serializable, O> {

	/**
	 * 增加
	 * 
	 * @param param
	 */

	ResultResponse<O> create(@Valid I param);
	/**
	 * 修改
	 * 
	 * @param param
	 */
	ResultResponse<Boolean> updateByID(I param);

	/**
	 * 通过主键删除
	 * 
	 * @param pk
	 */
	ResultResponse<Boolean> deleteByID(ID id);

	/**
	 * 删除
	 * 
	 * @param exchangeCriteria
	 */
	ResultResponse<Boolean> delete(ExchangeCriteria searchCriteria);

	/**
	 * 通过主键查询
	 * 
	 * @param pk
	 * @return
	 */

	ResultResponse<O> getByID(ID id);

	/**
	 * 查询全部
	 * 
	 * @return
	 */
	ResultResponse<List<O>> findAll();
	/**
	 * 根据条件查询全部
	 * 
	 * @param exchangeCriteria
	 * @return
	 */

	ResultResponse<PageData<O>> findByCriteria(ExchangeCriteria searchCriteria);

}
