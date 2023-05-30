package org.infantaelena.modelo.dao;

import org.infantaelena.excepciones.PokemonNotFoundException;
import org.infantaelena.excepciones.PokemonRepeatedException;
import org.infantaelena.modelo.entidades.Pokemon;
import org.infantaelena.modelo.entidades.TipoPokemon;

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
 * @since 30/05/2023
 */
public class PokemonDAOImp implements PokemonDAO {
    // Ruta de la base de datos SQLite
    private final String RUTA = "jdbc:sqlite:src\\main\\resources\\";
    // Base de datos
    private final String DEFAULT_DB = "pokedex.db";
    // Sentencia de creacion de la tabla
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

    // Sentencia de actualizacion de pokemon
    private final String POKEMON_UPDATE = "UPDATE pokemon SET nombre = ?, tipoPrimario = ?, tipoSecundario = ?, puntosSalud = ?," +
            " ataque = ?, defensa = ?, ataqueEspecial = ?, defensaEspecial = ?, velocidad = ? ";
    // Sentencia para insertar nuevos pokemon
    private final String POKEMON_INSERT = "INSERT INTO pokemon (nombre, tipoPrimario, tipoSecundario, puntosSalud," +
            " ataque, defensa, ataqueEspecial, defensaEspecial, velocidad) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

    //Sentencia para borrar de la tabla
    private final String POKEMON_SEARCH = "SELECT nombre, tipoPrimario, tipoSecundario, puntosSalud," +
            "ataque, defensa, ataqueEspecial, defensaEspecial, velocidad FROM pokemon ";
    private final String POKEMON_DELETE = "DELETE FROM pokemon ";

    // Componentes de las sentencias
    private final String BY_NAME = " WHERE nombre LIKE '%";
    private final String WHERE = "WHERE ";
    private final String BY_TYPE_1 = " tipoPrimario = '";

    private final String BY_TYPE_2 = " tipoSecundario = '";

    private final String END_TYPE = "';";
    private final String END_SQL = "%';";
    private final String END_SQL_2 = ";";

    private Connection connection;
    private Statement statement;

    // Tipos de Pokemon
    public TipoPokemon[] tiposPokemon = TipoPokemon.values();


    /**
     * Crea un nuevo objeto PokemonDAOImp utilizando la base de datos por defecto.
     */
    public PokemonDAOImp() {

        try {
            connection = DriverManager.getConnection(RUTA + DEFAULT_DB);
            statement = connection.createStatement();

            // Crear la tabla si no existe
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
    public PokemonDAOImp(String db) {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:resources/" + db);
            statement.execute(POKEMON_TABLE);
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
        List<Pokemon> lista1 = new ArrayList<>();
        String consulta = POKEMON_SEARCH + BY_NAME + nombre + END_SQL;
        try (ResultSet buscar = statement.executeQuery(consulta)) {
            lista1 = leerPokemon(buscar);
            if (lista1.isEmpty()) {
                throw new PokemonNotFoundException();
            } else {
                pokemonBuscado = lista1.get(0);
            }
            if (pokemonBuscado.getNombre() == null) {
                throw new PokemonNotFoundException();
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
            listaPokemon = leerPokemon(allPokedex);
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
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
                trabajoSQL(POKEMON_UPDATE + BY_NAME + nombreAbuscar + END_SQL, pokemon);
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


/**
 * Funciones auxiliares
 */

    /**
     * Lee los datos de un ResultSet y crea un objeto Pokemon.
     *
     * @param resultBusqueda el ResultSet que contiene los datos del Pokemon
     * @return el Pokemon creado
     */
    private List<Pokemon> leerPokemon(ResultSet resultBusqueda) throws SQLException {
        List<Pokemon> listaBuqueda = new ArrayList<>();
        while (resultBusqueda.next()) {
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
            listaBuqueda.add(pokemon);

        }
        return listaBuqueda;
    }

    /**
     * Ejecuta una consulta SQL para realizar operaciones en la base de datos.
     *
     * @param sqlQuery la consulta SQL a ejecutar
     * @param pokemon  el Pokemon utilizado para completar los parámetros de la consulta
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

    /**
     * Obtiene los tipos de Pokemon disponibles.
     *
     * @return un arreglo con los tipos de Pokemon
     */
    public String[] obtenerTiposPokemon() {
        TipoPokemon[] tiposPokemon = TipoPokemon.values();
        String[] tiposPokemonArray = new String[tiposPokemon.length];

        for (int i = 0; i < tiposPokemon.length; i++) {
            tiposPokemonArray[i] = tiposPokemon[i].toString();
        }

        return tiposPokemonArray;
    }

    /**
     * Busca Pokemon por tipo primario y/o tipo secundario.
     *
     * @param tipo1 el tipo primario del Pokemon
     * @param tipo2 el tipo secundario del Pokemon
     * @return una lista de Pokemon que coinciden con los tipos especificados
     */
    public List<Pokemon> buscarPorTipo(String tipo1, String tipo2) {
        String consulta = POKEMON_SEARCH + WHERE + BY_TYPE_1 + tipo1 + " AND " + BY_TYPE_2 + tipo2 + END_TYPE;

        /* Si tipo 1 es NINGUNO busca por tipo 2 y si tipo2 es NINGUNO busca por tipo1, si los dos son distintos busca
        por ambos */
        if (tipo1.equals(TipoPokemon.NINGUNO.toString())) {
            consulta = POKEMON_SEARCH + WHERE + BY_TYPE_2 + tipo2 + END_TYPE;
        } else if (tipo2.equals(TipoPokemon.NINGUNO.toString())) {
            consulta = POKEMON_SEARCH + WHERE + BY_TYPE_1 + tipo1 + END_TYPE;
        }
        List<Pokemon> listaPorTipo = new ArrayList<>();

        try (ResultSet allTipos = statement.executeQuery(consulta)) {
            listaPorTipo = leerPokemon(allTipos);
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
        return listaPorTipo;
    }
}
