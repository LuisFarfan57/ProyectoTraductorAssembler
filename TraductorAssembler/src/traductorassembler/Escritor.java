/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectomeia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author garya
 */
public class Escritor {
    public static boolean Escribir(String strPath,String strContenido)
    {
        File Archivo = new File(strPath);
         String[] spli=strContenido.split("\n");
        try
        {
            if(!Archivo.exists()){
            Archivo.createNewFile();
            }
            else{
                Archivo.delete();
                Archivo.createNewFile();
            }
                FileWriter Escribir = new FileWriter(Archivo,true);
                BufferedWriter bw = new BufferedWriter(Escribir);
                for (int i = 0; i < spli.length; i++) {
                bw.write(spli[i]);
                bw.newLine();
            }               

                
                bw.close();
                Escribir.close();
                
                return true;
        }
        catch(IOException ex)
        {
            return false;
        } 
        
}

}
