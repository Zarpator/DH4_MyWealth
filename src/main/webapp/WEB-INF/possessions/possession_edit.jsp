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
        <form method="post" class="stacked">
            <div class="column">
                <%-- CSRF-Token --%>
                <input type="hidden" name="csrf_token" value="${csrf_token}">
                
                <%-- Eingabefelder --%>                
                <label for="poss_name">Name:</label>
                <div class="side-by-side">
                    <input type="text" name="poss_name" value="${task_form.values["task_owner"][0]}">
                </div>

                <label for="task_category">Anlagetyp:</label>
                <div class="side-by-side">
                    <select name="poss_type">
                        <option value="">Kein Typ</option>

                        <c:forEach items="${poss_types}" var="type">
                            <option value="${type.id}">
                                <c:out value="${type.name}" />
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <label for="poss_value">Wert:</label>
                <div class="side-by-side">
                    <input type="text" name="poss_value" value="${task_form.values["task_owner"][0]}">
                </div>

                <label for="poss_comments">Kommentar:</label>
                <div class="side-by-side">
                    <input type="text" name="poss_comments" value="${task_form.values["task_owner"][0]}">
                </div>

                <%-- Button zum Abschicken --%>
                <div class="side-by-side">
                    <button class="icon-pencil" type="submit" name="action" value="save">
                        Speichern
                    </button>

                    <c:if test="${edit}">
                        <button class="icon-trash" type="submit" name="action" value="delete">
                            Löschen
                        </button>
                    </c:if>
                </div>
            </div>

            <%-- Fehlermeldungen --%>
            <c:if test="${!empty task_form.errors}">
                <ul class="errors">
                    <c:forEach items="${task_form.errors}" var="error">
                        <li>${error}</li>
                        </c:forEach>
                </ul>
            </c:if>
        </form>
    </jsp:attribute>
</template:base>
