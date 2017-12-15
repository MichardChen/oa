package com.thinkgem.jeesite.common.utils;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.springframework.context.i18n.LocaleContextHolder;

public class MessageUtil {

	public static String getMessage(String messageId) {
		Locale locale = LocaleContextHolder.getLocale();
		String message;
		try {
			message = ResourceBundle.getBundle("applicationResource", locale).getString(messageId);
		} catch (MissingResourceException mse) {
			message = messageId;
		}
		return message;
	}

	public static String getMessage(String messageId, String... parameter) {
		Locale locale = LocaleContextHolder.getLocale();
		String message;
		try {
			message = ResourceBundle.getBundle("applicationResource", locale).getString(messageId);
		} catch (MissingResourceException mse) {
			message = messageId;
		}
		Object[] objects = parameter;
		message = MessageFormat.format(message, objects);
		return message;
	}

	public static String getMessage(String messageId, Locale locale) {
		String message;
		try {
			message = ResourceBundle.getBundle("applicationResource", locale).getString(messageId);
		} catch (MissingResourceException mse) {
			message = messageId;
		}
		return message;
	}

	public static String getMessage(String messageId, Locale locale, Object... parameter) {
		String message;
		try {
			message = ResourceBundle.getBundle("applicationResource", locale).getString(messageId);
		} catch (MissingResourceException mse) {
			message = messageId;
		}
		message = MessageFormat.format(message, parameter);
		return message;
	}
}
