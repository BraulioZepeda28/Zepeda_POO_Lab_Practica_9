import javafx.scene.paint.Color;

public class Rastrero extends Enemigo {
    private int inicioX;
    private String direccion = "este";

    public Rastrero(int x, int y) {
        super("Rastrero", 2, x, y, 10, 1, 50);
        inicioX = x;
    }

    @Override
    public String elegirDireccion() {
        if (getX() == inicioX + 3) {
            direccion = "oeste";
        }
        if (getX() == inicioX) {
            direccion = "este";
        }
        return direccion;
    }

    @Override
    public Color getColor() {
        return Color.RED;
    }
}
