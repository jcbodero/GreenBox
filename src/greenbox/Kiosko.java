/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package greenbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import peliculas.TDAs.CopiaPelicula;
import peliculas.TDAs.Pelicula;
import usuarios.TDAs.Cliente;

/**
 *
 * @author CltControl
 */
public class Kiosko {
    private String numeroSerie;
    private String modeloEquipo;
    private String fabricanteEquipo;
    private LinkedList<CopiaPelicula>  copiasPelicula;

    public Kiosko() {
    }
    
    public Kiosko(String numeroSerie, String modeloEquipo, String fabricanteEquipo) {
        this.numeroSerie = numeroSerie;
        this.modeloEquipo = modeloEquipo;
        this.fabricanteEquipo = fabricanteEquipo;
        this.copiasPelicula = new LinkedList<>();
    }
    
    public String getNumeroSerie() {
        return numeroSerie;
    }
    
    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }
    
    public String getModeloEquipo() {
        return modeloEquipo;
    }
    
    public void setModeloEquipo(String modeloEquipo) {
        this.modeloEquipo = modeloEquipo;
    }
    
    public String getFabricanteEquipo() {
        return fabricanteEquipo;
    }
    
    public void setFabricanteEquipo(String fabricanteEquipo) {
        this.fabricanteEquipo = fabricanteEquipo;
    }
    
    public LinkedList<CopiaPelicula> getCopiasPelicula() {
        return copiasPelicula;
    }
    
    public void setCopiasPelicula(LinkedList<CopiaPelicula> copiasPelicula) {
        this.copiasPelicula = copiasPelicula;
    }

    @Override
    public String toString() {
        return numeroSerie;
    }
    
    public static LinkedList<Kiosko> cargarKioskos(){
        LinkedList<Kiosko> K1= new LinkedList<>();
        File archivo = new File("src/archivos/kioskos.txt");
	try (Scanner sc = new Scanner(archivo)) {
            sc.useDelimiter("\n");
            while(sc.hasNext()){
                String linea = sc.nextLine();
                if (linea.length()!=0){
                    String[] campos = linea.split("\\\n");
                    campos = campos[0].split("\\,");
                    Kiosko u = new Kiosko(campos[0],campos[1], campos[2]);
                    K1.add(u);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        for (Kiosko kiosko : K1){
            CopiaPelicula.cargarCopiasEnKiosko(kiosko);
        }
        return K1;
    }
    
    public Urbanizacion seEncuentraEn(Greenbox greenbox){
        for(Urbanizacion urb: greenbox.getUrbanizaciones()){
            if(urb.getKiosko().getNumeroSerie().equalsIgnoreCase(this.numeroSerie))
                return urb;
        }
        return null;
    }
    
    public boolean tieneLaCopia(CopiaPelicula copia){
        for(CopiaPelicula copi : this.getCopiasPelicula()){
            if(copi.getCodigoBarras().equals(copia.getCodigoBarras())){
                return true;
            }
        }
        return false;
    }
    
    public CopiaPelicula buscarCopia(String codigobarra){
        for(CopiaPelicula copia: this.copiasPelicula){
            if(copia.getCodigoBarras().equalsIgnoreCase(codigobarra))
                return copia;
        }
        return null;
    }
    
    public LinkedList<CopiaPelicula> copiasDisponibles(LinkedList<Renta> rentas){
        LinkedList<CopiaPelicula> copiasDisponibles = new LinkedList();
        for(CopiaPelicula copia: this.copiasPelicula){
            if(Renta.copiaDisponible(copia, rentas)){
                copiasDisponibles.add(copia);
            }
        }
        return copiasDisponibles;
    }
    
    public LinkedList<Pelicula> peliculasKiosko(LinkedList<Pelicula> peliculas){
        LinkedList<Pelicula> peliculasKiosko = new LinkedList<>();
        for(CopiaPelicula copia: this.copiasPelicula){
            Pelicula pelicula = copia.obtenerPelicula(peliculas);
            if(pelicula != null){
                if(peliculasKiosko.isEmpty()){
                    peliculasKiosko.addLast(pelicula);
                }else if(!peliculasKiosko.contains(pelicula)){
                    peliculasKiosko.addLast(pelicula);
                }
            }
        }
        return peliculasKiosko;
    }
    
    public LinkedList<Pelicula> peliculasDisponibles(LinkedList<Renta> rentas, LinkedList<Pelicula> peliculas){
        LinkedList<Pelicula> peliculasDisponibles = new LinkedList<>();
        for(CopiaPelicula copia: this.copiasDisponibles(rentas)){
            Pelicula pelicula = copia.obtenerPelicula(peliculas);
            if(pelicula != null){
                if(peliculasDisponibles.isEmpty()){
                    peliculasDisponibles.addLast(pelicula);
                }else if(!peliculasDisponibles.contains(pelicula)){
                    peliculasDisponibles.addLast(pelicula);
                }
            }
        }
        return peliculasDisponibles;
    }
    
    public void rentarPorGenero(Cliente cliente, Greenbox greenbox){
        greenbox.divisor();
        System.out.println("");
        System.out.println("Rentas por Genero");
        int opcion = 0;
        LinkedList<Pelicula> peliculasDisponibles = this.peliculasDisponibles(greenbox.getRentas(), greenbox.getPeliculas());
        LinkedList<String> generos = Pelicula.generosEncontrados(peliculasDisponibles);
        if(generos.isEmpty()){
                System.out.println("No existen peliculas disponibles en este kiosko.");
                return;
            }
        int cont = 1;
        for(String genero: generos){
            System.out.println(cont + ".-" + genero);
            cont++;
        }
        System.out.println(cont + ".-Regresar");
        System.out.print("¿Que genero desea consultar?: ");
        opcion = Greenbox.recibirEntero();
        if(opcion != generos.size() + 1){
            int opcion2 = 0;
            LinkedList<Pelicula> peliculasGenero;
            System.out.println("\nBUSQUEDA DEL GENERO: " + generos.get(opcion-1));
            System.out.println("");
            peliculasGenero = Pelicula.peliculasDelGenero(generos.get(opcion-1),peliculasDisponibles);
            this.rentar(cliente, greenbox, peliculasGenero);
        }
    }
    
    public void rentar(Cliente cliente, Greenbox greenbox, LinkedList<Pelicula> peliculas){
        int opcion;
        greenbox.divisor();
        System.out.println("");
        Pelicula.listarPeliculas(peliculas);
        System.out.println(peliculas.size() + 1 + ".- Regresar.");
        System.out.print("¿De cual pelicula desea ver el detalle?: ");
        opcion = Greenbox.recibirEntero();

        if(opcion < peliculas.size() + 1  && opcion >= 1){
            System.out.println("");
            peliculas.get(opcion - 1).detallePelicula();
            System.out.println("");
            int opcion2 = (int) this.elegirTipoDeCopia(peliculas.get(opcion - 1), greenbox.getRentas(), true);

            if(opcion2 == 1){
                System.out.println("¿Cuantos dias rentara la pelicula?");
                int dias;
                do{
                    dias = Greenbox.recibirEntero();
                }while(dias <= 0);
                LinkedList<CopiaPelicula> copiasR = new LinkedList<>();
                CopiaPelicula c = CopiaPelicula.obtenerPrimerDVD(this.copiasDePeliculaDisponibles(peliculas.get(opcion - 1), greenbox.getRentas()));
                copiasR.add(c);
                this.rentar(copiasR, cliente, greenbox.getRentas(), dias);
                System.out.println("Renta Realizada");
            }

            if(opcion2 == 2){
                System.out.println("¿Cuantos dias rentara la pelicula?");
                int dias;
                do{
                    dias = Greenbox.recibirEntero();
                }while(dias <= 0);
                LinkedList<CopiaPelicula> copiasR = new LinkedList<>();
                CopiaPelicula c = CopiaPelicula.obtenerPrimerBluRay(this.copiasDePeliculaDisponibles(peliculas.get(opcion - 1), greenbox.getRentas()));
                copiasR.add(c);
                this.rentar(copiasR, cliente, greenbox.getRentas(), dias);
                System.out.println("Renta Realizada");
            }
        }
    }
    
    public LinkedList<CopiaPelicula> copiasDePeliculaDisponibles(Pelicula pelicula, LinkedList<Renta> rentas){
        LinkedList<CopiaPelicula> cD = new LinkedList<>();
        for(CopiaPelicula copia: this.copiasPelicula){
            if(Renta.copiaDisponible(copia, rentas) && copia.getCodigoPelicula().equals(pelicula.getCodigo())){
                cD.add(copia);
            }
        }
        return cD;
    }
    
    public int elegirTipoDeCopia(Pelicula pelicula, LinkedList<Renta> rentas, boolean opcionDeNoRentar){
        boolean[] flag = {false,false};
        int cont = 1, opcion = 0;
        for(CopiaPelicula copia: this.copiasDePeliculaDisponibles(pelicula, rentas)){
            if(copia.getTipo() == 1)
                flag[0] = true;
            if(copia.getTipo() == 2)
                flag[1] = true;
        }
        if(flag[0]){
            System.out.println(cont +".- Rentar DVD");
            cont++;
        }
        if(flag[1]){
            System.out.println(cont +".- Rentar BLU-RAY");
            cont++;
        }
        if(opcionDeNoRentar)
            System.out.println(cont +".- No Rentar");
        else
            cont--;
        do{
            System.out.print("\n¿Que desea hacer?: ");
            opcion = Greenbox.recibirEntero();
        }while(opcion > cont || opcion < 1);
        if(opcion == cont && opcionDeNoRentar)
            return -1;
        cont = 0;
        for(int i=0; i< 2; i++){
            if(flag[i] == true){
                cont ++;
            }
            if(cont == opcion){
                return i+1;
            }
        }
        System.out.println("*" + opcion);
        return opcion;
    }
    
    public void rentar(LinkedList<CopiaPelicula> copias, Cliente cliente, LinkedList<Renta> rentas, int dias){
        for(CopiaPelicula copia: copias){
            try {
                cliente.rentar(copia, rentas, dias);
            } catch (IOException ex) {
                Logger.getLogger(Kiosko.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void rentarSuerte(LinkedList<Pelicula> peliculas, Cliente cliente, Greenbox greenbox){
        if(peliculas.isEmpty()){
            System.out.println("No rento pelicula Alguna.");
            return;
        }
        
        for(Pelicula pelicula: peliculas){
            System.out.println("Pelicula: " + pelicula.getTitulo());
            System.out.println("");
            int opcion = (int) this.elegirTipoDeCopia(pelicula, greenbox.getRentas(), false);

            if(opcion == 1){
                System.out.println("¿Cuantos dias rentara la pelicula?");
                int dias;
                do{
                    dias = Greenbox.recibirEntero();
                }while(dias <= 0);
                LinkedList<CopiaPelicula> copiasR = new LinkedList<>();
                CopiaPelicula c = CopiaPelicula.obtenerPrimerDVD(this.copiasDePeliculaDisponibles(pelicula, greenbox.getRentas()));
                copiasR.add(c);
                this.rentar(copiasR, cliente, greenbox.getRentas(), dias);
                System.out.println("Renta Realizada");
            }

            if(opcion == 2){
                System.out.println("¿Cuantos dias rentara la pelicula?");
                int dias;
                do{
                    dias = Greenbox.recibirEntero();
                }while(dias <= 0);
                LinkedList<CopiaPelicula> copiasR = new LinkedList<>();
                CopiaPelicula c = CopiaPelicula.obtenerPrimerBluRay(this.copiasDePeliculaDisponibles(pelicula, greenbox.getRentas()));
                copiasR.add(c);
                this.rentar(copiasR, cliente, greenbox.getRentas(), dias);
                System.out.println("Renta Realizada");
            }
        }
    }
    
    public void rentarPorNombre(Cliente cliente, Greenbox greenbox){
        System.out.println("");
        System.out.println("Rentas por Nombre\n");
        System.out.println(cliente.getNombres() + "Ingrese el nombre de las peliculas a buscar: ");
        LinkedList<Pelicula> peliculasFiltradasPorNombre = 
                Pelicula.peliculasFiltradasPorNombre(new Scanner(System.in).nextLine(), 
                        this.peliculasDisponibles(greenbox.getRentas(), greenbox.getPeliculas()));
        
        System.out.println("");
        this.rentar(cliente, greenbox, peliculasFiltradasPorNombre);
    }
    
    public LinkedList<Pelicula> obtenerSuerte(Greenbox greenbox){
        Random r = new Random();
        LinkedList<Pelicula> peliculasDisponibles = this.peliculasDisponibles(greenbox.getRentas(), greenbox.getPeliculas());
        ArrayList<Integer> aleatorios =  new ArrayList<>();
        LinkedList<Pelicula> peliculasAleatorias = new LinkedList<>();
        while(aleatorios.size() < 5 && aleatorios.size() < peliculasDisponibles.size())
        {
            int aux = r.nextInt(peliculasDisponibles.size());
            if(!aleatorios.contains(Integer.valueOf(aux))){
                aleatorios.add(Integer.valueOf(aux));
            }
        }
        for(Integer aleatorio: aleatorios){
            peliculasAleatorias.add(peliculasDisponibles.get((int)aleatorio));
        }
        return peliculasAleatorias;
    }
        
    public LinkedList<Pelicula> manejarSuerte(Greenbox greenbox){
        LinkedList<Pelicula> peliculasAleatorias = this.obtenerSuerte(greenbox);
        int opcion = 0;
        int indice = 0;
        while(opcion != 4 && !peliculasAleatorias.isEmpty()){
            System.out.println("");
            peliculasAleatorias.get(indice).detallePelicula();
            do{
                System.out.println("");
                System.out.println("1.- Anterior.");
                System.out.println("2.- Siguiente.");
                System.out.println("3.- Eliminar de la lista.");
                System.out.println("4.- Salir(Rentar todas las peliculas que quedan en la lista).");
                System.out.println("");
                System.out.print("¿Que desea hacer?: ");
                opcion = Greenbox.recibirEntero();
            }while(opcion < 1 || opcion > 4);
            
            switch (opcion) {
                case 1:
                    indice--;
                    if(indice < 0)
                        indice = peliculasAleatorias.size() - 1;
                    break;
                case 2:
                    indice++;
                    if(indice > peliculasAleatorias.size() - 1)
                        indice = 0;
                    break;
                case 3:
                    peliculasAleatorias.remove(indice);
                    indice++;
                    if(indice > peliculasAleatorias.size() - 1)
                        indice = 0;
                    break;
                default:
                    System.out.println("/nUsted decidio quedarse con las peliculas:");
                    int opcion2 = 0;
                    for(Pelicula pelicula: peliculasAleatorias){
                        System.out.println(" + " + pelicula.getTitulo());
                       
                    }
                    
                    do{
                        System.out.println("\n¿Que desea hacer?:");
                        System.out.println("   1.- Rentar todas las peliculas.");
                        System.out.println("   2.- Editar eleccion.");
                        System.out.println("   3.- No rentar pelicula alguna.");
                        opcion2 = Greenbox.recibirEntero();
                        }while(opcion2 < 1 || opcion2 > 3);
                        if(opcion2 == 1){
                            return peliculasAleatorias;
                        }else if(opcion2 == 2){
                            opcion = 0;
                        }else if(opcion2 == 3){
                            return new LinkedList<>();
                    }
                    break;
            }
            
        }
        return peliculasAleatorias;
    }
    
    public static void main(String args[]){
        Greenbox greenbox = new Greenbox();
        greenbox.getUrbanizaciones().getFirst().getKiosko().manejarSuerte(greenbox);
    }
    
    
}
