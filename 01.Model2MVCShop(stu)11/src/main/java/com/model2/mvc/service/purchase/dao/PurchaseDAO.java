package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class PurchaseDAO {

	public PurchaseDAO() {
		// TODO Auto-generated constructor stub
	}
	
	public void addPurchase(PurchaseVO purchaseVO) throws Exception{
		Connection con=DBUtil.getConnection();
		System.out.println("Purchase DAO : 진입");
		String sql = "insert into transaction "
				+ "(tran_no , prod_no, buyer_id, payment_option,receiver_name, receiver_phone, demailaddr,dlvy_request,tran_status_code,order_data,dlvy_date)"
				+ "values(seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,sysdate,?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		System.out.println("purchaseVO:" + purchaseVO);
		stmt.setInt(1, purchaseVO.getPurchaseProd().getProdNo());
		stmt.setString(2,purchaseVO.getBuyer().getUserId());
		stmt.setString(3,purchaseVO.getPaymentOption());
		stmt.setString(4,purchaseVO.getReceiverName());
		stmt.setString(5,purchaseVO.getReceiverPhone());
		stmt.setString(6,purchaseVO.getDivyAddr());
		stmt.setString(7,purchaseVO.getDivyRequest());
		stmt.setString(8,purchaseVO.getTranCode());
//		stmt.setDate(9,purchaseVO.getOrderDate());
		stmt.setString(9,purchaseVO.getDivyDate());
		stmt.executeUpdate();
		
		System.out.println("db추가 완료");
		con.close();
	}
	
	public HashMap<String, Object> getPurchaseList(SearchVO searchVO, String buyerId) throws Exception{
		
		Connection con=DBUtil.getConnection();		
		System.out.println("getPurchaseList 진입 :DAO");
		String sql = "select * from transaction";
		
		if (searchVO.getSearchCondition() != null) {
			if (searchVO.getSearchCondition().equals("0") && !searchVO.getSearchKeyword().equals("null")) {
				sql += "where TRAN_NO like'%" + searchVO.getSearchKeyword()
						+ "%'";
			} else if (searchVO.getSearchCondition().equals("1")&& !searchVO.getSearchKeyword().equals("null")) {
				sql += "where PROD_NO like'%" + searchVO.getSearchKeyword()
						+ "%'";
			}
			else if(searchVO.getSearchCondition().equals("2")&& !searchVO.getSearchKeyword().equals("null")) {
				sql += "where tran_status_code like '%" + searchVO.getSearchKeyword()
				+ "%'";
			}
		}
		sql += " order by TRAN_NO";
		System.out.println(sql);
		
		PreparedStatement stmt = 
				con.prepareStatement(	sql,
															ResultSet.TYPE_SCROLL_INSENSITIVE,
															ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = stmt.executeQuery();
			
			rs.last();
			int total = rs.getRow();
			System.out.println("로우의 수:" + total);

			HashMap<String,Object> map = new HashMap<String,Object>();
			map.put("count", new Integer(total));
		
			rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
			System.out.println("searchVO.getPage():" + searchVO.getPage());
			System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());
			
			ArrayList<PurchaseVO> list = new ArrayList<PurchaseVO>();
			if (total > 0) {
				for (int i = 0; i < searchVO.getPageUnit(); i++) {
					PurchaseVO purchaseVO = new PurchaseVO();
					UserVO userVO = new UserVO();
					ProductVO productVO = new ProductVO();
					
					ProductService productService = new ProductServiceImpl();
					productVO = productService.getProduct(purchaseVO.getPurchaseProd().getProdNo());
					
					UserService userService = new UserServiceImpl();
					userVO = userService.getUser(buyerId);
					
					purchaseVO.setBuyer(userVO);
					purchaseVO.setDivyAddr(rs.getString("demailaddr"));
					purchaseVO.setDivyDate(rs.getDate("dlvy_date").toString());
					purchaseVO.setDivyRequest(rs.getString("dlvy_request"));
					purchaseVO.setOrderDate(rs.getDate("order_data"));
					purchaseVO.setPaymentOption(rs.getString("payment_option"));
					purchaseVO.setPurchaseProd(productVO);
					purchaseVO.setReceiverName(rs.getString("receiver_name"));
					purchaseVO.setReceiverPhone(rs.getString("receiver_phone"));
					purchaseVO.setTranCode(rs.getString("tran_status_code"));
					purchaseVO.setTranNo(rs.getInt("tran_no"));

					list.add(purchaseVO);
					if (!rs.next())
						break;
					
				}
			}
			System.out.println("list.size() : "+ list.size());
			map.put("list", list);
			System.out.println("map().size() : "+ map.size());

			con.close();
		
		return map;
		
		
	}

}
