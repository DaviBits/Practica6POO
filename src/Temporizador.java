import javax.swing.*;
import java.awt.*;

public class Temporizador {
    private int tiempoRestante; // en segundos
    private Timer timer;        // Swing Timer
    private boolean terminado = false;

    public Temporizador(int duracionSegundos) {
        this.tiempoRestante = duracionSegundos;

        timer = new Timer(1000, e -> {
            tiempoRestante--;
            if (tiempoRestante <= 0) {
                tiempoRestante = 0;
                terminado = true;
                timer.stop();
            }
        });
        timer.start();
    }

    public void reiniciar() {
        tiempoRestante = 60;
        terminado = false;
        timer.restart();
    }

    public int getTiempoRestante() {
        return tiempoRestante;
    }

    public boolean estaTerminado() {
        return terminado;
    }

    public void dibujar(Graphics g, int x, int y) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 24));
        g.drawString("Tiempo: " + tiempoRestante, x, y);
    }
}
