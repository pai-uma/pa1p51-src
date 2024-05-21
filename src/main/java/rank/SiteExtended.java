package rank;

/**
 * La clase SiteExtended extiende a la clase Site e incluye en su estado una 
 * variable booleana para indicar si la página se debe tener en cuenta 
 * para el cálculo del posicionamiento.
 */
public class SiteExtended extends Site {
	// Variable privada para calificar la página como válida para calcular posicionamiento
    private boolean valid;

    /**
     * Constructor de una página, a partir de su nombre.
     * 
     * @param name	Nombre de la página
     */
    public SiteExtended(String name) {
        super(name);
        valid = true;
    }

    /**
     * Cambia el valor de la variable que indica si la página se tiene en cuenta o no
     * para el cálculo del posicionamiento.
     * 
     * @param v	Nuevo valor
     */
    public void setValid(boolean v) {
        this.valid = v;
    }

    /**
     * Devuelve si la página se considera para calcular posicionamientos.
     * 
     * @return Boolean
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * Devuelve la representación textual.
     */
    @Override
    public String toString() {
        return super.toString() + (valid ? "" : "*");
    }
}
