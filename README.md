# Blackjack JavaFX

Blackjack on nüüd tehtud JavaFX graafilise kasutajaliidesega rühmatöö 2. etapi jaoks. Mängu saab kasutada ilma terminalikäskudeta pärast käivitamist, see toetab nii hiirt kui klaviatuuri ning oskab mänguseisu faili salvestada ja hiljem taastada.

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

1. Sisesta vasakul mängija nimi, vanus ja stardiraha.
2. Vajuta `Alusta mang`.
3. Sisesta panus ja vajuta `Jaga`.
4. Vooru ajal kasuta nuppe `Vota kaart` või `Jaa pidama`.
5. Vooru lõpus vajuta `Uus voor`.
6. Kui tahad mängu hiljem jätkata, vajuta `Salvesta`. Seis kirjutatakse faili `data/savegame.json`.
7. Varem salvestatud mängu taastamiseks vajuta `Lae`.

## Klaviatuuri otseteed

- `Enter` kinnitab aktiivse sisestusvälja.
- `H` võtab kaardi.
- `S` jätab pidama.
- `N` alustab uue vooru.
- `Ctrl+S` salvestab mängu.
- `Ctrl+L` laadib salvestatud mängu.

## Projekti ülesehitus

- `src/main/java/ee/oop/blackjack/BlackjackApplication.java` sisaldab JavaFX kasutajaliidest.
- `src/main/java/ee/oop/blackjack/service/BlackjackGameEngine.java` haldab mängu reegleid ja vooru olekut.
- `src/main/java/ee/oop/blackjack/service/SaveLoadService.java` loeb ja kirjutab JSON-faili.
- `src/main/java/ee/oop/blackjack/model/` sisaldab mängu andmemudeleid.

## Nõuete katvus

- Teema on inimtegevus: kasutaja mängib Blackjacki diileri vastu.
- Suhtlus kasutajaga käib JavaFX GUI kaudu.
- Rakendus töötleb hiire ja klaviatuuri sündmusi.
- Aken on muudetava suurusega ning paigutus kohaneb.
- Vigaste sisestuste ja failivigade puhul näidatakse `Alert` teateid.
- Mänguseis salvestatakse faili ja loetakse failist tagasi.
- Rakendus koosneb mitmest klassist.
- Käivitamisel ja abiaknas kuvatakse kasutusjuhend.

## Märkus dokumentatsiooni kohta

`RÜHMATÖÖ-KIRJELDUS.md` autorite ja panuse jaotuse osas tuleb enne lõplikku esitamist täita tegeliku 2-liikmelise rühma nimedega. Selle info usaldusväärne tuletamine ainult koodirepost ei ole võimalik.
