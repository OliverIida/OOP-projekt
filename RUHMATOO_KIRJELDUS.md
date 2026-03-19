# Rühmatöö kirjeldus

## Autorid

Karl Elmar Vikat  
Oliver Iida  
Elias Mikael Teikari

## Projekti põhjalik kirjeldus

Meie rühmatööks on tekstipõhine Blackjacki mäng, mis töötab terminalis. Programmi eesmärk on simuleerida tuntud kaardimängu Blackjack nii, et kasutaja saaks mängida diileri vastu, teha panuseid ja näha iga vooru tulemust. Mängu keskne idee on saada kaartidega punktisumma võimalikult 21 lähedale ilma üle 21 minemata.

Programmi töö algab sellest, et kasutajale kuvatakse lühike tutvustus ja reeglid. Seejärel küsitakse mängija nimi, vanus ja algsumma. Kui kasutaja ei ole täisealine või tal ei ole mängu alustamiseks raha, siis programm mängu ei käivita. Kui tingimused on täidetud, algab mäng voorude kaupa. Iga vooru alguses küsitakse panust, jagatakse mängijale ja diilerile kaardid ning kontrollitakse, kas kellelgi on kohe alguses blackjack. Kui mäng jätkub, saab mängija valida, kas võtta kaart juurde või jääda pidama. Pärast mängija käiku teeb oma käigu diiler, kes võtab kaarte seni, kuni tal on vähemalt 17 punkti. Vooru lõpus võrreldakse tulemusi, arvestatakse võit või kaotus ning uuendatakse mängija rahasummat. Mäng kestab seni, kuni mängijal on raha alles või ta otsustab ise lõpetada.

Lühike kasutusjuhis:

1. Ava projektikaust terminalis.
2. Kompileeri programm käsuga `javac *.java`.
3. Käivita programm käsuga `java Main`.
4. Sisesta küsitud andmed.
5. Vooru ajal kasuta käsku `1`, et võtta kaart, `2`, et jääda pidama, või `q`, et mäng lõpetada.

## Klasside eesmärgid ja olulisemad meetodid

### Main

`Main` on programmi käivitav klass. Selle ülesanne on kuvada kasutajale mängu alginfo, küsida sisendid ning luua vajalikud objektid, et mäng saaks alata. Olulisem meetod on `main`, mis kontrollib ka seda, kas mängija vanus ja algraha lubavad mängu alustada.

### BlackjackMäng

`BlackjackMäng` juhib kogu mängu loogikat ja voorude käiku. See klass seob kokku mängija, diileri, kaardipaki ja kasutaja sisestused. Kõige olulisem meetod on `alusta`, mis haldab mängu üldist tsüklit. Vooru ettevalmistamiseks kasutatakse meetodit `alustaVooru`, panuse küsimiseks `küsiPanus`, algkaartide jagamiseks `jagaAlgkaardid`, mängija käigu haldamiseks `mängijaKäik`, diileri loogika jaoks `diileriKäik` ning vooru lõpetamiseks `lõpetaVoor`. Meetod `kontrolliBlackjack` kontrollib kohe pärast algkaartide jagamist, kas kellelgi on blackjack.

### Mängija

`Mängija` kirjeldab nii päris mängijat kui ka diilerit. Selles klassis hoitakse mängija nime, vanust, raha, panust ja käes olevaid kaarte. Olulisemad meetodid on `kasOnTäisealine`, millega kontrollitakse vanust, `lisaKaart`, `nulliKaardid` ja `getKaardidTekstina`, mis aitavad käes olevaid kaarte hallata, ning `arvutaPunktid`, mis arvutab blackjacki reeglite järgi punktisumma. Lisaks on tähtsad meetodid `onBust` ja `onBlackjack`, samuti panusega seotud meetodid `asetaPanus`, `tagastaPanus`, `võidaPanus` ja `kaotaPanus`.

### Kaardipakk

