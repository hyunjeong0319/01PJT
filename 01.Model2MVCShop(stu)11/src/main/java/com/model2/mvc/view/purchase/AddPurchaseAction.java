package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.vo.UserVO;

public class AddPurchaseAction extends Action {
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		PurchaseVO purchaseVO = new PurchaseVO();
		System.out.println(request.getRequestedSessionId());
		
		HttpSession session = request.getSession(true);
		
		System.out.println(request.getParameter("prodNo"));
		UserVO userVO = (UserVO)session.getAttribute("user");
		
		ProductService productService = new ProductServiceImpl();
		ProductVO productVO = productService.getProduct(Integer.parseInt(request.getParameter("prodNo")));
		
		System.out.println(userVO);
		System.out.println(productVO);
		//purchaseVO.setTranNo(Integer.parseInt(request.getParameter("tranNo")));
		purchaseVO.setBuyer(userVO);
		purchaseVO.setPurchaseProd(productVO);
		purchaseVO.setTranCode(request.getParameter("tranCode"));
		purchaseVO.setPaymentOption(request.getParameter("paymentOption"));
		purchaseVO.setReceiverName(request.getParameter("receiverName"));
		purchaseVO.setDivyAddr(request.getParameter("divyAddr"));
		purchaseVO.setDivyDate(request.getParameter("divyDate"));
		purchaseVO.setBuyer(userVO);
		//purchaseVO.setBuyer;
		System.out.println("purchaseVO:" + purchaseVO);
		
		PurchaseService purchaseService = new PurchaseServiceImpl();
		
		purchaseService.addPurchase(purchaseVO);
		
		request.setAttribute("purchaseVO",purchaseVO);
		System.out.println("purchaseVO:"+request);
		 
		return "forward:/purchase/addPurchase.jsp";
	}

}
