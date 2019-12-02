import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

class Graph {
    private static int edges;
    private static int vertices;
    private List<Integer> head;
    private List<Integer> succ;
    static Tuple[][] matrix;

    Graph(int edges1, int vertices1, List<Integer> head, List<Integer> succ) {
        edges = edges1;
        vertices = vertices1;
        this.head = head;
        this.succ = succ;
        matrix = buildMatrix();
    }

    private Tuple[][] buildMatrix() {
        Tuple[][] matr = new Tuple[vertices][vertices];
        // construire une matrices de 0
        for (int i=0; i < matr.length; i++) {
            for (int j=0; j < matr[i].length; j++) {
                matr[i][j] = new Tuple(0);
            }
        }

        // compléter la matrice avec les 1 aux bons endroits
        for(int i = 0; i < head.size()-1; i++) {
            int start = head.get(i)-1;
            int end = head.get(i+1)-1;
            while (start < end) {
                matr[i][succ.get(start)-1].setLinked();
                start++;
            }
        }

        // Compléter la matrice avec les probabilités
        for (int i=0; i<matr.length; i++) {
            for (int j=0; j<matr.length; j++) {
                int di = 0;
                int dj = 0;
                for (int k=0; k<matr.length; k++) {
                    di += matr[i][k].getLinked();
                    dj += matr[k][j].getLinked();
                }
                matr[i][j].setProbability(di*dj);
            }
        }
        return matr;
    }

    void setEdges(int edges) {
        this.edges = edges;
    }

    void setVertices(int vertices) {
        this.vertices = vertices;
    }

    public void addInHead(int x) {
        this.head.add(x);
    }

    public void addInSucc(int x) {
        this.succ.add(x);
    }

    static int getVertices() {
        return vertices;
    }

    static int getEdges() {
        return edges;
    }

    void printMatrix() {
        for (int i=0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
    }
}

class Tuple{
    private int linked;
    private int probability; // entier pour éviter les erreurs d'arrondi dû a la division

    Tuple(int linked) {
        this.linked = linked;
        probability = 0;
    }

    int getLinked() {
        return linked;
    }

    float getProbability() {
        return this.probability;
    }

    void setProbability(int probability) {
        this.probability = probability;
    }

    void setLinked() {
        this.linked = 1;
    }

    @Override
    public String toString() {
        return " <" + linked + ";" + probability + "> ";
    }

}
