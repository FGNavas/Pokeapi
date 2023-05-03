package org.infantaelena.modelo.dao;

import org.infantaelena.excepciones.PokemonNotFoundException;
import org.infantaelena.excepciones.PokemonRepeatedException;
import org.infantaelena.modelo.entidades.Pokemon;
import org.infantaelena.modelo.entidades.TipoPokemon;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Clase que implementa los métodos de acceso a datos de la clase Pokemon
 * Esta puede hacerse mediante un fichero CSV separado por puntos y coma o una base de datos
 *
 * @author
 * @version 1.0
 * @date 24/04/2023
 */
public class PokemonDAOImp implements PokemonDAO {
    private static final String RUTA = "modelo/dao/pokedex.csv";
    private List<Pokemon> listaSesion;


    /**
     * Funciones auxiliares
     */

    public void crearPokedexCSV(){
        File pokedex = new File(RUTA);
        if(!pokedex.exists()){
            try {
                pokedex.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el fichero");
            }
        }
    }
    private Pokemon leerPokemon(String linea) {
        String[] campo = linea.split(":");
        String nombre = campo[0];
        TipoPokemon tipoPrimario = TipoPokemon.valueOf(campo[1]);
        TipoPokemon tipoSecundario = TipoPokemon.valueOf(campo[2]);
        int puntosSalud = Integer.parseInt(campo[3]);
        int ataque = Integer.parseInt(campo[4]);
        int defensa = Integer.parseInt(campo[5]);
        int ataqueEspecial = Integer.parseInt(campo[6]);
        int defensaEspecial = Integer.parseInt(campo[7]);
        int velocidad = Integer.parseInt(campo[8]);
        return new Pokemon(nombre, tipoPrimario, tipoSecundario, puntosSalud,
                ataque, defensa, ataqueEspecial, defensaEspecial, velocidad);
    }


    @Override
    public void crear(Pokemon pokemon) throws PokemonRepeatedException {

        try {
            Pokemon encontrado = leerPorNombre(pokemon.getNombre());
            if (encontrado != null) {
                throw new PokemonRepeatedException("Pokemon repetido");
            } else {
                try (FileWriter fw = new FileWriter(RUTA, true);
                     PrintWriter pw = new PrintWriter(fw)) {
                    pw.println(pokemon.getNombre() + ":" + pokemon.getTipoPrimario() + ":" + pokemon.getTipoSecundario() + ":" +
                            pokemon.getPuntosSalud() + ":" + pokemon.getAtaque() + ":" + pokemon.getDefensa() + ":" +
                            pokemon.getAtaqueEspecial() + ":" + pokemon.getDefensaEspecial() + ":" + pokemon.getVelocidad());
                } catch (IOException e) {
                    System.err.println("Error al escribir el fichero");
                }

            }
        } catch (PokemonNotFoundException e) {

        } catch (PokemonRepeatedException e) {

        }

    }

    @Override
    public Pokemon leerPorNombre(String nombre) throws PokemonNotFoundException {
        File pokedex = new File(RUTA);
        boolean encontrado = false;
        try (FileReader fr = new FileReader(pokedex);
             BufferedReader br = new BufferedReader(fr)) {
            String linea;
            while ((linea = br.readLine()) != null && !encontrado) {
                Pokemon pokemon = leerPokemon(linea);
                if (pokemon.getNombre().startsWith(nombre)) {
                    encontrado = true;
                    return pokemon;
                }
            }
            if (!encontrado) {
                throw new PokemonNotFoundException("Pokemon no enconrado en la pokedex");
            }
        } catch (FileNotFoundException e) {
            System.err.println("No se encontro el fichero");
        } catch (IOException e) {
            System.err.println("Error al leer el fichero");
        } catch (PokemonNotFoundException e) {

        }
        return null;
    }

    @Override
    public List<Pokemon> leerTodos() {
        List<Pokemon> listaPokemon = new ArrayList<>();
        File pokedex = new File(RUTA);
        try (FileReader fr = new FileReader(pokedex);
             BufferedReader br = new BufferedReader(fr)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                Pokemon pokemon = leerPokemon(linea);
                listaPokemon.add(pokemon);
            }

        } catch (FileNotFoundException e) {
            System.err.println("No se encontro el fichero");
        } catch (IOException e) {
            System.err.println("Error al leer el fichero");
        }

        return listaPokemon;

    }

    @Override
    public void actualizar(Pokemon pokemon) throws PokemonNotFoundException {

    }

    @Override
    public void eliminarPorNombre(String nombre) throws PokemonNotFoundException {

    }
}
