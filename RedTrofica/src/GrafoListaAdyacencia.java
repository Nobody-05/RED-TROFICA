//Importar Librerias
import java.util.LinkedList;
import java.util.Arrays;


public class GrafoListaAdyacencia {
    /*Lista de adyacencia para crear las
    conexiones entre nodos de tipo arista*/
    public LinkedList<Arista>[] adyacencia; 
    /*Cantidad de Nodos(Animales y productores)*/
    public int vertices; 
    /*Cantidad de conexiones entre
    los animales y productores*/
    public int aristas; 
    /*Vector donde se guardaran las especies
    de animales contando a los productores*/
    public serVivo[] animales;
   
    /*Constructor del grafo, en el cual se ingresa la cantidad de animales
    total, contando productores y la cantidad de productores en el grafo*/
    @SuppressWarnings("unchecked")
    public GrafoListaAdyacencia(int cantidadAnimales,int cantidadProductores){
        //Se asinga la cantidad de animales a la cantidad de vertices (nodos)
        this.vertices = cantidadAnimales;
        this.aristas = 0; 
        /*El vector animales tendra como tamaño la cantidad de animales*/
        this.animales = new serVivo[cantidadAnimales];
        /*Se asigna el tamaño a la lista de adyacencia y despues creamos
        en cada posición de la lista una LinkedList que seran de tipo arista*/
        this.adyacencia = new LinkedList[cantidadAnimales];
        for(int i = 0; i<vertices; i++){
            adyacencia[i] = new LinkedList<>();
        }
    }
   
    //Metodo para agregar un productor a la red trófica
    public void agregarProductor(int id, String especie, double energia){
        AnimalPro animalPro = new AnimalPro(id, especie, energia);
        /*Se añade el objeto del productor creado al vector de animales 
        en la posición correspondiente respecto a su id*/
        animales[id] = animalPro;
    }    
   
    //Metodo para agregar animales que no sean productores.
    public void agregarAnimal(int id, String especie){
        Animal animal = new Animal(id, especie);
        animales[id] = animal;
    }
    
    //Metodo para agregar una arista a la red
    //Como parametros recibimos los atributos de la clase arista
    //menos el peso ya que este se calcula con calcularEnergia().
    public void agregarArista(serVivo origen, serVivo destino){
        adyacencia[origen.id].add(new Arista(origen, destino));
        aristas++;
    }

