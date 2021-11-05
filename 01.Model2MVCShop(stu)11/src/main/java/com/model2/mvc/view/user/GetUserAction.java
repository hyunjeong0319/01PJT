package com.model2.mvc.view.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;


public class GetUserAction extends Action{

	@Override
	public String execute(	HttpServletRequest request,
												HttpServletResponse response) throws Exception {
		String userId=request.getParameter("userId");
		//response의 의미는 client의 요청을 한다는 의미 
		//response의 sendredirect 를 통해 자동 parsing 을 통해 경로와 parameter를 포함해서 가져감 
		//UserId 는 get으로 간다. 
		//뭔가를 보낼 때 session을 통해 가면 information hiding 이 가능해진다.
		
		UserService service=new UserServiceImpl();
		UserVO vo=service.getUser(userId);
		
		request.setAttribute("vo", vo);

		return "forward:/user/readUser.jsp";
	}
}