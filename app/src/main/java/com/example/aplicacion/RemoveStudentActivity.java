package com.example.aplicacion;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoveStudentActivity extends AppCompatActivity {

    private int estudianteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        estudianteId = getIntent().getIntExtra("estudianteId", -1);

        if (estudianteId != -1) {
            eliminarEstudiante(estudianteId);
        } else {
            Toast.makeText(this, "Error al obtener el ID del estudiante", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    private void eliminarEstudiante(int id) {
        ApiService apiService = ApiClient.getRetrofitInstance().create(ApiService.class);
        Call<Void> call = apiService.eliminar(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RemoveStudentActivity.this, "Student eliminado", Toast.LENGTH_SHORT).show();
                    redirigirAMainActivity();
                } else {
                    Toast.makeText(RemoveStudentActivity.this, "Error al eliminar estudiante", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RemoveStudentActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void redirigirAMainActivity() {
        Intent intent = new Intent(RemoveStudentActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish(); // Cierra la actividad actual para evitar que el usuario regrese aquí
    }
}


