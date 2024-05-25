package tools;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.File;

import address.data.AddressEntry;

public class LoadAddressFile {
    
    public static String loadFileFromExplorer(String fileExtension)
    {
        String filePath ;
        FileDialog fileDialog = new FileDialog(new Frame(), "Selecciona el archivo de direcciones.", FileDialog.LOAD);
        fileDialog.setFilenameFilter((File dir, String fileName) -> fileName.endsWith(fileExtension));
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

    public static boolean isAddressComplete(AddressEntry entry)
    {
        return entry.getFirstName() != null && !entry.getFirstName().isEmpty() &&
               entry.getLastName() != null && !entry.getLastName().isEmpty() &&
               entry.getStreet() != null && !entry.getStreet().isEmpty() &&
               entry.getCity() != null && !entry.getCity().isEmpty() &&
               entry.getState() != null && !entry.getState().isEmpty() &&
               entry.getPostalCode() != null && !entry.getPostalCode().isEmpty() &&
               entry.getEmail() != null && !entry.getEmail().isEmpty() &&
               entry.getPhoneNumber() != null && !entry.getPhoneNumber().isEmpty();
    }
}
