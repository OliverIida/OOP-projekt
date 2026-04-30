import java.util.Scanner;

class Main {
    // Programmi käivitamise alguspunkt.
    public static void main(String[] args) {
        System.out.println("Tere tulemast Blackjacki mängu!");
        System.out.println("Selle mängu eesmärk on saada võimalikult 21 lähedale, aga mitte üle 21.");
        System.out.println(" ");
        System.out.println("Kaartide väärtused:");
        System.out.println("- numbrikaardid on oma väärtusega");
        System.out.println("- J, Q ja K on 10");
        System.out.println("- A on 1 või 11");
        System.out.println(" ");
        System.out.println("Mängu ajal saad valida:");
        System.out.println("1 - võta veel 1 kaart");
        System.out.println("2 - jää pidama");
        System.out.println("q - lõpeta mäng");
        System.out.println(" ");
        System.out.println("Alustuseks sisesta mõned oma detailid:");

        // Scanner loeb kasutaja sisestusi konsoolist.
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nimi: ");
        String nimi = scanner.nextLine();

        System.out.print("Vanus: ");
        int vanus = Integer.parseInt(scanner.nextLine());

        System.out.print("Summa, millega soovid mängida: ");
        int raha = Integer.parseInt(scanner.nextLine());

        // Kontrollime, et mängijal oleks alguses raha olemas.
        if (raha <= 0) {
            System.out.println("Mängimiseks peab algsumma olema suurem kui 0.");
            scanner.close();
            return;
        }

        Mängija mängija = new Mängija(nimi, vanus, raha);

        // Ainult täisealine mängija saab mängu alustada.
        if (!mängija.kasOnTäisealine(vanus)) {
            System.out.println("Blackjacki saavad mängida ainult täisealised.");
            scanner.close();
            return;
        }

        // Loome mängu objekti ja käivitame mängu.
        BlackjackMäng mäng = new BlackjackMäng(mängija, scanner);
        mäng.alusta();
        scanner.close();
    }
}
