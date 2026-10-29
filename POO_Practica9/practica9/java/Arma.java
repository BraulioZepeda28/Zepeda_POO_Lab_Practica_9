public class Arma implements Mejora {
    private String nombre;
    private int dano;
    private int alcance;

    public Arma(String nombre, int dano, int alcance) {
        this.nombre = nombre;
        this.dano = dano;
        this.alcance = alcance;
    }

    public String getNombre() {
        return nombre;
    }

    public int getDano() {
        return dano;
    }

    public int getAlcance() {
        return alcance;
    }

    @Override
    public void registrar() {
        System.out.println("Se agrego el arma: " + nombre);
    }

    @Override
    public void borrar() {
        System.out.println("Se elimino el arma: " + nombre);
    }

    @Override
    public void aplicar(Jugador jugador) {
        jugador.agregarArma(this);
    }

    @Override
    public String toString() {
        return "Arma: " + nombre;
    }
}
