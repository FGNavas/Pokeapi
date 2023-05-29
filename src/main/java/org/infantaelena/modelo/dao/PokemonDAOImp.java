package org.infantaelena.modelo.dao;

import org.infantaelena.excepciones.PokemonNotFoundException;
import org.infantaelena.excepciones.PokemonRepeatedException;
import org.infantaelena.modelo.entidades.Pokemon;
import org.infantaelena.modelo.entidades.TipoPokemon;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase que implementa los métodos de acceso a datos de la clase Pokemon
 * Esta puede hacerse mediante un fichero CSV separado por puntos y coma o una base de datos
 *
 * @author Fernando
 * @author Pablo
 * @version 2.0
 * @since 13/05/2023
 */
public class PokemonDAOImp implements PokemonDAO {
    private final String RUTA ="jdbc:sqlite:src\\main\\resources\\";
    private final String DEFAULT_DB = "pokedex.db";
    private final String POKEMON_TABLE = "CREATE TABLE IF NOT EXISTS pokemon (\n"
            + " nombre VARCHAR(50) NOT NULL UNIQUE,\n"
            + " tipoPrimario VARCHAR(15),\n"
            + " tipoSecundario VARCHAR(15),\n"
            + " puntosSalud INT,\n"
            + " ataque INT,\n"
            + " defensa INT,\n"
            + " ataqueEspecial INT,\n"
            + " defensaEspecial INT,\n"
            + " velocidad INT\n"
            + ");\n";

    private final String POKEMON_UPDATE ="UPDATE pokemon SET nombre = ?, tipoPrimario = ?, tipoSecundario = ?, puntosSalud = ?," +
            " ataque = ?, defensa = ?, ataqueEspecial = ?, defensaEspecial = ?, velocidad = ? ";
    private final String POKEMON_INSERT= "INSERT INTO pokemon (nombre, tipoPrimario, tipoSecundario, puntosSalud," +
            " ataque, defensa, ataqueEspecial, defensaEspecial, velocidad) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";


    private final String POKEMON_SEARCH ="SELECT nombre, tipoPrimario, tipoSecundario, puntosSalud," +
            "ataque, defensa, ataqueEspecial, defensaEspecial, velocidad FROM pokemon ";
    private final String POKEMON_DELETE = "DELETE FROM pokemon ";
    private final String BY_NAME = " WHERE nombre LIKE '%";
    private final String BY_TYPE_1 = " WHERE tipoPrimario = ";
    private final
    private final String END_SQL= "%';";
    private final String END_SQL_2 = ";";

    private Connection connection;
    private Statement statement;

    public TipoPokemon[] tiposPokemon = TipoPokemon.values();


    /**
     * Crea un nuevo objeto PokemonDAOImp utilizando la base de datos por defecto.
     */
    public PokemonDAOImp (){

        try {
            connection = DriverManager.getConnection(RUTA+DEFAULT_DB);
            statement = connection.createStatement();


            String createTableSQL = POKEMON_TABLE;
            statement.execute(createTableSQL);

        } catch (SQLException e) {

        }
    }

