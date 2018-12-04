<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <%--  注意：其中的2是指停留2秒钟后自动刷新到URL网址。 --%>
    <%--方式2：跳转到主页--%>
    <meta http-equiv="Refresh" content="0;URL=${APP_PATH }/index.htm"> 

<title>Insert title here</title>
</head>
<body>
index
<%--<a href="${APP_PATH}/index.htm">test</a>--%>
<%--方式1：跳转到主页--%>
<%--<jsp:forward page="${APP_PATH }/index.htm"></jsp:forward>--%>
<%--<a href="${pageContext.request.contextPath }/test.do">test</a>--%>
</body>
</html>