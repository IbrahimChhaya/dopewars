package datawars.events;

import datawars.Game;
import datawars.Message;
import datawars.Product;

/**
 * Event to randomly award the user a random product
 * @author rob
 */
public class DataLeakEvent extends Event {

  final static int CHANCE = 7;
  final static int MAX_QUANTITY = 15;

  public DataLeakEvent(Game game){
    super(game);
  }
  
  @Override
  public boolean inEvent() {
    return hit(CHANCE);
  }

  @Override
  public Message handleEvent(boolean yes) {
    Product[] products = game.getProducts();

    int t = (int) (Math.random() * products.length);
    int q = (int) (Math.random() * MAX_QUANTITY) + 1;

    Product product = products[t];
    player.addProduct(product, q, 0);

    Message m = new Message(EVENT_COLOR, String.format("You discovered %d leaked %s records!", q, product.getName()));
    return m;
  }

  @Override
  public boolean requiresInput() {
    return false;
  }
}
