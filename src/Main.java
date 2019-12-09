import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        Graph graph = FilesProcessing.readFile("assets/instance1");
        graph.printMatrix();

        MockAnnealing mock = new MockAnnealing(50,"assets/instance1");
        //System.out.println(sol.toString());
        //System.out.println(sol.getmP());
    }
}
