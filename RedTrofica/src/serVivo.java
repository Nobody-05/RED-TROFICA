public class serVivo {
    //Atributos de cada animal de la red
    public int id; 
    public String especie;
    public double energia;
    //Constructor parametrizado del serVivo
    /*En este constructor no se inicializa la energia
    Pues dependiendo del tipo de animal (productor o otro)
    se inicializara al crear un objeto o se le modificara
    a lo largo de la ejecución del programa*/
    public serVivo(int id, String especie){
       this.id = id;
       this.especie = especie;
   }
}
