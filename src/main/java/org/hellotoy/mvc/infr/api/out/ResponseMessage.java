package org.hellotoy.mvc.infr.api.out;

public class ResponseMessage {

	private String code;

	private String message;
	
	private String subCode;
	
	private String subMessage;


	ResponseMessage(MessageEnum codeEnum) {
		this.code = codeEnum.getCode();
		this.message = codeEnum.getMsg();
	}

	ResponseMessage(String code, String msg) {
		this.code = code;
		this.message = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public ResponseMessage setSubMessage(String code,String msg) {
		this.subCode = code;
		this.subMessage = msg;
		
		return this;
	}
	


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSubCode() {
		return subCode;
	}

	public void setSubCode(String subCode) {
		this.subCode = subCode;
	}

	public String getSubMessage() {
		return subMessage;
	}

	public void setSubMessage(String subMessage) {
		this.subMessage = subMessage;
	}



	public static enum MessageEnum {

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

}
