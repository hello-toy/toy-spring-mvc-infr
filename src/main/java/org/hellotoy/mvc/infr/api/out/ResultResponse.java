package org.hellotoy.mvc.infr.api.out;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
public class ResultResponse<T> implements Serializable{

	private static final long serialVersionUID = 247961650095714281L;

	private String code;

	private String message;
	
	private T data;
	
	public void setMessage(MessageEnum msgEnum) {
		this.code = msgEnum.getCode();
		this.message = msgEnum.getMsg();
	}
	
	public void setMessage(String code,String message) {
		this.code = code;
		this.message = message;
	}
	

	public static <T> ResultResponse<T> buildSuccess() {
		return buildSuccess(null);
	}
	
	public static <T> ResultResponse<T> buildSuccess(T data) {
		ResultResponse<T> response = new ResultResponse<T>();
		response.setMessage(MessageEnum.SUCCESS);
		response.setData(data);
		return response;
	}
	
	public static <T> ResultResponse<T> buildSysError() {
		ResultResponse<T> response = new ResultResponse<T>();
		response.setMessage(MessageEnum.SYS_ERROR);
		return response;
	}
	
	public static <T> ResultResponse<T> buildIllegalArgs() {
		ResultResponse<T> response = new ResultResponse<T>();
		response.setMessage(MessageEnum.ILLEGAL_ARGS);
		return response;
	}
	

	
	public static <T> ResultResponse<T> build(MessageEnum codeEnmu, T data) {
		ResultResponse<T> response = new ResultResponse<T>();
		response.setMessage(codeEnmu);
		response.setData(data);
		return response;
	}

	public static ResultResponse<Boolean> build(Boolean result) {
		ResultResponse<Boolean> response = new ResultResponse<Boolean>();
		if (result) {
			response.setMessage(MessageEnum.SUCCESS);
		}
		response.setMessage(MessageEnum.BIZ_ERROR);
		response.setData(result);
		return response;
	}
	
}
