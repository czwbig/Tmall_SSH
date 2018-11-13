<%--
  Created by IntelliJ IDEA.
  User: czwbig
  Date: 2018/11/6
  Time: 21:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" isELIgnored="false" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="searchProducts">

    <c:forEach items="${products}" var="p">
        <div class="productUnit" price="${p.promotePrice}">
            <a href="foreproduct?product.id=${p.id}">
                <img class="productImage" src="img/productSingle/${p.firstProductImage.id}.jpg">
            </a>
            <span class="productPrice">¥<fmt:formatNumber type="number" value="${p.promotePrice}"
                                                          minFractionDigits="2"/></span>
            <a class="productLink" href="foreproduct?product.id=${p.id}">
                    ${fn:substring(p.name, 0, 50)}
            </a>

            <a class="tmallLink" href="foreproduct?product.id=${p.id}">天猫专卖</a>

            <div class="show1 productInfo">
                <span class="monthDeal ">月成交 <span class="productDealNumber">${p.saleCount}笔</span></span>
                <span class="productReview">评价<span class="productReviewNumber">${p.reviewCount}</span></span>
                <span class="wangwang"><img src="img/site/wangwang.png"></span>
            </div>

        </div>
    </c:forEach>
    <c:if test="${empty ps}">
    <div class="noMatch">没有满足条件的产品
        <div>
            </c:if>

            <div style="clear:both"></div>
        </div>
    </div>
</div>