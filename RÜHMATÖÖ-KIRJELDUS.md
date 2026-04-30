# Rühmatöö kirjeldus

## Autorid

Karl Elmar Vikat  
Oliver Iida  
Elias Mikael Teikari

## Projekti põhjalik kirjeldus

Meie rühmatööks on JavaFX graafilise kasutajaliidesega Blackjacki mäng. Programmi eesmärk on simuleerida tuntud kaardimängu Blackjack nii, et kasutaja saaks mängida diileri vastu, teha panuseid ja näha iga vooru tulemust. Mängu keskne idee on saada kaartidega punktisumma võimalikult 21 lähedale ilma üle 21 minemata.

Programmi käivitamisel avaneb avakuva, kus kuvatakse lühike tutvustus ja reeglid ning küsitakse mängija nimi, vanus ja algsumma. Kui kasutaja ei ole täisealine, sisestab algsumma 0 või sisestab teksti seal, kus oodatakse arvu, kuvab programm vastava veateate ega luba mängu alustada. Kui tingimused on täidetud, avaneb mängukuva, kus mäng käib voorude kaupa. Iga vooru alguses sisestab mängija panuse ja talle ning diilerile jagatakse kaardid, mis kuvatakse väikeste paneelidena (väärtus ja masti sümbol). Diileri teine kaart on alguses pööratud (näeb ainult `?`). Vooru ajal saab mängija hiirega või klaviatuuriga (`1`, `2`) valida, kas võtta kaart juurde või jääda pidama. Pärast mängija käiku mängib diiler oma käigu lõpuni (võtab kaarte kuni vähemalt 17 punktini), seejärel võrreldakse tulemusi ja uuendatakse mängija raha. Iga vooru tulemus salvestatakse `logid.csv` faili. Kui mängija lõpetab mängu (`Q`, Lõpeta-nupp või akna sulgemine), loetakse logifail uuesti läbi ja koostatakse `kokkuvõte.md`, kus on kirjas võidetud/kaotatud voorude arv ja võidetud/kaotatud raha kokku.

Lühike kasutusjuhis:

1. Klooni repositoorium ja ava projektikaust.
2. Käivita `.\käivita.cmd` (Windows) või `./käivita.sh` (Linux/macOS). Skript laeb Maveni ja JavaFX-i ise alla — eraldi midagi installida pole vaja peale Java 17+.
3. Sisesta avakuval küsitud andmed (nimi, vanus, algsumma) ja vajuta Alusta.
4. Mängu ajal kasuta hiirt või klaviatuuri: `1` võtab kaardi, `2` jääb pidama, `Enter` panustab või alustab uut vooru, `Q` lõpetab mängu.

## Klasside eesmärgid ja olulisemad meetodid

### MänguAken

`MänguAken` on JavaFX rakenduse põhiklass, mis pärib `Application` klassist. See ehitab üles nii avakuva (sisestusväljad nime, vanuse ja raha jaoks) kui ka mängukuva (kaartide paneelid, panuse väli ja tegevusnupud) ning seob hiire- ja klaviatuurisündmused mängu loogikaga. Olulisemad meetodid on `start`, mis käivitab rakenduse, `näitaAvakuva` ja `näitaMänguKuva`, mis loovad kuvad, `panusta`, `võtaKaart`, `jääPidama`, `lõpetaVoor` ja `lõpetaBlackjackiga`, mis juhivad vooru käiku, ning `näitaSeisu`, `looKaardiPaneel` ja `looPööratudKaart`, mis vastutavad kaartide visuaalse kuvamise eest. Meetod `lõpetaMäng` kutsub mängu lõpetamisel kokkuvõtte koostaja välja.

### Mängija

`Mängija` kirjeldab nii päris mängijat kui ka diilerit. Selles klassis hoitakse mängija nime, vanust, raha, panust ja käes olevaid kaarte. Olulisemad meetodid on `kasOnTäisealine`, millega kontrollitakse vanust, `lisaKaart`, `nulliKaardid` ja `getKaardidTekstina`, mis aitavad käes olevaid kaarte hallata, ning `arvutaPunktid`, mis arvutab blackjacki reeglite järgi punktisumma (sh ässa väärtuse 1 või 11). Lisaks on tähtsad meetodid `onBust` ja `onBlackjack` ning panusega seotud meetodid `asetaPanus`, `tagastaPanus`, `võidaPanus` ja `kaotaPanus`.

### Kaardipakk

`Kaardipakk` vastutab kaardipaki loomise, segamise ja kaartide väljastamise eest. Konstruktor loob uue 52-kaardise paki ja segab selle automaatselt ära. Meetod `looTäispakk` moodustab kõik masti ja väärtuse kombinatsioonid, `segaKaardid` muudab kaartide järjekorra juhuslikuks ning `võtaKaart` tagastab paki pealmise kaardi ja eemaldab selle pakist. Abimeetodid `getSuurus` ja `onTühi` võimaldavad paki seisu kontrollida.

