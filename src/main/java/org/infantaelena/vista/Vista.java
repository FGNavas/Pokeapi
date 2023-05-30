package org.infantaelena.vista;

import org.infantaelena.modelo.entidades.Pokemon;
import org.infantaelena.modelo.entidades.TipoPokemon;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.Arrays;
import java.util.List;

/**
 * Clase que representa la vista de la aplicación
 * Implementar con menus de texto o con interfaz gráfica
 * Clase que representa la vista de la aplicación.
 * Implementa la interfaz gráfica de usuario para interactuar con la funcionalidad del programa.
 * Extiende la clase JFrame para crear una ventana principal.
 * Permite consultar, agregar, actualizar y borrar Pokémon, y mostrar una lista de todos los Pokémon.
 * Utiliza componentes de la biblioteca Swing para la construcción de la interfaz gráfica.
 *
 * @author Fernando
 * @author Pablo
 * @version 2.0
 * @date 30/05/2023
 */
public class Vista extends JFrame {
    private JPanel panelMain;
    private JTextField txtName;
    private JButton consultarPokemonButton;
    private JButton agregarPokemonButton;
    private JButton actualizarPokemonButton;
    private JButton borrarPokemonButton;
    private JTextField cajaNombre;
    private JComboBox cajaTipo1;
    private JComboBox cajaTipo2;
    private JTextField cajaSalud;
    private JTextField cajaAtaque;
    private JTextField cajaAtEsp;
    private JTextField cajaDefensa;
    private JTextField cajaVelocidad;
    private JTextField cajaDefEsp;
    private JButton mostrarTodosLosPokemonButton;
    private JButton buscarPorTipo;

    // Frases de estado
    private final String POKEMON_STATUS = "El pokemon ";
    private final String NOT_FOUND = " no se encuentra en la pokedex";
    private final String REPEATED = " esta repetido";

    private final String CREATED = " ha sido introducido ";
    private final String DELETED = " ha sido borrado";

    public JButton getMostrarTodosLosPokemonButton() {
        return mostrarTodosLosPokemonButton;
    }

    public void setMostrarTodosLosPokemonButton(JButton mostrarTodosLosPokemonButton) {
        this.mostrarTodosLosPokemonButton = mostrarTodosLosPokemonButton;
    }

    public JTextField getTxtName() {
        return txtName;
    }

    public void setTxtName(JTextField txtName) {
        this.txtName = txtName;
    }

    public JButton getConsultarPokemonButton() {
        return consultarPokemonButton;
    }

    public void setConsultarPokemonButton(JButton consultarPokemonButton) {
        this.consultarPokemonButton = consultarPokemonButton;
    }

    public JButton getAgregarPokemonButton() {
        return agregarPokemonButton;
    }

    public void setAgregarPokemonButton(JButton agregarPokemonButton) {
        this.agregarPokemonButton = agregarPokemonButton;
    }

    public JButton getActualizarPokemonButton() {
        return actualizarPokemonButton;
    }

    public void setActualizarPokemonButton(JButton actualizarPokemonButton) {
        this.actualizarPokemonButton = actualizarPokemonButton;
    }

    public JButton getBorrarPokemonButton() {
        return borrarPokemonButton;
    }

    public void setBorrarPokemonButton(JButton borrarPokemonButton) {
        this.borrarPokemonButton = borrarPokemonButton;
    }

    public JTextField getCajaNombre() {
        return cajaNombre;
    }

    public void setCajaNombre(JTextField cajaNombre) {
        this.cajaNombre = cajaNombre;
    }

    public JComboBox getCajaTipo1() {
        return cajaTipo1;
    }

    public void setCajaTipo1(JComboBox cajaTipo1) {
        this.cajaTipo1 = cajaTipo1;
    }

    public JComboBox getCajaTipo2() {
        return cajaTipo2;
    }

    public void setCajaTipo2(JComboBox cajaTipo2) {
        this.cajaTipo2 = cajaTipo2;
    }

    public JTextField getCajaSalud() {
        return cajaSalud;
    }

