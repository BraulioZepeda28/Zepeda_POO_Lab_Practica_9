import java.util.ArrayList;

public class Jugador extends Personaje {
    private int vidaMaxima;
    private int puntos;
    private String mirando = "este";
    private ArrayList<Arma> armas = new ArrayList<>();
    private int armaActual;

    public Jugador(String nombre, int vida, int x, int y) {
        super(nombre, vida, x, y);
        vidaMaxima = vida;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public void setVidaMaxima(int vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public String getMirando() {
        return mirando;
    }

    public void setMirando(String mirando) {
        this.mirando = mirando;
    }

    public Arma getArmaActual() {
        return armas.get(armaActual);
    }

    public int getCantidadArmas() {
        return armas.size();
    }

    public void agregarArma(Arma arma) {
        armas.add(arma);
        armaActual = armas.size() - 1;
    }

    public void cambiarArma() {
        armaActual = armaActual + 1;
        if (armaActual == armas.size()) {
            armaActual = 0;
        }
    }

    public void dejarSoloArmas(int cantidad) {
        while (armas.size() > cantidad) {
            armas.remove(armas.size() - 1);
        }
        armaActual = armas.size() - 1;
    }

    public void sumarPuntos(int cantidad) {
        puntos = puntos + cantidad;
    }

    public void aumentarVidaMaxima(int cantidad) {
        vidaMaxima = vidaMaxima + cantidad;
        setVida(vidaMaxima);
    }
}