    /**
     * Crea un nuevo objeto PokemonDAOImp utilizando la base de datos especificada.
     *
     * @param db el nombre de la base de datos
     */
    public  PokemonDAOImp (String db){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:resources/"+db);
            String createTableSQL = POKEMON_TABLE;
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Crea un nuevo registro de Pokemon en la base de datos.
     *
     * @param pokemon el Pokemon a crear
     * @throws PokemonRepeatedException si se intenta crear un Pokemon con un nombre repetido
     */
    @Override
    public void crear(Pokemon pokemon) throws PokemonRepeatedException {
        Pokemon encontrado = null;
        try {
            encontrado = leerPorNombre(pokemon.getNombre());
            throw new PokemonRepeatedException();
        } catch (PokemonNotFoundException e) {
            trabajoSQL(POKEMON_INSERT, pokemon);
        }

    }

    /**
     * Lee un Pokemon de la base de datos por su nombre.
     *
     * @param nombre el nombre del Pokemon a buscar
     * @return el Pokemon encontrado
     * @throws PokemonNotFoundException si no se encuentra un Pokemon con el nombre especificado
     */
    @Override
    public Pokemon leerPorNombre(String nombre) throws PokemonNotFoundException {
        Pokemon pokemonBuscado = null;
        String consulta = POKEMON_SEARCH + BY_NAME + nombre + END_SQL;
        System.out.println(consulta);
        try (ResultSet buscar = statement.executeQuery(consulta)) {
            if (!buscar.next()) {
                throw new PokemonNotFoundException();
            } else {
                pokemonBuscado = leerPokemon(buscar);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return pokemonBuscado;
    }

    /**
     * Lee todos los Pokemon de la base de datos.
     *
     * @return una lista de Pokemon
     */
    @Override
    public List<Pokemon> leerTodos() {
        List<Pokemon> listaPokemon = new ArrayList<>();
        Pokemon pokemon = null;
        try (ResultSet allPokedex = statement.executeQuery(POKEMON_SEARCH + END_SQL_2)) {
            while (allPokedex.next()) {
                String nombre = allPokedex.getString("nombre");
                String tipPri = allPokedex.getString("tipoPrimario");
                String tipSec = allPokedex.getString("tipoSecundario");
                int puntosSalud = allPokedex.getInt("puntosSalud");
                int ataque = allPokedex.getInt("ataque");
                int defensa = allPokedex.getInt("defensa");
                int ataqueEspecial = allPokedex.getInt("ataqueEspecial");
                int defensaEspecial = allPokedex.getInt("defensaEspecial");
                int velocidad = allPokedex.getInt("velocidad");
                pokemon = new Pokemon(nombre, tipPri, tipSec,
                        puntosSalud, ataque, defensa, ataqueEspecial, defensaEspecial, velocidad);
                System.out.println(pokemon);
                listaPokemon.add(pokemon);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaPokemon;
    }

    /**
     * Actualiza un Pokemon existente en la base de datos.
     *
     * @param pokemon el Pokemon a actualizar
     * @throws PokemonNotFoundException si no se encuentra un Pokemon con el nombre especificado
     */
    @Override
    public void actualizar(Pokemon pokemon) throws PokemonNotFoundException {
        String nombreAbuscar = pokemon.getNombre();

        try (ResultSet pokemonEnPokedex = statement.executeQuery(POKEMON_SEARCH + BY_NAME + nombreAbuscar + END_SQL)) {
            if (!pokemonEnPokedex.next()) {
                throw new PokemonNotFoundException();
            } else {
                trabajoSQL(POKEMON_UPDATE+BY_NAME+nombreAbuscar+END_SQL, pokemon);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Elimina un Pokemon de la base de datos por su nombre.
     *
     * @param nombre el nombre del Pokemon a eliminar
     * @throws PokemonNotFoundException si no se encuentra un Pokemon con el nombre especificado
     */
    @Override
    public void eliminarPorNombre(String nombre) throws PokemonNotFoundException {
        Pokemon pokemonABorrar = leerPorNombre(nombre);

        if (pokemonABorrar == null) {
            throw new PokemonNotFoundException();
        } else {
            try (Statement statement = connection.createStatement()) {
                String delete = POKEMON_DELETE + BY_NAME + nombre + END_SQL;
                statement.executeUpdate(delete);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }


    // FUNCIONES AUXILIARES
    /**
     * Lee los datos de un ResultSet y crea un objeto Pokemon.
     *
     * @param resultBusqueda el ResultSet que contiene los datos del Pokemon
     * @return el Pokemon creado
     */
    private Pokemon leerPokemon(ResultSet resultBusqueda) throws SQLException {
        Pokemon pokemonBuscado = null;
        do {
            String nombre = resultBusqueda.getString("nombre");
            String tipPri = resultBusqueda.getString("tipoPrimario");
            String tipSec = resultBusqueda.getString("tipoSecundario");
            int puntosSalud = resultBusqueda.getInt("puntosSalud");
            int ataque = resultBusqueda.getInt("ataque");
            int defensa = resultBusqueda.getInt("defensa");
            int ataqueEspecial = resultBusqueda.getInt("ataqueEspecial");
            int defensaEspecial = resultBusqueda.getInt("defensaEspecial");
            int velocidad = resultBusqueda.getInt("velocidad");
            Pokemon pokemon = new Pokemon(nombre, tipPri, tipSec,
                    puntosSalud, ataque, defensa, ataqueEspecial, defensaEspecial, velocidad);
            System.out.println(pokemon);
            pokemonBuscado = pokemon;
        } while (resultBusqueda.next());
        return pokemonBuscado;
    }
    /**
     * Ejecuta una consulta SQL para realizar operaciones en la base de datos.
     *
     * @param sqlQuery la consulta SQL a ejecutar
     * @param pokemon el Pokemon utilizado para completar los parámetros de la consulta
     */

    public void trabajoSQL(String sqlQuery, Pokemon pokemon) {
        try (PreparedStatement updateStatement = connection.prepareStatement(sqlQuery)) {
            updateStatement.setString(1, pokemon.getNombre());
            //
            updateStatement.setString(2, pokemon.getTipoPrimario().toString());
            updateStatement.setString(3, String.valueOf(pokemon.getTipoSecundario()));
            //
            updateStatement.setInt(4, pokemon.getPuntosSalud());
            updateStatement.setInt(5, pokemon.getAtaque());
            updateStatement.setInt(6, pokemon.getDefensa());
            updateStatement.setInt(7, pokemon.getAtaqueEspecial());
            updateStatement.setInt(8, pokemon.getDefensaEspecial());
            updateStatement.setInt(9, pokemon.getVelocidad());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    public String[] obtenerTiposPokemon() {
        TipoPokemon[] tiposPokemon = TipoPokemon.values();
        String[] tiposPokemonArray = new String[tiposPokemon.length];

        for (int i = 0; i < tiposPokemon.length; i++) {
            tiposPokemonArray[i] = tiposPokemon[i].toString();
        }

        return tiposPokemonArray;
    }
    public List<Pokemon> buscarPorTipo(String tipoAbuscar) {
        List<Pokemon> listaPokemon = new ArrayList<>();
        Pokemon pokemon = null;
        try (ResultSet allPokedex = statement.executeQuery(POKEMON_SEARCH + END_SQL_2)) {
            while (allPokedex.next()) {
                String nombre = allPokedex.getString("nombre");
                String tipPri = allPokedex.getString("tipoPrimario");
                String tipSec = allPokedex.getString("tipoSecundario");
                int puntosSalud = allPokedex.getInt("puntosSalud");
                int ataque = allPokedex.getInt("ataque");
                int defensa = allPokedex.getInt("defensa");
                int ataqueEspecial = allPokedex.getInt("ataqueEspecial");
                int defensaEspecial = allPokedex.getInt("defensaEspecial");
                int velocidad = allPokedex.getInt("velocidad");
                pokemon = new Pokemon(nombre, tipPri, tipSec,
                        puntosSalud, ataque, defensa, ataqueEspecial, defensaEspecial, velocidad);
                System.out.println(pokemon);
                listaPokemon.add(pokemon);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaPokemon;
    }
}
