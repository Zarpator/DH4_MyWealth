<%-- 
    Document   : profile_edit
    Created on : 01.04.2019, 08:56:31
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
        
        <div class="menuitem">
            <a href="<c:url value="/app/profile/"/>">zurück zum Profil</a>
        </div>
    </jsp:attribute>

    <jsp:attribute name="content">
        <div class="container">
            <form method="post" class="stacked">
                <div class="column">
                  <%-- CSRF-Token --%>
                    <input type="hidden" name="csrf_token" value="${csrf_token}">

               
                               <%-- Eingabefelder --%>
                  <div class="entry">             
                    <label for="userdata_username">
                        Benutzername:
                       
                    </label>
                   <div>
                        <label type="text" name="label_username" class="textFormat">
                            ${userdata_form.values["username"][0]}
                        </label>
                        <input id="username_field" name="username" value="${userdata_form.values["username"][0]}" type="hidden" />
                    </div>    
                  </div> 
                  
                  <div class="entry" class="edit">   
                    <label for="userdata_firstname">
                        Vorname:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="firstname" value="${userdata_form.values["firstname"][0]}">
                    </div>
                  </div> 
                    
                   <div class="entry" class="edit">  
                    <label for="userdata_lastname">
                        Nachname:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="text" name="lastname" value="${userdata_form.values["lastname"][0]}">
                    </div>
                   </div> 
                    
                     <div class="entry" class="edit"> 
                    <%-- Button zum Abschicken --%>
                    <div class="side-by-side">
                        <button type="submit" name="action" value="changeData" class="icon-pencil">
                            Änderungen speichern
                        </button>
                    </div>
                    </div>
                    
                    
                    
                      <%-- Fehlermeldungen --%>
                <c:if test="${!empty userdata_form.errors}">
                    <ul class="errors">
                        <c:forEach items="${userdata_form.errors}" var="error">
                            <li>${error}</li>
                            </c:forEach>
                    </ul>
                </c:if>
                    
                    
                    
                    <div class="entry" class="edit">  
                    <label>
                        Passwort ändern:
                        <span class="required"></span>
                    </label>
                    </div>    

                   <div class="entry" class="edit"> 
                    <label for="userdata_password1">
                        neues Passwort:
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="password1" value="${userdata_form.values["password1"][0]}">
                    </div>
                   </div> 

                   <div class="entry" class="edit"> 
                    <label for="signup_password2">
                        Passwort (wdh.):
                        <span class="required">*</span>
                    </label>
                    <div class="side-by-side">
                        <input type="password" name="password2" value="${userdata_form.values["password2"][0]}">
                    </div>
                   </div>
                    
                   <div class="entry" class="edit"> 
                    <%-- Button zum Abschicken --%>
                    <div class="side-by-side">
                        <button type="submit" name="action" value="changePassword" class="icon-pencil">
                            neues Passwort sichern
                        </button>
                    </div>
                    </div>
                    
                   
                </div>
                    
               
            </form>
        </div>
    </jsp:attribute>
</template:base>
