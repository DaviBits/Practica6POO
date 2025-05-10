import java.awt.*;

public class Techo extends Entidad {
    public Techo(int x, int y, int ancho, int alto, int vida) {
        super(x, y, ancho, alto, vida);
    }

    public void dibujar(Graphics g) {
        g.setColor(Color.DARK_GRAY);
        g.fillRect(x, y, ancho, alto);
    }
    @Override
    public void actualizar() {

    }
}
