import java.io.IOException;

import rank.Web;
import rank.WebExtended;

public class MainRank {
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

        Web web = new Web();
        for (String arc: enlaces) {
            web.addLink(arc);
		}
        System.out.println(web);
        web.simulateClick(4000);
        System.out.println("Paginas ordenadas alfabeticamente");
        System.out.println(web.getSitesByName());
        System.out.println("Paginas ordenadas por rank");
        System.out.println(web.getSitesByRank());
    }
}
