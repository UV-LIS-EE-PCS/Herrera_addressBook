package address;
import java.util.Scanner;

import address.data.AddressBook;
import address.data.AddressEntry;
import tools.Colors;
import tools.Option;
import tools.OptionsList;

public class Menu {
    

    private Scanner input ;
    private AddressBook addressList;
    private final OptionsList optionsList;

    public Menu(){
        optionsList =  createOptionsList() ;
        addressList = AddressBook.getInstance();
        input = new Scanner(System.in) ;
    }

    private OptionsList createOptionsList(){
        return new OptionsList(
            new Option("Cargar de archivo", this::loadFile),
            new Option("Agregar", this::addAddress),
            new Option("Eliminar", this::deleteAddress),
            new Option("Buscar", this::searchAddress),
            new Option("Mostrar", this::showAllAddress),
            new Option("Salir", this::exitMenu)
        );
    }
           

    public void displayMenu()
    {
        while(true)
        {
            optionsList.displayOptions();
            System.out.print(Colors.ANSI_YELLOW + "Ingrese una opción: " + Colors.ANSI_RESET);
            char optionEntered = input.next().charAt(0);
            input.nextLine();
            if(isValidOption(optionEntered))
                optionsList.executeOption(optionEntered - OptionsList.OPTION_INITIAL);
            else
                System.out.println(Colors.CRITICAL_ERROR + "[Entrada inválida] ¡Ingrese una opción disponible!" + Colors.ANSI_RESET);
        }
    }

    private boolean isValidOption(char option) {
        return option >= OptionsList.OPTION_INITIAL && option < OptionsList.OPTION_INITIAL + optionsList.size();
    }
  
    private void loadFile(){
        System.out.println(Colors.ANSI_YELLOW + "Ingresa en la ventana desplegada y selecciona el archivo." + Colors.ANSI_RESET);
        addressList.addAddressFromFile() ;
    }
    
    private void addAddress(){
        System.out.println(Colors.ANSI_YELLOW + "Ingresa los datos del contacto." + Colors.ANSI_RESET);
        AddressEntry addressEntry = readAddressEntry() ;
        addressList.addAddress(addressEntry);
    }
    private void deleteAddress(){
        System.out.println(Colors.ANSI_YELLOW + "Ingrese algún campo del contacto que desea " + Colors.CRITICAL_ERROR+ "eliminar." + Colors.ANSI_RESET);
        String deletedString = input.nextLine() ; 
        addressList.deleteAddress(deletedString);
    }

    private void searchAddress(){
        System.out.println(Colors.ANSI_YELLOW + "Ingrese algún campo del contacto que desea" + Colors.ANSI_BLUE + " buscar." + Colors.ANSI_RESET);
        String searchString = input.nextLine() ; 
        addressList.searchAddress(searchString);
    }
    private void showAllAddress(){
        System.out.println(Colors.ANSI_YELLOW + "Direcciones guardadas:" + Colors.ANSI_RESET);
        addressList.showAllAddress() ;
    }
    
    private void exitMenu(){
        System.exit(0);
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
