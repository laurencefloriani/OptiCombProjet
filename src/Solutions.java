import java.util.Iterator;
import java.util.List;

public class Solutions {
    private List<List<Integer>> solutions;
    private float mP;

    Solutions(List<List<Integer>> solutions, int m) {
        this.solutions = solutions;
        this.mP = computeM(m);
    }

    private float computeM(int m){
        float mP = 0;
        for (List<Integer > list : solutions  ) {
            float temp = 0;
            for ( int i : list) {
                for(int j : list) {
                    // -1 à chaque sommet car matrice indicée à partir de 0 et nom des sommets donnés à partir de 1
                    temp += Graph.matrix[i-1][j-1].getLinked() - Graph.matrix[i-1][j-1].getProbability()/(2*m);
                }
            }
            mP += temp;
        }
        System.out.println("---------");
        return mP/(2*m);
    }

    List<List<Integer>> getSolutions() {
        return solutions;
    }

    float getmP() {
        return mP;
    }

    void setSolutions(List<List<Integer>> solutions, int m) {
        this.solutions = solutions;
        deleteEmptyList();
        this.mP = computeM(m);
    }

    private void deleteEmptyList() {
        Iterator<List<Integer>> iterator = this.solutions.iterator();
        while (iterator.hasNext() ) {
            List<Integer> o = iterator.next();
            if (o.size() == 0) {
                System.out.println(o);

                // On supprime l'élément courant de la liste
                iterator.remove();
            }
        }

    }
    @Override
    public String toString() {
        String str = "";
        for (List<Integer> com : solutions) {
            str += "COM : ";
            for (int edge : com) {
                str += edge;
                str += " ";
            }
            str += "\n";
        }
        return str;
    }

    @Override
    public boolean equals(Object o) {
        if(!(o instanceof Solutions)) {
            return false;
        } else {
            for (List<Integer> com1 : solutions) {
                for (int edge1 : com1) {
                    for (List<Integer> com2 : ((Solutions) o).getSolutions()) {
                        for (int edge2 : com2) {
                            if (edge1 != edge2) {
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }
    }
}
