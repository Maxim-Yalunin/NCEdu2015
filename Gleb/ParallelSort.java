import java.io.*;
import java.util.*;

class Reader extends Thread{//reader.hasEnded=>всё, слов больше не станет
	public boolean isWorking;
	public String stringToInput;
	public String stringToOutput;
	public List<String> words;
	String buffer=new String();
	public int amount=0;
	
	public Reader(String input, String output){
		this.stringToInput=input;
		this.stringToOutput=output;
		this.words=Collections.synchronizedList(new ArrayList<String>());
		
	}
	
	
	public void readFile(){	
	FileInputStream fis = null;
	try {
		fis = new FileInputStream(this.stringToInput);
	} catch (FileNotFoundException e) {
		System.out.println("Can't locate input file");
		this.isWorking=false;
	}
	
	Scanner scanner = new Scanner(fis, "UTF-8");
	this.isWorking=true;
	while(scanner.hasNext()){
		words.add(scanner.next());
		amount+=1;
	}
	System.out.println("That was my last word");
	scanner.close();
	this.isWorking=false;
	}
	
	void readConsole(){	
		Scanner scanner = new Scanner(System.in);
		this.isWorking=true;
		System.out.println("Remember, the stopword is THEEND");
		while(scanner.hasNext()){
			buffer=scanner.next();
			if(buffer.equals("THEEND")){
			break;
			}
			words.add(buffer);
			amount+=1;
			
		}
		System.out.println("\n So let it be");
		scanner.close();
		this.isWorking=false;
		}
	
	public void writeToFile(String strg, List<String> list){
		 Writer writer = null;
	        try {
	            writer = new FileWriter(strg);
	            for (String line : list) {
	                writer.write(line);
	                writer.write(System.getProperty("line.separator"));
	            }
	            writer.flush();
	        } catch (Exception e) {
	           System.out.println("Can't write to "+strg);
	        } finally {
	            if (writer != null) {
	                try {
	                    writer.close();
	                } catch (IOException ex) {
	                }
	            }
	        }
	}
	
	public boolean hasNoMoreWords(){
		if(this.isWorking==false&&this.words.size()==0){
			return true;}
		return false;
	}
	
	public void run(){
	if(this.stringToInput==null){
	this.readConsole();
	}
	else{
	this.readFile();
		}
	}
	
}

	class Sorter extends Thread{
		
public List<String> subFile;
private Reader reader;
public boolean parameter_u;
public boolean parameter_i;
public boolean isWorking;
public boolean hasEnded=false;

public Sorter(Reader read, String nam, boolean param_u, boolean param_i){
this.reader=read;
this.subFile=new ArrayList<String>();//Можно было попробовать сет, в случае u
this.setName(nam);
this.parameter_u=param_u;
this.parameter_i=param_i;
}

public void deleteDoubledFromSublist(){
for(int i=0;i<this.subFile.size()-1;i++){
	if(this.parameter_i==true&&this.subFile.get(i).compareToIgnoreCase(this.subFile.get(i+1))==0){
		this.subFile.remove(i+1);
		i-=1;
	}
	if(this.parameter_i==false&&this.subFile.get(i).compareTo(this.subFile.get(i+1))==0){
		this.subFile.remove(i+1);
	}
	}
}
@Override
public void run(){
String buff;

if(this.reader.hasNoMoreWords()){//Проверка, не поздно ли был создан тред
	this.isWorking=false;
	this.hasEnded=true;
	this.interrupt();
}

while(true){
		if(this.reader.words.size()>0){
		try{
		buff=this.reader.words.remove(0);
		this.subFile.add(buff);
		this.isWorking=true;
		}
		catch(IndexOutOfBoundsException e){
			this.isWorking=false;
			if(this.reader.hasNoMoreWords()){
				this.hasEnded=true;
				break;
			}
			}
}	
else{
if(!this.reader.hasNoMoreWords()){
	this.isWorking=false;
	try {
		this.sleep(100);
	} catch (InterruptedException e) {
	System.out.println("No rest for the wicked");
	}
} else{
	Collections.sort(this.subFile, new SimpleComparator(this.parameter_i));
	if(this.parameter_u==true){
	this.deleteDoubledFromSublist();
	}
	this.isWorking=false;
	this.hasEnded=true;
	break;
}

}
		
}//Конец вечного цикла
}

}

