package android.primer.bryanalvarez.b_sabana.Models;

/**
 * Created by nayar on 22/05/2018.
 */

public class Noticia {

    private String titulo;
    private String descripcion;
    private int categoria;

    public Noticia(String titulo, String descripcion, int categoria) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
}
