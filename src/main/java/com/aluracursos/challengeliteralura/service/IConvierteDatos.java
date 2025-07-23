package com.aluracursos.challengeliteralura.service;

public interface IConvierteDatos {

    <T> T convierteDatos(String json, Class<T> clase);
}
