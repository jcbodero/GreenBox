/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuarios.TDAs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;



/**
 *
 * @author CltControl
 */
public abstract class Usuario {
    private String nombreUsuario;
    private String clave;
    private int rol;

    public Usuario(String nombreUsuario, String clave, int rol) {
        this.nombreUsuario = nombreUsuario;
        this.clave = clave;
        this.rol = rol;
    }
    
    public String getNombreUsuario() {
	return this.nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
	this.nombreUsuario = nombreUsuario;
    }
    
    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    public boolean equals(Usuario usuario) {
        return this.getNombreUsuario().equalsIgnoreCase(usuario.getNombreUsuario());
    }
    
    public abstract void menu();
    
    public static Usuario buscarUsuario(String nombreUsuario, LinkedList<Usuario> usuarios){
        for (Usuario usuario : usuarios) {
            if(nombreUsuario.equalsIgnoreCase(usuario.getNombreUsuario())){
                return usuario;
            }
        }
        return null;
    }
    
    public static LinkedList<Usuario> cargarUsuarios(){
        LinkedList<Usuario> usuarios= new LinkedList<>();
        File archivo = new File("src/archivos/usuarios.txt");
        try {
            Scanner sc = new Scanner(archivo);
            sc.useDelimiter("\n");
            while(sc.hasNext()){
                String linea = sc.nextLine();
                if (linea.length()!=0){
                    String[] campos = linea.split("\\\n");
                    campos = campos[0].split("\\,");
                    if (Integer.parseInt(campos[2])==1){
                        Usuario c1 = new Cliente(campos[3], campos[4], campos[5], campos[6], campos[7], campos[0], campos[1]);
                        usuarios.add(c1);
                    }else{
                        Usuario c1 = new Administrador(campos[0], campos[1]);
                        usuarios.add(c1);
                    } 
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return usuarios;
    }
}