    public void setCajaSalud(JTextField cajaSalud) {
        this.cajaSalud = cajaSalud;
    }

    public JTextField getCajaAtaque() {
        return cajaAtaque;
    }

    public void setCajaAtaque(JTextField cajaAtaque) {
        this.cajaAtaque = cajaAtaque;
    }

    public JTextField getCajaAtEsp() {
        return cajaAtEsp;
    }

    public void setCajaAtEsp(JTextField cajaAtEsp) {
        this.cajaAtEsp = cajaAtEsp;
    }

    public JTextField getCajaDefensa() {
        return cajaDefensa;
    }

    public void setCajaDefensa(JTextField cajaDefensa) {
        this.cajaDefensa = cajaDefensa;
    }

    public JTextField getCajaVelocidad() {
        return cajaVelocidad;
    }

    public void setCajaVelocidad(JTextField cajaVelocidad) {
        this.cajaVelocidad = cajaVelocidad;
    }

    public JTextField getCajaDefEsp() {
        return cajaDefEsp;
    }

    public void setCajaDefEsp(JTextField cajaDefEsp) {
        this.cajaDefEsp = cajaDefEsp;
    }

    public JButton getBuscarPorTipo() {
        return buscarPorTipo;
    }

    public void setBuscarPorTipo(JButton buscarPorTipo) {
        this.buscarPorTipo = buscarPorTipo;
    }

    /**
     * Constructor de la clase Vista.
     * Configura la apariencia de la ventana y carga los valores iniciales del ComboBox de tipos de Pokémon.
     */
    public Vista() {
        setContentPane(panelMain);
        setTitle("PokeApi");
        setSize(1000, 800);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<TipoPokemon> result = Arrays.asList(TipoPokemon.values());
        for (TipoPokemon tipo : result) {
            getCajaTipo1().addItem(tipo);
            getCajaTipo2().addItem(tipo);
        }
    }

    /**
     * Muestra un mensaje de acuerdo al código de mensaje proporcionado.
     *
     * @param nombrePokemon El nombre del Pokémon al que se refiere el mensaje.
     * @param codigoMensaje El código del mensaje que determina qué tipo de mensaje mostrar.
     */
    // FUNCIONES AUXILIARES
    public void mensajes(String nombrePokemon, int codigoMensaje) {
        switch (codigoMensaje) {
            // NO ENCONTRADO
            case 1:
                JOptionPane.showMessageDialog(null, POKEMON_STATUS + nombrePokemon + NOT_FOUND);
                break;
            // REPETIDO
            case 2:
                JOptionPane.showMessageDialog(null, POKEMON_STATUS + nombrePokemon + REPEATED);
                break;
            // MENSAJE PERSONAL
            case 3:
                JOptionPane.showMessageDialog(null, nombrePokemon);
                break;
        }
    }

    /**
     * Inicializa los ComboBox de tipos de Pokémon con los valores proporcionados.
     *
     * @param tiposPokemon Un array de strings que contiene los tipos de Pokémon.
     */
    public void inicializarComboBoxTiposPokemon(String[] tiposPokemon) {
        getCajaTipo1().removeAllItems();
        getCajaTipo2().removeAllItems();

        for (String tipo : tiposPokemon) {
            getCajaTipo1().addItem(tipo);
            getCajaTipo2().addItem(tipo);
        }
    }

    /**
     * Crea una tabla con la lista de Pokémon proporcionada.
     *
     * @param listaPokemon La lista de Pokémon a mostrar en la tabla.
     */
    public void crearTabla(List<Pokemon> listaPokemon) {

        // Crear una nueva ventana para mostrar los resultados
        JFrame ventanaResultados = new JFrame("Lista de Pokémon");
        ventanaResultados.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Definir las columnas de la tabla
        String[] columnas = {"Nombre", "Tipo 1", "Tipo 2", "Salud", "Ataque", "Defensa", "Ataque Especial", "Defensa Especial", "Velocidad"};

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
            contador = contador + 1;
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
