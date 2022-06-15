import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Put a short phrase describing the program here.
 *
 * @author Ali Raouidah
 *
 */
public final class Blackjack {

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

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private Blackjack() {
    }

    /**
     *
     * @param hand
     * @return the total value of this hand's cards
     */
    private static int handValue(List<Card> hand) {
        final int faceValue = 10;
        final int aceValue = 11;
        final int winValue = 21;

        int handValue = 0;
        for (Card c : hand) {
            if (c.value == 1) {
                if (handValue > winValue - aceValue) {
                    handValue += 1;
                } else {
                    handValue += aceValue;
                }
            } else if (c.value < aceValue) {
                handValue += Integer.parseInt(c.values[c.value]);
            } else {
                handValue += faceValue;
            }
        }
        return handValue;
    }

    /**
    *
    * @param c
    *      the card that was recently drawn.
    */
   private static void printCardDraw(Card c) {
       final int eight = 8;
       final int ace = 1;

       if (c.value == ace || c.value == eight) {
           System.out.print(" an "
                   + c.values[c.value] + " of "
                   + c.suits[c.suit] + ".\n");
       } else {
           System.out.print(" a "
                   + c.values[c.value] + " of "
                   + c.suits[c.suit] + ".\n");
       }
   }

   /**
   *
   * @param chips
   *     the number of chips the player has
   * @param in
   *     simple reader that takes inputs from the console
   * @return the player's bet
   */
   private static int betting(int chips, Scanner in) {
       final int minBet = 100;
       System.out.println("\nHow many chips would you like to bet?"
               + "\nThe minimum bet is " + minBet + " chips.");
       String response = in.nextLine();
       int bet = Integer.parseInt(response);
       boolean validResponse = false;
       if (!validResponse) {
           if (bet < minBet) {
               System.out.println("Please insert a bet worth more than the minimum bet. "
                       + "The minimum bet is " + minBet + " chips.");
               bet = betting(chips, in);
           } else if (bet > chips) {
               System.out.println("Please insert a bet worth less than the your current "
                       + "balance. Your current balance is " + chips + " chips.");
               bet = betting(chips, in);
           } else {
               validResponse = true;
           }

       }
       return bet;
   }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        final int deckCount = 4;
        final int winValue = 21;
        final int dealerStand = 17;
        final int startingChips = 10000;
        final double blackjackMultiplier = 1.5;

        Deck deck = new Deck(deckCount);
        Collections.shuffle(deck.cards);

        int chips = startingChips;
        System.out.println("You begin with " + chips + " chips.");

        boolean playing = true;
        boolean bankrupt = false;
        while (playing && !bankrupt) {
            int bet = betting(chips, in);

            List<Card> dealersHand = new ArrayList<Card>();
            List<Card> dealersDraw = deck.deal(2);
            Card dealerCard = dealersDraw.get(0);
            System.out.print("\nThe dealer reveals their first card."
                    + "\nIt's");
            printCardDraw(dealerCard);
            System.out.println();
            dealersHand.addAll(dealersDraw);

            List<Card> playersHand = new ArrayList<Card>();
            List<Card> playersDraw = deck.deal(2);

            for (Card c : playersDraw) {
                System.out.print("You drew");
                printCardDraw(c);
            }

            playersHand.addAll(playersDraw);

            int playerValue = handValue(playersHand);
            System.out.println("The total value of your hand is " + playerValue + ".");

            boolean blackjack = false;
            if (playerValue == winValue) {
                System.out.println("\nBlackjack! The player wins!");
                chips += (bet * blackjackMultiplier);
                blackjack = true;
            }

            if (!blackjack) {
                boolean stand = false;
                while (!stand && playerValue < winValue) {
                    System.out.println("\nWould you like to hit (h) or stand (s)?");
                    String response = in.nextLine();
                    response = response.toLowerCase();
                    if (response.equals("h") || response.equals("hit")) {
                        playersDraw = deck.deal(1);
                        Card drawnCard = playersDraw.remove(0);
                        System.out.print("You drew");
                        printCardDraw(drawnCard);
                        playersHand.add(drawnCard);
                        playerValue = handValue(playersHand);
                        System.out.println("The total value of your hand is "
                                + playerValue + ".");
                    } else {
                        stand = true;
                        Card dealerCard2 = dealersHand.get(1);
                        System.out.print("\nThe dealer reveals their second card."
                                + "\nIt's");
                        printCardDraw(dealerCard2);
                        int dealerValue = handValue(dealersHand);
                        System.out.println("The total value of the dealer's hand is "
                                + dealerValue + ".");
                    }
                }

                if (playerValue <= winValue) {
                    int dealerValue = handValue(dealersHand);

                    while (dealerValue < dealerStand) {
                        dealersDraw = deck.deal(1);
                        dealerCard = dealersDraw.remove(0);
                        System.out.print("\nThe dealer drew");
                        printCardDraw(dealerCard);
                        dealersHand.add(dealerCard);
                        dealerValue = handValue(dealersHand);
                        System.out.print("The total value of the dealer's hand is "
                                + dealerValue + ".");
                    }

                    if (dealerValue > winValue) {
                        System.out.println("\nThe dealer busts, and the player wins!");
                        chips += bet;
                        System.out.println("\nYou've won " + bet + " chips!"
                                + "\nYour current balance is "
                                + chips + " chips!");
                    } else {
                        switch (Integer.compare(dealerValue, playerValue)) {
                            case 1:
                                System.out.println("\nThe dealer wins.");
                                chips -= bet;
                                System.out.println("\nYou've lost " + bet + " chips."
                                        + "\nYour current balance is "
                                        + chips + " chips.");
                                break;
                            case 0:
                                System.out.print("\nNobody won. "
                                        + "\nYour current balance is "
                                        + chips + " chips.");
                                break;
                            case -1:
                                System.out.println("\nThe player wins.");
                                chips += bet;
                                System.out.println("\nYou've won " + bet + " chips!"
                                        + "\nYour current balance is "
                                        + chips + " chips!");
                                break;
                            default:
                                break;
                        }
                    }
                } else {
                    System.out.println("\nThe player busts, and the dealer wins.");
                    chips -= bet;
                    System.out.println("\nYou've lost " + bet + " chips."
                            + "\nYour current balance is "
                            + chips + " chips.");
                }
            }
            if (chips > 0) {
                System.out.println("\nWould you like to play again? Yes (y) or No (n).");
                String response = in.nextLine();
                response = response.toLowerCase();
                if (response.equals("n") || response.equals("no")) {
                    playing = false;
                }
            } else {
                System.out.println("\nYou've lost all your chips.");
                playing = false;
            }
        }
        System.out.println("\nYou leave with " + chips + " chips, "
                + "and started with " + startingChips + ".");
        /*
         * Close input and output streams
         */
        in.close();
        System.out.close();
    }

}
