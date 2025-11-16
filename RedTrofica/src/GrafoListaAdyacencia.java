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
                    aux = aux - a.peso;
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
        // o se calcula aqui, cuando se crea el animal o cuando se crea la arista en el metodo agregarArista
        //leer primero el comentario en el metodo agregarArista
    }

    public void agregarArista(serVivo origen, serVivo destino){
        adj[origen.id].add(new Arista(origen, destino));
        destino.energy = calcularEnergia(destino.id);//para poder llamar la función aqui y calcular la energia
        //la energia se calcula para el destino porque es el que recibe la energia del origen? es decir cuando se crea la arista
        //leer el comentario el metodo agregarAnimal
        aristas++;
    }
   
    public void BellamFord(int origenId, int destinoId){
        double[]dist = new double[vertices];
        int[] predecesor = new int[vertices];
       
       //Inicializar en el arreglo el la distancia del NodoOrigen en 0 y las demas en "infinito"
       
        for(int i=0; i<vertices; i++){
            dist[i]=Double.MAX_VALUE;
            predecesor[i] = -1;
        }
        dist[origenId] = 0;
       
        for(int j=0; j<vertices-1; j++){//for hasta el numero de vertices
            for(int k =0; k<vertices; k++){//for que recorre todos nodos del grafo
                for(Arista arista : adj[k]){//itera todas las aristas salientes del nodo k
                    int v = arista.destino.id;
                    double  p = arista.peso;
                    int o = arista.origen.id;
                    if(((dist[o]+p)<dist[v]) && (dist[o] != Double.MAX_VALUE)){
                        dist[v] = dist[o]+p;
                        predecesor[v] = k;
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
        if(dist[destinoId] == Double.MAX_VALUE){
            System.out.println("No hay camino desde " + animales[origenId].name + " hasta " + animales[destinoId].name);
            return;
        }
        else{
            System.out.println("El la energía total del camino mas eficiente es: " + (-1*dist[destinoId]));
            imprimirCamino(predecesor, origenId, destinoId);
        }
    }
   
    public void imprimirGrafo() {
        for (int i = 0; i < vertices; i++) {
            System.out.print("Nodo: " + animales[i].name+ " conectado a: ");
            if(adj[i].isEmpty()){
                System.out.print("Nada, no tiene depredadores");
            }
            for (Arista arista : adj[i]) {
                System.out.print("(Depredador: " + arista.destino.name + ", Peso: " + (-1*arista.peso) + ") ");
            }
            System.out.println();
        }
    }

    private void imprimirCamino(int[] predecesor, int origenId, int destinoId){
        if(destinoId == origenId){
            System.out.print(animales[origenId].name + " ");
        }
        else if(predecesor[destinoId] == -1){
            System.out.println("No hay camino desde " + animales[origenId].name + " hasta " + animales[destinoId].name);
        }
        else{
            imprimirCamino(predecesor, origenId, predecesor[destinoId]);
            System.out.print("-> " + animales[destinoId].name + " ");
        }
    }
}
