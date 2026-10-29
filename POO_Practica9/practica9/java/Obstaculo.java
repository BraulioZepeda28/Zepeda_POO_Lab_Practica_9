import javafx.scene.paint.Color;

public class Obstaculo implements Destruible {
    private String nombre;
    private int x;
    private int y;
    private boolean destruido;

    public Obstaculo(String nombre, int x, int y) {
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

    public boolean isDestruido() {
        return destruido;
    }

    public void setDestruido(boolean destruido) {
        this.destruido = destruido;
    }

    public boolean puedeRomperse(Arma arma) {
        return true;
    }

    public Color getColor() {
        return Color.SADDLEBROWN;
    }

    @Override
    public void destruye() {
        destruido = true;
        System.out.println("Se destruyo: " + nombre);
    }
}
