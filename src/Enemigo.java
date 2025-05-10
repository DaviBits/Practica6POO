import java.awt.image.BufferedImage;

public abstract class Enemigo extends Entidad {

    public Enemigo(int x, int y, int ancho, int alto, int vida) {
        super(x, y, ancho, alto, vida);

    }
    public  void bajarVida(int i){vida-=i;}
    public  int getVida(){return vida;}
}
