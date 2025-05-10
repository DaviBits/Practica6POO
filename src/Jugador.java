import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.List;

public class Jugador extends Entidad {

    private int dy = 0;
    private boolean izquierda = false, derecha = false, enSuelo = true , disparado = false;
    private boolean arriba, abajo;
    private BufferedImage[] frames;
    private int frameActual = 0;
    private long tiempoUltimoFrame = 0;
    private final long duracionFrame = 120;
    private boolean atacando;
    private boolean disparando;
    private long tiempoInicioDisparo = 0;
    private final long duracionDisparo = 300;
    private long tiempoInicioAtaque = 0;
    private final long duracionAtaque = 300;
    private enum EstadoAnimacion { PARADO, CAMINANDO, DISPARANDO, ATACANDO };
    private EstadoAnimacion estado = EstadoAnimacion.PARADO;
    public Jugador(int x, int y, int ancho, int alto, String imagen, int vida) {
        super(x, y, ancho, alto, vida);
        this.atacando=false;
        this.disparando=false;
        try {
            BufferedImage spriteSheet = ImageIO.read(new File(imagen)); // 🖼️ Tu spritesheet 24x8
            frames = new BufferedImage[6]; // 3 frames de 8x8

            for (int i = 0; i < 6; i++) {
                frames[i] = spriteSheet.getSubimage(i * 32, 0, 32, 32); // Separa cada frame
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actualizar() {
        if(arriba) y-=10;
        if (izquierda) x -= 10;
        if (derecha) x += 10;
        if(abajo) y+=10;
        dy +=0;
        y += dy;
        long ahora = System.currentTimeMillis();

        if (atacando) {
            estado = EstadoAnimacion.ATACANDO;
            if (ahora - tiempoInicioAtaque > duracionAtaque) {
                atacando = false;
                estado = (izquierda || derecha||arriba||abajo) ? EstadoAnimacion.CAMINANDO : EstadoAnimacion.PARADO;
            }
        } else if (izquierda || derecha||arriba||abajo) {
            estado = EstadoAnimacion.CAMINANDO;
            if (ahora - tiempoUltimoFrame > duracionFrame) {
                frameActual = (frameActual == 1) ? 2 : 1; // alternar entre caminar 1 y 2
                tiempoUltimoFrame = ahora;
            }
        }else if(disparando){
            estado = EstadoAnimacion.DISPARANDO;
            if (ahora - tiempoInicioDisparo > duracionDisparo) {
                disparando = false;
                estado = EstadoAnimacion.PARADO;
            }

        }else {
            estado = EstadoAnimacion.PARADO;
            frameActual = 0;
        }

    }

    public void verificarColisiones(List<Entidad> entidades) {
        enSuelo = true;
        for (Entidad e : entidades) {
            if (e instanceof Plataforma && getRect().intersects(e.getRect())) {
                y = e.getRect().y - alto;
                dy = 0;
                enSuelo = true;
            }
            if (e instanceof Enemigo && getRect().intersects(e.getRect())) {
                x = 250; y = 500; dy = 0;
            }
            if (e instanceof Pared && getRect().intersects(e.getRect())){
                if(izquierda){
                    x = e.getRect().x + ancho;
                }else{
                    x = e.getRect().x - ancho;
                }

            }
            if (e instanceof Techo && getRect().intersects(e.getRect())){
                y = e.getRect().y + alto;
            }
        }
    }

    public void dibujar(Graphics g) {
        System.out.println("Estado: " + estado + " | Frame actual: " + frameActual);

        BufferedImage sprite = null;
        switch (estado) {
            case PARADO->{sprite = frames[0];}
            case CAMINANDO->{sprite = frames[frameActual];}
            case DISPARANDO->{sprite = frames[4];}
            case ATACANDO->{sprite = frames[5];}
        }
        if (sprite != null) {
            int escala = 3;
            g.drawImage(sprite, x, y, ancho * escala, alto * escala, null);
        }
    }

    public void saltar() {
       // if (enSuelo) dy = -15;
    }

    public void setIzquierda(boolean b) { izquierda = b; }
    public void setDerecha(boolean b) { derecha = b; }
    public void setArriba(boolean b){arriba=b;}
    public void setAbajo(boolean b){abajo =b;}
    public int getX() { return x; }
    public int getY() { return y; }

public boolean getIzquierda() {
        return izquierda;
    }


public void atacar(){
    if (!atacando) {
        atacando = true;
        estado = EstadoAnimacion.ATACANDO;
        tiempoInicioAtaque = System.currentTimeMillis();
    }
}

public void disparar(){
    if (!disparando) {
        disparando = true;
        estado = EstadoAnimacion.DISPARANDO;
        tiempoInicioDisparo = System.currentTimeMillis();
    }
}
}
