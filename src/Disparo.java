import java.awt.*;
import java.util.List;

public class Disparo extends Entidad{

    private int velocidad = 30;
    long tiempoCreado;
    private boolean direccion = false;

    public Disparo(int x, int y, int ancho, int alto, boolean direccion, int vida) {
        super(x, y, ancho, alto, vida);
        tiempoCreado = System.currentTimeMillis();
        this.direccion = direccion;
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, ancho, alto);
    }
    @Override
    public void actualizar() {
        if (!direccion) {
            x += velocidad; // Mueve el disparo a la derecha
            System.out.println("hola prueba");
        }else{
            x -= velocidad;//izquierda
            System.out.println("hola prueba 2");

        }
    }
    public void actualizarIzquierda() {
        x-= velocidad;
    }



    public void verificarColisiones(List<Entidad> entidades) {
        for (Entidad e : entidades) {
            if (e instanceof Enemigo && getRect().intersects(e.getRect())) {
                System.out.println("oa");
                //remove(this);
                if (e.getVida() > 0) {
                    System.out.println("colision");
                    e.bajarVida(20);
                    e.informarAtaque();
                }
                if (e.getVida() == 0) {
                    entidades.remove(e);
                    entidades.remove(this);
                    break;
                }
            }

        }
    }

    public void eliminarDisparo(List<Entidad> entidades) {
        entidades.remove(this);
    }

    public boolean haExpirado() {
        return System.currentTimeMillis() - tiempoCreado > 2000; // 2 segundos
    }
}
