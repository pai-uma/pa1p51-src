package rank;

/**
 * Una WebExtended es una Web pero que solo permite incluir páginas extendidas. 
 * A la hora de calcular el posicionamiento de una página, no distribuirá a 
 * aquellas que no sean válidas.
 */
public class WebExtended extends Web {

    //public WebExtended() {
	//	super();			  // Generado automaticamente por el compilador
    //}						
	
	/**
	 * Crea una página extendida con nombre name, y llama al método addSite 
	 * para añadirla al conjunto de páginas.
	 */
    @Override
    protected void addSiteWithName(String name) {
        SiteExtended siteExt = new SiteExtended(name);
        addSite(siteExt);
    }

    /**
     * Redefinición del método que se comporta como en la superclase, a no ser
     * que la página que se pasa como primer argumento no sea válida.
     */
    @Override
    protected void distribute(Site site, double prize) {
		SiteExtended siteExt = (SiteExtended)site;
		if (siteExt.isValid()) {
			super.distribute(site,prize);
		}
    }

    /**
     * Busca la página con el nombre dado en el argumento y cambia el estado de 
     * la página de válida a inválida o viceversa.
     * 
     * @param name	Nombre de la página
     */
    public void switchSiteWithName(String name) {
		SiteExtended siteExt = (SiteExtended)getSite(name);
		siteExt.setValid( ! siteExt.isValid());
    }
}
