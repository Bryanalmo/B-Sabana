package android.primer.bryanalvarez.b_sabana.Models;

/**
 * Created by nayar on 21/04/2018.
 */

public class Evento {
    private String descripcion;
    private int personasSolicitadas;
    private int categoria;
    private String usuario;
    private String celularUsuario;

    public Evento(){}

    public Evento(String descripcion, int personasSolicitadas, int categoria, String usuario, String celularUsuario) {
        this.descripcion = descripcion;
        this.personasSolicitadas = personasSolicitadas;
        this.categoria = categoria;
        this.usuario = usuario;
        this.celularUsuario = celularUsuario;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getPersonasSolicitadas() {
        return personasSolicitadas;
    }

    public void setPersonasSolicitadas(int personasSolicitadas) {
        this.personasSolicitadas = personasSolicitadas;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int idCategoria) {
        this.categoria= idCategoria;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String idUsuario) {
        this.usuario = idUsuario;
    }

    public String getCelularUsuario() {
        return celularUsuario;
    }

    public void setCelularUsuario(String celularUsuario) {
        this.celularUsuario = celularUsuario;
    }
}
