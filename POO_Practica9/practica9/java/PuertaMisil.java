import javafx.scene.paint.Color;

public class PuertaMisil extends Obstaculo {

    public PuertaMisil(int x, int y) {
        super("Puerta de misiles", x, y);
    }

    @Override
    public boolean puedeRomperse(Arma arma) {
        return arma.getDano() >= 3;
    }

    @Override
    public Color getColor() {
        return Color.ORANGE;
    }
}
