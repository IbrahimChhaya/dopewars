package datawars.events;

import datawars.Fight;
import datawars.Game;
import datawars.Message;

/**
 * Event to handle if fights are to pop up
 * @author rob
 */
public class LegalThreatEvent extends Event {
  final static int CHANCE = 10;

  public LegalThreatEvent(Game game){
    super(game);
  }
  
  @Override
  public boolean inEvent() {
    return hit(CHANCE);
  }

  @Override
  public Message handleEvent(boolean yes) {
    Fight f = game.startFight();
    return new Message(String.format("%s is threatening legal action! How do you respond?", f.getNpc()));
  }

  @Override
  public boolean requiresInput() {
    return false;
  }
}
