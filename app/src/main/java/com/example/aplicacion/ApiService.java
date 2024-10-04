package com.example.aplicacion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @GET("estudiante")
    Call<List<Student>> obtenerTodos();

    @POST("student")
    Call<String> registrar(@Body Student student);

    @PUT("student/{id}")
    Call<Void> modificar(@Path("id") Integer id, @Body Student student);

    @DELETE("estudiante/{id}")
    Call<Void> eliminar(@Path("id") Integer id);
}

