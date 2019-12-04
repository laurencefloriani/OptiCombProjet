import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MockAnnealing {
    private int initTemp;
    private int n;
    private float MPTRESHOLD = 0.357f;

    public MockAnnealing(int initTemp) {
        this.initTemp = initTemp;
        this.n = Graph.getVertices();
        start();
    }

    private void start() {
        Solutions solutions = generateSolution();
        System.out.println(solutions.toString() + " M(P) : " + solutions.getmP());
        while(solutions.getmP() < MPTRESHOLD) {
            for(List<Integer> l : solutions.getSolutions()) {
                if(l.size() == 1) {
                    decraeseNbrCom(solutions);
                }
            }
            mutation(solutions);
            System.out.println(solutions.toString() + " M(P) : " + solutions.getmP());
        }
    }

    private void decraeseNbrCom(Solutions solutions) {
        List<List<Integer>> list = solutions.getSolutions();
        if(list.size() > 1) {
            int newNbreCom = ThreadLocalRandom.current().nextInt(0, list.size());
            while (list.size() != newNbreCom) {
                int indexCom1 = ThreadLocalRandom.current().nextInt(0, list.size());
                int indexCom2 = ThreadLocalRandom.current().nextInt(0, list.size());

                List<Integer> com = list.get(indexCom1);
                com.addAll(list.get(indexCom2));

                list.remove(indexCom1);
                list.remove(indexCom2);

                Collections.sort(com);
                list.add(com);
            }
        }
    }

    private void mutation(Solutions solutions) {
        List<List<Integer>> list = solutions.getSolutions();

        int indexCom1 = ThreadLocalRandom.current().nextInt(0, list.size());
        int indexCom2 = ThreadLocalRandom.current().nextInt(0, list.size());

        // Travailler dans deux communautés différentes
        if (list.size() != 1) {
            while (indexCom1 == indexCom2) {
                indexCom2 = ThreadLocalRandom.current().nextInt(0, list.size());;
            }
        }

        int indexEdge1 = ThreadLocalRandom.current().nextInt(0, list.get(indexCom1).size());
        int indexEdge2 = ThreadLocalRandom.current().nextInt(0, list.get(indexCom2).size());

        int edge1 = list.get(indexCom1).get(indexEdge1);
        int edge2;
        if (!list.get(indexCom2).isEmpty()) {

            edge2 = list.get(indexCom2).get(indexEdge2);
            list.get(indexCom1).set(indexEdge1, edge2);
            list.get(indexCom2).set(indexEdge2, edge1);
        } else {
            list.get(indexCom2).add(edge1);
            list.get(indexCom1).remove(indexEdge1);
        }
        solutions.setSolutions(list);
    }

    private Solutions generateSolution() {
        List<Integer> used = new ArrayList<Integer>(n);

        // Tirage aléatoire d'un nombre de communauté (si n communauté => 1 sommet par com => useless
        int numberCom = ThreadLocalRandom.current().nextInt(1, n/2);
        List<List<Integer>> solutions = new ArrayList<List<Integer>>(numberCom);

        // Pour chaque communauté, tirage aléatoire d'un nombre de sommets puis remplir les communautés aléatoirement
        for (int i = 0; i < numberCom; i++) {
            // Le nbre max de sommets est déterminé à partir du nombre de sommet
            // - nbre de communauté (car plus il y a de communauté moins on peut mettre de sommet)
            // - taille de utilisé (car on ne peut pas utilisé deux fois le même sommet)
            int maxEdges = n - numberCom - used.size();
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
            Collections.sort(com);
            solutions.add(com);
        }
        Solutions sol = new Solutions(solutions);
        return sol;
    }
}
