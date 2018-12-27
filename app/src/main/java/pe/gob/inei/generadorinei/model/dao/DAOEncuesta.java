package pe.gob.inei.generadorinei.model.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import pe.gob.inei.generadorinei.model.pojos.componentes.PCheckbox;
import pe.gob.inei.generadorinei.model.pojos.componentes.PEditText;
import pe.gob.inei.generadorinei.model.pojos.componentes.PRadio;
import pe.gob.inei.generadorinei.model.pojos.componentes.SPCheckbox;
import pe.gob.inei.generadorinei.model.pojos.componentes.SPEdittext;
import pe.gob.inei.generadorinei.model.pojos.componentes.SPRadio;
import pe.gob.inei.generadorinei.model.pojos.encuesta.CamposMarco;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Encuesta;
import pe.gob.inei.generadorinei.model.pojos.encuesta.FiltrosMarco;
import pe.gob.inei.generadorinei.model.pojos.encuesta.ItemMarco;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Marco;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Modulo;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Pagina;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Pregunta;
import pe.gob.inei.generadorinei.model.pojos.encuesta.Usuario;

public class DAOEncuesta {
    Context contexto;
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase sqLiteDatabase;

    public DAOEncuesta(Context contexto){
        this.contexto = contexto;
        sqLiteOpenHelper = new DBHelper(contexto);
    }

    public DAOEncuesta(Context contexto, int flag) throws IOException {
        this.contexto = contexto;
        sqLiteOpenHelper = new DBHelper(contexto);
        createDataBase();
    }

    public DAOEncuesta(Context contexto, String inputPath) throws IOException {
        this.contexto = contexto;
        sqLiteOpenHelper = new DBHelper(contexto);
        createDataBase(inputPath);
    }


    public void createDataBase() throws IOException {
        boolean dbExist = checkDataBase();
        if(!dbExist){
            sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
            sqLiteDatabase.close();
            try{
                copyDataBase();
                sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
//                sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_CARATULA);

                sqLiteDatabase.close();
            }catch (IOException e){
                throw new Error("Error: copiando base de datos");
            }
        }

    }


    public void createDataBase(String inputPath) throws IOException {
        boolean dbExist = checkDataBase();
        if(dbExist){
            File dbFile = new File(SQLConstantes.DB_PATH + SQLConstantes.DB_NAME);
            SQLiteDatabase.deleteDatabase(dbFile);
        }
        sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
        sqLiteDatabase.close();
        try{
            copyDataBase(inputPath);
            sqLiteDatabase = sqLiteOpenHelper.getWritableDatabase();
//            sqLiteDatabase.execSQL(SQLConstantes.SQL_CREATE_TABLA_CARATULA);

            sqLiteDatabase.close();
        }catch (IOException e){
            throw new Error("Error: copiando base de datos");
        }
    }


