package address.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

/*
 ¿Dónde debo guardar cada cambio? ¿Tengo que reescribir todo el código?
 */

import tools.Colors;
import tools.LoadAddressFile;
import tools.SortedArrayListAddress;
 // Permitir las entradas con acentos.
public class AddressBook {
    private ArrayList<AddressEntry> AddressEntryList = new ArrayList<>() ;
    public static String pathAdressBook  = Paths.get( "address_book/src/main/java/info/AddressBook.txt").toString();

   public AddressBook()
   {
        addAddressFromFileDefault();
   }
    
    public void addAddressFromFile() 
    {
        
        String  filePath = LoadAddressFile.loadFileFromExplorer(".txt") ;

        if(!filePath.contains("null") || filePath.isEmpty())
        {
            try {
                File importFile = new File(filePath) ;
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                        new FileInputStream(importFile), "UTF-8"))) {
                    AddressEntry addressEntryFile = new AddressEntry();
                    String line ;
                    while((line = reader.readLine()) != null)
                    {
                        LoadAddressFile.fillAddressEntry(addressEntryFile, line);

                        if(LoadAddressFile.isAddressComplete(addressEntryFile))
                        {         
                            if(validationAddressEntry(addressEntryFile))
                            {
                                if(Collections.binarySearch(AddressEntryList, addressEntryFile) < 0)    
                                    SortedArrayListAddress.addOrder(AddressEntryList, addressEntryFile); 
                                else 
                                    System.out.println( Colors.ANSI_YELLOW + "¡Este contacto ya existe! " + Colors.ANSI_RESET +addressEntryFile.toString());
                            }
                            else 
                                System.err.println(Colors.ANSI_RED + "[Parámetro inválido] " + Colors.ANSI_RESET + "Algún campo no cumple con el formato adecuado. " + addressEntryFile.toString());   
                            addressEntryFile = new AddressEntry();
                        }
                        
                    }
                    System.out.println(Colors.ANSI_GREEN + "[Archivo procesado correctamente] " + Colors.ANSI_RESET + "\n" + filePath);
                } 
            } 
            catch (Exception e) {
                System.err.println(Colors.ANSI_RED + "[Archivo no encontrado] " +  Colors.ANSI_RESET + e.getMessage() );
            }
        }
        else
            System.err.println(Colors.ANSI_RED + "[Operación cancelada] " +  Colors.ANSI_RESET );
    }
    public void addAddressFromFileDefault() 
    {        
        try {
            File importFile = new File(pathAdressBook) ;
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                new FileInputStream(importFile), "UTF-8"))) {
                AddressEntry addressEntryFile = new AddressEntry();
                String line ;
                while((line = reader.readLine()) != null)
                {
                    LoadAddressFile.fillAddressEntry(addressEntryFile, line);
                    if(LoadAddressFile.isAddressComplete(addressEntryFile))
                    {         
                        if(validationAddressEntry(addressEntryFile))
                        {
                            if(Collections.binarySearch(AddressEntryList, addressEntryFile) < 0)    
                                SortedArrayListAddress.addOrder(AddressEntryList, addressEntryFile); 
                            else 
                                System.out.println( Colors.ANSI_YELLOW + "¡Este contacto ya existe! " + Colors.ANSI_RESET +addressEntryFile.toString());
                        }
                        else 
                            System.err.println(Colors.ANSI_RED + "[Parámetro inválido] " + Colors.ANSI_RESET + "Algún campo no cumple con el formato adecuado. " + addressEntryFile.toString());   
                            addressEntryFile = new AddressEntry();
                    }
                        
                }
                System.out.println(Colors.ANSI_GREEN + "[AddressBook importado] " + Colors.ANSI_RESET + "Se importaron correctamente las direcciones.");
            } 
        } 
        catch (Exception e) {
            System.err.println(Colors.ANSI_RED + "[Archivo no encontrado] " +  Colors.ANSI_RESET + e.getMessage() );
        }
        
     
    }
    public void addAddress()
    {   
        AddressEntry addressEntry = readAddressEntry() ;
        
        if(validationAddressEntry(addressEntry) == true)
        {
            if(Collections.binarySearch(AddressEntryList, addressEntry) < 0)    
            {
                SortedArrayListAddress.addOrder(AddressEntryList, addressEntry);
                LoadAddressFile.saveAfterAdd(addressEntry); 
            }
            else 
                System.out.println( Colors.ANSI_YELLOW + "¡Este contacto ya existe! " + Colors.ANSI_RESET +addressEntry.toString());
        }       
        else
            System.out.println(Colors.ANSI_RED + "[Parámetro inválido] " + Colors.ANSI_RESET + "Algún campo no cumple con el formato adecuado.");
    }



    public void showAllAddress()
    {
        
        if(AddressEntryList.size() > 0)
        {
            System.out.println(Colors.ANSI_CYAN + "Número\tFull Name\tAddress\tEmail\tPhone Number" + Colors.ANSI_RESET);
            int index = 0;
            for(AddressEntry addressEntry : AddressEntryList)
            {
                System.out.println(
                    index + "\t" +
                    addressEntry.getFirstName() + ' ' + addressEntry.getLastName() + "\t" +
                    addressEntry.getStreet() + '\n' + addressEntry.getCity() + ", " + addressEntry.getState() + ' ' + addressEntry.getPostalCode() + "\t" +
                    addressEntry.getEmail() + "\t" +
                    addressEntry.getPhoneNumber()
                );
                index ++;
            }
        }
        else 
            System.out.println(Colors.ANSI_RED + "[No se encontraron resultados] " + Colors.ANSI_RESET +  "No hay contactos que mostrar.");
    }


    public ArrayList<Integer> searchAddress(String action)
    {
        Scanner input = new Scanner(System.in, "UTF-8");
        System.out.print("Ingrese algún campo del contacto que desea ");
        
        switch (action) {
            case "eliminar":
                System.out.println(Colors.ANSI_RED + action + Colors.ANSI_RESET); 
                break;
            case "editar":
                System.out.println(Colors.ANSI_GREEN + action + Colors.ANSI_RESET); 
                break;
            case "buscar":
                System.out.println(Colors.ANSI_BLUE + action + Colors.ANSI_RESET); 
                break;
            default:
                System.out.println(action);
                break;
        }
        String searchString = input.nextLine() ; // Agregar coincidencia

        ArrayList<Integer> addressFound = new ArrayList<>() ;
        int index = 0;
        
        for(AddressEntry addressEntry : AddressEntryList)
        {
            if(addressEntry.toString().contains(searchString))
            {
                System.out.println(
                    index + "\t" +
                    addressEntry.getFirstName() + ' ' + addressEntry.getLastName() + "\t" +
                    addressEntry.getStreet() + '\n' + addressEntry.getCity() + ", " + addressEntry.getState() + ' ' + addressEntry.getPostalCode() + "\t" +
                    addressEntry.getEmail() + "\t" +
                    addressEntry.getPhoneNumber()
                );
                addressFound.add((Integer)index) ;
                
            } 
            index ++;
        }
        if(index == 0 && !action.equals("eliminar"))
            System.out.println(Colors.ANSI_RED + "[No se encontraron resultados] " + Colors.ANSI_RESET +  "No hay contactos que mostrar.");
        return addressFound ;
    }

    public void deleteAddress()
    {
        Scanner input = new Scanner(System.in, "UTF-8") ;
        ArrayList<Integer>addressFound = searchAddress("eliminar") ;
        if(addressFound.size() >= 1)
        {
            System.out.println("Ingresar 'y' eliminará todos los registros.");
            System.out.println("Ingresar 'y [número] [número_2]' eliminará los registros seleccionados.");
            System.out.println("Ingresar 'n' lo regresará al menú.");

            ArrayList<String> optionAnswered = new ArrayList<>();
            Collections.addAll(optionAnswered, input.nextLine().split(" "));
            switch (optionAnswered.get(0)) {
                case "y":
                    if(optionAnswered.size() > 1)
                    {
                        optionAnswered.remove(0);
                        Collections.sort(optionAnswered, Comparator.reverseOrder());
                        for(String indexAddress : optionAnswered)
                        {
                            if(Integer.parseInt(indexAddress) <= AddressEntryList.size() - 1){                       
                                
                                LoadAddressFile.saveAfterDelete(AddressEntryList.get(Integer.parseInt(indexAddress)));
                                AddressEntryList.remove(Integer.parseInt(indexAddress));
                            }
                            else 
                                System.err.println(Colors.ANSI_RED + "[Parámetro inválido] " + Colors.ANSI_RESET + indexAddress + " ¡No pertenece a ningún registro!");   
                        }
                    }
                    else if(optionAnswered.size() == 1)
                    {                           
                        Collections.sort(addressFound, Comparator.reverseOrder());
                        for(Integer indexAddress : addressFound){
                            LoadAddressFile.saveAfterDelete(AddressEntryList.get((int)(indexAddress))); 
                            AddressEntryList.remove((int)indexAddress);   
                        }
                    }
                    break;
                default:
                    System.out.println(Colors.ANSI_RED + "[Operación cancelada]" + Colors.ANSI_RESET);;
                }
        }
        else
        System.out.println(Colors.ANSI_RED + "[No se encontraron resultados] " + Colors.ANSI_RESET +  "No hay contactos que mostrar.");
    }

    public boolean validationAddressEntry(AddressEntry addressEntry)
    {
        if(addressEntry.getFirstName().length() < 1 || addressEntry.getLastName().length() < 1 || addressEntry.getStreet().length() < 1 && 
        addressEntry.getCity().length() < 1 || addressEntry.getState().length() < 1 || addressEntry.getPostalCode().length() < 1 &&
        addressEntry.getEmail().length() < 1 || addressEntry.getPhoneNumber().length() < 1)
            return false;

        return true;
    }

    public AddressEntry readAddressEntry() // Función creada para la escalibilidad.
    {
        Scanner input = new Scanner(System.in, "UTF-8") ;
        System.out.println("Ingrese el nombre: ") ; String firstName = input.nextLine() ;
        System.out.println("Ingrese los apellidos: ") ; String lastName = input.nextLine() ;
        System.out.println("Ingrese la calle: ") ; String street = input.nextLine() ;
        System.out.println("Ingrese la ciudad: ") ; String city = input.nextLine();
        System.out.println("Ingrese el estado: ") ; String state = input.nextLine() ;
        System.out.println("Ingrese el código postal: ") ; String postalCode = input.nextLine();
        System.out.println("Ingrese un correo electrónico: ") ; String email = input.nextLine() ;
        System.out.println("Ingrese un número telefónico: ") ; String phoneNumber = input.nextLine() ;

        AddressEntry newAddressEntry = new AddressEntry(firstName, lastName, street, city, state, postalCode, email, phoneNumber) ;
        return newAddressEntry;
    }
}
