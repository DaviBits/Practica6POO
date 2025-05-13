import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class PantallaDeInicio extends JPanel {

    public PantallaDeInicio(JPanel contenedor, CardLayout layout){
        setLayout(new BorderLayout());
        JLabel logo =new JLabel(new ImageIcon("SunFlowerGames.png"));
        add(logo, BorderLayout.CENTER);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                layout.show(contenedor, "menu");
            }
        }, 3000);

    }

}
