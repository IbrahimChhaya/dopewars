package datawars;

import java.util.*;

import datawars.events.*;
import datawars.exceptions.*;

/**
 * Main dopewars game class. Holds state for the game
 * see http://gotgoodlist.com/dopewars-prices-stats-and-facts for some changes data from the original dopewars
 */
public final class Game implements TraderConstants {

  static private Game instance = null;
  final static private Product[] products = {
    new Product("Browser History", 1000, 4500),
    new Product("Email Lists", 15000, 30000),
    new Product("Location Data", 450, 1500),
    new Product("Shopping Habits", 5000, 14000),
    new Product("Social Connections", 10, 60),
    new Product("Medical Records", 1500, 4500),
    new Product("Financial Data", 500, 1300),
    new Product("Search History", 1000, 3500),
    new Product("Dating Profiles", 100, 700),
    new Product("Gaming Habits", 600, 1400),
    new Product("Browsing Patterns", 70, 250),
    new Product("Social Media Posts", 300, 900)
  };
  final static private Location[] locations = {
    new Location("EU (GDPR)", products),
    new Location("California (CCPA)", products),
    new Location("Brazil (LGPD)", products),
    new Location("South Africa (POPIA)", products),
    new Location("China (PIPL)", products),
    new Location("Japan (APPI)", products),
    new Location("Australia (Privacy Act)", products),
    new Location("Canada (PIPEDA)", products)
  };
  final static private Npc[] npcs = {
    new Npc("Junior Analyst", 20, 20, 5, 2000, 5000),
    new Npc("Privacy Advocate", 50, 25, 0, 10000, 20000),
    new Npc("Rival Broker", 80, 25, 2, 50000, 75000, 2),
    new Npc("Federal Agent", 300, 40, 0, 160000, 250000, 3)
  };
  private Player player;
  private Location location;
  private Trade currentTrade;
  private int day;
  private Event[] gameEvents;
  private ArrayList<Event> events;
  private ArrayList<Message> messages;
  private Fight fight;

  static public Game getInstance() {
    if (instance == null) {
      instance = new Game();
    }

    return instance;
  }

  private Game() {
    events = new ArrayList<Event>();
    messages = new ArrayList<Message>();
    day = 0;

    player = new Player("You");
    location = locations[0];

    setupEvents();
    updatePrices();
  }

  public void checkGameConditions()
          throws GameOverException {
    boolean over = false;

    if (day > DAYS_IN_GAME) {
      throw new GameOverException("Time's up!");
    }

    if (getPlayer().getCash() < 0) {
      throw new GameOverException("You're broke!");
    }

    if (!getPlayer().isAlive()) {
      throw new GameOverException("You're dead!");
    }
  }

  public Trade getCurrentTrade() {
    return currentTrade;
  }

  public void setCurrentTrade(Trade trade) {
    this.currentTrade = trade;
  }

  public int getDay() {
    return day;
  }

  public boolean moveTo(Location location) throws GameOverException {
    boolean moved = false;

    if (this.location != location) {
      events.clear();
      messages.clear();
      this.location = location;
      runGameEvents();
      day++;
      moved = true;
      checkGameConditions();
    }

    return moved;
  }

  public Location[] getLocations() {
    return locations;
  }

  public Npc[] getNpcs() {
    return npcs;
  }

  public Location getLocation() {
    return location;
  }

  public ArrayList<Event> getEvents() {
    return events;
  }

  public Player getPlayer() {
    return player;
  }

  public Product[] getProducts() {
    return products;
  }

  public ArrayList<Message> getMessages() {
    return messages;
  }

  public void addMessage(Message message) {
    messages.add(message);
  }

  public void runGameEvents() {
    updatePrices();
    int r;
    Product product;

    for (Event e : gameEvents) {
      if (e.inEvent()) {
        events.add(e);
      }
    }
  }

  public void updatePrices() {
    Message m;
    for (Product p : products) {
      p.updatePrice();
      if ((m = p.getMessage()) != null) {
        addMessage(m);
      }
    }
  }

  public Fight getFight() {
    return fight;
  }

  public boolean inFight() {
    return (fight != null);
  }

  public void fightOver() {
    fight = null;
  }

  public int daysLeft() {
    return (DAYS_IN_GAME - day);
  }

  public Fight startFight() {
    ArrayList<Npc> available = new ArrayList<Npc>();

    for (Npc n : npcs) {
      if (n.getLevel() <= player.getLevel()) {
        available.add(n);
      }
    }

    int i = (int) (Math.random() * available.size());
    return startFight(available.get(i));
  }

  public Fight startFight(Npc npc) {
    npc.reset();
    this.fight = new Fight(npc);

    return getFight();
  }

  /**
   * setup the events that make the game "fun"
   * these need to be here since we cannot get proper references without
   * initializing first
   */
  private void setupEvents() {
    this.gameEvents = new Event[]{
      new SecuritySystemEvent(this),
      new SystemRecoveryEvent(this),
      new StorageEvent(this),
      new LegalThreatEvent(this),
      new SystemRepairEvent(this),
      new EncryptionEvent(this),
      new DataLeakEvent(this),
      new DataBreachEvent(this),
      new HomomorphicEncryptionEvent(this),
      new DifferentialPrivacyEvent(this),
      new ZeroKnowledgeProofEvent(this)
    };
  }
}
