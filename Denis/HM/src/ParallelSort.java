
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Денис
 */
public class ParallelSort extends Thread {
    private List sortedSubText;
    private TextRedactor ts;
    
    public ParallelSort(int number, String... args) throws IOException {
        ts = new TextRedactor(args);
 
        sortedSubText = ts.getPart(number);
    }

    @Override
    public void run() {
         Collections.sort(sortedSubText);
    }
    
    public List sortedPart() {
        return sortedSubText;
    }
 
}
