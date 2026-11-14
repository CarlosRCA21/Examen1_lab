/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pokemon;

/**
 *
 * @author ANGILI PALACIOS
 */
import java.io.*;
public class GestorSerializacion {
 public static byte[] guardarEquipoEnMemoria(Entrenador entrenador) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(entrenador); 
        oos.close();
        return baos.toByteArray();
    }

    public static Entrenador restaurarEquipoDesdeMemoria(byte[] datos) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(datos);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (Entrenador) ois.readObject(); 
    }   
}
