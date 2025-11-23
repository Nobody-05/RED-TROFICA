import java.util.Scanner; 

public class RedTrofica {
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
        redTrofica.agregarProductor(0, "Arbol de roble", 1500);
        redTrofica.agregarProductor(1, "Musgo", 210);
        redTrofica.agregarProductor(2, "Arbusto de bayas", 1200);
        redTrofica.agregarProductor(3, "Hierba", 700);
        redTrofica.agregarProductor(4, "Helecho", 490);
        
        // Agregar animales
        redTrofica.agregarAnimal(5, "Mariposa");
        redTrofica.agregarAnimal(6, "Ardilla");
        redTrofica.agregarAnimal(7, "Raton");
        redTrofica.agregarAnimal(8, "Abeja");
        redTrofica.agregarAnimal(9, "Jabali");
        redTrofica.agregarAnimal(10, "Venado");
        redTrofica.agregarAnimal(11, "Pajaro carpintero");
        redTrofica.agregarAnimal(12, "Rana");  
        redTrofica.agregarAnimal(13, "Oso");
        redTrofica.agregarAnimal(14, "Conejo");
        redTrofica.agregarAnimal(15, "Coyote");
        redTrofica.agregarAnimal(16, "Serpiente");
        redTrofica.agregarAnimal(17, "Buitre cabecirrojo");
        redTrofica.agregarAnimal(18, "Aguila");
        redTrofica.agregarAnimal(19, "Zorro");
        redTrofica.agregarAnimal(20, "Lobo");
        redTrofica.agregarAnimal(21, "Hongo");
        redTrofica.agregarAnimal(22, "Bacteria Descomponedora");
        
    
        // Crear referencias a los nodos
        serVivo arbolDeRoble = redTrofica.animales[0];
        serVivo musgo = redTrofica.animales[1];
        serVivo arbustoDeBayas = redTrofica.animales[2];
        serVivo hierba = redTrofica.animales[3]; 
        serVivo helecho = redTrofica.animales[4];
        serVivo mariposa = redTrofica.animales[5];
        serVivo ardilla = redTrofica.animales[6];
        serVivo raton = redTrofica.animales[7];
        serVivo abeja = redTrofica.animales[8];
        serVivo jabali = redTrofica.animales[9];
        serVivo venado = redTrofica.animales[10];
        serVivo pajaroCarpintero = redTrofica.animales[11];
        serVivo rana = redTrofica.animales[12];
        serVivo oso = redTrofica.animales[13];
        serVivo conejo = redTrofica.animales[14];
        serVivo coyote = redTrofica.animales[15];
        serVivo serpiente = redTrofica.animales[16];
        serVivo buitreCabecirrojo = redTrofica.animales[17];
        serVivo aguila = redTrofica.animales[18];
        serVivo zorro = redTrofica.animales[19];
        serVivo lobo = redTrofica.animales[20];
        serVivo hongo = redTrofica.animales[21];
        serVivo bacteriaDescomponedora = redTrofica.animales[22];
    
        // Crear aristas (relaciones tróficas)
        redTrofica.agregarArista(arbolDeRoble, ardilla);
        redTrofica.agregarArista(arbolDeRoble, raton);
        redTrofica.agregarArista(arbolDeRoble, mariposa);
        redTrofica.agregarArista(musgo, raton);
        redTrofica.agregarArista(arbustoDeBayas, abeja);
        redTrofica.agregarArista(arbustoDeBayas, oso);
        redTrofica.agregarArista(hierba, conejo);
        redTrofica.agregarArista(helecho, venado);
        redTrofica.agregarArista(ardilla, aguila);
        redTrofica.agregarArista(raton, serpiente);
        redTrofica.agregarArista(abeja, rana);
        redTrofica.agregarArista(oso, bacteriaDescomponedora);
        redTrofica.agregarArista(conejo, zorro);
        redTrofica.agregarArista(venado, lobo);
        redTrofica.agregarArista(mariposa, pajaroCarpintero);
        redTrofica.agregarArista(rana, serpiente);
        redTrofica.agregarArista(zorro, bacteriaDescomponedora);
        redTrofica.agregarArista(zorro, lobo);
        redTrofica.agregarArista(pajaroCarpintero, aguila);
        redTrofica.agregarArista(serpiente, aguila);
        redTrofica.agregarArista(lobo, hongo);
        redTrofica.agregarArista(aguila, hongo);
        redTrofica.agregarArista(arbustoDeBayas, jabali);
        redTrofica.agregarArista(hierba, jabali);
        redTrofica.agregarArista(helecho, jabali);
        redTrofica.agregarArista(buitreCabecirrojo, aguila);
        redTrofica.agregarArista(jabali, oso);
        redTrofica.agregarArista(jabali, lobo);
        redTrofica.agregarArista(venado, oso);
        redTrofica.agregarArista(venado, coyote);
        redTrofica.agregarArista(conejo, lobo);
        redTrofica.agregarArista(coyote, lobo);
        redTrofica.agregarArista(coyote, buitreCabecirrojo);
        redTrofica.agregarArista(rana, aguila);
        redTrofica.agregarArista(arbustoDeBayas, conejo);
        redTrofica.agregarArista(conejo, aguila);
    }
}