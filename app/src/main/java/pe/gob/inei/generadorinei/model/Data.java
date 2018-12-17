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

public class Data {
    Context contexto;
    SQLiteOpenHelper sqLiteOpenHelper;
    SQLiteDatabase sqLiteDatabase;

    public Data(Context contexto){
        this.contexto = contexto;
        sqLiteOpenHelper = new DBHelper(contexto);
    }

    public Data(Context contexto,int flag) throws IOException {
        this.contexto = contexto;
        sqLiteOpenHelper = new DBHelper(contexto);
        createDataBase();
    }

    public Data(Context contexto, String inputPath) throws IOException {
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
        Encuesta encuesta = new Encuesta();
        String[] whereArgs = new String[]{"1"};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablaencuestas,
                    null,SQLConstantes.clausula_where_id,whereArgs,null,null,null);
            if(cursor.getCount() == 1){
                cursor.moveToFirst();
                encuesta.setTipo(cursor.getString(cursor.getColumnIndex(SQLConstantes.encuestas_tipo)));
                encuesta.setTitulo(cursor.getString(cursor.getColumnIndex(SQLConstantes.encuestas_titulo)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return encuesta;
    }


    public Usuario getUsuario(String idUsuario){
        Usuario usuario = new Usuario();
        String[] whereArgs = new String[]{idUsuario};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablausuarios,
                    null,SQLConstantes.clausula_where_id,whereArgs,null,null,null);
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
        return usuario;
    }


    public Marco getMarco(String idMarco){
        Marco marco = new Marco();
        String[] whereArgs = new String[]{idMarco};
        Cursor cursor = null;
        try{
            cursor = sqLiteDatabase.query(SQLConstantes.tablamarco,
                    null,SQLConstantes.clausula_where_id,whereArgs,null,null,null);
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
                marco.setProvincia(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setCcdi(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setDistrito(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setCodccpp(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setNomccpp(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setRuc(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setRazon_social(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setNombre_comercial(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setTipo_empresa(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setEncuestador(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setSupervisor(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setCoordinador(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setFrente(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setNumero_orden(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setManzana_id(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setTipo_via(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setNombre_via(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setPuerta(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setLote(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setPiso(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setManzana(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setBlock(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setInterior(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
                marco.setEstado(cursor.getString(cursor.getColumnIndex(SQLConstantes.marco_id)));
            }
        }finally{
            if(cursor != null) cursor.close();
        }
        return marco;
    }

}
