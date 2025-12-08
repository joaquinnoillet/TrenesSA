package DiccionarioAVL;

import lineales.dinamicas.Lista;

/**
 *
 * @author JoaquinNoillet
 */
public class Diccionario {

    private NodoDiccionario raiz;

    //Constructor
    public Diccionario() {
        raiz = null;
    }
    public boolean insertar(Object elemento, Comparable clave) {
        //Inserta un elemento y lo acomoda en el arbol lexicograficamente
        NodoDiccionario nuevo = new NodoDiccionario(elemento, clave);
        boolean res = true;
        if (this.esVacio()) {
            this.raiz = nuevo;
        } else {
            res = insertarRec(this.raiz, nuevo);
            this.raiz = this.rebalancear(this.raiz);
        }
        return res;
    }
    private boolean insertarRec(NodoDiccionario nodo, NodoDiccionario elemento) {
        boolean res = false;
        if (nodo != null) {
            if (nodo.getClave().compareTo(elemento.getClave()) > 0) { //si nodo es MAYOR que elemento.getClave() voy por Izq
                if (nodo.getIzq() == null) { //si su hijo Izq es null
                    nodo.setIzq(elemento);
                    res = true;
                } else { //si su hijo Izq NO es null, recursividad por Izq
                    res = insertarRec(nodo.getIzq(), elemento);
                    nodo.setIzq(this.rebalancear(nodo.getIzq())); // cunado vuelve del retono valancea
                }
            } else if (nodo.getClave().compareTo(elemento.getClave()) < 0) { //si nodo es MENOR que elemento.getClave() voy por Der
                if (nodo.getDer() == null) { //si su hijo Derecho es null
                    nodo.setDer(elemento);
                    res = true;
                } else { //si su hijo Derecho NO es null, recursividad por Derecha
                    res = insertarRec(nodo.getDer(), elemento);
                    nodo.setDer(this.rebalancear(nodo.getDer())); // cunado vuelve del retono valancea
                }
            }
            nodo.recalcularAltura();
        }
        return res;
    }
    public boolean eliminar(Comparable clave) {
        boolean res = false;
        if (!this.esVacio()) {
            res = elimRec(this.raiz,this.raiz,clave);
            this.raiz = this.rebalancear(this.raiz);
        }
        return res;
    }
    private boolean elimRec(NodoDiccionario nodo, Comparable clave) {
        boolean res = false;
        if (nodo != null) {
            if (nodo.getClave().compareTo(clave) > 0) { //si nodo es MAYOR que elemento.getClave() voy por Izq
                res = elimRec(nodo.getIzq(), clave);
                nodo.setIzq(this.rebalancear(nodo.getIzq())); // cunado vuelve del retono valancea
            } else if (nodo.getClave().compareTo(clave) < 0) { //si nodo es MAYOR que elemento.getClave() voy por Der
                res = elimRec(nodo.getDer(), clave);
                nodo.setDer(this.rebalancear(nodo.getDer())); // cunado vuelve del retono valancea
            } else { //nodo es igual a clave
                System.out.println("se encontro el nodo ");               
                if (nodo.esHoja()) { //caso 1: nodo es hoja
                    System.out.println("AAAAA");
                    nodo = null;
                    System.out.println(nodo);
                } else if (nodo.getIzq() == null) { //caso 2: nodo tiene solo hijo derecho
                    nodo = nodo.getDer();
                } else if (nodo.getDer() == null) { //caso 3: nodo tiene solo hijo izquierdo
                    nodo = nodo.getIzq();
                } else { //caso 4: nodo tiene dos hijos
                    NodoDiccionario nodoCandidato = encontrarMinimo(nodo.getDer()); //busco el candidato nimino por Derecha
                    System.out.println("nodo candidato: "+nodoCandidato.getElem());
                    nodo.setElem(nodoCandidato.getElem());
                    nodo.setClave(nodoCandidato.getClave());
                    elimRec(nodo.getDer(), nodoCandidato.getClave()); //elimino el nodo candidato que aun esta en el fondo del arbol
                    //nodo.recalcularAltura(); //recalculo altura antes de rebalancear (creo que esta de mas)
                    nodo.setDer(this.rebalancear(nodo.getDer())); // cunado vuelve del retono valancea
                }
                res = true;
            }
            if (nodo!=null) {
                nodo.recalcularAltura(); //recalculo altura cada vez que vuelvo de la recursividad
            }
        }
        return res;
    }

