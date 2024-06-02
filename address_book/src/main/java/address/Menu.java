package address;
import java.util.Scanner;

import address.data.AddressBook;
import tools.Colors;
import tools.Option;
import tools.OptionsList;

public class Menu {
    

    private Scanner input = new Scanner(System.in) ;
    AddressBook addressList = new AddressBook();
    private final OptionsList optionsList;

    public Menu(){
        optionsList =  createOptionsList() ;
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
        addressList.addAddress();
    }
    private void deleteAddress(){
        System.out.println(Colors.ANSI_YELLOW + "Ingrese algún campo del contacto que desea" + Colors.CRITICAL_ERROR+ " eliminar." + Colors.ANSI_RESET);
        addressList.deleteAddress();
    }

    private void searchAddress(){
        System.out.println(Colors.ANSI_YELLOW + "Ingrese algún campo del contacto que desea" + Colors.ANSI_BLUE + " buscar." + Colors.ANSI_RESET);
        addressList.searchAddress();
    }
    private void showAllAddress(){
        System.out.println(Colors.ANSI_YELLOW + "Direcciones guardadas:" + Colors.ANSI_RESET);
        addressList.showAllAddress() ;
    }
    
    private void exitMenu(){
        System.exit(0);
    }

  
}
