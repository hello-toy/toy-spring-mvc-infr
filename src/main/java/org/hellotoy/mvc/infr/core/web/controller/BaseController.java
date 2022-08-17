package org.hellotoy.mvc.infr.core.web.controller;

import org.hellotoy.mvc.infr.api.Controller;
import org.hellotoy.mvc.infr.api.criteria.ExchangeCriteria;
import org.hellotoy.mvc.infr.api.out.MessageEnum;
import org.hellotoy.mvc.infr.api.out.PageData;
import org.hellotoy.mvc.infr.api.out.ResultResponse;
import org.hellotoy.mvc.infr.core.service.IService;
import org.hellotoy.mvc.infr.core.web.controller.assembler.Assembler;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public abstract class BaseController<I,ID extends Serializable,O,M> implements Controller<I, ID, O> {
	@Override
	 @RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResultResponse<O> create(@Valid I param) {
		ResultResponse<O> retData = null;
    	try {
    		logInputParam(param);
    		if(null==param) {
    			return  ResultResponse.buildIllegalArgs();
    		}
    		M model = getService().create(getAssembler().assemblerModel(param));
    		retData = buildResultResponse(model);
		} catch (Exception e) {
			logError(e);
			retData = ResultResponse.buildSysError();
		}
    	return retData;
	}

	@Override
	@RequestMapping(value = "/updateByID", method = RequestMethod.POST)
	public ResultResponse<Boolean> updateByID(I param) {
		ResultResponse<Boolean> retData = null;
    	try {
    		logInputParam(param);
    		if(null==param) {
    			return  ResultResponse.buildIllegalArgs();
    		}
    		M model = getService().create(getAssembler().assemblerModel(param));
    		Boolean result = getService().updateByID(model);
    		retData = this.buildBooleanResultResponse(result);
		} catch (Exception e) {
			logError(e);
			retData = ResultResponse.buildSysError();
		}
    	return retData;
	}

	@Override
	@RequestMapping(value = "/deleteByID", method = RequestMethod.GET)
	public ResultResponse<Boolean> deleteByID(ID id) {
		ResultResponse<Boolean> retData = null;
    	try {
    		logInputParam(id);
    		if(null==id) {
    			return  ResultResponse.buildIllegalArgs();
    		}
    		Boolean result = getService().deleteByID(id);
    		retData = this.buildBooleanResultResponse(result);
		} catch (Exception e) {
			logError(e);
			retData = ResultResponse.buildSysError();
		}
    	return retData;
	}

	@Override
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public ResultResponse<Boolean> delete(@RequestBody ExchangeCriteria searchCriteria) {
		ResultResponse<Boolean> retData = null;
    	try {
    		logInputParam(searchCriteria);
    		if(null==searchCriteria) {
    			return  ResultResponse.buildIllegalArgs();
    		}
    		Boolean result = getService().delete(searchCriteria);
    		retData = buildBooleanResultResponse(result);
		} catch (Exception e) {
			logError(e);
			retData = ResultResponse.buildSysError();
		}
    	return retData;
	}

	@Override
	@RequestMapping(value = "/getByID", method = RequestMethod.GET)
	public ResultResponse<O> getByID(ID id) {
		ResultResponse<O> retData = null;
    	try {
    		logInputParam(id);
    		M result = getService().getByID(id);
    		retData = buildResultResponse(result);
		} catch (Exception e) {
			logError(e);
			retData = ResultResponse.buildSysError();
		}
    	return retData;
	}

	@Override
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	public ResultResponse<List<O>> findAll() {
		ResultResponse<List<O>> retData = null;
    	try {
    		logInputParam("find All");
    		List<M> models = getService().findAll();
    		List<O> tempData = new ArrayList<>();
    		models.forEach(data -> {
    			tempData.add(getAssembler().assemblerInfo(data));
    		});
    		retData = ResultResponse.buildSuccess(tempData);
    		logOutputParam(retData);
		} catch (Exception e) {
			logError(e);
			retData = ResultResponse.buildSysError();
		}
    	return retData;
	}

	@Override
	@RequestMapping(value = "/findByCriteria", method = RequestMethod.POST)
	public ResultResponse<PageData<O>> findByCriteria(@RequestBody ExchangeCriteria searchCriteria) {
		ResultResponse<PageData<O>> retData = null;
    	try {
    		logInputParam(searchCriteria);
    		if(null==searchCriteria) {
    			return  ResultResponse.buildIllegalArgs();
    		}
    		PageData<M> result = getService().findByCriteria(searchCriteria);
    		PageData<O> pageResult = PageData.builder(result.getTotal(), result.getPageSize(),
    				result.getPageNum());
    		retData = ResultResponse.buildSuccess(pageResult);
    		logOutputParam(retData);
		} catch (Exception e) {
			logError(e);
			retData = ResultResponse.buildSysError();
		}
    	return retData;
	}
	abstract protected IService<M,ID> getService();
	/**
	 * 记录日志
	 * @param retData
	 */
	protected void logOutputParam(Object retData) {
		getLogger().debug("response data is:{}",retData.toString());
	}

	/**
	 * 记录日志
	 * @param inputEntity
	 */
	protected void logInputParam(Object inputEntity) {
		getLogger().debug("request params is:{}",inputEntity.toString());
	}
	/**
	 * 记录错误
	 * @param inputEntity
	 */
	protected void logError(Exception  e) {
		getLogger().error("occured error!",e);
	}
	
	abstract protected Logger getLogger();
	
	public abstract Assembler<I, O, M> getAssembler();
	
	protected ResultResponse<O> buildResultResponse(M result) {
		ResultResponse<O> retData =  ResultResponse.build(MessageEnum.SUCCESS, getAssembler().assemblerInfo(result));
		this.logOutputParam(retData);
		return retData;
	}

	protected ResultResponse<Boolean> buildBooleanResultResponse(Boolean result) {
		return ResultResponse.buildSuccess(result);
	}
}
