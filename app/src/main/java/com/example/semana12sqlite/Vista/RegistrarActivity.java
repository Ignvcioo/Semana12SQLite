package com.example.semana12sqlite.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.semana12sqlite.Controlador.ConexionHelper;
import com.example.semana12sqlite.Controlador.Utility;
import com.example.semana12sqlite.R;

public class RegistrarActivity extends AppCompatActivity {

    EditText txtID;
    EditText txtNombre;
    EditText txtCorreo;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);
        txtID = findViewById(R.id.txtIDRegistro);
        txtNombre = findViewById(R.id.txtNombreRegistro);
        txtCorreo = findViewById(R.id.txtCorreoRegistro);
        btnRegistrar = findViewById(R.id.btnRegistrarUsuario);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrarUsuarios();
            }
        });
    }

    private void registrarUsuarios() {
        ConexionHelper conn = new ConexionHelper(this, "bd_usuarios", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(Utility.CAMPO_ID, txtID.getText().toString());
        contentValues.put(Utility.CAMPO_NOMBRE, txtNombre.getText().toString());
        contentValues.put(Utility.CAMPO_CORREO, txtCorreo.getText().toString());

        Long idResultante = db.insert(Utility.TABLA_USUARIO, Utility.CAMPO_ID, contentValues);
        Toast.makeText(getApplicationContext(), "ATENCION, id registrado..." + idResultante, Toast.LENGTH_SHORT).show();
        db.close();
    }
}
