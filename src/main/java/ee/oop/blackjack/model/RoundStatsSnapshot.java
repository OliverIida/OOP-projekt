package ee.oop.blackjack.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record RoundStatsSnapshot(
        int roundsPlayed,
        int wins,
        int losses,
        int pushes,
        int blackjacks
) {
    @JsonCreator
    public RoundStatsSnapshot(
            @JsonProperty("roundsPlayed") int roundsPlayed,
            @JsonProperty("wins") int wins,
            @JsonProperty("losses") int losses,
            @JsonProperty("pushes") int pushes,
            @JsonProperty("blackjacks") int blackjacks
    ) {
        this.roundsPlayed = roundsPlayed;
        this.wins = wins;
        this.losses = losses;
        this.pushes = pushes;
        this.blackjacks = blackjacks;
    }
}
