import java.util.LinkedList;

public class Animal extends serVivo {
        public Animal(int id, String name){
        super(id, name);
        this.energy = 0;
   }

   //si en la clsase GrafoListaAdyacencia se esta calculando la energia de un animal en la arista usando el id
   //se debria de eliminar este metodo y usar el de la clase GrafoListaAdyacencia
    public double calcularEnergia(LinkedList<Arista>[] adj){
        double aux = 0;
        for(int i =0; i<adj.length; i++){
            for(Arista a: adj[i]){
                if(a.destino.id == this.id){
                    aux = aux+ a.peso;
                }
            }
        }
        return aux; 
    }
}
