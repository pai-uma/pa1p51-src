package rank;

import java.util.Objects;
import java.util.Locale;

/**
 * Esta clase mantiene información de una página web. Por simplificar, 
 * solo mantendremos información del nombre de la página
 * (de tipo String) y de un valor double que determina el posicionamiento 
 * de esta página (double rank). Cuanto mayor sea el valor rank mejor 
 * posicionada se considerará la página.
 */
public class Site implements Comparable<Site> {
	// Variable que representa el nombre de la página web
    private String name;
    
    // Variable que almacena el posicionamiento del sitio
    private double rank;

    /**
     * Constructor para crear objetos Site a partir del nombre. 
     * Se supone que el posicionamiento comienza con valor 0.
     * 
     * @param n	Nonmbre de la página web	
     */
    public Site(String n) {
        name = n;
		rank = 0;
    }

    /**
     * Devuelve el nombre de la página web
     * 
     * @return	Nombre de la pábina web
     */
    public String getName() {
        return name;
    }

    /**
     * Devuelve el valor del posicionamiento.
     * 
     * @return	Valor del posicionamiento
     */
    public double getRank() {
        return rank;
    }

    /**
     * Método para incrementar el posicionamiento en el valor
     * proporcionado en el argumento.
     * 
     * @param r	Incremento del posicionamiento
     */
    public void addRank(double r) {
        rank += r;
    }

    /**
     * Dos páginas web son iguales si coinciden sus nombres 
     * sin tener en cuenta mayúsculas o minúsculas.
     */
    @Override
    public boolean equals(Object obj) {
        /* 
        boolean ok = false;
        if (obj instanceof Site) {
            Site other = (Site)obj;
            ok = name.equalsIgnoreCase(other.name);
        }
        return ok;
        */
    	return obj instanceof Site s && name.equalsIgnoreCase(s.name);
    }

    /**
     * Redefinición del mátodo hashCode() acorde a la redefinición del método equals.
     */
    @Override
    public int hashCode() {
        // return name.toLowerCase().hashCode();
        return Objects.hash(name.toLowerCase());
    }

    /**
     * Una página web se considera menor que otra cuando su nombre
     * es anterior lexicográficamente, sin tener en cuenta mayúsculas y minúsculas.
     */
    @Override
    public int compareTo(Site other) {
        return name.compareToIgnoreCase(other.name);
    }

    /**
     * Representación textual de una página web, determinada por su nombre seguido 
     * inmediatamente del posicionamiento con cinco decimales entre paréntesis.
     */
    @Override
    public String toString() {
        return String.format(Locale.UK, "%s(%.5f)", name, rank);
    }
}
