import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// Klass, mis loeb logid.csv ja kirjutab selle põhjal kokkuvõtte faili.
// Kokkuvõte arvestab kõiki seni mängitud voore.
public class KokkuvõtteSalvestaja {
    private final String logiFail;
    private final String kokkuvõtteFail;

    public KokkuvõtteSalvestaja(String logiFail, String kokkuvõtteFail) {
        this.logiFail = logiFail;
        this.kokkuvõtteFail = kokkuvõtteFail;
    }

    // Loeb logifaili ja kirjutab kokkuvõtte üle. Kui logifail puudub, ei tee midagi.
    public void koostaKokkuvõte() {
        File fail = new File(logiFail);
        if (!fail.exists()) {
            return;
        }

        int võiduRoundid = 0;
        int kaotusRoundid = 0;
        int viigid = 0;
        int võidetudRaha = 0;
        int kaotatudRaha = 0;

        try (BufferedReader lugeja = new BufferedReader(new FileReader(fail))) {
            String rida = lugeja.readLine(); // jätame päiserea vahele
            while ((rida = lugeja.readLine()) != null) {
                String[] osad = rida.split(",");
                if (osad.length < 3) {
                    continue;
                }

                int panus;
                String tulemus;

                // Vanem formaat ilma panus-veeruta vs. uus formaat panusega.
                if (osad.length == 3) {
                    panus = 0;
                    tulemus = osad[2].trim();
                } else {
                    try {
                        panus = Integer.parseInt(osad[2].trim());
                    } catch (NumberFormatException e) {
                        continue;
                    }
                    tulemus = osad[3].trim();
                }

                if (tulemus.equals("MÄNGIJA VÕIT")) {
                    võiduRoundid++;
                    võidetudRaha += panus;
                } else if (tulemus.equals("DIILERI VÕIT")) {
                    kaotusRoundid++;
                    kaotatudRaha += panus;
                } else if (tulemus.equals("VIIK")) {
                    viigid++;
                }
            }
        } catch (IOException e) {
            System.out.println("Logifaili lugemine ebaõnnestus: " + e.getMessage());
            return;
        }

        try (FileWriter kirjutaja = new FileWriter(kokkuvõtteFail)) {
            kirjutaja.write("# Mänguajaloo kokkuvõte\n\n");
            kirjutaja.write("- Võidetud voore: " + võiduRoundid + "\n");
            kirjutaja.write("- Kaotatud voore: " + kaotusRoundid + "\n");
            kirjutaja.write("- Viike: " + viigid + "\n");
            kirjutaja.write("- Võidetud raha kokku: " + võidetudRaha + " eurot\n");
            kirjutaja.write("- Kaotatud raha kokku: " + kaotatudRaha + " eurot\n");
        } catch (IOException e) {
            System.out.println("Kokkuvõtte kirjutamine ebaõnnestus: " + e.getMessage());
        }
    }
}
