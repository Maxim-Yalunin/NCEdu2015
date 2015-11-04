/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Денис
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Set.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
/**
 *
 * @author Kurbat
 */
/* Returned input text from stdin or files
 */
public class TextRedactor { 
    private boolean fromFiles;
    private InputParser inp;
    private String inputText;
    private List<String> splittedText;
    private List<String>[] partOfText;
  
    public TextRedactor (String... args) throws IOException {
        inputText = "";
        inp = new InputParser (args);
        
        if (inp.getFromList().isEmpty()) {
            fromFiles = false;
        } else {
            fromFiles = true;
        }
 
        getInputText();
        getInputArray();
        checkInputArray();
        divideInputArray();

    }
    
    private void checkInputArray() {
        if (inp.getUI()) {  // -ui
            uiFound();
           
        } else if (!inp.getUI() && inp.getI() && inp.getU()) { //-iu
            iuFound();
            
        } else if (inp.getI() && !inp.getU()) { //-i
            iFound();
            
        } else if (!inp.getI() && inp.getU()) { //-u
            uFound(); 
        }
    }

    private void getFromStdin() {
        Scanner sc = new Scanner(System.in);
        inputText = sc.nextLine();
    }
    
    private void getFromFiles() throws FileNotFoundException, IOException {
        String[] infoFromFiles = new String[inp.getFromList().size()];
        
        InputStreamReader[] ifss = new InputStreamReader[infoFromFiles.length]; //new FileInputStream[infoFromFiles.length];
        for (int i = 0; i < ifss.length; ++ i) {
            InputStream tr = new FileInputStream ((String)inp.getFromList().get(ifss.length - 1 - i));
            ifss[i] =  new InputStreamReader(new FileInputStream((String)inp.getFromList().get(ifss.length - 1 - i)));
            char[] buff = new char[tr.available()];
            ifss[i].read(buff);
            infoFromFiles[i] = new String(buff);
            
        }
        StringBuffer sb = new StringBuffer();
 
        for (int i = 0; i < infoFromFiles.length; ++ i) {
            sb.append(infoFromFiles[i] + " ");
        }
        inputText = new String(sb);
    }
    
    public String getInputText() throws FileNotFoundException, IOException {
        if (fromFiles == true) {
            getFromFiles();
        } else {
            getFromStdin();
        }
        return inputText;
    }
    
    public InputParser getInputParser() {
        return inp;
    }
    
    public List getInputArray() throws IOException { 
        String[] splitted = inputText.split("\\s+");
        splittedText = Arrays.asList(splitted);
        return splittedText;
    }
    
    private void divideInputArray() throws IOException {
        partOfText = new List[inp.getCount()];
        
        for (int i = 0; i < partOfText.length; ++ i) {
            partOfText[i] = new ArrayList();
        }
        int threads = partOfText.length;
        for (int i = 0; i < threads; ++ i) {
            int start = splittedText.size()*i/threads;
            int finish = splittedText.size()*(i+1)/threads;
            List subList = new ArrayList();
            for (int j = start; j < finish; ++ j) {
                subList.add(splittedText.get(j));
            }
            partOfText[i].addAll(subList);
        } 
    }
    
    public List getPart (int i) throws IOException {
        return partOfText[i];
    }
    
    public String getPartString (int i) throws IOException {
        List sub = getPart(i);
        String subString = "";
        for (int j = 0; j < sub.size(); ++ j) {
            subString += (String)sub.get(j) + ' '; 
        }
        return subString;
    }


    private void uFound() {
        Set st = new HashSet(splittedText);
        List res = new ArrayList(st);
        splittedText = res;
    }

    private void iFound() {
        List res = new ArrayList();
        for (int i = 0; i < splittedText.size(); ++ i) {
            res.add(splittedText.get(i).toLowerCase());
        }
        splittedText = res;
    }

    private void iuFound() {
        iFound();
        Set st = new HashSet(splittedText);
        List res = new ArrayList(st);
        splittedText = res;
    }

    private void uiFound() {
        uFound();
        List res = new ArrayList();
        for (int i = 0; i < splittedText.size(); ++ i) {
            res.add(splittedText.get(i).toLowerCase());
        }
        splittedText = res;
    }
    
}
