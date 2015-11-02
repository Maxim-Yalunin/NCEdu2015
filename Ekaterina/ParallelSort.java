package ru.ncedu.java.tasks;

import java.io.*;
import java.util.*;

/**
 * Created by Sony on 02.11.2015.
 */
public class ParallelSort {
    private String[] args;
    private int numberOfThreads = 1;
    private boolean onlyUnique = false;
    private boolean caseInsensitive = false;
    private String output = "";
    private String fileName = "";

    public ParallelSort(){};
    public ParallelSort(String[] r){
        this.args = r;
    }

    //parse java ParallelSort [-iu] [-t THREAD_COUNT] [-o OUTPUT] [FILES...]
    void sortFile(){
        if (args.length == 0) return;
        else {
            int i = 0;
            int last_position = 0;
            while (i != args.length && output == ""){
                if (args[i].equals("-i")) {
                    caseInsensitive = true;
                    last_position = i;
                }
                if (args[i].equals("-u")) {
                    onlyUnique = true;
                    last_position = i;
                }
                if (args[i].equals("-iu") || args[i].equals("-ui")) {
                    caseInsensitive = onlyUnique = true;
                    last_position = i;
                }
                if (args[i].equals("-t")) {
                    numberOfThreads = Integer.parseInt(args[i + 1]);
                    last_position = i+1;
                }
                if (args[i].equals("-o")) {
                    output = args[i+1];
                    last_position = i+1;
                }
                i++;
            }
            if (last_position != args.length-1)
                fileName = args[last_position + 1];
            BufferedReader br = null;
            if (fileName != ""){
                try {
                    br = new BufferedReader(new InputStreamReader (new FileInputStream(fileName), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            else {
                br = new BufferedReader(new InputStreamReader(System.in));
            }
            String s;
            SortedSet<String> res_Set = new TreeSet<>();
            SortedSet<String> res_Set_ins = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            List<String> res_List = new ArrayList<>();
            try {
                while ((s = br.readLine()) != null) {
                    if (onlyUnique && caseInsensitive) res_Set_ins.add(s);
                    if (onlyUnique && !caseInsensitive) res_Set.add(s);
                    if (!onlyUnique) {
                        res_List.add(s);
                        if (caseInsensitive)
                            Collections.sort(res_List, String.CASE_INSENSITIVE_ORDER);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (output != "") {
                BufferedWriter writer = null;
                try {
                    writer = new BufferedWriter(new OutputStreamWriter (new FileOutputStream(output), "UTF-8"));
                    if (res_List.size() != 0) {
                        for (String str : res_List) {
                            writer.write(str);
                            writer.newLine();
                        }
                        writer.flush();
                        writer.close();
                    }
                    if (res_Set.size() != 0) {
                        for (String str : res_Set) {
                            writer.write(str);
                            writer.newLine();
                        }
                        writer.flush();
                        writer.close();
                    }
                    if (res_Set_ins.size() != 0) {
                        for (String str : res_Set_ins) {
                            writer.write(str);
                            writer.newLine();
                        }
                        writer.flush();
                        writer.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            else {
                if (res_List.size() != 0) {
                    for (String str : res_List) {
                        System.out.println(str);
                    }
                }
                if (res_Set.size() != 0) {
                    for (String str : res_Set) {
                        System.out.println(str);
                    }
                }
                if (res_Set_ins.size() != 0) {
                    for (String str : res_Set_ins) {
                        System.out.println(str);
                    }
                }
            }
        }
    }
}
