package rank;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Esta clase mantiene información de todas las páginas que hay en la web 
 * y de todos los enlaces entre ellas. Las páginas las mantiene en un conjunto 
 * (protected Set<Site> sites) y los enlaces en otro conjunto (private Set<Link> links).
 */
public class Web {
	// Variable privada que almacena un umbral por debajo del cual no se consideran incentivos
    private static final double THRESHOLD = 1E-5;
    
    // Variable privada para generar números aleatorios con semilla 1 para pruebas/testing
    private static Random alea = new Random(1);
    
    // Conjunto de enlaces de la web
    private Set<Link> links;
    
    // Conjunto de páginas de la web
    protected Set<Site> sites;

    /**
     * Constructor para inicializar los valores de los conjuntos
     */
    public Web() {
        links = new HashSet<>();
        sites = new HashSet<>();
    }

    /**
     * Añade una página a la web
     * @param site	Página
     */
    protected void addSite(Site site) {
        sites.add(site);
    }

    /**
     * crea un página con nombre name y la añade con el método addSite.
     * 
     * @param name	Nombre de página a añadir
     */
    protected void addSiteWithName(String name) {
        Site site = new Site(name);
        addSite(site);
    }

    /**
     * A partir de una cadena del tipo A->B pasada como argumento, añade una página 
     * con nombre A y otra con nombre B al conjunto de páginas (con el método addSiteWithName), 
     * crea un enlace con estos nombres y lo añade al conjunto de enlaces. 
     * Este método puede fallar si la forma de dataLink no es la correcta. 
     * En ese caso lanza una IllegalArgumentException con un mensaje que incluya el 
     * dataLink que ha dado el problema.
     * @param dataLink	Cadena con información del enlace
     */
    public void addLink(String dataLink) {
        try {
            String[] datos = dataLink.split("->");
            addSiteWithName(datos[0]);
            addSiteWithName(datos[1]);
            links.add(new Link(datos[0], datos[1]));
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Bad line "+ dataLink );
        }
    }

    /**
     * Método para añadir enlaces pero resuelto con Scanner, en vez de con split.
     * 
     * @param dataLink Cadena con información del enlace
     */
    public void addLink_AlternativoConScanner(String dataLink) {
        try (Scanner sc = new Scanner(dataLink)) {
            sc.useDelimiter("->");
            String org = sc.next();
            String dst = sc.next();
            addSiteWithName(org);
            addSiteWithName(dst);
            links.add(new Link(org, dst));
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("Bad line "+ dataLink );
        }
    }

    /**
     * Método para añadir enlaces a partir de la información de un fichero, cuyas
     * líneas tienen el formato "A->B"
     * @param nomFichero	Nombre del fichero
     * @throws IOException
     */
    public void addLinkFromFile(String nomFichero) throws IOException {
    	try (Scanner scFichero = new Scanner(Path.of(nomFichero))) {
    		while (scFichero.hasNextLine())
    			addLink(scFichero.nextLine());
    	}
    }
    
    /**
     * Devuelve la página de nombre name del conjunto sites sin diferenciar 
     * mayúsculas o minúsculas. Si no existe esa página se lanza una 
     * NoSuchElementException indicando tal circunstancia.
     * 
     * @param name	Nombre de la página
     * @return		Página con el nombre 
     */
    public Site getSite(String name) {
        Site a = null;
        boolean ok = false;
        Iterator<Site> it = sites.iterator();
        while ( ! ok && it.hasNext()) {
            a = it.next();
            ok = name.equalsIgnoreCase(a.getName());
        }
        if ( ! ok) {
			throw new NoSuchElementException("No such site "+ name);
		}
        return a;
    }

    /**
     * Deuvleve un conjunto con los nombres de las páginas de la Web.
     * 
     * @return	Conjunto de nonmbres de páginas
     */
    public Set<String> getNames() {
        Set<String> set = new HashSet<>();
        for (Site site: sites) {
            set.add(site.getName());
		}
        return set;
    }

    /**
     * Dada una página, pasada como argumento, devuelve un conjunto con 
     * todas las páginas enlazadas desde ésta.
     * 
     * @param site	Página
     * @return	Conjunto de páginas enlazadas
     */
    private Set<Site> getSitesLinkedFrom(Site site) {
        Set<Site> setFrom = new HashSet<>();
        for (Link link : links) {
            Site org = this.getSite(link.getOrigin());
            if (org.equals(site)) {
                setFrom.add(this.getSite(link.getLinked()));
			}
        }
        return setFrom;
    }

    /**
     * Método que  distribuye el incentivo (prize) que se pasa como segundo argumento, de la siguiente manera:
     * 1. Si prize es menor que el umbral THRESHOLD no se hace nada. En otro caso se sigue con los siguientes pasos.
     * 2. La mitad de prize se utiliza para incrementar el posicionamiento de la página (site) que se pasa como primer argumento.
     * 3. La otra mitad se distribuye equitativamente de forma recursiva entre las páginas enlazadas desde site. 
     * 	  Así, si site tiene n enlaces a otras páginas, se distribuirá (de forma recursiva) a cada una de ellas 
     *    el valor prize/(2*n). Si site no tiene páginas enlazadas, el valor prize/2 se pierde.
     *    
     * @param site	Página para distribuir incentivos
     * @param prize	Incentivo a distribuir
     */
    protected void distribute(Site site, double prize) {
        if (prize >= THRESHOLD) {
            double halfPrize = prize/2;
            site.addRank(halfPrize);
            Set<Site> set = getSitesLinkedFrom(site);
            if (!set.isEmpty()) {
                double newPrize = halfPrize/set.size();
                for(Site s: set) {
                    distribute(s, newPrize);
				}
            }
        }
    }

    /**
     * Dado un nombre de página, distribuye (con el método distribute) a esa página el valor 1. 
     * Si se produce un error porque la página no existe, no hace nada y se ignora el mensaje.
     * 
     * @param name	Nombre de la página a incentivar
     */
    public void click(String name) {
        try {
            Site site = getSite(name);
            distribute(site, 1);
        } catch (NoSuchElementException e) {
            // Ignorar
        }
    }

    /**
     * Método que repite numClick veces el proceso de seleccionar aleatoriamente una página y hacer click sobre ella. 
     * Si no hubiera ninguna página en la web no hace nada. 
     * Para seleccionar aleatoriamente una página se utiliza una variable de tipo Random y una lista con todos los
     * nombres de sitios.
     * 
     * @param numClick	Número de clicks a simular
     */
    public void simulateClick(int numClick) {
        List<String> names = new ArrayList<>(this.getNames());
        int size = names.size();
        if (size > 0) {
            for(int i = 0; i < numClick; ++i) {
                int pos = alea.nextInt(size);
                this.click(names.get(pos));
            }
		}
    }

    /**
     * Devuelve el conjunto de páginas ordenadas por el orden natural.
     * @return
     */
    public SortedSet<Site> getSitesByName() {
        return new TreeSet<Site>(sites);
    }

    /**
     * Devuelve el conjunto de páginas ordenadas por el valor rank en orden 
     * decreciente y a igualdad de rank, por el orden natural.
     *
     * @return	Conjunto ordenado de páginas
     */
	public SortedSet<Site> getSitesByRank() {
		Comparator<Site> comp = new RankOrder().reversed().thenComparing(Comparator.naturalOrder());
		SortedSet<Site> res = new TreeSet<>(comp);
		res.addAll(sites);
		return res;
	}

	/**
	 * Representación textual de una Web.
	 */
    @Override
    public String toString() {
        return String.format("Web(%s, %s)", sites, links);
    }
}
