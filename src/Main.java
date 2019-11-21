import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Graph graph = FilesProcessing.readFile("assets/instance1");
        graph.buildMatrix();
        graph.printMatrix();
    }
}
