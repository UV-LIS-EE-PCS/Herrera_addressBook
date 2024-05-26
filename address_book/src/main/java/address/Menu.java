package address;
import java.util.Scanner;

import address.data.AddressBook;
import address.data.AddressEntry;
import tools.Colors;
import tools.Option;
import tools.OptionsList;

public class Menu {
    

    private Scanner input = new Scanner(System.in) ;
    AddressBook addressList = new AddressBook();
    

    OptionsList optionsList = new OptionsList(
        new Option("Cargar de archivo", this::loadFile),
        new Option("Agregar", this::addAddress),
        new Option("Eliminar", this::deleteAddress),
        new Option("Buscar", this::searchAddress),
        new Option("Mostrar", this::showAllAddress),
        new Option("Salir", this::exitMenu)
    );
           

    public void displayMenu()
    {
        String option ;
        while(true)
        {
            optionsList.displayOptions();
            option = input.nextLine();
            if(!option.isEmpty() && option.charAt(0) >= 'a' && option.charAt(0) < 'a' + optionsList.size())
                optionsList.executeOption(option.charAt(0) - 'a');
            else
                System.out.println("Entrada inválida.");
            
            
        }
    }
  
    private void loadFile()
    {
        System.out.println("Ingresa en la ventana desplegada y selecciona el archivo.");
        addressList.addAddressFromFile() ;
    }
    private void addAddress()
    {
        System.out.println(Colors.ANSI_PURPLE + "Ingresa los datos del contacto." + Colors.ANSI_RESET);
        addressList.addAddress();
    }
    private void deleteAddress()
    {
        addressList.deleteAddress();
    }
    private void searchAddress()
    {
        addressList.searchAddress("buscar");
    }
    private void showAllAddress()
    {
        System.out.println(Colors.ANSI_CYAN + "Direcciones guardadas:" + Colors.ANSI_RESET);
        addressList.showAllAddress() ;
    }
    private void exitMenu()
    {
        System.exit(0);
    }

  
}
