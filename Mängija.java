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

    public boolean kasOnTäisealine(int vanus) {
        if (vanus >= 18) {
            System.out.println("Mängija on täisealine.");
            return true;
        } else {
            System.out.println("Mängija ei ole täisealine.");
            return false;   
        }
    }

    public void lisaKaart(Kaart kaart) {
        kaardid.add(kaart);
    }

    public int arvutaPunktid() {
        int punktid = 0;
        for (Kaart kaart : kaardid) {
            punktid += kaart.väärtus; // peab lisama Kaart.java
        }
        return punktid;
    }

    public boolean onBust() {
        if (arvutaPunktid() > 21) {
            bust = true;
        }
        return bust;
    }
}