### Kaart

`Kaart` esindab ühte mängukaarti. Selles klassis hoitakse kaardi masti ja väärtust ning teisendatakse need mängu jaoks sobivale kujule. Meetod `getPunktiVäärtus` annab kaardi blackjacki punktiväärtuse, `onÄss` kontrollib, kas kaart on äss, ja `onPildikaart` kontrollib, kas tegemist on soldati, emanda või kuningaga. Meetod `toString` tagastab kaardi loetaval kujul, mida kasutab nii konsoolimäng kui ka JavaFX kaartide paneelide ehitamine.

### LogiSalvestaja

`LogiSalvestaja` salvestab iga vooru tulemuse CSV-faili kujul `mängijaSkoor,diileriSkoor,panus,tulemus`. Konstruktor loob faili koos päisereaga, kui seda veel pole. Meetod `salvestaVoor` lisab faili lõppu uue rea ja meetod `looFailKuiPuudub` tagab, et päiserida on alati olemas. Klass püüab kinni `IOException`-i, et faili kirjutamise tõrked ei katkestaks mängu.

### KokkuvõtteSalvestaja

`KokkuvõtteSalvestaja` loeb `logid.csv` faili kogu sisu ja koostab selle põhjal `kokkuvõte.md` faili, kus on kirjas võidetud, kaotatud ja viigistatud voorude arv ning võidetud ja kaotatud raha kokku. Meetod `koostaKokkuvõte` käivitatakse iga kord, kui kasutaja mängu lõpetab — fail kirjutatakse alati üle, nii et kokkuvõte vastab kogu seni mängitud ajaloole. Klass tuleb toime ka vana, panus-veeruta logiformaadiga ja püüab kinni faili lugemise/kirjutamise erindid.

## Projekti tegemise protsess

Projekti esimeses etapis loodi GitHubi repositoorium, paigaldati algne klasside struktuur ja kirjutati esimene töötav versioon, mis töötas terminalis. Selles etapis pandi paika andmeklassid (`Mängija`, `Kaart`, `Kaardipakk`) ning blackjacki põhiloogika.

Teises etapis viisime mängu üle JavaFX kasutajaliidesele, nagu nõudsid 2. rühmatöö juhendi tingimused. Lisasime uue klassi `MänguAken`, mis ehitab üles graafilise kasutajaliidese ja töötleb hiire- ja klaviatuurisündmusi. Kaartide tekstilise kuva asemel joonistasime nüüd iga kaardi väikese paneelina, kus on näha väärtus ja masti sümbol; pööratud kaart kuvatakse küsimärgiga. Lisaks lisasime sisendvigade jaoks veateated (näiteks kui kasutaja sisestab vanuse asemel teksti või paneb panuse, mis ületab raha).

Kolmandas etapis lisasime nõuetekohase failidega töötamise: klass `LogiSalvestaja` salvestab iga vooru tulemuse CSV-faili ja klass `KokkuvõtteSalvestaja` loeb selle uuesti, et koostada mängu lõpetamisel `kokkuvõte.md`. Samuti seadistasime projekti Maven Wrapperi peale, et kasutaja saaks pärast `git clone`-i mängu käivitada ühe käsuga, ilma Maveni või JavaFX-i eelnevalt ise installimata. Lõpus viimistlesime kasutajaliidese paigutust nii, et akna suuruse muutudes paigutus mõistlikult kaasa muutuks.

## Tehisintellekti kasutamise kirjeldus

Kasutasime JavaFX kasutajaliidese ehitamisel abivahendina tehisintellekti (Claude). AI aitas peamiselt JavaFX layoutide (VBox, HBox, StackPane) seadistamisel, sündmuste sidumisel ning Maven Wrapperi paigaldamisel. Mängu reeglid, andmeklassid (`Mängija`, `Kaart`, `Kaardipakk`), failidega töötamise loogika ja sisendite valideerimine on rühmaliikmete endi kirjutatud ja arusaadavad. AI väljundeid kontrollisime ja muutsime ise, et need vastaksid meie koodistiilile (eestikeelsed muutujad, lihtne ja algajasõbralik kood).

## Rühmaliikmete panus ja ajakulu

### Karl Elmar Vikat

Karl Elmar Vikat tegi peamiselt `Kaart.java` ja `Kaardipakk.java` klassid. Ta lisas `Kaart` klassi esimese versiooni, lõi ja täiendas `Kaardipakk` klassi ning refaktoreeris hiljem mõlemat klassi nii, et kaardi punktiväärtus oleks selgemalt eraldatud. JavaFX etapis vastutas Karl kaartide visuaalse kuva eest — masti sümbolite valiku (♥ ♦ ♣ ♠), kaardipaneelide CSS-stiili ja pööratud kaardi kuvamise. Karli töö maht oli orienteeruvalt umbes 7 tundi.

