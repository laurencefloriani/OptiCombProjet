import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        FilesProcessing.readFile("assets/instance1");
        List<Integer> head = new ArrayList<>();
        head.add(1);
        head.add(3);
        head.add(5);
        head.add(8);
        head.add(11);
        head.add(13);
        head.add(15);
        List<Integer> succ = new ArrayList<>();
        succ.add(2);
        succ.add(3);
        succ.add(1);
        succ.add(3);
        succ.add(1);
        succ.add(2);
        succ.add(4);
        succ.add(3);
        succ.add(5);
        succ.add(6);
        succ.add(4);
        succ.add(6);
        succ.add(4);
        succ.add(5);
        Graph graph = new Graph(14, 6, head, succ);
        graph.buildMatrix();
        graph.printMatrix();
    }
}