    private boolean elimRec(NodoDiccionario nodoAnterior,NodoDiccionario nodo, Comparable clave) {
        boolean res = false;
        if (nodo != null) {
            if (nodo.getClave().compareTo(clave) > 0) { //si nodo es MAYOR que elemento.getClave() voy por Izq
                res = elimRec(nodo,nodo.getIzq(), clave);
                nodoAnterior.setIzq(this.rebalancear(nodoAnterior.getIzq())); // cunado vuelve del retono valancea
            } else if (nodo.getClave().compareTo(clave) < 0) { //si nodo es MAYOR que elemento.getClave() voy por Der
                res = elimRec(nodo,nodo.getDer(), clave);
                nodoAnterior.setDer(this.rebalancear(nodoAnterior.getDer())); // cunado vuelve del retono valancea
            } else { //nodo es igual a clave
                System.out.println("se encontro el nodo");               
                if (nodo.esHoja()) {//caso 1: nodo es hoja
                    if (nodoAnterior.getDer().getClave().compareTo(clave)==0) {
                        nodoAnterior.setDer(null);
                    } else {
                        nodoAnterior.setIzq(null); 
                    }
                    //nodo = null;
                } else if (nodo.getIzq() == null) { //caso 2: nodo tiene solo hijo derecho
                    nodoAnterior.setDer(nodo.getDer());
                    nodo.setDer(null);
                } else if (nodo.getDer() == null) { //caso 3: nodo tiene solo hijo izquierdo
                    nodoAnterior.setIzq(nodo.getIzq());;
                    nodo.setIzq(null);
                } else { //caso 4: nodo tiene dos hijos
                    NodoDiccionario nodoCandidato = encontrarMinimo(nodo.getDer()); //busco el candidato nimino por Derecha
                    System.out.println("nodo candidato: "+nodoCandidato.getElem());
                    nodo.setElem(nodoCandidato.getElem());
                    nodo.setClave(nodoCandidato.getClave());
                    System.out.println(this.raiz.toString());
                    elimRec(nodo,nodo.getDer(), nodoCandidato.getClave()); //elimino el nodo candidato que aun esta en el fondo del arbol
                    nodo.recalcularAltura(); //recalculo altura antes de rebalancear (creo que esta de mas)
                    nodo.setDer(this.rebalancear(nodo.getDer())); // cunado vuelve del retono valancea
                }
                res = true;
            }
            if (nodo!=null) {
                nodo.recalcularAltura(); //recalculo altura cada vez que vuelvo de la recursividad
            }
        }
        return res;
    }

    private NodoDiccionario rebalancear(NodoDiccionario nodo) {
        NodoDiccionario nodoRet = nodo;
        int balance;
        if(nodo!=null){
        if (!nodo.esHoja()) {
            balance = nodo.getBalance();
            if (balance < -1) {
                if (nodo.getDer().getBalance() <= 0) {
                    nodoRet = this.rotSimpleIzq(nodo);
                } else {
                    nodoRet = this.rotDobleIzq(nodo);
                }

            } else if (balance > 1) {
                if (nodo.getIzq().getBalance() >= 0) {
                    nodoRet = this.rotSimpleDer(nodo);
                } else {
                    nodoRet = this.rotDobleDer(nodo);
                }
            }
        }
        }
        return nodoRet;
    }

