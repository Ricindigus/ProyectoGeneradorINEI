package pe.gob.inei.generadorinei.model;

public class SQLConstantes {
    public static final String DB_PATH = "/data/data/pe.gob.inei.generadorinei/databases/";
    public static final String DB_NAME = "dbgenerador.sqlite";

    /** Tablas primarias que conforman la aplicacion:
     *  -Estas tablas se "cargan" al iniciar la aplicacion por primera vez
     *  -Contienen datos que permiten generar la encuesta
     *  -Tablas preguntas, subpreguntas, eventos, marco inicial, configuraciones, etc.
     * */

    /** Tabla Encuesta
     *  -contiene los datos generales de la encuesta
     *  @_id: id comun
     *  @titulo: nombre de la encuesta
     *  @tipo: tipo de encuesta (empresas 1, hogar 2)
     * */
    public static final String tablaencuestas = "encuestas";

    public static final String encuestas_id = "_id";
    public static final String encuestas_titulo = "titulo";
    public static final String encuestas_tipo = "tipo";


    /** Tabla Usuarios
     *  -usuarios que tienen acceso a la encuesta
     *  @_id: id comun
     *  @usuario: nickname del usuario de la encuesta
     *  @clave: contraseña del usuario
     *  @cargo_id: cargo del usuario (encuestador 1, supervisor 2, coordinador 3)
     *  @dni: dni del usuario
     *  @nombre: nombre del usuario
     *  @supervisor_id: supervisor del usuario
     *  @coordinador_id: coordinador del usuario
     * */

    public static final String tablausuarios = "usuarios";

    public static final String usuario_id = "_id";
    public static final String usuario_clave = "clave";
    public static final String usuario_cargo_id = "cargo_id";
    public static final String usuario_dni = "dni";
    public static final String usuario_nombre = "nombre";
    public static final String usuario_supervisor_id = "supervisor_id";
    public static final String usuario_coordinador_id = "coordinador_id";

    /** Tabla Marco
     *  -marco general de la encuesta
     *  @_id: id comun
     *  @anio: año

     * */

    public static final String tablamarco = "marco";

    public static final String marco_id = "_id";
    public static final String marco_anio = "anio";
    public static final String marco_mes = "mes";
    public static final String marco_periodo = "periodo";
    public static final String marco_zona = "zona";
    public static final String marco_conglomerado = "conglomerado";
    public static final String marco_ccdd = "ccdd";
    public static final String marco_departamento = "departamento";
    public static final String marco_ccpp = "ccpp";
    public static final String marco_provincia = "provincia";
    public static final String marco_ccdi = "ccdi";
    public static final String marco_distrito = "distrito";
    public static final String marco_codccpp = "codccpp";
    public static final String marco_nomccpp = "nomccpp";
    public static final String marco_ruc = "ruc";
    public static final String marco_razon_social = "razon_social";
    public static final String marco_nombre_comercial = "nombre_comercial";
    public static final String marco_tipo_empresa = "tipo_empresa";
    public static final String marco_encuestador = "encuestador";
    public static final String marco_supervisor = "supervisor";
    public static final String marco_coordinador = "coordinador";
    public static final String marco_frente = "frente";
    public static final String marco_numero_orden = "numero_orden";
    public static final String marco_manzana_id = "manzana_id";
    public static final String marco_tipo_via = "tipo_via";
    public static final String marco_nombre_via = "nombre_via";
    public static final String marco_puerta = "puerta";
    public static final String marco_lote = "lote";
    public static final String marco_piso = "piso";
    public static final String marco_manzana = "manzana";
    public static final String marco_block = "block";
    public static final String marco_interior = "interior";
    public static final String marco_estado = "estado";


    public static final String tablacamposmarco = "marco";
    public static final String tablafiltrosmarco = "marco";


    public static final String tablamodulos = "modulos";
    public static final String tablapaginas = "paginas";
    public static final String tablapreguntas = "preguntas";
    public static final String tablavariables = "variables";
    public static final String tablaeventos = "eventos";




    /** Tablas secundarias que conforman la aplicacion:
     *  -Estas tablas se "crean" iniciar la aplicacion por primera vez
     *  -Almacenan datos para el funcionamiento de la aplicacion en produccion.
     *  -Tablas de guardado de datos capturados
     * */


    /** Clausulas WHERE * */
    public static final String clausula_where_id = "_id=?";





}
