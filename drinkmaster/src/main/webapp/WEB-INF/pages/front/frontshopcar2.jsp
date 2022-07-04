<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />
<jsp:include page="layout/header.jsp"></jsp:include>


<head>
<meta charset="utf-8" />
<title>購物車</title>
<link rel="stylesheet" href="<c:url value="/css/lib/bootstrap.min.css"/>"/>
<link rel="stylesheet" href="<c:url value="/css/lib/font-awesome.min.css"/>"/>

<style type="text/css">
.table>tbody>tr>td, .table>tfoot>tr>td{
    vertical-align: middle;
}
@media screen and (max-width: 600px) {
    table#cart tbody td .form-control{
		width:20%;
		display: inline !important;
	}
	.actions .btn{
		width:36%;
		margin:1.5em 0;
	}
	
	.actions .btn-info{
		float:left;
	}
	.actions .btn-danger{
		float:right;
	}
	
	table#cart thead { display: none; }
	table#cart tbody td { display: block; padding: .6rem; min-width:320px;}
	table#cart tbody tr td:first-child { background: #333; color: #fff; }
	table#cart tbody td:before {
		content: attr(data-th); font-weight: bold;
		display: inline-block; width: 8rem;
	}
	
	
	
	table#cart tfoot td{display:block; }
	table#cart tfoot td .btn{display:block;}
	
}

</style>
</head>




<body>
<div class="container">
<form action="${contextRoot}/front/shopcar/writeData" method="post">
<%-- <form:form class="form" method="post" action="${contextRoot}/front/shopcar/buy" modelAttribute="shopcarBean"> --%>
<%-- 	<input type="text" value="${canSeeUser.userName}"> --%>
	<table id="cart" class="table table-hover table-condensed">
    				<thead>
						<tr>
							<th style="width:150px;text-align: center;" colspan="2">商品</th>
<!-- 							<th style="width:80px;text-align: center;"></th> -->
							<th style="width:30px;text-align: center;">單價</th>
							<th style="width:100px;text-align: center;">數量</th>
							<th style="width:30px;text-align: center;">甜度</th>
							<th style="width:30px;text-align: center;">冷熱</th>
							<th style="width:80px;text-align: center;" class="text-center">總金額</th>
							<th style="width:20px"></th>
						</tr>
					</thead>
					<tbody id="tbody">
					<c:forEach varStatus="vs" var="shopcar" items="${shopcarBuy}">
						<tr>
							<td data-th="Product">
									<div class="col-sm-2 hidden-xs">
									<img src="" style="width: 300px;text-align: center;" /></div>
									<div class="col-sm-10">
									
										
									</div>
							</td>
							<td style="font-size: 18px;">
								<input type="text" id="productname" value="${shopcar.value.productName}" style="text-align: center;border-style:none;" readonly="true">
							</td>
							
							<td data-th="Price" >
							<input type="text" id="price" value="${shopcar.value.price}" style="width:50px;text-align: center;border-style:none;" readonly="true">
							</td>
							<td data-th="Quantity">
								<input type="number" id="number" name="number" class="form-control text-center" value="${shopcarBuy.quantity}" min="1">
							</td> 
<!-- 							<td data-th="Product" style="text-align: center;" > -->
<%-- 							<form:select id="sugar" path="shopcarBuy.sweet"> --%>
<%-- 							<form:option value="無糖">無糖</form:option> --%>
<%-- 							<form:option value="微糖">微糖</form:option> --%>
<%-- 							<form:option value="少糖">少糖</form:option> --%>
<%-- 							<form:option value="半糖">半糖</form:option> --%>
<%-- 							<form:option value="正常">正常</form:option> --%>
<%-- 							</form:select>							 --%>
<!-- 							</td> -->
<!-- 							<td data-th="Product" style="text-align: center;"> -->
<%-- 							<form:select id="coldhot" path="shopcarBuy.coldhot"> --%>
<%-- 							<form:option value="冷">冷</form:option> --%>
<%-- 							<form:option value="熱">熱</form:option> --%>
<%-- 							</form:select>							 --%>
<!-- 							</td> -->
							<td data-th="Subtotal" class="text-center" >
								<input type="text" id="totalprice" value="${shopcar.value.totalPrice}" style="width:100px;text-align: center;border-style:none;" readonly="true">
							</td>
							<td class="actions" data-th="">

								<button class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i></button>								
							</td>
						</tr>
						</c:forEach>
					</tbody>
					<tfoot>
						<tr class="visible-xs">
<!-- 						備註:折扣碼前的價格 -->
							<td class="text-center" id="total"><strong>Total <c:out value="${shopcar.value.totalPrice}"></c:out></strong></td>
						</tr>
						<tr>
							<td><a href="#" class="btn btn-warning">
							<i class="fa fa-angle-left"></i> 
							&thinsp;繼續購買</a></td>
							<td colspan="2">折扣碼:&thinsp;<input type="text" ></td>
							<td  class="hidden-xs"></td>
							
<!-- 							備註:折扣碼後的價格 -->
							<td class="hidden-xs text-center" colspan="2">
							<span style="color:red;font-weight: bold;">折扣後&ensp;</span>
							<strong>Total <input type="text" id="totalprice2" value="${shopcar.value.totalPrice}" style="width:100px;text-align: center;border-style:none;" readonly="true"></strong></td>
							<td colspan="2"><button type="submit" class="btn btn-success btn-block">結帳&thinsp;><i class="fa fa-angle-right"></i></button></td>
						</tr>
					</tfoot>
				</table>
				</form>
<%-- 				</form:form> --%>
</div>
</body>

<script type="text/javascript">
$(function(){
	

	$('#tbody').mouseover(function(){
		$('#totalprice').css("background-color","rgb(218, 218, 218)");
		$('#price').css("background-color","rgb(218, 218, 218)");
		$('#productname').css("background-color","rgb(218, 218, 218)");
	})

	
	$('#tbody').mouseout(function(){
		$('#totalprice').css("background-color","white");
		$('#price').css("background-color","white");
		$('#productname').css("background-color","white");
	})
	
	
	$('#number').click(function(){				
        var price = $('#price').val();      
        var number = $('#number').val();
        var totalprice = price*number;

        $('#totalprice').attr('value',totalprice);
    })
    
    $('#number').keyup(function(){				
        var price = $('#price').val();      
        var number = $('#number').val();
        var totalprice = price*number;

        $('#totalprice').attr('value',totalprice);       	
    })
    
    $('#number').click(function(){				
        var price = $('#price').val();      
        var number = $('#number').val();
        var totalprice = price*number;

        $('#totalprice2').attr('value',totalprice);
    })
    
    $('#number').keyup(function(){				
        var price = $('#price').val();      
        var number = $('#number').val();
        var totalprice = price*number;

        $('#totalprice2').attr('value',totalprice);       	
    })
    
	$('#number').click(function(){				
        var price = $('#price').val();      
        var number = $('#number').val();
        var totalprice = price*number;

        $('#total').html("<strong>Total " + totalprice + "</strong>");
    })
    
    $('#number').keyup(function(){				
        var price = $('#price').val();      
        var number = $('#number').val();
        var totalprice = price*number;

        $('#total').html(totalprice);      	
    })
	
})
</script>

<script src="<c:url value="/js/lib/bootstrap.min.js"/>"></script>
<script src="<c:url value="/js/lib/jquery.min.js"/>"></script>

<jsp:include page="layout/footer.jsp"></jsp:include>