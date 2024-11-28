package datawars.events;

import datawars.Game;
import datawars.Message;

/**
 * Event to aks the user if they want to upgrade their weapon
 * @author rob
 */
public class EncryptionEvent extends Event {

  final static long COST = 30000;
  final static int CHANCE = 10;
  final static int STRENGTH = 15;
  
  public EncryptionEvent(Game game) {
    super(game);
    message = new Message(String.format("Would you like to upgrade your encryption system for %s?", CURRENCY_FORMATTER.format(COST)));
  }

  @Override
  public boolean inEvent() {
    boolean b = false;
    
    if(hit(CHANCE) && (player.getCash() >= COST && player.getStrength() == STARTING_STRENGTH)){
      b = true;
    }

    return b;
  }

  @Override
  public Message handleEvent(boolean yes) {
    Message m;
    
    if (yes) {
      player.removeCash(COST);
      player.addStrength(STRENGTH);
      m = new Message(String.format("Your enhanced encryption adds %d attack power!", STRENGTH));
    } else {
      m = new Message("Keeping your old encryption? Good luck...");
    }

    return m;
  }

  @Override
  public boolean requiresInput() {
    return true;
  }
}
