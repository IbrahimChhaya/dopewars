package datawars.events;

import datawars.Game;
import datawars.Message;

public class DifferentialPrivacyEvent extends Event {
    final static long COST = 100000;
    final static int CHANCE = 6;
    final static int DEFENSE = 35;
    final static int SPACE = 25;   // Allows more data storage due to better aggregation

    public DifferentialPrivacyEvent(Game game) {
        super(game);
        message = new Message(String.format(
            "Would you like to implement differential privacy for %s? (Adds mathematical privacy guarantees)", 
            CURRENCY_FORMATTER.format(COST)));
    }

    @Override
    public boolean inEvent() {
        return hit(CHANCE) && (player.getCash() >= COST) && (player.getDefense() < 100);
    }

    @Override
    public Message handleEvent(boolean yes) {
        Message m;
        if (yes) {
            player.removeCash(COST);
            player.addDefense(DEFENSE);
            player.setSpace(player.getSpace() + SPACE);
            m = new Message(String.format(
                "Differential privacy adds %d defense and %d storage! Your data aggregation is now mathematically private.", 
                DEFENSE, SPACE));
        } else {
            m = new Message("Keeping your old privacy systems? The regulators won't be happy...");
        }
        return m;
    }

    @Override
    public boolean requiresInput() {
        return true;
    }
} 