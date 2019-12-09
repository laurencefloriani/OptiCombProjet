import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class MockAnnealing {
    private Graph graph;
    private float temp;
    private float energy;
    private Solutions bestSol;
    private Solutions currentSol;

    private float MPTRESHOLD = 0.357f;
    private float LAMBDA = 0.99f;

    MockAnnealing(String name) {
        this.graph =  FilesProcessing.readFile(name);
        this.temp = graph.getEdges()*graph.getVertices();
        this.energy = temp*4;
        start();
        System.out.println(bestSol.getmP());
        if(bestSol.getmP() < MPTRESHOLD) {
            start();
        }
        System.out.println("La solution est : " + bestSol.getSolutions());
        System.out.println("Avec un M(P) de : " + bestSol.getmP());
        FilesProcessing.writeInFile(bestSol);
    }

    private void start() {
        bestSol = generateSolution();
        currentSol = bestSol.copy();
        int u = 1;

        while(energy > temp) {
            System.out.println("itération : " + u);
            System.out.println(bestSol.toString() + "    M(P) : " + bestSol.getmP());
            float oldMP = bestSol.getmP();
            int nbre = ThreadLocalRandom.current().nextInt(1, bestSol.getSolutions().size());
            mutation();

            float newMP;
            if(currentSol.getmP() >= bestSol.getmP()) {
                bestSol = currentSol.copy();
                newMP = bestSol.getmP();
            } else {
                currentSol = bestSol.copy(); // Efface cet essai car il est mauvais
                newMP = bestSol.getmP();
            }
            updateEnergy(oldMP, newMP);
            updateTemp();
            u++;
            System.out.println(energy > temp);
            System.out.println("en " + energy + " t " + temp);
        }
    }

    private void updateTemp () {
        temp *= LAMBDA;
    }

    private void updateEnergy (float oldMP, float newMP) {
        float deltaEn = newMP - oldMP;
        if (deltaEn < 0) {
            energy += deltaEn;
        } else {
            energy += (float) Math.exp(-(deltaEn)/temp);
        }
    }

    private void mutation() {
        List<List<Integer>> sol = currentSol.getSolutions();
        int numCom = ThreadLocalRandom.current().nextInt(0, sol.size());
        int numVert = ThreadLocalRandom.current().nextInt(0, sol.get(numCom).size());

        List<Integer> friends = searchBestFriends(sol.get(numCom).get(numVert), graph.getMatrix());
        int friend = friends.get(ThreadLocalRandom.current().nextInt(0, friends.size()));
        List<Integer> used = new ArrayList<Integer>();

        while(sol.get(numCom).contains(friend) && !used.contains(friend)) {
            used.add(friend);
            friend = friends.get(ThreadLocalRandom.current().nextInt(0, friends.size()));
        }
        for (List<Integer> com2 : sol) {
            if(com2.contains(friend)) {
                com2.remove(getIndexToRemove(com2, friend));
            }
        }
        sol.get(numCom).add(friend);
        for(List<Integer> com : sol) {
            Collections.sort(com);
        }
        currentSol.setSolutions(sol, graph.getEdges());
    }

    private int getIndexToRemove (List<Integer> list, int v) {
        int i = 0;
        while (i < list.size() && list.get(i) != v) {
            i++;
        }
        return i;
    }

    private Solutions generateSolution() {
        int n = graph.getVertices();
        List<Integer> used = new ArrayList<Integer>(n);

        // Tirage aléatoire d'un nombre de communauté (si n communauté => 1 sommet par com => useless
        int numberCom = ThreadLocalRandom.current().nextInt(2, n/2);
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
        List<Integer> com = new ArrayList<Integer>();
        int i=1;
        while(i<n){
            if (!used.contains(i)) {
                com.add(i);
            }
            i++;
        }
        Collections.sort(com);
        solutions.add(com);
        return new Solutions(solutions, graph.getEdges());
    }

    private List<Integer> searchBestFriends(int vertice, Tuple[][] matrix) {
        List<Integer> temp = new ArrayList<Integer>();
        for(int i = 0; i < matrix.length; i++) {
            if (matrix[vertice - 1][i].getLinked() == 1) {
                temp.add(i + 1);
            }
        }
        return temp;
    }
}