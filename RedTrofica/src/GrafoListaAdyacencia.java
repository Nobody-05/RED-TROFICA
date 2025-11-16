import java.util.LinkedList;
import java.util.Arrays;

public class GrafoListaAdyacencia {
    public LinkedList<Arista>[] adj; 
    public int vertices; 
    public int aristas; 
    public serVivo[] animales;
    public AnimalPro[] productores;
   
    @SuppressWarnings("unchecked")
    public GrafoListaAdyacencia(int cantidadAnimales,int cantidadProductores){
        this.vertices = cantidadAnimales;
        this.aristas = 0; 
        this.animales = new serVivo[cantidadAnimales];
        this.productores = new AnimalPro[cantidadProductores];
        this.adj = new LinkedList[cantidadAnimales];
        for(int i =0; i<vertices; i++){
            adj[i] = new LinkedList<>();
        }
    }
   
    public double calcularEnergia(int servivo){ 
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
        productores[id] = (AnimalPro) animales[id];
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
   
    public void BellamFord(int destinoId){
        
        System.out.println("Caminos más eficientes desde cada productor hacia " + animales[destinoId].name);
        // Ejecutar Bellman-Ford desde cada productor
        for(int i = 0; i < productores.length; i++){
            
            // Inicializar para cada productor
            double[] dist = new double[vertices];
            int[] predecesor = new int[vertices];
            Arrays.fill(dist, Double.MAX_VALUE);
            Arrays.fill(predecesor, -1);
            
            // El productor actual es el origen
            dist[productores[i].id] = 0;
        
            // Relajación de aristas (V-1 veces)
            for(int j = 0; j < vertices - 1; j++){
                for(int k = 0; k < vertices; k++){
                    for(Arista arista : adj[k]){
                        int v = arista.destino.id;
                        double p = arista.peso;
                        int o = arista.origen.id;
                        
                        if(dist[o] != Double.MAX_VALUE && (dist[o] + p) < dist[v]){
                            dist[v] = dist[o] + p;
                            predecesor[v] = o; // Debería ser 'o', no 'k'
                        }
                    }
                }
            }
        
            // Detección de ciclos negativos
            boolean cicloNegativo = false;
            for(int k = 0; k < vertices; k++){
                for(Arista arista : adj[k]){
                    int v = arista.destino.id;
                    double p = arista.peso;
                    int o = arista.origen.id;
                    
                    if(dist[o] != Double.MAX_VALUE && (dist[o] + p) < dist[v]){
                        System.out.println("El grafo contiene un ciclo de peso negativo.");
                        cicloNegativo = true;
                        break;
                    }
                }
                if(cicloNegativo) break;
            }
            
            if(cicloNegativo) return;
            
            // Imprimir resultado para este productor
            if(dist[destinoId] == Double.MAX_VALUE){
                System.out.println("No hay camino desde " + productores[i].name + 
                                " hasta " + animales[destinoId].name);
            } else {
                System.out.println("\nDesde " + productores[i].name + ":");
                System.out.println("La energía total del camino más eficiente es: " + 
                                (-1 * dist[destinoId]));
                imprimirCamino(predecesor, productores[i].id, destinoId);
                System.out.println(); 
            }
            System.out.println(); // Línea en blanco entre resultados
        }
    }

    private void imprimirCamino(int[] predecesor, int origenId, int destinoId){
        if(destinoId == origenId){
            System.out.print(animales[origenId].name + " ");
        }
        else if(predecesor[destinoId] == -1){
            System.out.println("No hay camino desde " + productores[origenId].name + " hasta " + animales[destinoId].name);
        }
        else{
            imprimirCamino(predecesor, origenId, predecesor[destinoId]);
            System.out.print("-> " + animales[destinoId].name + " ");
        }
    }

    public void imprimirGrafo() {
        System.out.println("\n---- ESTRUCTURA DEL GRAFO ----");
        for(int i = 0; i < vertices; i++) {
            System.out.println(i + " " + animales[i].name );
            for(Arista arista : adj[i]) {
                System.out.println("   |--> " + arista.destino.id + 
                                " (" + arista.destino.name + 
                                ") [peso: " + (arista.peso*-1) + "]");
            }
            System.out.println();
        }
        System.out.println("\n---- ENERGIA DE LOS ANIMALES ----");
            for(int i = 0; i < vertices; i++) {
                System.out.println(animales[i].name + " --> energia total: " + animales[i].energy);
                System.out.println();
            }
    }
}
