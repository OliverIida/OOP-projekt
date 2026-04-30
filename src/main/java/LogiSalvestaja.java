import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// Klass, mis salvestab iga vooru tulemuse .csv faili.
public class LogiSalvestaja {
    private final String failiNimi;

    public LogiSalvestaja(String failiNimi) {
        this.failiNimi = failiNimi;
        looFailKuiPuudub();
    }

    // Kui faili veel ei ole, loome selle ja kirjutame päise.
    private void looFailKuiPuudub() {
        File fail = new File(failiNimi);
        if (fail.exists()) {
            return;
        }

        try (FileWriter kirjutaja = new FileWriter(fail)) {
            kirjutaja.write("mängijaSkoor,diileriSkoor,panus,tulemus\n");
        } catch (IOException e) {
            System.out.println("Logifaili loomine ebaõnnestus: " + e.getMessage());
        }
    }

    // Lisab faili lõppu uue vooru tulemuse.
    public void salvestaVoor(int mängijaSkoor, int diileriSkoor, int panus, String tulemus) {
        try (FileWriter kirjutaja = new FileWriter(failiNimi, true)) {
            kirjutaja.write(mängijaSkoor + "," + diileriSkoor + "," + panus + "," + tulemus + "\n");
        } catch (IOException e) {
            System.out.println("Logifaili kirjutamine ebaõnnestus: " + e.getMessage());
        }
    }
}
