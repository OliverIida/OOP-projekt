package ee.oop.blackjack.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record PlayerSnapshot(
        String name,
        int age,
        int bankroll,
        int currentBet,
        List<Card> hand
) {
    @JsonCreator
    public PlayerSnapshot(
            @JsonProperty("name") String name,
            @JsonProperty("age") int age,
            @JsonProperty("bankroll") int bankroll,
            @JsonProperty("currentBet") int currentBet,
            @JsonProperty("hand") List<Card> hand
    ) {
        this.name = name;
        this.age = age;
        this.bankroll = bankroll;
        this.currentBet = currentBet;
        this.hand = hand == null ? List.of() : List.copyOf(hand);
    }
}
