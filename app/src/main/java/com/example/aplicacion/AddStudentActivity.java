package com.example.aplicacion;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddStudentActivity extends AppCompatActivity {

    private EditText editTextNombre, editTextEdad, editTextGrupo, editTextPromedio;
    private Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextEdad = findViewById(R.id.editTextEdad);
        editTextGrupo = findViewById(R.id.editTextGrupo);
        editTextPromedio = findViewById(R.id.editTextPromedio);
        btnRegistrar = findViewById(R.id.btnRegistrar);

        // Listener para validar la edad mientras se escribe
        editTextEdad.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 3) {
                    Toast.makeText(AddStudentActivity.this, "Edad válida", Toast.LENGTH_SHORT).show();
                } else if (s.length() >=4) {
                    Toast.makeText(AddStudentActivity.this, "Error: La edad debe tener exactamente 2 cifras", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        btnRegistrar.setOnClickListener(view -> agregarEstudiante());
    }

    private void agregarEstudiante() {
        String nombre = editTextNombre.getText().toString();
        String edadStr = editTextEdad.getText().toString();
        String grupo = editTextGrupo.getText().toString();
        String promedioStr = editTextPromedio.getText().toString();

        // Validar que la edad sea un número de 2 o 3 cifras
        if (edadStr.length() < 2 || edadStr.length() > 3) {
            Toast.makeText(this, "La edad debe tener entre 2 y 3 cifras", Toast.LENGTH_SHORT).show();
            return;
        }

        int edad = Integer.parseInt(edadStr);

        // Validar que el promedio tenga 2 cifras
        if (promedioStr.length() > 2) {
            Toast.makeText(this, "El promedio debe tener como máximo 2 cifras", Toast.LENGTH_SHORT).show();
            return;
        }

        double promedio = Double.parseDouble(promedioStr);
        // Convertir a decimal con dos decimales
        promedio = Math.round(promedio * 100.0) / 100.0;
        promedio = Double.parseDouble(String.format("%.2f", promedio));

        Student student = new Student(null, nombre, edad, grupo, promedio);

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        apiService.registrar(student).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddStudentActivity.this, "Student agregado correctamente", Toast.LENGTH_SHORT).show();
                    finish(); // Regresar a MainActivity
                } else {
                    Toast.makeText(AddStudentActivity.this, "Error al agregar student", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(AddStudentActivity.this, "Student agregado correctamente", Toast.LENGTH_SHORT).show();
                finish(); }
        });
    }}