package com.jdc.balancedemo1.controller.utils;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.jdc.balancedemo1.model.BalanceAppException;

@ControllerAdvice
public class BalanceAppExceptionHandler {

	@ExceptionHandler(value = BalanceAppException.class)
	String handle(BalanceAppException e, HttpServletRequest req) {

		RequestContextUtils.getOutputFlashMap(req).put("message", e.getMessage());

		return "redirect:/";
	}

}
