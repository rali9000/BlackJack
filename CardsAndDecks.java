import java.util.ArrayList;
import java.util.List;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class CardsAndDecks {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private CardsAndDecks() {
    }

    /**
     * A card object that holds a suit and a value.
     *
     * @author Ali Raouidah
     *
     */
    public static class Card {
        /**
         * The suit and value that will be assigned to this card.
         */
        private final int suit, value;

        /**
         * All names of suits.
         */
        private final String[] suits = { "Spades", "Clubs", "Hearts", "Diamonds"};

        /**
         * All possible card values.
         */
        private final String[] values = { null, "Ace", "2", "3", "4", "5", "6", "7",
                "8", "9", "10", "Jack", "Queen", "King"};

        /**
         * Standard Constructor.
         *
         * @param suit
         *          A number between 0 and 3 which assigns the suit of the card,
         *          the suits are in this order:
         *          spades, clubs, hearts, diamonds
         * @param value
         *          A number between 1 and 13 which assigns the value of the card,
         *          the values are in this order:
         *          Ace, 2, 3, 4, 5, 6, 7, 8, 9, 10, Jack, Queen, King
         */
        public Card(int suit, int value) {
            assert (1 <= value && value < values.length)
                : "Violation of: Card value must be between 1 and 13";
            assert (0 <= suit && suit < suits.length)
                : "Violation of: Card suit must be between 0 and 3";

            this.suit = suit;
            this.value = value;
        }

        /**
         *
         * @param s the index for the suit of the current card
         * @return the suit at s
         */
        public String returnSuits(int s) {
            return this.suits[s];
        }

       /**
        *
        * @param v the index for the value of the current card
        * @return the value at v
        */
       public String returnValues(int v) {
           return this.values[v];
       }

       /**
       *
       * @return the value of this card
       */
      public int returnValue() {
          return this.value;
      }

      /**
      *
      * @return the suit of this card
      */
     public int returnSuit() {
         return this.suit;
     }
    }

    /**
     * The max number of suits, or suits.length in cards class.
     */
    public static final int MAX_SUITS = 4;

    /**
     * The max number of values, or values.length in cards class.
     */
    public static final int MAX_VALUES = 14;

    /**
     * An object that holds all the cards in one deck.
     *
     * @author Ali Raouidah
     *
     */
    public static class Deck {

        /**
         * The total number of cards in a deck.
         */
        private static final int DECK_SIZE = 52;

        /**
         * All the cards in the deck.
         */
        private List<Card> cards;

        /**
        *
        * @return the cards in the deck
        */
       public List<Card> returnCards() {
           return this.cards;
       }

        /**
         * Standard Constructor.
         *
         * @param deckCount
         *          The number of decks to be used, default should be 1,
         *          however, some games such as blackjack use 4
         */
        public Deck(int deckCount) {
            cards = new ArrayList<Card>(DECK_SIZE);
            for (int i = 0; i < deckCount; i++) {
                for (int suit = 0; suit < MAX_SUITS; suit++) {
                    for (int value = 1; value < MAX_VALUES; value++) {
                        cards.add(new Card(suit, value));
                    }
                }
            }
        }

        /**
         * Deals a certain number of cards to the player.
         *
         * @param numCards
         *          the number of cards to be dealt
         * @return the cards drawn by the player.
         */
        public List<Card> deal(int numCards) {
            List<Card> drawnCards = new ArrayList<Card>(numCards);
            for (int i = 0; i < numCards; i++) {
                drawnCards.add(cards.remove(0));
            }
            return drawnCards;
        }
    }

}
