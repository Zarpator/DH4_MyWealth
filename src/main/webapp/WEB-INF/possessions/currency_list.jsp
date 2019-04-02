<%-- 
    Document   : currency_list
    Created on : 01.04.2019, 12:54:25
    Author     : davidscheid
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<template:base>
    <jsp:attribute name="title">
        Währungen verwalten
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/currency_view.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">Dashboard</a>
        </div>

    </jsp:attribute>

    <jsp:attribute name="content">
        <form method="post" class="stacked">
            <%-- CSRF-Token --%>
            <input type="hidden" name="csrf_token" value="${csrf_token}">

            <%-- Feld zum Anlegen einer neuen Währung --%>
            <div class="column margin">
                <label class="headerForm" for="j_username">Neue Währung:</label>
                <input type="text" name="name" value="${currency_form.values["name"][0]}">
                
                <label class="headerForm" for="j_currency">Umrechnungsfaktor (Eine Einheit dieser Währung entsprechen ... Euro):</label>
                <input type="text" name="conversionRate" value="${currency_form.values["conversionRate"][0]}">
                
                <br/>
                
                <button type="submit" name="action" value="create" class="icon-pencil">
                    Anlegen
                </button>
                
            </div>
             
                 <br/>
                 
            <%-- Fehlermeldungen --%>
            <c:if test="${!empty currency_form.errors}">
                <ul class="errors margin">
                    <c:forEach items="${currency_form.errors}" var="error">
                        <li>${error}</li>
                        </c:forEach>
                </ul>
            </c:if>
            
             <br/>

            <%-- Vorhandene Anlagetypen --%>
            <c:choose>
                <c:when test="${empty currencies}">
                    <p>
                        Es sind noch keine Währungen vorhanden. 🐏
                    </p>
                </c:when>
                <c:otherwise>
                    
                    <div>
                        <div>
                            <table>
                                <thead>
                                <tr>
                                    <th></th>
                                    <th class="headerTable">Währung</th>
                                    <th class="headerTable">Umrechnungsfaktor</th>                             
                                </tr>
                                </thead>                              
                            
                            <c:forEach items="${currencies}" var="currency">
                                <tr>
                                    <td>
                                        <input type="checkbox" name="currency" id="${'currency-'.concat(currency.id)}" value="${currency.id}" />
                                    </td>
                                    <td>
                                       <label for="${'currency-'.concat(currency.id)}">
                                           <c:out value="${currency.name}"/>
                                       </label> 
                                    </td>
                                    <td>
                                        <label for="${'currency-'.concat(currency.id)}">
                                            <c:out value="${currency.conversionRate}"/>
                                        </label>
                                    </td>                         
                                   
                                </tr>
                            </c:forEach>
                                
                           </table>
                        </div>
                        <br/>

                        <button type="submit" name="action" value="delete" class="icon-trash">
                            Markierte löschen
                        </button>
                    </div>
                </c:otherwise>
            </c:choose>
        </form>
    </jsp:attribute>
</template:base>
