package pe.gob.inei.generadorinei.model.pojos.componentes;

public class PRadio {
    private String _id;
    private String modulo;
    private String numero;
    private String pregunta;
    private String id_tabla;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getModulo() {
        return modulo;
    }

    public void setModulo(String modulo) {
        this.modulo = modulo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }

    public String getId_tabla() {
        return id_tabla;
    }

    public void setId_tabla(String id_tabla) {
        this.id_tabla = id_tabla;
    }
}
