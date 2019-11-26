import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

class Graph {
    private int edges;
    private int vertices;
    private List<Integer> head;
    private List<Integer> succ;
    static Tuple[][] matrix;

    Graph(int edges, int vertices, List<Integer> head, List<Integer> succ) {
        this.edges = edges;
        this.vertices = vertices;
        this.head = head;
        this.succ = succ;
        matrix = buildMatrix();
    }

    Tuple[][] buildMatrix() {
        Tuple[][] matr = new Tuple[vertices][vertices];
        // construire une matrives de 0
        for (int i=0; i < matr.length; i++) {
            for (int j=0; j < matr[i].length; j++) {
                matr[i][j] = new Tuple(0);
            }
        }




        // compléter la matrice avec les bons 1
        for(int i = 0; i < head.size()-1; i++) {
            int start = head.get(i)-1;
            int end = head.get(i+1)-1;
            while (start < end) {
                matr[i][succ.get(start)-1].setLinked();
                start++;
            }
        }

        // compléter les probabilités
        for (int i=0; i < matr.length; i++) {
            float di = 0;
            for (int j=0; j < matr[i].length; j++) {
                if(matr[i][j].getLinked() == 1) {
                    di++;
                }
                float dj = 0;
                for(int k=0; k<matr.length; k++) {
                    if(matr[k][j].getLinked() == 1) {
                        dj++;
                    }
                }
                matr[i][j].setProbability((di*dj)/(2*edges));
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

    int getVertices() {
        return this.vertices;
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

    float getProbability() {
        return this.probability;
    }

    void setProbability(float probability) {
        this.probability = roundToN(probability, 3);
    }

    void setLinked() {
        this.linked = 1;
    }

    @Override
    public String toString() {
        return " <" + linked + ";" + probability + "> ";
    }
     private float roundToN(float f, int n) {
        return (float) Math.round(f*Math.pow(10, n))/ (float) Math.pow(10, n);
     }

}
