import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MockAnnealing {
    private int initTemp;

    public MockAnnealing(int initTemp) {
        this.initTemp = initTemp;
    }

    static Solutions initSolutions() {
        int n = Graph.getVertices();

        List<Integer> used = new ArrayList<Integer>(n);
        int numberCom = ThreadLocalRandom.current().nextInt(1, n); // Car un sommmet ne peut être dans plusieurs communauté à la fois
        List<List<Integer>> solutions = new ArrayList<List<Integer>>(numberCom);

        for (int i = 0; i < numberCom; i++) {
            // Pour chaque com poss tiré un nbre de sommmet aléatoire pouvant y être contenu + tiré les sommets de la com
            int maxEdges = n - numberCom - used.size() + 1;
            List<Integer> com = new ArrayList<Integer>();

            if (maxEdges <= 1) {
                int f = 1;
                while (used.contains(f) && f <= n) {
                    f++;
                }
                com.add(f);
                used.add(f);
            } else {
                int numberEdgesByCom = ThreadLocalRandom.current().nextInt(1, maxEdges);
                // - size de used car plus il y a de sommets utilisés moins il y a de possibilités

                for (int j = 0; j < numberEdgesByCom; j++) {
                    int edge = ThreadLocalRandom.current().nextInt(1, n + 1);
                    while (used.contains(edge)) {
                        edge = ThreadLocalRandom.current().nextInt(1, n + 1);
                    }
                    used.add(edge);
                    com.add(edge);
                }
            }
            solutions.add(com);
        }
        return new Solutions(solutions);




    }

    private int factoriel(int n) {
        return n > 1?n * factoriel(n-1):1;
    }














/**
    private Solutions mutation(Solutions solutions) {
        List<List<Integer>> copy = solutions.getSolutions();
        int indexCom1 = (int) (Math.random()*copy.size());

        while (copy.get(indexCom1).isEmpty()) {
            indexCom1=(int)(Math.random()*copy.size());
        }

        int indexCom2 = (int) (Math.random()*copy.size());

        // Pour être sûr d'avoir deux communautés différentes
        if (copy.size() != 1) {
            while (indexCom1 == indexCom2) {
                indexCom2 = (int) (Math.random()*copy.size());
            }
        }

        int indexVertice1 = (int) (Math.random()*copy.size());
        int indexVertice2 = (int) (Math.random()*copy.size());

        int vertice1 = copy.get(indexCom1).get(indexVertice1);
        int vertice2;
        if(!copy.get(indexCom2).isEmpty()) {
            vertice2 = copy.get(indexCom2).get(indexVertice2);
            copy.get(indexCom1).set(indexVertice1, vertice2);
            copy.get(indexCom2).set(indexVertice2, vertice1);
        } else {
            copy.get(indexCom2).add(vertice1);
            copy.get(indexCom1).remove(indexVertice1);
        }
        return new Solutions(copy);
    }**/
}
