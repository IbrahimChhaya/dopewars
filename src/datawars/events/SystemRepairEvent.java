package datawars.events;

import datawars.Game;
import datawars.Message;

/**
 * Event to aks the user if the ywould like to heal themselves
 * @author rob
 */
public class SystemRepairEvent extends Event {

  final static int CHANCE = 5;
  final static int COST_PER = 1500;
  final static long MIN_COST = 30000; // don't offer until they're at 80% or lower

  public SystemRepairEvent(Game game) {
    super(game);
  }

  @Override
  public Message getMessage() {
    System.out.println(healAmount());
    return new Message(String.format("Would you like to repair your systems for %s?", CURRENCY_FORMATTER.format(healAmount())));
  }

  @Override
  public boolean inEvent() {
    boolean b = false;

    if (hit(CHANCE) && (healAmount() > MIN_COST) && (player.getCash() >= healAmount())) {
      b = true;
    }

    return b;
  }

  @Override
  public Message handleEvent(boolean yes) {
    Message m;

    if (yes) {
      long cost = healAmount();
      int n = player.getMaxHealth() - player.getHealth();

      player.removeCash(cost);
      player.heal(n);

      m = new Message(String.format("System integrity restored by %d points!", n));
    } else {
      m = new Message("Your systems remain vulnerable.");
    }

    return m;
  }

  @Override
  public boolean requiresInput() {
    return true;
  }

  private long healAmount() {
    int n = player.getMaxHealth() - player.getHealth();
    return (long)(n * COST_PER);
  }
}
