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

    public EnemigoTerrestre(int x, int y, int ancho, int alto,String imagen, int vida) {
        super(x, y, ancho, alto, vida);
        try {
            BufferedImage spriteSheet = ImageIO.read(new File(imagen));
            frames = new BufferedImage[2];

            for (int i = 0; i < 2; i++) {
                frames[i] = spriteSheet.getSubimage(i * 8, 0, 8, 8);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        frameActual=0;
    }

    public void dibujar(Graphics g) {

        BufferedImage sprite = null;
        switch (estado) {
            case PARADO->{sprite = frames[0];}
            case MURIENDO->{sprite = frames[1];}
        }
        if (sprite != null) {
            int escala = 3;
            g.drawImage(sprite, x, y, ancho * escala, alto * escala, null);
        }
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

        if (x > objetivoX) x -= 2;
        else if (x < objetivoX) x += 2;

        if (y > objetivoY) y -= 2;
        else if (y < objetivoY) y += 2;
    }


    @Override
    public void actualizar () {
        if(parado){
            estado = EstadoAnimacion.PARADO ;
        }else{
            estado= EstadoAnimacion.MURIENDO ;
        }
    }

}
