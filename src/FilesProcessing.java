import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class FilesProcessing {

    static void readFile(String name) {
        try {
            FileInputStream fis = new FileInputStream(new File(name+".txt"));
            InputStreamReader ipsr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(ipsr);
            String line;
            int n = 1;
            int edges = 0;
            int vertices = 0;
            List<Integer> head = new ArrayList<Integer>();
            List<Integer> succ = new ArrayList<Integer>();

            while ((line = br.readLine())!= null){
                StringTokenizer tok = new StringTokenizer(line, " ");
                String x;
                switch (n){
                    case 1 :
                        edges = Integer.parseInt(tok.nextToken());
                        break;
                    case 2 :
                        vertices = Integer.parseInt(tok.nextToken());
                        break;
                    case 3 :
                        while (tok.hasMoreTokens()) {
                            head.add(Integer.parseInt(tok.nextToken()));
                        }
                        break;
                    case 4 :
                        while (tok.hasMoreTokens()) {
                            succ.add(Integer.parseInt(tok.nextToken()));
                        }
                        break;
                }
                n++;
            }
            br.close();
            Graph graph = new Graph(edges, vertices, head, succ);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }
}

