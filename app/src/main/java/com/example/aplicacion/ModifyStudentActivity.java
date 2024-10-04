package com.example.aplicacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyStudentActivity extends AppCompatActivity {

    private EditText editTextNombre;
    private EditText editTextEdad;
    private EditText editTextGrupo;
    private EditText editTextPromedio;
    private Button buttonGuardar;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_student);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextEdad = findViewById(R.id.editTextEdad);
        editTextGrupo = findViewById(R.id.editTextGrupo);
        editTextPromedio = findViewById(R.id.editTextPromedio);
        buttonGuardar = findViewById(R.id.buttonGuardar);

        student = (Student) getIntent().getSerializableExtra("student");

        if (student != null) {
            editTextNombre.setText(student.getNombre());
            editTextEdad.setText(String.valueOf(student.getEdad()));
            editTextGrupo.setText(student.getGrupo());
            editTextPromedio.setText(String.valueOf(student.getPromedioGeneral()));
        }

        buttonGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCambios();
            }
        });
    }

    private void guardarCambios() {
        String nombre = editTextNombre.getText().toString().trim();
        String grupo = editTextGrupo.getText().toString().trim();
        String promedioStr = editTextPromedio.getText().toString().trim();

        if (nombre.isEmpty() || grupo.isEmpty() || promedioStr.isEmpty()) {
            Toast.makeText(ModifyStudentActivity.this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        int edad = Integer.parseInt(editTextEdad.getText().toString().trim());
        double promedio = Double.parseDouble(promedioStr);

        student.setNombre(nombre);
        student.setEdad(edad);
        student.setGrupo(grupo);
        student.setPromedioGeneral(promedio);

        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<Void> call = apiService.modificar(student.getId(), student);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ModifyStudentActivity.this, "Student modificado correctamente", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ModifyStudentActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(ModifyStudentActivity.this, "Error al modificar student", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ModifyStudentActivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
