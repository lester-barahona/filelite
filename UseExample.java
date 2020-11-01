public class UseExample{

  private FileLite fl;

  public UseExample(){
    fl=new FileLite("pokemons.txt");
  }

  public void writePokemon(String name,String type){
    fl.writeLine(name+fl.separator+type,true);
  }
  
  
  public String readPokemons(){
  return fl.readFile();
  }
  



}