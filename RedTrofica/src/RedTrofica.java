import java.util.Scanner; 
public class RedTrofica {
    public static void main(String[] args) throws Exception {
        GrafoListaAdyacencia redTrofica = new GrafoListaAdyacencia(20, 5);
        //agregar nodos al vector animales, para tenerlos almacenadfos y posterior añadirlos al grafo
        Scanner scanner = new Scanner(System.in);
        boolean reiniciar = false;
        do{
            redTrofica.agregarProductor(0, "Arbol de roble", 4200);
            redTrofica.agregarProductor(1, "Musgo", 210);
            redTrofica.agregarProductor(2, "Arbusto de bayas", 1400);
            redTrofica.agregarProductor(3, "Hierba", 700);
            redTrofica.agregarProductor(4, "Helecho", 490);
            redTrofica.agregarAnimal(5, "ardilla");
            redTrofica.agregarAnimal(6, "Raton");
            redTrofica.agregarAnimal(7, "Abeja");
            redTrofica.agregarAnimal(8, "Oso");
            redTrofica.agregarAnimal(9, "conejo");
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
        
            //creación de los nodos como los elementos del grafo como tal 
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
            serVivo venado =redTrofica.animales[10];
            serVivo rana = redTrofica.animales[11];
            serVivo zorro = redTrofica.animales[12];
            serVivo mariposa = redTrofica.animales[13];
            serVivo serpiente = redTrofica.animales[14];
            serVivo bacteriaDescomponedora = redTrofica.animales[15];
            serVivo lobo = redTrofica.animales[16];
            serVivo pajaroCarpintero = redTrofica.animales[17];
            serVivo aguila = redTrofica.animales[18];
            serVivo hongo = redTrofica.animales[19]; 
        
            //creacion de las aristas con los nodos (animales)  creados anteriormente 
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
        
            redTrofica.imprimirGrafo(redTrofica.adj);


            System.out.println("desea ver el camnio más optimo entre los productores y un consumidor especifico? (s/n)");
            char respuestaCaminoOptimo = scanner.next().charAt(0);

            if (respuestaCaminoOptimo == 's' || respuestaCaminoOptimo == 'S') {
                System.out.println("Ingrese el ID del consumidor: ");
                int consumidorId = scanner.nextInt();
                redTrofica.BellamFord( consumidorId);   
            }

            System.out.println("desea eliminar un nodo (s/n)");
            char respuestaEliminar = scanner.next().charAt(0);
            if (respuestaEliminar == 's' || respuestaEliminar == 'S') {
                System.out.println("Ingrese el ID del animal a eliminar: ");
                int animalId = scanner.nextInt();
                redTrofica.eliminarAnimalDeCopia(animalId);
                redTrofica.muerteEnCadena(animalId);
                redTrofica.imprimirGrafoExtinto(redTrofica.adjCopia);
            }

            System.out.println("desea reiniciar la simulacion para eliminar otro primer nodo (s/n)");
            char respuestaReiniciar = scanner.next().charAt(0);
            if (respuestaReiniciar == 's' || respuestaReiniciar == 'S') {
                reiniciar = true;
            } else {
                reiniciar = false;
            }
        }while(reiniciar);
        boolean continuar;

        do {
           System.out.println("desea eliminar un nodo (s/n)");
            char respuestaEliminar = scanner.next().charAt(0);
            if (respuestaEliminar == 's' || respuestaEliminar == 'S') {
                System.out.println("Ingrese el ID del animal a eliminar: ");
                int animalId = scanner.nextInt();
                redTrofica.eliminarAnimalDeCopia(animalId);
                redTrofica.muerteEnCadena(animalId);
                redTrofica.imprimirGrafoExtinto(redTrofica.adjCopia);
            }
            System.out.println("desea continuar eliminando nodos (s/n)");
            char respuestaContinuar = scanner.next().charAt(0); 
            if (respuestaContinuar == 'n' || respuestaContinuar == 'N') {
                continuar = false;
            }
            else {
                continuar = true;
            }
        } while (continuar);
        scanner.close();
    }
}