class SimpleComparator implements Comparator<String> {
boolean ignore_case;
SimpleComparator(boolean i_c){
this.ignore_case=i_c;
}
@Override
public int compare(String arg0, String arg1){
	if(this.ignore_case) return arg0.compareToIgnoreCase(arg1);
	else return arg0.compareTo(arg1);
}
}


public class ParallelSort {
	private boolean param_i=false;
	private boolean param_u=false;
	private int param_t=3;
	private String param_o=null;
	private String param_f=null;
	
	public void getParams(String[] arg){
	for(int i=0;i<arg.length;i++){
		
		if(arg[i].equals("-u")){
			this.param_u=true;
		}
		if(arg[i].equals("-i")){
			this.param_i=true;
		}	
		if(arg[i].equals("-iu")||arg[i].equals("ui")){
			this.param_u=true;
			this.param_i=true;
		}
	
		if(arg[i].equals("-t")){
			this.param_t=Integer.parseInt(arg[i+1]);
		}
		if(arg[i].equals("-o")){
			this.param_o=new String(arg[i+1]);
		}
		if(arg[i].equals("-f")){
			this.param_f=new String(arg[i+1]);
		}
	}
	}
	
	private boolean workEnded(List<Sorter> list){
for(int i=0;i<list.size();i++){
	if(list.get(i).hasEnded==false){
		return false;
	}
}
return true;
	}
	
	private static void mergeTwoLists(List<String> a, List<String> b, boolean i_c){
		a.addAll(b);
		Collections.sort(a, new SimpleComparator(i_c));
	}
	
	private static void mergedList2(List<Sorter> list){//Медленно, но верно
		for(int i=1;i<list.size();i++){
			mergeTwoLists(list.get(0).subFile, list.get(i).subFile,list.get(0).parameter_i);
		}
	}
	
	private void deleteDoubled(List<String> list){
		for(int i=0;i<list.size()-1;i++){
		if(this.param_u==true&&this.param_i==true&&list.get(i).compareToIgnoreCase(list.get(i+1))==0){
			list.remove(i+1);
			i-=1;
		}
		if(this.param_u==true&&this.param_i==false&&list.get(i).compareTo(list.get(i+1))==0){
			list.remove(i+1);
			i-=1;
		}
		}
	}

	private void processResult(List<String> list, Reader r){
	if(this.param_u==true){
		System.out.println("Started to delete doubled strings");
		this.deleteDoubled(list);
	}
	if(this.param_o==null){
	System.out.println(list);
	}
	else{
		System.out.println("Started to save result to "+this.param_o);
		r.writeToFile(this.param_o, list);
	}
	}
	
	public ParallelSort(String[] argmnts){
		this.getParams(argmnts);
	    System.out.println("parameter_o="+this.param_o+" parameter_f="+this.param_f+" parameter_i ="+this.param_i+" parameter_u ="+this.param_u+" parameter_t="+this.param_t);
		List<Sorter> workers=new ArrayList<Sorter>();
		Reader readr=new Reader(this.param_f,this.param_o);
		readr.start();
		while(true){
		if(workers.size()==0&&readr.words.size()>0||(readr.words.size()>readr.amount/(10*workers.size()+1)&&workers.size()<this.param_t)){
		Sorter sortr=new Sorter(readr, "Worker"+workers.size(), this.param_u, this.param_i);
		sortr.start();
		workers.add(workers.size(), sortr);
		}
		if(workers.size()>0&&readr.hasNoMoreWords()&&workEnded(workers)){
			int size=0;
			for(int q=0;q<workers.size();q++){
				size+=workers.get(q).subFile.size();
			}
			
			break;
		}
		}
		
		
		mergedList2(workers);
		
		processResult(workers.get(0).subFile, readr);
		
		System.out.println("The end.");
	}	
	
	public static void main(String[] args) throws InterruptedException{
			ParallelSort p=new ParallelSort(args);
			
		}
	}