    /*Metodo para calular la energia de todas las especies*/
    public void calcularEnergia(){ 
        for(int k = 0; k < adyacencia.length; k++){
            double energiaTotal = 0;
            if(animales[k] !=null && animales[k] instanceof Animal){ // el intanceof nos ayuda a calcular la energia solo de no productores
                // Buscar todas las aristas donde k es el depredador
                for(int i = 0; i < adyacencia.length; i++){
                    for(Arista a : adyacencia[i]){
                        // Si el animal k come al animal i
                        if(a.destino.id == k && a.destino != null && a.origen !=null){
                            energiaTotal += a.peso; // SUMA la energía que obtiene usando pesos negativos  
                        }
                    }
                }
                // el valor de todas las aristas salientes desde de la energia del anterior animal calculada calculada
                animales[k].energia = energiaTotal;
                for(Arista a: adyacencia[k]){
                    a.peso = a.origen.energia*0.1;
                }
            }
        }
    }
    //Bellman Ford para hallar la ruta que más proporciona energía desde todos los productores hacia el depredador elegido
    public void BellmanFord(int destinoId){

        // validar que no pida un productor
        if(animales[destinoId] instanceof AnimalPro){
            System.out.println("No se pueden calcular caminos hacia productores");
            return;
        }
        // validar que no pida una ya extinto
        if(animales[destinoId] == null){
            System.out.println("ERROR: El animal ya fue eliminado");
            return;
        }
        
        System.out.println("Caminos mas eficientes desde cada productor hacia " + animales[destinoId].especie);
        for(int i = 0; i < animales.length; i++){ // Bellman-Ford desde cada productor
            if(animales[i] instanceof AnimalPro && animales[i] !=null){
                //se inicializa la distancia desde el productor a todos los demas nodos
                double[] dist = new double[vertices];
                int[] predecesor = new int[vertices];
                // la distancia desde el productor a todos los demas nodos es infinita al inicio
                Arrays.fill(dist, Double.MAX_VALUE);
                //el recorrdio desde el productor a los demas nodos es de -1
                Arrays.fill(predecesor, -1);
                // El productor actual es el origen
                dist[animales[i].id] = 0;
                
                
                // Relajación de aristas (V-1 veces)
                for(int j = 0; j < vertices - 1; j++){
                    for(int k = 0; k < vertices; k++){
                        for(Arista arista : adyacencia[k]){
                            //id del nodo destino de la arista k de adj
                            int idAristaDestino = arista.destino.id;
                            //peso de la arista k de adj
                            double pesoArista = arista.peso;
                            //id del nodo origen de la arista k de adj
                            int idAristaOrigen = arista.origen.id;
                            
                            if(dist[idAristaOrigen] != Double.MAX_VALUE && (dist[idAristaOrigen] + pesoArista) < dist[idAristaDestino]){
                                // si el camino es mas corto que el que esta originalmente se actualiza la distancia
                                dist[idAristaDestino] = dist[idAristaOrigen] + pesoArista;
                                // actualiza el predecesor del nodo destino como el nodo origen
                                predecesor[idAristaDestino] = idAristaOrigen;
                            }
                        }
                    }
                }
            
                // Detección de ciclos negativos
                boolean cicloNegativo = false;
                for(int k = 0; k < vertices; k++){
                    for(Arista arista : adyacencia[k]){
                        //id del nodo destino de la arista k de adj
                        int v = arista.destino.id;
                        //peso de la arista k de adj
                        double p = arista.peso;
                        //id del nodo origen de la arista k de adj
                        int o = arista.origen.id;
                        
                        if(dist[o] != Double.MAX_VALUE && (dist[o] + p) < dist[v]){
                            System.out.println("El grafo contiene un ciclo de peso negativo.");
                            cicloNegativo = true;
                            break;
                        }
                    }
                    //si se encuentra un ciclo negativo se sale del bucle externo
                    if(cicloNegativo) break;
                }
                //si se encuentra un ciclo negativo se termina la ejecucion del metodo
                if(cicloNegativo) return;
                
                // Imprimir resultado para este productor
                //si la distancia desde el productor al destino es infinita significa que no se actualizo 
                if(dist[destinoId] == Double.MAX_VALUE){
                    //la distacia con bellman ford y por ende no hay camino hasta el destino
                    System.out.println("No hay camino desde " + animales[i].especie + 
                                    " hasta " + animales[destinoId].especie);
                } else {
                    //se imprime el nombre del productor i actual
                    System.out.println("\nDesde " + animales[i].especie + ":");
                    //se imprime el valor desde el productor i hasta el destino multiplicado 
                    System.out.println("La energia total desde el camino por el cual depredador recibe mas energia de un productor es: " + 
                                    ( dist[destinoId]));
                    //se imprime el camino desde el productor i hasta el destino
                    imprimirCamino(predecesor, animales[i].id, destinoId);
                    System.out.println(); 
                }
                // Línea en blanco entre resultados
                System.out.println(); 
            }
        }
    }    

    private void imprimirCamino(int[] predecesor, int origenId, int destinoId){
        // caso base: si el nodo destino es igual al nodo origen
        if(destinoId == origenId){
            System.out.print(animales[origenId].especie + " ");
        }
        else{
            imprimirCamino(predecesor, origenId, predecesor[destinoId]);
            //se llama recursivamente al método para imprimir el camino desde el origen hasta el predecesor del destino
            System.out.print("-> " + animales[destinoId].especie + " ");
        }
    }

