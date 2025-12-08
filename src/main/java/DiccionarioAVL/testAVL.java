/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DiccionarioAVL;

/**
 *
 * @author JoaquinNoillet
 */
public class testAVL {

    public static void main(String[] args) {
        Diccionario prueba = new Diccionario();

        //prueba.insertar(7,7 );
        //prueba.insertar(10,10 );
        //prueba.insertar(18,18);
        //prueba.insertar(25,25);
        //prueba.insertar(16,16);
        //prueba.insertar(27,27);
        //prueba.insertar(26,26);
        //prueba.insertar(24,24);

        // prueba.insertar(90, 90);
        // prueba.insertar(19, 19);
        // prueba.insertar(33, 33);
        // prueba.insertar(24, 24);
        // prueba.insertar(15, 15);
        // prueba.insertar(12, 12);
        // prueba.insertar(35, 35);
        // prueba.insertar(27, 27);
        // prueba.insertar(23, 23);
        // prueba.insertar(40, 40);
        // prueba.insertar(100, 100);
        // prueba.insertar(17, 17);
        // prueba.insertar(13, 13);
        // prueba.insertar(18, 18);
        // prueba.insertar(22, 22);
        // prueba.insertar(50, 50);
        // prueba.insertar(20, 20);
        // prueba.insertar(30, 30);
        // prueba.insertar(37, 37);
        // prueba.insertar(16, 16);
        // prueba.insertar(105, 105);

        prueba.insertar(30, 30);
        prueba.insertar(20, 20);
        prueba.insertar(50, 50);
        prueba.insertar(15, 15);
        prueba.insertar(24, 24);
        prueba.insertar(35, 35);
        prueba.insertar(100, 100);
        prueba.insertar(12, 12);
        prueba.insertar(18, 18);
        prueba.insertar(22, 22);
        prueba.insertar(27, 27);
        prueba.insertar(33, 33);
        prueba.insertar(40, 40);
        prueba.insertar(90, 90);
        prueba.insertar(105, 105);
        prueba.insertar(13, 13);
        prueba.insertar(17, 17);
        prueba.insertar(19, 19);
        prueba.insertar(23, 23);
        prueba.insertar(37, 37);
        prueba.insertar(16, 16);

        System.out.println("---------------------");
        System.out.println("A: "+ prueba.listarClaves().toString());
        System.out.println("---------------------");
        System.out.println("B: "+ prueba.listarElementos().toString());
        System.out.println("---------------------");
        System.out.println(prueba.toString());
        System.out.println("---------------------");
        //System.out.println("se elimina el nodo ");
        //System.out.println(prueba.eliminar(27));
        System.out.println("---------------------");
        //System.out.println(prueba.toString());
        //System.out.println("B: "+ prueba.listarElementos().toString());


    }
}
