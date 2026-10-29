import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class JuegoFX extends Application {
    private final int TAM = 40;

    private Nivel nivel;
    private Canvas canvas;
    private Label lblDatos = new Label();
    private Label lblMensaje = new Label();

    @Override
    public void start(Stage ventana) {
        nivel = new Nivel(new Inventario(10));
        canvas = new Canvas(nivel.getColumnas() * TAM, nivel.getFilas() * TAM);

        lblDatos.setFont(Font.font(18));
        lblMensaje.setFont(Font.font(16));
        Label lblControles = new Label("Flechas: mover    Espacio: saltar    F: disparar    Q: cambiar arma    S: esperar    I: inventario    R: reiniciar");

        VBox raiz = new VBox(10);
        raiz.setPadding(new Insets(10));
        raiz.getChildren().addAll(lblDatos, canvas, lblMensaje, lblControles);

        Scene escena = new Scene(raiz);
        escena.setOnKeyPressed(evento -> teclaPresionada(evento.getCode()));

        ventana.setTitle("Practica 9 - Metroid sencillo");
        ventana.setScene(escena);
        ventana.show();

        dibujar();
    }

    private void teclaPresionada(KeyCode tecla) {
        if (tecla == KeyCode.R) {
            nivel = new Nivel(new Inventario(10));
        } else if (nivel.isGanado() || nivel.isPerdido()) {
            return;
        } else if (tecla == KeyCode.LEFT) {
            nivel.moverJugador("oeste");
        } else if (tecla == KeyCode.RIGHT) {
            nivel.moverJugador("este");
        } else if (tecla == KeyCode.SPACE || tecla == KeyCode.UP) {
            nivel.saltar();
        } else if (tecla == KeyCode.F) {
            nivel.disparar();
        } else if (tecla == KeyCode.Q) {
            nivel.cambiarArma();
        } else if (tecla == KeyCode.S || tecla == KeyCode.DOWN) {
            nivel.esperar();
        } else if (tecla == KeyCode.I) {
            nivel.getInventario().listarItems();
        }
        dibujar();
    }

    private void dibujar() {
        GraphicsContext g = canvas.getGraphicsContext2D();

        g.setFill(Color.BLACK);
        g.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        g.setFill(Color.GRAY);
        for (int fila = 0; fila < nivel.getFilas(); fila++) {
            for (int col = 0; col < nivel.getColumnas(); col++) {
                if (nivel.esPared(col, fila)) {
                    g.fillRect(col * TAM, fila * TAM, TAM, TAM);
                }
            }
        }

        for (Obstaculo o : nivel.getObstaculos()) {
            if (!o.isDestruido()) {
                g.setFill(o.getColor());
                g.fillRect(o.getX() * TAM, o.getY() * TAM, TAM, TAM);
            }
        }

        for (CheckPoint c : nivel.getCheckPoints()) {
            g.setFill(c.getColor());
            g.fillRect(c.getX() * TAM, c.getY() * TAM + TAM - 10, TAM, 10);
        }

        for (Utileria u : nivel.getObjetos()) {
            if (!u.isUsada()) {
                g.setFill(u.getColor());
                g.fillOval(u.getX() * TAM + 10, u.getY() * TAM + 10, 20, 20);
            }
        }

        for (Enemigo e : nivel.getEnemigos()) {
            if (e.estaVivo()) {
                g.setFill(e.getColor());
                g.fillOval(e.getX() * TAM + 4, e.getY() * TAM + 4, 32, 32);
            }
        }

        Jugador jugador = nivel.getJugador();
        g.setFill(jugador.getColor());
        g.fillRect(jugador.getX() * TAM + 8, jugador.getY() * TAM + 4, 24, 36);

        if (nivel.getHuboDisparo()) {
            g.setStroke(Color.YELLOW);
            g.setLineWidth(4);
            int y = nivel.getDisparoY() * TAM + 20;
            g.strokeLine(nivel.getDisparoInicioX() * TAM + 20, y, nivel.getDisparoFinX() * TAM + 20, y);
            nivel.borrarDisparo();
        }

        g.setFont(Font.font(50));
        if (nivel.isGanado()) {
            g.setFill(Color.LIME);
            g.fillText("GANASTE!", 290, 130);
        } else if (nivel.isPerdido()) {
            g.setFill(Color.RED);
            g.fillText("GAME OVER", 270, 130);
        }

        lblDatos.setText("Vida: " + jugador.getVida() + "/" + jugador.getVidaMaxima()
                + "    Arma: " + jugador.getArmaActual().getNombre()
                + "    Puntos: " + jugador.getPuntos()
                + "    Checkpoint: " + nivel.getNombreCheckPoint());
        lblMensaje.setText(nivel.getMensaje());
    }
}
