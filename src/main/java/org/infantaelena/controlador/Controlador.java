package org.infantaelena.controlador;

import org.infantaelena.excepciones.PokemonNotFoundException;
import org.infantaelena.excepciones.PokemonRepeatedException;
import org.infantaelena.modelo.dao.PokemonDAOImp;
import org.infantaelena.modelo.entidades.Pokemon;
import org.infantaelena.modelo.entidades.TipoPokemon;
import org.infantaelena.vista.Vista;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

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

    public Controlador() {
        vista = new Vista();
        modelo = new PokemonDAOImp();
        String[] tiposPokemon = modelo.obtenerTiposPokemon();
        vista.inicializarComboBoxTiposPokemon(tiposPokemon);

        vista.getConsultarPokémonButton().addActionListener(e -> {
            consultarPokemon();

        });

        // Listener de Agregar Pokémon
        vista.getAgregarPokémonButton().addActionListener(e -> {
            agregarPokemon();
        });

        // Listener de Borrar Pokémon
        vista.getBorrarPokémonButton().addActionListener(e -> {
            borrarPokemon();
        });


        // Listener de Actualizar Pokémon
        vista.getActualizarPokémonButton().addActionListener(e -> {
            actualizarPokemon();
        });
    }


    private void consultarPokemon() {
        // Pedirle al usuario que escriba el nombre del Pokémon a buscar
        Pokemon pokemonConsulta = null;

        String nombrePokemon = vista.getCajaNombre().getText();

        try {
            pokemonConsulta = modelo.leerPorNombre(nombrePokemon);
            mostrarEnPantalla(pokemonConsulta);
        } catch (PokemonNotFoundException e) {
            vista.mensajes( nombrePokemon, 1);
        }


    }

    private void agregarPokemon() {
        Pokemon pokemonCrear = capturarDePantalla();
        try {
            modelo.crear(pokemonCrear);
            vista.mensajes("Pokemon "+ vista.getCajaNombre().getText() +" agregado a la pokedex",3);
        } catch (PokemonRepeatedException e) {
            vista.mensajes(pokemonCrear.getNombre(), 2);
        }

    }

    private void borrarPokemon() {
        String nombrePokemon = vista.getCajaNombre().getText();

        try {
            modelo.eliminarPorNombre(nombrePokemon);
            vista.mensajes("Pokemon "+vista.getCajaNombre().getText() +" eliminado de la pokedex",3);
        } catch (PokemonNotFoundException e) {
            vista.mensajes(nombrePokemon, 1);
        }
    }

    private void actualizarPokemon() {
        Pokemon pokemonActualizado = capturarDePantalla();
        if(pokemonActualizado != null) {
            try {
                modelo.actualizar(pokemonActualizado);
                vista.mensajes("Pokemon "+vista.getCajaNombre().getText() +" actualizado en pokedex",3);
            } catch (PokemonNotFoundException e) {
                vista.mensajes(pokemonActualizado.getNombre(), 1);
            }
        }
    }

    // FUNCIONES AUXILIARES


    private void mostrarEnPantalla(Pokemon pokemon) {
        vista.getCajaNombre().setText(pokemon.getNombre());
        vista.getCajaTipo1().setSelectedItem(pokemon.getTipoPrimario());
        vista.getCajaTipo2().setSelectedItem(pokemon.getTipoSecundario());
        vista.getCajaSalud().setText(String.valueOf(pokemon.getPuntosSalud()));
        vista.getCajaAtaque().setText(String.valueOf(pokemon.getAtaque()));
        vista.getCajaDefensa().setText(String.valueOf(pokemon.getDefensa()));
        vista.getCajaAtEsp().setText(String.valueOf(pokemon.getAtaqueEspecial()));
        vista.getCajaDefEsp().setText(String.valueOf(pokemon.getDefensaEspecial()));
        vista.getCajaVelocidad().setText(String.valueOf(pokemon.getVelocidad()));

    }

    private Pokemon capturarDePantalla() {
        Pokemon pokemonVacio= null;
        boolean todosCorrectos = true;

        // Obtener los valores de las cajas de texto
        String nombre = vista.getCajaNombre().getText();
        String tipo1 = vista.getCajaTipo1().getSelectedItem().toString();
        String tipo2 = vista.getCajaTipo2().getSelectedItem().toString();
        String salud = vista.getCajaSalud().getText();
        String ataque = vista.getCajaAtaque().getText();
        String defensa = vista.getCajaDefensa().getText();
        String atEsp = vista.getCajaAtEsp().getText();
        String defEsp = vista.getCajaDefEsp().getText();
        String velocidad = vista.getCajaVelocidad().getText();

        // Validar los valores

        int puntosSalud = 0;
        int ataqueValor = 0;
        int defensaValor = 0;
        int ataqueEspecial = 0;
        int defensaEspecial = 0;
        int velocidadValor = 0;
        if(tipo1.equals(TipoPokemon.NINGUNO.toString())){
            tipo1= TipoPokemon.NORMAL.toString();
        }
        if(nombre.isEmpty()){
            vista.mensajes("El nombre no puede estar vacío", 3);
            todosCorrectos= false;
        }

        if(tipo2.equals(tipo1)){
            tipo2= TipoPokemon.NINGUNO.toString();
        }
        try {
            puntosSalud = Integer.parseInt(salud);
        } catch (NumberFormatException ex) {
            vista.mensajes("Los puntos de salud deben ser un número entero",3);
            todosCorrectos = false;
        }

        try {
            ataqueValor = Integer.parseInt(ataque);
        } catch (NumberFormatException ex) {
            vista.mensajes("El valor de ataque debe ser un número entero",3);
            todosCorrectos = false;
        }

        try {
            defensaValor = Integer.parseInt(defensa);
        } catch (NumberFormatException ex) {
            vista.mensajes("El valor de defensa debe ser un número entero",3);
            todosCorrectos = false;
        }

        try {
            ataqueEspecial = Integer.parseInt(atEsp);
        } catch (NumberFormatException ex) {
            vista.mensajes("El valor de ataque especial debe ser un número entero",3);
            todosCorrectos = false;
        }

        try {
            defensaEspecial = Integer.parseInt(defEsp);
        } catch (NumberFormatException ex) {
            vista.mensajes("El valor de defensa especial debe ser un número entero",3);
            todosCorrectos = false;
        }

        try {
            velocidadValor = Integer.parseInt(velocidad);
        } catch (NumberFormatException ex) {
            vista.mensajes("El valor de velocidad debe ser un número entero",3);
            todosCorrectos = false;
        }

        // Actualizar el objeto Pokemon
        if (todosCorrectos) {
             pokemonVacio = new Pokemon(nombre, tipo1, tipo2, puntosSalud, ataqueValor, defensaValor,
                    ataqueEspecial, defensaEspecial, velocidadValor);
        }


        return pokemonVacio;

    }
}