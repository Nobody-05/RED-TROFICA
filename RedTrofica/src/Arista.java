public class Arista {
    public serVivo origen; 
    public serVivo destino; 
    public double peso;
   
    public Arista(serVivo origen, serVivo destino){
        this.destino = destino;
        this.origen = origen;
        this.peso = origen.energy * -0.1;
    }
}
