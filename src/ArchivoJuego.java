import java.io.*;

public class ArchivoJuego {
    private final String ruta;

    public ArchivoJuego(String ruta) {
        this.ruta = ruta;
    }

    // Guarda datos de ambos jugadores
    public void guardar(Jugador jugador1, Jugador jugador2) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ruta))) {
            guardarJugador(pw, jugador1);
            guardarJugador(pw, jugador2);
        } catch (IOException e) {
            System.err.println("Error al guardar los datos del juego:");
            e.printStackTrace();
        }
    }

    // Carga datos a ambos jugadores (en el orden en que se guardaron)
    public void cargar(Jugador jugador1, Jugador jugador2) {
        File archivo = new File(ruta);
        if (!archivo.exists()) {
            System.err.println("Archivo no encontrado: " + ruta);
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            cargarJugador(br, jugador1);
            cargarJugador(br, jugador2);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error al cargar los datos del juego:");
            e.printStackTrace();
        }
    }

    private void guardarJugador(PrintWriter pw, Jugador jugador) {
        pw.println(jugador.getX());
        pw.println(jugador.getY());
        pw.println(jugador.getMunicion());
    }

    private void cargarJugador(BufferedReader br, Jugador jugador) throws IOException {
        int x = Integer.parseInt(br.readLine());
        int y = Integer.parseInt(br.readLine());
        int municion = Integer.parseInt(br.readLine());
        jugador.setX(x);
        jugador.setY(y);
        jugador.setMunicion(municion);
    }
}
