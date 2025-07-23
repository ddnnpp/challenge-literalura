package com.aluracursos.challengeliteralura.principal;

import com.aluracursos.challengeliteralura.dto.AutorDTO;
import com.aluracursos.challengeliteralura.dto.DatosDTO;
import com.aluracursos.challengeliteralura.dto.LibroDTO;
import com.aluracursos.challengeliteralura.model.Autor;
import com.aluracursos.challengeliteralura.model.Libro;
import com.aluracursos.challengeliteralura.repository.AutorRepository;
import com.aluracursos.challengeliteralura.repository.LibroRepository;
import com.aluracursos.challengeliteralura.service.ConsumoAPI;
import com.aluracursos.challengeliteralura.service.ConvertirDatos;
import com.aluracursos.challengeliteralura.service.IConvierteDatos;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class Principal {

    private ConsumoAPI api = new ConsumoAPI();
    private IConvierteDatos conversor = new ConvertirDatos();
    private Scanner entrada = new Scanner(System.in);
    private String URL_BASE = "https://gutendex.com/books/?search=";
    private String menu = """
            ----------
            Elija la opción a través de su número:
            1 - Buscar libro por título
            2 - Listar libros registrados
            3 - Listar autores registrados
            4 - Listar autores vivos en un determinado año
            5 - Listar libros por idioma
            0 - Salir
            ----------
            """;
    private AutorRepository autorRepository;
    private LibroRepository libroRepository;

    public Principal(AutorRepository autorRepository, LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    public void mostrarMenu() {
        int opcionUsuario = -666;
        while (opcionUsuario != 0) {
            System.out.println(menu);
            opcionUsuario = entrada.nextInt();
            entrada.nextLine();

            switch (opcionUsuario) {
                case 1:
                    buscarLibroTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4: 
                    listarAutoresVivos();
                    break;
                case 5:
                    listaLibrosPorIdioma();
                    break;
                case 0:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Escoge una opción válida");
            }
        }
    }

    public LibroDTO obtenerDatos(String libroUsuario) {
        String json = api.obtenerDatos(URL_BASE + libroUsuario.replace(" ", "+"));
        System.out.println(json);
        DatosDTO datos = conversor.convierteDatos(json, DatosDTO.class);
        System.out.println(datos);
        Optional<LibroDTO> libroDTO = datos.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(libroUsuario.toUpperCase()))
                .findFirst();
        if (libroDTO.isPresent()) {
            LibroDTO libro = libroDTO.get();
            return libro;
        } else {
            return null;
        }
    }

    public void buscarLibroTitulo() {
        System.out.println("Ingrese el nombre del libro que desea buscar");
        String libroUsuario = entrada.nextLine();
        LibroDTO libroDTO = obtenerDatos(libroUsuario);

        if (libroDTO != null) {
            Libro libro;
            AutorDTO autorDTO = libroDTO.autor().getFirst();
            Autor autorExisteDB = autorRepository.findByNombre(autorDTO.nombre());
            if (autorExisteDB != null) {
                libro = new Libro(libroDTO, autorExisteDB);
            } else {
                Autor autor = new Autor(autorDTO);
                libro = new Libro(libroDTO, autor);
                autorRepository.save(autor);
            }
            try {
                libroRepository.save(libro);
                System.out.println(libro);
            } catch (Exception e) {
                System.out.println("El libro ya existe en la base de datos");
            }
        } else {
            System.out.println("El libro no existe");
        }
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepository.findAll();
        libros.forEach(l -> System.out.println(l));
    }

    private void listarAutoresRegistrados() {
        List<Autor> autores = autorRepository.findAll();
        autores.forEach(a -> System.out.println(a));
    }

    private void listarAutoresVivos() {
        System.out.println("Ingrese el año vivo de autor(es) que desea buscar");
        int respuestaUsuario = entrada.nextInt();
        entrada.nextLine();
        List<Autor> listaAutoresVivos = autorRepository.findByFechaNacimientoLessThanEqualAndFechaMuerteGreaterThanEqual(respuestaUsuario, respuestaUsuario);
        if (listaAutoresVivos.isEmpty()) {
            System.out.println("Ningún autor en la base de datos vivía durante ese año");
        } else {
            listaAutoresVivos.forEach(a -> System.out.println(a));
        }
    }

    private void listaLibrosPorIdioma() {
        System.out.println("""
                Ingresa el idioma para buscar los libros:
                es - español
                en - ingles
                fr - francés
                pt - portugués
                """);
        String idiomaUsuario = entrada.nextLine();
        List<Libro> librosIdioma = libroRepository.findByIdioma(idiomaUsuario);
        if (librosIdioma.isEmpty()) {
            System.out.println("No hay ningún libro con ese idioma");
        } else {
            librosIdioma.forEach(l -> System.out.println(l));
        }

    }
}
