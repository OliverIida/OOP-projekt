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
        // iga kaardil on enda väärtus, mille põhjal arvutame mängija punktid kokku
        int punktid = 0;
        int ässadeArv = 0;

        for (Kaart kaart : kaardid) { // käime läbi kõik mängija kaardid
            punktid += kaart.getPunktiVäärtus();

            if (kaart.onÄss()) { 
                ässadeArv++;
            }
        }

        // Kui summa läheb üle 21, loeme osa ässasid väärtusena 1.
        while (punktid > 21 && ässadeArv > 0) {
            punktid -= 10;
            ässadeArv--;
        }

        return punktid;
    }

    public boolean onBust() {
        // kui punktide summa läheb üle 21, siis mängija on bust ja
        bust = arvutaPunktid() > 21;
        return bust;
    }

    public boolean onBlackjack() { // kui mängijal on täpselt 21 punkti kahe kaardiga, siis on blackjack
        blackjack = kaardid.size() == 2 && arvutaPunktid() == 21;
        return blackjack;
    }

    public void asetaPanus(int summa) {
        if (summa > raha) {
            System.out.println("Sul pole piisavalt raha panustamiseks.");
        } else {
            panus = summa;
            raha -= summa;
            System.out.println(nimi + " on pannud panuse: " + panus);
        }
    }
}
