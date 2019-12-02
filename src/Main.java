import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        Graph graph = FilesProcessing.readFile("assets/instance0");
        graph.printMatrix();

        MockAnnealing mock = new MockAnnealing(50);
        Solutions sol = mock.initSolutions();
        System.out.println(sol.toString());
    }
}
