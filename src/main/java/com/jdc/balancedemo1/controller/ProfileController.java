package com.jdc.balancedemo1.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.jdc.balancedemo1.controller.utils.Pagination;
import com.jdc.balancedemo1.model.service.UserAccessLogService;
import com.jdc.balancedemo1.model.service.UserService;

@Controller
@RequestMapping("user/profile")
public class ProfileController {

	@Autowired
	private UserService userService;

	@Autowired
	private UserAccessLogService userAccessLogService;

	@GetMapping
	String index(ModelMap model, @RequestParam Optional<Integer> page, @RequestParam Optional<Integer> size) {

		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		var userVO = userService.findByLoginId(username);
		model.put("user", userVO);

		var accessLogs = userAccessLogService.search(username, page, size);
		model.put("list", accessLogs.getContent());

		// var pagination = Pagination.builder("/user/profile").current(accessLogs.getNumber())
		//		.total(accessLogs.getTotalPages()).first(accessLogs.isFirst()).last(accessLogs.isLast()).build();
		var pagination = Pagination.builder("/user/profile").page(accessLogs).build();

		model.put("pagination", pagination);

		return "profile";
	}

	@PostMapping("contact")
	String updateContact(@RequestParam(required = false) String phone, @RequestParam(required = false) String email) {

		var username = SecurityContextHolder.getContext().getAuthentication().getName();
		userService.updateContact(username, phone, email);

		return "redirect:/user/profile";
	}

}