`Kaardipakk` vastutab kaardipaki loomise, segamise ja kaartide väljastamise eest. Konstruktor loob uue 52-kaardise paki ja segab selle automaatselt ära. Meetod `looTäispakk` moodustab kõik masti ja väärtuse kombinatsioonid, `segaKaardid` muudab kaartide järjekorra juhuslikuks ning `võtaKaart` tagastab paki pealmise kaardi. Abimeetodid `getSuurus`, `getKaardipakkiSuurus` ja `onTühi` võimaldavad paki seisu kontrollida.

### Kaart

`Kaart` esindab ühte mängukaarti. Selles klassis hoitakse kaardi masti ja väärtust ning teisendatakse need mängu jaoks sobivale kujule. Meetod `getMastiSümbol` tagastab masti sümbolina, `getPunktiVäärtus` annab kaardi blackjacki punktiväärtuse, `onÄss` kontrollib, kas kaart on äss, ja `onPildikaart` kontrollib, kas tegemist on soldati, emanda või kuningaga. Meetod `toString` tagastab kaardi kasutajale loetaval kujul.

## Projekti tegemise protsess

Projekti tegemine algas idee ja algstruktuuri paika panemisega. Esmalt loodi GitHubi repositoorium, README ning algsed klassifailid. Selles etapis pandi paika, et projektiks tuleb terminalis töötav Blackjacki mäng, kus on eraldi klassid mängija, kaardi, kaardipaki ja mängu juhtimise jaoks.

Seejärel jaotasime töö klasside kaupa. Elias Mikael Teikari tegeles algse struktuuri ja `Mängija` klassi loogikaga. Karl Elmar Vikat keskendus `Kaart` ja `Kaardipakk` klassidele. Oliver Iida töötas `Main` ja `BlackjackMäng` klassidega, et kasutaja sisestused ja mängu üldine voog tervikuks siduda.

Kui põhilised klassid olid valmis, liikus töö järgmisse etappi, kus erinevad osad ühendati üheks töötavaks programmiks. Selles etapis valmis esimene täisversioon, kus kasutaja sai juba mängu reaalselt käivitada, teha panuseid, võtta kaarte ja mängida diileri vastu. Pärast seda täiendasime programmi kommentaaride, loetavama terminaliväljundi ja README failiga.

Viimases etapis tegime väiksemaid parandusi ja viimistlusi. Näiteks lisati kommentaare, korrastati kasutajaliidest terminalis, täiustati dokumentatsiooni ja muudeti kaartide kuva arusaadavamaks, kasutades mastide sümboleid.

## Rühmaliikmete panus ja ajakulu

### Karl Elmar Vikat

Karl Elmar Vikat tegi peamiselt `Kaart.java` ja `Kaardipakk.java` klassid. Git ajaloost on näha, et ta lisas `Kaart` klassi esimese versiooni, lõi ja täiendas `Kaardipakk` klassi ning refaktoreeris hiljem mõlemat klassi nii, et kaardi punktiväärtus oleks selgemalt eraldatud ja kaardipakk töötaks mugavamalt. Samuti lisas ta projekti juhendi, kirjutas kommentaare ja lisas kaartidele mastisümbolid. Karli töö maht oli orienteeruvalt umbes 5 tundi.

### Oliver Iida

Oliver Iida tegi peamiselt `Main.java` ja `BlackjackMäng.java` klassid. Git ajaloost on näha, et tema lisas `main` klassi algse loogika, kasutaja sisestuste lugemise ning suure osa mängu põhivoo juhtimisest. Tema commitid „esimene töötav versioon” ja „UI parandamine terminalis” näitavad, et just Oliver sidus eri klassid üheks toimivaks mänguks ning parandas mängu kasutatavust terminalis. Lisaks korrastas ta README faili. Oliveri töö maht oli orienteeruvalt umbes 6 tundi.

### Elias Mikael Teikari

