package address;
import java.util.Scanner;

import address.data.AddressBook;
import address.data.AddressEntry;
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
        do
        {
            optionsList.displayOptions();
            option = input.nextLine();
            if(!option.isEmpty() && option.charAt(0) >= 'a' && option.charAt(0) < 'a' + optionsList.size())
                optionsList.executeOption(option.charAt(0) - 'a');
            else
                System.out.println("Entrada inválida.");
            
            
        }while(!option.equals("f"));
    }
  
    private void loadFile()
    {
        System.out.println("Ingresa en la ventana desplegada y selecciona el archivo.");
        addressList.addAddressFromFile() ;
    }
    private void addAddress()
    {
        AddressEntry addressEntry = new AddressEntry();
        System.out.println("Nombre:");
        addressEntry.setFirstName(input.nextLine());
        System.out.println("Apellido:");
        addressEntry.setLastName(input.nextLine());
        System.out.println("Calle:");
        addressEntry.setStreet(input.nextLine());
        System.out.println("Ciudad:");
        addressEntry.setCity(input.nextLine());
        System.out.println("Estado:");
        addressEntry.setState(input.nextLine());
        System.out.println("CP:");
        addressEntry.setPostalCode(input.nextLine());
        System.out.println("Email:");
        addressEntry.setEmail(input.nextLine());
        System.out.println("Teléfono:");
        addressEntry.setPhoneNumber(input.nextLine());

        addressList.addAddress(addressEntry);
    }
    private void deleteAddress()
    {
        System.out.println("Ingresa algún campo del contacto a eliminar");
        addressList.deleteAddress();
    }
    private void searchAddress()
    {
        addressList.searchAddress("buscar");
    }
    private void showAllAddress()
    {

        addressList.showAllAddress() ;
    }
    private void exitMenu()
    {
        System.exit(0);
    }

  
}
