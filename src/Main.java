import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame ventana = new JFrame("Juego con Menú");
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setResizable(false);
        ventana.setSize(1380, 640);

        // Aquí usamos CardLayout para cambiar entre pantallas
        CardLayout layout = new CardLayout();
        JPanel contenedor = new JPanel(layout);

        // Creamos los paneles
        Menu menu = new Menu(contenedor, layout);
        GamePanel juego = new GamePanel();


        contenedor.add(new PantallaDeInicio(contenedor, layout), "logo");
        contenedor.add(menu, "menu");
        contenedor.add(juego, "juego");

        ventana.setContentPane(contenedor);
        ventana.setVisible(true);
        layout.show(contenedor, "logo");
    }
}