    public void copyDataBase() throws IOException{
        InputStream myInput = contexto.getAssets().open(SQLConstantes.DB_NAME);
        String outFileName = SQLConstantes.DB_PATH + SQLConstantes.DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) != -1){
            if (length > 0){
                myOutput.write(buffer,0,length);
            }
        }
        myOutput.flush();
        myInput.close();
        myOutput.close();
    }


    public void copyDataBase(String inputPath) throws IOException{
        InputStream myInput = new FileInputStream(inputPath);
        String outFileName = SQLConstantes.DB_PATH + SQLConstantes.DB_NAME;
        OutputStream myOutput = new FileOutputStream(outFileName);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) != -1){
            if (length > 0){
                myOutput.write(buffer,0,length);
            }
        }
        myOutput.flush();
        myInput.close();
        myOutput.close();

    }

    public void open() throws SQLException {
        String myPath = SQLConstantes.DB_PATH + SQLConstantes.DB_NAME;
        sqLiteDatabase = SQLiteDatabase.openDatabase(myPath,null,SQLiteDatabase.OPEN_READWRITE);
    }

    public synchronized void close(){
        if(sqLiteDatabase != null){
            sqLiteDatabase.close();
        }
    }

    public boolean checkDataBase(){
        try{
            String myPath = SQLConstantes.DB_PATH + SQLConstantes.DB_NAME;
            sqLiteDatabase = SQLiteDatabase.openDatabase(myPath,null, SQLiteDatabase.OPEN_READWRITE);
        }catch (Exception e){
            File dbFile = new File(SQLConstantes.DB_PATH + SQLConstantes.DB_NAME);
            return dbFile.exists();
        }
        if (sqLiteDatabase != null) sqLiteDatabase.close();

        return sqLiteDatabase != null ? true : false;
    }

    public Encuesta getEncuesta(){
        open();
        Encuesta encuesta = new Encuesta();
        String[] whereArgs = new String[]{"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaencuestas,
                    null, SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                encuesta.setTipo(cursor.getString(cursor.getColumnIndex(SQLConstantes.encuestas_tipo)));
                encuesta.setTitulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.encuestas_titulo)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return encuesta;
    }


    public Usuario getUsuario(String idUsuario){
        open();
        Usuario usuario = new Usuario();
        String[] whereArgs = new String[]{idUsuario};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablausuarios,
                    null, SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                usuario.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_id)));
                usuario.setDni(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_dni)));
                usuario.setNombre(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_nombre)));
                usuario.setClave(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_clave)));
                usuario.setCargo_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_cargo_id)));
                usuario.setCoordinador_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_coordinador_id)));
                usuario.setSupervisor_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.usuario_supervisor_id)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return usuario;
    }


    public Marco getMarco(String idMarco){
        open();
        Marco marco = new Marco();
        String[] whereArgs = new String[]{idMarco};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamarco,
                    null, SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                marco.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setAnio(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_anio)));
                marco.setMes(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_mes)));
                marco.setPeriodo(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_periodo)));
                marco.setZona(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_zona)));
                marco.setConglomerado(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_conglomerado)));
                marco.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_ccdd)));
                marco.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_departamento)));
                marco.setCcpp(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_ccpp)));
                marco.setProvincia(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_provincia)));
                marco.setCcdi(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_ccdi)));
                marco.setDistrito(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_distrito)));
                marco.setCodccpp(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_codccpp)));
                marco.setNomccpp(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_nomccpp)));
                marco.setRuc(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_ruc)));
                marco.setRazon_social(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_razon_social)));
                marco.setNombre_comercial(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_nombre_comercial)));
                marco.setTipo_empresa(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_tipo_empresa)));
                marco.setEncuestador(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_encuestador)));
                marco.setSupervisor(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_supervisor)));
                marco.setCoordinador(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_coordinador)));
                marco.setFrente(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_frente)));
                marco.setNumero_orden(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_numero_orden)));
                marco.setManzana_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_manzana_id)));
                marco.setTipo_via(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_tipo_via)));
                marco.setNombre_via(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_nombre_via)));
                marco.setPuerta(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_puerta)));
                marco.setLote(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_lote)));
                marco.setPiso(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_piso)));
                marco.setManzana(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_manzana)));
                marco.setBlock(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_block)));
                marco.setInterior(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_interior)));
                marco.setEstado(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_estado)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return marco;
    }

    public ArrayList<Marco> getAllMarco(String idUsuario){
        open();
        ArrayList<Marco> marcos = new ArrayList<>();
        String[] whereArgs = new String[]{idUsuario};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamarco,
                    null, SQLConstantes.clausula_where_encuestador,whereArgs,null,null,null);
            while (cursor.moveToNext()){
                Marco marco = new Marco();
                marco.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setAnio(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_anio)));
                marco.setMes(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_mes)));
                marco.setPeriodo(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_periodo)));
                marco.setZona(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_zona)));
                marco.setConglomerado(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_conglomerado)));
                marco.setCcdd(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_ccdd)));
                marco.setDepartamento(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_departamento)));
                marco.setCcpp(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_ccpp)));
                marco.setProvincia(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_provincia)));
                marco.setCcdi(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_ccdi)));
                marco.setDistrito(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_distrito)));
                marco.setCodccpp(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_codccpp)));
                marco.setNomccpp(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_nomccpp)));
                marco.setRuc(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_ruc)));
                marco.setRazon_social(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_razon_social)));
                marco.setNombre_comercial(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_nombre_comercial)));
                marco.setTipo_empresa(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_tipo_empresa)));
                marco.setEncuestador(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_encuestador)));
                marco.setSupervisor(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_supervisor)));
                marco.setCoordinador(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_coordinador)));
                marco.setFrente(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_frente)));
                marco.setNumero_orden(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_numero_orden)));
                marco.setManzana_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_manzana_id)));
                marco.setTipo_via(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_tipo_via)));
                marco.setNombre_via(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_nombre_via)));
                marco.setPuerta(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_puerta)));
                marco.setLote(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_lote)));
                marco.setPiso(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_piso)));
                marco.setManzana(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_manzana)));
                marco.setBlock(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_block)));
                marco.setInterior(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_interior)));
                marco.setEstado(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_estado)));
                marcos.add(marco);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return marcos;
    }

    public ArrayList<ItemMarco> getAllItemsMarco(String idUsuario, CamposMarco camposMarco){
        open();
        ArrayList<ItemMarco> marcos = new ArrayList<>();
        String[] whereArgs = new String[]{idUsuario};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamarco,
                    null, SQLConstantes.clausula_where_encuestador,whereArgs,null,null,null);
            while (cursor.moveToNext()){
                ItemMarco itemMarco = new ItemMarco();
                if (!camposMarco.getVar1().equals("")) itemMarco.setCampo1(cursor.getString(cursor.getColumnIndex(camposMarco.getVar1())));
                if (!camposMarco.getVar2().equals("")) itemMarco.setCampo2(cursor.getString(cursor.getColumnIndex(camposMarco.getVar2())));
                if (!camposMarco.getVar3().equals("")) itemMarco.setCampo3(cursor.getString(cursor.getColumnIndex(camposMarco.getVar3())));
                if (!camposMarco.getVar4().equals("")) itemMarco.setCampo4(cursor.getString(cursor.getColumnIndex(camposMarco.getVar4())));
                if (!camposMarco.getVar5().equals("")) itemMarco.setCampo5(cursor.getString(cursor.getColumnIndex(camposMarco.getVar5())));
                if (!camposMarco.getVar6().equals("")) itemMarco.setCampo6(cursor.getString(cursor.getColumnIndex(camposMarco.getVar6())));
                if (!camposMarco.getVar7().equals("")) itemMarco.setCampo7(cursor.getString(cursor.getColumnIndex(camposMarco.getVar7())));
                marcos.add(itemMarco);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return marcos;
    }

    public ArrayList<ItemMarco> getAllItemsMarcoFiltro1(String idUsuario, CamposMarco camposMarco,String column1,String valorFiltro1){
        open();
        ArrayList<ItemMarco> marcos = new ArrayList<>();
        String[] whereArgs = new String[]{idUsuario,valorFiltro1};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamarco,
                    null, SQLConstantes.clausula_where_encuestador + " AND " + column1+"=?",whereArgs,null,null,null);
            while (cursor.moveToNext()){
                ItemMarco itemMarco = new ItemMarco();
                if (!camposMarco.getVar1().equals("")) itemMarco.setCampo1(cursor.getString(cursor.getColumnIndex(camposMarco.getVar1())));
                if (!camposMarco.getVar2().equals("")) itemMarco.setCampo2(cursor.getString(cursor.getColumnIndex(camposMarco.getVar2())));
                if (!camposMarco.getVar3().equals("")) itemMarco.setCampo3(cursor.getString(cursor.getColumnIndex(camposMarco.getVar3())));
                if (!camposMarco.getVar4().equals("")) itemMarco.setCampo4(cursor.getString(cursor.getColumnIndex(camposMarco.getVar4())));
                if (!camposMarco.getVar5().equals("")) itemMarco.setCampo5(cursor.getString(cursor.getColumnIndex(camposMarco.getVar5())));
                if (!camposMarco.getVar6().equals("")) itemMarco.setCampo6(cursor.getString(cursor.getColumnIndex(camposMarco.getVar6())));
                if (!camposMarco.getVar7().equals("")) itemMarco.setCampo7(cursor.getString(cursor.getColumnIndex(camposMarco.getVar7())));
                marcos.add(itemMarco);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return marcos;
    }

    public ArrayList<ItemMarco> getAllItemsMarcoFiltro2(String idUsuario, CamposMarco camposMarco,String column1,String valorFiltro1,String column2,String valorFiltro2){
        open();
        ArrayList<ItemMarco> marcos = new ArrayList<>();
        String[] whereArgs = new String[]{idUsuario,valorFiltro1,valorFiltro2};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamarco,
                    null, SQLConstantes.clausula_where_encuestador + " AND " + column1+"=?" + " AND " + column2+"=?",whereArgs,null,null,null);
            while (cursor.moveToNext()){
                ItemMarco itemMarco = new ItemMarco();
                if (!camposMarco.getVar1().equals("")) itemMarco.setCampo1(cursor.getString(cursor.getColumnIndex(camposMarco.getVar1())));
                if (!camposMarco.getVar2().equals("")) itemMarco.setCampo2(cursor.getString(cursor.getColumnIndex(camposMarco.getVar2())));
                if (!camposMarco.getVar3().equals("")) itemMarco.setCampo3(cursor.getString(cursor.getColumnIndex(camposMarco.getVar3())));
                if (!camposMarco.getVar4().equals("")) itemMarco.setCampo4(cursor.getString(cursor.getColumnIndex(camposMarco.getVar4())));
                if (!camposMarco.getVar5().equals("")) itemMarco.setCampo5(cursor.getString(cursor.getColumnIndex(camposMarco.getVar5())));
                if (!camposMarco.getVar6().equals("")) itemMarco.setCampo6(cursor.getString(cursor.getColumnIndex(camposMarco.getVar6())));
                if (!camposMarco.getVar7().equals("")) itemMarco.setCampo7(cursor.getString(cursor.getColumnIndex(camposMarco.getVar7())));
                marcos.add(itemMarco);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return marcos;
    }

    public ArrayList<ItemMarco> getAllItemsMarcoFiltro3(String idUsuario, CamposMarco camposMarco,String column1,String valorFiltro1,
                                                 String column2,String valorFiltro2,String column3,String valorFiltro3){
        open();
        ArrayList<ItemMarco> marcos = new ArrayList<>();
        String[] whereArgs = new String[]{idUsuario,valorFiltro1,valorFiltro2,valorFiltro3};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamarco,
                    null, SQLConstantes.clausula_where_encuestador + " AND " + column1+"=?" + " AND " + column2+"=?" + " AND " + column3+"=?",
                    whereArgs,null,null,null);
            while (cursor.moveToNext()){
                ItemMarco itemMarco = new ItemMarco();
                if (!camposMarco.getVar1().equals("")) itemMarco.setCampo1(cursor.getString(cursor.getColumnIndex(camposMarco.getVar1())));
                if (!camposMarco.getVar2().equals("")) itemMarco.setCampo2(cursor.getString(cursor.getColumnIndex(camposMarco.getVar2())));
                if (!camposMarco.getVar3().equals("")) itemMarco.setCampo3(cursor.getString(cursor.getColumnIndex(camposMarco.getVar3())));
                if (!camposMarco.getVar4().equals("")) itemMarco.setCampo4(cursor.getString(cursor.getColumnIndex(camposMarco.getVar4())));
                if (!camposMarco.getVar5().equals("")) itemMarco.setCampo5(cursor.getString(cursor.getColumnIndex(camposMarco.getVar5())));
                if (!camposMarco.getVar6().equals("")) itemMarco.setCampo6(cursor.getString(cursor.getColumnIndex(camposMarco.getVar6())));
                if (!camposMarco.getVar7().equals("")) itemMarco.setCampo7(cursor.getString(cursor.getColumnIndex(camposMarco.getVar7())));
                marcos.add(itemMarco);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return marcos;
    }

    public ArrayList<ItemMarco> getAllItemsMarcoFiltro4(String idUsuario, CamposMarco camposMarco,String column1,String valorFiltro1,
                                                 String column2,String valorFiltro2,String column3,String valorFiltro3,String column4,String valorFiltro4){
        open();
        ArrayList<ItemMarco> marcos = new ArrayList<>();
        String[] whereArgs = new String[]{idUsuario,valorFiltro1,valorFiltro2,valorFiltro3,valorFiltro4};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamarco, null,
                    SQLConstantes.clausula_where_encuestador + " AND " + column1+"=?" + " AND " + column2+"=?" + " AND " + column3+"=?" + " AND " + column4+"=?",
                    whereArgs,null,null,null);
            while (cursor.moveToNext()){
                ItemMarco itemMarco = new ItemMarco();
                if (!camposMarco.getVar1().equals("")) itemMarco.setCampo1(cursor.getString(cursor.getColumnIndex(camposMarco.getVar1())));
                if (!camposMarco.getVar2().equals("")) itemMarco.setCampo2(cursor.getString(cursor.getColumnIndex(camposMarco.getVar2())));
                if (!camposMarco.getVar3().equals("")) itemMarco.setCampo3(cursor.getString(cursor.getColumnIndex(camposMarco.getVar3())));
                if (!camposMarco.getVar4().equals("")) itemMarco.setCampo4(cursor.getString(cursor.getColumnIndex(camposMarco.getVar4())));
                if (!camposMarco.getVar5().equals("")) itemMarco.setCampo5(cursor.getString(cursor.getColumnIndex(camposMarco.getVar5())));
                if (!camposMarco.getVar6().equals("")) itemMarco.setCampo6(cursor.getString(cursor.getColumnIndex(camposMarco.getVar6())));
                if (!camposMarco.getVar7().equals("")) itemMarco.setCampo7(cursor.getString(cursor.getColumnIndex(camposMarco.getVar7())));
                marcos.add(itemMarco);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return marcos;
    }

    public ArrayList<String> getArrayFiltro1(String idUsuario, String column){
        open();
        ArrayList<String> arrayFiltro1 = new ArrayList<>();
        arrayFiltro1.add("Seleccionar");
        String[] columns = {column};
        String[] whereArgs = new String[]{idUsuario};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(true, SQLConstantes.tablamarco,
                    columns,SQLConstantes.clausula_where_encuestador,whereArgs,null,null,column,null);
            while (cursor.moveToNext()){
                String elemento = cursor.getString(cursor.getColumnIndex(column));
                arrayFiltro1.add(elemento);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return arrayFiltro1;
    }

    public ArrayList<String> getArrayFiltro2(String idUsuario, String column1,String valorFiltro1,String column2){
        open();
        ArrayList<String> arrayFiltro2 = new ArrayList<>();
        arrayFiltro2.add("Seleccionar");
        String[] whereArgs = new String[]{idUsuario,valorFiltro1};
        String[] columns = {column2};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(true, SQLConstantes.tablamarco,
                    columns,SQLConstantes.clausula_where_encuestador + " AND "+ column1+"=?",whereArgs,null,null,column2,null);
            while (cursor.moveToNext()){
                String elemento = cursor.getString(cursor.getColumnIndex(column2));
                arrayFiltro2.add(elemento);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return arrayFiltro2;
    }

    public ArrayList<String> getArrayFiltro3(String idUsuario,String column1,String valorFiltro1,String column2,String valorFiltro2,String column3){
        open();
        ArrayList<String> arrayFiltro3 = new ArrayList<>();
        arrayFiltro3.add("Seleccionar");
        String[] whereArgs = new String[]{idUsuario,valorFiltro1,valorFiltro2};
        String[] columns = {column3};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(true, SQLConstantes.tablamarco,
                    columns,SQLConstantes.clausula_where_encuestador + " AND "+ column1+"=?" + " AND " + column2+"=?",whereArgs,null,null,column3,null);
            while (cursor.moveToNext()){
                String elemento = cursor.getString(cursor.getColumnIndex(column3));
                arrayFiltro3.add(elemento);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return arrayFiltro3;
    }

    public ArrayList<String> getArrayFiltro4(String idUsuario, String column1,String valorFiltro1,
                                             String column2,String valorFiltro2,
                                             String column3,String valorFiltro3,String column4){
        open();
        ArrayList<String> arrayFiltro4 = new ArrayList<>();
        arrayFiltro4.add("Seleccionar");
        String[] whereArgs = new String[]{idUsuario,valorFiltro1,valorFiltro2,valorFiltro3};
        String[] columns = {column4};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(true, SQLConstantes.tablamarco,
                    columns,SQLConstantes.clausula_where_encuestador + " AND "+ column1+"=?" + " AND " + column2+"=?"+ " AND " + column3+"=?",whereArgs,null,null,column4,null);
            while (cursor.moveToNext()){
                String elemento = cursor.getString(cursor.getColumnIndex(column4));
                arrayFiltro4.add(elemento);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return arrayFiltro4;
    }

    public CamposMarco getCamposMarco(){
        open();
        CamposMarco camposMarco = new CamposMarco();
        String[] whereArgs = new String[]{"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacamposmarco,
                    null, SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                camposMarco.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                camposMarco.setNombre1(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_nombre1)));
                camposMarco.setNombre2(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_nombre2)));
                camposMarco.setNombre3(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_nombre3)));
                camposMarco.setNombre4(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_nombre4)));
                camposMarco.setNombre5(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_nombre5)));
                camposMarco.setNombre6(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_nombre6)));
                camposMarco.setNombre7(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_nombre7)));
                camposMarco.setPeso1(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_peso1)));
                camposMarco.setPeso2(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_peso2)));
                camposMarco.setPeso3(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_peso3)));
                camposMarco.setPeso4(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_peso4)));
                camposMarco.setPeso5(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_peso5)));
                camposMarco.setPeso6(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_peso6)));
                camposMarco.setPeso7(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_peso7)));
                camposMarco.setVar1(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_var1)));
                camposMarco.setVar2(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_var2)));
                camposMarco.setVar3(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_var3)));
                camposMarco.setVar4(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_var4)));
                camposMarco.setVar5(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_var5)));
                camposMarco.setVar6(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_var6)));
                camposMarco.setVar7(cursor.getString(cursor.getColumnIndex(SQLConstantes.campos_marco_var7)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return camposMarco;
    }

    public FiltrosMarco getFiltrosMarco(){
        open();
        FiltrosMarco filtrosMarco = new FiltrosMarco();
        String[] whereArgs = new String[]{"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablafiltrosmarco,
                    null, SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                filtrosMarco.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.filtros_marco_id)));
                filtrosMarco.setFiltro1(cursor.getString(cursor.getColumnIndex(SQLConstantes.filtros_marco_filtro1)));
                filtrosMarco.setFiltro2(cursor.getString(cursor.getColumnIndex(SQLConstantes.filtros_marco_filtro2)));
                filtrosMarco.setFiltro3(cursor.getString(cursor.getColumnIndex(SQLConstantes.filtros_marco_filtro3)));
                filtrosMarco.setFiltro4(cursor.getString(cursor.getColumnIndex(SQLConstantes.filtros_marco_filtro4)));
                filtrosMarco.setNombre1(cursor.getString(cursor.getColumnIndex(SQLConstantes.filtros_marco_nombre1)));
                filtrosMarco.setNombre2(cursor.getString(cursor.getColumnIndex(SQLConstantes.filtros_marco_nombre2)));
                filtrosMarco.setNombre3(cursor.getString(cursor.getColumnIndex(SQLConstantes.filtros_marco_nombre3)));
                filtrosMarco.setNombre4(cursor.getString(cursor.getColumnIndex(SQLConstantes.filtros_marco_nombre4)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return filtrosMarco;
    }


    public Modulo getModulo(String numModulo,String tipoActividad){
        open();
        Modulo modulo = new Modulo();
        String[] whereArgs = new String[]{numModulo,tipoActividad};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamodulos,
                    null,SQLConstantes.clausula_where_numero_modulo +
                    " AND " + SQLConstantes.clausula_where_tipo_actividad,
                    whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                modulo.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.modulos_id)));
                modulo.setTitulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.modulos_titulo)));
                modulo.setCabecera(cursor.getString(cursor.getColumnIndex(SQLConstantes.modulos_cabecera)));
                modulo.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.modulos_numero)));
                modulo.setTipo_actividad(cursor.getString(cursor.getColumnIndex(SQLConstantes.modulos_tipo_actividad)));
            }
        }finally {
            if(cursor != null) cursor.close();
        }
        close();
        return modulo;
    }

    public ArrayList<Modulo> getAllModulos(String tipoActividad){
        open();
        ArrayList<Modulo> modulos = new ArrayList<Modulo>();
        String[] whereArgs = new String[]{tipoActividad};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamodulos,
                    null,SQLConstantes.clausula_where_tipo_actividad,
                    whereArgs,null,null,null);
            while(cursor.moveToNext()){
                Modulo modulo = new Modulo();
                modulo.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.modulos_id)));
                modulo.setTitulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.modulos_titulo)));
                modulo.setCabecera(cursor.getString(cursor.getColumnIndex(SQLConstantes.modulos_cabecera)));
                modulo.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.modulos_numero)));
                modulo.setTipo_actividad(cursor.getString(cursor.getColumnIndex(SQLConstantes.modulos_tipo_actividad)));
                modulos.add(modulo);
            }
        }finally {
            if(cursor != null) cursor.close();
        }
        close();
        return modulos;
    }

    public Pagina getPagina(String numPagina,String tipoActividad){
        open();
        Pagina pagina = new Pagina();
        String[] whereArgs = new String[]{numPagina,tipoActividad};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablapaginas,
                    null,SQLConstantes.clausula_where_numero_pagina +
                            " AND " + SQLConstantes.clausula_where_tipo_actividad,
                    whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                pagina.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_id)));
                pagina.setModulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_modulo)));
                pagina.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_numero)));
                pagina.setNombre(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_nombre)));
                pagina.setTipo_actividad(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_tipo_actividad)));
                pagina.setTipo_pagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_tipo_pagina)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return pagina;
    }

    public ArrayList<Pagina> getAllPaginas(String tipoActividad){
        open();
        ArrayList<Pagina> paginas = new ArrayList<>();
        String[] whereArgs = new String[]{tipoActividad};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablapaginas,
                    null,SQLConstantes.clausula_where_tipo_actividad,
                    whereArgs,null,null,null);
            while (cursor.moveToNext()){
                Pagina pagina = new Pagina();
                pagina.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_id)));
                pagina.setModulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_modulo)));
                pagina.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_numero)));
                pagina.setNombre(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_nombre)));
                pagina.setTipo_actividad(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_tipo_actividad)));
                pagina.setTipo_pagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_tipo_pagina)));
                paginas.add(pagina);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return paginas;
    }

    public int getNroPaginas(String tipoActividad){
        open();
        int numero = 0;
        String[] whereArgs = new String[]{tipoActividad};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablapaginas,
                    null,SQLConstantes.clausula_where_tipo_actividad,
                    whereArgs,null,null,null);
            if (cursor.getCount() > 0){
                numero = cursor.getCount();
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return numero;
    }

    public ArrayList<Pagina> getPaginasxModulo(String idModulo){
        open();
        ArrayList<Pagina> paginas =  new ArrayList<Pagina>();
        String[] whereArgs = new String[]{idModulo};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablapaginas,
                    null,SQLConstantes.clausula_where_modulo_pagina,whereArgs,null,null,null);
            while(cursor.moveToNext()){
                Pagina pagina = new Pagina();
                pagina.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_id)));
                pagina.setModulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_modulo)));
                pagina.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_numero)));
                pagina.setNombre(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_nombre)));
                pagina.setTipo_actividad(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_tipo_actividad)));
                pagina.setTipo_pagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.paginas_tipo_pagina)));
                paginas.add(pagina);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return paginas;
    }

    public Pregunta getPregunta(String idPregunta){
        open();
        Pregunta pregunta = new Pregunta();
        String[] whereArgs = new String[]{idPregunta};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablapreguntas,
                    null,SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                pregunta.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_id)));
                pregunta.setModulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_modulo)));
                pregunta.setPagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_pagina)));
                pregunta.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_numero)));
                pregunta.setTipo_pregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_tipo_pregunta)));
                pregunta.setTipo_actividad(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_tipo_actividad)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return pregunta;
    }

    public ArrayList<Pregunta> getPreguntasXPagina(String idPagina){
        open();
        ArrayList<Pregunta> preguntas = new ArrayList<>();
        String[] whereArgs = new String[]{idPagina};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablapreguntas,
                    null,SQLConstantes.clausula_where_pagina_pregunta,whereArgs,null,null,null);
            while (cursor.moveToNext()){
                Pregunta pregunta = new Pregunta();
                pregunta.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_id)));
                pregunta.setModulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_modulo)));
                pregunta.setPagina(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_pagina)));
                pregunta.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_numero)));
                pregunta.setTipo_pregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_tipo_pregunta)));
                pregunta.setTipo_actividad(cursor.getString(cursor.getColumnIndex(SQLConstantes.preguntas_tipo_actividad)));
                preguntas.add(pregunta);
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return preguntas;
    }

    public PEditText getPEditText(String idPOJOEditText){
        open();
        PEditText pEditText = new PEditText();
        String[] whereArgs = new String[]{idPOJOEditText};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaedittext, null,
                    SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                pEditText.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.edittext_id)));
                pEditText.setModulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.edittext_modulo)));
                pEditText.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.edittext_numero)));
                pEditText.setPregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.edittext_pregunta)));
                pEditText.setId_tabla(cursor.getString(cursor.getColumnIndex(SQLConstantes.edittext_id_tabla)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return pEditText;
    }

    public ArrayList<SPEdittext> getSPEditTexts(String idPregunta) {
        open();
        ArrayList<SPEdittext> spEditTexts = new ArrayList<SPEdittext>();
        String[] whereArgs = new String[]{idPregunta};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaspedittext, null,
                    SQLConstantes.clausula_where_id_pregunta, whereArgs, null, null, null);
            while(cursor.moveToNext()){
                SPEdittext spEditText = new SPEdittext();
                spEditText.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_edittext_id)));
                spEditText.setId_pregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_edittext_id_pregunta)));
                spEditText.setSubpregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_edittext_subpregunta)));
                spEditText.setVar_input(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_edittext_var_input)));
                spEditText.setTipo(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_edittext_tipo)));
                spEditText.setLongitud(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_edittext_longitud)));
                spEditTexts.add(spEditText);
            }
        }finally {
            if(cursor != null) cursor.close();
        }
        close();
        return spEditTexts;
    }

    public PCheckbox getPCheckbox(String idCheckbox){
        open();
        PCheckbox PCheckBox = new PCheckbox();
        String[] whereArgs = new String[]{idCheckbox};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablacheckbox, null,
                    SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                PCheckBox.setId(cursor.getString(cursor.getColumnIndex(SQLConstantes.checkbox_id)));
                PCheckBox.setModulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.checkbox_modulo)));
                PCheckBox.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.checkbox_numero)));
                PCheckBox.setPregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.checkbox_pregunta)));
                PCheckBox.setId_tabla(cursor.getString(cursor.getColumnIndex(SQLConstantes.checkbox_id_tabla)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return PCheckBox;
    }

    public ArrayList<SPCheckbox> getSPCheckBoxs(String idPregunta) {
        open();
        ArrayList<SPCheckbox> spCheckBoxs = new ArrayList<SPCheckbox>();
        String[] whereArgs = new String[]{idPregunta};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaspcheckbox, null,
                    SQLConstantes.clausula_where_id_pregunta, whereArgs, null, null, null);
            while(cursor.moveToNext()){
                SPCheckbox spCheckBox = new SPCheckbox();
                spCheckBox.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_checkbox_id)));
                spCheckBox.setId_pregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_checkbox_id_pregunta)));
                spCheckBox.setSubpregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_checkbox_subpregunta)));
                spCheckBox.setVar_guardado(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_checkbox_var_guardado)));
                spCheckBox.setVar_especifique(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_checkbox_var_especifique)));
                spCheckBox.setDeshabilita(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_checkbox_deshabilita)));
                spCheckBoxs.add(spCheckBox);
            }
        }finally {
            if(cursor != null) cursor.close();
        }
        close();
        return spCheckBoxs;
    }

    public PRadio getPRadio(String idCRadio){
        open();
        PRadio PRadio = new PRadio();
        String[] whereArgs = new String[]{idCRadio};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaradio, null,
                    SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                PRadio.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.radio_id)));
                PRadio.setModulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.radio_modulo)));
                PRadio.setNumero(cursor.getString(cursor.getColumnIndex(SQLConstantes.radio_numero)));
                PRadio.setPregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.radio_pregunta)));
                PRadio.setId_tabla(cursor.getString(cursor.getColumnIndex(SQLConstantes.radio_id_tabla)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        close();
        return PRadio;
    }

    public ArrayList<SPRadio> getSPRadios(String idPregunta) {
        open();
        ArrayList<SPRadio> spRadios = new ArrayList<SPRadio>();
        String[] whereArgs = new String[]{idPregunta};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaspradio, null,
                    SQLConstantes.clausula_where_id_pregunta, whereArgs, null, null, null);
            while(cursor.moveToNext()){
                SPRadio spRadio = new SPRadio();
                spRadio.set_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_radio_id)));
                spRadio.setId_pregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_radio_id_pregunta)));
                spRadio.setSubpregunta(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_radio_subpregunta)));
                spRadio.setVar_input(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_radio_var_input)));
                spRadio.setVar_especifique(cursor.getString(cursor.getColumnIndex(SQLConstantes.sp_radio_var_especifique)));
                spRadios.add(spRadio);
            }
        }finally {
            if(cursor != null) cursor.close();
        }
        close();
        return spRadios;
    }
}
