/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuarios.TDAs;

import greenbox.Greenbox;
import greenbox.Renta;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;
import peliculas.TDAs.CopiaPelicula;
import peliculas.TDAs.Pelicula;

/**
 *
 * @author CltControl
 */
public class Cliente extends Usuario{
    private String numeroCedula;
    private String nombres;
    private String apellidos;
    private String correo;
    private String numeroCelular;

    public Cliente(String numeroCedula, String nombres, String apellidos, String correo, String numeroCelular, String nombreUsuario, String clave) {
        super(nombreUsuario, clave, 1);
        this.numeroCedula = numeroCedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.numeroCelular = numeroCelular;
    }
    
    public String getNumeroCedula() {
            return numeroCedula;
    }
    public void setNumeroCedula(String numeroCedula) {
            this.numeroCedula = numeroCedula;
    }
    public String getNombres() {
            return nombres;
    }
    public void setNombres(String nombres) {
            this.nombres = nombres;
    }
    public String getApellidos() {
            return apellidos;
    }
    public void setApellidos(String apellidos) {
            this.apellidos = apellidos;
    }
    public String getCorreo() {
            return correo;
    }
    public void setCorreo(String correo) {
            this.correo = correo;
    }
    public String getNumeroCelular() {
            return numeroCelular;
    }
    public void setNumeroCelular(String numeroCelular) {
            this.numeroCelular = numeroCelular;
    }
    
    public String toString(){
    	return this.getNombres() + " " + this.getApellidos();
    }

    @Override
    public void menu() {
        System.out.println("");
        
        System.out.print((char)27 + "[31;49m");
        System.out.println("* * * * * * * * * * * * * * *");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("         CLIENTE           ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("                           ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("  1.- Rentar.              ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("  2.- Devolver.            ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("  3.- Reportar Perdida.    ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("  4.- Voy a tener suerte.  ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("  5.- Salir                ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("                           ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m");
        System.out.println("* * * * * * * * * * * * * * *");
    }
    
    public void rentar(CopiaPelicula copia, LinkedList<Renta> rentas, int dias) throws IOException{
        Renta.rentar(rentas, copia, this.numeroCedula, dias);
    }
    
    public boolean devolver(Greenbox greenbox){
        System.out.println(" DEVOLVER ");
        System.out.println("\n Ingrese el codigo de barras de la copia a devolver:");
        String codigo = new Scanner(System.in).nextLine();
        Renta renta = Renta.ultimaRentaDeCopia(greenbox.getRentas(), codigo);
        if(renta == null)
            return false;
        
        if(renta.getCedulaCliente().equals(this.getNumeroCedula())){
            Renta.cambiarRenta(greenbox.getRentas(), renta, 2);
            Renta.guardarRentas(greenbox.getRentas());
            return true;
        }
        return false;
    }
    
    public boolean reportarPerdida(Greenbox greenbox){
        System.out.println(" REPORTAR PERDIDA ");
        System.out.println("\n Ingrese el codigo de barras de la copia a devolver:");
        String codigo = new Scanner(System.in).nextLine();
        Renta renta = Renta.ultimaRentaDeCopia(greenbox.getRentas(), codigo);
        if(renta == null)
            return false;
        
        if(renta.getCedulaCliente().equals(this.getNumeroCedula())){
            Renta.cambiarRenta(greenbox.getRentas(), renta, 3);
            Renta.guardarRentas(greenbox.getRentas());
            return true;
        }
        return false;
    }
    
    public static Cliente buscarCliente(String cedula, LinkedList<Usuario> usuarios){
        Cliente cliente;
        for (Usuario usuario : usuarios) {
            if(usuario.getClass().equals(Cliente.class)){
                cliente = (Cliente) usuario;
                if(cliente.getNumeroCedula().equals(cedula)){
                    return cliente;
                }
            }
        }
        return null;
    }
}
