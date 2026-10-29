public class Recompensa implements Mejora {
    private String nombre;
    private int valor;

    public Recompensa(String nombre, int valor) {
        this.nombre = nombre;
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public int getValor() {
        return valor;
    }

    @Override
    public void registrar() {
        System.out.println("Se agrego la recompensa: " + nombre);
    }

    @Override
    public void borrar() {
        System.out.println("Se elimino la recompensa: " + nombre);
    }

    @Override
    public void aplicar(Jugador jugador) {
        jugador.sumarPuntos(valor);
    }

    @Override
    public String toString() {
        return "Recompensa: " + nombre;
    }
}
