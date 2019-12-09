import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

class Graph {
    private int edges;
    private int vertices;
    private List<Integer> head;
    private List<Integer> succ;
    static Tuple[][] matrix;
    private float average;
    private List<Integer[]> bestBows;

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
                int proba = di*dj;
                this.average = (this.average + proba)/2;
                matr[i][j].setProbability(proba);
            }
        }
        return matr;
    }

    void setEdges(int e) {
        edges = e;
    }

    void setVertices(int v) {
        vertices = v;
    }

    private void addInHead(int x) {
        this.head.add(x);
    }

    private void addInSucc(int x) {
        this.succ.add(x);
    }

    int getVertices() {
        return vertices;
    }

    int getEdges() {
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

    Tuple[][] getMatrix() {
        return this.matrix;
    }
/**
    private void searchBestBows(){
        // Methode pour selectionner les elements les plus importants de la matrice du point de vu de notre raisonnement
        for (int i = 0 ; i< matrix.length; i++){
            for (int j = 0 ; j< matrix.length; j++){
                if(matrix[i][j].getLinked() ==1 && matrix[i][j].getProbability() <= average){
                    bestBows.add(new Integer[] {i,j});
                }
            }
        }
    }

    private  ArrayList<Integer[]> completeCommunity(ArrayList<Integer[]> indices){
        ArrayList<Integer[]> temp =  new ArrayList<Integer[]>();
        ArrayList<Integer[]> compare = indices; // REDONDANT INUTILE CAR PAS MAJ

        for (Integer[] indice : indices){
            temp.add(indice);
            for(Integer[] a : compare){
                if(indice != a){
                    Integer [] tempo = new Integer []{indice[0], a[1]};
                    if(!temp.contains(tempo)) {
                        temp.add(tempo);
                    }
                    //  temp.add(tempo);
                }
            }
            for(Integer[] a : compare){
                if(indice != a){
                    Integer [] tempo = new Integer []{indice[1], a[0]};
                    if(!temp.contains(tempo)) {
                        temp.add(tempo);
                    }
                }
            }
        }
        return temp;
    }

    private List<Integer> findNode(List<Integer[]> array ){
        ArrayList<Integer> result = new ArrayList<>();
        for (Integer [] t: array ) {
            if(!result.contains(t[0])){
                result.add(t[0]);
            }
            if(!result.contains(t[1])){
                result.add(t[1]);
            }
        }
        return result ;
    }

    float computeM(ArrayList<Integer[]> array){
        float temp = 0;
        int m = Graph.getEdges();
        for (Integer [] t :array) {
            temp += matrix[t[0]][t[1]].getLinked() - matrix[t[0]][t[1]].getProbability()/(2*m);
        }

        System.out.println("m = " + m);
        return temp/(2*m);
    }

    public void tries(ArrayList<Integer[]> array){
        int n = 5+6;
        ArrayList<Integer[]> tocompute =  new ArrayList<>();
        for (int i =8; i< n ; i++){
            tocompute.add(array.get(i));
        }
        List<Integer> result =  findNode(tocompute);
        tocompute.clear();
        tocompute = makeall(result);
        float reponse = computeM(tocompute);
        System.out.println("Mp de la partition selectionnée est " + reponse );
    }

    private ArrayList<Integer[]> makeall(ArrayList<Integer > array){
        ArrayList<Integer > liste = array;
        ArrayList<Integer[] > tempip = new ArrayList<>();
        for(Integer entier : array){
            for(Integer i : liste){
                Integer [] temp = new Integer []{entier , i};
                if(!tempip.contains(temp)){
                    tempip.add(temp);
                }
            }
        }
        return tempip;

    }
 **/
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
