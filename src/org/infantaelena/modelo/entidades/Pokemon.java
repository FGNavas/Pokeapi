package org.infantaelena.modelo.entidades;
/**
 * Clase que representa a un pokemon
 * @author
 * @version 1.0
 * @date 24/04/2023
 *
 */
public class Pokemon {
    private String nombre;
    private TipoPokemon tipoPrimario;
    private TipoPokemon tipoSecundario;
    private int puntosSalud;
    private int ataque;
    private int defensa;
    private int ataqueEspecial;
    private int defensaEspecial;
    private int velocidad;



    public Pokemon(String nombre, TipoPokemon tipoPrimario, TipoPokemon tipoSecundario, int puntosSalud,
                   int ataque, int defensa, int ataqueEspecial, int defensaEspecial, int velocidad) {
        this.nombre = nombre;
        this.tipoPrimario = tipoPrimario;
        this.tipoSecundario = tipoSecundario;
        this.puntosSalud = puntosSalud;
        this.ataque = ataque;
        this.defensa = defensa;
        this.ataqueEspecial = ataqueEspecial;
        this.defensaEspecial = defensaEspecial;
        this.velocidad = velocidad;
    }

    public Pokemon() {
        this.nombre = "";
        this.tipoPrimario = TipoPokemon.NORMAL;
        this.tipoSecundario =TipoPokemon.NINGUNO;
        this.puntosSalud = 1;
        this.ataque = 1;
        this.defensa = 1;
        this.ataqueEspecial = 1;
        this.defensaEspecial = 1;
        this.velocidad = 1;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public TipoPokemon getTipoPrimario() {
        return tipoPrimario;
    }

    public void setTipoPrimario(TipoPokemon tipoPrimario) {
        this.tipoPrimario = tipoPrimario;
    }

    public TipoPokemon getTipoSecundario() {
        return tipoSecundario;
    }

    public void setTipoSecundario(TipoPokemon tipoSecundario) {
        if(tipoPrimario.equals(tipoSecundario)) {
            this.tipoSecundario = TipoPokemon.NINGUNO;
        } else {
            this.tipoSecundario = tipoSecundario;
        }
    }

    public int getPuntosSalud() {
        return puntosSalud;
    }

    public void setPuntosSalud(int puntosSalud) {
        if(puntosSalud<=0){
            this.puntosSalud=1;
        }else {
            this.puntosSalud = puntosSalud;
        }
    }

    public int getAtaque() {
        return ataque;
    }

    public void setAtaque(int ataque) {
        if(ataque<=0){
            this.ataque=1;
        } else {
            this.ataque = ataque;
        }
    }

    public int getDefensa() {
        return defensa;
    }

    public void setDefensa(int defensa) {
        if(defensa<=0){
            this.defensa=1;
        } else {
            this.defensa = defensa;
        }
    }

    public int getAtaqueEspecial() {
        return ataqueEspecial;
    }

    public void setAtaqueEspecial(int ataqueEspecial) {
        if(ataqueEspecial<=0){
            this.ataqueEspecial=1;
        }else {
            this.ataqueEspecial = ataqueEspecial;
        }
    }

    public int getDefensaEspecial() {
        return defensaEspecial;
    }

    public void setDefensaEspecial(int defensaEspecial) {
        if(defensaEspecial<=0){
            this.defensaEspecial=1;
        } else {
            this.defensaEspecial = defensaEspecial;
        }
    }

    public int getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(int velocidad) {
        if(velocidad<=0){
            this.velocidad=1;
        } else {
            this.velocidad = velocidad;
        }
    }

}