    private NodoDiccionario rotSimpleDer(NodoDiccionario raizRec) {
        //Solo se puede aplicar si el nodo pasado por parametro tiene un HI
        System.out.println("realiza rotacion simple a Derecha con pivote: "+ raizRec.getClave());
        NodoDiccionario nuevaRaiz = new NodoDiccionario(raizRec.getIzq().getElem(), raizRec.getIzq().getClave(), raizRec.getIzq().getIzq(), raizRec);
        raizRec.setIzq(raizRec.getIzq().getDer());
        if (nuevaRaiz.getIzq() != null) {
            nuevaRaiz.getIzq().recalcularAltura();
        }
        if (nuevaRaiz.getDer() != null) {
            nuevaRaiz.getDer().recalcularAltura();
        }
        nuevaRaiz.recalcularAltura();
        raizRec.recalcularAltura();
        return nuevaRaiz;
    }

    private NodoDiccionario rotSimpleIzq(NodoDiccionario raizRec) {
        //Solo se puede aplicar si [raizRec] tiene un hijo derecho
        System.out.println("realiza rotacion simple a Izquierda con pivote: "+ raizRec.getClave());
        NodoDiccionario nuevaRaiz = new NodoDiccionario(raizRec.getDer().getElem(), raizRec.getDer().getClave(), raizRec, raizRec.getDer().getDer());
        raizRec.setDer(raizRec.getDer().getIzq());
        if (nuevaRaiz.getIzq() != null) {
            nuevaRaiz.getIzq().recalcularAltura();
        }
        if (nuevaRaiz.getDer() != null) {
            nuevaRaiz.getDer().recalcularAltura();
        }
        nuevaRaiz.recalcularAltura();
        raizRec.recalcularAltura();
        return nuevaRaiz;
    }

    private NodoDiccionario rotDobleDer(NodoDiccionario raizRec) {
        //Solo se puede aplicar si [raizRec] tiene un hijo izquierdo que tenga un hijo derecho
        System.out.println("realiza rotacion Doble a Derecha con pivote: "+ raizRec.getClave());
        raizRec.setIzq(this.rotSimpleIzq(raizRec.getIzq()));
        raizRec = this.rotSimpleDer(raizRec);
        return raizRec;
    }

    private NodoDiccionario rotDobleIzq(NodoDiccionario raizRec) {
        System.out.println("realiza rotacion Doble a Derecha con pivote: "+ raizRec.getClave());
        //Solo se puede aplicar si [raizRec] tiene un hijo derecho que tenga un hijo izquierdo
        raizRec.setDer(this.rotSimpleDer(raizRec.getDer()));
        raizRec = this.rotSimpleIzq(raizRec);
        return raizRec;
    }

    //Observadoras
    public Lista listarRango(Comparable min, Comparable max) {
        Lista ret = null;
        if ((!this.esVacio()) && (min.compareTo(max)) <= 0) {
            ret = new Lista();
            rangoRec(min, max, this.raiz, ret);
        }
        return ret;
    }

    private void rangoRec(Comparable min, Comparable max, NodoDiccionario r, Lista l) {
        int esMin, esMax;
        if (r != null) {
            esMin = r.getClave().compareTo(min);
            esMax = r.getClave().compareTo(max);
            if (esMax < 0) {
                rangoRec(min, max, r.getDer(), l);
            }
            if ((esMin >= 0) && (esMax <= 0)) {
                l.insertar(r.getElem(), 1);
            }
            if (esMin > 0) {
                rangoRec(min, max, r.getIzq(), l);
            }

        }
    }

    public Lista listarElementos() {
        Lista datos = new Lista();
        listaElementoRec(this.raiz, datos);
        return datos;
    }

    private void listaElementoRec(NodoDiccionario n, Lista datos) {
        //recorrido inorden muestra ordenadamente el arbol
        if (n != null) {
            listaElementoRec(n.getIzq(), datos);
            datos.insertar(n.getElem(), datos.longitud() + 1);
            listaElementoRec(n.getDer(), datos);
        }
    }

    public Lista listarElementosInOrdenInverso() {
        Lista datos = new Lista();
        listaElementoInOrdenInversoAux(this.raiz, datos);
        return datos;
    }

    private void listaElementoInOrdenInversoAux(NodoDiccionario n, Lista datos) {
        //recorrido inorden muestra ordenadamente el arbol
        if (n != null) {
            listaElementoInOrdenInversoAux(n.getDer(), datos);
            datos.insertar(n.getElem(), datos.longitud() + 1);
            listaElementoInOrdenInversoAux(n.getIzq(), datos);
        }
    }

