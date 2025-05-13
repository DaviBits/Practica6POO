import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;


public class MensajeGanaron extends  JPanel {
    private Image imagen;
    public MensajeGanaron(){
        imagen = new ImageIcon("pantallaGanaron.png").getImage();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
    }
}