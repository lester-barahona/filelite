/**
 *Clase Ejemplo crud para manejo de archivos "Ficheros de Texto" 
 * @author Lester Barahona Aguirre
 * @version 1.0
 */

import java.io.*;
import java.util.*;

public class FileLite{
  
  
  private FileReader fReader; 
  private BufferedReader bReader;
  
  private FileWriter fWriter;
  private BufferedWriter bWriter;
  private PrintWriter writer;
  
  private String path="file.txt"; 
  public  String separator="\t";
 

  public FileLite(){}
  
  public FileLite(String path){
      setPath(path);
  }

  public FileLite(String path,String separator){
  setPath(path);
  setSeparator(separator);
  }
  
  public void startReader(){
    try{
      fReader =new FileReader(path);
      bReader=new BufferedReader(fReader);
    }catch(IOException e){
      System.err.println("Error to start the Reader: "+e.getMessage());
    }
  }
  
  public void startWriter(boolean append){
     try{
      fWriter =new FileWriter(path,append);
      bWriter=new BufferedWriter(fWriter);
      writer=new PrintWriter(bWriter);
    }catch(IOException e){
      System.err.println("Error to Start the Writer: "+e.getMessage());
    }
  }

//------------------------------------------------------------------------------------------------WRITE
  public void writeLine(String text,boolean append){
    try{
      startWriter(append);
      writer.println(text);
      writer.flush();
      bWriter.close();
    }catch(IOException e){
      System.err.println("Error to write in the file: "+e.getMessage());
    }
  }
  
   public void writeLines(String[] lines,boolean append){
    try{
      startWriter(append);
      for(String line:lines){
        writer.println(line);
      }
      writer.flush();
      bWriter.close();
    }catch(IOException e){
      System.err.println("Error to write in the file: "+e.getMessage());
    }
  }
   
   public void writeArrayLine(String[] arrayLine,boolean append){
    try{
      startWriter(append);
      writer.println(String.join(getSeparator(), arrayLine));
      writer.flush();
      bWriter.close();
    }catch(IOException e){
      System.err.println("Error to write in the file: "+e.getMessage());
    }
  } 
  
    public void writeArrayLines(String[][] arrayLines,boolean append){
    try{
      startWriter(append);
      for(String[] arrayLine:arrayLines){
        writer.println(String.join(getSeparator(), arrayLine));
      }
      writer.flush();
      bWriter.close();
    }catch(IOException e){
      System.err.println("Error to write in the file: "+e.getMessage());
    }
  }
  
  //-------------------------------------------------------------------------------------------READ

  public String readFile(){
    String tmp="";
    String output="";
    try{
      startReader();
      while((tmp=bReader.readLine())!=null){
        output+=tmp+"\n";   
      }
      bReader.close();
    }catch(IOException e){
      System.err.println("Error to Read  the file: "+e.getMessage());
    }
    return output;
  }
    
  public String[] readLines(){
    return readFile().split("\n");  
  }
  
  public String readLine(int line){
    return readLines()[line];
  }
  
  public String[] readArrayLine(int line){
    return readLine(line).split(getSeparator());
  }
  
  public String[][] readArrayLines(){
    String [] lines=readLines();
    String[][] arrayLines=new String[lines.length][];
    for(int i=0;i<arrayLines.length;i++){
      arrayLines[i]=lines[i].split(getSeparator());
    }
    return arrayLines;
  }
  
  public String readLastLine(){
    String[] lines=readLines(); 
    return lines[lines.length-1];
  }
  
   public String[] readLastArrayLine(){
    return readLastLine().split(getSeparator());
  }
 //------------------------------------------------------------------------FindMethods
   //One
  public String findLine(int section,String data){
    String[] lines=readLines();
    String findLine="";
    for(String line:lines){
      if(line.split(getSeparator())[section].equals(data)){
        findLine=line;
        break;
      }
    }
    return findLine;
  }
  
    public String[] findArrayLine(int section,String data){
    String[] findArrayLine=null;
    for(String line:readLines()){
        String[] sections=line.split(getSeparator());
        if(sections[section].equals(data)){
          findArrayLine=sections;
          break;
        }
    }
    return findArrayLine;
  }
    
  //multi
  public String findLines(int section,String data){
    String[] lines=readLines();
    String findLines="";
    for(String line:lines){
      if(line.split(getSeparator())[section].equals(data)){
        findLines+=line+"\n";
      }
    }
    return findLines;
  }
  
   public String[][] findArrayLines(int section,String data){
    String[] arrayLines=findLines(section,data).split("\n");
    String[][] findArrayLines=new String[arrayLines.length][];
    for(int i=0;i<findArrayLines.length;i++){
      findArrayLines[i]=arrayLines[i].split(getSeparator());
    }
    return findArrayLines;
  }
  
 //------------------------------------------------------------------------REMOVE
   
   public void removeLine(int line){
     ArrayList<String> list = new ArrayList<>(Arrays.asList(readLines()));
     list.remove(line);
     writeLines(list.toArray(new String[list.size()]),false);
   }
   
   public void removeLine(int section,String data){
     ArrayList<String> list = new ArrayList<>(Arrays.asList(readLines()));
     for(int i=0;i<list.size();i++){
       String[] sections=list.get(i).split(getSeparator());
       if(sections[section].equals(data)){
         list.remove(i);
         break;
       }
     }
     writeLines(list.toArray(new String[list.size()]),false);
   }
   
    public void removeLines(int section,String data){
     ArrayList<String> list = new ArrayList<>(Arrays.asList(readLines()));
     for(int i=0;i<list.size();i++){
       String[] sections=list.get(i).split(getSeparator());
       if(sections[section].equals(data)){
         list.remove(i);
       }
     }
     writeLines(list.toArray(new String[list.size()]),false);
   }
 //------------------------------------------------------------------------UPDATE
    public void updateLine(int line,String data){
     ArrayList<String> list = new ArrayList<>(Arrays.asList(readLines()));
     list.set(line,data);
     writeLines(list.toArray(new String[list.size()]),false);
   }
    
    public void updateLine(int section,String idData, String newData){
    ArrayList<String> list = new ArrayList<>(Arrays.asList(readLines()));
    for(int i=0;i<list.size();i++){
      String[] sections=list.get(i).split(getSeparator());
      if(sections[section].equals(idData)){
        sections=newData.split(getSeparator());
        list.set(i,String.join(getSeparator(),sections));
      }
    }
     writeLines(list.toArray(new String[list.size()]),false);
   }
    
    public void updateSectionLine(int section,String idData,int sectionUpdate, String newData){
    ArrayList<String> list = new ArrayList<>(Arrays.asList(readLines()));
    for(int i=0;i<list.size();i++){
      String[] sections=list.get(i).split(getSeparator());
      if(sections[section].equals(idData)){
        sections[sectionUpdate]=newData;
        list.set(i,String.join(getSeparator(),sections));
      }
    }
     writeLines(list.toArray(new String[list.size()]),false);
   }
    
//-------------------------------------------------------------------------setters and getters

  public void setPath(String path){
    this.path=path;
  }
  
  public String getPath(){
    return this.path;
  }

  public void setSeparator(String separator){
  this.separator=separator;
  }
  
  public String getSeparator(){
    return this.separator;
  }

}