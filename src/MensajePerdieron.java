import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class MensajePerdieron extends  JPanel {
    private Image imagen;
    public MensajePerdieron(){

       imagen = new ImageIcon("pantallaPerdieron.png").getImage();
    }

 @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }
}
