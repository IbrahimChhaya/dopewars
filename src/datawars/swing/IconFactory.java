package datawars.swing;

import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import datawars.Product;

/**
 * Manages the icons for the swing application
 * @author rob
 */
public class IconFactory {

  static public ImageIcon getSpaceFullIcon() {
    return iconAtResourcePath("/TradeWars/resources/space_full.png");
  }

  static public ImageIcon getSpaceEmptyIcon() {
    return iconAtResourcePath("/TradeWars/resources/space_empty.png");
  }

  static public ImageIcon getDialogIcon(int messageType) {
    String resourceName;
    ImageIcon i = null;

    switch (messageType) {
      case JOptionPane.ERROR_MESSAGE:
        resourceName = "error.png";
        break;
      case JOptionPane.INFORMATION_MESSAGE:
      default:
        resourceName = "information.png";
    }

    if(resourceName != null){
      i = iconAtResourcePath(String.format("/TradeWars/resources/%s", resourceName));
    }
    
    return i;
  }

  static public ImageIcon getProductIcon(Product p) {
    return iconAtResourcePath(String.format("/TradeWars/resources/products/%s.png", p.getName().toLowerCase()));
  }

  static private ImageIcon iconAtResourcePath(String path) {
    ImageIcon i = null;
    URL u = "".getClass().getResource(path);

    if (u != null) {
      i = new ImageIcon(u);
    }

    return i;
  }
}
