package DiccionarioAVL;

/**
 *
 * @author JoaquinNoillet
 */
public class NodoDiccionario {

    private int altura;
    private Object elem;
    private Comparable<?> clave;
    private NodoDiccionario izq;
    private NodoDiccionario der;

    public NodoDiccionario(Object elto, Comparable clave, NodoDiccionario nodoIzq, NodoDiccionario nodoDer) {
        this.elem = elto;
        this.clave = clave;
        this.izq = nodoIzq;
        this.der = nodoDer;
        this.recalcularAltura();
    }

    public NodoDiccionario(Object e, Comparable c) {
        this.elem = e;
        this.clave = c;
        this.izq = null;
        this.der = null;
        this.altura = 0;
    }

    public NodoDiccionario() {
        this.elem = null;
        this.clave = null;
        this.der = null;
        this.izq = null;
        this.altura = 0;
    }

    public Object getElem() {
        return this.elem;
    }

    public void setElem(Object e) {
        this.elem = e;
    }

    public Comparable getClave() {
        return this.clave;
    }

    public void setClave(Comparable c) {
        this.clave = c;
    }

    public NodoDiccionario getIzq() {
        return this.izq;
    }

    public NodoDiccionario getDer() {
        return this.der;
    }

    public void setIzq(NodoDiccionario i) {
        this.izq = i;
    }

    public void setDer(NodoDiccionario d) {
        this.der = d;
    }

    public boolean esHoja() {
        return (this.izq == null) && (this.der == null);
    }

    public int getAltura() {
        return this.altura;
    }

    public void setAltura(int alt) {
        this.altura = alt;
    }

    public void recalcularAltura() {
        int alt1 = 0, alt2 = 0;
        if (this.izq != null) {
            alt1 = this.izq.getAltura() + 1;
        }
        if (this.der != null) {
            alt2 = this.der.getAltura() + 1;
        }
        this.setAltura(Math.max(alt1, alt2));
    }

    public int getBalance() {
        int altI = -1, altD = -1;
        if (this.izq != null) {
            altI = this.izq.getAltura();
        }
        if (this.der != null) {
            altD = this.der.getAltura();
        }
        return altI - altD;
    }
}
