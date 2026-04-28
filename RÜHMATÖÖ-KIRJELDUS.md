# Rühmatöö kirjeldus

## Autorid

- `[ASENDA ENNE ESITAMIST] Rühmaliige 1`
- `[ASENDA ENNE ESITAMIST] Rühmaliige 2`

Kui töö tehakse erandina üksinda, siis asendage ülalolev loetelu ühe nimega ja lisage siia lühike põhjendus, miks töö esitati üksinda.

## Projekti kirjeldus

Meie rühmatööks on JavaFX graafilise kasutajaliidesega Blackjacki mäng. Kasutaja mängib diileri vastu, teeb panuseid, vaatab oma ja diileri kaarte ning saab voorude vahel mängu seisu faili salvestada ja hiljem samast kohast taastada.

Rakenduse käivitamisel kuvatakse kasutajale lühike tutvustus. Seejärel sisestab mängija oma nime, vanuse ja stardiraha. Kui mängija on alaealine või stardiraha ei ole korrektne, kuvatakse veateade ning mängu ei alustata. Kui andmed on sobivad, saab kasutaja sisestada panuse ja alustada vooru nupuga `Jaga`.

Vooru ajal saab mängija valida, kas võtta kaart juurde või jääda pidama. Kui kasutaja jääb pidama või saab 21 täis, teeb diiler oma automaatse käigu. Diiler tõmbab kaarte seni, kuni tal on vähemalt 17 punkti. Vooru lõpus võrreldakse tulemusi, uuendatakse mängija rahaseisu ning salvestatakse statistika. Kui mängija raha saab otsa, kuvatakse selle kohta teade ning kasutaja peab alustama uut mängu.

Mängu saab juhtida nii hiire kui klaviatuuriga. Klaviatuuri otseteed on `H` kaardi võtmiseks, `S` pidama jäämiseks, `N` uue vooru alustamiseks, `Ctrl+S` salvestamiseks ja `Ctrl+L` laadimiseks. `Enter` kinnitab aktiivse sisestusvälja.

## Tähtsamad klassid

### Main

`Main` on käivitusklass, mis alustab JavaFX rakendust.

### BlackjackApplication

`BlackjackApplication` ehitab kogu JavaFX kasutajaliidese. Selles klassis luuakse vormid, nupud, sündmuste käsitlejad, veateated ning mängulaua dünaamiline uuendamine. Samuti seotakse siin klaviatuuri otseteed hiirega juhitava kasutajaliidesega.

### BlackjackGameEngine

`BlackjackGameEngine` juhib kogu mängu loogikat. See klass haldab mängu faase, panuseid, kaartide jagamist, diileri automaatset käiku, tulemuse arvutamist ja vooru lõpetamist.

### SaveLoadService

`SaveLoadService` vastutab mänguseisu faili kirjutamise ja failist lugemise eest. Seis salvestatakse JSON-faili `data/savegame.json`.

### Mudeliklassid

Mudeliklassid `Card`, `Deck`, `Player`, `GameState`, `GameSnapshot`, `PlayerSnapshot`, `RoundStats` ja `RoundStatsSnapshot` kirjeldavad kaarte, kaardipakki, mängijaid, mängu aktiivset seisu ning salvestusvormingut.

## Nõuete täitmine

### 1. Programm käsitleb inimtegevust

Jah. Programm simuleerib kaardimängu Blackjack, kus kasutaja mängib diileri vastu.

### 2. Suhtlus kasutajaga toimub JavaFX GUI kaudu

Jah. Lõppversioon kasutab ainult JavaFX graafilist kasutajaliidest. Terminalisisestusi ei kasutata.

### 3. Programm töötleb nii hiire kui klaviatuuriga tekitatud sündmusi

Jah. Kõik põhifunktsioonid on kasutatavad nuppude kaudu ning tähtsamad tegevused on seotud ka klaviatuuri otseteedega.

### 4. Akna suuruse muutmisel muutub kuvatu mõistlikult

Jah. Kasutajaliides on ehitatud `BorderPane`, `VBox`, `HBox` ja `FlowPane` peale, mistõttu kaardid ja paneelid kohanevad akna suuruse muutmisel.

### 5. Erinditöötlus tagab mõistliku reageerimise vigadele

Jah. Vigaste arvude, alaealisuse, liiga suure panuse, puuduva salvestusfaili, tühja faili või katkise faili korral kuvatakse kasutajale `Alert` veateade.

### 6. Programm kirjutab andmeid faili ja loeb neid failist

Jah. Mänguseis salvestatakse JSON-faili `data/savegame.json` ja sealt saab sama seisu taastada.

### 7. Programm koosneb mitmest klassist

Jah. Programmis on mitu mudeli-, teenuse- ja kasutajaliidese klassi.

### 8. Programm on kasutatav ilma eriliste eelteadmisteta

Jah. Käivitamisel kuvatakse lühike sissejuhatus ning `Abi` nupust avaneb lühike kasutusjuhend.

### 9. Programm on mõistlikult kommenteeritud

Jah. Kommentaarid on lisatud kohtadesse, kus loogika ei ole kohe ilmne, näiteks ässade punktiarvestuse ja diileri automaatse käigu juures.

### 10. Programm on rühmaliikmete enda kirjutatud

Jah. Projekti lähtekood on kirjutatud rühmatöö käigus. Kui kasutati tehisintellekti abi, siis ainult abivahendina ning kõik lahenduse osad peavad olema rühma liikmetele arusaadavad ja seletatavad.

## Failist lugemine ja kirjutamine

Salvestusfaili kirjutatakse vähemalt järgmised andmed:

- mängija nimi, vanus, raha ja aktiivne panus
- mängija käes olevad kaardid
- diileri käes olevad kaardid
- kaardipakki alles jäänud kaardid õiges järjekorras
- mängu faas
- voorude statistika
- viimane olekusõnum

See võimaldab mängu jätkata täpselt sealt, kus kasutaja pooleli jäi.

## Testimine

Käsitsi testides tuleb läbi proovida vähemalt järgmised juhud:

1. uus mäng hiirega
2. uus mäng ainult klaviatuuri abil
3. akna suurendamine ja vähendamine
4. salvestamine poole vooru pealt
5. laadimine ja mängu jätkamine
6. vigane arvusisestus
7. puuduva või katkise salvestusfaili käsitlemine

## Rühmaliikmete panus ja ajakulu

See osa tuleb enne esitamist täita tegeliku 2-liikmelise rühma järgi. Soovitatav vorm:

### [ASENDA] Rühmaliige 1

- millised klassid või funktsionaalsused tegi
- ligikaudne ajakulu tundides

### [ASENDA] Rühmaliige 2

- millised klassid või funktsionaalsused tegi
- ligikaudne ajakulu tundides

## Projekti tegemise protsess

Töö algas olemasoleva tekstipõhise Blackjacki loogika ülevaatamisest. Seejärel tõsteti loogika eraldi teenusekihti, et kasutajaliides ja mängureeglid oleksid teineteisest sõltumatud. Järgmise sammuna ehitati JavaFX kasutajaliides, kus on eraldi vorm mängija andmete sisestamiseks, mängulaud ja juhtnupud.

Pärast seda lisati salvestamise ja laadimise tugi, et programm täidaks failitöötluse nõude. Seejärel lisati klaviatuuri otseteed, veateated ja käsitsi testimise jaoks vajalikud kasutusjuhised. Viimases etapis uuendati dokumentatsioon, et see kirjeldaks GUI-põhist lõppversiooni.
