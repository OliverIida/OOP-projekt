import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kaardipakk {
    private final List<Kaart> kaardid;

    public Kaardipakk() {
        this.kaardid = new ArrayList<>();
        looTäispakk();
        segaKaardid();
    }

    // Loob tavalise 52-kaardise mängupaki.
    private void looTäispakk() {
        String[] mastid = {"ärtu", "ruutu", "risti", "poti"};
        String[] väärtused = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String mast : mastid) {
            for (String väärtus : väärtused) {
                kaardid.add(new Kaart(mast, väärtus));
            }
        }
    }

    public void lisaKaart(Kaart kaart) {
        kaardid.add(kaart);
    }

    // Segab paki kaartide järjekorra juhuslikuks.
    public void segaKaardid() {
        Collections.shuffle(kaardid);
    }

    // Võtab paki pealmise kaardi ja eemaldab selle pakist.
    public Kaart võtaKaart() {
        if (kaardid.isEmpty()) {
            throw new IllegalStateException("Kaardipakk on tühi.");
        }

        return kaardid.remove(0);
    }

    public int getKaardipakkiSuurus() {
        return kaardid.size();
    }

    public boolean onTühi() {
        return kaardid.isEmpty();
    }

    @Override
    public String toString() {
        return kaardid.toString();
    }
}
