package blackjackfxml;

import javafx.scene.image.Image;

/**
 *
 * @author Dragos Secara
 */
public class Card {

    private final Rank RANK;
    private final Suit SUIT;
    private int value;
    private boolean isVisible = true;
    private final Image CARD_FRONT;
    private final Image CARD_BACK;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (isVisible) {
            sb.append("[").append(this.getRANK()).append(" of ").append(this.getSUIT()).append("]");
        } else {
            sb.append("[HIDDEN CARD]");
        }
        return sb.toString();
    }

    private Image getCardImage(Card card) {
        String fileName = "img/" + (this.SUIT.toString() + this.value).toLowerCase() + ".png";
        return new Image(getClass().getResourceAsStream(fileName));

    }

    public Rank getRANK() {
        return RANK;
    }

    public Suit getSUIT() {
        return SUIT;
    }

    public int getVALUE() {
        return value;
    }

    public boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public Image getCARD_FRONT() {
        return CARD_FRONT;
    }

    public Image getCARD_BACK() {
        return CARD_BACK;
    }
    


    /**
     * change the value for aces only
     *
     * @param value
     */
    public void setVALUE(int value) {
        if (this.RANK.equals(Rank.ACE)) {
            this.value = value;
        }
    }

    public Card(Suit suit, Rank rank) {
        this.SUIT = suit;
        this.RANK = rank;
        this.value = rank.value;
//        this.cardImageFile = (this.SUIT.toString() + this.value).toLowerCase() + ".png";
        this.CARD_FRONT = getCardImage(this);
        this.CARD_BACK = new Image(getClass().getResourceAsStream("img/card_back.png"));
    }

}
