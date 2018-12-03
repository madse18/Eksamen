public class Event{
  /*
  *Class atributes
  */
  private double time;
  private char type;
  private Individual individual;
  private final static char MUTATION = 'm';
  private final static char DEATH = 'd';
  private final static char REPRODUCTION = 'r';

  /*
  *Constructer
  */
  public Event(char type, double time, Individual individual){
    this.time = time;
    this.type = type;
    this.individual = individual;
  }
  public double time(){
    return this.time;
  }
  public char type(){
    return this.type;
  }
  public Individual individual(){
    return this.individual;
  }

  /*
  *String convertion
  */
  public String toString(){
    String res = "";
    if(this.type == 'r'){
      res = res + "Reproduction: " + "time: " + time;
    }
    else if(this.type == 'm'){
      res = res + "Mutation: " + "time: " + time;
    }
    else if(this.type == 'd'){
      res = res + "Death: " + "time: " + time;
    }
    return res;
  }
}
