package tools;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;

import address.data.AddressBook;
import address.data.AddressEntry;

/**
 * Clase que proporciona métodos para cargar y manipular archivos de texto con el fin del guardado y actualización de direcciones.
*/
public class LoadAddressFile {
    
    /**
     * Determina la cantidad de parámetros que tiene {@link AddressEntry}.
    */
    private static final int NUMBER_OF_ADDRESS_ENTRY_PARAMETERS = 8 ;
    /**
     * Determina la ruta relativa por defecto del archivo de texto para la permanencia de datos.
    */
    public static final String PATH_ADDRESS_BOOK  = "address_book/src/main/resources/AddressBook.txt" ;

    /**
     * Constructor privado para evitar la creación de instancias de esta clase. 
    */
    private LoadAddressFile() {
        // Este constructor está vacío para evitar que se cree una instancia, no se hace uso.
    }

    /**
     * Método que abre un diálogo de selección de archivos para que el usuario seleccione un archivo de direcciones.
     * @return La ruta del archivo seleccionado.
    */
    public static String selectFileFromDialog(){
        FileDialog fileDialog = new FileDialog(new Frame(), "Selecciona el archivo de direcciones.", FileDialog.LOAD); 
        fileDialog.setAlwaysOnTop(true);
        fileDialog.setVisible(true);
        
        String selectedFile = fileDialog.getFile();
        if (selectedFile != null ) 
            return fileDialog.getDirectory() + selectedFile;

        return null; 
    }

    /**
     * Método que llena una entrada de dirección con los datos de una línea del archivo.
     * Verifica cuál es el primer atributo faltante y lo asigna.
     * @param entry La entrada de dirección a llenar.
     * @param line La línea del archivo que contiene los datos de la entrada de dirección.
    */
    public static void fillAddressEntry(AddressEntry entry, String line){
        
        if (entry.getFirstName() == null || entry.getFirstName().isEmpty()) {
            entry.setFirstName(line);
        } else if (entry.getLastName() == null || entry.getLastName().isEmpty()) {
            entry.setLastName(line);
        } else if (entry.getStreet() == null || entry.getStreet().isEmpty()) {
            entry.setStreet(line);
        } else if (entry.getCity() == null || entry.getCity().isEmpty()) {
            entry.setCity(line);
        } else if (entry.getState() == null || entry.getState().isEmpty()) {
            entry.setState(line);
        } else if (entry.getPostalCode() == null || entry.getPostalCode().isEmpty()) {
            entry.setPostalCode(line);
        } else if (entry.getEmail() == null || entry.getEmail().isEmpty()) {
            entry.setEmail(line);
        } else if (entry.getPhoneNumber() == null || entry.getPhoneNumber().isEmpty()) {
            entry.setPhoneNumber(line);
        }
    }

    /**
     * Método que verifica si una entrada de dirección está completa, con todos los atributos válidos.
     * @param entry La entrada de dirección a verificar.
     * @return true si la entrada de dirección está completa, false en caso contrario.
    */
    public static boolean isAddressComplete(AddressEntry entry){
        
        return entry.getFirstName() != null && !entry.getFirstName().isEmpty() &&
               entry.getLastName() != null && !entry.getLastName().isEmpty() &&
               entry.getStreet() != null && !entry.getStreet().isEmpty() &&
               entry.getCity() != null && !entry.getCity().isEmpty() &&
               entry.getState() != null && !entry.getState().isEmpty() &&
               entry.getPostalCode() != null && !entry.getPostalCode().isEmpty() &&
               entry.getEmail() != null && !entry.getEmail().isEmpty() &&
               entry.getPhoneNumber() != null && !entry.getPhoneNumber().isEmpty();
    }

