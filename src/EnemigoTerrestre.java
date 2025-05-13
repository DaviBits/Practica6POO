import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import java.util.List;


public class EnemigoTerrestre extends Enemigo {
    private boolean parado=true;
    private boolean muriendo = false;
    private BufferedImage[] frames;
    private int frameActual;
    private enum EstadoAnimacion { PARADO, MURIENDO };
    private EstadoAnimacion estado = EstadoAnimacion.PARADO;
    private int framesEspera = 600;
    final int ESCALA = 2;
    private boolean arriba, abajo, izquierda, derecha;


    public EnemigoTerrestre(int x, int y, int ancho, int alto,String imagen, int vida) {
        super(x, y, ancho*3, alto*3, vida);
        try {
            BufferedImage spriteSheet = ImageIO.read(new File(imagen));
            frames = new BufferedImage[2];

            for (int i = 0; i < 2; i++) {
                frames[i] = spriteSheet.getSubimage(i * 32, 0, 32, 32);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        frameActual=0;


        this.arriba=false;
        this.abajo=false;
        this.izquierda=false;
        this.derecha=false;

    }

    public void dibujar(Graphics g) {
        g.drawImage(frames[frameActual], x, y, ancho, alto, null);
    }

    public void RecibirDaño(){
        if(!muriendo&&informarAtaque()){
            muriendo=true;
        }
        muriendo=false;
    }
    public void verificarColisiones(List<Entidad> entidades) {
        int distanciaMinima = 50;

        for (Entidad e : entidades) {
            if (e != this && e instanceof EnemigoTerrestre) {
                int dx = this.x - e.x;
                int dy = this.y - e.y;
                double distancia = Math.sqrt(dx * dx + dy * dy);

                if (distancia < distanciaMinima) {
                    if (dx == 0 && dy == 0) {
                        dx = 1;
                    }
                    double factor = 2 / distancia;
                    this.x += (int)(dx * factor);
                    this.y += (int)(dy * factor);
                }
            }
            if (e instanceof Techo && getRect().intersects(e.getRect())) {
                Rectangle r = e.getRect();
                if (arriba) {
                    y =y+1; // Te chocaste por abajo
                } else if (abajo) {
                    y =y-1; // Te chocaste por arriba
                } else if (izquierda) {
                    x = x+1; // Te chocaste por la izquierda
                } else if (derecha) {
                    x =  x-1; // Te chocaste por la derecha
                }
            }


        }
    }

    public void moverse(int xJugador1, int xJugador2, int yJugador1, int yJugador2, List<Entidad> entidades) {
      /*  if (this.framesEspera > 0) {
            this.framesEspera--;
            return;
        }*/

        verificarColisiones(entidades);

        double distanciaJugador1 = Math.sqrt(Math.pow(x - xJugador1, 2) + Math.pow(y - yJugador1, 2));
        double distanciaJugador2 = Math.sqrt(Math.pow(x - xJugador2, 2) + Math.pow(y - yJugador2, 2));

        int objetivoX = (distanciaJugador1 <= distanciaJugador2) ? xJugador1 : xJugador2;
        int objetivoY = (distanciaJugador1 <= distanciaJugador2) ? yJugador1 : yJugador2;

        if (x > objetivoX) {
            x -= 2;
            izquierda=true;
            derecha=false;
        }else if (x < objetivoX){
            x += 2;
            derecha=true;
            izquierda=false;
        }

        if (y > objetivoY){
            y -= 2;
            arriba=true;
            abajo=false;
        } else if (y < objetivoY) {
            y += 2;
            abajo=true;
            arriba=false;
        }
    }


    @Override
    public void actualizar () {
        frameActual=(frameActual + 1) % frames.length;
    }

}
