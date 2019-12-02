import java.util.List;

public class Solutions {
    private List<List<Integer>> solutions;

    Solutions(List<List<Integer>> solutions) {
        this.solutions = solutions;
    }

    float computeM(){
        int m = Graph.getEdges();
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
        return mP/(2*m);
    }

    List<List<Integer>> getSolutions() {
        return solutions;
    }

}
