package com.aluracursos.challengeliteralura.model;

import com.aluracursos.challengeliteralura.dto.AutorDTO;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autor")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nombre;
    private int fechaNacimiento;
    private int fechaMuerte;
    @OneToMany(mappedBy ="autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros = new ArrayList<>();

    public Autor() {
    }

    public Autor(AutorDTO autor) {
        this.nombre = autor.nombre();
        this.fechaNacimiento = autor.fechaNacimiento();
        this.fechaMuerte = autor.fechaMuerte();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(int fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public int getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(int fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    @Override
    public String toString() {
        String nombresLibros = libros.stream()
                .map(Libro::getTitulo)
                .collect(Collectors.joining("\n - ", "\n - ", "\n"));
        return
                "Autor: " + nombre + "\n" +
                "Fecha de nacimiento: " + fechaNacimiento + "\n" +
                "Fecha de fallecimiento: " + fechaMuerte + "\n" +
                "Libros: " + nombresLibros;
    }
}


