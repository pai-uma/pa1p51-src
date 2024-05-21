package rank;

import java.util.Objects;

/**
 * Esta clase mantiene información sobre un hipervínculo de una página a otra. 
 * Tendrá un origin (de tipo String) que será el nombre de una página que contiene 
 * el hipervínculo y un linked (de tipo String) que será el nombre de la página 
 * a la que se enlaza.
 */
public class Link {
	// Variables privadas que apuntan al nombre de las páginas origen y destino del enlace, respectivamente.
    private String origin, linked;

    /**
     * Constructor para crear enlaces con un origen y destino propocionados como parámetros
     * @param o	Nombre del origen
     * @param d	Nombre del destino
     */
    public Link(String o, String d) {
        origin = o;
        linked = d;
    }

    /**
     * Obtiene el nombre del origen.
     * 
     * @return	Nombre del origen del enlace
     */
    public String getOrigin() {
        return origin;
    }

    /**
     * Obtiene el nombre del destino.
     * @return	Nombre del destino del enlace
     */
    public String getLinked() {
        return linked;
    }

    /** 
     * Dos enlaces son iguales si coinciden su nombre de página origen y destino 
     * sin tener en cuenta mayúsculas o minúsculas.
     */
    @Override
    public boolean equals(Object obj) {
        boolean ok = false;
        if (obj instanceof Link) {
            Link other = (Link) obj;
            ok = this.origin.equalsIgnoreCase(other.origin)
				&& this.linked.equalsIgnoreCase(other.linked);
        }
        return ok;
    }

    /**
     * Redefinición de hashCode acorde a la redefinición de equals.
     */
    @Override
    public int hashCode() {
        return Objects.hash(origin.toLowerCase(), linked.toLowerCase());
    }

    /**
     * Representación textual de un enlace:
     * 		origen -> destino
     */
    @Override
    public String toString() {
        return origin +"->"+ linked;
    }

}
