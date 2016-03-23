package blackjackfxml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 *
 *@author Dragos Secara
 */
public class Deck {

    private ArrayList<Card> cards = new ArrayList<>();
    private int packsInDeck;

    Random rand;

    /**
     * shuffles the elements in cards
     */
    public void shuffle() {
        rand = new Random();
        int shuffles = rand.nextInt(20) + 30;
        for (int i = 0; i < shuffles; i++) {
            Collections.shuffle(this.cards);
        }
    }

    /**
     * passes the first element on the cards list to a player and removes that element from the list
     *
     * @param player player that receives the card
     */
    public void deal(Player player) {
        if (this.getCardsInDeck() > 0) {
            player.takeCard(this.cards.get(0));
            this.cards.remove(0);
            this.cards.trimToSize();
        }
    }

    //add 52 standard cards into the cards array
    private void createCardPack() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                this.cards.add(new Card(suit, rank));
            }
        }
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public int getCardsInDeck() {
        return cards.size();
    }

    public int getPacksInDeck() {
        return packsInDeck;
    }

    /**
     * create a deck containing multiple packs of cards with each pack containing 52 cards
     *
     * @param numberOfPacks = number of packs in the deck
     */
    public Deck(int numberOfPacks) {
        this.packsInDeck = numberOfPacks;
        for (int i = 0; i < this.packsInDeck; i++) {
            createCardPack();
        }
    }

    /**
     * create a deck containing one standard pack of 52 cards
     */
    public Deck() {
        this(1);
    }

}//end of class
