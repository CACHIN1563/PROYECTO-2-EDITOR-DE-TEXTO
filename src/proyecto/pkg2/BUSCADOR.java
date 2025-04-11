/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.pkg2;

/**
 *
 * @author cachi
 */

import java.util.ArrayList;
import java.util.List;

public class BUSCADOR {

    public static List<Integer> buscarOcurrencias(String texto, String palabra, boolean ignoreCase) {
        List<Integer> posiciones = 
                new ArrayList<>();
        String textoComparar = 
                ignoreCase ? texto.toLowerCase() : texto;
        String palabraComparar = 
                ignoreCase ? palabra.toLowerCase() : palabra; //CONERTIMOS TODO EN MINUSCULAS.

        int 
            index = textoComparar.indexOf(palabraComparar);
                while 
                        (index >= 0) {
                         posiciones.add(index);
                         index = textoComparar.indexOf(palabraComparar, index + palabraComparar.length());  //BUSCAMOS LA POSICIÓN PARA GUARDARLA EN UNA LISTA
        }
        return posiciones;
    }

    public static String reemplazar
                        (String texto, String buscar, String reemplazo, boolean ignoreCase) {
        if 
                (ignoreCase) {
            return 
                    texto.replaceAll("(?i)" + buscar, reemplazo); // REMPLAZA UNA PALABRA POR LA OTRA
        }
        return 
                texto.replaceAll(buscar, reemplazo);
    }
}
