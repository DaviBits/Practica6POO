import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu extends JPanel {
    private Image imagenFondo;

    public Menu(JPanel contenedor, CardLayout layout) {
        imagenFondo= new ImageIcon("FondoMenu2.png").getImage();
        setLayout(new GridBagLayout());
        setOpaque(false);

        JButton botonJugar=new JButton("JUGAR");
        JButton botonSalir=new JButton("SALIR");
        JButton botonCargar=new JButton("Cargar");

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

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panelBotones.setOpaque(false); // También transparente

        panelBotones.add(botonJugar);
        panelBotones.add(botonCargar);
        panelBotones.add(botonSalir);

        // Añadir ese panel centrado en Menu
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weighty = 1.0; // Espacio extra arriba
        gbc.anchor = GridBagConstraints.PAGE_END; //;
        add(panelBotones, gbc);

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagenFondo != null) {
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
