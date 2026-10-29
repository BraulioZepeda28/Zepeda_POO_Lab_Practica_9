import javafx.scene.paint.Color;

public class Jefe extends Enemigo {

    public Jefe(int x, int y) {
        super("Jefe", 9, x, y, 15, 3, 500);
    }

    @Override
    public String elegirDireccion() {
        return "";
    }

    @Override
    public void recibirDisparo(Arma arma) {
        if (arma.getDano() >= 3) {
            recibirDano(arma.getDano());
        }
    }

    @Override
    public Color getColor() {
        return Color.PURPLE;
    }
}
