package PropiasTP;

/**
 *
 * @author JoaquinNoillet
 */
public class Estacion implements Comparable<Estacion> {
    private String nombre;
    private String calle;
    private int numero; //clave
    private String ciudad;
    private int codPostal;
    private int cantVias;
    private int cantPlataformas;
    
    public Estacion(Object n){
        this.nombre=(String) n;
    }
    
    public Estacion(String nombre,String calle,int numero,String ciudad,int codPostal,int cantVias,int cantPlataformas){
        this.nombre=nombre;
        this.calle=calle;
        this.numero=numero;
        this.ciudad=ciudad;
        this.codPostal=codPostal;
        this.cantVias=cantVias;
        this.cantPlataformas=cantPlataformas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public int getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(int codPostal) {
        this.codPostal = codPostal;
    }

    public int getCantVias() {
        return cantVias;
    }

    public void setCantVias(int cantVias) {
        this.cantVias = cantVias;
    }

    public int getCantPlataformas() {
        return cantPlataformas;
    }

    public void setCantPlataformas(int cantPlataformas) {
        this.cantPlataformas = cantPlataformas;
    }
    @Override
    public String toString(){
        return "\n nombre: "+this.nombre+
        " ,calle :"+this.calle+
        " ,numero: "+this.numero+
        " ,ciudad: "+this.ciudad+
        " ,codPostal: "+this.codPostal+
        " ,cantVias: "+this.cantVias+
        " ,cantPlataformas: "+this.cantPlataformas+"\n";
    }
    public int compareTo(Estacion o) {
        return Integer.compare(this.numero, o.numero);
    }
    public boolean equals(Comparable<?> o){
        Estacion c =(Estacion)o;
        return (this.nombre.equals(c.nombre) && this.numero==c.numero);
    }
}
