import javafx.scene.paint.Color;

public class CheckPoint {
    private String nombre;
    private int x;
    private int y;
    private boolean activado;
    private Guardado guardado;

    public CheckPoint(String nombre, int x, int y) {
        this.nombre = nombre;
        this.x = x;
        this.y = y;
    }

    public String getNombre() {
        return nombre;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void activar() {
        activado = true;
        System.out.println("Se activo el punto de control: " + nombre);
    }

    public void guardarPartida(Nivel nivel) {
        guardado = new Guardado(nivel);
    }

    public void cargarPartida(Nivel nivel) {
        guardado.restaurar(nivel);
    }

    public Color getColor() {
        if (activado) {
            return Color.LIMEGREEN;
        }
        return Color.DARKGREEN;
    }
}
