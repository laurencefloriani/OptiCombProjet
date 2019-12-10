import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class FilesProcessing {

    static Graph readFile(String name) {
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
            return new Graph(edges, vertices, head, succ);
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        return null;
    }

    static void writeInFile(Solutions solutions) {
        File f =  new File("Solution.txt");
        String debut = "L'application a trouvé les communautés suivantes : ";
        String after1 = "Avec un M(P) de : " + solutions.getmP();

        try{
            PrintWriter printer = new PrintWriter("Solution.txt");
            printer.println(debut);
            for (List<Integer> com : solutions.getSolutions()) {
                String str = "COM : ";
                for (int edge : com) {
                    str += edge;
                    str += " ";
                }
                str += "\n";
                printer.println(str);
            }
            printer.println(after1);
            printer.close();

        } catch(IOException e){
            System.out.printf("ERROR : %s/n",e);
        }
    }
}

