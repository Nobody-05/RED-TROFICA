//Importar Librerias
import java.util.LinkedList;
import java.util.Arrays;


public class GrafoListaAdyacencia {
    //Atributos del grafo
    /*Lista de adyacencia para crear las
    conexiones entre nodos de tipo arista*/
    public LinkedList<Arista>[] adyacencia; 
    /*Copia auxiliar para operaciones en los 
    metodos*/
    public LinkedList<Arista>[] adyacenciaCopia;
    /*Cantidad de vertices osea animales
    no productores*/
    public int vertices; 
    /*Cantidad de conexiones entre
    los animales y productores*/
    public int aristas; 
    /*Vector donde se guardaran las especies
    de animales contando a los productores*/
    public serVivo[] animales;
    /*Copia para ayudar en los procedimientos
    de los metodos*/
    public serVivo[] animalesCopia;
    /*Vecetor para guardar a los productores
    solamente*/
    public AnimalPro[] productores;
   
    /*Constructor del grafo, en el cual se ingresa la cantidad de animales
    total, contando productores y la cantidad de productores en el grafo*/
    @SuppressWarnings("unchecked")//Para evitar alertas de copilación en el VSC
    public GrafoListaAdyacencia(int cantidadAnimales,int cantidadProductores){
        //Se asinga la cantidad de animales a la cantidad de vertices (nodos)
        this.vertices = cantidadAnimales;
        //La cantidad de aristas al crear el grafo es 0
        this.aristas = 0; 
        /*El vector animales tendra como tamaño la cantidad de animales*/
        this.animales = new serVivo[cantidadAnimales];
        //Igual la copia
        this.animalesCopia = new serVivo[cantidadAnimales];
        /*El vector de productores tendra como tamaño la cantidad de 
        productores*/
        this.productores = new AnimalPro[cantidadProductores];
        /*Se asigna el tamaño a la lista de adyacencia y despues creamos
        en cada posición de la lista una LinkedList que seran de tipo arista*/
        this.adyacencia = new LinkedList[cantidadAnimales];
        for(int i = 0; i<vertices; i++){
            adyacencia[i] = new LinkedList<>();
        }
        //Lo mismo pero con la copia de la lista de adyacencia
        this.adyacenciaCopia = new LinkedList[cantidadAnimales];
        for(int i = 0; i<vertices; i++){
            adyacenciaCopia[i] = new LinkedList<>();
        }
    }
   
    //Metodo para agregar un productor a la red trófica.
    //Se reciben como parametros los atributos de la clase
    public void agregarProductor(int id, String especie, double energia){
        //Creamos el objeto del productor
        AnimalPro animalPro = new AnimalPro(id, especie, energia);
        /*Se añade el objeto del productor creado al vector de animales 
        en la posición correspondiente respecto a su id*/
        animales[id] = animalPro;
        /*Se repite lo mismo para el vector copia*/
        animalesCopia[id] = animalPro;
        /*Tambien se añade al vector de productores*/
        productores[id] = animalPro;
    }    
   
    //Metodo para agregar animales que no sean productores.
    //Se reciben como parametro el id y la especie
    public void agregarAnimal(int id, String especie){
        //Creamos el objeto del animal añadido
        Animal animal = new Animal(id, especie);
        //Anadimos al vector de animales y a la copia
        animales[id] = animal;
        animalesCopia[id] = animal;
    }
    
    //Metodo para agregar una arista a la red
    //Como parametros recibimos los atributos de la clase arista
    //menos el peso
    public void agregarArista(serVivo origen, serVivo destino){
        /*Para la posición del serVivo origen en la lista de
        adyacencia agregamos la arista (la conexión con otro nodo)*/
        adyacencia[origen.id].add(new Arista(origen, destino));
        /*Inmediatamente calculamos la energia que el origen transfiere
        al destino*/
        destino.energia = calcularEnergiaInicial(destino.id);
        //Aumentamos el contador de aristas
        aristas++;
    }
    
