public class RedTrofica {
    public static void main(String[] args) throws Exception {
        GrafoListaAdyacencia RedTrofica = new GrafoListaAdyacencia(20);
        //agregar nodos al vector animales, para tenerlos almacenadfos y posterior añadirlos al grafo
        RedTrofica.agregarProductor(0, "Arbol de roble", 100);
        RedTrofica.agregarProductor(1, "musgo", 500);
        RedTrofica.agregarProductor(2, "Arbusto de bayas", 100);
        RedTrofica.agregarProductor(3, "hierba", 100);
        RedTrofica.agregarProductor(4, "Helecho", 100);
        RedTrofica.agregarAnimal(5, "ardilla");
        RedTrofica.agregarAnimal(6, "Raton");
        RedTrofica.agregarAnimal(7, "Abeja");
        RedTrofica.agregarAnimal(8, "Oso");
        RedTrofica.agregarAnimal(9, "conejo");
        RedTrofica.agregarAnimal(10, "Venado");
        RedTrofica.agregarAnimal(11, "Rana");
        RedTrofica.agregarAnimal(12, "Zorro");
        RedTrofica.agregarAnimal(13, "Mariposa");
        RedTrofica.agregarAnimal(14, "Serpiente");
        RedTrofica.agregarAnimal(15, "Bacteria Descomponedora");
        RedTrofica.agregarAnimal(16, "Lobo");
        RedTrofica.agregarAnimal(17, "Pajaro carpintero");
        RedTrofica.agregarAnimal(18, "Aguila");
        RedTrofica.agregarAnimal(19, "Hongo");
        
        //creación de los nodos como los elementos del grafo como tal 
        
        serVivo arbolDeRoble = RedTrofica.animales[0];
        serVivo musgo = RedTrofica.animales[1];
       // Animal arbustoDeBayas = RedTrofica.animales[2];
        //Animal hierba = RedTrofica.animales[3]; 
        //Animal helecho = RedTrofica.animales[4];
        serVivo ardilla = RedTrofica.animales[5];
        serVivo raton = RedTrofica.animales[6];
        //Animal abeja = RedTrofica.animales[7];
        //Animal oso = RedTrofica.animales[8];
        //Animal conejo = RedTrofica.animales[9];
        //Animal venado =RedTrofica.animales[10];
        //Animal rana = RedTrofica.animales[11];
        //Animal zorro = RedTrofica.animales[12];
        serVivo mariposa = RedTrofica.animales[13];
        serVivo serpiente = RedTrofica.animales[14];
        //Animal bacteriaDescomponedora = RedTrofica.animales[15];
        //Animal lobo = RedTrofica.animales[16];
        serVivo pajaroCarpintero = RedTrofica.animales[17];
        serVivo aguila = RedTrofica.animales[18];
        serVivo hongo = RedTrofica.animales[19]; 
        
        //creacion de las aristas con los nodos (animales)  creados anteriormente 
        
        RedTrofica.agregarArista(arbolDeRoble, ardilla);
        RedTrofica.agregarArista(arbolDeRoble, raton);
        RedTrofica.agregarArista(arbolDeRoble, mariposa);
        RedTrofica.agregarArista(musgo, raton);
        //RedTrofica.agregarArista(arbustoDeBayas, abeja);
        //RedTrofica.agregarArista(hierba, conejo);
        //RedTrofica.agregarArista(helecho, venado);
        RedTrofica.agregarArista(ardilla, aguila);
        RedTrofica.agregarArista(raton, serpiente);
        //RedTrofica.agregarArista(abeja, rana);
        //RedTrofica.agregarArista(oso, bacteriaDescomponedora);
        //RedTrofica.agregarArista(conejo, zorro);
        //RedTrofica.agregarArista(venado, lobo);
        RedTrofica.agregarArista(mariposa, pajaroCarpintero);
        //RedTrofica.agregarArista(rana, serpiente);
        //RedTrofica.agregarArista(zorro, bacteriaDescomponedora);
        //RedTrofica.agregarArista(zorro, lobo);
        RedTrofica.agregarArista(pajaroCarpintero, aguila);
        RedTrofica.agregarArista(serpiente, aguila);
        //RedTrofica.agregarArista(lobo, hongo);
        //RedTrofica.agregarArista(aguila, hongo);
        
        RedTrofica.imprimirGrafo();
        
        RedTrofica.BellamFord(0, 18);
    }
}
