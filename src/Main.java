import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        Graph graph = FilesProcessing.readFile("assets/instance1");
        graph.printMatrix();
        graph.getBestNodes();
        ArrayList<Integer[]> temporaire =  new ArrayList<>();
        ArrayList<Integer> talion= new ArrayList<>();
        for (int i = 0; i < graph.bestNodes.size() ; i++){
            temporaire.add(graph.bestNodes.get(i));

        }


        // ArrayList<Integer[]> tempo =  graph.makeall(talion)/*graph.completeCommunity(temporaire)*/;
      /*  for(Integer [] t : tempo){
            System.out.println("<"+t[0]+","+ t[1]+">");
        }*/
        graph.tries(temporaire);

        //MockAnnealing mock = new MockAnnealing(50);
        //System.out.println(sol.toString());
        //System.out.println(sol.getmP());
    }
}
