<%@ page contentType="text/html; charset=EUC-KR" %>

<html>
<head>

<title>��� ��ǰ ����</title>

</head>
<body>
	����� ��� ��ǰ�� �˰� �ִ�
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
			if (cookie.getName().equals("history")) {//cookie���� history ��� �ָ� �������� ���� ����
				history = cookie.getValue();
			}
		}
		if (history != null) {
			String[] h = history.split(","); //history�� �ش��ϴ� ���� ������ , parsing�� ���ִµ� split�� h��� array�� ��ȯ�� ���ش�.
			for (int i = 0; i < h.length; i++) {
				if (!h[i].equals("null")) { //������ �� �� ������ �ؿ� �ִ°� �����ϰڴ� �� �ƴϰ� h�� array�� null�� �ƴϸ� ����ϰڴ�. 
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