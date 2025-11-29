package grafos;

/**
 *
 * @author JoaquinNoillet
 */
import lineales.dinamicas.Cola;
import lineales.dinamicas.Lista;
import java.util.HashMap;
//import java.util.LinkedHashMap;
//import java.util.Map;

public class GrafoEtiquetado {

    private NodoVerticeEtiquetado inicio;

    public GrafoEtiquetado() {
        this.inicio = null;
    }

    public boolean insertarVertice(Object element) {//Nodo nuevo
        boolean retorno = true;
        NodoVerticeEtiquetado nodo = this.inicio;
        if (nodo == null) {
            this.inicio = new NodoVerticeEtiquetado(element, null, null);
            retorno = true;
        } else {
            while (retorno && nodo.getSigVertice() != null) {
                if (nodo.getElemento().equals(element)) {
                    retorno = false;
                }
                nodo = nodo.getSigVertice();
            }
            if (retorno) { // si es true
                nodo.setSigVertice(new NodoVerticeEtiquetado(element, null, null)); //se agrega nuevo nodo vertice
                retorno = true;
            }
        }
        return retorno;
    }//check

    private boolean insertarAdyacente(NodoVerticeEtiquetado nodo, NodoVerticeEtiquetado Nodoadyacente, double etiqueta) {
        boolean res = false;
        if (nodo != null) {
            NodoAdyacenteEtiquetado aux = nodo.getAdyacente(); //puntero nodoAdyAux
            if (aux != null) {
                boolean control = true; // esta condicion se podria quitar
                while (aux.getSiguienteAdy() != null && control) {
                    aux = aux.getSiguienteAdy();
                }
                aux.setSiguienteAdy(new NodoAdyacenteEtiquetado(Nodoadyacente, null, etiqueta));
                res = true;
            } else {
                nodo.setAdyacente(new NodoAdyacenteEtiquetado(Nodoadyacente, null, etiqueta));
                res = true;
            }
        }
        return res;
    }

    private HashMap<Object, NodoVerticeEtiquetado> ubicarVertices(Object elemA, Object elemB) {
        //retorna un hash con los nodos que coincidan con los elementosque recive por parametro
        boolean corte = false;
        HashMap<Object, NodoVerticeEtiquetado> retorno = new HashMap<>();
        NodoVerticeEtiquetado aux = this.inicio; //puntero auxiliar

        while (aux != null && !corte) {
            if (aux.getElemento().equals(elemA)) {
                NodoVerticeEtiquetado verticeA = aux;
                retorno.put(elemA, verticeA);
            } else if (aux.getElemento().equals(elemB)) {
                NodoVerticeEtiquetado verticeB = aux;
                retorno.put(elemB, verticeB);
            }

            if (retorno.get(elemA) != null && retorno.get(elemB) != null) {
                corte = true;
            }
            aux = aux.getSigVertice();
        }
        return retorno;
    }//check

    public boolean eliminarVertice(Object elemento) {
        boolean retorno = false;
        NodoVerticeEtiquetado nodo = this.inicio;
        if (nodo.getElemento().equals(elemento)) {
            retorno = this.eliminarDeAdyacentes(nodo, nodo); //elimina los adyacentes que apunten a  dicho nodo a eliminar
            this.inicio = nodo.getSigVertice(); //elimina el nodo, va a trash
        } else {
            NodoVerticeEtiquetado sigVertice = nodo.getSigVertice();
            while (sigVertice != null && !sigVertice.getElemento().equals(elemento)) { //si el nodo a eleiminar no es el primero, recorre los nodos verties
                sigVertice = sigVertice.getSigVertice();
                nodo = nodo.getSigVertice();
            }
            if (sigVertice != null) {// si encontro el nodo vertice a eliminar, procede a quitar sus adyacentes
                retorno = this.eliminarDeAdyacentes(sigVertice, sigVertice);
                nodo.setSigVertice(sigVertice.getSigVertice()); //elimina el nodo, va a trash
            }
        }
        return retorno;
    }//check

    private boolean eliminarDeAdyacentes(NodoVerticeEtiquetado vertice, NodoVerticeEtiquetado elemento) {
        boolean retorno = false;
        NodoAdyacenteEtiquetado adyacente = vertice.getAdyacente();
        while (adyacente != null) {
            retorno = this.eliminarAdyacente(adyacente.getVertice(), elemento);
            adyacente = adyacente.getSiguienteAdy();
        }
        return retorno;
    }

