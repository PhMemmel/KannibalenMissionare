package kannibalenmissionare;

import java.util.ArrayList;
import java.util.Stack;
import java.util.stream.Collectors;

public class KannibalenSolver {

    private final Zustand startZustand;
    private final Zustand endZustand;
    private final ArrayList<Zustand> queue;
    private final ArrayList<Zustand> zustaendeBesucht;

    public KannibalenSolver(Zustand startZustand, Zustand endZustand) {
        this.startZustand = startZustand;
        this.endZustand = endZustand;
        queue = new ArrayList<>();
        zustaendeBesucht = new ArrayList<>();
    }

    /*
     * hier werden ausgehend von einem Zustand alle moeglichen Zielzustaende bestimmt.
     *
     * TODO das geht sehr viel eleganter...
     */
    public ArrayList<Zustand> berechneZielzustaende(Zustand zustand) {
        ArrayList<Zustand> zustaende = new ArrayList<>();
        String neuePositionBoot = zustand.getPositionBoot().equals("links") ? "rechts" : "links";

        if (zustand.getPositionBoot().equals("links")) {
            zustaende.add(new Zustand(zustand.getAnzahlMissionareLinks() - 1, zustand.getAnzahlKannibalenLinks(),
                    zustand.getAnzahlMissionareRechts() + 1, zustand.getAnzahlKannibalenRechts(), neuePositionBoot, zustand));
            zustaende.add(new Zustand(zustand.getAnzahlMissionareLinks() - 2, zustand.getAnzahlKannibalenLinks(),
                    zustand.getAnzahlMissionareRechts() + 2, zustand.getAnzahlKannibalenRechts(), neuePositionBoot, zustand));
            zustaende.add(new Zustand(zustand.getAnzahlMissionareLinks(), zustand.getAnzahlKannibalenLinks() - 1,
                    zustand.getAnzahlMissionareRechts(), zustand.getAnzahlKannibalenRechts() + 1, neuePositionBoot, zustand));
            zustaende.add(new Zustand(zustand.getAnzahlMissionareLinks(), zustand.getAnzahlKannibalenLinks() - 2,
                    zustand.getAnzahlMissionareRechts(), zustand.getAnzahlKannibalenRechts() + 2, neuePositionBoot, zustand));
            zustaende.add(new Zustand(zustand.getAnzahlMissionareLinks() - 1, zustand.getAnzahlKannibalenLinks() - 1,
                    zustand.getAnzahlMissionareRechts() + 1, zustand.getAnzahlKannibalenRechts() + 1, neuePositionBoot, zustand));
            // Leerfahrt erlaubt:
            zustaende.add(new Zustand(zustand.getAnzahlMissionareLinks(), zustand.getAnzahlKannibalenLinks(),
                    zustand.getAnzahlMissionareRechts(), zustand.getAnzahlKannibalenRechts(), neuePositionBoot, zustand));
        } else {
            zustaende.add(new Zustand(zustand.getAnzahlMissionareLinks() + 1, zustand.getAnzahlKannibalenLinks(),
                    zustand.getAnzahlMissionareRechts() - 1, zustand.getAnzahlKannibalenRechts(), neuePositionBoot, zustand));
            zustaende.add(new Zustand(zustand.getAnzahlMissionareLinks() + 2, zustand.getAnzahlKannibalenLinks(),
                    zustand.getAnzahlMissionareRechts() - 2, zustand.getAnzahlKannibalenRechts(), neuePositionBoot, zustand));
            zustaende.add(new Zustand(zustand.getAnzahlMissionareLinks(), zustand.getAnzahlKannibalenLinks() + 1,
                    zustand.getAnzahlMissionareRechts(), zustand.getAnzahlKannibalenRechts() - 1, neuePositionBoot, zustand));
            zustaende.add(new Zustand(zustand.getAnzahlMissionareLinks(), zustand.getAnzahlKannibalenLinks() + 2,
                    zustand.getAnzahlMissionareRechts(), zustand.getAnzahlKannibalenRechts() - 2, neuePositionBoot, zustand));
            zustaende.add(new Zustand(zustand.getAnzahlMissionareLinks() + 1, zustand.getAnzahlKannibalenLinks() + 1,
                    zustand.getAnzahlMissionareRechts() - 1, zustand.getAnzahlKannibalenRechts() - 1, neuePositionBoot, zustand));
            // Leerfahrt erlaubt:
            zustaende.add(new Zustand(zustand.getAnzahlMissionareLinks(), zustand.getAnzahlKannibalenLinks(),
                    zustand.getAnzahlMissionareRechts(), zustand.getAnzahlKannibalenRechts(), neuePositionBoot, zustand));
        }
        return (ArrayList<Zustand>) zustaende.stream().filter((z) -> zustandErlaubt(z)).collect(Collectors.toList());
    }

