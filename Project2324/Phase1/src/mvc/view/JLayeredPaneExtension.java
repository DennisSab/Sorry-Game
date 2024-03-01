package mvc.view;

import javax.swing.*;
import java.awt.*;

public class JLayeredPaneExtension extends JLayeredPane {

    Image image;

    public JLayeredPaneExtension(Image img){image=img;}

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(image,0,0,this);
    }


}