    private boolean eliminarAdyacente(NodoVerticeEtiquetado nodoA, NodoVerticeEtiquetado nodoB) {
        boolean res = false;
        if (nodoA != null) {
            NodoAdyacenteEtiquetado adyacente = nodoA.getAdyacente();
            if (adyacente.getVertice().equals(nodoB)) {
                nodoA.setAdyacente(adyacente.getSiguienteAdy());
                res = true;
            } else {
                NodoAdyacenteEtiquetado siguienteAdyacente = adyacente.getSiguienteAdy();
                while (siguienteAdyacente != null && !siguienteAdyacente.getVertice().equals(nodoB)) {
                    siguienteAdyacente = siguienteAdyacente.getSiguienteAdy();
                    adyacente = adyacente.getSiguienteAdy();
                }
                if (siguienteAdyacente != null) {
                    adyacente.setSiguienteAdy(siguienteAdyacente.getSiguienteAdy());
                    res = true;
                }
            }
        }
        return res;
    }//check

    public boolean insertarArco(Object elemA, Object elemB, double etiqueta) {
        boolean retorno = false;
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);// ubica los nodos con hash
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            this.insertarAdyacente(verticeA, verticeB, etiqueta);
            this.insertarAdyacente(verticeB, verticeA, etiqueta);
            retorno = true;
        }
        return retorno;
    }//check

    public boolean eliminarArco(Object elemA, Object elemB) {
        boolean retorno = false;
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            this.eliminarAdyacente(verticeA, verticeB);
            this.eliminarAdyacente(verticeB, verticeA);
            retorno = true;
        }

        return retorno;
    }//check

    public Object getEtiquetaArco(Object elemA, Object elemB) {
        Object res = null;
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {//verifico que los 2 nodos existen
            NodoAdyacenteEtiquetado aux = verticeA.getAdyacente();
            while (aux != null && res == null) {
                if (aux.getVertice().equals(verticeB)) {
                    res = aux.getEtiqueta();
                }
                aux = aux.getSiguienteAdy();
            }
            //return res;
        }
        return res;
    }//check

    public boolean setEtiquetaArco(Object elemA, Object elemB, double etiquetaNueva) {
        //actualiza la etiqueta de un arco entre 2 nodos del grafo
        boolean res = false;
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        System.out.println("existen nodos");
        if (verticeA != null && verticeB != null) {
            res = this.setEtiquetaAdyacenteAux(verticeA, verticeB, etiquetaNueva);
        }
        return res;
    }//check

    private boolean setEtiquetaAdyacenteAux(NodoVerticeEtiquetado nodoA, NodoVerticeEtiquetado nodoB, double etiquetaNueva) {
        boolean res = false;
        NodoAdyacenteEtiquetado aux = nodoA.getAdyacente();
        while (aux != null && !res) {
            if (aux.getVertice().equals(nodoB)) {
                aux.setEtiqueta(etiquetaNueva);
                res = true;
            }
            aux = aux.getSiguienteAdy();
        }
        if(res){
            res = false;
            aux=nodoB.getAdyacente();
            while (aux != null && !res) {
            if (aux.getVertice().equals(nodoA)) {
                aux.setEtiqueta(etiquetaNueva);
                res = true;
            }
            aux = aux.getSiguienteAdy();
        }
        }
        return res;
    }//check

    public boolean existeVertice(Object elem) {
        boolean res = false;
        NodoVerticeEtiquetado aux = this.inicio;
        while (aux != null && !res) {
            if (aux.getElemento().equals(elem)) {
                res = true;
            }
            aux = aux.getSigVertice();
        }
        return res;
    }//check

    public Object recuperarVertice(Object elem) {
        Object res = null;
        NodoVerticeEtiquetado aux = this.inicio;
        while (aux != null && res == null) {
            if (aux.getElemento().equals(elem)) {
                res = aux.getElemento();
            }
            aux = aux.getSigVertice();
        }
        return res;
    }//check

    public boolean existeArco(Object elemA, Object elemB) {
        boolean res = false;
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            NodoAdyacenteEtiquetado aux = verticeA.getAdyacente();
            while (aux != null && !res) {
                if (aux.getVertice().equals(verticeB)) {
                    res = true;
                }
                aux = aux.getSiguienteAdy();
            }
        }

        return res;
    }//check

    public boolean existeCamino(Object elemA, Object elemB) {
        boolean res = false;
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            res = existeCaminoAux(verticeA, verticeB, new Lista());
        }

        return res;
    }//check

    private boolean existeCaminoAux(NodoVerticeEtiquetado inicio, NodoVerticeEtiquetado destino, Lista visitados) {
        boolean res = false;
        if (inicio != null) {
            if (inicio.getElemento().equals(destino.getElemento())) {
                res = true;
            } else {
                visitados.insertar(inicio.getElemento(), visitados.longitud() + 1);
                NodoAdyacenteEtiquetado aux = inicio.getAdyacente();
                while (!res && aux != null) {
                    if (visitados.localizar(aux.getVertice().getElemento()) < 0) {// se pregunta si ya se paso por el nodo, si no paso, continua
                        res = existeCaminoAux(aux.getVertice(), destino, visitados); // recursividad
                    }
                    aux = aux.getSiguienteAdy();
                }
            }
        }
        return res;
    }//check

    public Lista caminoMasCorto(Object elemA, Object elemB) {
        Lista camino = new Lista();
        HashMap<String, Object> visitados = new HashMap<>(); //hash vacio para los nodos NodosVisitados
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);// busca los 2 nodos
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            camino = this.caminoMasCortoAux(verticeA, verticeB, visitados, camino, new Lista()); // modulo recursivo
        }
        return camino;
    }//check

    private Lista caminoMasCortoAux(NodoVerticeEtiquetado inicio, NodoVerticeEtiquetado destino, HashMap<String, Object> nodosVisitados, Lista camino, Lista comparar) {
        if (inicio != null) {
            nodosVisitados.put(inicio.getElemento().toString(), inicio);// coloca nodo en hash
            camino.insertar(inicio.getElemento(), camino.longitud() + 1);// inseta nodo en lista camino
            if (comparar.esVacia() || camino.longitud() < comparar.longitud()) {// si listaCaminosPosibles es vacia o si camino es menor a listaCaminosPosibles
                if (inicio.equals(destino)) {// si llego a destino
                    comparar = camino.clone();
                } else {// si no llego a destino
                    NodoAdyacenteEtiquetado nodoAdyAux = inicio.getAdyacente(); // puntero a los ady del nodo actual
                    while (nodoAdyAux != null) {
                        if (nodosVisitados.get(nodoAdyAux.getVertice().getElemento().toString()) == null) {// se pregunta en el hash si se paso por el nodoAdy
                            comparar = this.caminoMasCortoAux(nodoAdyAux.getVertice(), destino, nodosVisitados, camino, comparar); // recursividad
                        }
                        nodoAdyAux = nodoAdyAux.getSiguienteAdy();
                    }
                }
            }
            nodosVisitados.remove(inicio.getElemento().toString());// se quita el nodo actual del hash
            camino.eliminar(camino.localizar(inicio.getElemento()));// se quita el nodo actual de la lista
        }
        return comparar;
    } //check

    public Lista caminoMasLargo(Object elemA, Object elemB) {
        Lista camino = new Lista();
        HashMap<String, Object> visitados = new HashMap<>();
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            camino = this.caminoMasLargoAux(verticeA, verticeB, visitados, camino, new Lista());
        }
        return camino;
    }//check

    private Lista caminoMasLargoAux(NodoVerticeEtiquetado inicio, NodoVerticeEtiquetado destino, HashMap<String, Object> visitados, Lista camino, Lista comparar) {
        if (inicio != null) {
            visitados.put(inicio.getElemento().toString(), inicio);
            camino.insertar(inicio.getElemento(), camino.longitud() + 1);
            if (comparar.esVacia() || camino.longitud() >= comparar.longitud()) {
                if (inicio.equals(destino)) {
                    comparar = camino.clone();
                } else {
                    NodoAdyacenteEtiquetado nodoAdyAux = inicio.getAdyacente();
                    while (nodoAdyAux != null) {
                        if (visitados.get(nodoAdyAux.getVertice().getElemento().toString()) == null) {
                            comparar = this.caminoMasLargoAux(nodoAdyAux.getVertice(), destino, visitados, camino, comparar);
                        }
                        nodoAdyAux = nodoAdyAux.getSiguienteAdy();
                    }
                }
            }
            visitados.remove(inicio.getElemento().toString());
            camino.eliminar(camino.localizar(inicio.getElemento()));
        }
        return comparar;
    }//check

    public Lista caminoConMasPeso(Object elemA, Object elemB) {

        Object[] arreglo = new Object[2];
        arreglo[0] = new Lista();
        arreglo[1] = 0.0;
        HashMap<String, NodoVerticeEtiquetado> visitados = new HashMap<>();
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            arreglo = this.caminoConMasPesoAux(verticeA, verticeB, visitados, new Lista(), 0, 0, arreglo);
        }

        return ((Lista) arreglo[0]);
    }//check

    private Object[] caminoConMasPesoAux(NodoVerticeEtiquetado inicio, NodoVerticeEtiquetado destino, HashMap<String, NodoVerticeEtiquetado> visitados, Lista camino, double peso, double comparar, Object[] arreglo) {
        if (inicio != null) {
            visitados.put(inicio.getElemento().toString(), inicio);
            camino.insertar(inicio.getElemento(), camino.longitud() + 1);
            if (inicio.equals(destino)) {// si llega a destino
                double sumaMasKm = peso;
                if (((Double) arreglo[1]) < peso) {
                    arreglo[0] = (Lista) camino.clone();
                    arreglo[1] = sumaMasKm;
                }
            } else {
                NodoAdyacenteEtiquetado nodoAdyAux = inicio.getAdyacente();
                while (nodoAdyAux != null) {
                    peso += nodoAdyAux.getEtiqueta();
                    if (visitados.get(nodoAdyAux.getVertice().getElemento().toString()) == null) {// si aun no paso por el nodo
                        arreglo = this.caminoConMasPesoAux(nodoAdyAux.getVertice(), destino, visitados, camino,
                                peso, comparar, arreglo);
                    }
                    peso -= nodoAdyAux.getEtiqueta();
                    nodoAdyAux = nodoAdyAux.getSiguienteAdy();
                }
            }
            visitados.remove(inicio.getElemento().toString());
            camino.eliminar(camino.localizar(inicio.getElemento()));
        }
        return arreglo;
    }//check

    public Lista caminoConMenosPeso(Object elemA, Object elemB) {
        Object[] arreglo = new Object[2];
        arreglo[0] = new Lista();
        arreglo[1] = 0.0;
        HashMap<String, NodoVerticeEtiquetado> visitados = new HashMap<>();
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            arreglo = this.caminoConMenosPesoAux(verticeA, verticeB, visitados, new Lista(), 0, arreglo);
        }

        return ((Lista) arreglo[0]);
    }//check

    private Object[] caminoConMenosPesoAux(NodoVerticeEtiquetado inicio, NodoVerticeEtiquetado destino,
            HashMap<String, NodoVerticeEtiquetado> visitados, Lista camino, double peso, Object[] arreglo) {
        if (inicio != null) {
            visitados.put(inicio.getElemento().toString(), inicio);
            camino.insertar(inicio.getElemento(), camino.longitud() + 1);
            if (inicio.equals(destino)) {// si llega a destino
                double sumaMasKm = peso;
                if (((Double) arreglo[1]) > peso || ((Double) arreglo[1] == 0.0)) {
                    arreglo[0] = (Lista) camino.clone();
                    arreglo[1] = sumaMasKm;
                }
            } else {
                NodoAdyacenteEtiquetado nodoAdyAux = inicio.getAdyacente();
                while (nodoAdyAux != null) {
                    peso += nodoAdyAux.getEtiqueta();
                    if (visitados.get(nodoAdyAux.getVertice().getElemento().toString()) == null) {// si aun no paso por el nodo
                        if (((peso <= (Double) arreglo[1]) || ((Double) arreglo[1] == 0.0))) {
                            arreglo = this.caminoConMenosPesoAux(nodoAdyAux.getVertice(), destino, visitados, camino,
                                    peso, arreglo);
                        }
                    }
                    peso -= nodoAdyAux.getEtiqueta();
                    nodoAdyAux = nodoAdyAux.getSiguienteAdy();
                }
            }
            visitados.remove(inicio.getElemento().toString());
            camino.eliminar(camino.localizar(inicio.getElemento()));
        }
        return arreglo;
    }//check

    public Lista caminos(Object elemA, Object elemB) {
        /**
         * Lista todos los caminos de A a B, sin importar la cantidad de nodos
         */
        Lista caminos = new Lista();
        HashMap<String, Object> visitados = new HashMap<>();
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            caminos = this.caminosAux(verticeA, verticeB, visitados);
        }
        return caminos;//check
    }

    private Lista caminosAux(NodoVerticeEtiquetado nodo, NodoVerticeEtiquetado destino, HashMap<String, Object> visitados) {
        /**
         *  Lista todos los caminos del nodo A al nodo B, por cada recorrido de A a B se va almacenado en un HashMap,
         *  cuando ese recorrido esta estacompleto lo guarda en la lista de caminos.
         */
        Lista retorno = new Lista();
        if (nodo != null) {
            visitados.put(nodo.getElemento().toString(), nodo);
            if (nodo.equals(destino)) {
                Lista lista = new Lista();
                lista.insertar(nodo.getElemento(), 1);
                retorno.insertar(lista, 1);
            } else {
                NodoAdyacenteEtiquetado adyacente = nodo.getAdyacente();
                while (adyacente != null) {
                    if (visitados.get(adyacente.getVertice().getElemento().toString()) == null) {
                        Lista listaRetorno = caminosAux(adyacente.getVertice(), destino, visitados);
                        retorno = this.concatenar(listaRetorno, retorno);
                    }
                    adyacente = adyacente.getSiguienteAdy();
                }
                int i = 1;
                while (i <= retorno.longitud()) {
                    Lista lista = (Lista) retorno.recuperar(i);
                    retorno.eliminar(i);
                    lista.insertar(nodo.getElemento(), 1);
                    retorno.insertar(lista, i);
                    i++;
                }
            }
            visitados.remove(nodo.getElemento().toString());
        }
        return retorno;//check
    }

    private Lista concatenar(Lista l1, Lista l2) {
        Lista retorno = new Lista();
        int i = 1;

        while (i <= l1.longitud() + l2.longitud()) {
            if (i <= l1.longitud()) {
                retorno.insertar(l1.recuperar(i), i);
            } else {
                retorno.insertar(l2.recuperar(i - l1.longitud()), i - l1.longitud());
            }
            i++;
        }
        return retorno;
    }

    public Lista menorCaminoQueTieneC(Object elemA, Object elemB, Object elemC) {
        /**
         * lista el camino de A a B que pasa por C, con respecto a la menor
         * cantidad de nodos recorridos, No por etiqueta
         */
        Lista listaCamino = new Lista();
        HashMap<String, NodoVerticeEtiquetado> NodosVisitados = new HashMap<>();
        NodoVerticeEtiquetado aux = this.inicio;
        NodoVerticeEtiquetado verticeA = null;
        NodoVerticeEtiquetado verticeB = null;
        NodoVerticeEtiquetado verticeC = null;
        boolean corte = false;
        while (aux != null && !corte) { // recorre los nodos para obtener los nodos A, B y C
            if (aux.getElemento().equals(elemA)) {
                verticeA = aux;
            } else if (aux.getElemento().equals(elemB)) {
                verticeB = aux;
            } else if (aux.getElemento().equals(elemC)) {
                verticeC = aux;
            }
            corte = verticeA != null && verticeB != null && verticeC != null;
            aux = aux.getSigVertice();
        }
        if (verticeA != null && verticeB != null && verticeC != null) { // si los tres nodos existen, se llama al metodo recursivo
            listaCamino = this.menorCaminoQueTieneC(verticeA, verticeB, verticeC, NodosVisitados, new Lista(), listaCamino);
        }
        return listaCamino;
    }//check

    private Lista menorCaminoQueTieneC(NodoVerticeEtiquetado inicio, NodoVerticeEtiquetado destino, NodoVerticeEtiquetado intermedio, HashMap<String, NodoVerticeEtiquetado> nodosVisitados, Lista camino, Lista comparar) {
        if (inicio != null) {
            nodosVisitados.put(inicio.getElemento().toString(), inicio);// coloca nodo en hash para evitar pasar por el mismo nodo
            camino.insertar(inicio.getElemento(), camino.longitud() + 1);// inseta nodo en lista camino
            if (comparar.esVacia() || camino.longitud() < comparar.longitud()) {// compara en caso de que comparar es vacia (por elprimer recorrido) o si camino(lista sobrre la que se trabaja) es menor a comparar,
                if (inicio.equals(destino)) {// si llego a destino
                    if (camino.localizar(intermedio.getElemento()) != -1) {

                        comparar = camino.clone();
                    }
                } else {// si no llego a destino
                    NodoAdyacenteEtiquetado nodoAdyAux = inicio.getAdyacente(); // puntero a los ady del nodo actual
                    while (nodoAdyAux != null) {
                        if (nodosVisitados.get(nodoAdyAux.getVertice().getElemento().toString()) == null) {// se pregunta en el hash si se paso por el nodoAdy, en caso que no, entra en la recursividad
                            comparar = this.menorCaminoQueTieneC(nodoAdyAux.getVertice(), destino, intermedio, nodosVisitados, camino, comparar); // recursividad
                        }
                        nodoAdyAux = nodoAdyAux.getSiguienteAdy();
                    }
                }
            }
            nodosVisitados.remove(inicio.getElemento().toString());// se quita el nodo actual del hash
            camino.eliminar(camino.localizar(inicio.getElemento()));// se quita el nodo actual de la lista
        }
        return comparar;
    } //check

    public Lista listaConCaminosQueNoTieneC(Object elemA, Object elemB, Object elemC) {
        /**
         * lista todos los caminos de A a B que NO pasen por C
         */
        Lista listaCaminosPosibles = new Lista();
        HashMap<String, NodoVerticeEtiquetado> NodosVisitados = new HashMap<>();
        NodoVerticeEtiquetado aux = this.inicio;
        NodoVerticeEtiquetado verticeA = null;
        NodoVerticeEtiquetado verticeB = null;
        NodoVerticeEtiquetado verticeC = null;
        boolean corte = false;

        while (aux != null && !corte) {
            if (aux.getElemento().equals(elemA)) {
                verticeA = aux;
            } else if (aux.getElemento().equals(elemB)) {
                verticeB = aux;
            } else if (aux.getElemento().equals(elemC)) {
                verticeC = aux;
            }
            corte = verticeA != null && verticeB != null && verticeC != null;
            aux = aux.getSigVertice();
        }
        if (verticeA != null && verticeB != null && verticeC != null) { //si localiza los tres nodos, llama al metodo recursivo, casocontrario devuelve nulo.
            listaCaminosPosibles = listaConCaminosQueNoTieneC(verticeA, verticeB, verticeC, NodosVisitados, new Lista(), listaCaminosPosibles);
        }
        return listaCaminosPosibles;
    }//check

    private Lista listaConCaminosQueNoTieneC(NodoVerticeEtiquetado inicio, NodoVerticeEtiquetado destino,
            NodoVerticeEtiquetado nodoC, HashMap<String, NodoVerticeEtiquetado> nodosVisitados, Lista camino,
            Lista listaCaminosPosibles) {
        if (inicio != null) {
            nodosVisitados.put(inicio.getElemento().toString(), inicio);// coloca nodo en hash
            camino.insertar(inicio.getElemento(), camino.longitud() + 1);// inseta nodo en lista camino
            if (inicio.equals(destino)) {// si llego a destino
                if (camino.localizar(nodoC.getElemento()) == -1 && listaCaminosPosibles.existeObjeto(camino)==false) {
                    //if (listaCaminosPosibles.existeObjeto(camino)==false) {
                        listaCaminosPosibles.insertar((Lista) camino.clone(), listaCaminosPosibles.longitud() + 1);
                    //}
                }
            } else {// si no llego a destino
                NodoAdyacenteEtiquetado nodoAdyAux = inicio.getAdyacente(); // puntero a los ady del nodo actual
                while (nodoAdyAux != null) {
                    if (nodosVisitados.get(nodoAdyAux.getVertice().getElemento().toString()) == null) {// se pregunta en el hash si se paso por el nodoAdy
                        listaCaminosPosibles = listaConCaminosQueNoTieneC(nodoAdyAux.getVertice(), destino, nodoC, nodosVisitados, camino, listaCaminosPosibles); // recursividad
                    }
                    nodoAdyAux = nodoAdyAux.getSiguienteAdy();
                }
            }
            nodosVisitados.remove(inicio.getElemento().toString());// se quita el nodo actual del hash
            camino.eliminar(camino.localizar(inicio.getElemento()));// se quita el nodo actual de la lista
        }
        return listaCaminosPosibles;
    } //check

    public Lista caminoMasRapidoQuePasaPorC(Object elemA, Object elemB, Object elemC) {
        HashMap<String, NodoVerticeEtiquetado> visitados = new HashMap<>();
        Object[] arreglo = new Object[2];
        arreglo[0] = new Lista();
        arreglo[1] = 0.0;
        NodoVerticeEtiquetado aux = this.inicio;
        NodoVerticeEtiquetado verticeA = null;
        NodoVerticeEtiquetado verticeB = null;
        NodoVerticeEtiquetado verticeC = null;
        boolean corte = false;
        while (aux != null && !corte) {
            if (aux.getElemento().equals(elemA)) {
                verticeA = aux;
            } else if (aux.getElemento().equals(elemB)) {
                verticeB = aux;
            } else if (aux.getElemento().equals(elemC)) {
                verticeC = aux;
            }
            corte = verticeA != null && verticeB != null && verticeC != null;
            aux = aux.getSigVertice();
        }
        if (verticeA != null && verticeB != null && verticeC != null) {
            arreglo = this.caminoMasRapidoQuePasaPorC(verticeA, verticeB, verticeC, visitados, new Lista(), 0.0, arreglo);
        }
        System.out.println("km: " + ((double) arreglo[1]));
        return ((Lista) arreglo[0]);
    }

    private Object[] caminoMasRapidoQuePasaPorC(NodoVerticeEtiquetado inicio, NodoVerticeEtiquetado destino, NodoVerticeEtiquetado nodoC,
            HashMap<String, NodoVerticeEtiquetado> visitados, Lista camino, double peso, Object[] arreglo) {
        if (inicio != null) {
            visitados.put(inicio.getElemento().toString(), inicio);
            camino.insertar(inicio.getElemento(), camino.longitud() + 1);
            if (inicio.equals(destino)) {// si llega a destino
                if (camino.localizar(nodoC.getElemento()) != -1) {
                    double sumaMasKm = peso;
                    if (((Double) arreglo[1]) > peso || ((Double) arreglo[1] == 0.0)) {
                        arreglo[0] = (Lista) camino.clone();
                        arreglo[1] = sumaMasKm;
                    }
                }
            } else {
                NodoAdyacenteEtiquetado nodoAdyAux = inicio.getAdyacente();
                while (nodoAdyAux != null) {
                    peso += nodoAdyAux.getEtiqueta();
                    if (visitados.get(nodoAdyAux.getVertice().getElemento().toString()) == null) {// si aun no paso por el nodo
                        if (((peso <= (Double) arreglo[1]) || ((Double) arreglo[1] == 0.0))) {
                            arreglo = this.caminoMasRapidoQuePasaPorC(nodoAdyAux.getVertice(), destino, nodoC, visitados, camino, peso, arreglo);
                        }
                    }
                    peso -= nodoAdyAux.getEtiqueta();
                    nodoAdyAux = nodoAdyAux.getSiguienteAdy();
                }
            }
            visitados.remove(inicio.getElemento().toString());
            camino.eliminar(camino.localizar(inicio.getElemento()));
        }
        return arreglo;
    }//check

    //preguntar concepto del metodo solicitado para ver si esta bien implementado
    public Lista caminoConTopeDeVertices(Object elemA, Object elemB, int cantidadVertices) {
        Lista comparar = new Lista();
        HashMap<String, NodoVerticeEtiquetado> visitados = new HashMap<>();
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        //NodoVerticeEtiquetado verticeA = (NodoVerticeEtiquetado) recuperarVertice(elemA);
        //NodoVerticeEtiquetado verticeB = (NodoVerticeEtiquetado) recuperarVertice(elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);
        if (verticeA != null && verticeB != null) {
            comparar = this.caminoConTopeDeVerticesAux(verticeA, verticeB, cantidadVertices, visitados, new Lista(), comparar);
        }
        return comparar;
    }

    private Lista caminoConTopeDeVerticesAux(NodoVerticeEtiquetado inicio, NodoVerticeEtiquetado destino, int CantVertices,
            HashMap<String, NodoVerticeEtiquetado> visitados, Lista camino, Lista comparar) {
        if (inicio != null) {
            visitados.put(inicio.getElemento().toString(), inicio);
            camino.insertar(inicio.getElemento(), camino.longitud() + 1);
            if (camino.longitud() <= CantVertices || comparar.esVacia() || camino.longitud() < comparar.longitud()) {
                if (inicio.equals(destino)) {
                    comparar = camino.clone();
                } else {
                    NodoAdyacenteEtiquetado adyacente = inicio.getAdyacente();
                    while (adyacente != null) {
                        if (visitados.get(adyacente.getVertice().getElemento().toString()) == null) {
                            comparar = this.caminoConTopeDeVerticesAux(adyacente.getVertice(), destino, CantVertices,
                                    visitados, camino, comparar);
                        }
                        adyacente = adyacente.getSiguienteAdy();
                    }
                }
            }
            visitados.remove(inicio.getElemento().toString());
            camino.eliminar(camino.localizar(inicio.getElemento()));
        }
        return comparar;
    }

    public Lista listarEnProfundidad() {
        Lista list = new Lista();
        HashMap<String, NodoVerticeEtiquetado> nodosVisitados = new HashMap<>();
        NodoVerticeEtiquetado nodo = this.inicio;
        while (nodo != null) {
            if (nodosVisitados.get(nodo.getElemento().toString()) == null) {
                this.listarEnProdundidadAux(nodo, list, nodosVisitados);
            }
            nodo = nodo.getSigVertice();
        }
        return list;
    }

    private void listarEnProdundidadAux(NodoVerticeEtiquetado nodo, Lista listaRes, HashMap<String, NodoVerticeEtiquetado> visitados) {
        visitados.put(nodo.getElemento().toString(), inicio); //guarda el nodo en el hash para indicar que fue visitado
        listaRes.insertar(nodo.getElemento(), listaRes.longitud() + 1);
        NodoAdyacenteEtiquetado aux = nodo.getAdyacente();
        while (aux != null) {
            if (visitados.get(aux.getVertice().getElemento().toString()) == null) {
                this.listarEnProdundidadAux(aux.getVertice(), listaRes, visitados);
            }
            aux = aux.getSiguienteAdy();
        }
    }//check

    public Lista listarAnchura() {
        Lista listaRes = new Lista();
        HashMap<String, Object> visitados = new HashMap<>();
        NodoVerticeEtiquetado nodo = this.inicio;
        while (nodo != null) {
            if (visitados.get(nodo.getElemento().toString()) == null) {
                this.listarAnchuraAux(nodo, listaRes, visitados);
            }
            nodo = nodo.getSigVertice();
        }
        return listaRes;
    }

    private void listarAnchuraAux(NodoVerticeEtiquetado nodo, Lista listaRes, HashMap<String, Object> visitados) {
        Cola q = new Cola();
        visitados.put(nodo.getElemento().toString(), inicio);
        q.poner(nodo);
        while (!q.esVacia()) {
            NodoVerticeEtiquetado aux = (NodoVerticeEtiquetado) q.obtenerFrente();
            listaRes.insertar(aux.getElemento(), listaRes.longitud() + 1);
            System.out.println(listaRes.toString());
            q.sacar();
            NodoAdyacenteEtiquetado adyacente = aux.getAdyacente();
            while (adyacente != null) {
                if (visitados.get(adyacente.getVertice().getElemento().toString()) == null) {
                    q.poner(adyacente.getVertice());
                    visitados.put(adyacente.getVertice().getElemento().toString(), adyacente.getVertice());
                }
                adyacente = adyacente.getSiguienteAdy();
            }
        }
    }//creo que esta bien

    public GrafoEtiquetado clone() {
        GrafoEtiquetado clon = new GrafoEtiquetado();
        NodoVerticeEtiquetado nodo = this.inicio;
        HashMap<String, NodoVerticeEtiquetado> nodos = new HashMap<>();
        if (nodo != null) {
            clon.inicio = new NodoVerticeEtiquetado(nodo.getElemento(), null, null);
            NodoVerticeEtiquetado nodoClone = clon.inicio; // puntero del grafo clonado
            nodos.put(nodo.getElemento().toString(), nodoClone);
            nodo = nodo.getSigVertice();
            while (nodo != null) {
                nodoClone.setSigVertice(new NodoVerticeEtiquetado(nodo.getElemento(), null, null));
                nodoClone = nodoClone.getSigVertice();
                nodos.put(nodoClone.getElemento().toString(), nodoClone);
                nodo = nodo.getSigVertice();
            }
            nodoClone = clon.inicio;
            nodo = this.inicio;
            while (nodoClone != null) {
                NodoAdyacenteEtiquetado adyacenteNodo = nodo.getAdyacente();
                if (adyacenteNodo != null) {
                    nodoClone.setAdyacente(
                            new NodoAdyacenteEtiquetado(nodos.get(adyacenteNodo.getVertice().getElemento().toString()),
                                    null, adyacenteNodo.getEtiqueta()));
                    NodoAdyacenteEtiquetado adyacenteClonado = nodoClone.getAdyacente();
                    adyacenteNodo = adyacenteNodo.getSiguienteAdy();
                    while (adyacenteNodo != null) {
                        adyacenteClonado.setSiguienteAdy(
                                new NodoAdyacenteEtiquetado(nodos.get(adyacenteNodo.getVertice().getElemento().toString()),
                                        null, adyacenteNodo.getEtiqueta()));
                        adyacenteNodo = adyacenteNodo.getSiguienteAdy();
                        adyacenteClonado = adyacenteClonado.getSiguienteAdy();
                    }
                }
                nodoClone = nodoClone.getSigVertice();
                nodo = nodo.getSigVertice();
            }
        }
        return clon;
    }

    @Override
    public String toString() {
        String retorno = "Grafo Vacio";
        NodoVerticeEtiquetado nodo = this.inicio;
        if (nodo != null) {
            retorno = "";
            while (nodo != null) {
                NodoAdyacenteEtiquetado adyacente = nodo.getAdyacente();
                retorno += nodo.getElemento().toString() + ":";
                while (adyacente != null) {
                    retorno += "->(" + adyacente.getVertice().getElemento().toString() + "," + adyacente.getEtiqueta()
                            + ")";
                    adyacente = adyacente.getSiguienteAdy();
                }
                retorno += "\n";
                nodo = nodo.getSigVertice();
            }
        }
        return retorno;
    }//check


    public boolean caminoConTopeDeKm(Object elemA, Object elemB, double n) {
        boolean res=true;;
        Object[] arreglo = new Object[2];
        double m=0;
        HashMap<String, NodoVerticeEtiquetado> visitados = new HashMap<>();
        HashMap<Object, NodoVerticeEtiquetado> vertices = this.ubicarVertices(elemA, elemB);
        NodoVerticeEtiquetado verticeA = vertices.get(elemA);
        NodoVerticeEtiquetado verticeB = vertices.get(elemB);

        if (verticeA != null && verticeB != null) { 
            arreglo = this.caminoConTopeDeKm(verticeA, verticeB, n, m, visitados, new Lista(), arreglo);
        }
        if(arreglo[0]==null){
            res=false;
        }
        return res;
    }

    private Object[] caminoConTopeDeKm(NodoVerticeEtiquetado nodoActual, NodoVerticeEtiquetado destino, double n, double contadorKm,
            HashMap<String, NodoVerticeEtiquetado> visitados, Lista recorridoActual, Object[] arreglo) {
        if (nodoActual != null) {
            visitados.put(nodoActual.getElemento().toString(), nodoActual);
            recorridoActual.insertar(nodoActual.getElemento(), recorridoActual.longitud() + 1);
            if (contadorKm <= n) {
                if (nodoActual.equals(destino)){
                    if(arreglo[1]==null){//para la primera vez que requiere guardar
                        arreglo[0]=recorridoActual.clone();
                        arreglo[1]=contadorKm;
                    }else{
                        if(contadorKm <= ((double)arreglo[1])){//compara para guardar el camino mas corto
                            arreglo[0]=recorridoActual.clone();
                            arreglo[1]=contadorKm;
                        }
                    }
                } else {//sino llega al destino sigue recorriendo
                    NodoAdyacenteEtiquetado adyacente = nodoActual.getAdyacente();
                    while (adyacente != null) {
                        contadorKm+= adyacente.getEtiqueta();
                        if (visitados.get(adyacente.getVertice().getElemento().toString()) == null) {
                            arreglo=this.caminoConTopeDeKm(adyacente.getVertice(), destino, n, contadorKm,
                            visitados, recorridoActual, arreglo);
                        }
                        contadorKm-= adyacente.getEtiqueta();                  
                        adyacente = adyacente.getSiguienteAdy();
                    }
                } 
                visitados.remove(nodoActual.getElemento().toString());
                recorridoActual.eliminar(recorridoActual.localizar(nodoActual.getElemento()));         
            }
        }
        return arreglo;
    }
}
