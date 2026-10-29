import java.util.ArrayList;

public class Nivel {
    private String[] mapa = {
        "####################",
        "#..............#...#",
        "#@......*....C.D..J#",
        "####.#######.#######",
        "#.E..B..R....C...M.#",
        "####################"
    };

    private Inventario inventario;
    private Jugador jugador;
    private Jefe jefe;
    private ArrayList<Obstaculo> obstaculos = new ArrayList<>();
    private ArrayList<CheckPoint> checkPoints = new ArrayList<>();
    private ArrayList<Enemigo> enemigos = new ArrayList<>();
    private ArrayList<Utileria> objetos = new ArrayList<>();
    private CheckPoint ultimoCheckPoint;
    private String mensaje = "Busca el misil para abrir la puerta naranja y vencer al jefe.";

    private boolean huboDisparo;
    private int disparoInicioX;
    private int disparoFinX;
    private int disparoY;

    public Nivel(Inventario inventario) {
        this.inventario = inventario;

        for (int fila = 0; fila < mapa.length; fila++) {
            for (int col = 0; col < mapa[fila].length(); col++) {
                char letra = mapa[fila].charAt(col);

                if (letra == '@') {
                    jugador = new Jugador("Nova", 100, col, fila);
                } else if (letra == 'B') {
                    obstaculos.add(new Obstaculo("Bloque", col, fila));
                } else if (letra == 'D') {
                    obstaculos.add(new PuertaMisil(col, fila));
                } else if (letra == 'C') {
                    checkPoints.add(new CheckPoint("Estacion " + (checkPoints.size() + 1), col, fila));
                } else if (letra == 'R') {
                    enemigos.add(new Rastrero(col, fila));
                } else if (letra == 'J') {
                    jefe = new Jefe(col, fila);
                    enemigos.add(jefe);
                } else if (letra == 'M') {
                    objetos.add(new Utileria("Misil", col, fila, new Arma("Misil", 3, 3)));
                } else if (letra == 'E') {
                    objetos.add(new Utileria("Tanque de energia", col, fila, new TanqueEnergia()));
                } else if (letra == '*') {
                    objetos.add(new Utileria("Cristal", col, fila, new Recompensa("Cristal", 100)));
                }
            }
        }

        Arma rayo = new Arma("Rayo", 1, 5);
        inventario.agregarItem(rayo);
        rayo.aplicar(jugador);
    }

