package pe.gob.inei.generadorinei.model;

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

    public ArrayList<String> getArrayFiltro1(String column){
        open();
        ArrayList<String> arrayFiltro1 = new ArrayList<>();
        arrayFiltro1.add("Seleccionar");
        String[] columns = {column};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(true, SQLConstantes.tablamarco,
                    columns,null,null,null,null,column,null);
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

    public ArrayList<String> getArrayFiltro2(String column1,String valorFiltro1,String column2){
        open();
        ArrayList<String> arrayFiltro2 = new ArrayList<>();
        arrayFiltro2.add("Seleccionar");
        String[] whereArgs = new String[]{valorFiltro1};
        String[] columns = {column2};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(true, SQLConstantes.tablamarco,
                    columns,column1+"=?",whereArgs,null,null,column2,null);
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

    public ArrayList<String> getArrayFiltro3(String column1,String valorFiltro1,String column2,String valorFiltro2,String column3){
        open();
        ArrayList<String> arrayFiltro3 = new ArrayList<>();
        arrayFiltro3.add("Seleccionar");
        String[] whereArgs = new String[]{valorFiltro1,valorFiltro2};
        String[] columns = {column3};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(true, SQLConstantes.tablamarco,
                    columns,column1+"=?" + " AND " + column2+"=?",whereArgs,null,null,column3,null);
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

    public ArrayList<String> getArrayFiltro4(String column1,String valorFiltro1,
                                             String column2,String valorFiltro2,
                                             String column3,String valorFiltro3,String column4){
        open();
        ArrayList<String> arrayFiltro4 = new ArrayList<>();
        arrayFiltro4.add("Seleccionar");
        String[] whereArgs = new String[]{valorFiltro1,valorFiltro2,valorFiltro3};
        String[] columns = {column4};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(true, SQLConstantes.tablamarco,
                    columns,column1+"=?" + " AND " + column2+"=?"+ " AND " + column3+"=?",whereArgs,null,null,column4,null);
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
}
