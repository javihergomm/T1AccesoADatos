package org.example;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Conversor {
    private List<Logs> logscrudos;
    private List<Logs> logsfiltrados;

    public Conversor() {
        logscrudos = new ArrayList<>();
        logsfiltrados = new ArrayList<>();
    }

    public void LeerArchivo (String ruta) {
        List<String> logsNoValidos = new ArrayList<>();
        DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try{
            List<String> lineasLog = Files.readAllLines(Path.of(ruta));
            for (String linea : lineasLog) {
                try{
                    String[] partes = linea.split("] ");
                    String fechaString = partes[0].replace("[", "");
                    String nivel = partes[1].replace("]", "");
                    nivel = nivel.replace("[", "");
                    String mensaje = partes[2];
                    LocalDateTime fecha = LocalDateTime.parse(fechaString, formatoFecha);
                    logscrudos.add(new Logs(fecha, nivel, mensaje));
                }catch (Exception e){
                    logsNoValidos.add(linea);
                }

            }
            if (!logsNoValidos.isEmpty()) {
                System.out.println("Advertencia: No se pudieron procesar las siguientes líneas:");
                for (String logNoValido : logsNoValidos) {
                    System.out.println(logNoValido);
                }
            }

        } catch (IOException e){
            System.err.println("No existe ese archivo");
            System.exit(1);
        }

    }


    public void filtroNivel(String nivel){
        if (nivel.equalsIgnoreCase("NINGUNO")){
            logsfiltrados = new ArrayList<>(logscrudos);
        } else{
            for (Logs log : logscrudos) {
                if (log.getNivel().equalsIgnoreCase(nivel)){
                    logsfiltrados.add(log);
                }
            }
        }
        mostrarLogsFiltrados();

    }

    public void mostrarLogsFiltrados(){
        if (logsfiltrados.isEmpty()){
            System.out.println("No hay nada que mostrar");
        } else {
            for (Logs log : logsfiltrados){
                System.out.println(log);
            }
        }
    }
    public void exportarAJSON(String rutaArchivo) {
        JSONArray jsonArray = new JSONArray();
        for (Logs log : logsfiltrados) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fechaHora", log.getFecha().toString());
            jsonObject.put("nivel", log.getNivel());
            jsonObject.put("mensaje", log.getMensaje());
            jsonArray.put(jsonObject);
        }
        try (FileWriter file = new FileWriter(rutaArchivo)) {
            file.write(jsonArray.toString(4));
            System.out.println("Logs exportados correctamente a JSON.");
        } catch (IOException e) {
            System.out.println("Error al exportar a JSON: " + e.getMessage());
        }
    }

    public void exportarAXML(String rutaArchivo) {
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.newDocument();

            Element rootElement = doc.createElement("logAplicacion");
            doc.appendChild(rootElement);

            for (Logs log : logsfiltrados) {
                Element logElement = doc.createElement("log");

                Element fechaHora = doc.createElement("fechaHora");
                fechaHora.appendChild(doc.createTextNode(log.getFecha().toString()));
                logElement.appendChild(fechaHora);

                Element nivel = doc.createElement("nivel");
                nivel.appendChild(doc.createTextNode(log.getNivel()));
                logElement.appendChild(nivel);

                Element mensaje = doc.createElement("mensaje");
                mensaje.appendChild(doc.createTextNode(log.getMensaje()));
                logElement.appendChild(mensaje);

                rootElement.appendChild(logElement);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            DOMSource domSource = new DOMSource(doc);
            StreamResult streamResult = new StreamResult(new File(rutaArchivo));

            transformer.transform(domSource, streamResult);
            System.out.println("Logs exportados correctamente a XML.");
        } catch (ParserConfigurationException | TransformerException e) {
            System.out.println("Error al exportar a XML: " + e.getMessage());
        }
        
    }

}
