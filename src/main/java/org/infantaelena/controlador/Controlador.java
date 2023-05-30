package org.infantaelena.controlador;

import org.infantaelena.excepciones.PokemonNotFoundException;
import org.infantaelena.excepciones.PokemonRepeatedException;
import org.infantaelena.modelo.dao.PokemonDAOImp;
import org.infantaelena.modelo.entidades.Pokemon;
import org.infantaelena.modelo.entidades.TipoPokemon;
import org.infantaelena.vista.Vista;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

        // Listener de Mostrar todos Pokémon
        vista.getMostrarTodosLosPokemonButton().addActionListener(e -> {
            mostrarTodos();
        });

        // Listener de Mostrar por tipos
        vista.getBuscarPorTipo().addActionListener(e -> {
            mostrarPorTipo();
        });

    }

    private void mostrarPorTipo() {
        // Capturar los tipos seleccionados
        String tipo1 = vista.getCajaTipo1().getSelectedItem().toString();
        String tipo2 = vista.getCajaTipo2().getSelectedItem().toString();
        if(tipo1.equals(TipoPokemon.NINGUNO.toString())){
            tipo1 = TipoPokemon.NORMAL.toString();
        }
        if(tipo2.equals(tipo1)){
            tipo2 = TipoPokemon.NINGUNO.toString();
        }

        // Llamar al método del controlador para obtener todos los Pokémon por su tipo
        List<Pokemon> listaPokemon = modelo.buscarPorTipo(tipo1,tipo2);
        //Llamar a metodo que confecciona la tabla
        crearTabla(listaPokemon);

    }

    private void mostrarTodos() {
        // Llamar al método del controlador para obtener todos los Pokémon
        List<Pokemon> listaPokemon = modelo.leerTodos();
        //Llamar a metodo que confecciona la tabla
        crearTabla(listaPokemon);
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

    // Listener de Mostrar Todos los Pokémon



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
    private void crearTabla(List<Pokemon> listaPokemon){

        // Crear una nueva ventana para mostrar los resultados
        JFrame ventanaResultados = new JFrame("Lista de Pokémon");
        ventanaResultados.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Definir las columnas de la tabla
        String[] columnas = {"Nombre", "Tipo 1","Tipo 2", "Salud", "Ataque", "Defensa","Ataque Especial","Defensa Especial","Velocidad"};

        // Crear el modelo de tabla
        DefaultTableModel modeloTabla = new DefaultTableModel(columnas, 0);

        int contador = 0;
        // Rellenar el modelo de tabla con los datos de la lista de Pokémon
        for (Pokemon pokemon : listaPokemon) {
            Object[] fila = {
                    pokemon.getNombre(),
                    pokemon.getTipoPrimario(),
                    pokemon.getTipoSecundario(),
                    String.valueOf(pokemon.getPuntosSalud()),
                    String.valueOf(pokemon.getAtaque()),
                    String.valueOf(pokemon.getDefensa()),
                    String.valueOf(pokemon.getAtaqueEspecial()),
                    String.valueOf(pokemon.getDefensaEspecial()),
                    String.valueOf(pokemon.getVelocidad())
            };
            modeloTabla.addRow(fila);
            contador = contador +1;
            modeloTabla.setRowCount(contador);
        }

        // Crear la tabla y asignarle el modelo
        JTable tablaPokemon = new JTable(modeloTabla);

        // Agregar la tabla a un JScrollPane para permitir el desplazamiento si hay muchos Pokémon
        JScrollPane scrollPane = new JScrollPane(tablaPokemon);

        // Agregar el JScrollPane a la ventana
        ventanaResultados.getContentPane().add(scrollPane);

        // Configurar el tamaño y la visibilidad de la ventana
        ventanaResultados.setSize(600, 400);
        ventanaResultados.setVisible(true);

    }
}