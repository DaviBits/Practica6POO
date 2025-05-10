import java.awt.*;

public abstract class Entidad {
    protected int x;
    protected int y;
    protected int ancho;
    protected int alto;
    protected  int vida;
    protected boolean esAtacado;
    public Entidad(int x, int y, int ancho, int alto, int vida) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.vida =vida;
        esAtacado=false;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, ancho, alto);
    }
    public abstract void actualizar();
    public abstract void dibujar(Graphics g);

    public int getX() { return x; }
    public int getY() { return y; }
    public int getAncho() { return ancho; }
    public int getAlto() { return alto; }
    public int getVida(){return vida;}
    public void bajarVida(int n){vida-=n;}
    public boolean informarAtaque(){return true;}
}
