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
}