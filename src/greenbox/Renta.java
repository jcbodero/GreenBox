/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package greenbox;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Instant;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import peliculas.TDAs.CopiaPelicula;
import peliculas.TDAs.Pelicula;
import usuarios.TDAs.Cliente;
import usuarios.TDAs.Usuario;

/**
 *
 * @author CltControl
 */
public class Renta {
    private String codigoRenta;
    private String codigoBarras;
    private String cedulaCliente;
    private Date clienteInicio;
    private Date clienteEntrega;
    private int estadoDeRenta;

    public Renta(String codigoRenta, String codigoBarras, String cedulaCliente, Date clienteInicio, Date clienteEntrega, int estadoDeRenta) {
        this.codigoRenta = codigoRenta;
        this.codigoBarras = codigoBarras;
        this.cedulaCliente = cedulaCliente;
        this.clienteInicio = clienteInicio;
        this.clienteEntrega = clienteEntrega;
        this.estadoDeRenta = estadoDeRenta;
    }
    
    public String getCodigoRenta() {
        return codigoRenta;
    }
    
    public void setCodigoRenta(String codigoRenta) {
        this.codigoRenta = codigoRenta;
    }
    
    public String getCodigoBarras() {
        return codigoBarras;
    }
    
    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }
    
    public String getCedulaCliente() {
        return cedulaCliente;
    }
    
    public void setCedulaCliente(String cedulaCliente) {
        this.cedulaCliente = cedulaCliente;
    }
    
    public Date getClienteInicio() {
        return clienteInicio;
    }
    
    public void setClienteInicio(Date clienteInicio) {
        this.clienteInicio = clienteInicio;
    }
    
    public Date getClienteEntrega() {
        return clienteEntrega;
    }
    
    public void setClienteEntrega(Date clienteEntrega) {
        this.clienteEntrega = clienteEntrega;
    }
    
    public int getEstadoDeRenta() {
        return estadoDeRenta;
    }
    
    public void setEstadoDeRenta(int estadoDeRenta) {
        this.estadoDeRenta = estadoDeRenta;
    }
    
    public void imprimirRenta(Greenbox greenbox){
        Kiosko kiosko = this.obtenerKiosko(greenbox);
        Pelicula p = kiosko.buscarCopia(this.codigoBarras).obtenerPelicula(greenbox.getPeliculas());
        System.out.print("Renta " + this.getCodigoRenta());
        System.out.print(" de la pelicula " + p.getTitulo());
        System.out.print(" realizada por " + Cliente.buscarCliente(this.cedulaCliente, greenbox.getUsuarios()));
        String fechaI = this.clienteInicio.getDate() + "/" + this.clienteInicio.getMonth() + "/" + this.clienteInicio.getYear();
        String fechaF = this.clienteEntrega.getDate() + "/" + this.clienteEntrega.getMonth() + "/" + this.clienteEntrega.getYear();
        System.out.print(" desde el " + fechaI + " hasta el " + fechaF);
        System.out.println(" en la urbanizacion " +  this.obtenerKiosko(greenbox).seEncuentraEn(greenbox));
    }
    
    public static LinkedList<Renta> cargarRentas(){
        LinkedList<Renta> rentas = new LinkedList<>();
        File archivo = new File("src/archivos/rentas.txt");
        try {
            if(archivo.exists()){
                Scanner sc = new Scanner(archivo);
                sc.useDelimiter("\n");
                while(sc.hasNext()){
                    Calendar c1 = new GregorianCalendar();
                    String linea = sc.nextLine();
                    String[] campos = linea.split("\\\n");
                    campos = campos[0].split("\\,");
                    Date fechaI;
                    Date fechaF;
                    try{
                        fechaI = new Date(Date.parse(campos[3]));
                        fechaF = new Date(Date.parse(campos[4]));
                    }catch(Exception e){
                        fechaI = c1.getTime();
                        fechaF = c1.getTime();
                    }
                    Renta renta = new Renta(campos[0], campos[1], campos[2], fechaI, fechaF, Integer.parseInt(campos[5]));
                    rentas.add(renta);
                }
                sc.close();   
            }
            else{
                System.out.println("No existe");
            }
            
        }catch(FileNotFoundException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Se cayo" + ex.getMessage());
        }
        return rentas;
    }
    
    public static LinkedList<Renta> filtrarPorFecha(Date fechaI, Date fechaF, LinkedList<Renta> rentas){
        LinkedList<Renta> rentasFecha = new LinkedList<>();
        for(Renta renta: rentas){
            if(renta.clienteInicio.equals(fechaI) || renta.clienteInicio.equals(fechaF) ||
               (renta.clienteInicio.after(fechaI) && renta.clienteInicio.before(fechaF))){
                rentasFecha.add(renta);
            }
        }
        return rentasFecha;
    }
            
    public static boolean copiaDisponible(CopiaPelicula copia, LinkedList<Renta> rentas){
        for(Renta renta: rentas){
            if(renta.getCodigoBarras().equals(copia.getCodigoBarras()) && renta.getEstadoDeRenta() != 2){
                return false;
            }
        }
        return true;
    }
    
    public static String generarCodigo(LinkedList<Renta> rentas){
        int mayor = 0;
        for(Renta renta: rentas){
            try{
                if(Integer.parseInt(renta.getCodigoRenta().substring(1)) > mayor){
                    mayor = Integer.parseInt(renta.getCodigoRenta().substring(1));
                }
            }catch(Exception ex){
                continue;
            }
        }
        return "R" + String.valueOf(mayor +1);
    }
    
    public Kiosko obtenerKiosko(Greenbox greenbox){
        for(Urbanizacion urb: greenbox.getUrbanizaciones()){
            CopiaPelicula copia = CopiaPelicula.obtenerCopiaPelicula(urb.getKiosko().getCopiasPelicula(), this.codigoBarras);
            if(copia != null){
                if(urb.getKiosko().tieneLaCopia(copia)){
                    return urb.getKiosko();
                }
            }
        }
        return null;
    }
    
    public void registrarRenta() throws IOException{
        String linea = this.codigoRenta + "," + this.codigoBarras + "," + this.cedulaCliente + ",";
        linea = linea + this.clienteInicio.getDate()+ "/" + this.clienteInicio.getMonth() + "/" + this.clienteInicio.getYear();
        linea = linea + "," + this.clienteEntrega.getDate() + "/" + this.clienteEntrega.getMonth() + "/" + this.clienteEntrega.getYear();
        linea = linea + "," + this.estadoDeRenta;
        File f;
        f = new File("src/archivos/rentas.txt");
        BufferedWriter bw;
        if(f.exists()) {
            bw = new BufferedWriter(new FileWriter(f, true));
            System.out.println("");
            bw.append("\n" + linea);
        } else {
            bw = new BufferedWriter(new FileWriter(f));
            bw.write(linea);
        }
        bw.close();
    }
    
    public static void rentar(LinkedList<Renta> rentas, CopiaPelicula copia, String cedulaCliente, int dias) throws IOException{
        Calendar c1 = new GregorianCalendar();
        Date fechaI = c1.getTime();
        fechaI.setYear(c1.get(Calendar.YEAR));
        c1.add(Calendar.DAY_OF_MONTH, dias);
        Date fechaF = c1.getTime();
        fechaF.setYear(c1.get(Calendar.YEAR));
        try{
        Renta r = new Renta(Renta.generarCodigo(rentas), copia.getCodigoBarras(), cedulaCliente, fechaI, fechaF, 1);
        rentas.add(r);
        r.registrarRenta();
        }catch(NullPointerException nx){
            System.out.println("No rento");
        }
    }
    
    public static LinkedList<Object[]> numRentasDePeliculasEnUrb(LinkedList<Renta> rentas, Urbanizacion urb, Greenbox greenbox){
        LinkedList<Object[]> numRentas = new LinkedList<>();
        LinkedList<Object[]> auxP = new LinkedList<>();
        for(Renta renta: rentas){
            boolean flag = true;
            CopiaPelicula cop = CopiaPelicula.obtenerCopiaPelicula(urb.getKiosko().getCopiasPelicula(), renta.getCodigoBarras());
            if(cop != null){
                Pelicula p = Pelicula.buscarPelicula(greenbox.getPeliculas(), cop.getCodigoPelicula());
                for(Object[] numRenta: numRentas){
                    if(((Pelicula)numRenta[0]).getCodigo().equals(p.getCodigo())){
                        numRenta[1] = Integer.valueOf(((int)numRenta[1]) + 1);
                        flag = false;
                        break;
                    }
                }
                if(flag){
                    Object[] aux = {p, Integer.valueOf(1)};
                    numRentas.add(aux);
                }
            }
        }
        
        for(Pelicula peli: urb.getKiosko().peliculasKiosko(greenbox.getPeliculas())){
            boolean flag = true;
            for(Object[] numRenta: numRentas){
                if(peli.getCodigo().equals(((Pelicula)numRenta[0]).getCodigo())){
                    auxP.add(numRenta);
                    flag = false;
                    break;
                }
            }
            if(flag){
                Object[] aux = {peli, Integer.valueOf(0)};
                auxP.add(aux);
            }
        }
        return auxP;
    }
    
    public static LinkedList<Object[]> numRentasPorClienteEnUrb(LinkedList<Renta> rentas, Urbanizacion urb, Greenbox greenbox){
        LinkedList<Object[]> numRentas = new LinkedList<>();
        LinkedList<Object[]> auxC = new LinkedList<>();
        for(Renta renta: rentas){
            boolean flag = true;
            if(renta.obtenerKiosko(greenbox).seEncuentraEn(greenbox).getNombre().equalsIgnoreCase(urb.getNombre())){
                Cliente c = Cliente.buscarCliente(renta.getCedulaCliente(), greenbox.getUsuarios());
                if(c != null){
                    for(Object[] numRenta: numRentas){
                        if(((Cliente)numRenta[0]).getNumeroCedula().equals(c.getNumeroCedula())){
                            numRenta[1] = Integer.valueOf(((int)numRenta[1]) + 1);
                            flag = false;
                            break;
                        }
                    }
                    if(flag){
                        Object[] aux = {c, Integer.valueOf(1)};
                        numRentas.add(aux);
                    }
                }
            }
        }
        return numRentas;
    }
    
    public static Object[] masRentadaUrbanizacion(LinkedList<Renta> rentas, Urbanizacion urbanizacion, Greenbox greenbox){ 
        LinkedList<Object[]> numRentas = Renta.numRentasDePeliculasEnUrb(rentas, urbanizacion, greenbox);
        Object[] pMayor = {null, 0};
        for(Object[] numRenta: numRentas){
            if((int) numRenta[1] > (int) pMayor[1]){
                pMayor = numRenta;
            }
        }
        return pMayor;
    }
    
    public static Renta ultimaRentaDeCopia(LinkedList<Renta> rentas, String codigoBarra){
        Renta ultRent = null;
        for(Renta renta: rentas){
            if(renta.getCodigoBarras().equals(codigoBarra) && renta.getEstadoDeRenta() == 1){
                ultRent = renta;
            }
        }
        return ultRent;
    }
    
    public static void cambiarRenta(LinkedList<Renta> rentas, Renta renta, int estado){
        for(Renta rentax: rentas){
            if(rentax.getCodigoRenta().equals(renta.getCodigoRenta()) && rentax.getEstadoDeRenta() == 1){
                rentax.setEstadoDeRenta(estado);
            }
        }
    }
    
    public static void guardarRentas(LinkedList<Renta> rentas){
    	FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("src/archivos/rentas.txt");
            pw = new PrintWriter(fichero);
            boolean aux = false;
            for (Renta rent : rentas){
                if(aux)
                    pw.println();
                aux = true;
                pw.print(rent.getCodigoRenta()+","+rent.getCodigoBarras()+","+rent.getCedulaCliente()+","
                            +rent.getClienteInicio().getDate()+"/"+rent.getClienteInicio().getMonth()+"/" + rent.getClienteInicio().getYear()+","
                            +rent.getClienteEntrega().getDate()+"/"+rent.getClienteEntrega().getMonth()+"/" + rent.getClienteEntrega().getYear()+","
                            +rent.getEstadoDeRenta());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
    
    public static void main(String args[]){
        LinkedList<Renta> r = Renta.cargarRentas();
        LinkedList<Urbanizacion> urb = Urbanizacion.cargarUrbanizacion();
        LinkedList<Pelicula> pelis = Pelicula.cargarPeliculas();
        System.out.println(Arrays.toString(pelis.toArray()));
        Urbanizacion urbanizacion = urb.get(2);
        LinkedList<Object[]> aux = Renta.numRentasDePeliculasEnUrb(r, urbanizacion, new Greenbox());
        LinkedList<Object[]> aux2 = Renta.numRentasPorClienteEnUrb(r, urbanizacion, new Greenbox());
        System.out.println(aux2.size());
        for(Object[] i: aux){
            System.out.println(((Pelicula)i[0]).getTitulo() + " ------> " + (int)i[1]);
        }
        
        for(Object[] i: aux2){
            System.out.println((Cliente)i[0] + " ------> " + (int)i[1]);
        }
        Object[] pMayor = Renta.masRentadaUrbanizacion(r, urbanizacion, new Greenbox());
        
        if(pMayor[0] == null){
            System.out.println("\nNo existen rentas la urbanizacion " + urbanizacion);
        }else{
            System.out.println("\nLa pelicula mas rentada en la Ubanizacion " + urbanizacion+" es: " + ((Pelicula)pMayor[0]).getTitulo());
            System.out.println("La cual ha sido rentada " + (int)pMayor[1] + " veces.");
        }
        
    }
    
}
