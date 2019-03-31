/*
 * Copyright © 2019 Dennis Schulmeister-Zimolong
 * 
 * E-Mail: dhbw@windows3.de
 * Webseite: https://www.wpvs.de/
 * 
 * Dieser Quellcode ist lizenziert unter einer
 * Creative Commons Namensnennung 4.0 International Lizenz.
 */
package dhbwka.wwi.vertsys.javaee.mywealth.tasks.ejb;

import dhbwka.wwi.vertsys.javaee.mywealth.common.ejb.UserBean;
import dhbwka.wwi.vertsys.javaee.mywealth.common.web.WebUtils;
import dhbwka.wwi.vertsys.javaee.mywealth.dashboard.ejb.DashboardContentProvider;
import dhbwka.wwi.vertsys.javaee.mywealth.dashboard.ejb.DashboardSection;
import dhbwka.wwi.vertsys.javaee.mywealth.dashboard.ejb.DashboardTile;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.ejb.PossessionTypeBean;
import dhbwka.wwi.vertsys.javaee.mywealth.possessions.jpa.PossessionType;
import dhbwka.wwi.vertsys.javaee.mywealth.tasks.jpa.Category;
import dhbwka.wwi.vertsys.javaee.mywealth.tasks.jpa.TaskStatus;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * EJB zur Definition der Dashboard-Kacheln für Aufgaben.
 */
@Stateless(name = "tasks")
public class DashboardContent implements DashboardContentProvider {

    @EJB
    UserBean userBean;
    
    @EJB
    private CategoryBean categoryBean;
    
    @EJB
    PossessionTypeBean possessionTypeBean;

    @EJB
    private TaskBean taskBean;

    /**
     * Vom Dashboard aufgerufenen Methode, um die anzuzeigenden Rubriken und
     * Kacheln zu ermitteln.
     *
     * @param sections Liste der Dashboard-Rubriken, an die die neuen Rubriken
     * angehängt werden müssen
     */
    @Override
    public void createDashboardContent(List<DashboardSection> sections) {
        // Zunächst einen Abschnitt mit einer Gesamtübersicht aller Aufgaben
        // in allen Kategorien erzeugen
        DashboardSection section = this.createSection();
        sections.add(section);
    }

    /**
     * Hilfsmethode, die für die übergebene Aufgaben-Kategorie eine neue Rubrik
     * mit Kacheln im Dashboard erzeugt. Je Aufgabenstatus wird eine Kachel
     * erzeugt. Zusätzlich eine Kachel für alle Aufgaben innerhalb der
     * jeweiligen Kategorie.
     *
     * Ist die Kategorie null, bedeutet dass, dass eine Rubrik für alle Aufgaben
     * aus allen Kategorien erzeugt werden soll.
     *
     * @param category Aufgaben-Kategorie, für die Kacheln erzeugt werden sollen
     * @return Neue Dashboard-Rubrik mit den Kacheln
     */
    private DashboardSection createSection() {
        // Neue Rubrik im Dashboard erzeugen
        DashboardSection section = new DashboardSection();
        String cssClass = "";

        section.setLabel("Was möchten sie anschauen?");
       
         cssClass = "overview";
       

        // Eine Kachel für alle Aufgaben in dieser Rubrik erzeugen
        DashboardTile tile = this.createTile(new PossessionType(), "Alle", cssClass + " status-all", "calendar");
        section.getTiles().add(tile);
        
        List<PossessionType> possessionTypes = this.possessionTypeBean.findAllByUser(this.userBean.getCurrentUser());
        // Ja Aufgabenstatus eine weitere Kachel erzeugen
        for (PossessionType possessionType2 : possessionTypes) {
            String cssClass1 = cssClass + " status-open";
            String icon = "rocket";

         /*   switch (status) {
                case OPEN:
                    icon = "doc-text";
                    break;
                case IN_PROGRESS:
                    icon = "rocket";
                    break;
                case FINISHED:
                    icon = "ok";
                    break;
                case CANCELED:
                    icon = "cancel";
                    break;
                case POSTPONED:
                    icon = "bell-off-empty";
                    break;
            }*/

            tile = this.createTile(possessionType2, possessionType2.getName(), cssClass1, icon);
            section.getTiles().add(tile);
        }

        // Erzeugte Dashboard-Rubrik mit den Kacheln zurückliefern
        return section;
    }

    /**
     * Hilfsmethode zum Erzeugen einer einzelnen Dashboard-Kachel. In dieser
     * Methode werden auch die in der Kachel angezeigte Anzahl sowie der Link,
     * auf den die Kachel zeigt, ermittelt.
     *
     * @param category
     * @param status
     * @param label
     * @param cssClass
     * @param icon
     * @return
     */
    private DashboardTile createTile(PossessionType possessionType, String label, String cssClass, String icon) {
        int amount = possessionTypeBean.findAllByUser(this.userBean.getCurrentUser()).size();
        String href = "/app/possessions/list/";

        if (possessionType != null) {
            href = WebUtils.addQueryParameter(href, "search_possessionType", "" + possessionType.getId());
        }

       

        DashboardTile tile = new DashboardTile();
        tile.setLabel(label);
        tile.setCssClass(cssClass);
        tile.setHref(href);
        tile.setIcon(icon);
        tile.setAmount(amount);
        tile.setShowDecimals(false);
        return tile;
    }

}