### Oliver Iida

Oliver Iida tegi peamiselt `MänguAken.java` klassi ja vastutas projekti üldise ülesehituse ning käivitamise eest. Tema sidus JavaFX kasutajaliidese andmeklassidega, kirjutas hiire- ja klaviatuurisündmuste käsitlejad ning sisendite valideerimise. Lisaks seadistas Oliver projekti Maven Wrapperi peale, kirjutas `käivita.cmd` ja `käivita.sh` skriptid, mis tuvastavad JAVA_HOME automaatselt, ning korrastas README. Oliveri töö maht oli orienteeruvalt umbes 7 tundi.

### Elias Mikael Teikari

Elias Mikael Teikari tegi peamiselt `Mängija.java`, `LogiSalvestaja.java` ja `KokkuvõtteSalvestaja.java` klassid. Tema lisas `Mängija` klassi väljad, täisealisuse kontrolli, punktide arvutamise ja panuse asetamise loogika. JavaFX etapis vastutas Elias failidega töötamise eest — CSV-formaadi disaini, logi kirjutamise ja kokkuvõtte koostamise loogika eest. Eliase töö maht oli orienteeruvalt umbes 7 tundi.

## Tegemise mured

Projekti tegemisel oli kõige suurem väljakutse JavaFX kasutajaliidese ülesehitamine, sest enne seda kursust polnud meil JavaFX-iga praktilist kogemust. Eriti keeruline oli mõista, kuidas sündmused (nupuvajutused, klaviatuur) on seotud mängu olekuga ning kuidas hoida kuva ajakohasena pärast iga muudatust mängijate käes.

Teine keerulisem koht oli faili lugemine ja kirjutamine ning erinditöötlus. Tuli mõista, kuidas avada fail nii, et see suletaks alati korralikult ka tõrke korral (`try-with-resources`) ja kuidas käsitleda olukordi, kus fail veel ei eksisteeri. Lisaks tuli läbi mõelda, kuidas hallata akna paigutust nii, et see näeks väikeses ja suures aknas mõistlik välja.

## Hinnang lõpptulemusele

Hindame oma töö lõpptulemust heaks. Programm täidab seatud eesmärgi: mängija saab JavaFX aknas mängida Blackjacki diileri vastu, teha panuseid, näha kaarte visuaalsete paneelidena ja jälgida oma mänguajalugu kokkuvõtte failis. Hästi õnnestus klasside selge tööjaotus, sündmuste ja loogika eraldatus ning see, et kasutaja saab mängu kasutada peaaegu nõuandeta - käsud on nuppudel ja avakuval kirjas.

Arendamist vajab eelkõige sisendite kontroll ja testimine. Praegu pole meil eraldi automaatteste, vaid testisime käsitsi. Tulevikus võiks lisada näiteks JUnit testid `Kaart` ja `Mängija` klassidele, samuti realistlikuma blackjacki reegli (nt 3:2 väljamakse blackjacki korral).

## Testimine

Programmi testisime peamiselt käsitsi, käivitades selle Maven Wrapperi kaudu (`.\käivita.cmd` / `./käivita.sh`) ning mängides läbi mitmesuguseid stsenaariume. Testisime programmi tervikuna vähemalt järgmiste juhtumitega:

1. Täisealine mängija sisestab nime, vanuse ja algraha, teeb panuse, jääb pidama ning diiler mängib oma käigu lõpuni. Selle käigus kontrollisime, et kaardid jagatakse õigesti, kaardipaneelid kuvatakse korrektselt, diiler võtab kaarte kuni vähemalt 17 punktini ja vooru lõpus uuendatakse raha õigesti.
2. Alaealine kasutaja sisestab vanuseks alla 18. Kontrollisime, et programm kuvab veateate ega luba mängu alustada.
3. Kasutaja sisestab algrahaks 0, panuseks suurema summa kui ta raha või vanuse asemel teksti. Kontrollisime, et programm kuvab vastava veateate ja ei katkesta tööd.
4. Kasutaja vajutab `Q` või sulgeb akna mängu ajal. Kontrollisime, et `kokkuvõte.md` koostatakse õigesti ja vastab `logid.csv` failis olevatele andmetele.
5. Mängisime mitu vooru järjest, kuni raha sai otsa, ja veendusime, et programm teatab mängu lõpust ega luba enam panustada.

Programmi osi kontrollisime eraldi koodi käitumise ja logifaili väljundi põhjal. Näiteks veendusime, et `Mängija.arvutaPunktid` ja `Kaart.getPunktiVäärtus` toetavad blackjacki punktiarvutust, sh ässa käsitlemist väärtusena 1 või 11. Samuti kontrollisime, et `Kaardipakk` looks uue segatud paki ja annaks kaarte ükshaaval välja, ning et `KokkuvõtteSalvestaja` loeb õigesti kogu logifaili ja arvutab võidetud/kaotatud raha summad õigesti.
