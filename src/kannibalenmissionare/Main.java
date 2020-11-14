package kannibalenmissionare;

public class Main {



    public static void main(String[] args) {

        Zustand startZustand = new Zustand(3, 3,0,0,"links", null);
        Zustand endZustand = new Zustand(0, 0, 3, 3, "rechts", null);
        KannibalenSolver kannibalenSolver = new KannibalenSolver(startZustand, endZustand);
        kannibalenSolver.breitenSuche();
    }


}
