import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        Graph graph = FilesProcessing.readFile("assets/instance0");
        graph.printMatrix();

        int n = Graph.getVertices();

        Solutions sol = MockAnnealing.initSolutions();
        for(List<Integer> ll : sol.getSolutions()) {
            System.out.print("COM : ");
            for (int i : ll) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }
}
