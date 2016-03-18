package blackjackfxml;

import java.util.ArrayList;

/**
 *
 * @author Dragos Secara
 */
public class Player {

    private String name;
    private ArrayList<Card> hand;
    private int handValue;
    private int score = 0;

   

    /**
     * receive a card from the deck an adds it to the hand. Updates the current hand value
     *
     * @param card card received from the deck
     */
    public void takeCard(Card card) {
        this.hand.add(card);
        checkAces(card);
        this.handValue += card.getVALUE();
    }

    /**
     * set value of aces based on the current hand value
     *
     * @param card card to be checked
     */
    public void checkAces(Card card) {
        if (card.getRANK().equals(Rank.ACE)) {
            if (this.getHandValue() <= 10) {
                card.setVALUE(11);
            } else {
                card.setVALUE(1);
            }
        }
    }

    /**
     * reset the handvalue and clear list of cards in hand
     */
    public void reset() {
        this.hand.clear();
        this.handValue = 0;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public int getHandValue() {
        return handValue;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();

    }

}
