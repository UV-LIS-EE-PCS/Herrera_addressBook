package address.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

import tools.Colors;
import tools.LoadAddressFile;
import tools.SortedArrayListAddress;
 
public class AddressBook{

    private ArrayList<AddressEntry> AddressEntryList = new ArrayList<>() ;
    // Esto es cuando compilas desde un IDE// public static final String PATH_ADDRESS_BOOK  = Paths.get( "address_book/src/main/java/info/AddressBook.txt").toString();
    //public static final String PATH_ADDRESS_BOOK  = Paths.get( "address_book/src/main/resources/AddressBook.txt").toString();
    public static final String PATH_ADDRESS_BOOK  = "/AddressBook.txt";
    public AddressBook(){
        addAddressFromFileDefault();
    }
    
    public void addAddressFromFile(){
        
        String  filePath = LoadAddressFile.loadFileFromExplorer() ; 

        if(!filePath.contains("null") || filePath.isEmpty()){
            try {
                File importFile = new File(filePath) ;
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(importFile), "UTF-8"))) {
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
                    System.out.println(Colors.ANSI_GREEN + "[Archivo procesado correctamente]" + Colors.ANSI_RESET + "\n" + filePath);
                } 
            } 
            catch (Exception e) {
                System.err.println(Colors.CRITICAL_ERROR + "[Archivo no encontrado]" +  Colors.ANSI_RESET + " " + e.getMessage() );
            }
        }
        else
            System.out.println(Colors.CRITICAL_ERROR + "[Operación cancelada]" +  Colors.ANSI_RESET );
    }
    public void addAddressFromFileDefault() {
        try {
            InputStream inputStream = getClass().getResourceAsStream(PATH_ADDRESS_BOOK);
            if (inputStream != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))) {
                    AddressEntry addressEntryFile = new AddressEntry();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        LoadAddressFile.fillAddressEntry(addressEntryFile, line);
                        if (LoadAddressFile.isAddressComplete(addressEntryFile)) {
                            if (isValidAddressEntry(addressEntryFile)) {
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
            } else {
                System.out.println(Colors.CRITICAL_ERROR + "[Archivo no encontrado]" + Colors.ANSI_RESET + " " + "No se pudo encontrar el archivo en el classpath.");
            }
        } catch (Exception e) {
            System.out.println(Colors.CRITICAL_ERROR + "[Error al leer el archivo]" + Colors.ANSI_RESET + " " + e.getMessage());
        }
    }
    public void addAddress(){   
        AddressEntry addressEntry = readAddressEntry() ;
        
        if(isValidAddressEntry(addressEntry) == true){
            if(Collections.binarySearch(AddressEntryList, addressEntry) < 0){
                SortedArrayListAddress.addOrder(AddressEntryList, addressEntry);
                LoadAddressFile.saveAfterAdd(addressEntry); 
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


    public ArrayList<Integer> searchAddress(){
        Scanner input = new Scanner(System.in, "UTF-8");
        String searchString = input.nextLine() ; 

        ArrayList<Integer> addressFound = new ArrayList<>() ;
        int index = 0;
        
        for(AddressEntry addressEntry : AddressEntryList){
            if(addressEntry.toString().contains(searchString)){
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

    public void deleteAddress(){

        Scanner input = new Scanner(System.in, "UTF-8") ;
        ArrayList<Integer>addressFound = searchAddress() ;
        if(addressFound.size() >= 1){
            System.out.println("Ingresar " + Colors.ANSI_GREEN + 'Y' + Colors.ANSI_RESET + " eliminará todos los registros.");
            System.out.println("Ingresar " + Colors.ANSI_GREEN + "Y [número] [número_2]" + Colors.ANSI_RESET + " eliminará los registros seleccionados.");
            System.out.println("Ingresar " + Colors.CRITICAL_ERROR +  'N' + Colors.ANSI_RESET+ "no eliminará ningún contacto.");

            ArrayList<String> optionAnswered = new ArrayList<>();
            Collections.addAll(optionAnswered, input.nextLine().split(" "));
            switch (optionAnswered.get(0)){
                case "Y":
                    if(optionAnswered.size() > 1){
                        optionAnswered.remove(0);
                        Collections.sort(optionAnswered, Comparator.reverseOrder());
                        for(String indexAddress : optionAnswered){
                            if(Integer.parseInt(indexAddress) <= AddressEntryList.size() - 1){                       
                                
                                LoadAddressFile.saveAfterDelete(AddressEntryList.get(Integer.parseInt(indexAddress)));
                                AddressEntryList.remove(Integer.parseInt(indexAddress));
                            }
                            else 
                                System.err.println(Colors.CRITICAL_ERROR + "[Parámetro inválido]" + Colors.ANSI_RESET + " " +  indexAddress + " ¡No pertenece a ningún registro!");   
                        }
                    }
                    else if(optionAnswered.size() == 1){                           
                        Collections.sort(addressFound, Comparator.reverseOrder());
                        for(Integer indexAddress : addressFound){
                            LoadAddressFile.saveAfterDelete(AddressEntryList.get((int)(indexAddress))); 
                            AddressEntryList.remove((int)indexAddress);   
                        }
                    }
                    break;
                case "N":
                    System.out.println(Colors.CRITICAL_ERROR + "[Operación cancelada]" + Colors.ANSI_RESET + "Se seleccionó cancelar la operación");
                default:
                    System.out.println(Colors.CRITICAL_ERROR + "[Operación cancelada]" + Colors.ANSI_RESET + "Se seleccionó una opción invalida.");
                }
        }
        else
            System.out.println(Colors.CRITICAL_ERROR + "[No se encontraron resultados]" + Colors.ANSI_RESET +  " No hay contactos que mostrar.");
    }

    public boolean isValidAddressEntry(AddressEntry addressEntry){
        if(addressEntry.getFirstName().length() < 1 || addressEntry.getLastName().length() < 1 || addressEntry.getStreet().length() < 1 && 
        addressEntry.getCity().length() < 1 || addressEntry.getState().length() < 1 || addressEntry.getPostalCode().length() < 1 &&
        addressEntry.getEmail().length() < 1 || addressEntry.getPhoneNumber().length() < 1)
            return false;

        return true;
    }

    public AddressEntry readAddressEntry(){
        Scanner input = new Scanner(System.in, "UTF-8") ;
        System.out.print(Colors.ANSI_BLUE + "Nombre: " + Colors.ANSI_RESET) ; String firstName = input.nextLine() ;
        System.out.print(Colors.ANSI_BLUE + "Apellidos: " + Colors.ANSI_RESET) ; String lastName = input.nextLine() ;
        System.out.print(Colors.ANSI_BLUE + "Calle: " + Colors.ANSI_RESET) ; String street = input.nextLine() ;
        System.out.print(Colors.ANSI_BLUE + "Ciudad: " + Colors.ANSI_RESET) ; String city = input.nextLine();
        System.out.print(Colors.ANSI_BLUE + "Estado: " + Colors.ANSI_RESET) ; String state = input.nextLine() ;
        System.out.print(Colors.ANSI_BLUE + "Código postal: " + Colors.ANSI_RESET) ; String postalCode = input.nextLine();
        System.out.print(Colors.ANSI_BLUE + "Correo electrónico: " + Colors.ANSI_RESET) ; String email = input.nextLine() ;
        System.out.print(Colors.ANSI_BLUE + "Número telefónico: " + Colors.ANSI_RESET) ; String phoneNumber = input.nextLine() ;

        AddressEntry newAddressEntry = new AddressEntry(firstName, lastName, street, city, state, postalCode, email, phoneNumber) ;
        return newAddressEntry;
    }
}
