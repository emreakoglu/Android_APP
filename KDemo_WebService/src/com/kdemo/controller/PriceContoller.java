package com.kdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kdemo.model.Price;
import com.kdemo.services.PriceService;


@Controller
@RequestMapping("/price")
public class PriceContoller {
	
	@Autowired
	PriceService priceService;
	
	
	@RequestMapping(value = "/save" , method = RequestMethod.POST)
	public String save(@ModelAttribute("price") Price price, Model model) {
		priceService.add(price);
		return "redirect:/list";
	}
	
	@RequestMapping(value="/add", method = RequestMethod.GET)
	public String add(Model model) {
		model.addAttribute("price", priceService.create());
		return "addPrice";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model) {
		List<Price> prices = priceService.findAll();
		model.addAttribute("prices", prices);
		return "listPrice";
	}
	
	@RequestMapping(value = "/edit/{priceId}",method = RequestMethod.GET)
	public String edit (@PathVariable Long priceId, Model model) {
		Price price = priceService.findById(priceId);
		model.addAttribute("price", price);
		return "addPrice";
	}
	
	@RequestMapping(value= "/delete{priceId}", method = RequestMethod.GET)
	public String delete (@PathVariable Long priceId, Model model) {
		Price price = priceService.findById(priceId);
		priceService.delete(price);
		return "redirect:/list";
	}
	
	@RequestMapping(value = "/findPrice/{type}", method = RequestMethod.GET)    	// Android tarafında geliştirme Yap
	@ResponseBody
	public Price findByType (@PathVariable String type, Model model) {
		return priceService.findByType(type);
	}
}
