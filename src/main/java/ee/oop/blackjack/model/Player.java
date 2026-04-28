package ee.oop.blackjack.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Player {
    private final String name;
    private final int age;
    private int bankroll;
    private int currentBet;
    private final List<Card> hand;

    public Player(String name, int age, int bankroll) {
        this(name, age, bankroll, 0, List.of());
    }

    public Player(String name, int age, int bankroll, int currentBet, List<Card> hand) {
        this.name = Objects.requireNonNull(name, "Nimi peab olema maaratud.").trim();
        this.age = age;
        this.bankroll = bankroll;
        this.currentBet = currentBet;
        this.hand = new ArrayList<>(Objects.requireNonNull(hand));

        if (this.name.isEmpty()) {
            throw new IllegalArgumentException("Nimi ei tohi olla tyhi.");
        }
        if (age < 0) {
            throw new IllegalArgumentException("Vanus ei tohi olla negatiivne.");
        }
        if (bankroll < 0) {
            throw new IllegalArgumentException("Raha ei tohi olla negatiivne.");
        }
        if (currentBet < 0) {
            throw new IllegalArgumentException("Panus ei tohi olla negatiivne.");
        }
    }

    public static Player createDealer() {
        return new Player("Diiler", 99, 0);
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getBankroll() {
        return bankroll;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    public List<Card> getHand() {
        return List.copyOf(hand);
    }

    public void clearHand() {
        hand.clear();
        currentBet = 0;
    }

    public void addCard(Card card) {
        hand.add(Objects.requireNonNull(card));
    }

    public int calculatePoints() {
        int points = 0;
        int aces = 0;

        // Assasid loeme alguses 11-na ja vajadusel muudame osa neist 1-ks.
        for (Card card : hand) {
            points += card.getPointValue();
            if (card.isAce()) {
                aces++;
            }
        }

        while (points > 21 && aces > 0) {
            points -= 10;
            aces--;
        }

        return points;
    }

    public boolean hasBlackjack() {
        return hand.size() == 2 && calculatePoints() == 21;
    }

    public boolean isBust() {
        return calculatePoints() > 21;
    }

    public void placeBet(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Panus peab olema suurem kui 0.");
        }
        if (amount > bankroll) {
            throw new IllegalArgumentException("Sul ei ole nii palju raha panustamiseks.");
        }

        currentBet = amount;
        bankroll -= amount;
    }

    public void winBet() {
        bankroll += currentBet * 2;
        currentBet = 0;
    }

    public void pushBet() {
        bankroll += currentBet;
        currentBet = 0;
    }

    public void loseBet() {
        currentBet = 0;
    }

    public PlayerSnapshot toSnapshot() {
        return new PlayerSnapshot(name, age, bankroll, currentBet, getHand());
    }

    public static Player fromSnapshot(PlayerSnapshot snapshot) {
        return new Player(
                snapshot.name(),
                snapshot.age(),
                snapshot.bankroll(),
                snapshot.currentBet(),
                snapshot.hand()
        );
    }
}
