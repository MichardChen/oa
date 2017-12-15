package com.thinkgem.jeesite.common.exception;

import com.thinkgem.jeesite.common.utils.MessageUtil;



public class ValidationException extends RuntimeException {

	private static final long serialVersionUID = -2968106036185689254L;

	private String messageKey;

	private String[] args;

	public ValidationException(String messageKey) {
		this(messageKey, new String[] {});
	}

	public ValidationException(String messageKey, String... args) {
		super(MessageUtil.getMessage(messageKey, args));
		this.messageKey = messageKey;
		this.args = args;
	}

	public String getMessageKey() {
		return messageKey;
	}

	public String[] getArgs() {
		return args;
	}

	@Override
	public String getMessage() {
		return MessageUtil.getMessage(messageKey, args);
	}
}
