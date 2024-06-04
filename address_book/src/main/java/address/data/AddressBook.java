package address.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import tools.Colors;
import tools.LoadAddressFile;
import tools.SortedArrayListAddress;
 
public class AddressBook{

    public ArrayList<AddressEntry> AddressEntryList = new ArrayList<>() ;
    private static AddressBook instance = null;
    
    private AddressBook(){
        LoadAddressFile.addAddressFromFileDefault(AddressEntryList);
    }
    
    public static synchronized AddressBook getInstance(){
        if(instance == null)
            instance = new AddressBook() ;
        return instance ;
    }
    
    public void addAddressFromFile(){
        
        String  filePath = LoadAddressFile.loadFileFromExplorer() ; 
        System.out.println(filePath == null);
        if(filePath != null){
            try {
                File importFile = new File(filePath) ;
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(importFile), StandardCharsets.UTF_8))) {
                    if(filePath.endsWith(".txt")){
                        AddressEntry addressEntryFile = new AddressEntry();
                        String line ;
                        while((line = reader.readLine()) != null){
                            LoadAddressFile.fillAddressEntry(addressEntryFile, line);

                            if(LoadAddressFile.isAddressComplete(addressEntryFile)){         
                                if(isValidAddressEntry(addressEntryFile)){
                                    if(Collections.binarySearch(AddressEntryList, addressEntryFile) < 0)    
                                        SortedArrayListAddress.addOrder(AddressEntryList, addressEntryFile); 
                                    else 
                                        System.out.println( Colors.ANSI_YELLOW + "¡Este contacto ya existe! " + Colors.ANSI_RESET +addressEntryFile.toString());
                                }
                                else 
                                    System.err.println(Colors.CRITICAL_ERROR + "[Parámetro inválido]" + Colors.ANSI_RESET + " Algún campo no cumple con el formato adecuado. " + addressEntryFile.toString());   
                                addressEntryFile = new AddressEntry();
                            }
                        }
                    }
                    else{
                        System.err.println(Colors.CRITICAL_ERROR + "[Parámetro inválido]" + Colors.ANSI_RESET + " No se seleccionó un documento de texto. " );   
                        return;
                    }
                    System.out.println(Colors.ANSI_GREEN + "[Archivo procesado correctamente]" + Colors.ANSI_RESET + "\n" + filePath);
                } 
            } 
            catch (Exception e) {
                System.err.println(Colors.CRITICAL_ERROR + "[Archivo no encontrado]" +  Colors.ANSI_RESET + " " + e.getMessage() );
            }
        }
        else
            System.out.println(Colors.CRITICAL_ERROR + "[Operación cancelada]" +  Colors.ANSI_RESET + " No se seleccionó un archivo.");
    }
   
    public void addAddress(AddressEntry addressEntry){   
        
        
        if(isValidAddressEntry(addressEntry) == true){
            if(Collections.binarySearch(AddressEntryList, addressEntry) < 0){
                SortedArrayListAddress.addOrder(AddressEntryList, addressEntry);
                LoadAddressFile.writeAddressInFileDefault(addressEntry);
            }
            else 
                System.out.println( Colors.ANSI_YELLOW + "¡Este contacto ya existe! " + Colors.ANSI_RESET +addressEntry.toString());
        }       
        else
            System.out.println(Colors.CRITICAL_ERROR + "[Parámetro inválido]" + Colors.ANSI_RESET + " Algún campo no cumple con el formato adecuado.");
    }



    public void showAllAddress(){
        
        if(AddressEntryList.size() > 0){
            int index = 0;
            for(AddressEntry addressEntry : AddressEntryList){
                System.out.println(
                    Colors.ANSI_BOLD + Colors.ANSI_PURPLE + index + "\t" + Colors.ANSI_RESET + Colors.ANSI_WHITE + 
                    addressEntry.getFirstName() + ' ' + addressEntry.getLastName() + "\n\t" +
                    addressEntry.getStreet() + ' ' + addressEntry.getCity() + ", " + addressEntry.getState() + ' ' + addressEntry.getPostalCode() + "\n\t" +
                    addressEntry.getEmail() + "\t" +
                    addressEntry.getPhoneNumber() + '\n' + Colors.ANSI_RESET
                );
                index ++;
            }
        }
        else 
            System.out.println(Colors.CRITICAL_ERROR + "[No se encontraron resultados]" + Colors.ANSI_RESET +  " No hay contactos que mostrar.");
    }


    public ArrayList<Integer> searchAddress(String searchString){
    
        ArrayList<Integer> addressFound = new ArrayList<>() ;
        int index = 0;
        
        for(AddressEntry addressEntry : AddressEntryList){
            if(addressEntry.getLastName().contains(searchString)){
                System.out.println(
                    Colors.ANSI_BOLD + Colors.ANSI_PURPLE + index + "\t" + Colors.ANSI_RESET + Colors.ANSI_WHITE + 
                    addressEntry.getFirstName() + ' ' + addressEntry.getLastName() + "\n\t" +
                    addressEntry.getStreet() + ' ' + addressEntry.getCity() + ", " + addressEntry.getState() + ' ' + addressEntry.getPostalCode() + "\n\t" +
                    addressEntry.getEmail() + "\t" +
                    addressEntry.getPhoneNumber() + '\n' + Colors.ANSI_RESET
                );
                addressFound.add((Integer)index) ;
                
            } 
            index ++;
        }
        if(index == 0)
            System.out.println(Colors.CRITICAL_ERROR + "[No se encontraron resultados]" + Colors.ANSI_RESET +  " No hay contactos que mostrar.");
        return addressFound ;
    }

    public void deleteAddress(String deletedString){

       
        ArrayList<Integer>addressFound = searchAddress(deletedString) ;
        if(addressFound.size() >= 1){
            System.out.println("Ingresar " + Colors.ANSI_BOLD + Colors.ANSI_GREEN + 'Y' + Colors.ANSI_RESET + " eliminará todos los registros.");
            System.out.println("Ingresar " + Colors.ANSI_BOLD + Colors.ANSI_GREEN + "Y [número] [número_2]" + Colors.ANSI_RESET + " eliminará los registros seleccionados.");
            System.out.println("Ingresar " + Colors.ANSI_BOLD + Colors.ANSI_RED +  'N' + Colors.ANSI_RESET+ " no eliminará ningún contacto.");

            ArrayList<String> optionAnswered = new ArrayList<>();
            Scanner input = new Scanner(System.in, StandardCharsets.UTF_8) ;
            while (true) {
                System.out.print(Colors.ANSI_YELLOW + "Ingresa una opción válida: " + Colors.ANSI_RESET);
                Collections.addAll(optionAnswered, input.nextLine().split(" "));
                switch (optionAnswered.get(0)){
                    case "Y":
                        if(optionAnswered.size() > 1){
                            optionAnswered.remove(0);
                            Collections.sort(optionAnswered, Comparator.reverseOrder());
                            
                                try {
                                    for(String indexAddress : optionAnswered){
                                        if(Integer.parseInt(indexAddress) <= AddressEntryList.size() - 1){                       
                                        
                                            LoadAddressFile.saveAfterDelete(AddressEntryList.get(Integer.parseInt(indexAddress)));
                                            AddressEntryList.remove(Integer.parseInt(indexAddress));

                                        }
                                        else 
                                        System.err.println(Colors.CRITICAL_ERROR + "[Parámetro inválido]" + Colors.ANSI_RESET + " " +  indexAddress + " ¡No pertenece a ningún registro!");   
                                    }
                                    return;
                                    
                                } catch (Exception e) {
                                    System.err.println(Colors.CRITICAL_ERROR + "[Parámetro inválido]" + Colors.ANSI_RESET  + " ¡Ingresa una pción válida!");   
                                }
                            
                        }
                        else if(optionAnswered.size() == 1){                           
                            Collections.sort(addressFound, Comparator.reverseOrder());
                            try {
                                for(Integer indexAddress : addressFound){
                                    LoadAddressFile.saveAfterDelete(AddressEntryList.get((int)(indexAddress))); 
                                    AddressEntryList.remove((int)indexAddress);   
                                }
                                return;
                            } catch (Exception e) {
                                System.err.println(Colors.CRITICAL_ERROR + "[Parámetro inválido]" + Colors.ANSI_RESET  + " ¡Ingresa una pción válida!");   
                            }
                        }
                        break;
                    case "N":
                        System.out.println(Colors.CRITICAL_ERROR + "[Operación cancelada]" + Colors.ANSI_RESET + " Se seleccionó cancelar la operación");
                    default:
                        System.out.println(Colors.CRITICAL_ERROR + "[Operación inválida]" + Colors.ANSI_RESET + " Se seleccionó una opción invalida."); /*  */
                    }
                }
        }
        else
            System.out.println(Colors.CRITICAL_ERROR + "[No se encontraron resultados]" + Colors.ANSI_RESET +  " No hay contactos que mostrar.");
    }

    public static boolean isValidAddressEntry(AddressEntry addressEntry){
        if(addressEntry.getFirstName().length() < 1 || addressEntry.getLastName().length() < 1 || addressEntry.getStreet().length() < 1 && 
        addressEntry.getCity().length() < 1 || addressEntry.getState().length() < 1 || addressEntry.getPostalCode().length() < 1 &&
        addressEntry.getEmail().length() < 1 || addressEntry.getPhoneNumber().length() < 1)
            return false;

        return true;
    }

   
}
