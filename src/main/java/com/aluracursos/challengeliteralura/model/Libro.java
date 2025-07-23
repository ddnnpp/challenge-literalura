package com.aluracursos.challengeliteralura.model;

import com.aluracursos.challengeliteralura.dto.AutorDTO;
import com.aluracursos.challengeliteralura.dto.LibroDTO;
import jakarta.persistence.*;

import static jakarta.persistence.FetchType.EAGER;

@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    @ManyToOne(fetch=EAGER)
    @JoinColumn(name="autor_id")
    private Autor autor;
    private String idioma;
    private int numeroDescargas;

    public Libro() {
    }

    public Libro(LibroDTO libro, Autor autor) {
        this.titulo = libro.titulo();
        this.autor = autor;
        this.idioma = libro.idiomas().getFirst();
        this.numeroDescargas = libro.numeroDescargas();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String lenguaje) {
        this.idioma = lenguaje;
    }

    public int getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(int numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    @Override
    public String toString() {
        return "---- LIBRO ----" + "\n" +
                "Título: " + titulo + "\n" +
                "Autor: " + autor.getNombre() + "\n" +
                "Idioma: " + idioma + "\n" +
                "Numero de dscargas: " + numeroDescargas + "\n" +
                "--------------" + "\n";
    }
}
