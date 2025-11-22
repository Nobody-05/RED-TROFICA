public class Animal extends serVivo{
    /*Constructor parametrizado con inicialización
    de energia en 0, pues cualquier animal que no
    sea productor, recibe energia la cual sube desde
    los niveles inferiores donde estan los productores
    hasta los depredadores de más alto nivel.*/
    public Animal(int id, String especie){
        super(id, especie);
        this.energia = 0;
   }
}
