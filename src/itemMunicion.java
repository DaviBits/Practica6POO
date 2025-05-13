import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class itemMunicion extends Entidad {
    private BufferedImage imagen;
    public boolean colision = false;


    public itemMunicion(int x, int y, int ancho, int alto, int vida) {
        super(x, y, ancho*2, alto*2, vida);
        try {
            imagen = ImageIO.read(new File("balasItem.png")); // Asegúrate de que "item.png" esté en la carpeta correcta
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actualizar() {
        // Puedes dejarlo vacío si no hace nada aún
        x=1000; y=1000;
    }

    @Override
    public void dibujar(Graphics g) {
        if (imagen != null) {
            int escala = 1;
            g.drawImage(imagen, x, y, ancho * escala, alto * escala, null);
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(x, y, ancho, alto);
        }
    }

    public void verificarColisiones(List<Entidad> entidades) {
        for (Entidad e : entidades) {
            if (e instanceof Jugador && getRect().intersects(e.getRect())) {
                System.out.println("item toca a jugador");
                colision = true;
            }
        }
    }
    public boolean getColision() {
        return colision;
    }
}
