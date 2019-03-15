<%-- 
    Document   : possessionType_list
    Created on : 15.03.2019, 12:54:25
    Author     : timba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Anlagetypen verwalten
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/category_list.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

        <div class="menuitem">
            <a href="<c:url value="/app/tasks/list/"/>">Liste</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <%-- CSRF-Token --%>
            <input type="hidden" name="csrf_token" value="${csrf_token}">

            <%-- Feld zum Anlegen eines neuen Anlagetyps --%>
            <div class="column margin">
                <label for="j_username">Neuer Anlagetyp:</label>
                <input type="text" name="name" value="${possessiontype_form.values["name"][0]}">

                <button type="submit" name="action" value="create" class="icon-pencil">
                    Anlegen
                </button>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty possessiontype_form.errors}">
                <ul class="errors margin">
                    <c:forEach items="${possessiontype_form.errors}" var="error">
                        <li>${error}</li>
                        </c:forEach>
                </ul>
            </c:if>

            <%-- Vorhandene Anlagetypen --%>
            <c:choose>
                <c:when test="${empty possessiontypes}">
                    <p>
                        Es sind noch keine Anlagetypen vorhanden. 🐏
                    </p>
                </c:when>
                <c:otherwise>
                    <div>
                        <div class="margin">
                            <c:forEach items="${possessiontypes}" var="possessiontype">
                                <input type="checkbox" name="category" id="${'possessiontype-'.concat(possessiontype.id)}" value="${possessiontype.id}" />
                                <label for="${'possessiontype-'.concat(possessiontype.id)}">
                                    <c:out value="${possessiontype.name}"/>
                                </label>
                                <br />
                            </c:forEach>
                        </div>

                        <button type="submit" name="action" value="delete" class="icon-trash">
                            Markierte löschen
                        </button>
                    </div>
                </c:otherwise>
            </c:choose>
        </form>
    </jsp:attribute>
</template:base>
