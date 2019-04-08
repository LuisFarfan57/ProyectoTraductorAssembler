/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package traductorassembler;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author luise
 */
public class Traductor {
    
    Hashtable<String, Integer> Simbolos = new Hashtable<String, Integer>();
    Hashtable<String, Integer> Etiquetas = new Hashtable<String, Integer>();
    List<String> codigo = new LinkedList<String>();
    List<String> codigoSinEtiquetas = new LinkedList<String>();
    
    public Traductor(String codigo){  
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
        String instruccion="";
        String[] codigoTemp = codigo.split("\n");
        for (int i = 0; i < codigoTemp.length; i++) {
              int contador = 0;    
            while(codigoTemp[i].charAt(contador) != '/' || codigoTemp[i].charAt(contador) != '\u0000'){
                instruccion += codigoTemp[i].charAt(contador);
                contador++;
            }
            if(instruccion!=""){
                if(!instruccion.startsWith("(")){
                    codigoSinEtiquetas.add(instruccion.trim());   
                    if(codigoSinEtiquetas.get(i).equals("")){
                        codigoSinEtiquetas.remove(i);
                    }
                }
                else{
                     Etiquetas.put(instruccion.trim(), (codigoSinEtiquetas.size()+1));  
                }
            }                
        }
    }
    
    public String traducir(){                    
        String binario = "";
        
        for (int i = 0; i < codigo.size(); i++) {
            if(codigo.get(i).startsWith("@"))
                binario = traducirInstruccionA(codigo.get(i));                            
            else                            
                binario = traducirInstruccionC(codigo.get(i));                          
        }            
        
        return binario;
    }        
    
    String traducirInstruccionA(String linea){
        String binario = "0";
        String numero = linea.substring(1);
        String tempBinario = "";
        Integer valor=0;
        if(isInteger(numero)){
            tempBinario = Integer.toBinaryString(Integer.parseInt(numero));
                        
            for (int i = tempBinario.length() - 1; i < 15; i++) {
                tempBinario = "0" + tempBinario;
            }
            
            binario = tempBinario;            
        }
        else if((valor=Simbolos.get(linea))!=null){
            numero=valor.toString();
            tempBinario = Integer.toBinaryString(Integer.parseInt(numero));                      
            for (int i = tempBinario.length() - 1; i < 15; i++) {
                tempBinario = "0" + tempBinario;
            }
            
            binario = tempBinario;
           
        }
        else if((valor=Etiquetas.get(linea))!=null){
            numero=valor.toString();
            tempBinario = Integer.toBinaryString(Integer.parseInt(numero));                      
            for (int i = tempBinario.length() - 1; i < 15; i++) {
                tempBinario = "0" + tempBinario;
            }
            
            binario = tempBinario;
        }
        
        return binario;
    }
    
    String traducirInstruccionC(String linea){
        String binario = "";
        
        return binario;
    }
    
    int regresarLineaEtiqueta(String nombre){
        return 0;
    }
    
    public boolean isInteger(String numero){
    try{
        Integer.parseInt(numero);
        return true;
    }catch(NumberFormatException e){
        return false;
    }
    
    
}
    
}
