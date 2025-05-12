import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Techo extends Entidad {
    private String imagen;
    public Techo(int x, int y, int ancho, int alto, String imagen,  int vida) {
        super(x, y, ancho, alto, vida);
        this.imagen=imagen;

    }

    public void dibujar(Graphics g) {
        Image sprite;
        sprite= new ImageIcon(imagen).getImage();
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(sprite, x  , y, ancho, alto , null);
       // g.setColor(Color.DARK_GRAY);
       // g.fillRect(x, y, ancho, alto);
    }
    @Override
    public void actualizar() {

    }
}
