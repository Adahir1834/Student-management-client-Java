package com.example.aplicacion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listViewAlumnos;
    private Button btnAgregarAlumno;
    private Button btnActualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewAlumnos = findViewById(R.id.listViewAlumnos);
        btnAgregarAlumno = findViewById(R.id.btnAgregarAlumno);
        btnActualizar = findViewById(R.id.btnConsultar);

        // Cargar estudiantes al iniciar
        cargarEstudiantes();

        // Configurar el botón de agregar alumno
        btnAgregarAlumno.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
            startActivity(intent);
        });

        // Configurar el botón de actualizar
        btnActualizar.setOnClickListener(v -> cargarEstudiantes());

        // Listener para la lista
        listViewAlumnos.setOnItemClickListener((parent, view, position, id) -> {
            Student studentSeleccionado = (Student) parent.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, StudentDetailActivity.class);
            intent.putExtra("estudiante", studentSeleccionado);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarEstudiantes(); // Actualizar la lista al regresar a la actividad
    }

    private void cargarEstudiantes() {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        apiService.obtenerTodos().enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Student> students = response.body();
                    ArrayAdapter<Student> adapter = new ArrayAdapter<>(MainActivity.this,
                            android.R.layout.simple_list_item_1, students);
                    listViewAlumnos.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {
                // Manejo de errores
            }
        });
    }
}
