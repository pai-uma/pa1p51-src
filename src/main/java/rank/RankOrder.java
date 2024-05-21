package rank;

import java.util.Comparator;

public class RankOrder implements Comparator<Site> {
    @Override
    public int compare(Site s1, Site s2) {
        return Double.compare(s1.getRank(), s2.getRank());
    }
}
