package ee.oop.blackjack;

import ee.oop.blackjack.model.Card;
import ee.oop.blackjack.model.GamePhase;
import ee.oop.blackjack.model.GameState;
import ee.oop.blackjack.model.Player;
import ee.oop.blackjack.model.RoundStats;
import ee.oop.blackjack.service.BlackjackGameEngine;
import ee.oop.blackjack.service.SaveLoadService;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public final class BlackjackApplication extends Application {
    private static final Path SAVE_FILE = Path.of("data", "savegame.json");

    private final BlackjackGameEngine gameEngine = new BlackjackGameEngine();
    private final SaveLoadService saveLoadService = new SaveLoadService();

    private TextField nameField;
    private TextField ageField;
    private TextField bankrollField;
    private TextField betField;

    private Button startGameButton;
    private Button dealButton;
    private Button hitButton;
    private Button standButton;
    private Button nextRoundButton;
    private Button saveButton;
    private Button loadButton;
    private Button newGameButton;
    private Button helpButton;

    private Label playerInfoLabel;
    private Label dealerInfoLabel;
    private Label statsLabel;
    private Label bankrollLabel;
    private Label statusLabel;
    private Label savePathLabel;

    private FlowPane playerCardsPane;
    private FlowPane dealerCardsPane;

    public static void launchApp(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(18));

        Node setupPanel = createSetupPanel();
        Node boardPanel = createBoardPanel();
        Node controls = createControls();
        Node header = createHeader();

        root.setTop(header);
        root.setLeft(setupPanel);
        root.setCenter(boardPanel);
        root.setBottom(controls);
        BorderPane.setMargin(setupPanel, new Insets(0, 18, 0, 0));
        BorderPane.setMargin(controls, new Insets(18, 0, 0, 0));

        Scene scene = new Scene(root, 1180, 760);
        scene.getStylesheets().add(loadStylesheet());
        configureKeyboardShortcuts(scene);

        stage.setTitle("Blackjack JavaFX");
        stage.setMinWidth(920);
        stage.setMinHeight(640);
        stage.setScene(scene);
        stage.show();

        refreshUi();
    }

    private Node createHeader() {
        Label title = new Label("Blackjack");
        title.getStyleClass().add("title");

        Label subtitle = new Label(
                "JavaFX versioon toetab hiirt, klaviatuuri, salvestamist ja laadimist. "
                        + "Sisesta vasakul oma andmed, seejarel alusta mang."
        );
        subtitle.getStyleClass().add("muted");
        subtitle.setWrapText(true);

        VBox box = new VBox(6, title, subtitle);
        box.getStyleClass().add("panel");
        return box;
    }

    private Node createSetupPanel() {
        Label title = new Label("Mangija andmed");
        title.getStyleClass().add("section-title");

        Label intro = new Label(
                "Eesmark on saada 21-le voimalikult lahedale ilma ule minemata.\n"
                        + "Kaardid J, Q ja K annavad 10 punkti ning A annab 1 voi 11."
        );
        intro.getStyleClass().add("muted");
        intro.setWrapText(true);

        nameField = new TextField();
        nameField.setPromptText("Nimi");
        nameField.setOnAction(event -> handleStartGame());

        ageField = new TextField();
        ageField.setPromptText("Vanus");
        ageField.setOnAction(event -> handleStartGame());

        bankrollField = new TextField();
        bankrollField.setPromptText("Stardiraha");

        startGameButton = new Button("Alusta mang");
        startGameButton.getStyleClass().add("action-button");
        startGameButton.setMaxWidth(Double.MAX_VALUE);
        startGameButton.setOnAction(event -> handleStartGame());

        bankrollField.setOnAction(event -> handleStartGame());

        Label saveHint = new Label("Salvestusfail: " + SAVE_FILE);
        saveHint.getStyleClass().add("muted");
        saveHint.setWrapText(true);

        VBox box = new VBox(12,
                title,
                intro,
                new Label("Nimi"),
                nameField,
                new Label("Vanus"),
                ageField,
                new Label("Stardiraha"),
                bankrollField,
                startGameButton,
                new Separator(),
                saveHint
        );
        box.getStyleClass().add("panel");
        box.setPrefWidth(280);
        return box;
    }

    private Node createBoardPanel() {
        Label boardTitle = new Label("Mangu laud");
        boardTitle.getStyleClass().add("section-title");

        statusLabel = new Label();
        statusLabel.setWrapText(true);
        statusLabel.getStyleClass().add("muted");

        VBox statusBox = new VBox(8, boardTitle, statusLabel);
        statusBox.getStyleClass().add("status-box");

        dealerInfoLabel = new Label();
        dealerInfoLabel.getStyleClass().add("muted");
        dealerCardsPane = createCardsPane();

        VBox dealerBox = new VBox(10,
                new Label("Diiler"),
                dealerInfoLabel,
                dealerCardsPane
        );
        dealerBox.getStyleClass().add("panel");
        VBox.setVgrow(dealerCardsPane, Priority.ALWAYS);

        playerInfoLabel = new Label();
        playerInfoLabel.getStyleClass().add("muted");
        playerCardsPane = createCardsPane();

        VBox playerBox = new VBox(10,
                new Label("Mangija"),
                playerInfoLabel,
                playerCardsPane
        );
        playerBox.getStyleClass().add("panel");
        VBox.setVgrow(playerCardsPane, Priority.ALWAYS);

        VBox content = new VBox(16, statusBox, dealerBox, playerBox);
        VBox.setVgrow(dealerBox, Priority.ALWAYS);
        VBox.setVgrow(playerBox, Priority.ALWAYS);

        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setStyle("-fx-background-color: transparent;");
        return scrollPane;
    }

    private FlowPane createCardsPane() {
        FlowPane pane = new FlowPane();
        pane.setHgap(10);
        pane.setVgap(10);
        pane.setPrefWrapLength(520);
        return pane;
    }

    private Node createControls() {
        betField = new TextField();
        betField.setPromptText("Panus");
        betField.setPrefWidth(120);
        betField.setOnAction(event -> handleDeal());

        dealButton = new Button("Jaga");
        hitButton = new Button("Vota kaart");
        standButton = new Button("Jaa pidama");
        nextRoundButton = new Button("Uus voor");
        saveButton = new Button("Salvesta");
        loadButton = new Button("Lae");
        newGameButton = new Button("Uus mang");
        helpButton = new Button("Abi");

        List<Button> buttons = List.of(
                dealButton, hitButton, standButton, nextRoundButton,
                saveButton, loadButton, newGameButton, helpButton
        );
        buttons.forEach(button -> button.getStyleClass().add("action-button"));

        dealButton.setOnAction(event -> handleDeal());
        hitButton.setOnAction(event -> handleHit());
        standButton.setOnAction(event -> handleStand());
        nextRoundButton.setOnAction(event -> handleNextRound());
        saveButton.setOnAction(event -> handleSave());
        loadButton.setOnAction(event -> handleLoad());
        newGameButton.setOnAction(event -> handleResetToSetup());
        helpButton.setOnAction(event -> showHelpDialog());

        bankrollLabel = new Label();
        bankrollLabel.getStyleClass().add("muted");

        statsLabel = new Label();
        statsLabel.getStyleClass().add("muted");

        savePathLabel = new Label();
        savePathLabel.getStyleClass().add("muted");
        savePathLabel.setWrapText(true);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        HBox firstRow = new HBox(10,
                new Label("Panus:"), betField,
                dealButton, hitButton, standButton, nextRoundButton,
                spacer,
                saveButton, loadButton, newGameButton, helpButton
        );
        firstRow.setAlignment(Pos.CENTER_LEFT);

        GridPane infoGrid = new GridPane();
        infoGrid.setHgap(18);
        infoGrid.setVgap(8);
        infoGrid.add(new Label("Raha:"), 0, 0);
        infoGrid.add(bankrollLabel, 1, 0);
        infoGrid.add(new Label("Statistika:"), 0, 1);
        infoGrid.add(statsLabel, 1, 1);
        infoGrid.add(new Label("Salvestus:"), 0, 2);
        infoGrid.add(savePathLabel, 1, 2);

        VBox box = new VBox(14, firstRow, infoGrid);
        box.getStyleClass().add("panel");
        return box;
    }

    private void configureKeyboardShortcuts(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN).match(event)) {
                handleSave();
                event.consume();
                return;
            }
            if (new KeyCodeCombination(KeyCode.L, KeyCombination.SHORTCUT_DOWN).match(event)) {
                handleLoad();
                event.consume();
                return;
            }

            if (scene.getFocusOwner() instanceof TextInputControl) {
                return;
            }

            if (event.getCode() == KeyCode.H && !hitButton.isDisabled()) {
                handleHit();
                event.consume();
            } else if (event.getCode() == KeyCode.S && !standButton.isDisabled()) {
                handleStand();
                event.consume();
            } else if (event.getCode() == KeyCode.N && !nextRoundButton.isDisabled()) {
                handleNextRound();
                event.consume();
            }
        });
    }

    private String loadStylesheet() {
        return Objects.requireNonNull(
                getClass().getResource("/ee/oop/blackjack/styles.css"),
                "Stiililehte ei leitud."
        ).toExternalForm();
    }

    private void handleStartGame() {
        try {
            int age = parseInteger(ageField.getText(), "Vanus peab olema arv.");
            int bankroll = parseInteger(bankrollField.getText(), "Stardiraha peab olema arv.");
            gameEngine.startNewGame(nameField.getText(), age, bankroll);
            betField.clear();
            betField.requestFocus();
            refreshUi();
        } catch (IllegalArgumentException exception) {
            showError("Vigane sisestus", exception.getMessage());
        }
    }

    private void handleDeal() {
        try {
            int bet = parseInteger(betField.getText(), "Panus peab olema arv.");
            gameEngine.dealRound(bet);
            refreshUi();
        } catch (IllegalArgumentException | IllegalStateException exception) {
            showError("Vooru ei saanud alustada", exception.getMessage());
        }
    }

    private void handleHit() {
        try {
            gameEngine.hit();
            refreshUi();
        } catch (IllegalStateException exception) {
            showError("Kaardi votmine ebaonnestus", exception.getMessage());
        }
    }

    private void handleStand() {
        try {
            gameEngine.stand();
            refreshUi();
        } catch (IllegalStateException exception) {
            showError("Seisma jaamine ebaonnestus", exception.getMessage());
        }
    }

    private void handleNextRound() {
        try {
            gameEngine.startNextRound();
            betField.clear();
            betField.requestFocus();
            refreshUi();
        } catch (IllegalStateException exception) {
            showError("Uut vooru ei saanud alustada", exception.getMessage());
        }
    }

    private void handleSave() {
        try {
            saveLoadService.save(gameEngine.createSnapshot(), SAVE_FILE);
            refreshUi();
            showInfo("Mang salvestatud", "Seis salvestati faili:\n" + SAVE_FILE.toAbsolutePath());
        } catch (IllegalStateException exception) {
            showError("Salvestamine ebaonnestus", exception.getMessage());
        } catch (IOException exception) {
            showError("Salvestamine ebaonnestus", "Faili kirjutamine ebaonnestus: " + exception.getMessage());
        }
    }

    private void handleLoad() {
        try {
            gameEngine.restoreSnapshot(saveLoadService.load(SAVE_FILE));
            syncFormWithLoadedState();
            refreshUi();
            showInfo("Mang laaditud", "Seis taastati failist:\n" + SAVE_FILE.toAbsolutePath());
        } catch (IOException exception) {
            showError("Laadimine ebaonnestus", "Faili lugemine ebaonnestus: " + exception.getMessage());
        } catch (IllegalArgumentException | IllegalStateException exception) {
            showError("Laadimine ebaonnestus", exception.getMessage());
        }
    }

    private void handleResetToSetup() {
        gameEngine.resetToSetup();
        betField.clear();
        nameField.requestFocus();
        refreshUi();
    }

    private void refreshUi() {
        GameState state = gameEngine.getState();
        GamePhase phase = state.getPhase();

        if (!state.hasActiveGame()) {
            playerInfoLabel.setText("Mang ei ole veel alustatud.");
            dealerInfoLabel.setText("Diileri kaardid ilmuvad siia, kui voor algab.");
            statsLabel.setText("0 vooru");
            bankrollLabel.setText("-");
            statusLabel.setText(state.getLastMessage());
            savePathLabel.setText(SAVE_FILE.toAbsolutePath().toString());
            playerCardsPane.getChildren().setAll(createPlaceholderLabel("Mangija kaardid ilmuvad siia."));
            dealerCardsPane.getChildren().setAll(createPlaceholderLabel("Diileri kaardid ilmuvad siia."));
            betField.setDisable(true);
            dealButton.setDisable(true);
            hitButton.setDisable(true);
            standButton.setDisable(true);
            nextRoundButton.setDisable(true);
            saveButton.setDisable(true);
            loadButton.setDisable(false);
            newGameButton.setDisable(false);
            return;
        }

        Player player = state.getPlayer();
        Player dealer = state.getDealer();
        RoundStats stats = state.getStats();

        playerInfoLabel.setText(player.getName() + " | punktid: " + player.calculatePoints() + " | kaardid: " + player.getHand().size());
        dealerInfoLabel.setText(buildDealerInfo(state));
        bankrollLabel.setText(player.getBankroll() + " eurot | aktiivne panus: " + player.getCurrentBet() + " eurot");
        statsLabel.setText(
                "voorud: " + stats.getRoundsPlayed()
                        + ", voidud: " + stats.getWins()
                        + ", kaotused: " + stats.getLosses()
                        + ", viigid: " + stats.getPushes()
                        + ", blackjackid: " + stats.getBlackjacks()
        );
        statusLabel.setText(state.getLastMessage());
        savePathLabel.setText(SAVE_FILE.toAbsolutePath().toString());

        renderCards(playerCardsPane, player.getHand(), false);
        renderCards(dealerCardsPane, dealer.getHand(), phase == GamePhase.PLAYER_TURN);

        betField.setDisable(phase != GamePhase.READY_FOR_ROUND);
        dealButton.setDisable(phase != GamePhase.READY_FOR_ROUND);
        hitButton.setDisable(phase != GamePhase.PLAYER_TURN);
        standButton.setDisable(phase != GamePhase.PLAYER_TURN);
        nextRoundButton.setDisable(phase != GamePhase.ROUND_OVER);
        saveButton.setDisable(false);
        loadButton.setDisable(false);
        newGameButton.setDisable(false);
    }

    private void renderCards(FlowPane targetPane, List<Card> cards, boolean hideHoleCard) {
        targetPane.getChildren().clear();

        if (cards.isEmpty()) {
            targetPane.getChildren().add(createPlaceholderLabel("Kaardid puuduvad."));
            return;
        }

        for (int index = 0; index < cards.size(); index++) {
            String text = hideHoleCard && index > 0 ? "Peidetud kaart" : cards.get(index).toString();
            Label cardLabel = new Label(text);
            cardLabel.getStyleClass().add("card-chip");
            cardLabel.setMinWidth(120);
            targetPane.getChildren().add(cardLabel);
        }
    }

    private Label createPlaceholderLabel(String text) {
        Label label = new Label(text);
        label.getStyleClass().add("muted");
        return label;
    }

    private String buildDealerInfo(GameState state) {
        Player dealer = state.getDealer();
        if (state.getPhase() == GamePhase.PLAYER_TURN && !dealer.getHand().isEmpty()) {
            return "Naha on esimene kaart. Teised kaardid avanevad vooru loppus.";
        }
        return dealer.getName() + " | punktid: " + dealer.calculatePoints() + " | kaardid: " + dealer.getHand().size();
    }

    private void syncFormWithLoadedState() {
        Player player = gameEngine.getState().getPlayer();
        nameField.setText(player.getName());
        ageField.setText(String.valueOf(player.getAge()));
        bankrollField.setText(String.valueOf(player.getBankroll()));
    }

    private int parseInteger(String text, String errorMessage) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    private void showHelpDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "", ButtonType.OK);
        alert.setTitle("Abi");
        alert.setHeaderText("Blackjacki luhijuhend");

        TextArea content = new TextArea(
                "1. Sisesta nimi, vanus ja stardiraha ning vajuta Alusta mang.\n"
                        + "2. Kirjuta panus ja vajuta Jaga.\n"
                        + "3. Vooru ajal kasuta nuppu Vota kaart voi Jaa pidama.\n"
                        + "4. Salvesta hetkeseis nupuga Salvesta, lae fail nupuga Lae.\n"
                        + "5. Otseteed: H = vota kaart, S = jaa pidama, N = uus voor, Ctrl+S = salvesta, Ctrl+L = lae.\n"
                        + "6. Kui voor loppeb, vajuta Uus voor. Kui tahad alustada otsast, vajuta Uus mang."
        );
        content.setEditable(false);
        content.setWrapText(true);
        content.setPrefColumnCount(44);
        content.setPrefRowCount(10);
        alert.getDialogPane().setContent(content);
        alert.showAndWait();
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

    private void showInfo(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
    }
}
