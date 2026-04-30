package ee.oop.blackjack.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Deck {
    private final List<Card> cards;

    private Deck(List<Card> cards) {
        this.cards = new ArrayList<>(Objects.requireNonNull(cards));
    }

    public static Deck shuffledStandardDeck() {
        List<Card> cards = new ArrayList<>();
        String[] suits = {"hearts", "diamonds", "clubs", "spades"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
            }
        }

        Collections.shuffle(cards);
        return new Deck(cards);
    }

    public static Deck fromCards(List<Card> cards) {
        return new Deck(cards);
    }

    public static Deck empty() {
        return new Deck(List.of());
    }

    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("Kaardipakk on tühi.");
        }
        return cards.remove(0);
    }

    public List<Card> getRemainingCards() {
        return List.copyOf(cards);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }
}