    /**
     * Método que elimina el el {@link AddressEntry} en el archivo.
     * Usa {@link NUMBER_OF_ADDRESS_ENTRY_PARAMETERS} para saber cuántas veces eliminará un número de linea. .
     * @param addressEntryDelete La entrada de dirección eliminada.
    */
    public static void updateFileAfterDelete(AddressEntry addressEntryDelete){
        Path path = Paths.get(PATH_ADDRESS_BOOK);
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            int indexOfFile  = 0;
            boolean isFoundDeletedAddress = false;
            ArrayList<String> dataOfFile = new ArrayList<>() ;
            AddressEntry tempAddressEntry = new AddressEntry();

            while ((line = reader.readLine()) != null) {
                LoadAddressFile.fillAddressEntry(tempAddressEntry, line);
                dataOfFile.add(line) ;
                if (isAddressComplete(tempAddressEntry)) {
                    if ( tempAddressEntry.toString().equals(addressEntryDelete.toString()) && !isFoundDeletedAddress) 
                        isFoundDeletedAddress  = true;
             
                    tempAddressEntry = new AddressEntry();
                }
                if(!isFoundDeletedAddress) indexOfFile ++;
            }
            indexOfFile -= NUMBER_OF_ADDRESS_ENTRY_PARAMETERS - 1;
            for(int c = 0 ;  c < NUMBER_OF_ADDRESS_ENTRY_PARAMETERS; c++)
                dataOfFile.remove(indexOfFile) ;
                                                                       
            try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8)) {
                for (int c = 0 ; c < dataOfFile.size() ; c++) {
                    writer.write(dataOfFile.get(c));
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println(Colors.CRITICAL_ERROR + "[Error al escribir en el archivo] " + e.getMessage() + Colors.ANSI_RESET);
            }
        } catch (IOException e) {
            System.err.println(Colors.CRITICAL_ERROR + "[Error al manipular el archivo] " + e.getMessage() + Colors.ANSI_RESET);
            return;
        }   
    }
    
    /**
     * Método que guarda las modificaciones después de agregar una entrada de dirección al archivo.
     * @param addressEntryAdd La entrada de dirección agregada.
    */
    public static void updateFileWithNewEntry(AddressEntry addressEntryAdd) {
        try{
            saveAddressToDefaultFile(addressEntryAdd);
        }
        catch (Exception e) {
            System.err.println(Colors.CRITICAL_ERROR +"[Error al escribir en el archivo] " + e.getMessage() + Colors.ANSI_RESET);
        }
    }

    /**
     * Método que escribe una entrada de dirección en el archivo de direcciones por defecto.
     * @param addressEntry La entrada de dirección a escribir en el archivo.
    */
    public static void saveAddressToDefaultFile(AddressEntry addressEntry){
        Path path = Paths.get(PATH_ADDRESS_BOOK);
        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8, Files.exists(path) ? StandardOpenOption.APPEND : StandardOpenOption.CREATE)) {
            writer.write(addressEntry.getFirstName()); 
            writer.newLine();
            writer.write(addressEntry.getLastName()); 
            writer.newLine();
            writer.write(addressEntry.getStreet()); 
            writer.newLine();
            writer.write(addressEntry.getCity()); 
            writer.newLine();
            writer.write(addressEntry.getState()); 
            writer.newLine();
            writer.write(addressEntry.getPostalCode()); 
            writer.newLine();
            writer.write(addressEntry.getEmail()); 
            writer.newLine();
            writer.write(addressEntry.getPhoneNumber()); 
            writer.newLine();
        } catch (IOException e) {
            System.err.println(Colors.CRITICAL_ERROR + "[Error al escribir en el archivo] " + e.getMessage()  + Colors.ANSI_RESET);
        }
    }

    /**
     * Método que agrega entradas de dirección desde el archivo de direcciones por defecto a la lista de entradas {@link AddressBook#AddressEntryList}.
     * @param AddressEntryList La lista de entradas de dirección.
    */
    public static void importAddressesFromDefaultFile(ArrayList<AddressEntry> AddressEntryList) {
        try {
            
            Path path = Paths.get(PATH_ADDRESS_BOOK);
            try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
                AddressEntry addressEntryFile = new AddressEntry();
                String line;
                while ((line = reader.readLine()) != null) {
                    LoadAddressFile.fillAddressEntry(addressEntryFile, line);
                    if (LoadAddressFile.isAddressComplete(addressEntryFile)) {
                        if (AddressBook.isValidAddressEntry(addressEntryFile)) {
                            if (Collections.binarySearch(AddressEntryList, addressEntryFile) < 0)
                                SortedArrayListAddress.addOrder(AddressEntryList, addressEntryFile);
                            else
                                System.out.println(Colors.ANSI_YELLOW + "¡Este contacto ya existe! El archivo contiene contactos duplicados. " + Colors.ANSI_RESET + addressEntryFile.toString());
                        } else
                            System.err.println(Colors.CRITICAL_ERROR + "[Parámetro inválido] " + Colors.ANSI_RESET + "El archivo contiene contactos donde algún campo no cumple con el formato adecuado. " + addressEntryFile.toString());
                        addressEntryFile = new AddressEntry();
                    }
                }
                System.out.println(Colors.ANSI_GREEN + "[AddressBook importado] " + Colors.ANSI_RESET + "Se importaron correctamente las direcciones.");
            }
        } catch (Exception e) {
            System.out.println(Colors.CRITICAL_ERROR + "[Error al leer el archivo]" + Colors.ANSI_RESET + " " + e.getMessage());
        }
    }

}
