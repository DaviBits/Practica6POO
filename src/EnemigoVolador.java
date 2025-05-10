import java.awt.*;

public class EnemigoVolador extends Enemigo {
    public EnemigoVolador(int x, int y, int ancho, int alto, int vida) {
        super(x, y, ancho, alto, vida);
    }

    @Override
    public void actualizar() {

    }

    public void dibujar(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillOval(x, y, ancho, alto);
    }
}
