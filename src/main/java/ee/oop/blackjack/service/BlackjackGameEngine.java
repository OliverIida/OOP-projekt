package ee.oop.blackjack.service;

import ee.oop.blackjack.model.Card;
import ee.oop.blackjack.model.Deck;
import ee.oop.blackjack.model.GamePhase;
import ee.oop.blackjack.model.GameSnapshot;
import ee.oop.blackjack.model.GameState;
import ee.oop.blackjack.model.Player;

import java.util.Objects;
import java.util.function.Supplier;

public final class BlackjackGameEngine {
    private final Supplier<Deck> deckSupplier;
    private final GameState state;

    public BlackjackGameEngine() {
        this(Deck::shuffledStandardDeck);
    }

    public BlackjackGameEngine(Supplier<Deck> deckSupplier) {
        this.deckSupplier = Objects.requireNonNull(deckSupplier, "Kaardipaki looja peab olemas olema.");
        this.state = new GameState();
    }

    public GameState getState() {
        return state;
    }

    public void startNewGame(String name, int age, int bankroll) {
        String trimmedName = Objects.requireNonNull(name, "Nimi peab olema maaratud.").trim();

        if (trimmedName.isEmpty()) {
            throw new IllegalArgumentException("Palun sisesta mangija nimi.");
        }
        if (age < 18) {
            throw new IllegalArgumentException("Blackjacki saavad mangida ainult taisealised.");
        }
        if (bankroll <= 0) {
            throw new IllegalArgumentException("Stardiraha peab olema suurem kui 0.");
        }

        state.setPlayer(new Player(trimmedName, age, bankroll));
        state.setDealer(Player.createDealer());
        state.setDeck(Deck.empty());
        state.setStats(new ee.oop.blackjack.model.RoundStats());
        state.setPhase(GamePhase.READY_FOR_ROUND);
        state.setLastMessage("Mang algas. Sisesta panus ja vajuta Jaga.");
    }

    public void resetToSetup() {
        state.resetToSetup();
    }

    public void startNextRound() {
        ensureGameStarted();

        if (state.getPhase() == GamePhase.PLAYER_TURN) {
            throw new IllegalStateException("Voor on pooleli. Lopeta see enne uue vooru alustamist.");
        }
        if (state.getPhase() == GamePhase.GAME_OVER) {
            throw new IllegalStateException("Raha on otsas. Alusta uut mangu.");
        }

        Player player = state.getPlayer();
        player.clearHand();
        state.setDealer(Player.createDealer());
        state.setDeck(Deck.empty());
        state.setPhase(GamePhase.READY_FOR_ROUND);
        state.setLastMessage("Uus voor on valmis. Sisesta panus ja vajuta Jaga.");
    }

    public void dealRound(int betAmount) {
        ensureGameStarted();

        if (state.getPhase() != GamePhase.READY_FOR_ROUND) {
            throw new IllegalStateException("Praegu ei saa uut vooru jagada.");
        }

        Player player = state.getPlayer();
        if (betAmount <= 0) {
            throw new IllegalArgumentException("Panus peab olema suurem kui 0.");
        }
        if (betAmount > player.getBankroll()) {
            throw new IllegalArgumentException("Panus ei tohi olla suurem kui sul olev raha.");
        }

        Deck deck = deckSupplier.get();
        Player dealer = Player.createDealer();

        player.clearHand();
        player.placeBet(betAmount);
        state.setDealer(dealer);
        state.setDeck(deck);

        dealInitialCards(player, dealer, deck);

        if (player.hasBlackjack() || dealer.hasBlackjack()) {
            resolveImmediateBlackjack(player, dealer);
            return;
        }

        state.setPhase(GamePhase.PLAYER_TURN);
        state.setLastMessage("Voor algas. Kasuta nuppe voi klahve H ja S.");
    }

