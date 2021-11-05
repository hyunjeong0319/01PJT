<%@ page contentType="text/html; charset=EUC-KR" %>

<html>
<head>

<title>열어본 상품 보기</title>

</head>
<body>
	당신이 열어본 상품을 알고 있다
<br>
<br>
<%
	request.setCharacterEncoding("euc-kr");
	response.setCharacterEncoding("euc-kr");
	String history = null;
	Cookie[] cookies = request.getCookies();
	if (cookies!=null && cookies.length > 0) {
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (cookie.getName().equals("history")) {//cookie에서 history 라는 애를 가져오기 위한 과정
				history = cookie.getValue();
			}
		}
		if (history != null) {
			String[] h = history.split(","); //history에 해당하는 값이 있으면 , parsing을 해주는데 split은 h라는 array로 반환을 해준다.
			for (int i = 0; i < h.length; i++) {
				if (!h[i].equals("null")) { //끝까지 돈 후 없으면 밑에 있는걸 실행하겠다 가 아니고 h의 array가 null이 아니면 출력하겠다. 
%>
<a href="/getProduct.do?prodNo=<%=h[i]%>&menu=search"	target="rightFrame"><%=h[i]%></a>
<br>
<%
				}
			}
		}
	}
%>

</body>
</html>