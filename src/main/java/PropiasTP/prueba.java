package PropiasTP;

import DiccionarioAVL.Diccionario;
import Utiles.TecladoIn;
import grafos.GrafoEtiquetado;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
//import java.util.concurrent.Phaser;

import lineales.dinamicas.Lista;

/**
 *
 * @author JoaquinNoillet
 */
public class prueba {

    private static GrafoEtiquetado grafoMapa = new GrafoEtiquetado();
    private static Diccionario avlEstaciones = new Diccionario();
    private static Diccionario avlTrenes = new Diccionario();
    private static HashMap<String, Object> hashLineas = new HashMap<>();

    private static File txtCambios = new File("src/main/java/Datos/archivoLOG.txt");
    private static File txtDatos = new File("src/main/java/Datos/Datos.txt");

    public static void main(String[] args) throws FileNotFoundException {
        int i;
        limpiarArchivoLog();
        altaDatos();
        do {
            i = menuPrincipal();
            switch (i) {
                case 1 ->
                    ABMEstaciones();
                case 2 ->
                    ABMTrenes();
                case 3 ->
                    ABMRieles();
                case 4 ->
                    ABMLineas();
                case 5 ->
                    SubMenuTrenes();
                case 6 ->
                    SubMenuViajes();
                case  7 ->
                    SubMenuEstaciones();
                case 8 ->
                    System.out.println("finalizando ejecucion...");
                default ->
                    System.out.println("ingresar opcion del 1 al 7");
            }
        } while (i != 8);
    }
    public static int menuPrincipal() {
        int res;
        System.out.println("""
                           Seleccione una de las opciones : 
                           1- ABM (Altas-Bajas-Modificaciones) de estaciones 
                           2- ABM (Altas-Bajas-Modificaciones) de trenes 
                           3- ABM (Altas-Bajas-Modificaciones) de lineas
                           4- ABM (Altas-Bajas-Modificaciones) de red de rieles 
                           5- consulta sobre trenes 
                           6- consulta sobre viajes
                           7- consulta sobre estaciones
                           8- Finalizar""");
        res = TecladoIn.readLineInt();
        return res;
    }
    public static void limpiarArchivoLog(){
        try {
            FileWriter archivo = new FileWriter(txtCambios,false);
            archivo.write("");
            archivo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public static void modificarTxt(String cadena) {
        try {
            FileWriter escribirArchivo = new FileWriter(txtCambios,true);
            BufferedWriter buffer = new BufferedWriter(escribirArchivo);
            buffer.write(cadena);
            buffer.newLine();
            buffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//check

    //ALTA
    public static void altaDatos() throws FileNotFoundException {
        Scanner sc = new Scanner(txtDatos);
        while(sc.hasNextLine()){ // mientras exista una siguiente linea
            String aux= sc.nextLine();
            modificarTxt(aux);
            String[] datos = aux.split(";");
            System.out.println(datos[0]+", "+datos[1]+", "+datos[3]);

            switch (datos[0]) {
                case "E":
                altaEstacion(datos);
                break;
                case "R":
                altaMapa(datos);
                break;
                case "L":
                altaLinea(datos);
                break;
                case "T":
                altaTren(datos);
                break;
                default :
            }
        }
        sc.close();
    }
    public static void altaEstacion(String[] datos) throws FileNotFoundException {
        String nombre = datos[1];
        String calle = datos[2];
        int numero = Integer.parseInt(datos[3]);
        String ciudad = datos[4];
        int codPostal = Integer.parseInt(datos[5]);
        int cantVias = Integer.parseInt(datos[6]);
        int cantPlataformas = Integer.parseInt(datos[7]);
        Estacion nuevaEstacion = new Estacion(nombre, calle, numero, ciudad, codPostal, cantVias, cantPlataformas);
        avlEstaciones.insertar(nuevaEstacion, datos[1]);
        grafoMapa.insertarVertice(nuevaEstacion);
    }   
    public static void altaMapa(String[] datos) throws FileNotFoundException {
        String nombreEstacion1 = ((String) datos[1]);
        Estacion estacion1 = (Estacion) avlEstaciones.obtenerDato(nombreEstacion1);
        String nombreEstacion2 = ((String) datos[2]);
        Estacion estacion2 = (Estacion) avlEstaciones.obtenerDato(nombreEstacion2);
        if (estacion1 != null && estacion2 != null) {
            double km = Double.parseDouble((String) datos[3]);
            grafoMapa.insertarArco(estacion1, estacion2, km);
        }
    }    
    public static void altaTren(String[] datos) throws FileNotFoundException {
        int codigo = Integer.parseInt(datos[1]);
        String tipoPropulsion = datos[2];
        int cantidadVagonesPasajeros = Integer.parseInt(datos[3]);
        int cantidadVagonesCarga = Integer.parseInt(datos[4]);;
        String lineAsignada = datos[5];
        avlTrenes.insertar(new Tren(codigo, tipoPropulsion, cantidadVagonesPasajeros, cantidadVagonesCarga, lineAsignada), datos[1]);
    }  
    public static void altaLinea(String[] datos) throws FileNotFoundException {
        String nombre = datos[1];
        int i = 2;
        Estacion aux;
        Lista listaEstaciones = new Lista();
        while (i < datos.length) {
            aux = (Estacion) avlEstaciones.obtenerDato(datos[i]);
            if(aux!=null){
                listaEstaciones.insertar(aux, i);
            }
            i++;
        }
        hashLineas.put(nombre, new Linea(nombre, listaEstaciones));
    }
    //AMB Esaciones
    private static void ABMEstaciones() {
        int res;
        do {
            System.out.println("""
                           Menu de opciones de Estacion:  
                           1- Alta Estacion 
                           2- Baja Estacion 
                           3- Modificar Estacion 
                           4- Terminar""");
            res = TecladoIn.readLineInt();

            switch (res) {
                case 1 ->
                    altaNuevaEstacion();
                case 2 ->
                    bajaEstacion();
                case 3 ->
                    modificarEstacion();
                case 4 ->
                    System.out.println("Fin ABM estaciones");
                default ->
                    System.out.println("Ingrese una opcion del 1 al 4");
            }
        } while (res != 4);
    }
    public static void altaNuevaEstacion() {
        Estacion nuevaEst;
        String nombre, text;
        do {
            System.out.println("Ingrese nombre estacion");
            nombre = TecladoIn.readLine();
        } while (avlEstaciones.obtenerDato(nombre) != null);// mientras no exista previamente la estacion

        System.out.println("Ingrese calle");
        String calle = TecladoIn.readLine();
        System.out.println("Ingrese numero de estacion");
        int num = TecladoIn.readLineInt();
        System.out.println("Ingrese ciudad");
        String ciudad = TecladoIn.readLine();
        System.out.println("Ingrese codigo postal");
        int codPostal = TecladoIn.readLineInt();
        System.out.println("Ingrese cantidad de vias");
        int cantVias = TecladoIn.readLineInt();
        System.out.println("Ingrese cantidad de plataformas");
        int cantPlataformas = TecladoIn.readLineInt();
        nuevaEst = new Estacion(nombre, calle, num, ciudad, codPostal, cantVias, cantPlataformas);

        if (avlEstaciones.insertar(nuevaEst, nombre)) {
            grafoMapa.insertarVertice(nuevaEst);
            text="nueva Estacion ingresada";
            System.out.println(text);
            modificarTxt(text);
            modificarTxt(nuevaEst.toString());
        } else {
            System.out.println("Ya existe esa Estacion");
        }
    }
    public static void bajaEstacion() {
        Estacion estacion;
        String nombre,text;
        do {
            System.out.println("Ingrese nombre de estacion a Eliminar");
            nombre = TecladoIn.readLine();
            estacion = (Estacion) avlEstaciones.obtenerDato(nombre);
        } while (estacion == null);// hasta encontrar la estacion
        grafoMapa.eliminarVertice(estacion);// elimina la estacion del mapa y sus ady
        eliminarEstacionDeLineasHash(estacion);//elimina la estacion de todas la lineas
        avlEstaciones.eliminar(nombre);// elimina la estacion
        text="La estacion "+nombre+" fue eliminada";
        modificarTxt(text);
    }
    private static void eliminarEstacionDeLineasHash(Estacion estacion){
        for (Map.Entry<String, Object> entry : hashLineas.entrySet()) { // Itera usando Map.Entry para recorrer todas las lineas del hash, y para cada una de sus listas buscar la estacion que se tiene que eliminar
            Lista l = (Lista) entry.getValue();
            for (int i = 1; i <= l.longitud(); i++) {
                if (l.recuperar(i).equals(estacion.getNombre())) {
                    l.eliminar(i); // Elimina la estacion de la lista de esa linea
                    //break; // Sale del bucle una vez que se elimina la estacion
                }
            }
        }
    }// hay qye probar
    private static void modificarEstacion() {
        int i;
        System.out.println("Ingrese nombre de Estacion a modificar");
        String n = TecladoIn.readLine();
        Estacion aux = (Estacion) avlEstaciones.obtenerDato(n);
        if (aux != null) {
            //MODIFICACIONES
            do{
                System.out.println("""
                    que desea modificar:
                    1- Nombre de estacion 
                    2- Calle de estacion 
                    3- Ciudad de estacion (Tambien se cambiara el codigo postal)
                    4- Cantidad de vias de estacion
                    5- Cantidad de Plataformas de estacion
                    6- Cancelar modificacion""");
                i = TecladoIn.readLineInt();
                switch(i){
                    case 1 -> {
                        System.out.println("Ingrese nuevo nombre de la estacion");
                        aux.setNombre(TecladoIn.readLine());
                    }
                    case 2 -> {
                        System.out.println("Ingrese nueva calle de la estacion");
                        aux.setCalle(TecladoIn.readLine());
                    }
                    case 3 -> {
                        System.out.println("Ingrese nueva cuidad de la estacion");
                        aux.setCiudad(TecladoIn.readLine());
                        System.out.println("Ingrese nuevo codigo postal de la estacion");
                        aux.setCodPostal(TecladoIn.readLineInt());
                    }
                    case 4 -> {
                        System.out.println("Ingrese nueva cantidad de vias de la estacion");
                        aux.setCantVias(TecladoIn.readLineInt());
                    }
                    case 5 -> {
                        System.out.println("Ingrese nueva cantidad de Plataformas de la estacion");
                        aux.setCantPlataformas(TecladoIn.readLineInt());
                    }
                    case 6 -> {
                        System.out.println("Se cancelo la modificacion de estacion");
                    }
                    default ->{
                        System.out.println("Ingrese una opcion del 1 al 6");
                    }
                }
                System.out.println("Desea realizar otra modificacion? si/no");
                if("no".equals(TecladoIn.readLine())){
                    i=0;
                }
            }while (i!=6 && i!=0);
            String text="Se modifico la estacion:\n"+ aux.toString();
            modificarTxt(text);
        } else {
            System.out.println("Estacion no existe");
        }
    }
    //AMB Trenes
    private static void ABMTrenes() {
        int res;
        do {
            System.out.println("""
                           Menu de opciones de Tren:  
                           1- Alta Tren 
                           2- Baja Tren 
                           3- Modificar Tren 
                           4- Terminar""");
            res = TecladoIn.readLineInt();

            switch (res) {
                case 1 ->
                    altaNuevoTren();
                case 2 ->
                    bajaTren();
                case 3 ->
                    modificarTren();
                case 4 ->
                    System.out.println("Fin ABM trenes");
                default ->
                    System.out.println("Ingrese una opcion del 1 al 4");
            }
        } while (res != 4);
    }
    public static void altaNuevoTren() {
        Tren nuevoTren;
        int codigo;
        boolean seguir=true;
        String lineaAsignada,text;
        do {
            System.out.println("Ingrese codigo de Tren (si ingresa un codigo ya existente se le pedira que ingrese otro nuevamente)");
            codigo = TecladoIn.readLineInt();
        } while (avlTrenes.obtenerDato(codigo) != null);// mientras no exista previamente el tren
        System.out.println("Ingrese tipo de propulsion");
        String tipoPropulsion = TecladoIn.readLine();
        System.out.println("Ingrese numero de cantidad de Vagones Pasageros");
        int cantVagonesPasageros = TecladoIn.readLineInt();
        System.out.println("Ingrese cantidad de Vagones Carga");
        int cantVagonesCarga = TecladoIn.readLineInt();
        do{ // al ingresar la linea asignada, se verifica que exista en el hash
            System.out.println("Ingrese linea asignada");
            lineaAsignada = TecladoIn.readLine();
            if(hashLineas.get(lineaAsignada)==null){
                System.out.println("la Linea no existe, ingrese otra linea");
            }else{
                seguir=false;
            }
        }while(seguir);
        nuevoTren = new Tren(codigo, tipoPropulsion, cantVagonesPasageros, cantVagonesCarga, lineaAsignada); // se crea nuevo tren
        avlTrenes.insertar(nuevoTren, codigo);// se inserta el tren en el AVL
        text="nuevo Tren ingresado";
        System.out.println(text);
        modificarTxt(text);
        modificarTxt(nuevoTren.toString());
    }
    public static void bajaTren() {
        Tren trenAux;
        int codigo;
        String text;
        do {
            System.out.println("Ingrese codigo del tren a Eliminar");
            codigo = TecladoIn.readLineInt();
            trenAux = (Tren) avlTrenes.obtenerDato(codigo);
        } while (trenAux == null);// Repite hasta encontrar el tren
        avlTrenes.eliminar(codigo);// elimina el tren
        text="El tren "+codigo+" fue eliminado";
        System.out.println(text);
        modificarTxt(text);
    }
    private static void modificarTren() {
        int i;
        System.out.println("Ingrese codigo del tren a modificar");
        int c = TecladoIn.readLineInt();
        Tren aux = (Tren) avlTrenes.obtenerDato(c);
        if (aux != null) {
            System.out.println("Ingrese nueva linea");
            aux.setLineaAsignada(TecladoIn.readLine());
            //MODIFICACIONES
            do{
                System.out.println("""
                    que desea modificar:
                    1- Tipo de Propulsion 
                    2- Cantidad de Vagones de Pasajeros
                    3- Cantidad de Vagone de Carga
                    4- Linea asignada
                    5- Cancelar modificacion""");
                i = TecladoIn.readLineInt();
                switch(i){
                    case 1 -> {
                        System.out.println("Ingrese nuevo tipo de propulsion del tren");
                        aux.setTipoPropulsion(TecladoIn.readLine());
                    }
                    case 2 -> {
                        System.out.println("Ingrese nueva cantidad de vagones de pasajeros del tren");
                        aux.setCantVagonesPasajeros(TecladoIn.readLineInt());
                    }
                    case 3 -> {
                        System.out.println("Ingrese nueva cantidad de vagones de carga del tren");
                        aux.setCantVagonesCarga(TecladoIn.readLineInt());
                    }
                    case 4 -> {
                        System.out.println("Ingrese nueva Linea asignada del tren");
                        aux.setLineaAsignada(TecladoIn.readLine());
                    }
                    case 5 -> {
                        System.out.println("Se cancelo la modificacion de estacion");
                    }
                    default ->{
                        System.out.println("Ingrese una opcion del 1 al 5");
                    }
                }
                System.out.println("Desea realizar otra modificacion? si/no");
                if("no".equals(TecladoIn.readLine())){
                    i=0;
                }
            }while (i!=5 && i!=0);
            String text="Se modifico el Tren:\n"+ aux.toString();
            modificarTxt(text);

        } else {
            System.out.println("Tren no existe");
        }
    }
    //ABMRieles
    private static void ABMRieles() {
        /**
         * AMB que trabaja sobre el grafo
         * Permite agregar, eliminar y modificar los rieles de entre las estaciones
         */
        int res;
        do {
            System.out.println("""
                           Menu de opciones de Riel :  
                           1- Alta Riel 
                           2- Baja Riel  
                           3- Modificar Riel  
                           4- Terminar""");
            res = TecladoIn.readLineInt();
            switch (res) {
                case 1 ->
                    altaNuevoRiel();
                case 2 ->
                    bajaRiel();
                case 3 ->
                    modificarRiel();
                case 4 ->
                    System.out.println("Fin ABM rieles");
                default ->
                    System.out.println("Ingrese una opcion del 1 al 4");
            }
        } while (res != 4);
    }
    public static void altaNuevoRiel() {
        Estacion aux1, aux2;
        String text;
        do {
            System.out.println("Ingrese nombre de la primera Estacion (si no existe se le pedira que ingrese otro nombre nuevamente)");
            aux1=(Estacion) grafoMapa.recuperarVertice(TecladoIn.readLine());
        } while (aux1 == null); //repite hasta encontrar la estacion
        do {
            System.out.println("Ingrese nombre de la segunda Estacion (si no existe se le pedira que ingrese otro nombre nuevamente)");
            aux2=(Estacion) grafoMapa.recuperarVertice(TecladoIn.readLine());
        } while (aux2 == null); //repite hasta encontrar la estacion
        System.out.println("Ingrese los Km del nuevo Riel");
        double riel = TecladoIn.readLineDouble();
        grafoMapa.insertarArco(aux1, aux2, riel);
        text="nuevo Riel ingresado";
        System.out.println(text);
        modificarTxt(text);
        modificarTxt(aux1.getNumero()+", "+riel+","+aux2.getNumero());
    }
    public static void bajaRiel() {
        Estacion aux1, aux2;
        String text;
        do {
            System.out.println("Ingrese nombre de la primera Estacion (si no existe se le pedira que ingrese otro nombre nuevamente)");
            aux1=(Estacion) grafoMapa.recuperarVertice(TecladoIn.readLine());
        } while (aux1 == null);
        do {
            System.out.println("Ingrese nombre de la segunda Estacion (si no existe se le pedira que ingrese otro nombre nuevamente)");
            aux2=(Estacion) grafoMapa.recuperarVertice(TecladoIn.readLine());
        } while (aux2 == null);
        // se encontraron las dos estaciones
        grafoMapa.eliminarArco(aux1, aux2);
        text="El Riel entre "+aux1.getNumero()+" y "+aux2.getNumero()+" fue eliminado";
        System.out.println(text);
        modificarTxt(text);
    }
    public static void modificarRiel() {
        Estacion aux1, aux2;
        do {
            System.out.println("Ingrese nombre de la primera Estacion (si no existe se le pedira que ingrese otro nombre nuevamente)");
            aux1=(Estacion) grafoMapa.recuperarVertice(TecladoIn.readLine());
        } while (aux1 == null);
        do {
            System.out.println("Ingrese nombre de la segunda Estacion (si no existe se le pedira que ingrese otro nombre nuevamente)");
            aux2=(Estacion) grafoMapa.recuperarVertice(TecladoIn.readLine());
        } while (aux2 == null);
        // se encontraron las dos estaciones
        grafoMapa.eliminarArco(aux1, aux2);
        System.out.println("ingrese el nuevo valor km del Riel");
        double riel = TecladoIn.readLineDouble();
        grafoMapa.insertarArco(aux1, aux2, riel);
        System.out.println("El riel fue modificado");
        String text="Se modifico el Riel entre"+aux1.getNumero()+" y "+aux2.getNumero();
        modificarTxt(text);
    }
    //ABM Lineas
    private static void ABMLineas() {
        int res;
        do {
            System.out.println("""
                           Menu de opciones de Linea :  
                           1- Alta Linea 
                           2- Baja Linea  
                           3- Modificar Linea  
                           4- Terminar""");
            res = TecladoIn.readLineInt();
            switch (res) {
                case 1 ->
                    altaNuevaLinea();
                    case 2 ->
                    bajaLinea();
//                case 3 ->
//                    modificarLinea();
                case 4 ->
                    System.out.println("Fin ABM Lineas");
                default ->
                    System.out.println("Ingrese una opcion del 1 al 4");
            }
        } while (res != 4);
    }
    public static void altaNuevaLinea() {
        String nombre, aux, text;
        boolean seguir=true, existe=false;;
        Lista listaEstaciones = new Lista();
        do {
            System.out.println("Ingrese nombre de la nueva Linea");
            nombre = TecladoIn.readLine();
        } while (hashLineas.get(nombre) != null);// mientras no exista previamente el tren
        do{
            existe=false;
            do{
                System.out.println("ingrese estacion");
                aux = TecladoIn.readLine();
                if (avlEstaciones.obtenerDato(aux) != null) {
                    listaEstaciones.insertar(aux, listaEstaciones.longitud() +1);
                    existe=true;
                    System.out.println("estacion insertada");
                } else {
                    System.out.println("no existe estacion (ingrese otro nombre nuevamente)");
                }
            }while(!existe);
            System.out.println("desea ingresar otra estacion  si/no");
            if ("no".equals(TecladoIn.readLine())) {
                if(listaEstaciones.longitud()<2){
                    System.out.println("disculpe, la linea debe tener al menos 2 estaciones, ingrese otra estacion porfavor");
                }else{
                    seguir = false;
                }
            }
        }while(seguir && listaEstaciones.longitud() < 2);
        hashLineas.put(nombre, new Linea(nombre, listaEstaciones)); // crea y guarda la nueva linea en el hash
        text="nueva Linea ingresada";
        System.out.println(text);
        modificarTxt(text);
        modificarTxt(listaEstaciones.toString());
    }
    public static void bajaLinea(){
        String nombre, text;
        boolean seguir=true;
        do {
            System.out.println("Ingrese nombre de la Linea a eliminar");
            nombre=TecladoIn.readLine();
            if(hashLineas.get(nombre)!=null){
                hashLineas.remove(nombre); // elimina la linea del hash
                seguir=false;
                text="La linea "+nombre+" fue eliminada";
                System.out.println(text);
                modificarTxt(text);
            }else{
                System.out.println(("La Linea no existe, ingrese otro nombre"));
            }
        }while(seguir);
    }
    public static void modificarLinea(){//testear
        String nombre;
        boolean seguir=true, valido=false;
        Estacion aux1,aux2;
        int i=0;
            System.out.println("Ingrese nombre de la Linea a modificar");
            nombre=TecladoIn.readLine();
            if(hashLineas.containsKey(nombre)){
            do{
                System.out.println("ingrese el nuevo recorrido de la linea, Ejem: Mitre;Retiro;Campana;Zárate;Baradero");
                //String nuevaRecorrido=TecladoIn.readLine();
                Lista listaEstaciones = new Lista();
                String[] arregloDeEstaciones = (TecladoIn.readLine()).split(";");
                //guarda el    
                aux1 = (Estacion)avlEstaciones.obtenerDato(nombre);
                if(aux1!=null){
                    listaEstaciones.insertar(aux1, i);
                    i++;
                    do{
                            aux2 = (Estacion)avlEstaciones.obtenerDato(nombre);
                            if(aux2!=null){
                                if(grafoMapa.existeCamino(aux1.getNombre(),aux2.getNombre())==true){
                                    listaEstaciones.insertar(aux2, i);
                                    i++;
                                    aux1=aux2;
                                }
                            }else{
                                seguir=false;
                            }
                        }while(arregloDeEstaciones[i]!= null && seguir);
                    }
                    if(seguir==true){
                        hashLineas.put(nombre, new Linea(nombre, listaEstaciones));// actualizo el nuevo recorrido de la linea
                        valido=true;
                        String text="Se modifico la linea"+nombre+":\n"+ listaEstaciones.toString();
                        modificarTxt(text);
                    }else{
                        System.out.println("el recorrido no es valido, ingrese otro por favor");
                }
            }while(valido==false);
        }
    }
    //SUBMENU Trenes
    private static void SubMenuTrenes() {
        int res;
        do {
            System.out.println("""
                           Sub Menu de opciones de Trenes :  
                           1- mostrar toda la información del Tren 
                           2- verificar si está destinado a alguna línea 
                           3- Terminar""");
            res = TecladoIn.readLineInt();
            switch (res) {
                case 1 ->
                    mostrarInfoTrenes();
                case 2 ->
                    verificarTrenLinea();
                case 3 ->
                    System.out.println("Fin sub menu trenes");
                default ->
                    System.out.println("Ingrese una opcion del 1 al 3");
            }
        } while (res != 3);
    }
    public static void mostrarInfoTrenes() {
        Tren trenAux;
        do {
            System.out.println("Ingrese Codigo del Tren (si el codigo no existe o es incorrecto se le pedira que lo ingrese nuevamente)");
            trenAux = (Tren) avlTrenes.obtenerDato(TecladoIn.readLine());
        } while (trenAux == null);
        System.out.println(trenAux.toString());
    }//check
    public static void verificarTrenLinea() {
        String cod;
        Tren trenAux;
        do {
            System.out.println("Ingrese Codigo del Tren (si el codigo no existe o es incorrecto se le pedira que lo ingrese nuevamente");
            cod = TecladoIn.readLine();
            trenAux = (Tren) avlTrenes.obtenerDato(cod);
        } while (trenAux == null);

        if (!"no-asignado".equals(trenAux.getLineaAsignada())) {// 
            Linea lineaAux = (Linea) hashLineas.get(trenAux.getLineaAsignada());
            System.out.println("La linea asignada del tren: " + cod + ", es: " + lineaAux.getNombre());
            System.out.println("Las ciudades que visitara son: ");
            lineaAux.getListaDeEstaciones().toString();
        } else {
            System.out.println("el tren no tiene una linea asignada");
        }
    }
    //SUBMENU Estaciones
    private static void SubMenuEstaciones() {
        int res;
        do {
            System.out.println("""
                           Sub Menu de opciones de Estacioes :  
                           1- mostrar toda la información de Estacion 
                           2- listar todas las estaciones que comienzan con una subcadena 
                           3- Terminar""");
            res = TecladoIn.readLineInt();
            switch (res) {
                case 1 ->
                    mostrarInfoEstacion();
                case 2 ->
                    estacionesComienzanConSubcadena();
                case 3 ->
                    System.out.println("Fin sub menu trenes");
                default ->
                    System.out.println("Ingrese una opcion del 1 al 3");
            }
        } while (res != 3);
    }
    public static void mostrarInfoEstacion(){
        Estacion estacionAux;
        do {
            System.out.println("Ingrese nombre de la Estacion (si no existe o el nombre es incorrecto se le pedira que lo ingrese nuevamente)");
            estacionAux=(Estacion) avlEstaciones.obtenerDato(TecladoIn.readLine());
        } while (estacionAux==null);
        System.out.println(estacionAux.toString());
    }
    public static void estacionesComienzanConSubcadena(){
        String subcadena;
        System.out.println("Ingrese subcadena");
        subcadena=TecladoIn.readLine();
        Lista aux=avlEstaciones.listarClavesPorPrefijo(subcadena);
        System.out.println(aux.toString());
    }
    //SUBMENU Viajes
    private static void SubMenuViajes() {
        int res;
        do {
            System.out.println("""
                           Sub Menu de opciones de Viajes :  
                           1- Obtener el camino que llegue de A a B que pase por menos estaciones 
                           2- Obtener el camino que llegue de A a B de menor distancia en kilómetros
                           3- Obtener todos los caminos posibles para llegar de A a B sin pasar por una estación C dada
                           4- Verificar si es posible llegar de A a B recorriendo como máximo una cantidad X de kilómetros
                           5- Terminar""");
            res = TecladoIn.readLineInt();
            switch (res) {
                case 1 ->
                    caminoDeAaBMenosEstaciones();
                case 2 ->
                    caminoDeAaBMenosKm();
                case 3 ->
                    posiblesCaminosDeAaBsinPasarPorUnaEstacionC();
                case 4 -> 
                    existeCaminoConMaximoKm();
                case 5 ->
                    System.out.println("Fin sub menu viajes");
                default ->
                    System.out.println("Ingrese una opcion del 1 al 5");
            }
        } while (res != 5);
    }
    private static void existeCaminoConMaximoKm() {
        double x=0;
        Estacion est1, est2;
        Estacion[] estaciones=encontrarEstacionesAyB();
        est1=estaciones[0];
        est2=estaciones[1];
        do {
            System.out.println("Ingrese un valor mayor a 0");
            x = TecladoIn.readLineDouble();
        } while (x>0);
        boolean res = grafoMapa.caminoConTopeDeKm(est1, est2, x);
        if(res==true){
            System.out.println("Existe un camino entre "+est1.getNombre()+" y "+est2.getNombre()+" menor a la distancia: "+x);
        }else{
            System.out.println("NO existe un camino entre "+est1.getNombre()+" y "+est2.getNombre()+" menor a la distancia: "+x);
        }
    }
    public static void caminoDeAaBMenosEstaciones() {
        Estacion est1, est2;
        Estacion[] estaciones=encontrarEstacionesAyB();
        est1=estaciones[0];
        est2=estaciones[1];
        Lista aux = grafoMapa.caminoMasCorto(est1, est2);
        System.out.println("el camino mas corto entre " + est1.getNombre() + "y " + est2.getNombre() + " es: ");
        System.out.println(aux.toString());
    }
    public static void caminoDeAaBMenosKm() {
        Estacion est1, est2;
        Estacion[] estaciones=encontrarEstacionesAyB();
        est1=estaciones[0];
        est2=estaciones[1];
        Lista aux = grafoMapa.caminoConMenosPeso(est1, est2);
        System.out.println("el camino mas corto entre " + est1.getNombre() + "y " + est2.getNombre() + " es: ");
        System.out.println(aux.toString());
    }
    public static void posiblesCaminosDeAaBsinPasarPorUnaEstacionC() {
        String nom3;
        Estacion est1, est2, est3;
        Estacion[] estaciones=encontrarEstacionesAyB();
        est1=estaciones[0];
        est2=estaciones[1];
        do {
            System.out.println("Ingrese nombre del estacion C por la cual no debe pasar");
            nom3 = TecladoIn.readLine();
            est3 = (Estacion) avlEstaciones.obtenerDato(nom3);
        } while (est3 == null);

        Lista aux = grafoMapa.listaConCaminosQueNoTieneC(est1, est2, est3);
        System.out.println("la lista de caminos entre " + est1.getNombre() + "y " + est2.getNombre() + " sin pasar por " + est2.getNombre() + "  es: ");
        System.out.println(aux.toString());
    }
    private static Estacion[] encontrarEstacionesAyB(){
        Estacion[] estaciones =new Estacion[2];
        String nom1, nom2;
        Estacion est1, est2;
        do {
            System.out.println("Ingrese nombre del estacion A");
            nom1 = TecladoIn.readLine();
            est1 = (Estacion) avlEstaciones.obtenerDato(nom1);
            if(est1==null){
                System.out.println("La estacion no existe, ingrese otro nombre");
            }
        } while (est1 == null);
        estaciones[0]=est1;
        do {
            System.out.println("Ingrese nombre del estacion B");
            nom2 = TecladoIn.readLine();
            est2 = (Estacion) avlEstaciones.obtenerDato(nom2);
            if(est2==null){
                System.out.println("La estacion no existe, ingrese otro nombre");
            }
        } while (est2 == null);
        estaciones[1]=est2;
        return estaciones;
    }//check
}