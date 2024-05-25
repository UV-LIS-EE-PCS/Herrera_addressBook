package address.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Pattern;

/*
 ¿Dónde debo guardar cada cambio? ¿Tengo que reescribir todo el código?
 */
import com.googlecode.lanterna.gui2.table.Table; //Dependencia lanterna: https://github.com/mabe02/lanterna/tree/master

import tools.Colors;
import tools.LoadAddressFile;
 
public class AddressBook {
    private ArrayList<AddressEntry> AddressEntryList = new ArrayList<>() ;
    // exportar algún archivo
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    private static final String FULL_NAME_PATTERN = "^[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+(?:\\s[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ]+)?$";
    private static final String ADDRESS_PATTERN = "^[a-zA-Z0-9áéíóúÁÉÍÓÚüÜñÑ.,\\s#]*$";
    private static final String POSTAL_CODE_PATTERN = "^\\d{5}(?:[-\\s]\\d{4})?$";
    private static final String PHONE_NUMBER_PATTERN = "^(?:\\+\\d{1,3})?\\d{10}$";


    public void addAddressFromFile() // verificar que no sean repetidos
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
                            AddressEntryList.add(addressEntryFile);
                            addressEntryFile = new AddressEntry();
                        }
                    }
                    System.out.println(Colors.ANSI_GREEN + "[Archivo importado correctamente]" + Colors.ANSI_RESET + "\n" + filePath);
                } 
            } 
            catch (Exception e) {
                System.err.println(Colors.ANSI_RED + "[Archivo no encontrado] " +  Colors.ANSI_RESET + e.getMessage() );
            }
        }
         
    }
    public void addAddress()
    {   
        AddressEntry addressEntry = readEntry() ;
        if(validationAddressEntry(addressEntry) == true)
            AddressEntryList.add(addressEntry);
        else
            System.err.println(Colors.ANSI_RED + "[Parámetros inválidos] " + Colors.ANSI_RESET + "Algún campo no cumple con el formato adecuado.");
    }

    public void readAddress(int index)
    {
        Table<String> tableAddress = new Table<String>("Número", "Full Name", "Address", "Email", "Phone Number");
        tableAddress.getTableModel().addRow(
                String.valueOf(index),
                AddressEntryList.get(index).getFirstName() + ' ' +  AddressEntryList.get(index).getLastName(),
                AddressEntryList.get(index).getStreet() + '\n' + AddressEntryList.get(index).getCity() + ", " + AddressEntryList.get(index).getState() + ' ' + AddressEntryList.get(index).getPostalCode(),
                AddressEntryList.get(index).getEmail(),
                AddressEntryList.get(index).getPhoneNumber()
            ) ;
    }

    public void showAllAddress()
    {
        System.out.println(Colors.ANSI_CYAN + "Número\tFull Name\tAddress\tEmail\tPhone Number" + Colors.ANSI_RESET);
        if(AddressEntryList.size() > 0)
        {
            int index = 1;
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
            System.out.println("No hay resultados que mostrar.");
    }


    public ArrayList<Integer> searchAddress(String action)
    {
        Scanner input = new Scanner(System.in);
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
                index ++;
            }  
        }

        return addressFound ;
    }

    public void editAddress(int index)
    {
        Scanner input = new Scanner(System.in) ;
        AddressEntry newDataEntry = new AddressEntry(
            input.nextLine(), // firstName
            input.nextLine(), // lastName
            input.nextLine(), // street
            input.nextLine(), // city
            input.nextLine(), // state
            input.nextLine(), // postalCode
            input.nextLine(), // email
            input.nextLine()  // phoneNumber
        );
        if(validationAddressEntry(newDataEntry))
        {
            AddressEntryList.set(index, newDataEntry) ;
        }
        else
            ; // Marcar el error especifico
        readAddress(index);
    }

    public void deleteAddress()
    {
        Scanner input = new Scanner(System.in) ;
        ArrayList<Integer>addressFound = searchAddress("eliminar") ;
        if(addressFound.size() > 1)
        {
            System.out.println("Ingresar 'y' eliminará todos los registros.");
            System.out.println("Ingresar 'y [número] [número_2]' eliminará los registros seleccionados.");
            System.out.println("Ingresar 'n' lo regresará al menú.");

            ArrayList<String> optionAnswered = new ArrayList<>();
            Collections.addAll(optionAnswered, input.nextLine().split(" "));

            
            try {
                switch (optionAnswered.get(0)) {
                    case "y":
                        if(optionAnswered.size() > 1)
                        {
                            optionAnswered.remove(0);
                            Collections.sort(optionAnswered, Comparator.reverseOrder());
                            for(String indexAddress : optionAnswered)
                                AddressEntryList.remove(Integer.parseInt(indexAddress));

                        }
                        else if(optionAnswered.size() == 1)
                        {
                           
                            Collections.sort(addressFound, Comparator.reverseOrder());
                            for(Integer indexAddress : addressFound)
                            {
                                AddressEntryList.remove((int)indexAddress);
                                System.out.println(indexAddress);
                            }
                            System.out.println(AddressEntryList.size());
                        }
                        break;
                    case "n":
                        return ;
                    default:
                        break;
                }

            } catch (Exception e) {
                // TODO: handle exception
            }
        }
        else
            System.out.println("No hay resultados que mostrar.");
    }

    public void findAddress()
    {
        // Analizar para qué era esta función. Probablemente era para ver un indice en especifico, pero en este punto no le encuentro diferencia por el readAddress
    }
    //considerar espacios y esas jaladas de acento, el .com y @, usar regex para eso.
    public boolean validationAddressEntry(AddressEntry addressEntry)
    {
        if(addressEntry.getFirstName().length() < 1 || addressEntry.getLastName().length() < 1 || addressEntry.getStreet().length() < 1 && 
        addressEntry.getCity().length() < 1 || addressEntry.getState().length() < 1 || addressEntry.getPostalCode().length() < 1 &&
        addressEntry.getEmail().length() < 1 || addressEntry.getPhoneNumber().length() < 1)
            return false;
            if (!Pattern.matches(FULL_NAME_PATTERN, addressEntry.getFirstName())) 
               {System.out.print(1);return false;}
            if (!Pattern.matches(FULL_NAME_PATTERN, addressEntry.getLastName())) 
            {System.out.print(2);return false;}
            if (!Pattern.matches(ADDRESS_PATTERN, addressEntry.getStreet())) 
            {System.out.print(3);return false;}
            if (!Pattern.matches(ADDRESS_PATTERN, addressEntry.getCity())) 
            {System.out.print(4);return false;}
            if (!Pattern.matches(ADDRESS_PATTERN, addressEntry.getState())) 
            {System.out.print(5);return false;}
            if (!Pattern.matches(POSTAL_CODE_PATTERN, addressEntry.getPostalCode())) 
            {System.out.print(6);return false;}
            if (!Pattern.matches(EMAIL_PATTERN, addressEntry.getEmail())) 
            {System.out.print(7);return false;}
            if (!Pattern.matches(PHONE_NUMBER_PATTERN, addressEntry.getPhoneNumber())) 
            {System.out.print(8);return false;}
        return true;
    }

    public AddressEntry readEntry() // Función creada para la escalibilidad.
    {
        Scanner input = new Scanner(System.in) ;
        System.out.println("Ingrese el nombre: ") ; String firstName = input.nextLine() ;
        System.out.println("Ingrese los apellidos: ") ; String lastName = input.nextLine() ;
        System.out.println("Ingrese la calle: ") ; String street = input.nextLine() ;
        System.out.println("Ingrese la ciudad: ") ; String city = input.nextLine() ;
        System.out.println("Ingrese el estado: ") ; String state = input.nextLine() ;
        System.out.println("Ingrese el código postal: ") ; String postalCode = input.nextLine() ;
        System.out.println("Ingrese un correo electrónico: ") ; String email = input.nextLine() ;
        System.out.println("Ingrese un número telefónico: ") ; String phoneNumber = input.nextLine() ;
        AddressEntry newAddressEntry = new AddressEntry(firstName, lastName, street, city, state, postalCode, email, phoneNumber) ;

        return newAddressEntry;
    }
}
