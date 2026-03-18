import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Kaardipakk {
    private final List<Kaart> kaardid;

    public Kaardipakk() {
        this.kaardid = new ArrayList<>();
    }

    public void lisaKaart(Kaart kaart) {
        kaardid.add(kaart);
    }
    
    public void segaKaardid() {
        Collections.shuffle(kaardid);
    }

    public Kaart looKaart() {
        return kaardid.remove(0);
    }
    
    public int getKaardipakkiSuurus() {
        return kaardid.size();
    }

    public String toString() {
        return kaardid.toString();
    }
}
