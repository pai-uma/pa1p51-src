import rank.Web;
import rank.WebExtended;

public class MainRank2 {
    public static void main(String[] args) {
        String[] enlaces = {"I->C",
			"J->C",
			"A->C",
			"A->D",
			"B->C",
			"B->F",
			"D->F",
			"E->B",
			"E->H",
			"F->G",
			"F->H",
			"G->E",
			"G->H"};

        WebExtended webe = new WebExtended();
        for (String arc: enlaces) {
            webe.addLink(arc);
		}
        webe.switchSiteWithName("A");
        webe.switchSiteWithName("I");
        webe.switchSiteWithName("J");
        System.out.println(webe);
        webe.simulateClick(4000);
        System.out.println("Paginas ordenadas alfabeticamente");
        System.out.println(webe.getSitesByName());
        System.out.println("Paginas ordenadas por rank");
        System.out.println(webe.getSitesByRank());
    }
}
