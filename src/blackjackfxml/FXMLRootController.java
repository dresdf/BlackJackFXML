package blackjackfxml;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Dragos Secara
 */
public class FXMLRootController implements Initializable {

    private final int WIN_SCORE = 21;
    private Player player;
    private Player dealer;
    private Deck deck;

//<editor-fold defaultstate="collapsed" desc="FXML links">
    //dealer
    @FXML
    private Label dealerHandValue;
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
//</editor-fold>

    //logic
    @FXML
    private void handleButtonAction(ActionEvent event) {

        //hit event.player plays
        if (event.getSource() == hitButton) {

            deck.deal(player);//deal card
            playerHandValue.setText("" + player.getHandValue());//update hand value label
            displayPlayerCards();//update images array

            //check for blackjack or for bust. start endgame if true
            if (player.getHandValue() == WIN_SCORE) {
                endGame();
            } else if (player.getHandValue() > WIN_SCORE) {
                endGame();
            }
            //stand event. player turn finishes. dealer turn starts
        } else if (event.getSource() == standButton) {
            if (dealer.getHandValue() <= player.getHandValue()) {
                //start drawing cards if dealer has less than player
                while (dealer.getHandValue() <= player.getHandValue()) {
                    deck.deal(dealer);//deal card to dealer
                    displayDealerCards();

                    //check for bust or higher than player. start endgame if true
                    if (dealer.getHandValue() > WIN_SCORE) {
                        endGame();
                    } else if (dealer.getHandValue() > player.getHandValue()) {
                        endGame();
                    }
                }
            } else {
                endGame();
            }
        } else if (event.getSource() == dealButton) {
            startGame();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb
    ) {

        clearTableCards();
        this.dealer = new Player("Dealer");//create new dealer
        this.player = new Player("Player");//create new player
        this.deck = new Deck();//create new deck

        hitButton.setDisable(true);
        standButton.setDisable(true);
        dealButton.setDisable(false);

    }

    //returns the parameter for the imageView.setImage() method
    private Image getCardImage(Card card) {
        if (card.getIsVisible()) {
            return card.getCARD_FRONT();
        } else {
            return card.getCARD_BACK();
        }

    }

    //set all hand card images to default
    private void clearTableCards() {
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
    }

    //start a new hand in the same game
    public void startGame() {
        messageLabel.setText("");//clear message text label
        deck.shuffle();//shuffle the deck
        clearTableCards();
        dealer.reset();//reset dealer hand
        player.reset();//reset player hand
        dealerHandValue.setDisable(true);
        dealerHandValue.setText("Hidden");
        
        //start giving cards
        deck.deal(dealer);//card1 to dealer
        dealer.getHand().get(0).setIsVisible(false);// set card1 facedown
        deck.deal(dealer);//card2 to dealer
        displayDealerCards();//update dealer hand images
        deck.deal(player);//card1 to player
        deck.deal(player);//card2 to player
        displayPlayerCards();//update player hand images

        playerHandValue.setText("" + player.getHandValue());//update player hand value label

        hitButton.setDisable(false);
        standButton.setDisable(false);
        dealButton.setDisable(true);
        //check for blackjack and dual blackjack. if true endgame
        if ((dealer.getHandValue() == WIN_SCORE) || (player.getHandValue() == WIN_SCORE)) {
            endGame();
        }

    }

    /**
     * calculates the winner and ends the current hand
     */
    private void endGame() {
        dealer.getHand().get(0).setIsVisible(true);//set dealer card1 faceup
        displayDealerCards();//update dealer  displayed card images
        int dHandValue = dealer.getHandValue();//get final value of dealer hand
        int pHandValue = player.getHandValue();//get final value of player hand
        String winner = "";//message to be displayed after evaluation in message label

        if (dHandValue == WIN_SCORE && pHandValue == WIN_SCORE) {
            //check for blackjack tie. no one wins
            winner = "It's a tie: Dealer: " + dHandValue + " Player: " + pHandValue;
        } else if (dHandValue == WIN_SCORE || pHandValue > WIN_SCORE || (dHandValue < WIN_SCORE && dHandValue > pHandValue)) {
            //dealer wins. check for blackjack, player bust, higher than player

            winner = "DEALER WINS!!!";//set win message
            dealer.setScore(dealer.getScore() + 1);//update dealer score 

        } else if (pHandValue == WIN_SCORE || dHandValue > WIN_SCORE || pHandValue > dHandValue) {
            //player wins.check for blackjack, dealer bust, higher than dealer

            winner = "PLAYER WINS!!!";//set win message
            player.setScore(player.getScore() + 1);//update player score

        }

        messageLabel.setText(winner);//set message label with win message
        dealerHandValue.setDisable(false);
        dealerHandValue.setText("" + dealer.getHandValue());
        dealerScore.setText(dealer.getScore() + "   points");
        playerScore.setText(player.getScore() + "   points");
        hitButton.setDisable(true);
        standButton.setDisable(true);
        dealButton.setDisable(false);

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
