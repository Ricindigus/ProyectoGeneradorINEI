package pe.gob.inei.generadorinei.model.pojos.componentes;

public class SpinnerFormulario {
    private String _id;
    private String id_variable;
    private String numero_dato;
    private String dato;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getId_variable() {
        return id_variable;
    }

    public void setId_variable(String id_variable) {
        this.id_variable = id_variable;
    }

    public String getNumero_dato() {
        return numero_dato;
    }

    public void setNumero_dato(String numero_dato) {
        this.numero_dato = numero_dato;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }
}
