package tools;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

import address.data.AddressBook;
import address.data.AddressEntry;

public class LoadAddressFile {
    
    private static final int NUMBER_OF_ADDRESS_ENTRY_PARAMETERS = 8 ;
    private static final String CHARACTER_ENCODING = "UTF-8" ;

    public static String loadFileFromExplorer(){
        FileDialog fileDialog = new FileDialog(new Frame(), "Selecciona el archivo de direcciones.", FileDialog.LOAD); 
        fileDialog.setAlwaysOnTop(true);
        fileDialog.setVisible(true);
        
        String selectedFile = fileDialog.getFile();
        if (selectedFile != null ) 
            return fileDialog.getDirectory() + selectedFile;

        return null; 
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

    public static void saveAfterDelete(AddressEntry addressEntryDelete){
        Path filePath = Paths.get(AddressBook.PATH_ADDRESS_BOOK);
        System.out.println(addressEntryDelete.toString());

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath.toFile()), CHARACTER_ENCODING))) {
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
                
            
            try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath.toFile()), "CHARACTER_ENCODING"))) {  
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
    
    public static void saveAfterAdd(AddressEntry addressEntryAdd) {
        try{
            writeAddressInFileDefault(addressEntryAdd);
        }
        catch (Exception e) {
            System.err.println(Colors.CRITICAL_ERROR +"[Error al escribir en el archivo] " + e.getMessage() + Colors.ANSI_RESET);
        }
    }
    public static void writeAddressInFileDefault(AddressEntry addressEntry){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AddressBook.PATH_ADDRESS_BOOK,StandardCharsets.UTF_8, true))) {
    
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

}