Elias Mikael Teikari tegi projekti algse struktuuri ja suure osa `Mängija.java` klassi loogikast. Git history järgi alustas tema repositooriumi, README ja algsete klassifailidega. Lisaks lisas ta `Mängija` klassi väljad, täisealisuse kontrolli, punktide arvutamise, panuse asetamise loogika ja kommentaarid. Elias tegeles ka varase projekti üldstruktuuri ning klasside algse paika seadmisega. Eliase töö maht oli orienteeruvalt umbes 5 tundi.

## Tegemise mured

Projekti tegemisel oli kõige suurem väljakutse erinevate klasside koostöö loogiline ülesehitamine. Kuigi üksikute meetodite kirjutamine oli jõukohane, nõudis tervikliku mängu loomine rohkem läbimõtlemist, eriti selles osas, kuidas objektid omavahel suhtlevad ja kuidas vooru seisu õigesti hallata.

Teine keerulisem koht oli blackjacki reeglite korrektne rakendamine. Näiteks tuli õigesti lahendada ässa väärtus, panuste arvestus, bust-oleku kontroll ja diileri automaatne käik. Samuti tundsime, et meil oleks kasu olnud rohkemast kogemusest süsteemse testimise ja sisendvigade käsitlemise osas. Praegune lahendus eeldab paljudes kohtades, et kasutaja sisestab andmed õiges formaadis.

## Hinnang lõpptulemusele

Hindame oma töö lõpptulemust heaks, sest valmis programm täidab seatud eesmärgi: mängija saab terminalis mängida Blackjacki diileri vastu, teha panuseid ja mängida mitu vooru järjest. Hästi õnnestus see, et projekt jagunes loogiliselt eraldi klassideks ning iga klass täidab suhteliselt selget rolli. Samuti on programmi töö kasutaja jaoks arusaadav, sest mängu alguses kuvatakse reeglid ja käskude selgitused.

Arendamist vajab eelkõige sisendite kontroll ja testimine. Näiteks võiks tulevikus lisada rohkem vigaste sisendite käsitlemist, eraldi automaatteste ja võib-olla ka täpsema mänguloogika, kui sooviksime Blackjacki reegleid veel realistlikumalt järgida. Samuti võiks tulevikus parandada kapseldatust, näiteks muuta kõik väljad ühtlasemalt privaatseks ja kasutada rohkem get- ning set-meetodeid.

## Testimine

Programmi testisime peamiselt käsitsi, kompileerides ja käivitades seda terminalis. Kõigepealt kontrollisime, et kogu projekt kompileeruks käsuga `javac *.java` ilma vigadeta. Seejärel käivitasime programmi käsuga `java Main` ja proovisime läbi mitu erinevat kasutusjuhtu.

Testisime programmi tervikuna vähemalt järgmiste stsenaariumidega:

1. Täisealine mängija sisestab nime, vanuse ja algraha, teeb panuse, jääb pidama ning diiler mängib oma käigu lõpuni. Selle käigus kontrollisime, et kaardid jagatakse õigesti, diiler võtab kaarte kuni vähemalt 17 punktini ja vooru lõpus uuendatakse raha õigesti.
2. Alaealine kasutaja sisestab vanuseks alla 18. Kontrollisime, et programm ei lase sellisel juhul mängu alustada.
3. Kasutaja sisestab algrahaks 0. Kontrollisime, et programm katkestab töö ja kuvab vastava teate.
4. Testisime ka mängu lõpetamist käsuga `q`, et veenduda, et programm oskab vooru või mängu korrektselt lõpetada.

Programmi osi kontrollisime eraldi koodi käitumise ja väljundi põhjal. Näiteks veendusime, et `Mängija.arvutaPunktid` ja `Kaart.getPunktiVäärtus` toetavad blackjacki punktiarvutust, sh ässa käsitlemist väärtusena 1 või 11. Samuti kontrollisime, et `Kaardipakk` looks uue segatud paki ja annaks kaarte ükshaaval välja. Kuigi me ei teinud eraldi automaatteste, veendusime käsitsi testides, et programmi põhifunktsionaalsus töötab korrektselt.
