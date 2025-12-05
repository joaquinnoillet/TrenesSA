package grafos;

import lineales.dinamicas.Lista;

public class testGrafo {
    public static void main(String[] args) {
        GrafoEtiquetado prueba = new GrafoEtiquetado();
        
        
        /**
         * 
         * System.out.println(prueba.insertarVertice("argentina"));
        System.out.println(prueba.insertarVertice("alemania"));
        System.out.println(prueba.insertarVertice("brasil"));
        System.out.println(prueba.insertarVertice("costa rica"));
        System.out.println(prueba.insertarVertice("colombia"));
        System.out.println(prueba.insertarVertice("senegal"));

        System.out.println(prueba.insertarArco(prueba.recuperarVertice("argentina"),prueba.recuperarVertice("alemania"), 100));
        System.out.println(prueba.insertarArco(prueba.recuperarVertice("argentina"),prueba.recuperarVertice("costa rica"), 50));
        System.out.println(prueba.insertarArco(prueba.recuperarVertice("argentina"),prueba.recuperarVertice("senegal"), 80));
        System.out.println(prueba.insertarArco(prueba.recuperarVertice("brasil"),prueba.recuperarVertice("alemania"), 90));
        System.out.println(prueba.insertarArco(prueba.recuperarVertice("brasil"),prueba.recuperarVertice("colombia"), 85));
        System.out.println(prueba.insertarArco(prueba.recuperarVertice("brasil"),prueba.recuperarVertice("senegal"), 40));
        
         */
        System.out.println(prueba.insertarVertice("A"));
        System.out.println(prueba.insertarVertice("B"));
        System.out.println(prueba.insertarVertice("C"));
        System.out.println(prueba.insertarVertice("D"));
        System.out.println(prueba.insertarVertice("E"));
        System.out.println(prueba.insertarVertice("F"));
        System.out.println(prueba.insertarVertice("G"));
        System.out.println(prueba.insertarVertice("H"));
        System.out.println(prueba.insertarVertice("H"));

        prueba.insertarArco("A","B", 100);
        prueba.insertarArco("A","D", 50);
        prueba.insertarArco("A","F", 80);
        prueba.insertarArco("C","B", 90);
        prueba.insertarArco("D","E", 85);
        prueba.insertarArco("C","F", 40);
        prueba.insertarArco("G","D", 40);

        
        System.err.println(prueba.toString());

        System.err.println("prueba de modulo");
        //Lista a = prueba.listarAnchura();
        //Lista b = prueba.listarProfundidad();
        //Lista n = prueba.concatenar(a, b);

        
        boolean res = prueba.caminoConTopeDeKm("A", "G", 8000);

        System.out.println(res);
    }

}
