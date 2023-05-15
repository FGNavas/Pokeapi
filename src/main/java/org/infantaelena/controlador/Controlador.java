package org.infantaelena.controlador;

import org.infantaelena.excepciones.PokemonNotFoundException;
import org.infantaelena.excepciones.PokemonRepeatedException;
import org.infantaelena.modelo.dao.PokemonDAOImp;
import org.infantaelena.modelo.entidades.Pokemon;
import org.infantaelena.vista.Vista;

/**
 * Clase que se encarga de obtener los datos de la vista
 * y enviarlos al modelo para que los procese
 *
 * @author Fernando
 * @author Pablo
 * @version 1.1
 * @date 13/05/2023
 */


public class Controlador {

    private Vista vista;
    private PokemonDAOImp modelo;
    private final String POKEMON_STATUS = "El pokemon ";
    private final String NOT_FOUND = " no se encuentra en la pokedex";
    private final String REPEATED = " esta repetido";
    private final String CREATED = " ha sido introducido ";
    private final String DELETED = " ha sido borrado";

    public Controlador() {
        vista = new Vista();
        modelo = new PokemonDAOImp();

    }

    public void intrucciones(int caso, Pokemon pokemon) throws PokemonNotFoundException {

        switch (caso) {
            case 1:
                try {
                    modelo.crear(pokemon);
                    vista.mostrarMensaje(POKEMON_STATUS + pokemon.getNombre() + CREATED;
                } catch (PokemonRepeatedException e) {
                    vista.mostrarMensaje(POKEMON_STATUS + pokemon.getNombre() + REPEATED);
                }
                break;
            case 2:
                try {
                    Pokemon pokeBuscado = modelo.leerPorNombre(pokemon.getNombre());
                    vista.mostrarPokemon(pokeBuscado);
                } catch (PokemonNotFoundException e) {
                    vista.mostrarMensaje(POKEMON_STATUS + pokemon.getNombre() + NOT_FOUND);
                }
                break;
            case 3:
                vista.mostrarTodos(modelo.leerTodos());
                break;
            case 4:
                try {
                    modelo.actualizar(pokemon);
                } catch (PokemonNotFoundException e) {
                    vista.mostrarMensaje(POKEMON_STATUS + pokemon.getNombre() + NOT_FOUND);
                }
                break;
            case 5:
                try {
                    modelo.eliminarPorNombre(pokemon.getNombre());
                } catch (PokemonNotFoundException) {
                    vista.mostrarMensaje(POKEMON_STATUS + pokemon.getNombre() + NOT_FOUND);
                }
                break;

        }
    }
}