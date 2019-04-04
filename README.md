MyWealth
=========================

Kurzbeschreibung
----------------

Hast du dich nicht auch schonmal so hilflos gefühlt? Deine Autos, deine Häuser, deine wertvollsten Besitztümer – in solch einer Anzahl, dass dir sogar die Garage ausgeht? Du überblickst nichts mehr und vergisst zum Teil ganze Villen am Straßenrand - und bemerkst, dass du einfach zu wohlhabend für dein Gehirn bist – wir verstehen dich, das ist doch jedem schonmal passiert.


Die MyWealth Webapp dient allen Menschen dazu, ihren Reichtum in Form von Sachanlagen besser zu überblicken. Mit dieser Anwendung wird es möglich sein, seine Immobilien, seine Luxusuhren, seinen Fuhrpark oder den Marktwert anderer hochpreisiger Sachanlagen persistent zu speichern und mithilfe eines Dashboards übersichtlich zu verwalten. 

Über da Dashboard ist es zum einen möglich, Besitztümer in die Verwaltungsanwendung aufzunehmen. Jedes Besitztum ist einem Anlagentyp zugeordnet. Die Anlagetypen sind dynamisch verwaltbar und können direkt nach der Registrierung angelegt werden. Da die Kunden der MyWealth-App ihre Anlagen auf der ganzen Welt verteilt haben, ist es unsere Pflicht, jedem Anlagentyp eine andere Währung zu ermöglichen. So können die integrierten Währungen ebenfalls dynamisch verwaltet werden – Euro ist dabei schon standardmäßig implementiert. Jeder Anlagentyp kann somit bei Erstellung eine Standardwährung zugewiesen werden. Wird ein Besitz angelegt wird diesem wiederum einen Anlagentyp und einen Wert in Standardwährung zugewiesen.

Der Nutzer kann nun über das Dashboard seine Besitztümer miteinander vergleichen. Die einzelnen Dashboard-Kacheln repräsentieren Anlagetypen, und agieren als Filteroption. Über eine Kachel gelangt der Nutzer auf die jeweilige Liste, in der seine Besitztümer aufgelistet sind – und diese Liste ist je nach Kachel nach einem bestimmten Anlagetyp gefiltert. Über die Kachel „Alle“ sieht der Nutzer natürlich ganz ungefiltert sein gesamtes Vermögen. Die Besitztümer werden über einen Umrechnungsfaktor, den man bei der Währungserstellung angibt, alle auf eine Einheit (Euro) umgerechnet, sodass der Nutzer seine Besitztümer natürlich Länder und Währungsübergreifend miteinander vergleichen kann. Die Besitztümer können selbstverständlich bearbeitet und gelöscht werden, denn sie bilden das Herz der MyWealth-Verwaltungsanwendung.

### Die Client Anwendung: 

Die MyWealth-Webservice-Testmaschine ist eine simple Client-Anwendung. Nachdem jegliche Versuche fehlschlugen, den über Authentifizierungsmechanismen abgesicherte Webservice zu konsumieren, haben wir nun doch eine Lösung gefunden. Um den Webservice dennoch mit einer FORM-Authentifizierung abzusichern haben wir uns für die spartanischste Variante aller Client-Anwendungen entschieden, entspricht aber dennoch allen gestellten Anforderungen an die Funktionen der Anwendung. Auf einer HTML Seite werden dem Nutzer verschiedene Selektions-Möglichkeiten des Rest-Services gegeben. Wird eine Selektions-Variante gewählt, werden die Daten des Webservices im Browser dargestellt. Über Vorwärts und Rückwärts-Navigation im Browser bleibt der Nutzer dennoch im Kontext der Client-Anwendung. Die Anwendung liegt im Ordner „ClientAnwendung“ – probier es einfach mal aus.


Verwendete Technologien
-----------------------

Die App nutzt Maven als Build-Werkzeug und zur Paketverwaltung. Auf diese Weise
werden die für Jakarta EE notwendigen APIs, darüber hinaus aber keine weiteren
Abhängigkeiten, in das Projekt eingebunden. Der Quellcode der Anwendung ist dabei
wie folgt strukturiert:

 * **Servlets** dienen als Controller-Schicht und empfangen sämtliche HTTP-Anfragen.
 * **Enterprise Java Beans** dienen als Model-Schicht und kapseln die fachliche Anwendungslogik.
 * **Persistence Entities** modellieren das Datenmodell und werden für sämtliche Datenbankzugriffe genutzt.
 * **Java Server Pages** sowie verschiedene statische Dateien bilden die View und generieren den
   auf dem Bildschirm angezeigten HTML-Code.

Folgende Entwicklungswerkzeuge kommen dabei zum Einsatz:

 * [NetBeans:](https://netbeans.apache.org/) Integrierte Entwicklungsumgebung für Java und andere Sprachen
 * [Maven:](https://maven.apache.org/) Build-Werkzeug und Verwaltung von Abhängigkeiten
 * [Git:](https://git-scm.com/") Versionsverwaltung zur gemeinsamen Arbeit am Quellcode
 * [TomEE:](https://tomee.apache.org/) Applikationsserver zum lokalen Testen der Anwendung
 * [Derby:](https://db.apache.org/derby/) In Java implementierte SQL-Datenbank zum Testen der Anwendung

Copyright
---------

Dieses Projekt ist lizenziert unter
[_Creative Commons Namensnennung 4.0 International_](http://creativecommons.org/licenses/by/4.0/)

© 2019 David Scheid, Jonas Strube, Tim Bayer <br/>

