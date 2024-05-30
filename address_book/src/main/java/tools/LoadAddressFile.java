package tools;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import address.data.AddressBook;
import address.data.AddressEntry;

public class LoadAddressFile {
    
    static final int numberOfAddressEntryParameters = 8 ;
    public static String loadFileFromExplorer(String fileExtension)
    {
        String filePath ;
        FileDialog fileDialog = new FileDialog(new Frame(), "Selecciona el archivo de direcciones.", FileDialog.LOAD); 
        fileDialog.setFilenameFilter((File dir, String fileName) -> fileName.endsWith(fileExtension)); // Esto no funciona, no filtra.
        fileDialog.setAlwaysOnTop(true);
        fileDialog.setVisible(true);
        
        filePath = fileDialog.getDirectory() + fileDialog.getFile() ;
        return filePath;
    }

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

    public static void saveAfterDelete(AddressEntry addressEntryDelete) {
        Path filePath = Paths.get(AddressBook.PATH_ADDRESS_BOOK);
        System.out.println(addressEntryDelete.toString());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath.toFile()), "UTF-8"))) {
            String line;
            int indexOfFile  = 0;
            boolean isFoundDeletedAddres = false;
            ArrayList<String> dataOfFile = new ArrayList<>() ;
            AddressEntry tempAddressEntry = new AddressEntry();

            while ((line = reader.readLine()) != null) {
                LoadAddressFile.fillAddressEntry(tempAddressEntry, line);
                dataOfFile.add(line) ;
                if (LoadAddressFile.isAddressComplete(tempAddressEntry)) {
                    if ( tempAddressEntry.toString().equals(addressEntryDelete.toString()) && !isFoundDeletedAddres) 
                        isFoundDeletedAddres  = true;
             
                    tempAddressEntry = new AddressEntry();
                }
                if(!isFoundDeletedAddres) indexOfFile ++;
            }
            indexOfFile -= 7;
            for(int c = 0 ;  c < numberOfAddressEntryParameters; c++)
                dataOfFile.remove(indexOfFile) ;
                
            
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath.toFile()), "UTF-8"))) {  
                for (int c = 0 ; c < dataOfFile.size() ; c++) {
                    writer.write(dataOfFile.get(c));
                    writer.newLine();
                }
            } catch (IOException e) {
                System.err.println(Colors.ANSI_RED + "[Error al escribir en el archivo] " + e.getMessage() + Colors.ANSI_RESET);
            }
        } catch (IOException e) {
            System.err.println(Colors.ANSI_RED + "[Error al manipular el archivo] " + e.getMessage() + Colors.ANSI_RESET);
            return;
        }   
    }
    
    public static void saveAfterAdd(AddressEntry addressEntryAdd) {
        try{
            writeAddressInFileDefault(addressEntryAdd);
        }
        catch (Exception e) {
            System.err.println(Colors.ANSI_RED +"Error al escribir en el archivo: " + e.getMessage() + Colors.ANSI_RESET);
        }
    }
// acentos al escribir
    public static void writeAddressInFileDefault(AddressEntry addressEntry){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AddressBook.PATH_ADDRESS_BOOK, true))) {
            
            writer.write(addressEntry.getFirstName()); writer.newLine();
            writer.write(addressEntry.getLastName()); writer.newLine();
            writer.write(addressEntry.getStreet()); writer.newLine();
            writer.write(addressEntry.getCity()); writer.newLine();
            writer.write(addressEntry.getState()); writer.newLine();
            writer.write(addressEntry.getPostalCode()); writer.newLine();
            writer.write(addressEntry.getEmail()); writer.newLine();
            writer.write(addressEntry.getPhoneNumber()); writer.newLine();
        } catch (IOException e) {
        System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

}
