	/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peliculas.TDAs;

import greenbox.Greenbox;
import greenbox.Kiosko;
import greenbox.Renta;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Date;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author CltControl
 */
public class CopiaPelicula {
    private String codigoBarras;
    private int tipo;
    private String fechaFabricacion;
    private String codigoPelicula;

    public CopiaPelicula(String codigoBarras, int tipo, String fechaFabricacion, String codigoPelicula) {
        this.codigoBarras = codigoBarras;
        this.tipo = tipo;
        this.fechaFabricacion = fechaFabricacion;
        this.codigoPelicula = codigoPelicula;
    }
  
    public String getCodigoBarras() {
        return codigoBarras;
    }
    
    public void setCodigoBarras(String codigoBarras) {
        this.codigoBarras = codigoBarras;
    }
    
    public int getTipo() {
        return tipo;
    }
    
    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
    
    public String getFechaFabricacion() {
        return fechaFabricacion;
    }
    
    public void setFechaFabricacion(String fechaFabricacion) {
        this.fechaFabricacion = fechaFabricacion;
    }
    
    public String getCodigoPelicula() {
        return codigoPelicula;
    }
    
    public void setCodigoPelicula(String pelicula) {
        this.codigoPelicula = pelicula;
    }

    @Override
    public String toString(){
        return this.codigoBarras;
    }
    
    public static void cargarCopiasEnKiosko(Kiosko kiosko){
        LinkedList<CopiaPelicula> copias= new LinkedList<>();
        File archivo = new File("src/archivos/copiasPeliculas.txt");
        try {
            Scanner sc = new Scanner(archivo);
            sc.useDelimiter("\n");
            while(sc.hasNext()){
                String linea = sc.nextLine();
                if (linea.length()!=0){
                    String[] campos = linea.split("\\\n");
                    campos=campos[0].split("\\,");
                    if(kiosko.getNumeroSerie().equals(campos[4])){
                        CopiaPelicula copia = new CopiaPelicula(campos[1], Integer.parseInt(campos[2]),campos[3], campos[0]);//trim elimina espacios
                        copias.add(copia);
                    }
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        kiosko.setCopiasPelicula((LinkedList<CopiaPelicula>) copias.clone());
    }
    
    
    public static LinkedList<CopiaPelicula> cargarCopias(){
        LinkedList<CopiaPelicula> copias= new LinkedList<>();
        File archivo = new File("src/archivos/copiasPeliculas.txt");
        try {
            Scanner sc = new Scanner(archivo);
            sc.useDelimiter("\n");
            while(sc.hasNext()){
                String linea = sc.nextLine();
                if (linea.length()!=0){
                    String[] campos = linea.split("\\\n");
                    campos=campos[0].split("\\,");
                    CopiaPelicula copia = new CopiaPelicula(campos[1], Integer.parseInt(campos[2]),campos[3], campos[0]);//trim elimina espacios
                    copias.add(copia);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return copias;
    }
    
    public Pelicula obtenerPelicula(LinkedList<Pelicula> peliculas){
        for(Pelicula pelicula: peliculas){
            if(pelicula.getCodigo().equals(this.codigoPelicula)){
                return pelicula;
            }
        }
        return null;
    }
    
    public static CopiaPelicula obtenerCopiaPelicula(LinkedList<CopiaPelicula> copias, String codigo){
        for(CopiaPelicula copia: copias){
            if(copia.getCodigoBarras().equals(codigo)){
                return copia;
            }
        }
        return null;
    }
    
    public static CopiaPelicula obtenerPrimerDVD(LinkedList<CopiaPelicula> copias){
        for(CopiaPelicula copia: copias){
            if(copia.getTipo() == 1){
                return copia;
            }
        }
        return null;
    }
    
    public static CopiaPelicula obtenerPrimerBluRay(LinkedList<CopiaPelicula> copias){
        for(CopiaPelicula copia: copias){
            if(copia.getTipo() == 2){
                return copia;
            }
        }
        return null;
    }
    
    public static int numCopiasDePelicula(LinkedList<CopiaPelicula> copias, Pelicula pelicula){
        int cont = 0;
        for(CopiaPelicula copia: copias){
            if(copia.getCodigoPelicula().equals(pelicula.getCodigo())){
                cont++;
            }
                    
        }
        return cont;
    }
    
    
}
