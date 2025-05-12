import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel {
    private Image imagenFondo;

    public Menu(JPanel contenedor, CardLayout layout) {
        imagenFondo= new ImageIcon("FondoMenu2.png").getImage();
        setLayout(null);
        setOpaque(false);

        JButton botonJugar=new JButton("JUGAR");
        JButton botonSalir=new JButton("SALIR");
        JButton botonCargar=new JButton("Cargar");

        botonJugar.setBounds(10, 10, 50, 30);
        add(botonJugar);

        botonJugar.setFont(new Font("Roboto",Font.BOLD, 24));
        botonSalir.setFont(new Font("Roboto",Font.BOLD, 24));
        botonCargar.setFont(new Font("Roboto",Font.BOLD, 24));

        botonJugar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                layout.show(contenedor, "juego");
               // GamePanel.requestFocusInWindow();
                for (Component c : contenedor.getComponents()) {
                    if (c instanceof GamePanel) {
                        c.requestFocusInWindow();
                       // break;
                    }
                }
            }
        });

        botonSalir.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        botonCargar.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                layout.show(contenedor, "guardado");
            }
        });


    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
