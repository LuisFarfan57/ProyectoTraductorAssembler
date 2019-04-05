/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TraductorAssembler;

import java.awt.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;

/**
 *
 * @author garya
 */
public class Lector {
    public static String Obtener(String strPath)
    {
        File Archivo=new File(strPath);
        String cadena="";
        if(Archivo.exists()==true)
        {
            Traductor traductor = new Traductor();
            FileReader LecturaArchivo;
            try {
                LecturaArchivo = new FileReader(Archivo);
                BufferedReader LeerArchivo = new BufferedReader(LecturaArchivo);
                String Linea="";
                try {                    
                    while((Linea=LeerArchivo.readLine()) != null)
                    {                        
                        traductor.traducir(Linea);
                    }                   
                    LecturaArchivo.close();
                    LeerArchivo.close();                                        
                    return cadena;
                    
                } catch (IOException ex) {
                    return ex.getMessage();                    
                }
            } catch (FileNotFoundException ex) {
                return ex.getMessage();                
            }            
        }
        else
        {
            return "No existe el archivo";            
        }
}

}
