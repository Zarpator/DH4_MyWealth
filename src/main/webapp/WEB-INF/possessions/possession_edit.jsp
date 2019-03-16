<%-- 
    Document   : possession_edit
    Created on : Mar 16, 2019, 4:11:48 PM
    Author     : Jonas Strube

    Dieser Quellcode ist lizenziert unter einer
    Creative Commons Namensnennung 4.0 International Lizenz.
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        <c:choose>
            <c:when test="${edit}">
                Besitztum bearbeiten
            </c:when>
            <c:otherwise>
                Besitztum hinzufügen
            </c:otherwise>
        </c:choose>
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/possession_list.css"/>" />
    </jsp:attribute>
        
    <jsp:attribute name="content">
        
    </jsp:attribute>
</template:base>
