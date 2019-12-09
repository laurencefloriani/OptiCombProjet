import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MockAnnealing {
    private Graph graph;
    private int initTemp;
    private float MPTRESHOLD = 0.357f;
    private Solutions bestSol;
    private Solutions currentSol;

    public MockAnnealing(int initTemp, String name) {
        this.initTemp = initTemp;
        this.graph =  FilesProcessing.readFile(name);
        start();
    }

    private void start() {
        bestSol = generateSolution();
        currentSol = bestSol;

        while(bestSol.getmP() < MPTRESHOLD) {
            mutation();
            System.out.println(bestSol.toString() + " M(P) : " + bestSol.getmP());
        }
    }





    private void mutation() {
        List<List<Integer>> sol = currentSol.getSolutions();
        int numCom = ThreadLocalRandom.current().nextInt(0, sol.size());
        int numVert = ThreadLocalRandom.current().nextInt(0, sol.get(numCom).size());

        List<Integer> friends = searchBestFriend(sol.get(numCom).get(numVert), graph.getMatrix(), true);
        int friend = friends.get(ThreadLocalRandom.current().nextInt(0, friends.size()));
        while(sol.get(numCom).contains(friend)) {
            friend = friends.get(ThreadLocalRandom.current().nextInt(0, friends.size()));
        }
        for (List<Integer> com2 : sol) {
            if(com2.contains(friend)) {
                System.out.println(friend);
                if(com2.size() == 1) {
                    sol.remove(com2);
                } else {
                    com2.remove(getIndexToRemove(com2, friend));
                }
            }
        }
        sol.get(numCom).add(friend);
        currentSol.setSolutions(sol, graph.getEdges());
    }

    private int getIndexToRemove (List<Integer> list, int v) {
        int i = 0;
        while (i < list.size() && list.get(i) != v) {
            i++;
        }
        return i;
    }
    //TODO faire en sorte que tous les sommets soient au moins dans une communauté
    private Solutions generateSolution() {
        int n = graph.getVertices();
        List<Integer> used = new ArrayList<Integer>(n);

        // Tirage aléatoire d'un nombre de communauté (si n communauté => 1 sommet par com => useless
        System.out.println(n);
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
        System.out.println("used:"+used);

        /*for (List f : solutions){

            System.out.println("taille com fin"+f.size());
        }*/

        Solutions sol = new Solutions(solutions, graph.getEdges());
        return sol;
    }

    private List<Integer> searchBestFriend(int vertice, Tuple[][] matrix, boolean bool) {
        List<Integer> temp = new ArrayList<Integer>();
        for(int i = 0; i < matrix.length; i++) {
            if(bool) {
                if (matrix[vertice - 1][i].getLinked() == 1) {
                    temp.add(i + 1);
                }
            } else {
                if (matrix[vertice - 1][i].getLinked() == 0) {
                    temp.add(i + 1);
                }
            }
        }
        return temp;
    }


    private void decraeseNbrCom(Solutions solutions) {
        List<List<Integer>> list = solutions.getSolutions();
        if(list.size() > 1) {
            int newNbreCom = ThreadLocalRandom.current().nextInt(0, list.size());
            while (list.size() != newNbreCom) {
                int indexCom1 = ThreadLocalRandom.current().nextInt(0, list.size()-1);
                int indexCom2 = ThreadLocalRandom.current().nextInt(0, list.size()-1);

                List<Integer> com = list.get(indexCom1);
                com.addAll(list.get(indexCom2));

                list.remove(indexCom1);
                list.remove(indexCom2);

                Collections.sort(com);
                list.add(com);
            }
        }
    }

}