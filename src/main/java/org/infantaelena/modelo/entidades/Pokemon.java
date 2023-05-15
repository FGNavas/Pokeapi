package org.infantaelena.modelo.entidades;
/**
 * Clase que representa a un pokemon
 * @author Fernando
 * @author Pablo
 * @version 1.2
 * @date 08/05/2023
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

    /**
     * Crea un nuevo objeto Pokemon con todos los atributos especificados. En los set se establece la condicion para
     * que no se introduzcan valores negativos en cuyo caso se establecera valor 1
     *
     * @param nombre            el nombre del pokemon
     * @param tipoPrimario      el tipo primario del pokemon
     * @param tipoSecundario    el tipo secundario del pokemon
     * @param puntosSalud       los puntos de salud del pokemon
     * @param ataque            el valor de ataque del pokemon
     * @param defensa           el valor de defensa del pokemon
     * @param ataqueEspecial    el valor de ataque especial del pokemon
     * @param defensaEspecial   el valor de defensa especial del pokemon
     * @param velocidad         el valor de velocidad del pokemon
     */
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

    /**
     * Crea un nuevo objeto Pokemon con valores predeterminados
     */
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

    /**
     * Devuelve el nombre del pokemon.
     *
     * @return el nombre del pokemon
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del pokemon.
     *
     * @param nombre el nombre del pokemon
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Devuelve el tipo primario del pokemon.
     *
     * @return el tipo primario del pokemon
     */
    public TipoPokemon getTipoPrimario() {
        return tipoPrimario;
    }

    /**
     * Establece el tipo primario del pokemon. En caso de que el usuario le de el valor NINGUNO se establece por
     * defecto el tipo NORMAL
     *
     * @param tipoPrimario el tipo primario del pokemon
     */
    public void setTipoPrimario(TipoPokemon tipoPrimario) {
        if(tipoPrimario.equals(TipoPokemon.NINGUNO)){
            this.tipoPrimario = TipoPokemon.NORMAL;
        } else {
            this.tipoPrimario = tipoPrimario;
        }
        }

    /**
     * Devuelve el tipo secundario del pokemon.
     *
     * @return el tipo secundario del pokemon
     */
    public TipoPokemon getTipoSecundario() {
        return tipoSecundario;
    }

    /**
     * Establece el tipo secundario del pokemon. No permite repetir tipo, si introduce el mismo tipo que el primario
     * se cambia a un valor por defecto NINGUNO
     *
     * @param tipoSecundario el tipo secundario del pokemon
     */
    public void setTipoSecundario(TipoPokemon tipoSecundario) {
        if(tipoPrimario.equals(tipoSecundario)) {
            this.tipoSecundario = TipoPokemon.NINGUNO;
        } else {
            this.tipoSecundario = tipoSecundario;
        }
    }
    /**
     * Devuelve los puntos de salud del pokemon.
     *
     * @return los puntos de salud del pokemon
     */
    public int getPuntosSalud() {
        return puntosSalud;
    }

    /**
    * Establece los puntos de salud del pokemon.
    *
    * @param puntosSalud los puntos de salud del pokemon
    */
    public void setPuntosSalud(int puntosSalud) {
        if(puntosSalud<=0){
            this.puntosSalud=1;
        }else {
            this.puntosSalud = puntosSalud;
        }
    }

    /**
     * Devuelve el valor de ataque del pokemon.
     *
     * @return el valor de ataque del pokemon
     */
    public int getAtaque() {
        return ataque;
    }

    /**
     * Establece el valor de ataque del pokemon.
     *
     * @param ataque el valor de ataque del pokemon
     */
    public void setAtaque(int ataque) {
        if(ataque<=0){
            this.ataque=1;
        } else {
            this.ataque = ataque;
        }
    }

    /**
     * Devuelve el valor de defensa del pokemon.
     *
     * @return el valor de defensa del pokemon
     */
    public int getDefensa() {
        return defensa;
    }

    /**
     * Establece el valor de defensa del pokemon.
     *
     * @param defensa el valor de defensa del pokemon
     */
    public void setDefensa(int defensa) {
        if(defensa<=0){
            this.defensa=1;
        } else {
            this.defensa = defensa;
        }
    }
    /**
     * Devuelve el valor de ataque especial del pokemon.
     *
     * @return el valor de ataque especial del pokemon
     */
    public int getAtaqueEspecial() {
        return ataqueEspecial;
    }
    /**
     * Establece el valor de ataque especial del pokemon.
     *
     * @param ataqueEspecial el valor de ataque especial del pokemon
     */
    public void setAtaqueEspecial(int ataqueEspecial) {
        if(ataqueEspecial<=0){
            this.ataqueEspecial=1;
        }else {
            this.ataqueEspecial = ataqueEspecial;
        }
    }

    /**
     * Devuelve el valor de defensa especial del pokemon.
     *
     * @return el valor de defensa especial del pokemon
     */
    public int getDefensaEspecial() {
        return defensaEspecial;
    }

    /**
     * Establece el valor de defensa especial del pokemon.
     *
     * @param defensaEspecial el valor de defensa especial del pokemon
     */
    public void setDefensaEspecial(int defensaEspecial) {
        if(defensaEspecial<=0){
            this.defensaEspecial=1;
        } else {
            this.defensaEspecial = defensaEspecial;
        }
    }

    /**
     * Devuelve el valor de velocidad del pokemon.
     *
     * @return el valor de velocidad del pokemon
     */
    public int getVelocidad() {
        return velocidad;
    }

    /**
     * Establece el valor de velocidad del pokemon.
     *
     * @param velocidad el valor de velocidad del pokemon
     */
    public void setVelocidad(int velocidad) {
        if(velocidad<=0){
            this.velocidad=1;
        } else {
            this.velocidad = velocidad;
        }
    }

    /**
     * Devuelve una representación en forma de cadena del pokemon. Esta funcion se hizo para la lectura de ficheros
     * al realizar consulta a base de datos ya no es necesaria
     *
     * @return una cadena que representa al pokemon
     */
    @Override
    public String toString() {
        return String.format("%s:%s:%s:%d:%d:%d:%d:%d:%d",
                this.nombre,this.tipoPrimario,this.tipoSecundario,this.puntosSalud,this.ataque,
                this.defensa,this.ataqueEspecial,this.defensaEspecial,this.velocidad);
    }
}

