package com.aluracursos.challengeliteralura.repository;

import com.aluracursos.challengeliteralura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Autor findByNombre(String nombre);

    List<Autor> findByFechaNacimientoLessThanEqualAndFechaMuerteGreaterThanEqual(int numero1, int numero2);
}
