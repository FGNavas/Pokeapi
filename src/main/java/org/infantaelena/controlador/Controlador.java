package org.infantaelena.controlador;

import org.infantaelena.excepciones.PokemonNotFoundException;
import org.infantaelena.excepciones.PokemonRepeatedException;
import org.infantaelena.modelo.dao.PokemonDAOImp;
import org.infantaelena.modelo.entidades.Pokemon;
import org.infantaelena.modelo.entidades.TipoPokemon;
import org.infantaelena.vista.Vista;

import java.util.List;

/**
 * Clase que se encarga de obtener los datos de la vista
 * y enviarlos al modelo para que los procese
 *
 * @author Fernando
 * @author Pablo
 * @version 2.0
 * @date 30/05/2023
 */

/**
 * Constructor de la clase Controlador.
 */
public class Controlador {

    private Vista vista;
    private PokemonDAOImp modelo;

    public Controlador() {
        vista = new Vista();
        modelo = new PokemonDAOImp();
        String[] tiposPokemon = modelo.obtenerTiposPokemon();
        vista.inicializarComboBoxTiposPokemon(tiposPokemon);

        // Listener de Consultar Pokémon
        vista.getConsultarPokemonButton().addActionListener(e -> {
            consultarPokemon();

        });

        // Listener de Agregar Pokémon
        vista.getAgregarPokemonButton().addActionListener(e -> {
            agregarPokemon();
        });

        // Listener de Borrar Pokémon
        vista.getBorrarPokemonButton().addActionListener(e -> {
            borrarPokemon();
        });


        // Listener de Actualizar Pokémon
        vista.getActualizarPokemonButton().addActionListener(e -> {
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

    /**
     * Método que muestra la ventana de búsqueda por tipo.
     */
    private void mostrarPorTipo() {

        // Capturar los tipos seleccionados
        String tipo1 = vista.getCajaTipo1().getSelectedItem().toString();
        String tipo2 = vista.getCajaTipo2().getSelectedItem().toString();

        // Llamar al método del controlador para obtener todos los Pokémon por su tipo
        List<Pokemon> listaPokemon = modelo.buscarPorTipo(tipo1, tipo2);
        //Llamar a metodo que confecciona la tabla
        vista.crearTabla(listaPokemon);

    }

    /**
     * Método que muestra todos los Pokémon.
     */
    private void mostrarTodos() {
        // Llamar al método del controlador para obtener todos los Pokémon
        List<Pokemon> listaPokemon = modelo.leerTodos();
        //Llamar a metodo que confecciona la tabla
        vista.crearTabla(listaPokemon);
    }

    /**
     * Método que consulta un Pokémon por su nombre.
     */
    private void consultarPokemon() {
        // Pedirle al usuario que escriba el nombre del Pokémon a buscar
        Pokemon pokemonConsulta = null;
        String nombrePokemon = vista.getCajaNombre().getText();

        // Buscar el pokemon en la pokedex y mostrarlo por pantalla
        try {
            pokemonConsulta = modelo.leerPorNombre(nombrePokemon);
            mostrarEnPantalla(pokemonConsulta);
        } catch (PokemonNotFoundException e) {
            vista.mensajes(nombrePokemon, 1);
        }
    }

    /**
     * Método que agrega un nuevo Pokémon.
     */
    private void agregarPokemon() {
        // Capturar parametros de la pantalla
        Pokemon pokemonCrear = capturarDePantalla();
        //Agregar pokemon en la pokedex
        try {
            modelo.crear(pokemonCrear);
            vista.mensajes("Pokemon " + vista.getCajaNombre().getText() + " agregado a la pokedex", 3);
        } catch (PokemonRepeatedException e) {
            vista.mensajes(pokemonCrear.getNombre(), 2);
        }

    }

    /**
     * Método que elimina un Pokémon.
     */
    private void borrarPokemon() {
        //Recoger el nombre de la caja nombre
        String nombrePokemon = vista.getCajaNombre().getText();

        //Eliminar pokemon de la pokedex
        try {
            modelo.eliminarPorNombre(nombrePokemon);
            vista.mensajes("Pokemon " + vista.getCajaNombre().getText() + " eliminado de la pokedex", 3);
        } catch (PokemonNotFoundException e) {
            vista.mensajes(nombrePokemon, 1);
        }
    }

    /**
     * Método que actualiza un Pokémon.
     */
    private void actualizarPokemon() {
        //Capturar parametros de la pantalla
        Pokemon pokemonActualizado = capturarDePantalla();
        if (pokemonActualizado != null) {
            try {
                modelo.actualizar(pokemonActualizado);
                vista.mensajes("Pokemon " + vista.getCajaNombre().getText() + " actualizado en pokedex", 3);
            } catch (PokemonNotFoundException e) {
                vista.mensajes(pokemonActualizado.getNombre(), 1);
            }
        }
    }


/**
 * FUNCIONES AUXILIARES
 */

    /**
     * Método que muestra los datos de un Pokémon en la pantalla.
     *
     * @param pokemon El Pokémon a mostrar.
     */

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

    /**
     * Método que captura los datos de la pantalla y crea un objeto Pokemon.
     *
     * @return El objeto Pokemon creado, o null si hay errores en los datos.
     */
    private Pokemon capturarDePantalla() {
        Pokemon pokemonVacio = null;
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

        if (nombre.isEmpty()) {
            vista.mensajes("El nombre no puede estar vacío", 3);
            todosCorrectos = false;
        }

        if (tipo2.equals(tipo1)) {
            tipo2 = TipoPokemon.NINGUNO.toString();
        }
        try {
            puntosSalud = Integer.parseInt(salud);
        } catch (NumberFormatException ex) {
            vista.mensajes("Los puntos de salud deben ser un número entero", 3);
            todosCorrectos = false;
        }

        try {
            ataqueValor = Integer.parseInt(ataque);
        } catch (NumberFormatException ex) {
            vista.mensajes("El valor de ataque debe ser un número entero", 3);
            todosCorrectos = false;
        }

        try {
            defensaValor = Integer.parseInt(defensa);
        } catch (NumberFormatException ex) {
            vista.mensajes("El valor de defensa debe ser un número entero", 3);
            todosCorrectos = false;
        }

        try {
            ataqueEspecial = Integer.parseInt(atEsp);
        } catch (NumberFormatException ex) {
            vista.mensajes("El valor de ataque especial debe ser un número entero", 3);
            todosCorrectos = false;
        }

        try {
            defensaEspecial = Integer.parseInt(defEsp);
        } catch (NumberFormatException ex) {
            vista.mensajes("El valor de defensa especial debe ser un número entero", 3);
            todosCorrectos = false;
        }

        try {
            velocidadValor = Integer.parseInt(velocidad);
        } catch (NumberFormatException ex) {
            vista.mensajes("El valor de velocidad debe ser un número entero", 3);
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