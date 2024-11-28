package datawars.events;

import datawars.Game;
import datawars.Message;

/**
 * Event to randomly remove a random amount of cash
 * @author rob
 */
public class DataBreachEvent extends Event {
  final static int CHANCE = 10;
  final static int PERCENT = 10; // may lose up to 10% of their cash
  
  public DataBreachEvent(Game game){
    super(game);
  }
  
  @Override
  public boolean inEvent() {
    return hit(CHANCE);
  }

  @Override
  public Message handleEvent(boolean yes) {
    int rate = (int) (Math.random() * PERCENT) + 1;
    double pct = rate / 100.0;
    long lose = (long) (player.getCash() * pct);
   
    player.removeCash(lose);
    Message m = new Message(EVENT_COLOR, String.format("A data breach cost you %s in damages!", CURRENCY_FORMATTER.format(lose)));
    
    return m;
  }

  @Override
  public boolean requiresInput() {
    return false;
  }
}
