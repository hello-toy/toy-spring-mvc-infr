package org.hellotoy.mvc.infr.core.service;

import org.hellotoy.mvc.infr.api.criteria.ExchangeCriteria;
import org.hellotoy.mvc.infr.api.out.PageData;
import org.hellotoy.mvc.infr.core.dao.Dao;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;
/**
 * 
 * @author admin
 *
 * @param <M> 模型
 * @param <ID> 主键
 */
@Slf4j
public abstract class BaseService<M, ID extends Serializable> implements IService<M,ID> {

	@Override
	public M create(M param) {
		getDao().insert(param);
		return param;
	}

	@Override
	public Boolean updateByID(M model) {
		return getDao().updateByID(model);
	}

	@Override
	public Boolean deleteByID(ID id) {
		return getDao().deleteByID(id);
	}

	@Override
	public Boolean delete(ExchangeCriteria searchCriteria) {
		return getDao().delete(searchCriteria);
	}

	@Override
	public M getByID(ID id) {
		return getDao().getByID(id);
	}

	@Override
	public List<M> findAll() {
		List<M> result = getDao().findAll();
		return result;
	}

	@Override
	public PageData<M> findByCriteria(ExchangeCriteria searchCriteria) {
		PageData<M> result = getDao()
				.findByCriteria(searchCriteria);
		return result;
	}
	@Override
	public M findOneByCriteria(ExchangeCriteria exchangeCriteria) {
		return this.getDao().findOneByCriteria(exchangeCriteria);
	}

	@Override
	public List<M> findAllByCriteria(ExchangeCriteria exchangeCriteria) {
		return this.getDao().findAllByCriteria(exchangeCriteria);
	}
	abstract protected Dao<M,ID> getDao();


}
