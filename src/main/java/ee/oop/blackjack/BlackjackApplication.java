package ee.oop.blackjack;

import ee.oop.blackjack.model.Card;
import ee.oop.blackjack.model.GamePhase;
import ee.oop.blackjack.model.GameState;
import ee.oop.blackjack.model.Player;
import ee.oop.blackjack.service.BlackjackGameEngine;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Objects;

public final class BlackjackApplication extends Application {
    private final BlackjackGameEngine gameEngine = new BlackjackGameEngine();

    private TextField bankrollField;
    private TextField betField;
    private Button startButton;
    private Button dealButton;
    private Button hitButton;
    private Button standButton;
    private Button nextRoundButton;

    private Label bankrollLabel;
    private Label statusLabel;
    private Label playerScoreLabel;
    private Label dealerScoreLabel;
    private HBox playerCards;
    private HBox dealerCards;

    private VBox setupView;
    private VBox gameView;
    private StackPane root;

    public static void launchApp(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        setupView = buildSetupView();
        gameView = buildGameView();

        root = new StackPane(setupView);
        root.getStyleClass().add("root-pane");

        Scene scene = new Scene(root, 720, 520);
        scene.getStylesheets().add(loadStylesheet());

        stage.setTitle("Blackjack");
        stage.setMinWidth(640);
        stage.setMinHeight(480);
        stage.setScene(scene);
        stage.show();
    }

