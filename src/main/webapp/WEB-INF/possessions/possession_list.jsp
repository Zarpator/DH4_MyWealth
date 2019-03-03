<%-- 
    Document   : possessions_list
    Created on : Mar 3, 2019, 2:08:41 PM
    Author     : zarpator

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Liste der Besitztümer
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/possession_list.css"/>" />
    </jsp:attribute>
        
    <jsp:attribute name="content">
        <%-- Gefundene Besitztümer --%>
        <c:choose>
            <c:when test="${empty possessions}">
                <p>
                    Es wurden keine Besitztümer gefunden. 🐈
                </p>
            </c:when>
            <c:otherwise>
                <jsp:useBean id="utils" class="dhbwka.wwi.vertsys.javaee.mywealth.common.web.WebUtils"/>
                
                <table>
                    <thead>
                        <tr>
                            <th>Besitztum</th>
                            <th>Wert [in €]</th>
                        </tr>
                    </thead>
                    <c:forEach items="${possessions}" var="possession">
                        <tr>
                            <td>
                                <a href="<c:url value="/app/possessions/possession/${possession.id}/"/>">
                                    <c:out value="${possession.name}"/>
                                </a>
                            </td>
                            <td>
                                <c:out value="${possession.valueInEuro}"/>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:otherwise>
        </c:choose>
    </jsp:attribute>
</template:base>