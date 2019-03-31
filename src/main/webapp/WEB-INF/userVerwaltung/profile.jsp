<%-- 
    Document   : profile
    Created on : 31.03.2019, 23:05:19
    Author     : timba
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib tagdir="/WEB-INF/tags/templates" prefix="template"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<c:set var="base_url" value="<%=request.getContextPath()%>" />

<template:base>
    <jsp:attribute name="title">
        Profilverwaltung
    </jsp:attribute>

    <jsp:attribute name="head">
        <link rel="stylesheet" href="<c:url value="/css/login.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">zurück zum Dashboard</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                <div class="column">
                  <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">

                <div>
                    <label for="username">
                        Benutzername:
                        
                    </label>
                    
                        <label type="text" name="username">
                            <c:out value="${user.username}"/>
                        </label>
                </div>
                        
                <div>
                    <label for="firstname">
                        Vorname:
                        
                    </label>
                    
                        <label type="text" name="vorname">
                            <c:out value="${user.firstname}"/>
                        </label>
                </div>
                        
                <div>
                    <label for="lastname">
                        Nachname:
                        
                    </label>
                    
                        <label type="text" name="lastname">
                            <c:out value="${user.lastname}"/>
                        </label>
                </div>        
                 
                 <div>
                    <label for="password">
                        Passwort:
                        
                    </label>
                    
                        <label type="text" name="username">
                            <c:out value="${user.password}"/>
                        </label>
                </div>       
                    
                    
               
                    <%-- Button zum Abschicken --%>
                    <div class="side-by-side">
                        <button class="icon-pencil" type="submit">
                            Editieren
                        </button>
                    </div>
                </div>
                    
               
                 
                 <c:choose>
                     <c:when test="${empty edit_mode}">
                          Hier sollte noch nichts stehen
                     </c:when>
                    <c:otherwise>
                              <%-- Eingabefelder --%>
                    <label for="userdata_username">
                        Benutzername:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="userdata_username" value="${user.username}">
                    </div>
                    
                    <label for="userdata_firstname">
                        Vorname:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="userdata_firstname" value="${user.firstname}">
                    </div>
                    
                    <label for="userdata_lastname">
                        Nachname:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="userdata_lastname" value="${user.lastname}">
                    </div>

                    <label for="userdata_password1">
                        neues Passwort:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="signup_password1" value="${userdata_form.values["signup_password1"][0]}">
                    </div>

                    <label for="signup_password2">
                        Passwort (wdh.):
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="signup_password2" value="${userdata_form.values["signup_password2"][0]}">
                    </div>
                     </c:otherwise>
                </c:choose>

                <%-- Fehlermeldungen --%>
                <c:if test="${!empty signup_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${signup_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
            </form>
        </div>
    </jsp:attribute>
</template:base>