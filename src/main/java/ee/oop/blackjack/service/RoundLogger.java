package ee.oop.blackjack.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

public final class RoundLogger {
    private static final String HEADER = "mängijaSkoor,diileriSkoor,tulemus";

    private final File file;

    public RoundLogger(String path) {
        this.file = new File(path);
    }

    public void log(int playerScore, int dealerScore, String outcome) {
        boolean writeHeader = !file.exists() || file.length() == 0;
        try (FileOutputStream fos = new FileOutputStream(file, true);
             OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
             BufferedWriter writer = new BufferedWriter(osw)) {
            if (writeHeader) {
                writer.write(HEADER);
                writer.newLine();
            }
            writer.write(playerScore + "," + dealerScore + "," + outcome);
            writer.newLine();
        } catch (IOException exception) {
            System.err.println("Vooru logimine ebaõnnestus: " + exception.getMessage());
        }
    }
}
