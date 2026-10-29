import java.util.ArrayList;

public class Guardado {
    private int x;
    private int y;
    private int vidaMaxima;
    private int puntos;
    private int cantidadArmas;
    private int cantidadItems;
    private ArrayList<Boolean> obstaculosDestruidos = new ArrayList<>();
    private ArrayList<Boolean> objetosUsados = new ArrayList<>();
    private ArrayList<Integer> vidaEnemigos = new ArrayList<>();
    private ArrayList<Integer> xEnemigos = new ArrayList<>();

    public Guardado(Nivel nivel) {
        Jugador jugador = nivel.getJugador();
        x = jugador.getX();
        y = jugador.getY();
        vidaMaxima = jugador.getVidaMaxima();
        puntos = jugador.getPuntos();
        cantidadArmas = jugador.getCantidadArmas();
        cantidadItems = nivel.getInventario().getCantidadItems();

        for (Obstaculo o : nivel.getObstaculos()) {
            obstaculosDestruidos.add(o.isDestruido());
        }
        for (Utileria u : nivel.getObjetos()) {
            objetosUsados.add(u.isUsada());
        }
        for (Enemigo e : nivel.getEnemigos()) {
            vidaEnemigos.add(e.getVida());
            xEnemigos.add(e.getX());
        }
    }

    public void restaurar(Nivel nivel) {
        Jugador jugador = nivel.getJugador();
        jugador.setPosicion(x, y);
        jugador.setVidaMaxima(vidaMaxima);
        jugador.setVida(vidaMaxima);
        jugador.setPuntos(puntos);
        jugador.dejarSoloArmas(cantidadArmas);
        nivel.getInventario().dejarSoloItems(cantidadItems);

        for (int i = 0; i < obstaculosDestruidos.size(); i++) {
            nivel.getObstaculos().get(i).setDestruido(obstaculosDestruidos.get(i));
        }
        for (int i = 0; i < objetosUsados.size(); i++) {
            nivel.getObjetos().get(i).setUsada(objetosUsados.get(i));
        }
        for (int i = 0; i < vidaEnemigos.size(); i++) {
            Enemigo e = nivel.getEnemigos().get(i);
            e.setVida(vidaEnemigos.get(i));
            e.setPosicion(xEnemigos.get(i), e.getY());
        }
    }
}
