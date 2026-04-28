package ee.oop.blackjack.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record GameSnapshot(
        PlayerSnapshot player,
        PlayerSnapshot dealer,
        List<Card> remainingDeck,
        GamePhase phase,
        RoundStatsSnapshot stats,
        String lastMessage
) {
    @JsonCreator
    public GameSnapshot(
            @JsonProperty("player") PlayerSnapshot player,
            @JsonProperty("dealer") PlayerSnapshot dealer,
            @JsonProperty("remainingDeck") List<Card> remainingDeck,
            @JsonProperty("phase") GamePhase phase,
            @JsonProperty("stats") RoundStatsSnapshot stats,
            @JsonProperty("lastMessage") String lastMessage
    ) {
        this.player = player;
        this.dealer = dealer;
        this.remainingDeck = remainingDeck == null ? List.of() : List.copyOf(remainingDeck);
        this.phase = phase;
        this.stats = stats;
        this.lastMessage = lastMessage;
    }
}
