# Blackjack JavaFX

Minimaalne JavaFX-versioon Blackjackist. Mängija sisestab stardiraha, paneb panuse ja mängib diileri vastu.

## Käivitamine

Esimesel käivitusel laadib Maven Wrapper automaatselt alla Maveni ja projekti sõltuvused.

macOS / Linux:

```bash
chmod +x mvnw
./mvnw javafx:run
```

Windows:

```bat
mvnw.cmd javafx:run
```

## Kasutamine

1. Sisesta stardiraha ja vajuta `Alusta`.
2. Sisesta panus ja vajuta `Jaga`.
3. Vooru ajal vajuta `Vota` (uus kaart) või `Pidama` (jää seisma).
4. Vooru lõpus vajuta `Uus voor`.
5. Kui raha saab otsa, vajuta `Alusta uuesti`.

## Projekti ülesehitus

- `src/main/java/ee/oop/blackjack/BlackjackApplication.java` — JavaFX kasutajaliides.
- `src/main/java/ee/oop/blackjack/service/BlackjackGameEngine.java` — mängu reeglid.
- `src/main/java/ee/oop/blackjack/model/` — mängu andmemudelid.
