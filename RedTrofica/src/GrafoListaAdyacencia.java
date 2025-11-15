import java.util.LinkedList;

public class GrafoListaAdyacencia {
    public LinkedList<Arista>[] adj; 
    public int vertices; 
    public int aristas; 
    public serVivo[] animales;
   
    public GrafoListaAdyacencia(int cantidadAnimales){
        this.vertices = cantidadAnimales;
        this.aristas = 0; 
        this.animales = new serVivo[cantidadAnimales];
        this.adj = new LinkedList[cantidadAnimales];
        for(int i =0; i<vertices; i++){
            adj[i] = new LinkedList<>();
        }
    }
   
    public double calcularEnergia(int servivo){ //duda aqui el parametro no debia ser la matriz adj?
        //leer comentario en la clase animal
        double aux = 0;
        for(int i =0; i<adj.length; i++){
            for(Arista a: adj[i]){
                if(a.destino.id == servivo){
                    aux = aux + a.peso;
                }
            }
        }
        return aux; 
    }
   
    public void agregarProductor(int id, String name, double energy){
        animales[id]=new AnimalPro(id, name, energy);
    }    
   
    public void agregarAnimal(int id, String name){
        animales[id]=new Animal(id, name);
        animales[id].energy = calcularEnergia(id);
        // o se calcula aqui, cuando se crea el animal o cuando se crea la arista en el metodo agregarArista
        //leer primero el comentario en el metodo agregarArista
    }

    public void agregarArista(serVivo origen, serVivo destino){
        adj[origen.id].add(new Arista(origen, destino));
        destino.energy = calcularEnergia(origen.id);//para poder llamar la función aqui y calcular la energia
        //la energia se calcula para el destino porque es el que recibe la energia del origen? es decir cuando se crea la arista
        //leer el comentario el metodo agregarAnimal
        aristas++;
    }
   
    public void BellamFord(int origenId, int destinoId){
        double[]dist = new double[vertices];
       
       //Inicializar en el arreglo el la distancia del NodoOrigen en 0 y las demas en "infinito"
       
        for(int i=0; i<vertices; i++){
            dist[i]=Double.MAX_VALUE;
        }
        dist[origenId] = 0;
       
        for(int j=0; j<vertices-1; j++){
            for(int k =0; k<vertices; k++){
                for(Arista arista : adj[k]){
                    int v = arista.destino.id;
                    double  p = arista.peso;
                    int o = arista.origen.id;
                    if(((dist[o]+p)<dist[v]) && (dist[o] != Double.MAX_VALUE)){
                        dist[v] = dist[o]+p;
                    }
                }
            }
        }
       
       //deteccion de ciclos negativos
        for(int k =0; k<vertices; k++){
            for(Arista arista : adj[k]){
                int v = arista.destino.id;
                    double p = arista.peso;
                    int o = arista.origen.id;
                    if((dist[o]+p)<dist[v]){
                        System.out.println("El grafo contiene un ciclo de peso negativo.");
                        return;
                    }
            }
        }
       
        System.out.println("la distancia más corta es; " + dist[destinoId]);
    }
   
    public void imprimirGrafo() {
        for (int i = 0; i < vertices; i++) {
            System.out.print("Nodo: " + animales[i].name+ " conectado a: ");
            if(adj[i].isEmpty()){
                System.out.print("Nada, no tiene depredadores");
            }
            for (Arista arista : adj[i]) {
                System.out.print("(Depredador: " + arista.destino.name + ", Peso: " + arista.peso + ") ");
            }
            System.out.println();
        }
    }
}
