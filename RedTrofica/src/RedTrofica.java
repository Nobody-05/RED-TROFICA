import java.util.Scanner; 

public class RedTrofica {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean continuarPrograma = true;
        
        while (continuarPrograma) {
            // Crear e inicializar la red trófica
            GrafoListaAdyacencia redTrofica = new GrafoListaAdyacencia(20, 5);
            inicializarRedTrofica(redTrofica);
            
            // Mostrar el grafo inicial
            redTrofica.imprimirGrafo();

            // Ciclo de eliminación de nodos
            boolean seguirEliminando = true;
            while (seguirEliminando) {
            // Opción de camino óptimo
            System.out.println("\nDesea ver el camino más óptimo entre los productores y un consumidor específico? (Si/No)");
            String respuestaCaminoOptimo = scanner.nextLine();
            
            if (respuestaCaminoOptimo.equalsIgnoreCase("Si")) {
                System.out.println("Ingrese el id del consumidor: ");
                int consumidorId = scanner.nextInt();
                scanner.nextLine();
                redTrofica.BellmanFord(consumidorId);   
            }

                System.out.println("\nDesea eliminar un nodo | extinción de especie? (Si/No)");
                String respuestaEliminar = scanner.nextLine();
                
                if (respuestaEliminar.equalsIgnoreCase("Si")) {
                    System.out.println("Ingrese el id del animal a eliminar: ");
                    int animalId = scanner.nextInt();
                    scanner.nextLine();
                    
                    redTrofica.muerteEnCadena(animalId);
                    redTrofica.imprimirGrafo();
                    
                    System.out.println("\n¿Qué desea hacer?");
                    System.out.println("1. Calcular flujo productor-consumidor");
                    System.out.println("2. Seguir eliminando nodos");
                    System.out.println("3. Reiniciar la simulación (red trófica completa)");
                    System.out.println("4. Salir del programa");
                    System.out.print("Opción: ");
                    
                    String opcion = scanner.nextLine();
                    
                    switch (opcion) {
                        case "1":
                            seguirEliminando = true;
                            break;
                        case "2":
                            seguirEliminando = true;
                            break;
                        case "3":
                            seguirEliminando = false;
                            break;
                        case "4":
                            seguirEliminando = false;
                            continuarPrograma = false;
                            break;    
                        default:
                            System.out.println("Opción inválida. Continuando...");
                            seguirEliminando = true;
                    }
                } else {
                    seguirEliminando = false;
                }
            }
            
            // Si no eligió salir, preguntar si quiere reiniciar
            if (continuarPrograma) {
                System.out.println("\n¿Desea reiniciar la simulación con una red trófica nueva? (Si/No)");
                String respuestaReiniciar = scanner.nextLine();
                continuarPrograma = respuestaReiniciar.equalsIgnoreCase("Si");
            }
        }
        
        System.out.println("\nGracias por usar el simulador de red tróficas. Hasta luego...");
        scanner.close();
    }
    
    // Método auxiliar para inicializar toda la red trófica
    private static void inicializarRedTrofica(GrafoListaAdyacencia redTrofica) {
        // Agregar productores
        redTrofica.agregarProductor(0, "Arbol de roble", 4200);
        redTrofica.agregarProductor(1, "Musgo", 210);
        redTrofica.agregarProductor(2, "Arbusto de bayas", 1400);
        redTrofica.agregarProductor(3, "Hierba", 700);
        redTrofica.agregarProductor(4, "Helecho", 490);
        
        // Agregar animales
        redTrofica.agregarAnimal(5, "Ardilla");
        redTrofica.agregarAnimal(6, "Raton");
        redTrofica.agregarAnimal(7, "Abeja");
        redTrofica.agregarAnimal(8, "Oso");
        redTrofica.agregarAnimal(9, "Conejo");
        redTrofica.agregarAnimal(10, "Venado");
        redTrofica.agregarAnimal(11, "Rana");
        redTrofica.agregarAnimal(12, "Zorro");
        redTrofica.agregarAnimal(13, "Mariposa");
        redTrofica.agregarAnimal(14, "Serpiente");
        redTrofica.agregarAnimal(15, "Bacteria Descomponedora");
        redTrofica.agregarAnimal(16, "Lobo");
        redTrofica.agregarAnimal(17, "Pajaro carpintero");
        redTrofica.agregarAnimal(18, "Aguila");
        redTrofica.agregarAnimal(19, "Hongo");
    
        // Crear referencias a los nodos
        serVivo arbolDeRoble = redTrofica.animales[0];
        serVivo musgo = redTrofica.animales[1];
        serVivo arbustoDeBayas = redTrofica.animales[2];
        serVivo hierba = redTrofica.animales[3]; 
        serVivo helecho = redTrofica.animales[4];
        serVivo ardilla = redTrofica.animales[5];
        serVivo raton = redTrofica.animales[6];
        serVivo abeja = redTrofica.animales[7];
        serVivo oso = redTrofica.animales[8];
        serVivo conejo = redTrofica.animales[9];
        serVivo venado = redTrofica.animales[10];
        serVivo rana = redTrofica.animales[11];
        serVivo zorro = redTrofica.animales[12];
        serVivo mariposa = redTrofica.animales[13];
        serVivo serpiente = redTrofica.animales[14];
        serVivo bacteriaDescomponedora = redTrofica.animales[15];
        serVivo lobo = redTrofica.animales[16];
        serVivo pajaroCarpintero = redTrofica.animales[17];
        serVivo aguila = redTrofica.animales[18];
        serVivo hongo = redTrofica.animales[19]; 
    
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

    }
}