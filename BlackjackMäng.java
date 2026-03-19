import java.util.Scanner;

public class BlackjackMäng {
    private final Mängija mängija;
    private final Scanner scanner;
    private Mängija diiler;
    private Kaardipakk kaardipakk;

    public BlackjackMäng(Mängija mängija, Scanner scanner) {
        this.mängija = mängija;
        this.scanner = scanner;
    }

    public void alusta() {
        System.out.println(" ");
        System.out.println("Tere, " + mängija.getNimi() + "! Mäng algab.");

        while (mängija.getRaha() > 0) {
            alustaVooru();

            if (!küsiPanus()) {
                System.out.println("Mäng lõpetati.");
                return;
            }

            jagaAlgkaardid();
            näitaAlgseis();

            if (kontrolliBlackjack()) {
                näitaRaha();
                continue;
            }

            if (!mängijaKäik()) {
                mängija.tagastaPanus();
                System.out.println("Mäng lõpetati.");
                return;
            }

            if (mängija.onBust()) {
                System.out.println("Läksid üle 21. Kaotasid selle vooru.");
                mängija.kaotaPanus();
                näitaRaha();
                continue;
            }

            diileriKäik();
            lõpetaVoor();
            näitaRaha();
        }

        System.out.println("Raha sai otsa. Mäng on läbi.");
    }

    private void alustaVooru() {
        kaardipakk = new Kaardipakk();
        diiler = new Mängija("Diiler", 90, 0);
        mängija.nulliKaardid();
        diiler.nulliKaardid();

        System.out.println(" ");
        System.out.println("Uus voor.");
    }

    private boolean küsiPanus() {
        while (true) {
            System.out.println("Sul on praegu " + mängija.getRaha() + " eurot.");
            System.out.print("Sisesta panus või q lõpetamiseks: ");
            String sisend = scanner.nextLine().trim();

            if (sisend.equalsIgnoreCase("q")) {
                return false;
            }

            int panus = Integer.parseInt(sisend);
            if (mängija.asetaPanus(panus)) {
                return true;
            }

            System.out.println("Sisesta sobiv panus.");
        }
    }

    private void jagaAlgkaardid() {
        mängija.lisaKaart(kaardipakk.võtaKaart());
        diiler.lisaKaart(kaardipakk.võtaKaart());
        mängija.lisaKaart(kaardipakk.võtaKaart());
        diiler.lisaKaart(kaardipakk.võtaKaart());
    }

    private void näitaAlgseis() {
        System.out.println("Sinu kaardid: " + mängija.getKaardidTekstina());
        System.out.println("Sinu punktid: " + mängija.arvutaPunktid());
        System.out.println("Diileri nähtav kaart: " + diiler.getKaart(0));
    }

    private boolean kontrolliBlackjack() {
        boolean mängijalOnBlackjack = mängija.onBlackjack();
        boolean diilerilOnBlackjack = diiler.onBlackjack();

        if (!mängijalOnBlackjack && !diilerilOnBlackjack) {
            return false;
        }

        System.out.println("Diileri kaardid: " + diiler.getKaardidTekstina());
        System.out.println("Diileri punktid: " + diiler.arvutaPunktid());

        if (mängijalOnBlackjack && diilerilOnBlackjack) {
            System.out.println("Mõlemal on blackjack. Voor jäi viiki.");
            mängija.tagastaPanus();
        } else if (mängijalOnBlackjack) {
            System.out.println("Blackjack! Võitsid vooru.");
            mängija.võidaPanus();
        } else {
            System.out.println("Diiler sai blackjacki. Kaotasid vooru.");
            mängija.kaotaPanus();
        }

        return true;
    }

    private boolean mängijaKäik() {
        while (true) {
            if (mängija.arvutaPunktid() >= 21) {
                return true;
            }

            System.out.print("Sisend (1 = kaart, 2 = jää pidama, q = lõpeta mäng): ");
            String sisend = scanner.nextLine().trim();

            if (sisend.equals("1")) {
                Kaart kaart = kaardipakk.võtaKaart();
                mängija.lisaKaart(kaart);
                System.out.println("Said kaardi: " + kaart);
                System.out.println("Sinu kaardid: " + mängija.getKaardidTekstina());
                System.out.println("Sinu punktid: " + mängija.arvutaPunktid());

                if (mängija.onBust()) {
                    return true;
                }
            } else if (sisend.equals("2")) {
                return true;
            } else if (sisend.equalsIgnoreCase("q")) {
                return false;
            } else {
                System.out.println("Sisesta 1, 2 või q.");
            }
        }
    }

    private void diileriKäik() {
        System.out.println("Diileri kord.");
        System.out.println("Diileri kaardid: " + diiler.getKaardidTekstina());
        System.out.println("Diileri punktid: " + diiler.arvutaPunktid());

        while (diiler.arvutaPunktid() < 17) {
            Kaart kaart = kaardipakk.võtaKaart();
            diiler.lisaKaart(kaart);
            System.out.println("Diiler võtab kaardi: " + kaart);
            System.out.println("Diileri punktid: " + diiler.arvutaPunktid());
        }
    }

    private void lõpetaVoor() {
        int mängijaPunktid = mängija.arvutaPunktid();
        int diileriPunktid = diiler.arvutaPunktid();

        System.out.println("Sinu lõppseis: " + mängija.getKaardidTekstina() + " (" + mängijaPunktid + ")");
        System.out.println("Diileri lõppseis: " + diiler.getKaardidTekstina() + " (" + diileriPunktid + ")");

        if (diiler.onBust()) {
            System.out.println("Diiler läks üle 21. Võitsid vooru.");
            mängija.võidaPanus();
        } else if (mängijaPunktid > diileriPunktid) {
            System.out.println("Sul oli parem tulemus. Võitsid vooru.");
            mängija.võidaPanus();
        } else if (mängijaPunktid == diileriPunktid) {
            System.out.println("Voor jäi viiki.");
            mängija.tagastaPanus();
        } else {
            System.out.println("Diiler võitis vooru.");
            mängija.kaotaPanus();
        }
    }

    private void näitaRaha() {
        System.out.println("Sul on nüüd " + mängija.getRaha() + " eurot.");
    }
}
