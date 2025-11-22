public class AnimalPro extends serVivo{
    /*Constructor parametrizado el cual cuenta con la energia
    inicializada como parametro del constructor. Esto se debe
    a que los productores son los que iniciaran con el flujo
    de energia desde el nivel más bajo. Por lo cual estos
    empiezan con una energia ya preestablecida.*/
    public AnimalPro(int id, String especie, double energia){
        super(id, especie);
        this.energia = energia;
    }
}