    /*
     * Diese Methode ueberprueft separat, ob ein Zustand eine der Regeln verletzt, wie viele Kannibalen und Missionare gleichzeitig wo sein duerfen
     */
    private boolean zustandErlaubt(Zustand zustand) {
        if ((zustand.getAnzahlKannibalenLinks() > zustand.getAnzahlMissionareLinks() && zustand.getAnzahlMissionareLinks() != 0)
                || (zustand.getAnzahlKannibalenRechts() > zustand.getAnzahlMissionareRechts() && zustand.getAnzahlMissionareRechts() != 0)
                || zustand.getAnzahlMissionareRechts() > 3 || zustand.getAnzahlKannibalenRechts() > 3
                || zustand.getAnzahlMissionareLinks() > 3 || zustand.getAnzahlKannibalenLinks() > 3
                || zustand.getAnzahlKannibalenLinks() + zustand.getAnzahlKannibalenRechts() != 3
                || zustand.getAnzahlMissionareLinks() + zustand.getAnzahlMissionareRechts() != 3
                || (zustand.getAnzahlMissionareRechts() == 0 && zustand.getAnzahlKannibalenRechts() == 0 && zustand.getPositionBoot().equals("rechts"))
                || (zustand.getAnzahlMissionareLinks() == 0 && zustand.getAnzahlKannibalenLinks() == 0 && zustand.getPositionBoot().equals("links"))) {
            return false;
        } else {
            return true;
        }
    }


    /**
     * Der eigentliche Algorithmus, in Form einer - sicherlich etwas verhunzt programmierten - Breitensuche
     */
    public void breitenSuche() {

        if (startZustand == null) {
            System.out.println("Vorsicht, Startzustand ungueltig. Abbruch!");
            return;
        }


        Zustand zustand = startZustand;
        queue.add(zustand);

        while (!queue.isEmpty()) {

            zustand = queue.get(0);
            if (zustaendeBesucht.contains(zustand)) {
                queue.remove(zustand);
                continue;
            } else {
                zustaendeBesucht.add(zustand);
                queue.remove(zustand);
            }

            if (zustand.equals(endZustand)) {
                System.out.println("gefunden");
                zeigeKette(zustand);
                return;
            } else {
                ArrayList<Zustand> folgeZustaende = berechneZielzustaende(zustand);

                folgeZustaende = (ArrayList<Zustand>) folgeZustaende.stream().filter((z) -> !zustaendeBesucht.contains(z))
                        .filter((z)->!queue.contains(z)).collect(Collectors.toList());

                System.out.println("Folgezustaende von :" + zustand.toString());
                gibAus(folgeZustaende);


                if (folgeZustaende.isEmpty()) {
                    System.out.println("leer");
                    continue;
                } else {

                    for (Zustand kandidat : folgeZustaende) {
                        System.out.println("Kandidat: " + kandidat);
                        System.out.println("besucht: ");
                        System.out.println(zustaendeBesucht);
                        queue.add(kandidat);
                    }
                    System.out.println("Queue: ");
                    gibAus(queue);
                }
            }
        }
    }

    /**
     * Gib die zu waehlende Folge von Zustaenden aus
     *
     * @param zustand Endzustand, von dem aus rueckwaerts die Vorgaenger bestimmt werden
     */
    private void zeigeKette(Zustand zustand) {
        Stack<Zustand> ausgabeStack = new Stack<>();

        Zustand tmp = zustand;
        while (tmp!=null) {
            ausgabeStack.add(tmp);
            tmp = tmp.getVorgaenger();
        }

        System.out.println("\n\n=============================");
        System.out.println("Loesung des Problems, beginnend bei Startzustand " + startZustand.toString());
        while (!ausgabeStack.isEmpty()) {
            System.out.println(ausgabeStack.pop().toString());
        }
    }


    private void gibAus(ArrayList<Zustand> queue) {
        for (Zustand zustand : queue) {
            System.out.println(zustand);
        }
        System.out.println("----------");
    }


}
