import javafx.scene.paint.Color;

public class Personaje implements Destruible, ElementoDinamico {
    private String nombre;
    private int vida;
    private int x;
    private int y;

    public Personaje(String nombre, int vida, int x, int y) {
        this.nombre = nombre;
        this.vida = vida;
        this.x = x;
        this.y = y;
    }

    public String getNombre() {
        return nombre;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosicion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean estaVivo() {
        return vida > 0;
    }

    public void recibirDano(int dano) {
        vida = vida - dano;
        if (vida <= 0) {
            destruye();
        }
    }

    @Override
    public void mover(String direccion, int distancia) {
        if (direccion.equals("norte")) {
            y = y - distancia;
        }
        if (direccion.equals("sur")) {
            y = y + distancia;
        }
        if (direccion.equals("este")) {
            x = x + distancia;
        }
        if (direccion.equals("oeste")) {
            x = x - distancia;
        }
    }

    @Override
    public void destruye() {
        vida = 0;
        System.out.println(nombre + " fue destruido");
    }

    public Color getColor() {
        return Color.DODGERBLUE;
    }
}
