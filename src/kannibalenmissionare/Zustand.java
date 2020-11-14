package kannibalenmissionare;

import java.util.Objects;

public class Zustand {

    private final int anzahlMissionareLinks;
    private final int anzahlKannibalenLinks;
    private final int anzahlMissionareRechts;
    private final int anzahlKannibalenRechts;
    private final String positionBoot;
    private final Zustand vorgaenger;

    /**
     * Konstruktor fuer einen Zustand der Situation
     *
     * @param anzahlMissionareLinks ...
     * @param anzahlKannibalenLinks ...
     * @param anzahlMissionareRechts ...
     * @param anzahlKannibalenRechts ...
     * @param positionBoot ...
     * @param vorgaenger Hier muss null uebergeben werden, wenn es sich um den Startzustand handelt, ansonsten der
     *                  Zustand, dessen Nachfolger der neu zu erstellende Zustand sein soll
     */
    public Zustand(int anzahlMissionareLinks, int anzahlKannibalenLinks, int anzahlMissionareRechts, int anzahlKannibalenRechts, String positionBoot, Zustand vorgaenger) {
        this.anzahlMissionareLinks = anzahlMissionareLinks;
        this.anzahlKannibalenLinks = anzahlKannibalenLinks;
        this.anzahlMissionareRechts = anzahlMissionareRechts;
        this.anzahlKannibalenRechts = anzahlKannibalenRechts;
        this.positionBoot = positionBoot;
        this.vorgaenger = vorgaenger;
    }

    public int getAnzahlMissionareLinks() {
        return anzahlMissionareLinks;
    }

    public int getAnzahlKannibalenLinks() {
        return anzahlKannibalenLinks;
    }

    public int getAnzahlMissionareRechts() {
        return anzahlMissionareRechts;
    }

    public int getAnzahlKannibalenRechts() {
        return anzahlKannibalenRechts;
    }

    public String getPositionBoot() {
        return positionBoot;
    }

    @Override
    public String toString() {
        return "(" + anzahlMissionareLinks +
                "," + anzahlKannibalenLinks +
                "," + anzahlMissionareRechts +
                "," + anzahlKannibalenRechts +
                "," + positionBoot + ")";
    }

    /*
     * equals muss ueberschrieben werden, da zwei Zustaende als gleich angesehen werden, wenn sie dieselben Attribute
     * besitzen
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Zustand zustand = (Zustand) o;
        return anzahlMissionareLinks == zustand.anzahlMissionareLinks &&
                anzahlKannibalenLinks == zustand.anzahlKannibalenLinks &&
                anzahlMissionareRechts == zustand.anzahlMissionareRechts &&
                anzahlKannibalenRechts == zustand.anzahlKannibalenRechts &&
                positionBoot.equals(zustand.positionBoot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(anzahlMissionareLinks, anzahlKannibalenLinks, anzahlMissionareRechts, anzahlKannibalenRechts, positionBoot);
    }

    public Zustand getVorgaenger() {
        return vorgaenger;
    }
}
