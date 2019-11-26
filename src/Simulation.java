import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Simulation {

    static float computeM(List<List<Integer>> lists , int m ){
        float mP = 0;
        for (List<Integer > list :lists  ) {
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
}
