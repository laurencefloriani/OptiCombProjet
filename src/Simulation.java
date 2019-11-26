import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private ArrayList<Graph> liste_de_matrices ;

/**
    public Simulation(ArrayList<Graph liste , int m>){
        liste_de_matrices = liste;
    }**/
    public void Start(){}


    @SuppressWarnings("Bug sur la valeur retournée")

    public static float computeM(List<List<Integer>> listes , int m ){
        float mP = 0;
        for (List<Integer > liste :listes  ) {
            float temp = 0;
            for ( int i : liste ) {
                for(int j : liste) {
                    temp += Graph.matrix[i-1][j-1].getLinked() - Graph.matrix[i-1][j-1].getProbability();
                }

            }
            mP += temp;
        }
        return mP/(2*m);
    }
}