    public void hit() {
        ensurePhase(GamePhase.PLAYER_TURN, "Kaarti saab votta ainult siis, kui voor on pooleli.");

        Player player = state.getPlayer();
        Card drawnCard = state.getDeck().drawCard();
        player.addCard(drawnCard);

        if (player.isBust()) {
            player.loseBet();
            state.getStats().recordLoss();
            finishRound("Tombasid kaardi " + drawnCard + " ja laksid ule 21. Voor on kaotatud.");
            return;
        }

        if (player.calculatePoints() == 21) {
            resolveDealerTurn();
            return;
        }

        state.setLastMessage("Said kaardi " + drawnCard + ". Soovi korral vota veel kaart.");
    }

    public void stand() {
        ensurePhase(GamePhase.PLAYER_TURN, "Jaa pidama saab ainult siis, kui voor on pooleli.");
        resolveDealerTurn();
    }

    public GameSnapshot createSnapshot() {
        return state.toSnapshot();
    }

    public void restoreSnapshot(GameSnapshot snapshot) {
        Objects.requireNonNull(snapshot, "Taastatav seis peab olemas olema.");
        state.restore(snapshot);
    }

    private void ensureGameStarted() {
        if (!state.hasActiveGame()) {
            throw new IllegalStateException("Alusta koigepealt uut mangu.");
        }
    }

    private void ensurePhase(GamePhase expectedPhase, String errorMessage) {
        ensureGameStarted();
        if (state.getPhase() != expectedPhase) {
            throw new IllegalStateException(errorMessage);
        }
    }

    private void dealInitialCards(Player player, Player dealer, Deck deck) {
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
        player.addCard(deck.drawCard());
        dealer.addCard(deck.drawCard());
    }

    private void resolveImmediateBlackjack(Player player, Player dealer) {
        if (player.hasBlackjack() && dealer.hasBlackjack()) {
            player.pushBet();
            state.getStats().recordPush();
            finishRound("Molemal tuli kohe blackjack. Voor jai viiki.");
            return;
        }

        if (player.hasBlackjack()) {
            player.winBet();
            state.getStats().recordWin(true);
            finishRound("Blackjack! Voitsid vooru kohe alguses.");
            return;
        }

        player.loseBet();
        state.getStats().recordLoss();
        finishRound("Diiler sai blackjacki. Kaotasid vooru.");
    }

    // Diiler tombab automaatselt seni, kuni tal on vahemalt 17 punkti.
    private void resolveDealerTurn() {
        Player dealer = state.getDealer();
        Deck deck = state.getDeck();

        while (dealer.calculatePoints() < 17) {
            dealer.addCard(deck.drawCard());
        }

        settleRound();
    }

    private void settleRound() {
        Player player = state.getPlayer();
        Player dealer = state.getDealer();
        int playerPoints = player.calculatePoints();
        int dealerPoints = dealer.calculatePoints();

        if (dealer.isBust()) {
            player.winBet();
            state.getStats().recordWin(false);
            finishRound("Diiler laks ule 21. Voitsid vooru.");
            return;
        }

        if (playerPoints > dealerPoints) {
            player.winBet();
            state.getStats().recordWin(false);
            finishRound("Sul oli parem tulemus kui diileril. Voitsid vooru.");
            return;
        }

        if (playerPoints == dealerPoints) {
            player.pushBet();
            state.getStats().recordPush();
            finishRound("Voor jai viiki.");
            return;
        }

        player.loseBet();
        state.getStats().recordLoss();
        finishRound("Diiler voitis vooru.");
    }

    private void finishRound(String message) {
        if (state.getPlayer().getBankroll() <= 0) {
            state.setPhase(GamePhase.GAME_OVER);
            state.setLastMessage(message + " Raha sai otsa. Alusta uut mangu.");
            return;
        }

        state.setPhase(GamePhase.ROUND_OVER);
        state.setLastMessage(message + " Vajuta Uus voor, et jatkata.");
    }
}
