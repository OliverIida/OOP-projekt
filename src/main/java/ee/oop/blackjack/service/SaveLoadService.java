package ee.oop.blackjack.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ee.oop.blackjack.model.GameSnapshot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public final class SaveLoadService {
    private final ObjectMapper objectMapper;

    public SaveLoadService() {
        this.objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void save(GameSnapshot snapshot, Path path) throws IOException {
        Objects.requireNonNull(snapshot, "Salvestatav seis peab olemas olema.");
        Objects.requireNonNull(path, "Salvestusfaili tee peab olemas olema.");

        if (path.getParent() != null) {
            Files.createDirectories(path.getParent());
        }

        objectMapper.writeValue(path.toFile(), snapshot);
    }

    public GameSnapshot load(Path path) throws IOException {
        Objects.requireNonNull(path, "Salvestusfaili tee peab olemas olema.");

        if (!Files.exists(path)) {
            throw new IOException("Salvestusfaili ei leitud.");
        }
        if (Files.size(path) == 0) {
            throw new IOException("Salvestusfail on tyhi.");
        }

        return objectMapper.readValue(path.toFile(), GameSnapshot.class);
    }
}
