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
    List<String> codigoSinEtiquetas = new LinkedList<String>();
    Integer variableDisponible;
    
    public Traductor(String codigo){  
        variableDisponible = 16;
        
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
        
        String instruccion = "";
        String[] codigoTemp = codigo.split("\n");
        
        for (int i = 0; i < codigoTemp.length; i++) {
            instruccion = "";
            int contador = 0;    
            
            if(!codigoTemp[i].startsWith("/") && !codigoTemp[i].equals("")){
                while(codigoTemp[i].charAt(contador) != '/'){
                instruccion += codigoTemp[i].charAt(contador);
                contador++;
                
                if(contador == codigoTemp[i].length())
                    break;
            }
                
                instruccion = instruccion.trim();
                        
                if(!instruccion.startsWith("(")){                
                    codigoSinEtiquetas.add(instruccion.trim());                
                }
                else{
                    Etiquetas.put(instruccion.trim().replace('(', '\u0000').replace(')', '\u0000').trim(), (codigoSinEtiquetas.size()));
                }
            }
        }
        
        for (int i = 0; i < codigoSinEtiquetas.size(); i++) {
            if(codigoSinEtiquetas.get(i).startsWith("@")){                
                if(!isInteger(codigoSinEtiquetas.get(i).substring(1))){
                    if(!Simbolos.containsKey(codigoSinEtiquetas.get(i).substring(1)) && !Etiquetas.containsKey(codigoSinEtiquetas.get(i).substring(1))){
                        Simbolos.put(codigoSinEtiquetas.get(i).substring(1), variableDisponible);
                        variableDisponible++;
                    }
                }                
            }
        }
    }
    
    public String traducir(){    
        try{
            String binario = "";
        
            for (int i = 0; i < codigoSinEtiquetas.size(); i++) {
                if(codigoSinEtiquetas.get(i).startsWith("@"))
                    binario += traducirInstruccionA(codigoSinEtiquetas.get(i));
                else                              
                    binario += traducirInstruccionC(codigoSinEtiquetas.get(i));
            
                if(i != codigoSinEtiquetas.size() - 1)
                    binario += System.lineSeparator();
            }            
        
            return binario;            
        }
        catch (Exception e){
            return "ERROR";
        }        
    }        
    
    String traducirInstruccionA(String linea){
        String binario = "0";
        String numero = linea.substring(1);
        String tempBinario = "";
        Integer valor = 0;
        
        if(isInteger(numero)){
            tempBinario = Integer.toBinaryString(Integer.parseInt(numero));
                        
            for (int i = tempBinario.length() - 1; i < 15; i++) {
                tempBinario = "0" + tempBinario;
            }
            
            binario = tempBinario;            
        }
        else if(Simbolos.containsKey(numero)){            
            valor = Simbolos.get(numero);
            tempBinario = Integer.toBinaryString(Integer.parseInt(valor.toString()));
            
            for (int i = tempBinario.length() - 1; i < 15; i++) {
                tempBinario = "0" + tempBinario;
            }
            
            binario = tempBinario;
           
        }
        else if(Etiquetas.containsKey(numero)){            
            valor = Etiquetas.get(numero);
            tempBinario = Integer.toBinaryString(Integer.parseInt(valor.toString()));   
            
            for (int i = tempBinario.length() - 1; i < 15; i++) {
                tempBinario = "0" + tempBinario;
            }
            
            binario = tempBinario;
        }        
        
        
        return binario;
    }
    
    String traducirInstruccionC(String linea){
        String binario = "111";
        String[] campos = new String[3];
        
        if(linea.contains("=") && linea.contains(";")){
            String[] primeraSeparacion = linea.split("=");
            campos[0] = primeraSeparacion[0].replace(' ', '\u0000');
            String[] segundaSeparacion = primeraSeparacion[1].split(";");
            campos[1] = segundaSeparacion[0].replace(' ', '\u0000');
            campos[2] = segundaSeparacion[1].replace(' ', '\u0000');
        }
        else if(linea.contains("=") && !linea.contains(";")){
            String[] primeraSeparacion = linea.split("=");
            campos[0] = primeraSeparacion[0].replace(' ', '\u0000');
            campos[1] = primeraSeparacion[1].replace(' ', '\u0000');
            campos[2] = "";
        }
        else if(!linea.contains("=") && linea.contains(";")){
            String[] primeraSeparacion = linea.split(";");
            campos[0] = "";
            campos[1] = primeraSeparacion[0].replace(' ', '\u0000');
            campos[2] = primeraSeparacion[1].replace(' ', '\u0000');
        }
        else{
            campos[0] = "";
            campos[1] = linea.replace(' ', '\u0000');
            campos[2] = "";
        }
        
        if(campos[1].contains("M"))
            binario += "1";
        else
            binario += "0";
        
        switch(campos[1]){            
            case "0":
                binario += "101010";
                break;
            case "1":
                binario += "111111";
                break;
            case "-1":
                binario += "111010";
                break;
            case "D":
                binario += "001100";
                break;
            case "A": case "M":
                binario += "110000";
                break;
            case "!D":
                binario += "001101";
                break;
            case "!A": case "!M":
                binario += "110001";
                break;
            case "-D":
                binario += "001111";
                break;
            case "-A": case "-M":
                binario += "110011";
                break;
            case "D+1":
                binario += "011111";
                break;
            case "A+1": case "M+1":
                binario += "110111";
                break;
            case "D-1":
                binario += "001110";
                break;
            case "A-1": case "M-1":
                binario += "110010";
                break;
            case "D+A": case "D+M":
                binario += "000010";
                break;
            case "D-A": case "D-M":
                binario += "010011";
                break;
            case "A-D": case "M-D":
                binario += "000111";
                break;
            case "D&A": case "D&M":
                binario += "000000";
                break;
            case "D|A": case "D|M":
                binario += "010101";
                break;                
        }            
        
        switch(campos[0]){
            case "":
                binario += "000";
                break;
            case "M":
                binario += "001";
                break;
            case "D":
                binario += "010";
                break;
            case "MD":
                binario += "011";
                break;                               
            case "A":
                binario += "100";
                break;
            case "AM":
                binario += "101";
                break;
            case "AD":
                binario += "110";
                break;
            case "AMD":
                binario += "111";
                break;
        }
        
        switch(campos[2]){
            case "":
                binario += "000";
                break;
            case "JGT":
                binario += "001";
                break;
            case "JEQ":
                binario += "010";
                break;
            case "JGE":
                binario += "011";
                break;                               
            case "JLT":
                binario += "100";
                break;
            case "JNE":
                binario += "101";
                break;
            case "JLE":
                binario += "110";
                break;
            case "JMP":
                binario += "111";
                break;
        }
        
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
