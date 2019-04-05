/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traductorassembler;

import java.util.Hashtable;

/**
 *
 * @author luise
 */
public class Traductor {
    
    Hashtable<String, Integer> Simbolos = new Hashtable<String, Integer>();
    Hashtable<String, Integer> Etiquetas = new Hashtable<String, Integer>();
    int contadorLinea;
    
    public Traductor(){
        contadorLinea = 0;
        for (int i = 0; i < 16; i++) {
            Simbolos.put("R" + i, i);
        }        
        
        Simbolos.put("SCREEN", 16384);
        Simbolos.put("KBD", 24576);
        Simbolos.put("SP", 0);
        Simbolos.put("LCL", 1);
        Simbolos.put("ARG", 2);
        Simbolos.put("THIS", 3);
        Simbolos.put("THAT", 4);
        Simbolos.put("LOOP", 4);
        Simbolos.put("THIS", 3);
        
        
    }
    
    public void traducir(String linea){
        String instruccion = "";
        int contador = 0;
        
        while(linea.charAt(contador) != '/' || linea.charAt(contador) == '\u0000'){
            instruccion += linea.charAt(contador);
            contador++;
        }
        
        instruccion = instruccion.trim();
    }        
    
}