    public void imprimirGrafo() {
        calcularEnergia(); // calculamos energia para ver los resultados de impresión
        System.out.println("\n---- ESTRUCTURA DEL GRAFO ----");
        for(int i = 0; i < vertices; i++) {
            if(animales[i] != null){
            System.out.println(i + " " + animales[i].especie );
            //impresión de los datos de al arista destino de i
            for(Arista arista : adyacencia[i]) {
                System.out.println("   |--> " + arista.destino.id + 
                                " (" + arista.destino.especie + 
                                ") [peso: " + (arista.peso) + "]");
            }
            System.out.println();
            }
        }

        System.out.println("\n   ID   |    ANIMALES    |    ENERGIA");
            for(int i = 0; i < vertices; i++) {
                if(animales[i] != null){
                //impresión de la energía total del animal i
                System.out.println("id: " + animales[i].id + "|" + animales[i].especie + " --> energia total: " + animales[i].energia);
                System.out.println();
                }
            }
    }
 
    public void muerteEnCadena(int primerMuerto){
        if(primerMuerto >= vertices || primerMuerto < 0){
            System.out.println("ERROR: ID no valido para el metodo");
            return;
        }
        
        // Validar que el animal exista antes de eliminarlo
        if(animales[primerMuerto] == null){
            System.out.println("ERROR: El animal ya fue eliminado");
            return;
        }
        
        boolean[] losMuertos = new boolean[vertices];
        losMuertos[primerMuerto] = true;
        
        System.out.println("Comienza la extincion en cadena...");
        System.out.println("Primer muerto: " + animales[primerMuerto].especie);
        
        boolean seMurioAlguien = true;
        int ronda = 1;
        
        while(seMurioAlguien){
            seMurioAlguien = false;
            System.out.println("\nRonda " + ronda + ":");
            
            for(int i = 0; i < vertices; i++){
                // Saltar si ya está muerto o es null
                if(losMuertos[i] || animales[i] == null) continue;
                
                // Verificar si es productor
                boolean esProductor = false;
                for(serVivo animal : animales){
                    if(animal != null && animal.id == i && animal instanceof AnimalPro){
                        esProductor = true;
                        break;
                    }
                }
                if(esProductor) continue;
                
                // Verificar si tiene comida
                boolean tieneComida = false;
                
                for(int j = 0; j < vertices; j++){
                    if(losMuertos[j] || animales[j] == null) continue;
                    for(Arista arista : adyacencia[j]){
                        // Verificar que el destino no sea null
                        if(arista.destino != null && arista.destino.id == i){
                            tieneComida = true;
                            break;
                        }
                    }
                    if(tieneComida) break;
                }
                
                if(!tieneComida){
                    losMuertos[i] = true;
                    seMurioAlguien = true;
                    System.out.println(animales[i].especie + 
                                    " se extingue por falta de alimento");
                }
            }
            
            if(!seMurioAlguien){
                System.out.println("No hay mas extinciones en esta ronda");
            }
            ronda++;
        }
        
        // Eliminar todos los muertos del grafo original
        for(int i = 0; i < vertices; i++){
            if(losMuertos[i]){
                adyacencia[i].clear();
                animales[i]  = null;
            }
        }
        //elimina las aristas entrantes que tengan en comun el animal muerto;
        for(int i = 0; i < vertices; i++){
            if(!losMuertos[i]){
                adyacencia[i].removeIf(arista -> losMuertos[arista.destino.id]);
            }
        }
        // Resumen
        System.out.println("\n" + "-------------");
        System.out.print("Especies extintas: ");
        int numeroMuertos = 0;
        for(int i = 0; i < vertices; i++){
            if(losMuertos[i]){
                numeroMuertos++;
            }
        }
        System.out.println("\nTotal extintas: " + numeroMuertos);
        System.out.println("----------------");
    }
}
