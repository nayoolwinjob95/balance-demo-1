package com.jdc.balancedemo1.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.jdc.balancedemo1.model.BalanceAppException;
import com.jdc.balancedemo1.model.domain.entity.Balance.Type;
import com.jdc.balancedemo1.model.domain.form.BalanceEditForm;
import com.jdc.balancedemo1.model.domain.form.BalanceItemForm;
import com.jdc.balancedemo1.model.domain.form.BalanceSummaryForm;
import com.jdc.balancedemo1.model.service.BalanceService;

@Controller
@RequestMapping("user/balance-edit")
@SessionAttributes("balanceEditForm")
public class BalanceEditController {

	@Autowired
	private BalanceService balanceService;

	@GetMapping
	public String edit(@ModelAttribute("balanceEditForm") BalanceEditForm form,
			@RequestParam(required = false) Integer id, @RequestParam(required = false) Type type) {

		if (null != id && form.getHeader().getId() != id) {
			var result = balanceService.findById(id);
			form.setHeader(result.getHeader());
			form.setItems(result.getItems());
		}

		if (null != type && form.getHeader().getType() != type) {
			form.setHeader(new BalanceSummaryForm());
			form.getHeader().setType(type);
			form.getItems().clear();
		}

		return "balance-edit";
	}

	@PostMapping("item")
	public String addItem(@ModelAttribute("balanceEditForm") BalanceEditForm form,
			@ModelAttribute("itemForm") @Valid BalanceItemForm itemForm, BindingResult result) {

		if (result.hasErrors()) {
			return "balance-edit";
		}

		form.getItems().add(itemForm);

		return "redirect:/user/balance-edit?%s".formatted(form.queryParams());
	}

	@GetMapping("item")
	public String deleteItem(@ModelAttribute("balanceEditForm") BalanceEditForm form, @RequestParam int index) {

		var item = form.getItems().get(index);

		if (item.getId() == 0) {
			form.getItems().remove(index);
		} else {
			item.setDeleted(true);
		}

		return "redirect:/user/balance-edit?%s".formatted(form.queryParams());
	}

	@GetMapping("confirm")
	public String confirm() {
		return "balance-edit-confirm";
	}

	@PostMapping()
	public String save(@ModelAttribute("balanceEditForm") BalanceEditForm form,
			@ModelAttribute("summaryForm") @Valid BalanceSummaryForm summaryForm, BindingResult result) {

		if (result.hasErrors()) {
			return "balance-edit-confirm";
		}
		
		form.getHeader().setCategory(summaryForm.getCategory());
		form.getHeader().setDate(summaryForm.getDate());
		
		var id = balanceService.save(form);
		form.clear();

		return "redirect:/user/balance/details/%d".formatted(id);
	}
	
	@ModelAttribute("summaryForm")
	BalanceSummaryForm summaryForm(@ModelAttribute("balanceEditForm") BalanceEditForm form) {
		return form.getHeader();
	}

	@ModelAttribute("itemForm")
	BalanceItemForm itemForm() {
		return new BalanceItemForm();
	}

	@ModelAttribute("balanceEditForm")
	public BalanceEditForm form(@RequestParam(required = false) Integer id, @RequestParam(required = false) Type type) {

		if (null != id) {
			return balanceService.findById(id);
		}

		if (null == type) {
			throw new BalanceAppException("Please set type for balance.");
		}

		return new BalanceEditForm().type(type);
	}

}
