package pe.gob.inei.generadorinei.model.pojos;

public class Pagina {
    private String _id;
    private String numero;
    private String modulo;
    private String tipo_actividad;
    private String nombre;

    public Pagina(String _id, String numero, String modulo, String tipo_actividad, String nombre) {
        this._id = _id;
        this.numero = numero;
        this.modulo = modulo;
        this.tipo_actividad = tipo_actividad;
        this.nombre = nombre;
    }

    public Pagina() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getTipo_actividad() {
        return tipo_actividad;
    }

    public void setTipo_actividad(String tipo_actividad) {
        this.tipo_actividad = tipo_actividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
