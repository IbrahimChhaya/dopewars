package datawars.events;

import datawars.Game;
import datawars.Message;

public class HomomorphicEncryptionEvent extends Event {
    final static long COST = 150000;  // Very expensive
    final static int CHANCE = 5;      // Rare to encounter
    final static int STRENGTH = 40;   // Significant boost
    final static int DEFENSE = 20;    // Also provides defense

    public HomomorphicEncryptionEvent(Game game) {
        super(game);
        message = new Message(String.format(
            "Would you like to implement homomorphic encryption for %s? (Allows computation on encrypted data)", 
            CURRENCY_FORMATTER.format(COST)));
    }

    @Override
    public boolean inEvent() {
        return hit(CHANCE) && (player.getCash() >= COST) && (player.getStrength() < 100);
    }

    @Override
    public Message handleEvent(boolean yes) {
        Message m;
        if (yes) {
            player.removeCash(COST);
            player.addStrength(STRENGTH);
            player.addDefense(DEFENSE);
            m = new Message(String.format(
                "Homomorphic encryption adds %d attack and %d defense! You can now process data while encrypted.", 
                STRENGTH, DEFENSE));
        } else {
            m = new Message("Maybe next time. Processing data securely would have been nice...");
        }
        return m;
    }

    @Override
    public boolean requiresInput() {
        return true;
    }
} 