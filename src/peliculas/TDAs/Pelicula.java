/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package peliculas.TDAs;

import greenbox.Greenbox;
import greenbox.Renta;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author novicompu
 */
public class Pelicula {
    private String codigo; 
    private String titulo;
    private int año;
    private String genero;
    private String sinopsis;
    private String restriccion_edad;
    private String duración;
    private int costo_alquiler;
    private String director;
    private LinkedList<String> listaActores;
   
    public Pelicula(String codigo, String titulo, int año, String genero, String sinopsis, String director, LinkedList<String> listaActores,String restriccion,String duración, int costo_alquiler ) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.año = año;
        this.genero = genero;
        this.sinopsis = sinopsis;
        this.restriccion_edad = restriccion;
        this.duración = duración;
        this.costo_alquiler = costo_alquiler;
        this.director = director;
        this.listaActores = listaActores;
    }

    public String getCodigo() {
        return codigo;
    }
    
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public int getAño() {
        return año;
    }
    
    public void setAño(int año) {
        this.año = año;
    }
    
    public String getGenero() {
        return genero;
    }
    
    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public String getSinopsis() {
        return sinopsis;
    }
    
    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }
    
    public String getRestriccion_edad() {
        return restriccion_edad;
    }
    
    public void setRestriccion_edad(String restriccion_edad) {
        this.restriccion_edad = restriccion_edad;
    }
    
    public String getDuración() {
        return duración;
    }
    
    public void setDuración(String duración) {
        this.duración = duración;
    }
    
    public int getCosto_alquiler() {
        return costo_alquiler;
    }
    
    public void setCosto_alquiler(int costo_alquiler) {
        this.costo_alquiler = costo_alquiler;
    }
    
    public String getDirector() {
        return director;
    }
    
    public void setDirector(String director) {
        this.director = director;
    }
    
    public LinkedList getListaActores() {
        return listaActores;
    }
    
    public void setListaActores(LinkedList listaActores) {
        this.listaActores = listaActores;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pelicula other = (Pelicula) obj;
        if (!Objects.equals(this.codigo, other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.codigo);
        return hash;
    }
   
    public boolean equals(Pelicula peli) {
        if(peli == null)
            return false;
        return this.codigo.equals(peli.getCodigo());
    }
    
    public String toString(){
        return this.getCodigo()+","+this.getTitulo()+","+this.getListaActores().toString();
    }
    
    public static LinkedList<Pelicula> cargarPeliculas(){
        LinkedList<Pelicula> pelicula= new LinkedList<>();
        File archivo = new File("src/archivos/peliculas.txt");
        try {
            Scanner sc = new Scanner(archivo);
            sc.useDelimiter("\n");
            while(sc.hasNext()){
                String linea = sc.nextLine();
                String[] campos = linea.split("\\\n");
                campos=campos[0].split("\\|");
                String[] actores = campos[6].split("\\,");
                LinkedList listaActores = new LinkedList<>();
                for(String actor: actores){
                    listaActores.add(actor);
                }
                

                Pelicula u = new Pelicula(campos[0],campos[1],Integer.parseInt(campos[2].trim()),campos[3],campos[4],campos[5],listaActores,campos[7],campos[8],Integer.parseInt(campos[9].trim()));//trim elimina espacios
                pelicula.add(u);
            }
            sc.close();
        }catch(FileNotFoundException e){
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return pelicula;
    }
    
    public static Pelicula buscarPelicula(LinkedList<Pelicula> peliculas, String codigo){
        if(peliculas == null){
            return null;
        }
        for(Pelicula pelicula: peliculas){
            if(pelicula.getCodigo().equals(codigo)){
                return pelicula;
            }
        }
        return null;
    }
    
    public static LinkedList<String> generosEncontrados(LinkedList<Pelicula> peliculas){
        LinkedList<String> generos = new LinkedList<>();
        for(Pelicula pelicula: peliculas){
            if(generos.isEmpty()){
                generos.addLast(pelicula.getGenero());
            }else if(!generos.contains(pelicula.getGenero())){
                generos.addLast(pelicula.getGenero());
            }
        }
        return generos;
    }
    
    public static LinkedList<Pelicula> peliculasDelGenero(String genero, LinkedList<Pelicula> peliculas){
        LinkedList<Pelicula> peliculasGenero = new LinkedList<>();
        for(Pelicula pelicula: peliculas){
            if(pelicula.getGenero().equals(genero)){
                peliculasGenero.add(pelicula);
            }
        }
        return peliculasGenero;
    }
    
    public static LinkedList<Pelicula> peliculasFiltradasPorNombre(String cadena, LinkedList<Pelicula> peliculas){
        LinkedList<Pelicula> peliculasGenero = new LinkedList<>();
        
        for(Pelicula pelicula: peliculas){
            for(int i = 0; i<= pelicula.getTitulo().length() - cadena.length();i++){
                if(pelicula.getTitulo().substring(i,i+cadena.length()).equalsIgnoreCase(cadena)){
                    peliculasGenero.add(pelicula);
                    break;
                }
            }
        }
        return peliculasGenero;
    }
    
    public static void listarPeliculas(LinkedList<Pelicula> peliculas){
        int cont = 1;
        
        System.out.println("PELICULAS DISPONIBLES: \n");
        for(Pelicula pelicula: peliculas){
            System.out.println(cont + ".- " + pelicula.getTitulo());
            cont++;
        }
    }
    
    public void detallePelicula(){
        int aux = this.titulo.length();
        int cont = 0;
        String[] sipnosis = ("Sipnosis: " + this.sinopsis).split(" ");
        LinkedList<String> lineas = new  LinkedList<>();
        String linea = " ";
        for(int i = 0; i < sipnosis.length; i++){
           if(linea.length() + sipnosis[i].length() < 77){
               linea = linea + " " + sipnosis[i];
           }else{
               while(linea.length() < 79){
                   linea = linea + " ";
               }
               lineas.add(linea);
               linea = " ";
           }
           if(i == sipnosis.length -1){
               while(linea.length() < 79){
                   linea = linea + " ";
               }
               lineas.add(linea);
           }
        }
        
        linea = " Actores:";
        LinkedList<String> lineasActores = new LinkedList<>();
        for(Object actor :this.listaActores){
            if(linea.length() + ((String)actor).length() < 79){
                linea = linea + " " + ((String) actor) + ",";
            }else{
                while(linea.length() < 79){
                    linea = linea + " ";
                }
                lineasActores.add(linea);
                linea = "";
            }
            if(((String)actor).equals((String)(this.listaActores.getLast()))){
                    while(linea.length() < 79){
                        linea = linea + " ";
                    }
                    lineasActores.add(linea);
                }
        }
        
        System.out.print((char)27 + "[34;43m");
        System.out.print((char)27 + "[34;31m");
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        System.out.print((char)27 + "[34;43m");
        System.out.print((char)27 + "[34;31m");
        System.out.print("*");
        for(int i=0; i <((int)(79/2) - (int)(aux/2)); i++){
            System.out.print(" ");
            cont ++;
        }
        System.out.print((char)27 + "[34;30m");
        System.out.print(this.titulo.toUpperCase());
        System.out.print((char)27 + "[34;31m");
        for(int i= cont + aux; i <79; i++){
            System.out.print(" ");
        }
        System.out.println("*");
        
        System.out.print((char)27 + "[34;43m");
        System.out.print((char)27 + "[34;31m");
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        
        System.out.print((char)27 + "[34;43m");
        System.out.print((char)27 + "[34;31m");
        System.out.println("*                                                                               *");
        
        for(String lineax: lineas){
            System.out.print((char)27 + "[34;43m");
            System.out.print((char)27 + "[34;31m");
            System.out.print("*");
            System.out.print((char)27 + "[34;30m");
            System.out.print(lineax);
            System.out.print((char)27 + "[34;31m");
            System.out.println("*");
        }
        
        System.out.print((char)27 + "[34;43m");
        System.out.print((char)27 + "[34;31m");
        System.out.println("*                                                                               *");
        
        System.out.print((char)27 + "[34;43m");
        System.out.print((char)27 + "[34;31m");
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
        
        for(String lineax: lineasActores){
            System.out.print((char)27 + "[34;43m");
            System.out.print((char)27 + "[34;31m");
            System.out.print("*");
            System.out.print((char)27 + "[34;30m");
            System.out.print(lineax);
            System.out.print((char)27 + "[34;31m");
            System.out.println("*");
        }
        
        System.out.print((char)27 + "[34;43m");
        System.out.print((char)27 + "[34;31m");
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
    }
    
    public int numRentas(LinkedList<Renta> rentas, Greenbox greenbox){
        int cont = 0;
        for(Renta renta: rentas){
            if(renta.getEstadoDeRenta() == 1){
                CopiaPelicula cop = CopiaPelicula.obtenerCopiaPelicula(renta.obtenerKiosko(greenbox).getCopiasPelicula(), renta.getCodigoBarras());
                if(cop.getCodigoPelicula().equalsIgnoreCase(this.codigo) ){
                    cont++;
                }
            }
        }
        return cont;
    }
    
    public static void main(String args[]){
        LinkedList<Pelicula> peliculas = Pelicula.cargarPeliculas();
        System.out.println(Arrays.toString(Pelicula.peliculasFiltradasPorNombre("in", peliculas).toArray()));
        
    }
}
