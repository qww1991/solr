package com.q.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.q.pojo.QueryVo;
import com.q.pojo.ResultModel;
import com.q.service.ProductService;

@Controller
@RequestMapping("/product")
public class ProductAction {

	@Resource
	ProductService productService;
	
	@RequestMapping("/list")
	public String list(QueryVo vo, Model model) throws Exception{
		ResultModel result = productService.findProductList(vo);
		model.addAttribute("result", result);
		model.addAttribute("vo", vo);
		return "product_list";
	}
	
}
