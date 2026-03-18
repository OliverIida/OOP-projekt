public class Kaart {
    private final String mast;
    private final String väärtus;

    public Kaart(String mast, String väärtus) {
        if (mast == null || väärtus == null) {
            throw new IllegalArgumentException("Kaardi mast ja väärtus peavad olema määratud.");
        }

        this.mast = mast;
        this.väärtus = väärtus;
    }

    public String getMast() {
        return mast;
    }

    public String getVäärtus() {
        return väärtus;
    }

    // Annab kaardi blackjacki punktiväärtuse.
    public int getPunktiVäärtus() {
        if (onÄss()) {
            return 11;
        }

        if (onPildikaart()) {
            return 10;
        }

        try {
            return Integer.parseInt(väärtus);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Tundmatu kaardi väärtus: " + väärtus, e);
        }
    }

    // Kas kaart on äss, mida saab hiljem lugeda ka väärtusena 1.
    public boolean onÄss() {
        return "A".equals(väärtus);
    }

    // Pildikaardid annavad blackjackis alati 10 punkti.
    public boolean onPildikaart() {
        return "K".equals(väärtus) || "Q".equals(väärtus) || "J".equals(väärtus);
    }

    // Säilitab varasema meetodinime, kui seda kasutatakse mujal koodis.
    public int getKaardiVäärtus() {
        return getPunktiVäärtus();
    }

    @Override
    public String toString() {
        return väärtus + " " + mast;
    }
}
