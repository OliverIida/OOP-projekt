import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // algne tekst, kus kasutajale kuvatakse sissejuhatus ja juhend
        System.out.println("Tere tulemast Blackjacki mängu!");
        System.out.println("Selle mängu eesmärk on saada võimalikult 21 lähedale, aga mitte üle 21.");
        System.out.println(" ");
        System.out.println( "Kaartide väärtused:");
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
                  
        // scanneri loomine
        Scanner scanner = new Scanner(System.in);

        // kasutajalt nime küsimine ja sisse lugemine
        System.out.print("Nimi: ");
        String nimi = scanner.nextLine();

        // kasutajalt vanuse küsimine ja sisse lugemine
        System.out.print("Vanus: ");
        String vanus = scanner.nextLine();

        // kasutajalt summa küsimine ja sisse lugemine
        System.out.print("Summa, millega soovid mängida: ");
        String raha = scanner.nextLine();


        while (true) {
            // kasutaja sisendi sisse lugemine (1, 2 või q)
            System.out.print("Sisend: ");
            String kasutajaSisend =  scanner.nextLine();

            // kui kasutaja kirjutab 'q' siis programm lõpetab töö
            if (kasutajaSisend == "q") {
                System.out.println("Headaega!");
                System.exit(0);
            }
        }
    }
}
