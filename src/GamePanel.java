import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private Timer timer;
    private Jugador jugador;
    private Jugador jugador2;
    private ArrayList<Entidad> entidades;
    private ArchivoJuego archivo;
    private Image fondo;


    public GamePanel() {
        setPreferredSize(new Dimension(1380, 640));
        setFocusable(true);
        //requestFocusInWindow();
        addKeyListener(this);



        try {
            fondo = new ImageIcon("fondobeta.png").getImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

        jugador = new Jugador(50, 500, 40, 40, "DaviSprites3.png", 100);
        jugador2 = new Jugador(55, 500, 40, 40, "DaviSprites3.png", 100);

        entidades = new ArrayList<>();
        archivo = new ArchivoJuego("progreso.txt");

        entidades.add(new Plataforma(0, 640, 1380, 20, 10000000));//suelo
       entidades.add(new Techo(0, 0, 1380, 1, 1000000));//techo
        entidades.add(new Pared(0, 0, 1, 640, 1000000));//pared izquierda
        entidades.add(new Pared(1380, 0, 10, 640, 1000000));//pared derecha
        entidades.add(new Plataforma(200, 450, 120, 20, 1000000));//plataforma chica
        entidades.add(new EnemigoTerrestre(450, 540, 40, 40,"SpriteEnemigo.png", 100));
        entidades.add(new EnemigoVolador(500, 300, 40, 40, 100));

        //archivo.cargar(jugador);
        //archivo.cargar(jugador2);

        timer = new Timer(16, this);
        timer.start();
    }



    public void actionPerformed(ActionEvent e) {
        jugador.actualizar();
        jugador.verificarColisiones(entidades);

        jugador2.actualizar();
        jugador2.verificarColisiones(entidades);




        for (int i = 0; i < entidades.size(); i++) {
            Entidad ent = entidades.get(i);

            if(ent instanceof EnemigoTerrestre enemigo){
                enemigo.moverse(jugador.getX(), jugador2.getX(), jugador.getY(), jugador2.getY());
                enemigo.actualizar();
            }

            if (ent instanceof Disparo) {
                Disparo d = (Disparo) ent;
                d.actualizar();
                d.verificarColisiones(entidades);

                if (d.haExpirado()) {
                    entidades.remove(i);
                    i--;
                }
            }
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
        if (e.getKeyCode() == KeyEvent.VK_S) {
            archivo.guardar(jugador);
            archivo.guardar(jugador2);
        }

        // Crear disparo

        if (e.getKeyCode() == KeyEvent.VK_M) {
            entidades.add(new Disparo(jugador.getX() + jugador.getAncho() / 2, jugador.getY() + jugador.getAlto() / 2, 10, 5,jugador.getIzquierda(), 1000));
        }

        if (e.getKeyCode() == KeyEvent.VK_C) {
            entidades.add(new Disparo(jugador2.getX() + jugador2.getAncho() / 2, jugador2.getY() + jugador2.getAlto() / 2, 10, 5,jugador2.getIzquierda(), 1000));
        }

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
