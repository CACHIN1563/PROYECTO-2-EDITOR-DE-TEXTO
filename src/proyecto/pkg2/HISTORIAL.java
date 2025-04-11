/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyecto.pkg2;

/**
 *
 * @author cachi
 */

import java.util.Stack; //USAREMOS DOS PILAS

public class HISTORIAL {
    private Stack<String> Deshacerpila = new Stack<>(); //GUARDAMOS TODOS LO TEXTOS ANTERIORES
    private Stack<String> Rehacerpila = new Stack<>(); // GUARDAMOS LO ULTIMO QUE SE BORRO

    public void guardar(String textoActual) { //CADA VEZ QUE ESCRIBIMOS UNA NUEVA PALABRA LA GUARDA 
        Deshacerpila.push(textoActual);
        Rehacerpila.clear();
    }

    public String deshacer() { //QUITA EL ULTIMO TEXTO Y LO PONEMOS EN LA PILA DE REHACER
        if (!Deshacerpila.isEmpty()) {
            String estado = Deshacerpila.pop();
            Rehacerpila.push(estado);
            return Rehacerpila.isEmpty() ? "" : Deshacerpila.peek();
        }
        return null;
    }

    public String rehacer() { //QUITA EL ULTIMO TEXTO Y LO PONE EN LA PILA DE DESHACER
        if (!Rehacerpila.isEmpty()) {
            String estado = Rehacerpila.pop();
            Deshacerpila.push(estado);
            return estado;
        }
        return null;
    }
}
