import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MockAnnealing {
    private int initTemp;
    private int n;

    public MockAnnealing(int initTemp) {
        this.initTemp = initTemp;
        this.n = Graph.getVertices();
    }

    private Solutions initSolutions() {
        List<Integer> used = new ArrayList<Integer>(n);

        // Tirage aléatoire d'un nombre de communauté
        int numberCom = ThreadLocalRandom.current().nextInt(1, n);
        List<List<Integer>> solutions = new ArrayList<List<Integer>>(numberCom);

        // Pour chaque communauté, tirage aléatoire d'un nombre de sommets puis remplir les communautés aléatoirement
        for (int i = 0; i < numberCom; i++) {
            // Le nbre max de sommets est déterminé à partir du nombre de sommet - nbre de communauté
            // (car plus il y a de communauté moins on peut mettre de sommet) - taille de utilisé
            // (car on ne peut pas utilisé deux fois le même sommet)
            int maxEdges = n - numberCom - used.size() + 1;
            List<Integer> com = new ArrayList<Integer>();

            // Cas où on ne peut plus que mettre un sommet dans la communauté
            if (maxEdges <= 1) {
                int f = 1;
                while (used.contains(f) && f <= n) {
                    f++;
                }
                com.add(f);
                used.add(f);
            } else {
                // Tirage aléatoire des sommets à ajouter à la communauté
                int numberEdgesByCom = ThreadLocalRandom.current().nextInt(1, maxEdges);

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
