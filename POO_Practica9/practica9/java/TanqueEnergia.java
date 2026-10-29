public class TanqueEnergia extends Recompensa {

    public TanqueEnergia() {
        super("Tanque de energia", 50);
    }

    @Override
    public void aplicar(Jugador jugador) {
        jugador.aumentarVidaMaxima(getValor());
    }
}