    public Lista listarClaves() {
        Lista claves = new Lista();
        listarClavesAux(this.raiz, claves);
        return claves;
    }

    private void listarClavesAux(NodoDiccionario n, Lista claves) {
        //recorrido inorden muestra ordenadamente el arbol
        if (n != null) {
            listarClavesAux(n.getIzq(), claves);
            claves.insertar(n.getClave(), claves.longitud() + 1);
            listarClavesAux(n.getDer(), claves);
        }
    }

        public Lista listarClavesPorPrefijo(Comparable prefijo) {
        Lista clavesConPrefijo = new Lista();
        listarClavesPorPrefijoAux(this.raiz, prefijo, clavesConPrefijo);
        return clavesConPrefijo;
    }

    private void listarClavesPorPrefijoAux(NodoDiccionario n,Comparable prefijo, Lista claves) {
        //
        if (n != null) {
            listarClavesPorPrefijoAux(n.getIzq(), prefijo, claves);
            if (((String) n.getClave()).toLowerCase().startsWith(((String) prefijo).toLowerCase())) {
                claves.insertar(n.getClave(), claves.longitud() + 1);
            }
            listarClavesPorPrefijoAux(n.getDer(), prefijo, claves);
        }
    }

    public Lista listarClavesInvertido() {
        Lista claves = new Lista();
        listarClavesInvertido(this.raiz, claves);
        return claves;
    }

    private void listarClavesInvertido(NodoDiccionario n, Lista claves) {
        if (n != null) {
            listarClavesInvertido(n.getDer(), claves);
            claves.insertar(n.getClave(), claves.longitud() + 1);
            listarClavesInvertido(n.getIzq(), claves);
        }
    }

    public boolean esVacio() {
        return this.raiz == null;
    }

    public Object obtenerDato(Comparable clave) {
        //Devuelve la Object con el nombre a buscar, o null si no existe
        Object resultado = null;
        if (!this.esVacio()) {
            resultado = obtenerDatoAux(clave, this.raiz);
        }
        return resultado;
    }

    private Object obtenerDatoAux(Comparable clave, NodoDiccionario raizRec) {
        Object aux = null;
        if (raizRec != null) {
            int numeroComparador = raizRec.getClave().compareTo(clave);
            if (numeroComparador == 0) {
                aux = raizRec.getElem();
            } else if (numeroComparador < 0) {
                aux = this.obtenerDatoAux(clave, raizRec.getDer());
            } else {
                aux = this.obtenerDatoAux(clave, raizRec.getIzq());
            }
        }
        return aux;
    }
          
     public String toString() {
        String s = "";
        if (this.raiz != null) {
            s = stringAux(this.raiz);
        } else {
            s = "arbol vacio";
        }
        return s;
    }
    private String stringAux(NodoDiccionario n) {
        String s = "[Altura: "+ n.getAltura()+"] " + n.getClave();
        if (n.getIzq() != null) {
            s = s + " - HI: " + n.getIzq().getClave();
        } else {
            s = s + " - HI: #";
        }
        if (n.getDer() != null) {
            s = s + " - HD: " + n.getDer().getClave() + "\n";
        } else {
            s = s + " - HD: #" + "\n";
        }
        if (n.getIzq() != null) {
            s += stringAux(n.getIzq());
        }
        if (n.getDer() != null) {
            s += stringAux(n.getDer());
        }
        return s;
    }

    private NodoDiccionario encontrarMinimo(NodoDiccionario raizRec) {
        NodoDiccionario aux;
        if (raizRec.getIzq() != null) {
            aux = encontrarMinimo(raizRec.getIzq());
        } else {
            aux = raizRec;
        }
        return aux;
    }

    //private NodoDiccionario maxRec(NodoDiccionario raizRec) {
    //    NodoDiccionario aux;
    //    if (raizRec.getDer() != null) {
    //        aux = maxRec(raizRec.getDer());
    //    } else {
    //        aux = raizRec;
    //    }
    //    return aux;
    //}
}
