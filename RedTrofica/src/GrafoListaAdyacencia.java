import java.util.LinkedList;
import java.util.Arrays;

public class GrafoListaAdyacencia {
    public LinkedList<Arista>[] adj; 
    public LinkedList<Arista>[] adjCopia;
    public int vertices; 
    public int aristas; 
    public serVivo[] animales;
    public serVivo[] animalesCopia;
    public AnimalPro[] productores;
   
    @SuppressWarnings("unchecked")
    public GrafoListaAdyacencia(int cantidadAnimales,int cantidadProductores){
        this.vertices = cantidadAnimales;
        this.aristas = 0; 
        this.animales = new serVivo[cantidadAnimales];
        this.animalesCopia = new serVivo[cantidadAnimales];
        this.productores = new AnimalPro[cantidadProductores];
        this.adjCopia = new LinkedList[cantidadAnimales];
        for(int i = 0; i<vertices; i++){
            adjCopia[i] = new LinkedList<>();
        }
        this.adj = new LinkedList[cantidadAnimales];
        for(int i =0; i<vertices; i++){
            adj[i] = new LinkedList<>();
        }
    }
   
    public double calcularEnergiaInicial(int servivo){ 
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

    public void calcularEnergiaFinal(serVivo[] animal){ 
        for(int k = 0; k < adjCopia.length; k++){
            if (animal[k] instanceof AnimalPro) {
                animal[k].energy = animales[k].energy;
                continue;
            }
            double energiaTotal = 0;
            
            if(animal[k] !=null){
            // Buscar todas las aristas donde k es el DEPREDADOR
            for(int i = 0; i < adjCopia.length; i++){
                for(Arista a : adjCopia[i]){
                    // Si el animal k come al animal i
                    if(a.destino.id == k){
                        energiaTotal -= a.peso; // SUMA la energía que obtiene
                    }
                }
            }
            
            // Asignar la energía calculada
            animal[k].energy = energiaTotal;
            }
        }
    }
   
    public void agregarProductor(int id, String name, double energy){
        animales[id]=new AnimalPro(id, name, energy);
        animalesCopia[id]=new AnimalPro(id, name, energy);
        productores[id] = (AnimalPro) animales[id];
    }    
   
    public void agregarAnimal(int id, String name){
        animales[id]=new Animal(id, name);
        animalesCopia[id]=new Animal(id, name);
    }

    public void agregarArista(serVivo origen, serVivo destino){
        adj[origen.id].add(new Arista(origen, destino));
        destino.energy = calcularEnergiaInicial(destino.id);
        aristas++;
    }
   
    public void BellamFord(int destinoId){
        
        System.out.println("Caminos más eficientes desde cada productor hacia " + animales[destinoId].name);
        // Ejecutar Bellman-Ford desde cada productor
        for(int i = 0; i < productores.length; i++){
            
            // Inicializar para cada productor
            double[] dist = new double[vertices];//se inicializa la distancia desde el productor a todos los demas nodos
            int[] predecesor = new int[vertices];
            Arrays.fill(dist, Double.MAX_VALUE);// la distancia desde el productor a todos los demas nodos es infinita al inicio
            Arrays.fill(predecesor, -1);//el recorrdio desde el productor a los demas nodos es de -1
            
            // El productor i actual es el origen
            dist[productores[i].id] = 0;
        
            // Relajación de aristas (V-1 veces)
            for(int j = 0; j < vertices - 1; j++){
                for(int k = 0; k < vertices; k++){
                    for(Arista arista : adj[k]){
                        int aristaDestino = arista.destino.id;//id del nodo destino de la arista k de adj
                        double aristaPeso = arista.peso;//peso de la arista k de adj
                        int aristaOrigen = arista.origen.id;//id del nodo origen de la arista k de adj
                        
                        if(dist[aristaOrigen] != Double.MAX_VALUE && (dist[aristaOrigen] + aristaPeso) < dist[aristaDestino]){
                            dist[aristaDestino] = dist[aristaOrigen] + aristaPeso; // si el camino es mas corto que el que esta originalmente se actualiza la distancia
                            predecesor[aristaDestino] = aristaOrigen; // actualiza el predecesor del nodo destino como el nodo origen
                        }
                    }
                }
            }
        
            // Detección de ciclos negativos
            boolean cicloNegativo = false;//se inicializa la variable boolean cicloNegativo en falso
            for(int k = 0; k < vertices; k++){
                for(Arista arista : adj[k]){
                    int aristaDestino = arista.destino.id;//id del nodo destino de la arista k de adj
                    double aristaPeso = arista.peso;//peso de la arista k de adj
                    int aristaOrigen = arista.origen.id;//id del nodo origen de la arista k de adj
                    
                    if(dist[aristaOrigen] != Double.MAX_VALUE && (dist[aristaOrigen] + aristaPeso) < dist[aristaDestino]){
                        System.out.println("El grafo contiene un ciclo de peso negativo.");
                        cicloNegativo = true;//si se encuentra un ciclo negativo se cambia la variable cicloNegativo a verdadero
                        break;
                    }
                }
                if(cicloNegativo) break;//si se encuentra un ciclo negativo se sale del bucle externo
            }
            
            if(cicloNegativo) return;//si se encuentra un ciclo negativo se termina la ejecucion del metodo
            
            // Imprimir resultado para este productor
            if(dist[destinoId] == Double.MAX_VALUE){//si la distancia desde el productor al destino es infinita significa que no se actualizo 
                //la distacia con bellman ford y por ende no hay camino hasta el destino
                System.out.println("No hay camino desde " + productores[i].name + 
                                " hasta " + animales[destinoId].name);
            } else {
                System.out.println("\nDesde " + productores[i].name + ":");//se imprime el nombre del productor i actual
                System.out.println("La energía total del camino más eficiente es: " + 
                                (-1 * dist[destinoId]));//se imprime el valor desde el productor i hasta el destino multiplicado 
                imprimirCamino(predecesor, productores[i].id, destinoId);//se imprime el camino desde el productor i hasta el destino
                System.out.println(); 
            }
            System.out.println(); // Línea en blanco entre resultados
        }
    }

    private void imprimirCamino(int[] predecesor, int origenId, int destinoId){
        if(destinoId == origenId){// caso base: si el nodo destino es igual al nodo origen 
            System.out.print(animales[origenId].name + " ");
        }
        else if(predecesor[destinoId] == -1){// si el predecesor del nodo destino es -1 significa que no se actualizo el recorrido y por ende no hay camino
            System.out.println("No hay camino desde " + productores[origenId].name + " hasta " + animales[destinoId].name);
        }
        else{
            imprimirCamino(predecesor, origenId, predecesor[destinoId]);//se llama recursivamente al método para imprimir el camino desde el origen hasta el predecesor del destino
            System.out.print("-> " + animales[destinoId].name + " ");
        }
    }

    public void imprimirGrafo(LinkedList<Arista>[] lista) {
        System.out.println("\n---- ESTRUCTURA DEL GRAFO ----");
        for(int i = 0; i < vertices; i++) {
            System.out.println(i + " " + animales[i].name );
            for(Arista arista : lista[i]) {//impresión de los datos de al arista destino de i
                System.out.println("   |--> " + arista.destino.id + 
                                " (" + arista.destino.name + 
                                ") [peso: " + (arista.peso*-1) + "]");
            }
            System.out.println();
        }
        System.out.println("\n---- ENERGIA DE LOS ANIMALES ----");
            for(int i = 0; i < vertices; i++) {
                if(animales[i] != null){
                System.out.println(animales[i].name + " --> energia total: " + animales[i].energy);//impresión de la energía total del animal i
                System.out.println();
                }
            }
    }

    public void imprimirGrafoExtinto(LinkedList<Arista>[] lista) {
        calcularEnergiaFinal(animalesCopia);
        System.out.println("\n---- ESTRUCTURA DEL GRAFO ----");
        for(int i = 0; i < vertices; i++) {
            System.out.println(i + " " + animales[i].name );
            for(Arista arista : lista[i]) {
                System.out.println("   |--> " + arista.destino.id + 
                                " (" + arista.destino.name + 
                                ") [peso: " + (arista.peso*-1) + "]");
            }
            System.out.println();
        }
        
        System.out.println("\n---- ENERGIA DE LOS ANIMALES ----");
            for(int i = 0; i < vertices; i++) {
                if(animalesCopia[i] != null){
                System.out.println(animalesCopia[i].name + " --> energia total: " + animalesCopia[i].energy);
                System.out.println();
                }
            }
    }

   public void eliminarAnimalDeCopia(int id){ //pide el id del animal a eliminar
       if(id < 0 || id >= vertices){ //validación para evitar errores por un id fuera del rango
           System.out.println("ERROR: ANIMAL NO EXISTENTE EN LA CADENA TRÓFICA");
           return;
       }
       
       // 1. Eliminar aristas salientes de adjCopia
       adjCopia[id].clear(); //usa el comando clear para eliminar todas las aristas salientes del nodo con el id dado
       animalesCopia[id] = null; //vuelve nulo el id del animal eliminado
       
       // 2. Eliminar aristas entrantes de adjCopia
       for(int i = 0; i < vertices; i++){ //elimina las aristas entrantes que tengan en común el id del animal eliminado
           if(i != id){
               adjCopia[i].removeIf(arista -> arista.destino.id == id);
           }
       }
   }
   
   // ==================== MUERTE EN CADENA ====================
   
   public void muerteEnCadena(int primerMuerto){ //algoritmo que ayudará a matar a medio ecosistema xD
       if(primerMuerto >= vertices || primerMuerto < 0){ //validación para evitar errores por un muerto que no existe
           System.out.println("ERROR: ID no válido para el método");
           return;
       }
       
       // Copiar adj a adjCopia al inicio
       for(int i = 0; i < vertices; i++){ //copia adj a adjCopia para trabajar con una copia
           adjCopia[i].clear();
           for(Arista arista : adj[i]){
               adjCopia[i].add(new Arista(arista.origen, arista.destino));
           }
       }
       
       boolean[] losMuertos = new boolean[vertices]; //arreglo booleano para llevar el control de los animales muertos
       losMuertos[primerMuerto] = true; //asume que el primer animal ya esta muerto
       
       System.out.println(" Comienza la extinción en cadena...");
       System.out.println(" Primer muerto: " + animales[primerMuerto].name);
       
       boolean seMurioAlguien = true; //se asume que alguien si se murió para entrar al while
       int ronda = 1;//contador para el numero de rondas 
       
       while(seMurioAlguien){ //se detiene cuando no mueran mas animales
           seMurioAlguien = false; //primero asume que no se murió nadie para tratar de demostrar lo contrario
           System.out.println("\n Ronda " + ronda + ":");
           
           for(int i = 0; i < vertices; i++){//empieza a recorrer la copia
               if(losMuertos[i]) continue; //validación para evitar matar a alguien que ya está muerto
               
               boolean esProductor = false; //variable boolean para verificar si el animal es productor
               for(serVivo productor : productores){//recorre la lista de productores
                   if(productor.id == i){ //si encuentra un productor, el boolean se convierte en true;
                       esProductor = true;
                       break;//descansa el for;
                   }
               }
               if(esProductor) continue; //si es productor, sigue al siguiente animal;
               
               boolean tieneComida = false; //boolean que verifica si un animal tiene comida;
               
               for(int j = 0; j < vertices; j++){
                   if(losMuertos[j]) continue;//si el animal j se murió, sigue;
                   
                   for(Arista arista : adjCopia[j]){//recorre las aristas del animal j
                       if(arista.destino.id == i){//si encuentra un animal que se coma a otro, tiene comida;
                           tieneComida = true;
                           break;
                       }
                   }
                   if(tieneComida) break; //si ya tiene comida, descansa el for;
               }
               
               if(!tieneComida){ //si no tiene comida, se procede a matarlo;
                   losMuertos[i] = true; //marca al animal i como muerto en el vector de muertos;
                   seMurioAlguien = true; //marca que alguien si murió en la ronda;
                   System.out.println(animales[i].name +" se extingue por falta de alimento");
               }
           }
           
           if(!seMurioAlguien){//si nadie murió en la ronda, se avisa con el print;
               System.out.println("No hay más extinciones en esta ronda");
           }
           ronda++; //aumenta el contador de rondas;
       }
       
       // Eliminar muertos de adjCopia
       for(int i = 0; i < vertices; i++){ //elimina los animales muertos de adjCopia
           if(losMuertos[i]){ 
               adjCopia[i].clear();
               animalesCopia[i]  = null;
           }
       }
       
       for(int i = 0; i < vertices; i++){//elimina las aristas entrantes que tengan en comun el animal muerto;
           if(!losMuertos[i]){
               adjCopia[i].removeIf(arista -> losMuertos[arista.destino.id]);
           }
       }
       
       // Resumen
       System.out.println("\n" + "=".repeat(50));//imprime un resumen de los animales extintos;
       System.out.print("Especies extintas: ");
       int numeroMuertos = 0;
       for(int i = 0; i < vertices; i++){ //for para imprimir los animales muertos;
           if(losMuertos[i]){
               System.out.print("| " + animales[i].name + " | ");
               numeroMuertos++;
           }
       }
       System.out.println("\n Total extintas: " + numeroMuertos);
       System.out.println("=".repeat(50));
   }
}
