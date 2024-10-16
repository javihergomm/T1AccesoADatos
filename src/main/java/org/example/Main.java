package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Conversor conversor = new Conversor();
        System.out.println("Indica el nombre del archivo log (asegurate de que está en la carpeta src/logs)");
        Scanner teclado = new Scanner(System.in);
        String nombreArchivo = teclado.nextLine();


        conversor.LeerArchivo("src/logs/" + nombreArchivo);

        System.out.println("Indica el filtro por nivel \n (NINGUNO, INFO, ERROR, WARNING)");
        String filtro = teclado.nextLine();
        conversor.filtroNivel(filtro);
        String formato;
        String nombreSinExtensión = nombreArchivo.split("\\.")[0];
        String rutaFinal;
        do {
            System.out.println("¿A que formato lo quieres exportar?: ");
            System.out.println("XML");
            System.out.println("JSON");
            formato = teclado.nextLine();
            System.out.println("¿En que ruta quieres guardar el archivo " + formato.toLowerCase() + "?");
            rutaFinal = teclado.nextLine();


            if (formato.equalsIgnoreCase("XML")) {
                conversor.exportarAXML(rutaFinal + "/" + nombreSinExtensión + ".xml");
            } else if (formato.equalsIgnoreCase("JSON")) {
                conversor.exportarAJSON(rutaFinal + "/" + nombreSinExtensión + ".json");
            } else {
                System.err.println("Ese formato no es válido");
            }

        }while (!(formato.equalsIgnoreCase("XML") || formato.equalsIgnoreCase("JSON")));
    }
}
