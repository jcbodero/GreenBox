/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package greenbox;

import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import peliculas.TDAs.Pelicula;
import usuarios.TDAs.Administrador;
import usuarios.TDAs.Cliente;
import usuarios.TDAs.Usuario;

/**
 *
 * @author novicompu
 */
public class Greenbox {
    private LinkedList<Pelicula> peliculas;
    private LinkedList<Urbanizacion> urbanizaciones;
    private LinkedList<Usuario> usuarios;
    private LinkedList<Renta> rentas;

    public Greenbox() {
        this.peliculas = (LinkedList<Pelicula>) Pelicula.cargarPeliculas().clone();
        this.urbanizaciones = (LinkedList<Urbanizacion>) Urbanizacion.cargarUrbanizacion().clone();
        this.usuarios = (LinkedList<Usuario>) Usuario.cargarUsuarios().clone();
        this.rentas = (LinkedList<Renta>) Renta.cargarRentas().clone();
    }

    public LinkedList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public LinkedList<Urbanizacion> getUrbanizaciones() {
        return urbanizaciones;
    }

    public LinkedList<Usuario> getUsuarios() {
        return usuarios;
    }

    public LinkedList<Renta> getRentas() {
        return rentas;
    }

    public void setPeliculas(LinkedList<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public void setUrbanizaciones(LinkedList<Urbanizacion> urbanizaciones) {
        this.urbanizaciones = urbanizaciones;
    }

    public void setUsuarios(LinkedList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void setRentas(LinkedList<Renta> rentas) {
        this.rentas = rentas;
    }
    
    public static void main(String args[]){
        Greenbox greenbox = new Greenbox();
        greenbox.run();
        
    }
    
    public void run(){
        Usuario usuario;
        int opcion = 0;
        do{
            this.divisor();
            this.menuPrincipal();
            System.out.print("¿Que desea hacer?: ");
            opcion = Greenbox.recibirEntero();
            
            if(opcion == 1){
                this.divisor();
                usuario = this.ingresar();
                
                if(usuario == null)
                    continue;
                
                if(usuario.getClass().equals(Cliente.class))
                    this.cliente(usuario);
                
                if(usuario.getClass().equals(Administrador.class))
                    this.administrador(usuario);
            }
            if(opcion == 4){
                System.out.println("Vuelva Pronto.");
                break;
            }
        }while(opcion != 4);
    }
    
    public Usuario ingresar(){
        Scanner sc = new Scanner(System.in);
        Usuario usuario;
        String clave;
        String nick;
        System.out.println("");
        System.out.print((char)27 + "[34;31m");
        System.out.println("INGRESO AL SISTEMA");
        do{
            System.out.println("");
            System.out.print((char)27 + "[34;34m");
            System.out.print("Ingrese el nick(Ingrese -1 para regresar al menu Principal):");
            nick = sc.nextLine();
            
            if(nick.equals("-1")){
                usuario = null;
                break;
            }
            
            usuario = Usuario.buscarUsuario(nick, this.usuarios);
            
            if(usuario == null){
                System.out.print((char)27 + "[34;31m");
                System.out.println("El nick ingresado no existe. Por favor ingrese uno que exista.");
                continue;
            }
            
            System.out.println("");
            System.out.print((char)27 + "[34;34m");
            System.out.print("Ingrese su clave:");
            clave = sc.nextLine();
            
            if(!usuario.getClave().equals(clave)){
                System.out.print((char)27 + "[34;31m");
                System.out.println("La clave ingresada es incorrecta.");
            }else{
                break;
            }
        }while(true);
        return usuario;
    }
    
    public void divisor(){
        System.out.print((char)27 + "[34;49m");
        System.out.println("");
        System.out.print((char)27 + "[34;35m");
        System.out.print("=====================================================");
        System.out.print("=====================================================");
        System.out.println("=====================================================");
        
        System.out.print((char)27 + "[34;35m");
        System.out.print("=====================================================");
        System.out.print("=====================================================");
        System.out.println("=====================================================");
        System.out.println("");
    }
    
    public void menuPrincipal(){
        System.out.println("");
        
        System.out.print((char)27 + "[31;49m");
        System.out.println("* * * * * * * * * * * * * *");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("        GREENBOX         ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("                         ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("  1.- Ingresar.          ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("  2.- Registrarse.       ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("  3.- Acerca De.         ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("  4.- Salir              ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("                         ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m");
        System.out.println("* * * * * * * * * * * * * *");
    }
    
    public void menuConsultas(){
        System.out.println("");
        
        System.out.print((char)27 + "[31;49m");
        System.out.println("* * * * * * * * * * * * * *");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("        CONSULTAS         ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("                         ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("  1.- POR GENERO.        ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("  2.- POR NOMBRE.        ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("  3.- TODAS.             ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("  4.- Salir              ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("                         ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m");
        System.out.println("* * * * * * * * * * * * * *");
    }
    
    public void menuKioskos(){
        String lineaMax = "* 1.- * "  + Urbanizacion.nombreKioskoMasGrande(this.urbanizaciones).getNombre() 
                            + "   *";
        int aux = lineaMax.length();
        
        System.out.print((char)27 + "[31;49m");
        for(int i = 0; i < (int)(aux/2); i++){
            System.out.print("* ");
        }
        System.out.println("");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        for(int i = 0; i < (int)(aux - 3 -" kioskos ".length()) / 2; i++){
            System.out.print(" ");
        }
        System.out.print(" KIOSKOS ");
        for(int i = 0; i < (int)(aux - 3 -" kioskos ".length()) / 2; i++){
            System.out.print(" ");
        }
        System.out.println((char)27 + "[31;49m"+"*");
        
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        for(int i = 0; i < (aux - 3); i++){
            System.out.print(" ");
        }
        System.out.println((char)27 + "[31;49m"+"*");
        
        int cont = 1;
        for(Urbanizacion urbanizacion : this.urbanizaciones){
            String linea = cont + ".-  "  + urbanizacion.getNombre();
            System.out.print((char)27 + "[31;49m" + "* ");
            System.out.print((char)27 + "[34;49m");
            System.out.print(linea);
            for(int i = 0; i < aux - "* ".length() - "  *". length()- linea.length(); i++){
                System.out.print(" ");
            }
            System.out.println((char)27 + "[31;49m" +" *");
            cont++;
        }
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        for(int i = 0; i < (aux - 3); i++){
            System.out.print(" ");
        }
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m");
        for(int i = 0; i < (int)(aux/2); i++){
            System.out.print("* ");
        }
        System.out.println("");        
    }
      
    public static int recibirEntero(){
        Scanner sc = new Scanner(System.in);
        int numero = 0;
        do{
            try{
                numero = Integer.parseInt(sc.nextLine());
            }catch(Exception ex){
                System.out.print((char)27 + "[31;49m");
                System.out.println("ERROR DE TIPO DE DATO. EL DATO INGRESADO NO ES ENTERO");
                
                System.out.println("Ingrese nuevamente el numero: ");
                continue;
            }
            break;
        }while(true);
        return numero;
    }
    
    public static Date recibirFecha(){
        Scanner sc = new Scanner(System.in);
        Date fecha;
        do{
            try{
                fecha = new Date(Date.parse(sc.nextLine()));
            }catch(Exception ex){
                System.out.print((char)27 + "[31;49m");
                System.out.println("ERROR DE TIPO DE DATO. LO INGRESADO NO TIENE EL FORMATO CORRECTO");
                
                System.out.println("Ingrese nuevamente la fecha: ");
                continue;
            }
            break;
        }while(true);
        return fecha; 
    }
      
    public Kiosko elegirKiosko(){
        int cont = 1;
        int nKiosko;
        this.divisor();
        System.out.println("");
        this.menuKioskos();
        do{
            System.out.println("");
            System.out.print("¿En que kiosko desea ingresar?:");
            nKiosko = Greenbox.recibirEntero();
        }while(nKiosko > this.urbanizaciones.size() || nKiosko < 1);
        return urbanizaciones.get(nKiosko - 1).getKiosko();
    }
      
    public void cliente(Usuario usuario){
        this.divisor();
        int opcion;
        do{
            this.divisor();
            System.out.println("");
            usuario.menu();
            System.out.print(usuario.getNombreUsuario() + " ¿Que desea hacer?: ");
            opcion = Greenbox.recibirEntero();
            System.out.println("");
            if(opcion == 1){
                System.out.println("RENTAR");
                Kiosko kiosko = this.elegirKiosko();
                System.out.println("");
                int opcion2;
                do{
                    this.divisor();
                    System.out.println("");
                    this.menuConsultas();
                    System.out.println(usuario.getNombreUsuario() + " ¿Que consulta desea realizar?: ");
                    opcion2 = Greenbox.recibirEntero();
                    
                    if(opcion2 == 1)
                        kiosko.rentarPorGenero((Cliente) usuario, this);
                    
                    if(opcion2 == 2)
                        
                        kiosko.rentarPorNombre((Cliente) usuario, this);
                    
                    if(opcion2 == 3)
                        kiosko.rentar((Cliente) usuario, this, kiosko.peliculasDisponibles(this.rentas, this.peliculas));
                    
                }while(opcion2 != 4);
            }
            
            else if(opcion == 2){
                if(((Cliente) usuario).devolver(this))
                    System.out.println("\nCopia Devuelta con exito");
                else
                    System.out.println("\nEl codigo de barras ingresado no corresponde a alguna copia prestada por usted");
            }
            
            else if(opcion == 3){
                if(((Cliente) usuario).reportarPerdida(this))
                    System.out.println("\nPerdida reportada con exito");
                else
                    System.out.println("\nEl codigo de barras ingresado no corresponde a alguna copia prestada por usted");
            
                
            }else if(opcion == 4){
                System.out.println("VOY A TENER SUERTE");
                Kiosko kiosko = this.elegirKiosko();
                System.out.println("");
                kiosko.rentarSuerte(kiosko.manejarSuerte(this), (Cliente) usuario, this);
            }
        }while(opcion != 5);
    }
    
    public void administrador(Usuario usuario){
        int opcion = 0;
        do{
            System.out.println("");
            this.divisor();
            usuario.menu();
            System.out.print("\n¿Que desea hacer?: ");
            opcion = Greenbox.recibirEntero();
            System.out.println("");
            this.divisor();
            System.out.println("");
            if(opcion == 1){
                ((Administrador) usuario).reportePeliculasKiosko(this);
            }
            if(opcion == 2){
                ((Administrador) usuario).reporteRentasUrbanizacion(this);
            }
            if(opcion == 3){
                ((Administrador) usuario).reporteRentaPorFecha(this);
            }
            if(opcion == 4){
                ((Administrador) usuario).reportePelisMasRentadas(this);
            }
            if(opcion == 5){
                ((Administrador) usuario).reporteClienteMasRenta(this);
            }
            if(opcion == 6){
                ((Administrador) usuario).reporteCopiasRentadas(this);
            }
        }while(opcion != 7);
    }
}
