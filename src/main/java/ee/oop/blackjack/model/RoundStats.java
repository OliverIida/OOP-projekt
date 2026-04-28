package ee.oop.blackjack.model;

public final class RoundStats {
    private int roundsPlayed;
    private int wins;
    private int losses;
    private int pushes;
    private int blackjacks;

    public RoundStats() {
    }

    public RoundStats(int roundsPlayed, int wins, int losses, int pushes, int blackjacks) {
        this.roundsPlayed = roundsPlayed;
        this.wins = wins;
        this.losses = losses;
        this.pushes = pushes;
        this.blackjacks = blackjacks;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public int getPushes() {
        return pushes;
    }

    public int getBlackjacks() {
        return blackjacks;
    }

    public void recordWin(boolean blackjack) {
        roundsPlayed++;
        wins++;
        if (blackjack) {
            blackjacks++;
        }
    }

    public void recordLoss() {
        roundsPlayed++;
        losses++;
    }

    public void recordPush() {
        roundsPlayed++;
        pushes++;
    }

    public RoundStatsSnapshot toSnapshot() {
        return new RoundStatsSnapshot(roundsPlayed, wins, losses, pushes, blackjacks);
    }

    public static RoundStats fromSnapshot(RoundStatsSnapshot snapshot) {
        return new RoundStats(
                snapshot.roundsPlayed(),
                snapshot.wins(),
                snapshot.losses(),
                snapshot.pushes(),
                snapshot.blackjacks()
        );
    }
}
