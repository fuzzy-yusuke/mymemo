<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ page import="java.util.*" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-type" content="text/html" charset="UTF-8">
<title>Hello World!</title>
<style>body {padding: 30px}</style>
</head>
<body>
<h1>メモ一覧</h1>
<% String message=(String)request.getAttribute("message"); %>
<p><%=message %></p>

<%
	ArrayList<HashMap<String,String>>rows=(ArrayList<HashMap<String,String>>)request.getAttribute("rows");
%>

<table>
	<tr>
		<th>ID</th>
		<th>タイトル</th>
	</tr>
	<%
	for(HashMap<String,String> columns:rows){
	%>
		<tr>
			<td><%=columns.get("id") %></td>
			<td><a href='show?id=<%=columns.get("id") %>'><%=columns.get("title") %></a></td>
		</tr>
	<% } %>
</table>

<p></p>
<p><a href="new">新規メモ</a></p>

</body>
</html>