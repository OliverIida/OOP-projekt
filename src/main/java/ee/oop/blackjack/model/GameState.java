package ee.oop.blackjack.model;

import java.util.Objects;

public final class GameState {
    private Player player;
    private Player dealer;
    private Deck deck;
    private RoundStats stats;
    private GamePhase phase;
    private String lastMessage;

    public GameState() {
        resetToSetup();
    }

    public void resetToSetup() {
        player = null;
        dealer = Player.createDealer();
        deck = Deck.empty();
        stats = new RoundStats();
        phase = GamePhase.SETUP;
        lastMessage = "Sisesta nimi, vanus ja stardiraha ning alusta uut mangu.";
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getDealer() {
        return dealer;
    }

    public void setDealer(Player dealer) {
        this.dealer = dealer;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public RoundStats getStats() {
        return stats;
    }

    public void setStats(RoundStats stats) {
        this.stats = stats;
    }

    public GamePhase getPhase() {
        return phase;
    }

    public void setPhase(GamePhase phase) {
        this.phase = phase;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public boolean hasActiveGame() {
        return player != null;
    }

    public GameSnapshot toSnapshot() {
        if (player == null) {
            throw new IllegalStateException("Mangu ei saa salvestada enne, kui see on alustatud.");
        }

        return new GameSnapshot(
                player.toSnapshot(),
                dealer.toSnapshot(),
                deck.getRemainingCards(),
                phase,
                stats.toSnapshot(),
                lastMessage
        );
    }

    public void restore(GameSnapshot snapshot) {
        Objects.requireNonNull(snapshot, "Taastatav seis peab olemas olema.");
        if (snapshot.player() == null || snapshot.dealer() == null || snapshot.stats() == null || snapshot.phase() == null) {
            throw new IllegalArgumentException("Salvestusfailis puuduvad vajalikud andmed.");
        }

        player = Player.fromSnapshot(snapshot.player());
        dealer = Player.fromSnapshot(snapshot.dealer());
        deck = Deck.fromCards(snapshot.remainingDeck());
        stats = RoundStats.fromSnapshot(snapshot.stats());
        phase = snapshot.phase();
        lastMessage = snapshot.lastMessage() == null ? "Mang taastati failist." : snapshot.lastMessage();
    }
}
