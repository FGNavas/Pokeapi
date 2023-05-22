package org.infantaelena.vista;
import org.infantaelena.controlador.Controlador;
import org.infantaelena.modelo.entidades.Pokemon;
import org.infantaelena.modelo.entidades.TipoPokemon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

/**
 * Clase que representa la vista de la aplicación
 * Implementar con menus de texto o con interfaz gráfica
 * @author
 * @version 1.0
 * @date 24/04/2023
 *
 */
public class Vista extends JFrame{
    private JPanel panelMain;
    private JTextField txtName;
    private JButton consultarPokémonButton;
    private JButton agregarPokémonButton;
    private JButton actualizarPokémonButton;
    private JButton borrarPokémonButton;
    private JTextField cajaNombre;
    private JComboBox cajaTipo1;
    private JComboBox cajaTipo2;
    private JTextField cajaSalud;
    private JTextField cajaAtaque;
    private JTextField cajaAtEsp;
    private JTextField cajaDefensa;
    private JTextField cajaVelocidad;
    private JTextField cajaDefEsp;
    private final String POKEMON_STATUS = "El pokemon ";
    private final String NOT_FOUND = " no se encuentra en la pokedex";
    private final String REPEATED = " esta repetido";

    private final String CREATED = " ha sido introducido ";
    private final String DELETED = " ha sido borrado";


    public JTextField getTxtName() {
        return txtName;
    }

    public void setTxtName(JTextField txtName) {
        this.txtName = txtName;
    }

    public JButton getConsultarPokémonButton() {
        return consultarPokémonButton;
    }

    public void setConsultarPokémonButton(JButton consultarPokémonButton) {
        this.consultarPokémonButton = consultarPokémonButton;
    }

    public JButton getAgregarPokémonButton() {
        return agregarPokémonButton;
    }

    public void setAgregarPokémonButton(JButton agregarPokémonButton) {
        this.agregarPokémonButton = agregarPokémonButton;
    }

    public JButton getActualizarPokémonButton() {
        return actualizarPokémonButton;
    }

    public void setActualizarPokémonButton(JButton actualizarPokémonButton) {
        this.actualizarPokémonButton = actualizarPokémonButton;
    }

    public JButton getBorrarPokémonButton() {
        return borrarPokémonButton;
    }

    public void setBorrarPokémonButton(JButton borrarPokémonButton) {
        this.borrarPokémonButton = borrarPokémonButton;
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


    // FUNCIONES AUXILIARES
    public void mensajes(String nombrePokemon, int codigoMensaje) {
        switch (codigoMensaje) {
            // NO ENCONTRADO
            case 1:
                JOptionPane.showMessageDialog(null, POKEMON_STATUS+nombrePokemon+NOT_FOUND);
                break;
            // REPETIDO
            case 2:
                JOptionPane.showMessageDialog(null, POKEMON_STATUS+nombrePokemon+REPEATED);
                break;
            // MENSAJE PERSONAL
            case 3:
                JOptionPane.showMessageDialog(null, nombrePokemon);
                break;
        }
    }
    public void inicializarComboBoxTiposPokemon(String[] tiposPokemon) {
        getCajaTipo1().removeAllItems();
        getCajaTipo2().removeAllItems();

        for (String tipo : tiposPokemon) {
            getCajaTipo1().addItem(tipo);
            getCajaTipo2().addItem(tipo);
        }
    }

}
