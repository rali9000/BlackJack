import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * A program where you can play Blackjack.
 *
 * @author Ali Raouidah
 *
 */
public final class Blackjack {



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
   private static int handValue(List<CardsAndDecks.Card> hand) {
       final int faceValue = 10;
       final int aceValue = 11;

       int handValue = 0;
       for (CardsAndDecks.Card c : hand) {
           if (c.returnValue() == 1) {
               if (handValue > WIN_VALUE - aceValue) {
                   handValue += 1;
               } else {
                   handValue += aceValue;
               }
           } else if (c.returnValue() < aceValue) {
               handValue += Integer.parseInt(c.returnValues(c.returnValue()));
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
   private static void printCardDraw(CardsAndDecks.Card c) {
       final int eight = 8;
       final int ace = 1;

       if (c.returnValue() == ace || c.returnValue() == eight) {
           System.out.print(" an "
                   + c.returnValues(c.returnValue()) + " of "
                   + c.returnSuits(c.returnSuit()) + ".\n");
       } else {
           System.out.print(" a "
                   + c.returnValues(c.returnValue()) + " of "
                   + c.returnSuits(c.returnSuit()) + ".\n");
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
    * Number of decks to be shuffled into the game.
    */
   static final int DECK_COUNT = 1;

   /**
    * Value required to win, going over means busting.
    */
   static final int WIN_VALUE = 21;

   /**
    * Deck value the dealer must stand on.
    */
   static final int DEALER_STAND = 17;

   /**
    * Chips the player starts the game with.
    */
   static final int STARTING_CHIPS = 10000;

   /**
    * Multiplier for winnings when the player gets blackjack.
    */
   static final double BLACKJACK_MULTIPLIER = 1.5;

   /**
    * Calculates whether the dealer or player won, and outputs a corresponding message.
    *
    * @param dealerValue
    *      value of the dealer's hand
    * @param playerValue
    *      value of the player's hand
    * @param chips
    *      how many chips the player has remaining
    * @param bet
    *      how much the player bet
    * @return the player's remaining chips
    */
   private static int winOrLose(int dealerValue, int playerValue, int chips, int bet) {
       switch (Integer.compare(dealerValue, playerValue)) {
           case 1:
               System.out.println("\nThe dealer wins.");
               System.out.println("\nYou've lost " + bet + " chips."
                       + "\nYour current balance is "
                       + (chips - bet) + " chips.\n");
               return chips - bet;
           case 0:
               System.out.println("\nNobody won. "
                       + "\nYour current balance is "
                       + chips + " chips.\n");
               return chips;
           case -1:
               System.out.println("\nThe player wins.");
               System.out.println("\nYou've won " + bet + " chips!"
                       + "\nYour current balance is "
                       + (chips + bet) + " chips.\n");
               return chips + bet;
           default:
               return -1;
       }
   }

   /**
    * Asks the player if they'd like to play again after a hand.
    *
    * @param in
    *      the input stream
    * @return whether the player would like to play again or not.
    */
   private static boolean playAgain(Scanner in) {
       System.out.println("\nWould you like to play again? Yes (y) or No (n).");
       String response = in.nextLine();
       response = response.toLowerCase();
       boolean validResponse = false;
       boolean playing = true;
       while (!validResponse) {
           if (response.equals("n") || response.equals("no")) {
               playing = false;
               validResponse = true;
           } else if (response.equals("y") || response.equals("yes")) {
               validResponse = true;
           } else {
               System.out.println("\nPlease input a valid response.");
           }
       }
       return playing;
   }

   /**
    * When a player decides they are no longer playing, a message is sent to the console
    * letting them know how many chips they gained or lost.
    *
    * @param chips
    *      the number of chips the player has remaining.
    */
   private static void exitMessage(int chips) {
       System.out.println("You leave with " + chips + " chips, "
               + "and started with " + STARTING_CHIPS + ".");
       if (chips > STARTING_CHIPS) {
           System.out.println("You made " + (chips - STARTING_CHIPS) + " chips!");
       } else if (chips < STARTING_CHIPS) {
           System.out.println("You lost " + (chips - STARTING_CHIPS) + " chips.");
       } else {
           System.out.println("You broke even.");
       }
   }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int chips = STARTING_CHIPS;
        System.out.println("You begin with " + chips + " chips.");
        CardsAndDecks.Deck deck = new CardsAndDecks.Deck(DECK_COUNT);
        Collections.shuffle(deck.returnCards());

        int turn = 0;
        boolean playing = true;
        boolean bankrupt = false;
        while (playing && !bankrupt) {
            int bet = betting(chips, in);

            List<CardsAndDecks.Card> dealersHand = new ArrayList<CardsAndDecks.Card>();
            List<CardsAndDecks.Card> dealersDraw = deck.deal(2);
            CardsAndDecks.Card dealerCard = dealersDraw.get(0);
            System.out.print("\nThe dealer reveals their first card."
                    + "\nIt's");
            printCardDraw(dealerCard);
            System.out.println();
            dealersHand.addAll(dealersDraw);

            List<CardsAndDecks.Card> playersHand = new ArrayList<CardsAndDecks.Card>();
            List<CardsAndDecks.Card> playersDraw = deck.deal(2);

            for (CardsAndDecks.Card c : playersDraw) {
                System.out.print("You drew");
                printCardDraw(c);
            }

            playersHand.addAll(playersDraw);

            int playerValue = handValue(playersHand);
            System.out.println("The total value of your hand is " + playerValue + ".");

            boolean blackjack = false;
            if (playerValue == WIN_VALUE) {
                System.out.println("\nBlackjack! The player wins!");
                chips += (bet * BLACKJACK_MULTIPLIER);
                blackjack = true;
                System.out.println("\nYour current balance is " + chips + " chips!");
            }

            if (!blackjack) {
                boolean stand = false;
                while (!stand && playerValue < WIN_VALUE) {
                    System.out.println("\nWould you like to hit (h) or stand (s)?");
                    if (turn == 0) {
                        System.out.println("You may also double down (d).");
                    }
                    String response = in.nextLine();
                    response = response.toLowerCase();

                    switch (response) {
                        case "h":
                        case "hit":
                            turn++;
                            playersDraw = deck.deal(1);
                            CardsAndDecks.Card drawnCard = playersDraw.remove(0);
                            System.out.print("You drew");
                            printCardDraw(drawnCard);
                            playersHand.add(drawnCard);
                            playerValue = handValue(playersHand);
                            System.out.println("The total value of your hand is "
                                    + playerValue + ".");
                            break;
                        case "s":
                        case "stand":
                            stand = true;
                            break;
                        case "d":
                        case "double":
                        case "double down":
                            if (turn == 0) {
                                System.out.println("Your bet has increased to "
                                        + bet + " chips.");
                                playersDraw = deck.deal(1);
                                CardsAndDecks.Card dDrawnCard = playersDraw.remove(0);
                                System.out.print("You drew");
                                printCardDraw(dDrawnCard);
                                playersHand.add(dDrawnCard);
                                playerValue = handValue(playersHand);
                                bet *= 2;
                                stand = true;
                                System.out.println("The total value of your hand is "
                                        + playerValue + ".");
                            }
                            break;
                        default:
                            break;
                    }

                    if (stand) {
                        CardsAndDecks.Card dealerCard2 = dealersHand.get(1);
                        System.out.print("\nThe dealer reveals their second card."
                                + "\nIt's");
                        printCardDraw(dealerCard2);
                        int dealerValue = handValue(dealersHand);
                        System.out.println("The total value of the dealer's hand is "
                                + dealerValue + ".");
                    }
                }

                if (playerValue <= WIN_VALUE) {
                    int dealerValue = handValue(dealersHand);

                    while (dealerValue < DEALER_STAND) {
                        dealersDraw = deck.deal(1);
                        dealerCard = dealersDraw.remove(0);
                        System.out.print("\nThe dealer drew");
                        printCardDraw(dealerCard);
                        dealersHand.add(dealerCard);
                        dealerValue = handValue(dealersHand);
                        System.out.print("The total value of the dealer's hand is "
                                + dealerValue + ".");
                    }

                    if (dealerValue > WIN_VALUE) {
                        System.out.println("\nThe dealer busts, and the player wins!");
                        chips += bet;
                        System.out.println("\nYou've won " + bet + " chips!"
                                + "\nYour current balance is "
                                + chips + " chips!");
                    } else {
                        chips = winOrLose(dealerValue, playerValue, chips, bet);
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
                playing = playAgain(in);
            } else {
                System.out.println("You've lost all your chips.");
                playing = false;
            }
        }
        exitMessage(chips);

        /*
         * Close input and output streams
         */
        in.close();
        System.out.close();
    }

}
