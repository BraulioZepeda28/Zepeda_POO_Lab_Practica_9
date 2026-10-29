public abstract class Enemigo extends Personaje {
    private int dano;
    private int alcance;
    private int puntos;

    public Enemigo(String nombre, int vida, int x, int y, int dano, int alcance, int puntos) {
        super(nombre, vida, x, y);
        this.dano = dano;
        this.alcance = alcance;
        this.puntos = puntos;
    }

    public int getDano() {
        return dano;
    }

    public int getAlcance() {
        return alcance;
    }

    public int getPuntos() {
        return puntos;
    }

    public abstract String elegirDireccion();

    public void recibirDisparo(Arma arma) {
        recibirDano(arma.getDano());
    }
}
