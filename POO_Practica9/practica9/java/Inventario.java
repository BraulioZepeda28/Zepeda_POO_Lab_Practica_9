import java.util.ArrayList;

public class Inventario {
    private int capacidadMaxima;
    private ArrayList<Inventariable> items = new ArrayList<>();

    public Inventario(int capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public int getCantidadItems() {
        return items.size();
    }

    public void agregarItem(Inventariable item) {
        if (items.size() < capacidadMaxima) {
            items.add(item);
            item.registrar();
        }
    }

    public void dejarSoloItems(int cantidad) {
        while (items.size() > cantidad) {
            items.remove(items.size() - 1);
        }
    }

    public void listarItems() {
        System.out.println("Inventario:");
        for (Inventariable item : items) {
            System.out.println("- " + item);
        }
    }
}
