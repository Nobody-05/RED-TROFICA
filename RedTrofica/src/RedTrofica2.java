import java.util.Scanner;

public record RedTrofica2() {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean continuarPrograma = true;

        // Saludo inicial
        System.out.println("╔════════════════════════════════════════════════════════════╗");
        System.out.println("║        BIENVENIDO AL SIMULADOR DE REDES TRÓFICAS           ║");
        System.out.println("║                                                            ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");

        while (continuarPrograma) {
            // Crear e inicializar la red trófica
            GrafoListaAdyacencia redTrofica = new GrafoListaAdyacencia(23, 5);
            inicializarRedTrofica(redTrofica);
            
            // Mostrar el grafo inicial
            System.out.println("\n--- RED TRÓFICA INICIAL ---");
            redTrofica.imprimirGrafo();
            
            // Menú principal
            boolean volverAlMenu = true;
            while (volverAlMenu) {
                System.out.println("\n╔════════════════════════════════════════════════════════════╗");
                System.out.println("║                    MENÚ PRINCIPAL                          ║");
                System.out.println("╠════════════════════════════════════════════════════════════╣");
                System.out.println("║ 1. Ver camino optimo productor-consumidor                  ║");
                System.out.println("║ 2. Eliminar nodo (simular extinción)                       ║");
                System.out.println("║ 3. Reiniciar simulación (red trófica completa)             ║");
                System.out.println("║ 4. Salir del programa                                      ║");
                System.out.println("╚════════════════════════════════════════════════════════════╝");
                System.out.print("Seleccione una opción: ");
                
                String opcion = scanner.nextLine();
                
                switch (opcion) {
                    case "1":
                        // Camino óptimo
                        System.out.println("\n--- ANALISIS DE CAMINO ÓPTIMO ---");
                        System.out.print("Ingrese el ID del consumidor: ");
                        int consumidorId = scanner.nextInt();
                        scanner.nextLine(); 
                        redTrofica.BellmanFord(consumidorId);
                        break;
                        
                    case "2":
                        // Eliminar nodo
                        System.out.println("\n--- SIMULACIÓN DE EXTINCIÓN ---");
                        System.out.print("Ingrese el ID del animal a eliminar: ");
                        int animalId = scanner.nextInt();
                        scanner.nextLine();
                        redTrofica.muerteEnCadena(animalId);
                        redTrofica.imprimirGrafo();
                        break;
                        
                    case "3":
                        // Reiniciar
                        System.out.println("\nReiniciando simulación...");
                        volverAlMenu = false;
                        break;
                        
                    case "4":
                        // Salir
                        volverAlMenu = false;
                        continuarPrograma = false;
                        break;
                        
                    default:
                        System.out.println("\n Opción invalida. Por favor seleccione una opcion del 1 al 4.");
                }
            }
        }

        System.out.println("\n╔════════════════════════════════════════════════════════════╗");
        System.out.println("║   Gracias por usar el Simulador de Redes Tróficas          ║");
        System.out.println("║   Hasta luego                                              ║");
        System.out.println("╚════════════════════════════════════════════════════════════╝\n");
        scanner.close();
    }

    
    // Método auxiliar para inicializar toda la red trófica
    private static void inicializarRedTrofica(GrafoListaAdyacencia redTrofica) {
        // Agregar productores
        redTrofica.agregarProductor(0, "pino", 300);
        redTrofica.agregarProductor(1, "pasto", 50);
        redTrofica.agregarProductor(2, "Roble", 80);

        
        // Agregar consumidores primerarios
        redTrofica.agregarAnimal(3, "venado");
        redTrofica.agregarAnimal(4, "conejo");
        redTrofica.agregarAnimal(5, "oruga");
        redTrofica.agregarAnimal(6, "ardilla");

        // Agregar consumidores secundarios y terciarios
        redTrofica.agregarAnimal(7, "lobo");
        redTrofica.agregarAnimal(8, "zorro");
        redTrofica.agregarAnimal(9, "Buho"); 

        // Agregar descomponedores
        redTrofica.agregarAnimal(10, "HONGO");
        
    
        // Crear referencias a los nodos
        serVivo pino = redTrofica.animales[0];
        serVivo pasto = redTrofica.animales[1];
        serVivo roble = redTrofica.animales[2];
        serVivo venado = redTrofica.animales[3];
        serVivo conejo = redTrofica.animales[4];
        serVivo oruga = redTrofica.animales[5];
        serVivo ardilla = redTrofica.animales[6];
        serVivo lobo = redTrofica.animales[7];
        serVivo zorro = redTrofica.animales[8];
        serVivo buho = redTrofica.animales[9];
        serVivo hongo = redTrofica.animales[10];
    
        // Crear aristas (relaciones tróficas)
        redTrofica.agregarArista(pino, oruga);
        redTrofica.agregarArista(pasto, conejo);
        redTrofica.agregarArista(pasto, venado);
        redTrofica.agregarArista(oruga, ardilla);
        redTrofica.agregarArista(roble, ardilla);
        redTrofica.agregarArista(ardilla, zorro);
        redTrofica.agregarArista(ardilla, buho);
        redTrofica.agregarArista(conejo, zorro);
        redTrofica.agregarArista(venado, lobo);
        redTrofica.agregarArista(zorro, lobo);
        redTrofica.agregarArista(lobo, hongo);
        redTrofica.agregarArista(buho, hongo);
        redTrofica.agregarArista(zorro, buho);
    }
}
