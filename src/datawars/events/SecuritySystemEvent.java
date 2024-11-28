package datawars.events;

import datawars.Game;
import datawars.Message;
import datawars.Player;
import datawars.TraderConstants;

/**
 * Handles the event where we ask the user if they want to upgrade their armor
 * @author rob
 */
public class SecuritySystemEvent extends Event {

  final static long COST = 25000;
  final static int CHANCE = 12;
  final static int DEFENSE = 10;

  public SecuritySystemEvent(Game game) {
    super(game);
    message = new Message(String.format("Would you like to upgrade your security system for %s?", CURRENCY_FORMATTER.format(COST)));
  }
  
  @Override
  public boolean inEvent() {
    boolean b = false;

    if(hit(CHANCE) && (player.getCash() >= COST && player.getDefense() == STARTING_DEFENSE)){
      b = true;
    }

    return b;
  }

  @Override
  public Message handleEvent(boolean yes) {
    Message m;

    if (yes) {
      player.removeCash(COST);
      player.addDefense(DEFENSE);
      m = new Message(String.format("Your improved security system adds %d defense!", DEFENSE));
    } else {
      m = new Message("Hope your current security holds up...");
    }

    return m;
  }

  @Override
  public boolean requiresInput() {
    return true;
  }
}
