package com.example.aplicacion;
import java.io.Serializable;
public class Student implements Serializable  {

    private Integer id;
    private String nombre;
    private int edad;
    private String grupo;
    private double promedioGeneral;

    public Student(Integer id, String nombre, int edad, String grupo, double promedioGeneral) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.grupo = grupo;
        this.promedioGeneral = promedioGeneral;
    }

    public Student(Integer id) {
        this.id = id;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getGrupo() { return grupo; }
    public void setGrupo(String grupo) { this.grupo = grupo; }

    public double getPromedioGeneral() { return promedioGeneral; }
    public void setPromedioGeneral(double promedioGeneral) { this.promedioGeneral = promedioGeneral; }

    public String toString() {
        return nombre; // Retorna el nombre del estudiante para mostrar en la lista
    }
}
