import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// JavaFX kasutajaliides Blackjacki mängule.
// Kasutab samu klasse (Mängija, Kaardipakk, Kaart) nagu konsoolimäng.
public class MänguAken extends Application {
    private Mängija mängija;
    private Mängija diiler;
    private Kaardipakk kaardipakk;
    private LogiSalvestaja logija;

    // Avakuva väljad
    private TextField nimiVäli;
    private TextField vanusVäli;
    private TextField rahaVäli;
    private Label avaSõnumSilt;

    // Mängukuva sildid
    private Label rahaSilt;
    private Label diileriKaardidSilt;
    private Label diileriPunktidSilt;
    private Label mängijaKaardidSilt;
    private Label mängijaPunktidSilt;
    private Label sõnumSilt;

    // Mängukuva sisestus ja nupud
    private TextField panusVäli;
    private Label panusVeaSilt;
    private Button panustaNupp;
    private Button võtaKaartNupp;
    private Button jääPidamaNupp;
    private Button uusVoorNupp;
    private Button lõpetaNupp;

    @Override
    public void start(Stage lava) {
        logija = new LogiSalvestaja("logid.csv");
        näitaAvakuva(lava);
    }

    // Esimene kuva: tutvustus ja mängija andmete sisestus.
    private void näitaAvakuva(Stage lava) {
        Label pealkiri = new Label("Tere tulemast Blackjacki mängu!");
        Label info = new Label(
                "Eesmärk on saada võimalikult 21 lähedale, aga mitte üle 21.\n"
                        + "Numbrikaardid on oma väärtusega, J/Q/K = 10, A = 1 või 11.\n"
                        + "Mängu ajal saad: võtta kaardi (1), jääda pidama (2) või lõpetada (Q).");

        Label nimiSilt = new Label("Nimi:");
        nimiVäli = new TextField();
        Label vanusSilt = new Label("Vanus:");
        vanusVäli = new TextField();
        Label algrahaSilt = new Label("Summa, millega soovid mängida:");
        rahaVäli = new TextField();

        avaSõnumSilt = new Label();

        Button alustaNupp = new Button("Alusta");
        alustaNupp.setOnAction(e -> alustaMäng(lava));

        VBox juur = new VBox(10);
        juur.setPadding(new Insets(20));
        juur.setAlignment(Pos.CENTER);
        juur.getChildren().addAll(pealkiri, info,
                nimiSilt, nimiVäli,
                vanusSilt, vanusVäli,
                algrahaSilt, rahaVäli,
                alustaNupp, avaSõnumSilt);

        Scene avaStseen = new Scene(juur, 520, 480);

        // Tekstiväljad on poole akna laiused ja keskele joondatud.
        nimiVäli.maxWidthProperty().bind(avaStseen.widthProperty().divide(2));
        vanusVäli.maxWidthProperty().bind(avaStseen.widthProperty().divide(2));
        rahaVäli.maxWidthProperty().bind(avaStseen.widthProperty().divide(2));

        // Klaviatuur: Enter alustab mängu.
        avaStseen.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                alustaMäng(lava);
            }
        });

        lava.setTitle("Blackjack");
        lava.setScene(avaStseen);
        lava.setMinWidth(420);
        lava.setMinHeight(420);
        lava.show();
    }

    // Kontrollib avakuva sisestused ja loob mängija objekti.
    private void alustaMäng(Stage lava) {
        try {
            String nimi = nimiVäli.getText().trim();
            if (nimi.isEmpty()) {
                avaSõnumSilt.setText("Sisesta nimi.");
                return;
            }

            int vanus = Integer.parseInt(vanusVäli.getText().trim());
            int raha = Integer.parseInt(rahaVäli.getText().trim());

            if (raha <= 0) {
                avaSõnumSilt.setText("Algsumma peab olema suurem kui 0.");
                return;
            }

            mängija = new Mängija(nimi, vanus, raha);

            if (!mängija.kasOnTäisealine(vanus)) {
                avaSõnumSilt.setText("Blackjacki saavad mängida ainult täisealised.");
                return;
            }

            näitaMänguKuva(lava);
        } catch (NumberFormatException e) {
            avaSõnumSilt.setText("Vanus ja algsumma peavad olema täisarvud.");
        }
    }

    // Mängu peakuva: kaardid, punktid, panus ja nupud.
    private void näitaMänguKuva(Stage lava) {
        rahaSilt = new Label();
        rahaSilt.setStyle("-fx-font-weight: bold;");

        diileriKaardidSilt = new Label("Diileri kaardid:");
        diileriPunktidSilt = new Label("Diileri punktid:");
        VBox diileriAla = new VBox(5, new Label("Diiler"), diileriKaardidSilt, diileriPunktidSilt);
        diileriAla.setPadding(new Insets(10));
        diileriAla.setStyle("-fx-border-color: gray; -fx-border-radius: 5;");

        mängijaKaardidSilt = new Label("Sinu kaardid:");
        mängijaPunktidSilt = new Label("Sinu punktid:");
        VBox mängijaAla = new VBox(5, new Label("Mängija"), mängijaKaardidSilt, mängijaPunktidSilt);
        mängijaAla.setPadding(new Insets(10));
        mängijaAla.setStyle("-fx-border-color: gray; -fx-border-radius: 5;");

        panusVäli = new TextField();
        panusVäli.setPromptText("Sisesta panus");
        panusVeaSilt = new Label();
        panusVeaSilt.setStyle("-fx-text-fill: red;");

        panustaNupp = new Button("Panusta");
        panustaNupp.setOnAction(e -> panusta());

        võtaKaartNupp = new Button("Võta kaart (1)");
        võtaKaartNupp.setOnAction(e -> võtaKaart());
        võtaKaartNupp.setDisable(true);

        jääPidamaNupp = new Button("Jää pidama (2)");
        jääPidamaNupp.setOnAction(e -> jääPidama());
        jääPidamaNupp.setDisable(true);

        uusVoorNupp = new Button("Uus voor");
        uusVoorNupp.setOnAction(e -> uusVoor());
        uusVoorNupp.setDisable(true);

        lõpetaNupp = new Button("Lõpeta (Q)");
        lõpetaNupp.setOnAction(e -> lava.close());

        sõnumSilt = new Label();

        HBox panuseRiba = new HBox(10, new Label("Panus:"), panusVäli, panustaNupp, panusVeaSilt);
        panuseRiba.setAlignment(Pos.CENTER_LEFT);

        HBox nuppudeRiba = new HBox(10, võtaKaartNupp, jääPidamaNupp, uusVoorNupp, lõpetaNupp);
        nuppudeRiba.setAlignment(Pos.CENTER_LEFT);

        VBox juur = new VBox(12);
        juur.setPadding(new Insets(20));
        juur.getChildren().addAll(rahaSilt, diileriAla, mängijaAla, panuseRiba, nuppudeRiba, sõnumSilt);

        // Lubame mängija ja diileri aladel akna suuruse muutudes laieneda.
        VBox.setVgrow(diileriAla, Priority.ALWAYS);
        VBox.setVgrow(mängijaAla, Priority.ALWAYS);

        Scene mänguStseen = new Scene(juur, 620, 560);

        // Klaviatuur: 1, 2, Q ja Enter teevad sama, mis nupud.
        mänguStseen.setOnKeyPressed(e -> {
            KeyCode kood = e.getCode();
            if (kood == KeyCode.DIGIT1 && !võtaKaartNupp.isDisabled()) {
                võtaKaart();
            } else if (kood == KeyCode.DIGIT2 && !jääPidamaNupp.isDisabled()) {
                jääPidama();
            } else if (kood == KeyCode.Q) {
                lava.close();
            } else if (kood == KeyCode.ENTER) {
                if (!panustaNupp.isDisabled()) {
                    panusta();
                } else if (!uusVoorNupp.isDisabled()) {
                    uusVoor();
                }
            }
        });

        lava.setScene(mänguStseen);
        valmistaPanusFaas();
    }

    // Valmistab uue vooru ette: uus pakk, tühjad käed, panuse küsimine.
    private void valmistaPanusFaas() {
        kaardipakk = new Kaardipakk();
        diiler = new Mängija("Diiler", 90, 0);
        mängija.nulliKaardid();
        diiler.nulliKaardid();

        rahaSilt.setText("Sul on " + mängija.getRaha() + " eurot.");
        diileriKaardidSilt.setText("Diileri kaardid: -");
        diileriPunktidSilt.setText("Diileri punktid: -");
        mängijaKaardidSilt.setText("Sinu kaardid: -");
        mängijaPunktidSilt.setText("Sinu punktid: -");
        sõnumSilt.setText("Sisesta panus, et alustada uut vooru.");
        panusVeaSilt.setText("");

        panusVäli.setDisable(false);
        panusVäli.clear();
        panustaNupp.setDisable(false);
        võtaKaartNupp.setDisable(true);
        jääPidamaNupp.setDisable(true);
        uusVoorNupp.setDisable(true);
    }

    // Mängija paneb panuse ja saab algkaardid.
    private void panusta() {
        try {
            int summa = Integer.parseInt(panusVäli.getText().trim());

            if (!mängija.asetaPanus(summa)) {
                panusVeaSilt.setText("Sisesta sobiv panus (1 - " + mängija.getRaha() + ").");
                return;
            }

            panusVeaSilt.setText("");
            panusVäli.setDisable(true);
            panustaNupp.setDisable(true);

            jagaAlgkaardid();
            näitaSeisu(false);

            if (mängija.onBlackjack() || diiler.onBlackjack()) {
                lõpetaBlackjackiga();
            } else {
                võtaKaartNupp.setDisable(false);
                jääPidamaNupp.setDisable(false);
                sõnumSilt.setText("Vali: võta kaart (1) või jää pidama (2).");
            }
        } catch (NumberFormatException e) {
            panusVeaSilt.setText("Panus peab olema number.");
        }
    }

    // Jagab mängijale ja diilerile kaks kaarti.
    private void jagaAlgkaardid() {
        mängija.lisaKaart(kaardipakk.võtaKaart());
        diiler.lisaKaart(kaardipakk.võtaKaart());
        mängija.lisaKaart(kaardipakk.võtaKaart());
        diiler.lisaKaart(kaardipakk.võtaKaart());
    }

    // Värskendab kaartide ja punktide silte.
    // näitaDiileriKõik = true puhul näeme diileri tervet kätt.
    private void näitaSeisu(boolean näitaDiileriKõik) {
        mängijaKaardidSilt.setText("Sinu kaardid: " + mängija.getKaardidTekstina());
        mängijaPunktidSilt.setText("Sinu punktid: " + mängija.arvutaPunktid());

        if (näitaDiileriKõik) {
            diileriKaardidSilt.setText("Diileri kaardid: " + diiler.getKaardidTekstina());
            diileriPunktidSilt.setText("Diileri punktid: " + diiler.arvutaPunktid());
        } else {
            diileriKaardidSilt.setText("Diileri nähtav kaart: " + diiler.getKaart(0));
            diileriPunktidSilt.setText("Diileri punktid: ?");
        }
    }

    // Mängija võtab veel ühe kaardi.
    private void võtaKaart() {
        Kaart kaart = kaardipakk.võtaKaart();
        mängija.lisaKaart(kaart);
        näitaSeisu(false);

        if (mängija.onBust()) {
            int mPunktid = mängija.arvutaPunktid();
            int dPunktid = diiler.arvutaPunktid();
            mängija.kaotaPanus();
            näitaSeisu(true);
            sõnumSilt.setText("Läksid üle 21. Kaotasid selle vooru.");
            võtaKaartNupp.setDisable(true);
            jääPidamaNupp.setDisable(true);
            logija.salvestaVoor(mPunktid, dPunktid, "DIILERI VÕIT");
            valmistaUueVooruVõiLõpu();
        } else if (mängija.arvutaPunktid() == 21) {
            // 21 punkti puhul ei ole mõtet enam kaarte võtta — diiler mängib.
            jääPidama();
        }
    }

    // Mängija jääb pidama, diiler mängib oma käigu lõpuni.
    private void jääPidama() {
        võtaKaartNupp.setDisable(true);
        jääPidamaNupp.setDisable(true);
        diileriKäik();
        näitaSeisu(true);
        lõpetaVoor();
        valmistaUueVooruVõiLõpu();
    }

    // Diiler võtab kaarte, kuni saab vähemalt 17 punkti.
    private void diileriKäik() {
        while (diiler.arvutaPunktid() < 17) {
            diiler.lisaKaart(kaardipakk.võtaKaart());
        }
    }

    // Võrdleb tulemusi ja kannab need logifaili.
    private void lõpetaVoor() {
        int mPunktid = mängija.arvutaPunktid();
        int dPunktid = diiler.arvutaPunktid();
        String tulemus;

        if (diiler.onBust()) {
            sõnumSilt.setText("Diiler läks üle 21. Võitsid vooru!");
            mängija.võidaPanus();
            tulemus = "MÄNGIJA VÕIT";
        } else if (mPunktid > dPunktid) {
            sõnumSilt.setText("Sul oli parem tulemus. Võitsid vooru!");
            mängija.võidaPanus();
            tulemus = "MÄNGIJA VÕIT";
        } else if (mPunktid == dPunktid) {
            sõnumSilt.setText("Voor jäi viiki.");
            mängija.tagastaPanus();
            tulemus = "VIIK";
        } else {
            sõnumSilt.setText("Diiler võitis vooru.");
            mängija.kaotaPanus();
            tulemus = "DIILERI VÕIT";
        }

        logija.salvestaVoor(mPunktid, dPunktid, tulemus);
    }

    // Voor lõppeb kohe, kui kellelgi on alguses blackjack.
    private void lõpetaBlackjackiga() {
        näitaSeisu(true);
        boolean mB = mängija.onBlackjack();
        boolean dB = diiler.onBlackjack();
        int mPunktid = mängija.arvutaPunktid();
        int dPunktid = diiler.arvutaPunktid();
        String tulemus;

        if (mB && dB) {
            sõnumSilt.setText("Mõlemal on blackjack. Voor jäi viiki.");
            mängija.tagastaPanus();
            tulemus = "VIIK";
        } else if (mB) {
            sõnumSilt.setText("Blackjack! Võitsid vooru.");
            mängija.võidaPanus();
            tulemus = "MÄNGIJA VÕIT";
        } else {
            sõnumSilt.setText("Diiler sai blackjacki. Kaotasid vooru.");
            mängija.kaotaPanus();
            tulemus = "DIILERI VÕIT";
        }

        logija.salvestaVoor(mPunktid, dPunktid, tulemus);
        valmistaUueVooruVõiLõpu();
    }

    // Pärast vooru: lubame uue vooru või teatame, et raha sai otsa.
    private void valmistaUueVooruVõiLõpu() {
        rahaSilt.setText("Sul on " + mängija.getRaha() + " eurot.");

        if (mängija.getRaha() <= 0) {
            sõnumSilt.setText(sõnumSilt.getText() + "  Raha sai otsa, mäng on läbi.");
            uusVoorNupp.setDisable(true);
        } else {
            uusVoorNupp.setDisable(false);
        }
    }

    // Alustab uue vooru.
    private void uusVoor() {
        valmistaPanusFaas();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
