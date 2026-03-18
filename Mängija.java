import java.util.ArrayList;

class Mängija {
    String nimi;
    int vanus;
    int raha;
    int panus;
    ArrayList<Kaart> kaardid;
    boolean bust;
    boolean blackjack;
    
    public Mängija(String nimi, int vanus, int raha) {
        this.nimi = nimi;
        this.vanus = vanus;
        this.raha = raha;
        this.kaardid = new ArrayList<>();
        this.bust = false;
        this.blackjack = false;
    }

    public void mängi() {
        System.out.println(nimi + " mängib.");
    }

    public static void kasOnTäisealine(int vanus) {
        if (vanus >= 18) {
            System.out.println("Mängija on täisealine.");
        } else {
            System.out.println("Mängija ei ole täisealine.");
        }
    }

    
}