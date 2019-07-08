package android.primer.bryanalvarez.b_sabana.Models;

/**
 * Created by nayar on 22/05/2018.
 */

public class Reserva {

    private String material;
    private String descripcionMaterial;
    private String hora;
    private String fecha;

    public Reserva(String material, String descripcionMaterial, String hora, String fecha) {
        this.material = material;
        this.descripcionMaterial = descripcionMaterial;
        this.hora = hora;
        this.fecha = fecha;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getDescripcionMaterial() {
        return descripcionMaterial;
    }

    public void setDescripcionMaterial(String descripcionMaterial) {
        this.descripcionMaterial = descripcionMaterial;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
