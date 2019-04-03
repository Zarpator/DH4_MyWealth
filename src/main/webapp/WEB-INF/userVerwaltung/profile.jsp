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
        <link rel="stylesheet" href="<c:url value="/css/profile.css"/>" />
    </jsp:attribute>

    <jsp:attribute name="menu">
        <div class="menuitem">
            <a href="<c:url value="/app/dashboard/"/>">zurück zum Dashboard</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container" >
            <form method="post" class="stacked">
          
                <div class="column">
                  <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">

                <div class="entry">
                    <label for="username">
                        Benutzername:
                        
                    </label>
                    <div>
                        <label type="text" name="username" class="textFormat">
                            <c:out value="${user.username}"/>
                        </label>
                    </div>    
                </div>
                        
                <div class="entry">
                    <label for="firstname">
                        Vorname:
                        
                    </label>
                    <div>
                        <label type="text" name="vorname" class="textFormat">
                            <c:out value="${user.firstname}"/>
                        </label>
                        </div>    
                </div>
                        
                <div class="entry">
                    <label for="lastname">
                        Nachname:
                        
                    </label>
                    <div>
                        <label type="text" name="lastname" class="textFormat">
                            <c:out value="${user.lastname}"/>
                        </label>
                    </div>    
                </div>        
                 
                 <div class="entry">
                    <label for="password">
                        Passwort:
                        
                    </label>
                     <div>
                        <label type="text" name="username" class="textFormat" >
                            Dein Passwort ist so gut verschlüsselt, da kommt nicht mal die NSA ran
                        </label>
                     </div>    
                </div>       
                    
                    
               
                    <%-- Link zum Weiterleien --%>
                    <div class="entry">
                        <br/>
                         <a href="<c:url value="/app/profile/edit"/>">Benutzerdaten ändern</a>
                    </div>
                </div>
                   
                    
                    
               
                 
                

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