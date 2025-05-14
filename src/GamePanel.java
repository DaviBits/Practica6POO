import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Jugador jugador;
    private Jugador jugador2;
    private ArrayList<Entidad> entidades;
    private ArchivoJuego archivo;
    private Image fondo;
    private boolean rondaDos;


    public GamePanel() {
        rondaDos=false;
        setPreferredSize(new Dimension(1380, 640));
        setFocusable(true);
        //requestFocusInWindow();
        addKeyListener(this);
        int xEnemigo = 0;
        int yEnemigo = 0;


        try {
            fondo = new ImageIcon("fondobeta3.png").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        jugador = new Jugador(50, 500, 40, 40, "DaviSprites3.png", 100);
        jugador2 = new Jugador(55, 500, 40, 40, "PimiSprites.png", 100);

        entidades = new ArrayList<>();
        archivo = new ArchivoJuego("progreso.txt");

        entidades.add(new Plataforma(0, 640, 1380, 20, 10000000));//suelo
        entidades.add(new Techo(0, 0, 1800, 1,"", 1000000));//techo
        entidades.add(new Pared(0, 0, 1, 640, 1000000));//pared izquierda
        entidades.add(new Pared(1380, 0, 10, 640,1000000));//pared derecha
        entidades.add(new Techo(255, 140, 140, 70, "mesas.png",99999));
        entidades.add(new Techo(995, 140, 140, 70, "mesas.png",99999));
        entidades.add(new Techo(255, 400, 140, 70, "mesas.png",99999));
        entidades.add(new Techo(995, 400, 140, 70, "mesas.png",99999));
        entidades.add(new Techo(610, 390, 159, 51, "BaseCimarronPixelartSinFondo.png",99999));


        for (int i = 0; i < 10; i++) {
            int x = 1300;
            int y = 0 + i * 60;
            entidades.add(new EnemigoTerrestre(x, y, 40, 40, "zombieSprite.png", 300));
        }

        entidades.add(new itemMunicion(600,10,25,25,90));
        entidades.add(new itemMunicion(600,580,25,25,90));
        entidades.add(new itemMunicion(1300,150,25,25,90));
        archivo.cargar(jugador, jugador2);


//        for (int i = 0; i < 5; i++) {
//            int x = 100 + i * 100;
//            int y = 300;
//            entidades.add(new Plataforma(x, y, 50, 50, 99999)); // o crea una clase nueva como ObstaculoRect
//        }

        timer = new Timer(16, this);
        timer.start();
    }

    public void aparecerBalas(){
        Random rnd = new Random();

        entidades.add(new itemMunicion(rnd.nextInt(1350),rnd.nextInt(630),25,25,90));
    }



    public void actionPerformed(ActionEvent e) {
        Random rnd = new Random();
        jugador.actualizar();
        jugador.verificarColisiones(entidades);
        jugador2.actualizar();
        jugador2.verificarColisiones(entidades);
        if(rnd.nextInt(70)==1){
            aparecerBalas();
        }
        for (int i = 0; i < entidades.size(); i++) {
            Entidad ent = entidades.get(i);

            if(ent instanceof EnemigoTerrestre enemigo){
                enemigo.moverse(jugador.getX(), jugador2.getX(), jugador.getY(), jugador2.getY(),entidades);
                enemigo.actualizar();
            }
            if (ent instanceof itemMunicion) {
                itemMunicion item = (itemMunicion) ent;

                if (jugador.getRect().intersects(item.getRect())) {
                    jugador.setMunicion(jugador.getMunicion() + 10);
                    entidades.remove(ent);
                    i--;
                    continue;
                }

                if (jugador2.getRect().intersects(item.getRect())) {
                    jugador2.setMunicion(jugador2.getMunicion() + 10);
                    entidades.remove(ent);
                    i--;
                    continue;
                }
            }

            if (ent instanceof Disparo) {
                Disparo d = (Disparo) ent;
                d.actualizar();
                if(d.verificarColisiones(entidades)){
                    remove(this);
                }

                if (d.haExpirado()) {
                    entidades.remove(i);
                    i--;
                }
            }
        }

        if(eliminaronAlosZombies()){
            //rondaDos=true;
            if(!rondaDos){
                for(int i=0; i<5; i++){
                    int x = 1300;
                    int y = 0 + i * 60;
                    entidades.add(new MegaZombie(x,y, 80, 80, "zombieSprite.png", 600));
                }
                rondaDos=true;
            }
        }
        if(estanEliminados()){
            timer.stop();
            mostrarPantallaDerrota();
        }

        if (eliminaronZombiesGrandes() && rondaDos ) {
            timer.stop();
            mostrarPantallaVictoria();
        }


        repaint();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (fondo != null) {
            g.drawImage(fondo, 0, 0, getWidth(), getHeight(), this);
        }

        jugador.dibujar(g);
        jugador2.dibujar(g);

        for (Entidad ent : entidades) {
            ent.dibujar(g);
        }
    }

    public void keyPressed(KeyEvent e) {
        // Jugador 1
        if (e.getKeyCode() == KeyEvent.VK_DOWN) jugador.setAbajo(true);
        if (e.getKeyCode() == KeyEvent.VK_LEFT) jugador.setIzquierda(true);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) jugador.setDerecha(true);
        if (e.getKeyCode() == KeyEvent.VK_UP) jugador.setArriba(true);
        if (e.getKeyCode() == KeyEvent.VK_N) jugador.atacar();
        if (e.getKeyCode() == KeyEvent.VK_M) jugador.disparar();

        // Jugador 2
        if (e.getKeyCode() == KeyEvent.VK_A) jugador2.setIzquierda(true);
        if (e.getKeyCode() == KeyEvent.VK_D) jugador2.setDerecha(true);
        if (e.getKeyCode() == KeyEvent.VK_W) jugador2.setArriba(true);
        if (e.getKeyCode() == KeyEvent.VK_S) jugador2.setAbajo(true);
        if (e.getKeyCode() == KeyEvent.VK_X) jugador2.atacar();
        if (e.getKeyCode() == KeyEvent.VK_C) jugador2.disparar();

        // Guardar
        if (e.getKeyCode() == KeyEvent.VK_R) {
            archivo.guardar(jugador, jugador2);
        }

        // Crear disparo
        if (e.getKeyCode() == KeyEvent.VK_M && jugador.municion > 0) {
            entidades.add(new Disparo(jugador.getX()+jugador.getAncho(), (jugador.getY()+jugador.getAlto()/2)-20, 10, 5,jugador.getIzquierda(), 1000));
            jugador.municion--;
        }
        if (e.getKeyCode() == KeyEvent.VK_C && jugador2.municion > 0) {
            entidades.add(new Disparo(jugador2.getX()+jugador2.getAncho(), (jugador2.getY()+jugador2.getAlto()/2)-20, 10, 5,jugador2.getIzquierda(), 1000));
            jugador2.municion--;
        }
    }

    public boolean estanEliminados(){

        if(jugador.getX()>1380&&jugador2.getX()>1380){
            return true;
        }
        return false;
    }

    private void mostrarPantallaDerrota() {
        JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
        ventana.setContentPane(new MensajePerdieron());
        ventana.revalidate();
    }

    private void mostrarPantallaVictoria(){
        JFrame ventana = (JFrame) SwingUtilities.getWindowAncestor(this);
        ventana.setContentPane(new MensajeGanaron());
        ventana.revalidate();
    }

    public boolean eliminaronAlosZombies(){
        int zombiesEliminados=0;
        for (Entidad ent : entidades) {
            if (ent instanceof EnemigoTerrestre) {
//                System.out.println("quedan enemigos");
                System.out.println("enemigo X "+ent.getX()+" Enemigo Y: "+ ent.getY()+ " vida:" +ent.getVida());
//                return false;
                if(!(ent.getY()<=20&&ent.getY()>=0)){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean eliminaronZombiesGrandes(){

            int zombiesEliminados=0;
            for (Entidad ent : entidades) {
                if (ent instanceof MegaZombie) {
                    System.out.println("quedan Mega enemigos");
                    System.out.println("enemigo X "+ent.getX()+" Enemigo Y: "+ ent.getY()+ " vida:" +ent.getVida());
                    return false;
                }
            }
            return true;

    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) jugador.setAbajo(false);
        if (e.getKeyCode() == KeyEvent.VK_UP) jugador.setArriba(false);
        if (e.getKeyCode() == KeyEvent.VK_LEFT) jugador.setIzquierda(false);
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) jugador.setDerecha(false);

        if (e.getKeyCode() == KeyEvent.VK_W) jugador2.setArriba(false);
        if (e.getKeyCode() == KeyEvent.VK_S) jugador2.setAbajo(false);
        if (e.getKeyCode() == KeyEvent.VK_A) jugador2.setIzquierda(false);
        if (e.getKeyCode() == KeyEvent.VK_D) jugador2.setDerecha(false);
    }
    public void keyTyped(KeyEvent e) {}


}
