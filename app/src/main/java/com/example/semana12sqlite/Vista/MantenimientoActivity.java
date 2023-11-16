package com.example.semana12sqlite.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.semana12sqlite.Controlador.ConexionHelper;
import com.example.semana12sqlite.Controlador.Utility;
import com.example.semana12sqlite.R;

public class MantenimientoActivity extends AppCompatActivity {

    EditText txtID;
    EditText txtNombre;
    EditText txtCorreo;
    Button btnConsultar, btnUpdate, btnDelete;
    ConexionHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mantenimiento);

        conn = new ConexionHelper(getApplicationContext(), "bd_usuarios", null, 1);

        txtID = findViewById(R.id.txtID);
        txtNombre = findViewById(R.id.txtNombre);
        txtCorreo = findViewById(R.id.txtCorreo);

        btnConsultar = findViewById(R.id.btnBuscar);
        btnUpdate = findViewById(R.id.btnActualizar);
        btnDelete = findViewById(R.id.btnEliminar);

        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultar();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarUsuario();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarUsuario();
            }
        });
    }
    private void consultar() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {txtID.getText().toString()};

        try {
            Cursor cursor = db.rawQuery("SELECT " + Utility.CAMPO_NOMBRE + "," + Utility.CAMPO_CORREO +
                    " FROM " + Utility.TABLA_USUARIO + " WHERE " + Utility.CAMPO_ID + "=? " , parametros);
            cursor.moveToFirst();
            txtNombre.setText(cursor.getString(0));
            txtCorreo.setText(cursor.getString(1));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "ATENCION, usuario no existe",
                    Toast.LENGTH_LONG).show();
            limpiar();
        }
    }

    private void actualizarUsuario() {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {txtID.getText().toString()};

        ContentValues values = new ContentValues();
        values.put(Utility.CAMPO_NOMBRE, txtNombre.getText().toString());
        values.put(Utility.CAMPO_CORREO, txtCorreo.getText().toString());

        db.update(Utility.TABLA_USUARIO, values, Utility.CAMPO_ID + "=?", parametros);
        Toast.makeText(getApplicationContext(), "ATENCION, se actualizo el usuario",
                Toast.LENGTH_LONG).show();
        limpiar();
        db.close();
    }

    private void eliminarUsuario(){
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {txtID.getText().toString()};

        db.delete(Utility.TABLA_USUARIO, Utility.CAMPO_ID + "=?" , parametros);
        Toast.makeText(getApplicationContext(), "ATENCION, se elimino el usuario",
                Toast.LENGTH_LONG).show();
        txtID.setText("");
        limpiar();
        db.close();
    }

    private void limpiar() {
        txtNombre.setText("");
        txtCorreo.setText("");
    }
}