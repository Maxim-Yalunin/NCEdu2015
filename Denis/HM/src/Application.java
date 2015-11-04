
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Денис
 */
public class Application {
    private List res;
    private InputParser prs;
    
    public Application(String... args) throws IOException, InterruptedException {
        res = new ArrayList();
        prs = new InputParser(args);
        doSort(args);
    }
    
    public void doSort(String... args) throws IOException, InterruptedException {
        TextRedactor ts = new TextRedactor(args);
        ParallelSort[] threads = new ParallelSort[ts.getInputParser().getCount()];
       
        for (int i = 0; i < threads.length; ++ i) {
            threads[i] = new ParallelSort(i, args);
            threads[i].start();
        }
        
        for (int i = 0; i < threads.length; ++ i) {
           threads[i].join();
        }
         
        List start = threads[0].sortedPart();
        List second = new ArrayList();
        List result = new ArrayList();
        for (int j = 1; j < threads.length; ++ j) {
            if (threads[j].sortedPart().size() == 0) {
                continue;
            }
            
            second = threads[j].sortedPart();
            int k = 0;
            int l = 0;
            while(true) {

                String firstCurr = (String) start.get(k);
                String secondCurr = (String) second.get(l);

                if (firstCurr.compareTo(secondCurr) < 0) {
                    result.add(firstCurr);
                    ++ k;
                } else {
                    result.add(secondCurr);
                    ++ l;
                }

                if (k == start.size() && l != second.size()) {
                    for (int i = l; i < second.size(); ++ i) {
                        result.add(second.get(i));
                    }
                    break;
                } else if (l == second.size() && k != start.size()) {
                    for (int i = k; i < start.size(); ++ i) {
                        result.add(start.get(i));
                    }
                    break;
                } else if (k == start.size() && l == second.size()) {
                    break;
                }

            }
            
            start = new ArrayList(result);
            if (j != threads.length - 1) {
                result.clear();
            }
           
        }
        
        res = result;
    }
    
    public void output() throws FileNotFoundException, IOException {
        if (prs.getO()) {
            OutputStream fos = new FileOutputStream (prs.getFile());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < res.size(); ++ i) {
                sb.append((String)res.get(i) + "\n");
            }
            String text = new String(sb);
            byte[] buff = text.getBytes();
            fos.write(buff);
        } else {
            for (int i = 0; i < res.size(); ++ i) {
                System.out.println(res.get(i));
            }
        }
    }
    
    
    public static void main (String... args) throws IOException, InterruptedException {
        Application app = new Application(new String[] {"-t","3", "-o", "D:\\java\\textTo.txt", "D:\\java\\wp.txt"});
        app.output();
        
    }
}
