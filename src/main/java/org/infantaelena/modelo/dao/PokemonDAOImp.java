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
 * @version 1.3
 * @date 08/05/2023
 */
public class PokemonDAOImp implements PokemonDAO {
    private final String RUTA ="jdbc:sqlite:main/resources/";
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
            + " velocidad INT,\n"
            + ");\n";

    private final String POKEMON_UPDATE ="UPDATE pokemon SET tipoPrimario = ?, tipoSecundario = ?, puntoSalud = ?," +
            " ataque = ?, defensa = ?, ataqueEspecial = ?, defensaEspecial = ?, velocidad = ? WHERE id = ?";
    private final String POKEMON_INSERT= "INSERT INTO pokemon (nombre, tipoPrimario, tipoSecundario, puntosSalud," +
            " ataque, defensa, ataqueEspecial, defensaEspecial, velocidad) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private final String POKEMON_DELETE = "DELETE FROM pokemon ";
    private final String POKEMON_SEARCH ="SELECT * FROM pokemon ";
    private final String BY_NAME = " WHERE nombre= ";
    private Connection connection;
    private Statement statement;



   public PokemonDAOImp (){
        try {
            connection = DriverManager.getConnection(RUTA+DEFAULT_DB);
            statement = connection.createStatement();

            String createTableSQL = POKEMON_TABLE;
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

  public  PokemonDAOImp (String db){
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:resources/"+db);
            String createTableSQL = POKEMON_TABLE;
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // FUNCIONES AUXILIARES
    private Pokemon leerPokemon(ResultSet resultBusqueda) {
       Pokemon pokemonBuscado;
        try {
            String nombre = resultBusqueda.getNString("nombre");
            TipoPokemon tipPri = TipoPokemon.valueOf(resultBusqueda.getString("tipoPrimario"));
            TipoPokemon tipSec = TipoPokemon.valueOf(resultBusqueda.getString("tipoSecundario"));
            int puntosSalud = resultBusqueda.getInt("puntosSalud");
            int ataque = resultBusqueda.getInt("ataque");
            int defensa = resultBusqueda.getInt("defensa");
            int ataqueEspecial = resultBusqueda.getInt("ataqueEspecial");
            int defensaEspecial = resultBusqueda.getInt("defensaEspecial");
            int velocidad = resultBusqueda.getInt("velocidad");
            pokemonBuscado = new Pokemon (nombre,tipPri,tipSec,
                    puntosSalud,ataque,defensa,ataqueEspecial,defensaEspecial,velocidad);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

       return pokemonBuscado;
    }

    public void trabajoSQL(String sqlQuery, Pokemon pokemon) {
        try {
            PreparedStatement updateStatement = connection.prepareStatement(sqlQuery);
            updateStatement.setString(1, pokemon.getNombre());
            updateStatement.setString(2, pokemon.getTipoPrimario().toString());
            updateStatement.setString(3, pokemon.getTipoSecundario().toString());
            updateStatement.setInt(4, pokemon.getPuntosSalud());
            updateStatement.setInt(5, pokemon.getAtaque());
            updateStatement.setInt(6, pokemon.getDefensa());
            updateStatement.setInt(7, pokemon.getAtaqueEspecial());
            updateStatement.setInt(8, pokemon.getDefensaEspecial());
            updateStatement.setInt(9, pokemon.getVelocidad());
            updateStatement.executeUpdate();
            updateStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void crear(Pokemon pokemon) throws PokemonRepeatedException {
        Pokemon encontrado = null;
        try {
            encontrado = leerPorNombre(pokemon.getNombre());
        } catch (PokemonNotFoundException e) {

        }
        if (encontrado != null) {
            throw new PokemonRepeatedException();
        } else {
            String sql =POKEMON_INSERT ;
            trabajoSQL(sql, pokemon);
        }
    }

    @Override
    public Pokemon leerPorNombre(String nombre) throws PokemonNotFoundException {
        Pokemon pokemonBuscado = null;
        ResultSet buscar = null;
        try {
            buscar = statement.executeQuery(POKEMON_SEARCH + BY_NAME + nombre);
            if (buscar.next() == false) {
                throw new PokemonNotFoundException();
            } else {
                pokemonBuscado = leerPokemon(buscar);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {

                try {
                    buscar.close();
                } catch (SQLException e) {

                }

        }
        return pokemonBuscado;
    }


    @Override
    public List<Pokemon> leerTodos() {
        List<Pokemon> listaPokemon = new ArrayList<>();
        try {
            ResultSet allPokedex = statement.executeQuery(POKEMON_SEARCH);
            while (allPokedex.next()){
                Pokemon pokemon = leerPokemon(allPokedex);
                listaPokemon.add(pokemon);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaPokemon;

    }
    @Override
    public void actualizar(Pokemon pokemon) throws PokemonNotFoundException {
        String nombreAbuscar = pokemon.getNombre();
        ResultSet pokemonEnPokedex = null;
        try {
            pokemonEnPokedex = statement.executeQuery(POKEMON_SEARCH + BY_NAME + nombreAbuscar);
            if (!pokemonEnPokedex.next()) {
                throw new PokemonNotFoundException();
            } else {
                String updateQuery = POKEMON_UPDATE;
                trabajoSQL(updateQuery, pokemon);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (pokemonEnPokedex != null) {
                try {
                    pokemonEnPokedex.close();
                } catch (SQLException e) {
                    // Manejar cualquier excepción de SQL al cerrar el ResultSet
                }
            }
        }
    }


    @Override
    public void eliminarPorNombre(String nombre) throws PokemonNotFoundException {


    }

}
