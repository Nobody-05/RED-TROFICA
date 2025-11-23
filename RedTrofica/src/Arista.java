public class Arista {
    //Atributo de la clase
    public serVivo origen; //Depredado
    public serVivo destino; //Depredador
    public double peso;
   /*Constructor parametrizado, el cual calcula el peso
    de la arista al momento de instanciarse.*/
    public Arista(serVivo origen, serVivo destino){
        this.destino = destino;
        this.origen = origen;
        this.peso = origen.energia * 0.1;
    }
}
