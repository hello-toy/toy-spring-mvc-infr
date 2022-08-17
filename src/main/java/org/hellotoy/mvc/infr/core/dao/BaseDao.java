package org.hellotoy.mvc.infr.core.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.hellotoy.mvc.infr.api.criteria.ExchangeCriteria;
import org.hellotoy.mvc.infr.api.criteria.Order;
import org.hellotoy.mvc.infr.api.criteria.criterion.Restriction;
import org.hellotoy.mvc.infr.api.criteria.criterion.RestrictionList;
import org.hellotoy.mvc.infr.api.criteria.criterion.Restrictions;
import org.hellotoy.mvc.infr.api.out.PageData;
import org.hellotoy.mvc.infr.core.entity.BaseEntity;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class BaseDao<ID extends Serializable, E extends BaseEntity,M> implements Dao<M, ID> {


	protected final Class<M> modelClass;

	protected final Class<E> entityClass;

	@SuppressWarnings("unchecked")
	public BaseDao() {
		modelClass = (Class<M>) getGenericClass(2);
		entityClass = (Class<E>) getGenericClass(1);
		Assert.notNull(modelClass, "modelClass Class is null");
		Assert.notNull(entityClass, "entityClass Class is null");
	}

	protected Class<?> getGenericClass(int index) {
		return (Class<?>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[index];
	}

	@Override
	public Boolean insert(M param) {
		E entity = buildEntity(param);
		entity.setGmtCreated(new Date());
		boolean retData = getMapper().insert(entity) > 0;
		BeanUtils.copyProperties(entity, param);
		return retData;
	}

	@Override
	public Boolean updateByID(M param) {
		E entity = buildEntity(param);
		entity.setGmtModified(new Date());
		return getMapper().updateById(entity) > 0;
	}

	@Override
	public Boolean deleteByID(ID id) {
		E entity = this.getMapper().selectById(id);
		entity.setIsDeleted("Y");
		return this.getMapper().updateById(entity) > 0;
	}

	@Override
	public Boolean delete(ExchangeCriteria exchangeCriteria) {
		QueryWrapper<E> wrapper = generateQueryWrapper(exchangeCriteria);
		List<E> entity = this.getMapper().selectList(wrapper);
		entity.forEach(e -> {
			e.setIsDeleted("Y");
			this.getMapper().updateById(e);
		});
		return true;
	}

	@Override
	public M getByID(ID id) {
		E entity = getMapper().selectById(id);
		if (ObjectUtils.anyNotNull(entity) && "N".equals(entity.getIsDeleted())) {
			return buildModel(entity);
		} else {
			return null;
		}
	}

	@Override
	public List<M> findAll() {
		QueryWrapper<E> queryWrapper = new QueryWrapper<E>();
		queryWrapper.eq("is_deleted", "N");
		List<E> entities = getMapper().selectList(queryWrapper);
		List<M> retData = new ArrayList<>();
		entities.forEach(entity -> {
			retData.add(buildModel(entity));
		});
		return retData;
	}

	@Override
	public PageData<M> findByCriteria(ExchangeCriteria exchangeCriteria) {
		PageData<M> retData = null;
		try {
			Page<E> page = new Page<E>(exchangeCriteria.getPageNum(), exchangeCriteria.getPageSize());
			QueryWrapper<E> wrapper = generateQueryWrapper(exchangeCriteria);
			Page<E> pageData = getMapper().selectPage(page, wrapper);
			retData = PageData.builder(pageData.getTotal(), pageData.getSize(), pageData.getCurrent());
			for (E entity : pageData.getRecords()) {
				retData.addData(buildModel(entity));
			}
		} catch (Exception e) {
			throw new RuntimeException("findByCriteria occured error!", e);
		}
		return retData;
	}
	

	@Override
	public M findOneByCriteria(ExchangeCriteria exchangeCriteria) {
		exchangeCriteria.setPageNum(1);
		exchangeCriteria.setPageSize(1);
		PageData<M> respData = this.findByCriteria(exchangeCriteria);
		if(respData.getDatas()!=null && !respData.getDatas().isEmpty()) {
			return respData.getDatas().get(0);
		}
		return null;
		
	}

	@Override
	public List<M> findAllByCriteria(ExchangeCriteria exchangeCriteria) {
		exchangeCriteria.setPageNum(1);
		exchangeCriteria.setPageSize(Integer.MAX_VALUE);
		PageData<M> respData = this.findByCriteria(exchangeCriteria);
		return respData.getDatas();
	}

	public QueryWrapper<E> generateQueryWrapper(ExchangeCriteria criteria) {
		criteria.add(Restrictions.eq("isDeleted", "N"));
		QueryWrapper<E> queryWrapper = new QueryWrapper<E>();
		queryWrapper.setEntityClass(entityClass);
		List<Restriction> conditons = criteria.getRestrictions();
		for (Restriction er : conditons) {
			transformQuery(er, queryWrapper);
		}
		List<Order> orders = criteria.getOrders();
		orders.forEach(order -> {
			String orderName = camel2under(order.getName());
			if (order.getType() == Order.Type.DESC) {
				queryWrapper.orderByDesc(orderName);
			} else if (order.getType() == Order.Type.ASC) {
				queryWrapper.orderByAsc(orderName);
			}
		});
		return queryWrapper;
	}

	protected void transformQuery(Restriction restriction, QueryWrapper<?> queryWrapper) {
		switch (restriction.getOperator()) {
		case OR:
			queryWrapper.or();
			List<Restriction> orRestrictions = ((RestrictionList) restriction).getRestrictions();
			orRestrictions.forEach(x -> {
				transformQuery(x, queryWrapper);
			});
			break;
		case AND:
			queryWrapper.and(and -> {
				transformBaseQuery(restriction, and);
			});
			break;
		default:
			transformBaseQuery(restriction, queryWrapper);
			break;
		}
	}

	public static String camel2under(String c) {
		String separator = "_";
		c = c.replaceAll("([a-z])([A-Z])", "$1" + separator + "$2").toLowerCase();
		return c;
	}

	protected void transformBaseQuery(Restriction restriction, QueryWrapper<?> queryWrapper) {
		String key = camel2under(restriction.getName());
		Object value = restriction.getValue();
		if(StringUtils.isEmpty(String.valueOf(value))||StringUtils.isEmpty(String.valueOf(key))) {
			return;
		}
		switch (restriction.getOperator()) {
		case EQUALS:
			queryWrapper.eq(key, value);
			break;
		case NOT_EQUALS:
			queryWrapper.ne(key, value);
			break;
		case LESS_THAN:
			queryWrapper.lt(key, value);
			break;
		case LESS_OR_EQUALS_THAN:
			queryWrapper.le(key, value);
			break;
		case GREATER_THAN:
			queryWrapper.gt(key, value);
			break;
		case GREATER_OR_EQUALS_THAN:
			queryWrapper.ge(key, value);
			break;
		case IN:
			queryWrapper.in(key, value);
			break;
		case IS_NULL:
			queryWrapper.isNull(key);
			break;
		case IS_NOT_NULL:
			queryWrapper.isNotNull(key);
			break;
		case CONTAINS:
			queryWrapper.like(key, value);
			break;
		default:
			break;
		}
	}

	protected abstract BaseMapper<E> getMapper();

	protected E buildEntity(M model) {
		E entity = createBean(entityClass);
		BeanUtils.copyProperties(model, entity);
		return entity;
	}

	protected M buildModel(E entity) {
		M model = createBean(modelClass);
		BeanUtils.copyProperties(entity, model);
		return model;
	}

	protected <C> C createBean(Class<C> clz) {
		try {
			return clz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

}
