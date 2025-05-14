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

        JButton botonJugar=new JButton(new ImageIcon("botonJugar.png"));
        JButton botonCargar=new JButton(new ImageIcon("botonCargar.png"));
        JButton botonSalir=new JButton(new ImageIcon("BotonSalir.png"));
        //JButton botonCargar=new JButton(new ImageIcon(getClass().getResource("/Imagenes/FiveCardDrawButton.png")));

        botonJugar.setBorderPainted(false);
        botonJugar.setContentAreaFilled(false);
        botonJugar.setFocusPainted(false);
        botonJugar.setOpaque(false);

        botonCargar.setBorderPainted(false);
        botonCargar.setContentAreaFilled(false);
        botonCargar.setFocusPainted(false);
        botonCargar.setOpaque(false);

        botonSalir.setBorderPainted(false);
        botonSalir.setContentAreaFilled(false);
        botonSalir.setFocusPainted(false);
        botonSalir.setOpaque(false);

        botonJugar.setBounds(370, 450, 240, 90);
        botonCargar.setBounds(610, 450, 240, 90);
        botonSalir.setBounds(850, 450, 240, 90);



        add(botonJugar);
        //add(botonCargar);
        add(botonSalir);

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
