package org.hellotoy.mvc.infr.api.out;

public enum MessageEnum {

	SYS_ERROR("-1", "系统内部错误"), SUCCESS("0", "Success"), BIZ_ERROR("-2", "业务错误"), ILLEGAL_ARGS("-3", "参数错误"),
	UNKNOWN_ERROR("404", "未知错误");

	private String code;

	private String msg;

	MessageEnum(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public static String getMessageByName(String name) {
		for (MessageEnum item : MessageEnum.values()) {
			if (item.name().equals(name)) {
				return item.msg;
			}
		}
		return name;
	}

	public static String getCodeByName(String name) {
		for (MessageEnum item : MessageEnum.values()) {
			if (item.name().equals(name)) {
				return item.code;
			}
		}
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