    private VBox buildSetupView() {
        Label title = new Label("Blackjack");
        title.getStyleClass().add("title");

        bankrollField = new TextField();
        bankrollField.setPromptText("Stardiraha");
        bankrollField.setMaxWidth(180);
        bankrollField.setOnAction(event -> handleStartGame());

        startButton = new Button("Alusta");
        startButton.getStyleClass().add("primary");
        startButton.setOnAction(event -> handleStartGame());

        VBox box = new VBox(16, title, bankrollField, startButton);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private VBox buildGameView() {
        bankrollLabel = new Label();
        bankrollLabel.getStyleClass().add("bankroll");

        dealerScoreLabel = new Label("Diiler");
        dealerScoreLabel.getStyleClass().add("hand-title");
        dealerCards = new HBox(10);
        dealerCards.setAlignment(Pos.CENTER_LEFT);
        VBox dealerBox = new VBox(8, dealerScoreLabel, dealerCards);

        playerScoreLabel = new Label("Mangija");
        playerScoreLabel.getStyleClass().add("hand-title");
        playerCards = new HBox(10);
        playerCards.setAlignment(Pos.CENTER_LEFT);
        VBox playerBox = new VBox(8, playerScoreLabel, playerCards);

        statusLabel = new Label();
        statusLabel.getStyleClass().add("status");
        statusLabel.setWrapText(true);

        betField = new TextField();
        betField.setPromptText("Panus");
        betField.setMaxWidth(100);
        betField.setOnAction(event -> handleDeal());

        dealButton = new Button("Jaga");
        hitButton = new Button("Vota");
        standButton = new Button("Pidama");
        nextRoundButton = new Button("Uus voor");

        dealButton.setOnAction(event -> handleDeal());
        hitButton.setOnAction(event -> handleHit());
        standButton.setOnAction(event -> handleStand());
        nextRoundButton.setOnAction(event -> handleNextRound());

        HBox actions = new HBox(8, betField, dealButton, hitButton, standButton, nextRoundButton);
        actions.setAlignment(Pos.CENTER_LEFT);

        VBox box = new VBox(18, bankrollLabel, dealerBox, playerBox, statusLabel, actions);
        box.setPadding(new Insets(24));
        return box;
    }

    private void handleStartGame() {
        try {
            int bankroll = parseInteger(bankrollField.getText(), "Stardiraha peab olema arv.");
            gameEngine.startNewGame("Mangija", 18, bankroll);
            root.getChildren().setAll(gameView);
            betField.clear();
            betField.requestFocus();
            refreshUi();
        } catch (IllegalArgumentException exception) {
            showError(exception.getMessage());
        }
    }

    private void handleDeal() {
        try {
            int bet = parseInteger(betField.getText(), "Panus peab olema arv.");
            gameEngine.dealRound(bet);
            refreshUi();
        } catch (IllegalArgumentException | IllegalStateException exception) {
            showError(exception.getMessage());
        }
    }

    private void handleHit() {
        try {
            gameEngine.hit();
            refreshUi();
        } catch (IllegalStateException exception) {
            showError(exception.getMessage());
        }
    }

    private void handleStand() {
        try {
            gameEngine.stand();
            refreshUi();
        } catch (IllegalStateException exception) {
            showError(exception.getMessage());
        }
    }

    private void handleNextRound() {
        try {
            if (gameEngine.getState().getPhase() == GamePhase.GAME_OVER) {
                gameEngine.resetToSetup();
                root.getChildren().setAll(setupView);
                bankrollField.clear();
                bankrollField.requestFocus();
                return;
            }
            gameEngine.startNextRound();
            betField.clear();
            betField.requestFocus();
            refreshUi();
        } catch (IllegalStateException exception) {
            showError(exception.getMessage());
        }
    }

    private void refreshUi() {
        GameState state = gameEngine.getState();
        GamePhase phase = state.getPhase();
        Player player = state.getPlayer();
        Player dealer = state.getDealer();

        bankrollLabel.setText("Raha: " + player.getBankroll() + "€");
        statusLabel.setText(state.getLastMessage());

        boolean hideHole = phase == GamePhase.PLAYER_TURN;
        renderHand(playerCards, player.getHand(), false);
        renderHand(dealerCards, dealer.getHand(), hideHole);

        playerScoreLabel.setText("Mangija — " + player.calculatePoints());
        dealerScoreLabel.setText(hideHole ? "Diiler" : "Diiler — " + dealer.calculatePoints());

        betField.setDisable(phase != GamePhase.READY_FOR_ROUND);
        dealButton.setDisable(phase != GamePhase.READY_FOR_ROUND);
        hitButton.setDisable(phase != GamePhase.PLAYER_TURN);
        standButton.setDisable(phase != GamePhase.PLAYER_TURN);
        nextRoundButton.setDisable(phase != GamePhase.ROUND_OVER && phase != GamePhase.GAME_OVER);
        nextRoundButton.setText(phase == GamePhase.GAME_OVER ? "Alusta uuesti" : "Uus voor");
    }

    private void renderHand(HBox target, List<Card> cards, boolean hideHole) {
        target.getChildren().clear();
        for (int i = 0; i < cards.size(); i++) {
            target.getChildren().add(buildCardNode(cards.get(i), hideHole && i > 0));
        }
    }

    private VBox buildCardNode(Card card, boolean hidden) {
        VBox node = new VBox();
        node.getStyleClass().add("card");
        node.setAlignment(Pos.CENTER);

        if (hidden) {
            node.getStyleClass().add("card-back");
            return node;
        }

        String symbol = suitSymbol(card.suit());
        boolean red = "hearts".equals(card.suit()) || "diamonds".equals(card.suit());

        Label rank = new Label(card.rank());
        rank.getStyleClass().add("card-rank");
        if (red) rank.getStyleClass().add("red");

        Label suit = new Label(symbol);
        suit.getStyleClass().add("card-suit");
        if (red) suit.getStyleClass().add("red");

        node.getChildren().setAll(rank, suit);
        return node;
    }

    private String suitSymbol(String suit) {
        return switch (suit) {
            case "hearts" -> "♥";
            case "diamonds" -> "♦";
            case "clubs" -> "♣";
            case "spades" -> "♠";
            default -> suit;
        };
    }

    private int parseInteger(String text, String message) {
        try {
            return Integer.parseInt(text == null ? "" : text.trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(message);
        }
    }

    private String loadStylesheet() {
        return Objects.requireNonNull(
                getClass().getResource("/ee/oop/blackjack/styles.css"),
                "Stiililehte ei leitud."
        ).toExternalForm();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
