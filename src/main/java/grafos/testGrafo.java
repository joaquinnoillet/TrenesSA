package grafos;

//import lineales.dinamicas.Lista;

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
        prueba.insertarVertice("A");
        prueba.insertarVertice("B");
        prueba.insertarVertice("C");
        prueba.insertarVertice("D");
        prueba.insertarVertice("E");
        prueba.insertarVertice("F");
        prueba.insertarVertice("G");
        prueba.insertarVertice("B");

        prueba.insertarArco(prueba.recuperarVertice("A"),prueba.recuperarVertice("B"), 100);
        prueba.insertarArco(prueba.recuperarVertice("A"),prueba.recuperarVertice("D"), 50);
        prueba.insertarArco(prueba.recuperarVertice("A"),prueba.recuperarVertice("F"), 80);
        prueba.insertarArco(prueba.recuperarVertice("C"),prueba.recuperarVertice("B"), 90);
        prueba.insertarArco(prueba.recuperarVertice("D"),prueba.recuperarVertice("E"), 85);
        prueba.insertarArco(prueba.recuperarVertice("C"),prueba.recuperarVertice("F"), 40);
        prueba.insertarArco(prueba.recuperarVertice("G"),prueba.recuperarVertice("D"), 40);
        
        prueba.insertarArco("A","B", 100);
        
        System.err.println(prueba.toString());

        System.err.println("prueba de modulo");
        //Lista a = prueba.listarAnchura();
        //Lista b = prueba.listarProfundidad();
        //Lista n = prueba.concatenar(a, b);

        
        boolean res = prueba.caminoConTopeDeKm("A", "G", 8000);

        System.out.println(res);
    }

}
