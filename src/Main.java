import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Graph graph = FilesProcessing.readFile("assets/instance0");
        graph.printMatrix();
        List <List<Integer>> list = new ArrayList<List<Integer>>();
        List <Integer> l = new ArrayList<>();
        l.add(1);
        l.add(2);
        l.add(3);
        list.add(l);

        List <Integer> l2 = new ArrayList<>();
        l2.add(4);
        l2.add(5);
        l2.add(6);
        list.add(l2);
        System.out.println(Simulation.computeM(list, graph.getVertices()));
    }
}