    /*Metodo para calular la energia antes de la extinción de
    una especie*/
    //Recibimos como parametros el id del destino
    public double calcularEnergiaInicial(int destinoID){ 
        //Declaramos un auxiliar tipo double y la igualamos a 0
        double auxiliar = 0;
        //Creamos un for para recorrer toda la lista de adyacencia
        for(int i =0; i<adyacencia.length; i++){
            /*Buscamos las aristas cuyo destino sea el mismo que
            el buscado a traves del id*/
            for(Arista arista: adyacencia[i]){
                if(arista.destino.id == destinoID){
                    /*Si es el mismo, restamos el peso para que
                    se sume pues el peso es negativo*/
                    auxiliar = auxiliar - arista.peso;
                }
            }
        }
        //Retornamos el valor para la energia del destino
        return auxiliar; 
    }
    //Bellman Ford para hallar la ruta que más proporciona.
    public void BellmanFord(int destinoId){
        
        System.out.println("Caminos mas eficientes desde cada productor hacia " + animales[destinoId].especie);
        // Ejecutar Bellman-Ford desde cada productor
        for(int i = 0; i < productores.length; i++){
            
            // Inicializar para cada productor
            //se inicializa la distancia desde el productor a todos los demas nodos
            double[] dist = new double[vertices];
            int[] predecesor = new int[vertices];
            // la distancia desde el productor a todos los demas nodos es infinita al inicio
            Arrays.fill(dist, Double.MAX_VALUE);
            //el recorrdio desde el productor a los demas nodos es de -1
            Arrays.fill(predecesor, -1);
            // El productor actual es el origen
            dist[productores[i].id] = 0;
            
            
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
            //se inicializa la variable boolean cicloNegativo en falso
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
                        //si se encuentra un ciclo negativo se cambia la variable cicloNegativo a verdadero
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
                System.out.println("No hay camino desde " + productores[i].especie + 
                                " hasta " + animales[destinoId].especie);
            } else {
                //se imprime el nombre del productor i actual
                System.out.println("\nDesde " + productores[i].especie + ":");
                //se imprime el valor desde el productor i hasta el destino multiplicado 
                System.out.println("La energia total del camino mas eficiente es: " + 
                                (-1 * dist[destinoId]));
                //se imprime el camino desde el productor i hasta el destino
                imprimirCamino(predecesor, productores[i].id, destinoId);
                System.out.println(); 
            }
            // Línea en blanco entre resultados
            System.out.println(); 
        }
    }
    public void calcularEnergiaFinal(LinkedList<Arista>[] lista, serVivo[] animales){ 
        for(int k = 0; k < lista.length; k++){
            if (animales[k] instanceof AnimalPro) {
                animales[k].energia = animales[k].energia;
                continue;
            }
            double energiaTotal = 0;
            
            if(animales[k] !=null){
            // Buscar todas las aristas donde k es el DEPREDADOR
            for(int i = 0; i < lista.length; i++){
                for(Arista a : lista[i]){
                    // Si el animal k come al animal i
                    if(a.destino.id == k){
                        energiaTotal -= a.peso; // SUMA la energía que obtiene
                    }
                }
            }
            
            // Asignar la energía calculada
            animales[k].energia = energiaTotal;
            }
        }
    }

    private void imprimirCamino(int[] predecesor, int origenId, int destinoId){
        // caso base: si el nodo destino es igual al nodo origen
        if(destinoId == origenId){
            System.out.print(animales[origenId].especie + " ");
        }
        // si el predecesor del nodo destino es -1 significa que no se actualizo el recorrido y por ende no hay camino
        else if(predecesor[destinoId] == -1){
            System.out.println("No hay camino desde " + productores[origenId].especie + " hasta " + animales[destinoId].especie);
        }
        else{
            imprimirCamino(predecesor, origenId, predecesor[destinoId]);
            //se llama recursivamente al método para imprimir el camino desde el origen hasta el predecesor del destino
            System.out.print("-> " + animales[destinoId].especie + " ");
        }
    }

    public void imprimirGrafo(LinkedList<Arista>[] lista) {
        System.out.println("\n---- ESTRUCTURA DEL GRAFO ----");
        for(int i = 0; i < vertices; i++) {
            System.out.println(i + " " + animales[i].especie );
            //impresión de los datos de al arista destino de i
            for(Arista arista : lista[i]) {
                System.out.println("   |--> " + arista.destino.id + 
                                " (" + arista.destino.especie + 
                                ") [peso: " + (arista.peso*-1) + "]");
            }
            System.out.println();
        }
        System.out.println("\n---- ANIMALES ----");
            for(int i = 0; i < vertices; i++) {
                if(animales[i] != null){
                //impresión de la energía total del animal i
                System.out.println("id: " + animales[i].id + "|" + animales[i].especie + " --> energia total: " + animales[i].energia);
                System.out.println();
                }
            }
    }

    public void imprimirGrafoExtinto(LinkedList<Arista>[] lista) {
        calcularEnergiaFinal(this.adyacenciaCopia, this.animalesCopia);
        System.out.println("\n---- ESTRUCTURA DEL GRAFO ----");
        for(int i = 0; i < vertices; i++) {
            System.out.println(i + " " + animales[i].especie );
            for(Arista arista : lista[i]) {
                System.out.println("   |--> " + arista.destino.id + 
                                " (" + arista.destino.especie + 
                                ") [peso: " + (arista.peso*-1) + "]");
            }
            System.out.println();
        }
        
        System.out.println("\n---- ENERGIA DE LOS ANIMALES ----");
            for(int i = 0; i < vertices; i++) {
                if(animalesCopia[i] != null){
                System.out.println("id: " + animales[i].id + "|" + animalesCopia[i].especie + " --> energia total: " + animalesCopia[i].energia);
                System.out.println();
                }
            }
    }
    
   //pide el id del animal a eliminar
   public void eliminarAnimalDeCopia(int id){
       if(id < 0 || id >= vertices){
           //validación para evitar errores por un id fuera del rango
           System.out.println("ERROR: ANIMAL NO EXISTENTE EN LA CADENA TRÓFICA");
           return;
       }
       
       // 1. Eliminar aristas salientes de adjCopia
       //usa el comando clear para eliminar todas las aristas salientes del nodo con el id dado
       adyacenciaCopia[id].clear();
       //vuelve nulo el id del animal eliminado
       animalesCopia[id] = null;
       
       // 2. Eliminar aristas entrantes de adjCopia
       //elimina las aristas entrantes que tengan en común el id del animal eliminado
       for(int i = 0; i < vertices; i++){
           if(i != id){
               adyacenciaCopia[i].removeIf(arista -> arista.destino.id == id);
           }
       }
   }
   
   //Metodo de muerte en cadena
   public void muerteEnCadena(int primerMuerto){
        //algoritmo que ayudará a matar a medio ecosistema 
        //validación para evitar errores por un muerto que no existe
       if(primerMuerto >= vertices || primerMuerto < 0){
           System.out.println("ERROR: ID no valido para el metodo");
           return;
       }
       
       // Copiar adj a adjCopia al inicio
       for(int i = 0; i < vertices; i++){
           adyacenciaCopia[i].clear();
           for(Arista arista : adyacencia[i]){
               adyacenciaCopia[i].add(new Arista(arista.origen, arista.destino));
           }
       }
       //arreglo booleano para llevar el control de los animales muertos
       boolean[] losMuertos = new boolean[vertices];
       //asume que el primer animal ya esta muerto
       losMuertos[primerMuerto] = true;
       
       System.out.println("Comienza la extincion en cadena...");
       System.out.println("Primer muerto: " + animales[primerMuerto].especie);
       //se asume que alguien si se murió para entrar al while
       boolean seMurioAlguien = true;
       //contador para el numero de rondas 
       int ronda = 1;
       
       //se detiene cuando no mueran mas animales
       while(seMurioAlguien){
           //primero asume que no se murió nadie para tratar de demostrar lo contrario
           seMurioAlguien = false;
           //empieza a recorrer la copia
           System.out.println("\nRonda " + ronda + ":");
           
           //empieza a recorrer la copia
           for(int i = 0; i < vertices; i++){
               //validación para evitar matar a alguien que ya está muerto
               if(losMuertos[i]) {
                    continue;
                }
               //variable boolean para verificar si el animal es productor
               boolean esProductor = false;
               //recorre la lista de productores
               for(serVivo productor : productores){
                   //si encuentra un productor, el boolean se convierte en true;
                   if(productor.id == i){
                       esProductor = true;
                       //descansa el for
                       break;
                   }
               }
               //si es productor, sigue al siguiente animal;
               if(esProductor) continue;
               //boolean que verifica si un animal tiene comida;
               boolean tieneComida = false;
               
               for(int j = 0; j < vertices; j++){
                   //si el animal j se murió, sigue;
                   if(losMuertos[j]) continue;
                   //recorre las aristas del animal j
                   for(Arista arista : adyacenciaCopia[j]){
                       //si encuentra un animal que se coma a otro, tiene comida;
                       if(arista.destino.id == i){
                           tieneComida = true;
                           break;
                       }
                   }
                   //si ya tiene comida, descansa el for;
                   if(tieneComida) break;
               }
               //si no tiene comida, se procede a matarlo;
               if(!tieneComida){
                   //marca al animal i como muerto en el vector de muertos;
                   losMuertos[i] = true;
                   //marca que alguien si murió en la ronda;
                   seMurioAlguien = true;
                   //si nadie murió en la ronda, se avisa con el print;
                   System.out.println( animales[i].especie + 
                                    " se extingue por falta de alimento");
               }
           }
           //si nadie murió en la ronda, se avisa con el print;
           if(!seMurioAlguien){
               System.out.println("No hay mas extinciones en esta ronda");
           }
           //aumenta el contador de rondas;
           ronda++;
       }
       
       // Eliminar muertos de adyacenciaCopia
       for(int i = 0; i < vertices; i++){
           if(losMuertos[i]){
               adyacenciaCopia[i].clear();
               animalesCopia[i]  = null;
           }
       }
       //elimina las aristas entrantes que tengan en comun el animal muerto;
       for(int i = 0; i < vertices; i++){
           if(!losMuertos[i]){
               adyacenciaCopia[i].removeIf(arista -> losMuertos[arista.destino.id]);
           }
       }
       
       // Resumen
       //imprime un resumen de los animales extintos;
       System.out.println("\n" + "=".repeat(50));
       System.out.print("Especies extintas: ");
       int numeroMuertos = 0;
       //for para imprimir los animales muertos;
       for(int i = 0; i < vertices; i++){
           if(losMuertos[i]){
               System.out.print("| " + animales[i].especie + " | ");
               numeroMuertos++;
           }
       }
       System.out.println("\nTotal extintas: " + numeroMuertos);
       System.out.println("=".repeat(50));
   }
}
