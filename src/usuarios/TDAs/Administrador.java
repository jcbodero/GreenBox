/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuarios.TDAs;

import greenbox.Greenbox;
import greenbox.Kiosko;
import greenbox.Renta;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import peliculas.TDAs.CopiaPelicula;
import peliculas.TDAs.Pelicula;

/**
 *
 * @author CltControl
 */
public class Administrador extends Usuario{
    
    public Administrador(String nombreUsuario, String clave) {
        super(nombreUsuario, clave, 2);
    }
    public String toString(){
        return this.getNombreUsuario();
    }

    @Override
    public void menu() {
        System.out.println("");
        
        System.out.print((char)27 + "[31;49m");
        System.out.println("* * * * * * * * * * * * * * * * * * *");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("             REPORTES              ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("                                   ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("  1.- Peliculas en kiosko          ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("  2.- Rentas en urbanizacion       ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("  3.- Rentas por fecha             ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("  4.- Pelicula mas rentada         ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("  5.- Cliente mas rentas           ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("  6.- Copias Rentadas              ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("  7.- Salir                        ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m"+"*");
        System.out.print((char)27 + "[34;49m");
        System.out.print("                                   ");
        System.out.println((char)27 + "[31;49m"+"*");
        
        System.out.print((char)27 + "[31;49m");
        System.out.println("* * * * * * * * * * * * * * * * * * *");
    }
    
    public void reportePeliculasKiosko(Greenbox greenbox){
        System.out.println("\nREPORTE PELICULAS EN KIOSKO");
        Kiosko kiosko = greenbox.elegirKiosko();
        LinkedList<Pelicula> peliculas = kiosko.peliculasKiosko(greenbox.getPeliculas());
        System.out.println("");
        greenbox.divisor();
        System.out.println("");
        System.out.println("PELICULAS EN EL KIOSKO " + kiosko.getNumeroSerie() 
                            + " UBICADO EN LA URB " + kiosko.seEncuentraEn(greenbox).getNombre());
        for(Pelicula pelicula: peliculas){
            System.out.println("\n + " + pelicula.getTitulo() + " ----> "
                                + CopiaPelicula.numCopiasDePelicula(kiosko.getCopiasPelicula(), pelicula) + " copias Registradas" 
                                + "----> Disponibles: "
                                + CopiaPelicula.numCopiasDePelicula(kiosko.copiasDisponibles(greenbox.getRentas()), pelicula) + " copias" );
        }
        System.out.print("\nPresione enter para continuar");
        String pausa = (new Scanner(System.in)).nextLine();
    }
    
    public void reporteRentasUrbanizacion(Greenbox greenbox){
        System.out.println("\nREPORTE RENTAS EN KIOSKO");
        Kiosko kiosko = greenbox.elegirKiosko();
        LinkedList<Renta> rentas = new LinkedList<>();
        for(Renta renta: greenbox.getRentas()){
            if(kiosko.getNumeroSerie().equals(renta.obtenerKiosko(greenbox).getNumeroSerie())){
                rentas.add(renta);
            }
        }
        
        System.out.println("");
        greenbox.divisor();
        System.out.println("");
        System.out.println("RENTAS EN LA URBANIZACION " + kiosko.seEncuentraEn(greenbox).getNombre());
        if(rentas.isEmpty()){
            System.out.println("\n No existen rentan en esta urbanizacion");
        }
        for(Renta renta: rentas){
            renta.imprimirRenta(greenbox);
        }
        System.out.print("\nPresione enter para continuar");
        String pausa = (new Scanner(System.in)).nextLine();
    }
    
    public void reportePelisMasRentadas(Greenbox greenbox){
        System.out.println("\nREPORTE PELICULA MAS RENTADA");
        Kiosko kiosko = greenbox.elegirKiosko();
        LinkedList<Pelicula> topPelis = new LinkedList<>();
        LinkedList<Object[]> numRentasDePeliculas = Renta.numRentasDePeliculasEnUrb(greenbox.getRentas(), 
                                         kiosko.seEncuentraEn(greenbox), greenbox);
        int aux = 0;
        for(Object[] numRentasDePelicula: numRentasDePeliculas){
            if((int)numRentasDePelicula[1] > aux){
                topPelis.clear();
                topPelis.add((Pelicula)numRentasDePelicula[0]);
                aux = (int)numRentasDePelicula[1];
            }
            if((int)numRentasDePelicula[1] == aux){
                topPelis.add((Pelicula)numRentasDePelicula[0]);
            }
        }
        if(topPelis.isEmpty()){
            System.out.println("No hay rentas en esta urbanizacion por lo tanto no hay Peliculas mas rentadas");
        }
        
        for(Pelicula pelicula: topPelis){
            try{
                System.out.println(" + " + pelicula.getTitulo() + " rentada " + aux + " veces.");
            }catch(NullPointerException nx){
                continue;
            }
        }
        System.out.print("\nPresione enter para continuar");
        String pausa = (new Scanner(System.in)).nextLine();
    }
    
    public void reporteClienteMasRenta(Greenbox greenbox){
        System.out.println("\nREPORTE CLIENTE MAS FRECUENTE");
        Kiosko kiosko = greenbox.elegirKiosko();
        LinkedList<Cliente> topClientes = new LinkedList<>();
        LinkedList<Object[]> numRentasPorCliente = Renta.numRentasPorClienteEnUrb(greenbox.getRentas(), 
                                         kiosko.seEncuentraEn(greenbox), greenbox);
        int aux = 0;
        for(Object[] numRentasDeCliente: numRentasPorCliente){
            if((int)numRentasDeCliente[1] > aux){
                topClientes.clear();
                topClientes.add((Cliente)numRentasDeCliente[0]);
                aux = (int)numRentasDeCliente[1];
            }
            else if((int)numRentasDeCliente[1] == aux){
                topClientes.add((Cliente)numRentasDeCliente[0]);
            }
        }
        
        if(topClientes.isEmpty()){
            System.out.println("No hay rentas en esta urbanizacion por lo tanto no hay Clientes con mas rentas");
            
        }
        for(Cliente cliente: topClientes){
            try{
                System.out.println(" + " + cliente + " rento " + aux + " veces.");
            }catch(NullPointerException nx){
                continue;
            }
        }
        
        System.out.print("\nPresione enter para continuar");
        String pausa = (new Scanner(System.in)).nextLine();
    }
    
    public void reporteRentaPorFecha(Greenbox greenbox){
        System.out.println("\nREPORTE RENTAS ENTRE FECHAS");
        System.out.print("\nIngrese la primera fecha en el formato(dia/mes/año): ");
        Date fechaI = Greenbox.recibirFecha();
        System.out.print("\nIngrese la segunda fecha en el formato(dia/mes/año): ");
        Date fechaF = Greenbox.recibirFecha();
        LinkedList<Renta> rentasEntreFechas = Renta.filtrarPorFecha(fechaI, fechaF, greenbox.getRentas());
        for(Renta renta: rentasEntreFechas){
            renta.imprimirRenta(greenbox);
        }
        System.out.print("\nPresione enter para continuar");
        String pausa = (new Scanner(System.in)).nextLine();
        
    }
    
    public void reporteCopiasRentadas(Greenbox greenbox){
        System.out.println("REPORTE DE COPIAS RENTADAS POR PELICULA\n");
        Pelicula.listarPeliculas(greenbox.getPeliculas());
        int opcion = 0;
        do{
            System.out.print("\n ¿Que pelicula desea consultar?: ");
            opcion = Greenbox.recibirEntero();
        }while(opcion < 1 || opcion > greenbox.getPeliculas().size());
        Pelicula pelicula = greenbox.getPeliculas().get(opcion-1);
        System.out.println("La pelicula " + pelicula.getTitulo() + " tiene " 
                            + pelicula.numRentas(greenbox.getRentas(), greenbox)
                            + " copias rentadas de " 
                            + CopiaPelicula.numCopiasDePelicula(CopiaPelicula.cargarCopias(), pelicula)
                            + " copias existentes en GREENBOX");
        
        System.out.print("\nPresione enter para continuar");
        String pausa = (new Scanner(System.in)).nextLine();
    }
}
