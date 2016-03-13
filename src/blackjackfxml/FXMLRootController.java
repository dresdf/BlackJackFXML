package blackjackfxml;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author drago
 */
public class FXMLRootController implements Initializable {

    private final int WIN_SCORE = 21;
    private Player player;
    private Player dealer;
    private Deck deck;

    //dealer
    @FXML
    private Label dealerScore;
    @FXML
    private HBox dealerLabels;
    @FXML
    private HBox dealerCards;
    @FXML
    private ImageView dealerCard1;
    @FXML
    private ImageView dealerCard2;
    @FXML
    private ImageView dealerCard3;
    @FXML
    private ImageView dealerCard4;
    @FXML
    private ImageView dealerCard5;
    @FXML
    private ImageView dealerCard6;
    @FXML
    private ImageView dealerCard7;

    //middle
    @FXML
    private Label messageLabel;

    //player
    @FXML
    private Label playerHandValue;
    @FXML
    private Label playerName;
    @FXML
    private Label playerScore;
    @FXML
    private HBox playerLabels;
    @FXML
    private HBox playerCards;
    @FXML
    private ImageView playerCard1;
    @FXML
    private ImageView playerCard2;
    @FXML
    private ImageView playerCard3;
    @FXML
    private ImageView playerCard4;
    @FXML
    private ImageView playerCard5;
    @FXML
    private ImageView playerCard6;
    @FXML
    private ImageView playerCard7;

    @FXML
    private GridPane playerButtons;
    @FXML
    private Button hitButton;
    @FXML
    private Button standButton;
    @FXML
    private Button dealButton;

    //logic
    @FXML
    private void handleButtonAction(ActionEvent event) {
        if (event.getSource() == hitButton) {
            deck.deal(player);
            playerHandValue.setText("" + player.getHandValue());

            if (player.getHandValue() == 21) {
                endGame();
            } else if (player.getHandValue() > WIN_SCORE) {
                endGame();
            } else {
                displayPlayerCards();
            }
        } else if (event.getSource() == standButton) {
            while (dealer.getHandValue() < 17) {
                deck.deal(dealer);
                if (dealer.getHandValue() > WIN_SCORE) {
                    endGame();
                    break;
                } else if (dealer.getHandValue() > player.getHandValue()) {
                    endGame();
                    break;
                }
            }
        } else if (event.getSource() == dealButton) {
            startGame();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        dealerCard1.setImage(new Image(getClass().getResourceAsStream("img/no_card.png")));
        dealerCard2.setImage(new Image(getClass().getResourceAsStream("img/no_card.png")));
        dealerCard3.setImage(new Image(getClass().getResourceAsStream("img/no_card.png")));
        dealerCard4.setImage(new Image(getClass().getResourceAsStream("img/no_card.png")));
        dealerCard5.setImage(new Image(getClass().getResourceAsStream("img/no_card.png")));
        dealerCard6.setImage(new Image(getClass().getResourceAsStream("img/no_card.png")));
        dealerCard7.setImage(new Image(getClass().getResourceAsStream("img/no_card.png")));

        playerCard1.setImage(new Image(getClass().getResourceAsStream("img/no_card.png")));
        playerCard2.setImage(new Image(getClass().getResourceAsStream("img/no_card.png")));
        playerCard3.setImage(new Image(getClass().getResourceAsStream("img/no_card.png")));
        playerCard4.setImage(new Image(getClass().getResourceAsStream("img/no_card.png")));
        playerCard5.setImage(new Image(getClass().getResourceAsStream("img/no_card.png")));
        playerCard6.setImage(new Image(getClass().getResourceAsStream("img/no_card.png")));
        playerCard7.setImage(new Image(getClass().getResourceAsStream("img/no_card.png")));

        this.dealer = new Player("Dealer");
        this.player = new Player("Gigel");
        this.deck = new Deck();
        playerName.setText(player.getName());

    }

    //returns the parameter for the imageView.setImage() method
    private Image getCardImage(Card card) {
        if (card.getIsVisible()) {
            return card.getCARD_FRONT();
        } else {
            return card.getCARD_BACK();
        }

    }

    public void startGame() {
        messageLabel.setText("");
        deck.shuffle();
        deck.deal(dealer);
        dealer.getHand().get(0).setIsVisible(false);
        deck.deal(dealer);
        displayDealerCards();
        deck.deal(player);
        deck.deal(player);
        displayPlayerCards();

        playerHandValue.setText("" + player.getHandValue());

        if ((dealer.getHandValue() == WIN_SCORE) && (player.getHandValue() == WIN_SCORE)) {
            endGame();
        } else if ((dealer.getHandValue() == WIN_SCORE) || (player.getHandValue() == WIN_SCORE)) {
            endGame();
        }

    }

    /**
     * calculates the winner and ends the current hand
     */
    private void endGame() {
        dealer.getHand().get(0).setIsVisible(true);
        displayDealerCards();
        int dHandValue = dealer.getHandValue();
        int pHandValue = player.getHandValue();
        String winner = "";

        if (dHandValue == WIN_SCORE && pHandValue == WIN_SCORE) {
            winner = "It's a tie: Dealer: " + dHandValue + " Player: " + pHandValue;
        } else if (dHandValue == pHandValue) {
            winner = "It's a tie: Dealer: " + dHandValue + " Player: " + pHandValue;
        } else if (dHandValue == WIN_SCORE || pHandValue > WIN_SCORE || (dHandValue < WIN_SCORE && dHandValue > pHandValue)) {
            if (dealer.getHandValue() == 21) {
                System.out.println("!!!!!BLACKJACK!!!!!");
            }
            winner = "DEALER WINS!!!";
            dealer.setScore(dealer.getScore() + 1);

        } else if (pHandValue == WIN_SCORE || dHandValue > WIN_SCORE || pHandValue > dHandValue) {
            if (player.getHandValue() == 21) {
                System.out.println("!!!!!BLACKJACK!!!!!");
            }
            winner = player.getName() + " WINS!!!";
            player.setScore(player.getScore() + 1);

        }

        messageLabel.setText(winner);
        dealer.reset();
        player.reset();

    }

    private void displayPlayerCards() {
        ImageView[] playerStack = {playerCard1, playerCard2, playerCard3, playerCard4, playerCard5, playerCard6, playerCard7};
        if (player.getHand().isEmpty()) {
            for (ImageView playerStack1 : playerStack) {
                playerStack1.setImage(new Image(getClass().getResourceAsStream("img/no_card.png")));
            }
        } else {
            for (int i = 0; i < player.getHand().size(); i++) {
                playerStack[i].setImage(getCardImage(player.getHand().get(i)));
            }
        }
    }

    private void displayDealerCards() {
        ImageView[] playerStack = {dealerCard1, dealerCard2, dealerCard3, dealerCard4, dealerCard5, dealerCard6, dealerCard7};
        if (dealer.getHand().isEmpty()) {
            for (ImageView playerStack1 : playerStack) {
                playerStack1.setImage(new Image(getClass().getResourceAsStream("img/no_card.png")));
            }
        } else {
            for (int i = 0; i < dealer.getHand().size(); i++) {
                playerStack[i].setImage(getCardImage(dealer.getHand().get(i)));
            }
        }
    }

}//end of class
