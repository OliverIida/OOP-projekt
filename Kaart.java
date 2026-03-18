public class Kaart {
    private final String mast;
    private final String väärtus;

    public Kaart(String mast, String väärtus) {
        this.mast = mast;
        this.väärtus = väärtus;
    }

    public String getMast() {
        return mast;
    }

    public String getVäärtus() {
        return väärtus;
    }

    public String toString() {
        return väärtus + " " + mast;
    }

    public int getKaardiVäärtus() {
        switch (väärtus) {
            case "A":
                return 11;
            case "K":
                return 10;
            case "Q":
                return 10;
            case "J":
                return 10;
            case "10":
                return 10;
            case "9":
                return 9;
            case "8":
                return 8;
            case "7":
                return 7;
            case "6":
                return 6;
            case "5":
                return 5;
            case "4":
                return 4;
            case "3":
                return 3;
            case "2":
                return 2;
            default:
                return 0;
        }
    }

}