    public Inventario getInventario() {
        return inventario;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public ArrayList<Obstaculo> getObstaculos() {
        return obstaculos;
    }

    public ArrayList<CheckPoint> getCheckPoints() {
        return checkPoints;
    }

    public ArrayList<Enemigo> getEnemigos() {
        return enemigos;
    }

    public ArrayList<Utileria> getObjetos() {
        return objetos;
    }

    public String getMensaje() {
        return mensaje;
    }

    public int getFilas() {
        return mapa.length;
    }

    public int getColumnas() {
        return mapa[0].length();
    }

    public boolean getHuboDisparo() {
        return huboDisparo;
    }

    public int getDisparoInicioX() {
        return disparoInicioX;
    }

    public int getDisparoFinX() {
        return disparoFinX;
    }

    public int getDisparoY() {
        return disparoY;
    }

    public void borrarDisparo() {
        huboDisparo = false;
    }

    public String getNombreCheckPoint() {
        if (ultimoCheckPoint == null) {
            return "ninguno";
        }
        return ultimoCheckPoint.getNombre();
    }

    public boolean isGanado() {
        return !jefe.estaVivo();
    }

    public boolean isPerdido() {
        return !jugador.estaVivo();
    }

    public boolean esPared(int x, int y) {
        return mapa[y].charAt(x) == '#';
    }

    public Obstaculo obstaculoEn(int x, int y) {
        for (Obstaculo o : obstaculos) {
            if (!o.isDestruido() && o.getX() == x && o.getY() == y) {
                return o;
            }
        }
        return null;
    }

    public Enemigo enemigoEn(int x, int y) {
        for (Enemigo e : enemigos) {
            if (e.estaVivo() && e.getX() == x && e.getY() == y) {
                return e;
            }
        }
        return null;
    }

    public boolean estaLibre(int x, int y) {
        if (esPared(x, y) || obstaculoEn(x, y) != null || enemigoEn(x, y) != null) {
            return false;
        }
        if (jugador.getX() == x && jugador.getY() == y) {
            return false;
        }
        return true;
    }

    public boolean estaEnElSuelo() {
        return !estaLibre(jugador.getX(), jugador.getY() + 1);
    }

    public void moverJugador(String direccion) {
        mensaje = "";
        jugador.setMirando(direccion);
        int nuevaX = jugador.getX() + 1;
        if (direccion.equals("oeste")) {
            nuevaX = jugador.getX() - 1;
        }
        if (estaLibre(nuevaX, jugador.getY())) {
            jugador.mover(direccion, 1);
        }
        terminarTurno(true);
    }

    public void saltar() {
        mensaje = "";
        if (estaEnElSuelo()) {
            for (int i = 0; i < 2; i++) {
                if (estaLibre(jugador.getX(), jugador.getY() - 1)) {
                    jugador.mover("norte", 1);
                }
            }
            terminarTurno(false);
        } else {
            terminarTurno(true);
        }
    }

    public void esperar() {
        mensaje = "";
        terminarTurno(true);
    }

    public void cambiarArma() {
        jugador.cambiarArma();
        mensaje = "Ahora usas: " + jugador.getArmaActual().getNombre();
    }

    public void disparar() {
        Arma arma = jugador.getArmaActual();
        int paso = 1;
        if (jugador.getMirando().equals("oeste")) {
            paso = -1;
        }
        int x = jugador.getX();
        int y = jugador.getY();
        mensaje = "El disparo no le dio a nada";

        for (int i = 1; i <= arma.getAlcance(); i++) {
            if (esPared(x + paso, y)) {
                break;
            }
            x = x + paso;

            Obstaculo obstaculo = obstaculoEn(x, y);
            if (obstaculo != null) {
                if (obstaculo.puedeRomperse(arma)) {
                    obstaculo.destruye();
                    mensaje = "Rompiste: " + obstaculo.getNombre();
                } else {
                    mensaje = "Necesitas el misil para romper la puerta";
                }
                break;
            }

            Enemigo enemigo = enemigoEn(x, y);
            if (enemigo != null) {
                int vidaAntes = enemigo.getVida();
                enemigo.recibirDisparo(arma);
                if (enemigo.getVida() == vidaAntes) {
                    mensaje = "El " + arma.getNombre() + " no le hace nada al " + enemigo.getNombre();
                } else if (enemigo.estaVivo()) {
                    mensaje = "Le diste al " + enemigo.getNombre();
                } else {
                    mensaje = "Destruiste al " + enemigo.getNombre() + "!";
                    jugador.sumarPuntos(enemigo.getPuntos());
                }
                break;
            }
        }

        huboDisparo = true;
        disparoInicioX = jugador.getX();
        disparoFinX = x;
        disparoY = y;
        terminarTurno(true);
    }

    private void terminarTurno(boolean conGravedad) {
        if (conGravedad && !estaEnElSuelo()) {
            jugador.mover("sur", 1);
        }
        recogerObjetos();
        revisarCheckPoints();
        moverElementosDinamicos();
        ataquesEnemigos();

        if (!jugador.estaVivo() && ultimoCheckPoint != null) {
            ultimoCheckPoint.cargarPartida(this);
            mensaje = "Perdiste toda tu vida. Regresaste a la " + ultimoCheckPoint.getNombre();
        }
    }

    private void recogerObjetos() {
        for (Utileria objeto : objetos) {
            if (!objeto.isUsada() && objeto.getX() == jugador.getX() && objeto.getY() == jugador.getY()) {
                objeto.setUsada(true);
                inventario.agregarItem(objeto.getContenido());
                objeto.getContenido().aplicar(jugador);
                mensaje = mensaje + " Recogiste: " + objeto.getNombre();
            }
        }
    }

    private void revisarCheckPoints() {
        for (CheckPoint estacion : checkPoints) {
            if (estacion.getX() == jugador.getX() && estacion.getY() == jugador.getY()) {
                estacion.activar();
                jugador.setVida(jugador.getVidaMaxima());
                estacion.guardarPartida(this);
                ultimoCheckPoint = estacion;
                mensaje = mensaje + " " + estacion.getNombre() + ": partida guardada";
            }
        }
    }

    public void moverElementosDinamicos() {
        for (Enemigo enemigo : enemigos) {
            String direccion = enemigo.elegirDireccion();
            int nuevaX = enemigo.getX();
            if (direccion.equals("este")) {
                nuevaX = nuevaX + 1;
            }
            if (direccion.equals("oeste")) {
                nuevaX = nuevaX - 1;
            }
            if (enemigo.estaVivo() && nuevaX != enemigo.getX() && estaLibre(nuevaX, enemigo.getY())) {
                enemigo.mover(direccion, 1);
            }
        }
    }

    private void ataquesEnemigos() {
        for (Enemigo enemigo : enemigos) {
            int distancia = Math.abs(enemigo.getX() - jugador.getX());
            if (enemigo.estaVivo() && enemigo.getY() == jugador.getY() && distancia <= enemigo.getAlcance()) {
                jugador.recibirDano(enemigo.getDano());
                mensaje = mensaje + " El " + enemigo.getNombre() + " te hizo " + enemigo.getDano() + " de dano!";
            }
        }
    }
}
