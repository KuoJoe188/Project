<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="layout/header.jsp" />
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />
<html>
<head>
<style>

</style>

</head>
<body>
<form action="${contextRoot}/backend/product/select" method="get">
  <div class="mb-3">
    <label for="exampleInputEmail1" class="form-label">搜尋</label>
    <input type="text" class="form-control" name="select" />
    
  </div>
  <button type="submit" class="btn btn-primary">Submit</button>
</form>
<table class="table table-hover"style="width:100%;table-layout:fixed;">
  <thead>
    <tr>
      <th scope="col">品項</th>
      <th scope="col">價格</th>
      <th scope="col">店家</th>
      <th scope="col">圖片</th>
      <th scope="col">溫度</th>
      <th scope="col">狀態</th>
      <th scope="col">功能</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach var="productBean" items="${page.content}">
    <tr>
      <th scope="row"><c:out value="${productBean.productName}"/></th>
      <td><c:out value="${productBean.price}"/></td>
      <td><c:out value="${productBean.storeBean.storeId}"/></td>
       <td><c:out value="${productBean.productImage}"/></td>
      <td><c:out value="${productBean.coldHot}"/></td>
      <td><c:out value="${productBean.status}"/></td>
      <td><a href="${contextRoot}/backend/editproduct?id=${productBean.productId}"><button class="btn btn-outline-primary">編輯</button></a>
        <a onclick="return confirm('真的要刪除嗎')"  href="${contextRoot}/backend/deleteproduct?id=${productBean.productId}"><button class="btn btn-outline-danger">刪除</button></a></td>
      </tr>
    </c:forEach>
 
  </tbody>
</table>
<div class="row justify-content-center" style="font-size: x-large;">
  <c:forEach var="pageNumber" begin="1" end="${page.totalPages}">
   <c:choose>
   <c:when test="${page.number!=pageNumber-1}">
   <a href="${contextRoot}/backend/product/all?p=${pageNumber}"> <c:out value="${pageNumber}" /> </a>
   </c:when>
   <c:otherwise>
   <c:out value="${pageNumber}"></c:out>
   </c:otherwise>
   </c:choose> 
   <c:if test="${pageNumber!= page.totalPages }">
   |
   </c:if>
   </c:forEach>
   </div>
   <div  class="row justify-content-center" style="font-size: large; color:black;">
   <c:out value="總共有 ${page.totalElements }筆資料"></c:out>
   </div>
</body>

</html>


<jsp:include page="layout/footer.jsp" />