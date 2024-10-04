package com.example.aplicacion;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class StudentDetailActivity extends AppCompatActivity {

    private TextView textViewNombre;
    private TextView textViewEdad;
    private TextView textViewGrupo;
    private TextView textViewPromedio;
    private Button buttonModificar;
    private Button buttonEliminar;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        textViewNombre = findViewById(R.id.textViewNombre);
        textViewEdad = findViewById(R.id.textViewEdad);
        textViewGrupo = findViewById(R.id.textViewGrupo);
        textViewPromedio = findViewById(R.id.textViewPromedio);
        buttonModificar = findViewById(R.id.buttonModificar);
        buttonEliminar = findViewById(R.id.buttonEliminar);

        student = (Student) getIntent().getSerializableExtra("student");

        if (student != null) {
            textViewNombre.setText(student.getNombre());
            textViewEdad.setText(String.valueOf(student.getEdad()));
            textViewGrupo.setText(student.getGrupo());
            textViewPromedio.setText(String.valueOf(student.getPromedioGeneral()));
        }

        buttonEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDialogoConfirmacion();
            }
        });

        buttonModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StudentDetailActivity.this, ModifyStudentActivity.class);
                intent.putExtra("student", student);
                startActivity(intent);
            }
        });
    }

    private void mostrarDialogoConfirmacion() {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar eliminación")
                .setMessage("¿Estás seguro de que deseas eliminar este student?")
                .setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(StudentDetailActivity.this, RemoveStudentActivity.class);
                        intent.putExtra("estudianteId", student.getId());
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
