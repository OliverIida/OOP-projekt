package ee.oop.blackjack.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Card(String suit, String rank) {
    @JsonCreator
    public Card(
            @JsonProperty("suit") String suit,
            @JsonProperty("rank") String rank
    ) {
        this.suit = Objects.requireNonNull(suit, "Mast peab olemas olema.").trim();
        this.rank = Objects.requireNonNull(rank, "Vaartus peab olemas olema.").trim();

        if (this.suit.isEmpty() || this.rank.isEmpty()) {
            throw new IllegalArgumentException("Kaardi mast ja vaartus peavad olema maaratud.");
        }
    }

    @JsonIgnore
    public int getPointValue() {
        if (isAce()) {
            return 11;
        }
        if (isFaceCard()) {
            return 10;
        }
        try {
            return Integer.parseInt(rank);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Tundmatu kaardi vaartus: " + rank, exception);
        }
    }

    @JsonIgnore
    public boolean isAce() {
        return "A".equals(rank);
    }

    @JsonIgnore
    public boolean isFaceCard() {
        return "J".equals(rank) || "Q".equals(rank) || "K".equals(rank);
    }

    @Override
    public String toString() {
        return rank + " " + getSuitDisplay();
    }

    private String getSuitDisplay() {
        return switch (suit) {
            case "hearts" -> "♥";
            case "diamonds" -> "♦";
            case "clubs" -> "♣";
            case "spades" -> "♠";
            default -> suit;
        };
    }
}
