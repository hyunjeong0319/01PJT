package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;

public  class AddPurchaseViewAction extends Action {

	public AddPurchaseViewAction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("진입");
		
		ProductService service = new ProductServiceImpl();
		
		
		ProductVO productVO = service.getProduct(Integer.parseInt(request.getParameter("prod_no")));
		
		System.out.println(productVO);
		
		request.setAttribute("productVO", productVO);
		return "forward:/purchase/addPurchaseView.jsp";
	}

	@Override
	public String toString() {
		return "AddPurchaseViewAction [] + 생성";
	}
	
	
	

}
