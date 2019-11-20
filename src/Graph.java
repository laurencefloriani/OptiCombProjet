import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

class Graph {
    private int edges;
    private int vertices;
    private List<Integer> head;
    private List<Integer> succ;
    private Tuple[][] matrix;

    Graph(int edges, int vertices, List<Integer> head, List<Integer> succ) {
        this.edges = edges;
        this.vertices = vertices;
        this.head = head;
        this.succ = succ;
    }

    void buildMatrix() {
        // construire une matrives de 0
        matrix =  new Tuple[vertices][vertices];
        for (int i=0; i < matrix.length; i++) {
            for (int j=0; j < matrix[i].length; j++) {
                matrix[i][j] = new Tuple(0);
            }
        }




        // compléter la matrice avec les bons 1
        for(int i = 0; i < head.size()-1; i++) {
            int start = head.get(i)-1;
            int end = head.get(i+1)-1;
            while (start < end) {
                matrix[i][succ.get(start)-1].setLinked();
                start++;
            }
        }

        // compléter les probabilités
        for (int i=0; i < matrix.length; i++) {
            float di = 0;
            for (int j=0; j < matrix[i].length; j++) {
                if(matrix[i][j].getLinked() == 1) {
                    di++;
                }
                float dj = 0;
                for(int k=0; k<matrix.length; k++) {
                    if(matrix[k][j].getLinked() == 1) {
                        dj++;
                    }
                }
                matrix[i][j].setProbability((di*dj)/(2*edges));
            }
        }
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
    private float probability;

    Tuple(int linked) {
        this.linked = linked;
        probability = 0;
    }

    int getLinked() {
        return linked;
    }

    void setProbability(float probability) {
        this.probability = probability;
    }

    void setLinked() {
        this.linked = 1;
    }

    @Override
    public String toString() {
        return "<" + linked + ";" + probability + ">";
    }
}
