/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.util.regex.*;
import java.util.Iterator;

/**
 *
 * @author Kurbat
 */
public class InputParser {
    private boolean iKey = false;
    private boolean uKey = false;
    private boolean tKey = false;
    private boolean oKey = false;
    private int threadCount = 0;
    public static final int DEFAULT_THREAD_COUNT = 2;
    private String fileName = "";
    private List fromFiles = new ArrayList();
    private boolean uiKey = false;
    
    public InputParser (String... args) {
        String all = "";
        for (String s : args) {
            all += s + ' ';
        }
        
        findIU(all);
        findT(all);
        findO(all);
        findOutputFile(all);
        findFrom(all);

 //     show();
    }
    
    private void findIU(String all) {
        Pattern p = Pattern.compile("-((ui)|(iu)|(u)|(i))");
        Matcher m = p.matcher(all);
        if (m.find()) {
            String found = m.group();
            if (found.equals("-ui")) {
                uiKey = true;
                uKey = true;
                iKey = true;
            } else if (found.equals("-u")) {
                uKey = true;
            } else if (found.equals("-i")) {
                iKey = true;
            } else if (found.equals("-iu")) {
                uiKey = false;
                uKey = true;
                iKey = true;
            }
        }
    }
    
    private void findT(String all) {
        Pattern p = Pattern.compile("(-t [0-9]+)|(-t)");
        Matcher m = p.matcher(all);
        if(m.find()) {
            String found = m.group();
            if (found.equals("-t")) {
                tKey = true;
                threadCount = DEFAULT_THREAD_COUNT;
            } else {
                tKey = true;
                threadCount = Integer.parseInt(found.substring(3));
            }
        }
        else {
            threadCount = DEFAULT_THREAD_COUNT;
        }
    }
    
    private void findO(String all) {
        Pattern p = Pattern.compile("-o");
        Matcher m = p.matcher(all);
        if (m.find()) {
            oKey = true;
        }
    }
    
    private void findOutputFile(String all) {
        if (oKey == true) {
            int start = all.indexOf("-o");
            String subAll = all.substring(start+3);
            if (!subAll.isEmpty()) {
                String[] files = subAll.split(" ");
                fileName = files[0];
            }
        }
    }
    
    private void findFrom (String all) {
        String[] subAll = all.split(" ");
        for (int i = subAll.length - 1; i >= 0; i--) {
            if (!subAll[i].equals(fileName) && !subAll.equals(Integer.toString(threadCount))
                    && subAll[i].length() > 3) {
                fromFiles.add(subAll[i]);
            }
        }
    }
    
    public boolean getI() {
        return iKey;
    }
    
    public boolean getU() {
        return uKey;
    }
    
    public boolean getT() {
        return tKey;
    }
    
    public boolean getO() {
        return oKey;
    }
    
    public boolean getUI() {
        return uiKey;
    }
    
    public int getCount() {
        return threadCount;
    }
    
    public String getFile() {
        return fileName;
    }
    
    public List getFromList() {
        return fromFiles;
    }

}
