/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package greenbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;



/**
 *
 * @author CltControl
 */
public class Urbanizacion {
    private String nombre;
    private String direccion;
    private int numTotalResidentes;
    private Kiosko kiosko;

    public Urbanizacion(String nombre, String direccion, int numTotalResidentes) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.numTotalResidentes = numTotalResidentes;
        this.kiosko = new Kiosko();
    }


    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getDireccion() {
        return direccion;
    }
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
    public int getNumTotalResidentes() {
        return numTotalResidentes;
    }
    public void setNumTotalResidentes(int numTotalResidentes) {
        this.numTotalResidentes = numTotalResidentes;
    }
    public Kiosko getKiosko() {
        return kiosko;
    }
    public void setKiosko(Kiosko kiosko) {
        this.kiosko = kiosko;
    }

    @Override
    public String toString() {
        return nombre;
    }
    
    public static LinkedList<Urbanizacion> cargarUrbanizacion(){
        LinkedList<Urbanizacion> urbani= new LinkedList<>();
        File archivo = new File("src/archivos/urbanizaciones.txt");
        try {
            Scanner sc = new Scanner(archivo);
            sc.useDelimiter("\n");
            while(sc.hasNext()){
                String linea = sc.nextLine();
                if (linea.length()!=0){
                    String[] campos = linea.split("\\\n");
                    campos=campos[0].split("\\,");
                    Urbanizacion u = new Urbanizacion(campos[0], campos[1], Integer.parseInt(campos[2]));//trim elimina espacios
                    u.cargarKioskoEnUrbanizacion(campos[3]);
                    urbani.add(u);
                }
            }
            sc.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return urbani;
    }
    
    public void cargarKioskoEnUrbanizacion(String codKiosko){
        LinkedList<Kiosko> kioskos = Kiosko.cargarKioskos();
        for (Kiosko kiosko : kioskos){
            if(kiosko.getNumeroSerie().equalsIgnoreCase(codKiosko)){
                this.setKiosko(kiosko);
            }
        }
    }
    
    public static Urbanizacion nombreKioskoMasGrande(LinkedList<Urbanizacion> urbanizaciones){
        try{
            Urbanizacion mayor = urbanizaciones.getFirst();
            for(Urbanizacion urbanizacion: urbanizaciones){
                if(urbanizacion.getNombre().length() > mayor.getNombre().length())
                    mayor = urbanizacion;
            }
            return mayor;
            
        }catch(NullPointerException nE){
            return null;
            
        }catch(Exception E){
            return null;
        }
    }
}
