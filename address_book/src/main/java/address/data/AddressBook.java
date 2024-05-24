package address.data;

import java.awt.FileDialog;
import java.awt.Frame;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
/*
 ¿Dónde debo guardar cada cambio? ¿Tengo que reescribir todo el código?
 */
import com.googlecode.lanterna.gui2.table.Table; //Dependencia lanterna: https://github.com/mabe02/lanterna/tree/master
import com.googlecode.lanterna.terminal.swing.TerminalScrollController.Null;
 
public class AddressBook {
    private ArrayList<AddressEntry> AddressEntryList = new ArrayList<>() ;
    // exportar algún archivo
    
    public void addAddressFromFile() // verificar que no sean repetidos
    {
        FileDialog fileDialog = new FileDialog(new Frame(), "Selecciona el archivo con los datos.");
        fileDialog.setVisible(true);
        String fileName = fileDialog.getFile() ;
        if(fileName != null)
        {
            String directory = fileDialog.getDirectory() ;
            try 
            {
                File importFile = new File(directory) ;
                Scanner scannerFile = new Scanner(importFile) ;
                while(scannerFile.hasNextLine())
                {
                    AddressEntry addressEntryFile = new AddressEntry() ;
                    if(addressEntryFile.getFirstName() == null || addressEntryFile.getFirstName().isEmpty())
                        addressEntryFile.setFirstName(scannerFile.nextLine());
                    else if(addressEntryFile.getLastName() == null || addressEntryFile.getLastName().isEmpty())
                        addressEntryFile.setLastName(scannerFile.nextLine());
                    else if(addressEntryFile.getStreet() == null || addressEntryFile.getStreet().isEmpty())
                        addressEntryFile.setStreet(scannerFile.nextLine());
                    else if(addressEntryFile.getCity() == null || addressEntryFile.getCity().isEmpty())
                        addressEntryFile.setCity(scannerFile.nextLine());
                    else if(addressEntryFile.getState() == null || addressEntryFile.getState().isEmpty())
                        addressEntryFile.setState(scannerFile.nextLine());
                    else if(addressEntryFile.getPostalCode() == null || addressEntryFile.getPostalCode().isEmpty())
                        addressEntryFile.setPostalCode(scannerFile.nextLine());
                    else if(addressEntryFile.getEmail() == null || addressEntryFile.getEmail().isEmpty())
                        addressEntryFile.setEmail(scannerFile.nextLine());
                   
                    else if(addressEntryFile.getPhoneNumber() == null)
                    {
                        addressEntryFile.setPhoneNumber(scannerFile.nextLine());
                        AddressEntryList.add(addressEntryFile);
                        addressEntryFile = null ;
                    }
                    
                }

            }
            catch (Exception e) 
            {
                // TODO: handle exception
            }
        }
    }
    public void addAddress(AddressEntry addressEntry)
    {
        if(validationAddressEntry(addressEntry) == true)
            AddressEntryList.add(readEntry());
        else
            ;
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
        Table<String> tableAddress = new Table<String>("Número", "Full Name", "Address", "Email", "Phone Number");
        int index = 1;
        for(AddressEntry addressEntry : AddressEntryList)
        {
            tableAddress.getTableModel().addRow(
                String.valueOf(index),
                addressEntry.getFirstName() + ' ' +  addressEntry.getLastName(),
                addressEntry.getStreet() + '\n' + addressEntry.getCity() + ", " + addressEntry.getState() + ' ' + addressEntry.getPostalCode(),
                addressEntry.getEmail(),
                addressEntry.getPhoneNumber()
            ) ;
            index ++;
        }
    }


    public ArrayList<Integer> searchAddress(String action)
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Ingrese algún campo del contacto que desea " + action);
        
        switch (action) {
            case "eliminar":
                //rojo
                break;
            case "editar":
                //verde a azul
                break;
            case "buscar":
                //?
                break;
            default:
                break;
        }
        String searchString = input.nextLine() ;
        //resetColor();


        Table<String> tableAddress = new Table<String>("Número", "Full Name", "Address", "Email", "Phone Number");
        ArrayList<Integer> addressFound = new ArrayList<>() ;
        int index = 0;
        for(AddressEntry addressEntry : AddressEntryList)
        {
            if(addressEntry.toString().contains(searchString))
            {
                tableAddress.getTableModel().addRow(
                    String.valueOf(index + 1),
                    addressEntry.getFirstName() + ' ' +  addressEntry.getLastName(),
                    addressEntry.getStreet() + '\n' + addressEntry.getCity() + ", " + addressEntry.getState() + ' ' + addressEntry.getPostalCode(),
                    addressEntry.getEmail(),
                    addressEntry.getPhoneNumber()                    
                ) ;
                addressFound.add((Integer)index) ;
                index++ ;
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
        ArrayList<Integer>addressFound = searchAddress("Eliminar") ;
        
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
                    else if(optionAnswered.size() == 0)
                    {
                        Collections.sort(addressFound, Comparator.reverseOrder());
                        for(Integer indexAddress : addressFound)
                            AddressEntryList.remove(indexAddress);
                    }
                    else 
                        System.out.println("No se encontraron contactos.");
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

    public void findAddress()
    {
        // Analizar para qué era esta función. Probablemente era para ver un indice en especifico, pero en este punto no le encuentro diferencia por el readAddress
    }
    //considerar espacios y esas jaladas de acento, el .com y @, usar regex para eso.
    public boolean validationAddressEntry(AddressEntry addressEntry)
    {
        if(addressEntry.getFirstName().length() < 1 && addressEntry.getLastName().length() < 1 && addressEntry.getStreet().length() < 1 && 
        addressEntry.getCity().length() < 1 && addressEntry.getState().length() < 1 && addressEntry.getPostalCode().length() < 1 &&
        addressEntry.getEmail().length() < 1 && addressEntry.getPhoneNumber().length() < 1)
            return false;
        for(char elementOfName : addressEntry.getFirstName().toCharArray())
            if( elementOfName < 'A' || (elementOfName > 'Z' && elementOfName < 'a') || elementOfName < 'z')
                return false;
        
        for(char elementOfLastName : addressEntry.getLastName().toCharArray())
            if( elementOfLastName < 'A' || (elementOfLastName > 'Z' && elementOfLastName < 'a') || elementOfLastName < 'z')
                return false;

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
