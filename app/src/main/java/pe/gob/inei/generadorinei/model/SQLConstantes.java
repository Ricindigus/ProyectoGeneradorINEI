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


    public static final String tablacamposmarco = "campos_marco";

    public static final String campos_marco_id = "_id";
    public static final String campos_marco_var1 = "var1";
    public static final String campos_marco_var2 = "var2";
    public static final String campos_marco_var3 = "var3";
    public static final String campos_marco_var4 = "var4";
    public static final String campos_marco_var5 = "var5";
    public static final String campos_marco_var6 = "var6";
    public static final String campos_marco_var7 = "var7";
    public static final String campos_marco_peso1 = "peso1";
    public static final String campos_marco_peso2 = "peso2";
    public static final String campos_marco_peso3 = "peso3";
    public static final String campos_marco_peso4 = "peso4";
    public static final String campos_marco_peso5 = "peso5";
    public static final String campos_marco_peso6 = "peso6";
    public static final String campos_marco_peso7 = "peso7";
    public static final String campos_marco_nombre1 = "nombre1";
    public static final String campos_marco_nombre2 = "nombre2";
    public static final String campos_marco_nombre3 = "nombre3";
    public static final String campos_marco_nombre4 = "nombre4";
    public static final String campos_marco_nombre5 = "nombre5";
    public static final String campos_marco_nombre6 = "nombre6";
    public static final String campos_marco_nombre7 = "nombre7";


    public static final String tablafiltrosmarco = "filtros_marco";

    public static final String filtros_marco_id = "_id";
    public static final String filtros_marco_filtro1 = "filtro1";
    public static final String filtros_marco_filtro2 = "filtro2";
    public static final String filtros_marco_filtro3 = "filtro3";
    public static final String filtros_marco_filtro4 = "filtro4";
    public static final String filtros_marco_nombre1 = "nombre1";
    public static final String filtros_marco_nombre2 = "nombre2";
    public static final String filtros_marco_nombre3 = "nombre3";
    public static final String filtros_marco_nombre4 = "nombre4";






    public static final String tablamodulos = "modulos";

    public static final String modulos_id = "_id";
    public static final String modulos_titulo = "titulo";
    public static final String modulos_cabecera = "cabecera";
    public static final String modulos_tipo_encuesta = "tipo_encuesta";



    public static final String tablapaginas = "paginas";

    public static final String paginas_id = "_id";
    public static final String paginas_numero = "numero";
    public static final String paginas_modulo = "modulo";
    public static final String paginas_tipo_encuesta = "tipo_encuesta";


    public static final String tablapreguntas = "preguntas";

    public static final String preguntas_id = "_id";
    public static final String preguntas_modulo = "modulo";
    public static final String preguntas_pagina = "pagina";
    public static final String preguntas_numero = "numero";
    public static final String preguntas_tipo = "tipo";


    public static final String tablavariables = "variables";

    public static final String variables_id = "_id";
    public static final String variables_id_tabla = "id_tabla";
    public static final String variables_id_modulo = "id_modulo";
    public static final String variables_id_pagina = "id_pagina";
    public static final String variables_id_pregunta = "id_pregunta";


    public static final String tablaeventos = "eventos";

    public static final String eventos_id = "_id";
    public static final String eventos_idpag1 = "idpag1";
    public static final String eventos_idpreg1 = "idpreg1";
    public static final String eventos_idvar = "idvar";
    public static final String eventos_valor = "valor";
    public static final String eventos_idpag2 = "idpag2";
    public static final String eventos_idpreg2 = "idpreg2";
    public static final String eventos_accion = "accion";







    /** Tablas secundarias que conforman la aplicacion:
     *  -Estas tablas se "crean" iniciar la aplicacion por primera vez
     *  -Almacenan datos para el funcionamiento de la aplicacion en produccion.
     *  -Tablas de guardado de datos capturados
     * */


    /** Clausulas WHERE * */
    public static final String clausula_where_id = "_id=?";



    /**Inicio Tablas Componentes **/
    public static final String tablacheckbox = "c_checkbox";
    /**InicioCampos**/
    public static final String checkbox_id = "_id";
    public static final String checkbox_modulo = "modulo";
    public static final String checkbox_numero = "numero";
    public static final String checkbox_pregunta = "pregunta";
    public static final String checkbox_id_tabla = "id_tabla";

    /**Fin Campos**/
    public static final String tablaedittext = "c_edittext";
    /**InicioCampos**/
    public static final String edittext_id = "_id";
    public static final String edittext_modulo = "modulo";
    public static final String edittext_numero = "numero";
    public static final String edittext_pregunta = "pregunta";
    public static final String edittext_id_tabla = "id_tabla";
    /**Fin Campos**/
    public static final String tablaformulario = "c_formulario";
    /**InicioCampos**/
    public static final String formulario_id = "_id";
    public static final String formulario_id_modulo = "id_modulo";
    public static final String formulario_id_numero = "id_numero";
    public static final String formulario_titulo = "titulo";
    public static final String formulario_id_tabla = "id_tabla";
    /**Fin Campos**/
    public static final String tablagps = "c_gps";
    /**InicioCampos**/
    public static final String gps_id = "_id";
    public static final String gps_modulo = "modulo";
    public static final String gps_numero = "numero";
    public static final String gps_var_lat = "var_lat";
    public static final String gps_var_long = "var_long";
    public static final String gps_var_alt = "var_alt";
    public static final String gps_id_tabla = "id_tabla";
    /**Fin Campos**/
    public static final String tablaradio = "c_radio";
    /**InicioCampos**/
    public static final String radio_id = "_id";
    public static final String radio_modulo = "modulo";
    public static final String radio_numero = "numero";
    public static final String radio_pregunta = "pregunta";
    public static final String radio_id_tabla = "id_tabla";
    /**Fin Campos**/
    public static final String tablaspcheckbox = "c_sp_checkbox";
    /**InicioCampos**/
    public static final String sp_checkbox_id = "_id";
    public static final String sp_checkbox_id_pregunta = "id_pregunta";
    public static final String sp_checkbox_subpregunta = "subpregunta";
    public static final String sp_checkbox_var_guardado = "var_guardado";
    public static final String sp_checkbox_var_especifique = "var_especifique";
    public static final String sp_checkbox_deshabilita = "deshabilita";
    /**Fin Campos**/
    public static final String tablaspedittext = "c_sp_edittext";
    /**InicioCampos**/
    public static final String sp_edittext_id = "_id";
    public static final String sp_edittex_id_pregunta = "id_pregunta";
    public static final String sp_edittex_subpregunta = "subpregunta";
    public static final String sp_edittex_var_input = "var_input";
    public static final String sp_edittex_tipo = "tipo";
    public static final String sp_edittex_longitud = "longitud";
    /**Fin Campos**/
    public static final String tablaspformulario = "c_sp_formulario";
    /**InicioCampos**/
    public static final String sp_formulario_id = "_id";
    public static final String sp_formulario_id_pregunta = "id_pregunta";
    public static final String sp_formulario_subpregunta = "subpregunta";
    public static final String sp_formulario_var_edittext = "var_edittext";
    public static final String sp_formulario_tipo_edittext = "tipo_edittext";
    public static final String sp_formulario_long_edittext = "long_edittext";
    public static final String sp_formulario_var_spinner = "var_spinner";
    public static final String sp_formulario_var_esp_spinner = "var_esp_spinner";
    public static final String sp_formulario_tipo_esp_spinner = "tipo_esp_spinner";
    public static final String sp_formulario_long_esp_spinner = "long_esp_spinner";
    public static final String sp_formulario_hab_esp_spinner = "hab_esp_spinner";
    public static final String sp_formulario_var_check_no = "var_check_no";

    /**Fin Campos**/
    public static final String tablaspradio = "c_sp_radio";
    /**InicioCampos**/
    public static final String sp_radio_id = "_id";
    public static final String sp_radio_id_pregunta = "id_pregunta";
    public static final String sp_radio_subpregunta = "subpregunta";
    public static final String sp_radio_var_input = "var_input";
    public static final String sp_radio_var_especifique = "var_especifique";
    public static final String sp_radio_deshabilita = "deshabilita";
    /**Fin Campos**/
    public static final String tablaubicacion = "c_ubicacion";
    /**InicioCampos**/
    public static final String ubicacion_id = "_id";
    public static final String ubicacion_modulo = "modulo";
    public static final String ubicacion_numero = "numero";
    public static final String ubicacion_var_dep = "var_dep";
    public static final String ubicacion_var_prov = "var_prov";
    public static final String ubicacion_var_dist = "var_dist";
    public static final String ubicacion_id_tabla = "id_tabla";
    /**Fin Campos**/
    public static final String tablavisitasencuestador = "c_visitas_encuestador";
    /**InicioCampos**/
    public static final String visitas_encuestador_id = "_id";
    public static final String visitas_encuestador_modulo = "modulo";
    public static final String visitas_encuestador_numero = "numero";
    public static final String visitas_encuestador_var_numero = "var_numero";
    public static final String visitas_encuestador_var_dia = "var_dia";
    public static final String visitas_encuestador_var_mes = "var_mes";
    public static final String visitas_encuestador_var_anio = "var_anio";
    public static final String visitas_encuestador_var_hora_inicio = "var_hora_inicio";
    public static final String visitas_encuestador_var_min_inicio = "var_min_inicio";
    public static final String visitas_encuestador_var_hor_final = "var_hor_final";
    public static final String visitas_encuestador_var_min_final = "var_min_final";
    public static final String visitas_encuestador_var_prox_dia = "var_prox_dia";
    public static final String visitas_encuestador_var_prox_mes = "var_prox_mes";
    public static final String visitas_encuestador_var_prox_anio = "var_prox_anio";
    public static final String visitas_encuestador_var_prox_hora = "var_prox_hora";
    public static final String visitas_encuestador_var_prox_min = "var_prox_min";
    public static final String visitas_encuestador_var_resultado = "var_resultado";
    public static final String visitas_encuestador_var_final_resultado = "var_final_resultado";
    public static final String visitas_encuestador_var_final_dia = "var_final_dia";
    public static final String visitas_encuestador_var_final_mes = "var_final_mes";
    public static final String visitas_encuestador_var_final_anio = "var_final_anio";
    public static final String visitas_encuestador_id_tabla_visitas = "var_id_tabla_visitas";
    public static final String visitas_encuestador_id_tabla_resultado = "var_id_tabla_resultado";
    /**Fin Campos**/
    public static final String tablavisitassupervisor = "c_visitas_supervisor";
    /**InicioCampos**/
    public static final String visitas_supervisor_id = "_id";
    public static final String visitas_supervisor_modulo = "modulo";
    public static final String visitas_supervisor_numero = "numero";
    public static final String visitas_supervisor_var_numero = "var_numero";
    public static final String visitas_supervisor_var_dia = "var_dia";
    public static final String visitas_supervisor_var_mes = "var_mes";
    public static final String visitas_supervisor_var_anio = "var_anio";
    public static final String visitas_supervisor_var_hora_inicio = "var_hora_inicio";
    public static final String visitas_supervisor_var_min_inicio = "var_min_inicio";
    public static final String visitas_supervisor_var_hor_final = "var_hor_final";
    public static final String visitas_supervisor_var_min_final = "var_min_final";
    public static final String visitas_supervisor_var_resultado = "var_resultado";
    public static final String visitas_supervisor_var_final_resultado = "var_final_resultado";
    public static final String visitas_supervisor_var_final_dia = "var_final_dia";
    public static final String visitas_supervisor_var_final_mes = "var_final_mes";
    public static final String visitas_supervisor_var_final_anio = "var_final_anio";
    public static final String visitas_supervisor_id_tabla_visitas = "var_id_tabla_visitas";
    public static final String visitas_supervisor_id_tabla_resultado = "var_id_tabla_resultado";
    /**Fin Campos**/

    /**Fin Tablas Componentes **/





}
