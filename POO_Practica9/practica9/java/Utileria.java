import javafx.scene.paint.Color;

public class Utileria implements ElementoDinamico {
    private String nombre;
    private int x;
    private int y;
    private boolean usada;
    private Mejora contenido;

    public Utileria(String nombre, int x, int y, Mejora contenido) {
        this.nombre = nombre;
        this.x = x;
        this.y = y;
        this.contenido = contenido;
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

    public boolean isUsada() {
        return usada;
    }

    public void setUsada(boolean usada) {
        this.usada = usada;
    }

    public Mejora getContenido() {
        return contenido;
    }

    @Override
    public void mover(String direccion, int distancia) {
        if (direccion.equals("este")) {
            x = x + distancia;
        }
        if (direccion.equals("oeste")) {
            x = x - distancia;
        }
    }

    public Color getColor() {
        return Color.GOLD;
    }
}
