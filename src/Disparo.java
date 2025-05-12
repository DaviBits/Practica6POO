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
        g.setColor(Color.BLACK);
        g.fillRect(x, y, ancho, alto);
    }
    @Override
    public void actualizar() {
        if (!direccion) {
            x += velocidad; // Mueve el disparo a la derecha
        }else{
            x -= velocidad;//izquierda

        }
    }
    public void actualizarIzquierda() {
        x-= velocidad;
    }



    public boolean verificarColisiones(List<Entidad> entidades) {
        Entidad enemigoAEliminar = null;
        boolean colision = false;

        for (Entidad e : entidades) {
            if (e instanceof Enemigo && getRect().intersects(e.getRect())) {
                System.out.println("oa");

                if (e.getVida() > 0) {
                    System.out.println("colision");
                    e.bajarVida(20);
                    e.informarAtaque();
                    colision = true;
                }

                if (e.getVida() <= 0) {
                    enemigoAEliminar = e;
                }
            }
        }

        if (enemigoAEliminar != null) {
            entidades.remove(enemigoAEliminar);
        }

        if (colision) {
            entidades.remove(this); // eliminar el disparo tras colisión
            return true;
        }

        return false;
    }


    public void eliminarDisparo(List<Entidad> entidades) {
        entidades.remove(this);
    }

    public boolean haExpirado() {
        return System.currentTimeMillis() - tiempoCreado > 2000; // 2 segundos
    }


}
