# Blackjack

Lihtne terminali Blackjack mäng, kus eesmärk on saada punktisumma võimalikult 21 lähedale ilma üle minemata.

## Kuidas käivitada

Ava projektikaust terminalis ja käivita:

```bash
javac *.java
java Main
```

## Kuidas mängida

Pärast käivitamist sisesta:
- oma nimi
- vanus
- summa, millega soovid mängida
- panus

Seejärel kasuta neid käske:
- `1` - võta üks kaart juurde
- `2` - jää pidama
- `q` - lõpeta mäng

## Kuidas diiler töötab

Diiler mängib sinu vastu pärast seda, kui sina jääd pidama. Kui diileri punktisumma on alla 17, võtab ta kaardi juurde. Kui diileril on 17 või rohkem punkti, jääb ta pidama. Võidab see, kelle punktisumma on 21-le lähemal ilma üle minemata.
