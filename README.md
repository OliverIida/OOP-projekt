# Blackjack

Lihtne Blackjack mäng JavaFX kasutajaliidesega, kus eesmärk on saada punktisumma võimalikult 21 lähedale ilma üle minemata.

## Kuidas käivitada

Eeldus: **Java 17+** on installitud ja PATH-is.

Windows (PowerShell või cmd):

```
.\käivita.cmd
```

Linux / macOS:

```bash
./käivita.sh
```

Skript laeb esmakordsel käivitamisel Maveni ise alla ja avab seejärel mängu akna. Eraldi midagi (Maven, JAVA_HOME, JavaFX SDK) installida ega seadistada ei ole vaja.

## Kuidas mängida

Pärast käivitamist sisesta avakuval:
- oma nimi
- vanus
- summa, millega soovid mängida

Mängu ajal saad kasutada hiirt või klaviatuuri:
- `1` - võta üks kaart juurde
- `2` - jää pidama
- `Enter` - panusta või alusta uut vooru
- `Q` - lõpeta mäng

Iga vooru tulemus kirjutatakse faili `logid.csv` kujul `mängijaSkoor,diileriSkoor,tulemus`.

## Kuidas diiler töötab

Diiler mängib sinu vastu pärast seda, kui sina jääd pidama. Kui diileri punktisumma on alla 17, võtab ta kaardi juurde. Kui diileril on 17 või rohkem punkti, jääb ta pidama. Võidab see, kelle punktisumma on 21-le lähemal ilma üle minemata.
