package PropiasTP;

/**
 *
 * @author JoaquinNoillet
 */
public class Tren implements Comparable<Tren>{
    
    private int codigo;
    private String tipoPropulsion;
    private int cantVagonesPasajeros;
    private int cantVagonesCarga;
    private String lineaAsignada;
    
    public Tren(int codigo,String tipoPropulsion,int cantVagonesPasageros,int cantVagonesCarga,String lineaAsignada){
        this.codigo= codigo;
        this.tipoPropulsion=tipoPropulsion;
        this.cantVagonesPasajeros =cantVagonesPasageros;
        this.cantVagonesCarga = cantVagonesCarga;
        this.lineaAsignada = lineaAsignada;       
    }
    public int getCodigo() {
        return codigo;
    }
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
    public String getTipoPropulsion() {
        return tipoPropulsion;
    }
    public void setTipoPropulsion(String tipoPropulsion) {
        this.tipoPropulsion = tipoPropulsion;
    }
    public int getCantVagonesPasajeros() {
        return cantVagonesPasajeros;
    }
    public void setCantVagonesPasajeros(int cantVagonesPasageros) {
        this.cantVagonesPasajeros = cantVagonesPasageros;
    }
    public int getCantVagonesCarga() {
        return cantVagonesCarga;
    }
    public void setCantVagonesCarga(int cantVagonesCarga) {
        this.cantVagonesCarga = cantVagonesCarga;
    }
    public String getLineaAsignada() {
        return lineaAsignada;
    }
    public void setLineaAsignada(String lineaAsignada) {
        this.lineaAsignada = lineaAsignada;
    }

    @Override
    public boolean equals(Object obj) {
        final Tren aux= (Tren) obj;
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {//si son de distintas clases
            return false;
        }
        return this.codigo == aux.codigo;
    }

    @Override
    public int compareTo(Tren t) {
        return Integer.compare(this.codigo, t.codigo);
    }

    @Override
    public String toString() {
        return "Tren{" + "codigo=" + codigo + ", tipoPropulsion=" + tipoPropulsion + ", cantVagonesPasageros=" + cantVagonesPasajeros + ", cantVagonesCarga=" + cantVagonesCarga + ", lineaAsignada=" + lineaAsignada + '}';
    }
}
