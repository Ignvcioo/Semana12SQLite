package com.example.semana12sqlite.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.semana12sqlite.Controlador.ConexionHelper;
import com.example.semana12sqlite.Controlador.Utility;
import com.example.semana12sqlite.Modelo.Usuario;
import com.example.semana12sqlite.R;

import java.util.ArrayList;

public class ListarActivity extends AppCompatActivity {

    ListView listarUsuario;
    ArrayList<String> listarInformacion;
    ArrayList<Usuario> listarUsuarioInformacion;
    ConexionHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        listarUsuario = findViewById(R.id.listaUsuario);
        conn = new ConexionHelper(getApplicationContext(), "bd_usuarios", null, 1);

        consultarListaUsuarios();

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1,
                listarInformacion);
        listarUsuario.setAdapter(adaptador);

        listarUsuario.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String informacion="id: " + listarUsuarioInformacion.get(position).getId() +"\n";
                informacion+="Nombres: " + listarUsuarioInformacion.get(position).getNombre() +"\n";
                informacion+="Correo: " + listarUsuarioInformacion.get(position).getCorreo() +"\n";
                Toast.makeText(getApplicationContext(), informacion,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void consultarListaUsuarios() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Usuario usuario = null;
        listarUsuarioInformacion = new ArrayList<Usuario>();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Utility.TABLA_USUARIO, null);
        while (cursor.moveToNext()) {
            usuario = new Usuario();
            usuario.setId(cursor.getInt(0));
            usuario.setNombre(cursor.getString(1));
            usuario.setCorreo(cursor.getString(2));
            listarUsuarioInformacion.add(usuario);
        }
        obtenerLista();
    }

    private void obtenerLista(){
        listarInformacion=new ArrayList<String>();
        for (int i=0; i<listarUsuarioInformacion.size(); i++){
            listarInformacion.add(listarUsuarioInformacion.get(i).getId()+ " - " + listarUsuarioInformacion.get(i).getNombre());
        }
    }
}