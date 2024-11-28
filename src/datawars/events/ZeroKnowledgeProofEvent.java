package datawars.events;

import datawars.Game;
import datawars.Message;

public class ZeroKnowledgeProofEvent extends Event {
    final static long COST = 200000;  // Most expensive
    final static int CHANCE = 4;      // Very rare
    final static int STRENGTH = 30;
    final static int DEFENSE = 30;    // Balanced boost to both

    public ZeroKnowledgeProofEvent(Game game) {
        super(game);
        message = new Message(String.format(
            "Would you like to implement zero-knowledge proofs for %s? (Verify data without revealing it)", 
            CURRENCY_FORMATTER.format(COST)));
    }

    @Override
    public boolean inEvent() {
        return hit(CHANCE) && (player.getCash() >= COST) && 
               (player.getStrength() < 100 || player.getDefense() < 100);
    }

    @Override
    public Message handleEvent(boolean yes) {
        Message m;
        if (yes) {
            player.removeCash(COST);
            player.addStrength(STRENGTH);
            player.addDefense(DEFENSE);
            m = new Message(String.format(
                "Zero-knowledge proofs add %d attack and %d defense! You can now prove data validity without revealing it.", 
                STRENGTH, DEFENSE));
        } else {
            m = new Message("Sticking with traditional proofs? Hope you like sharing sensitive data...");
        }
        return m;
    }

    @Override
    public boolean requiresInput() {
        return true;
    }
} 