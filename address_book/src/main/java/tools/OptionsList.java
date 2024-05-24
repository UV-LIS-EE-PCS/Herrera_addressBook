package tools;
import java.util.ArrayList;
import tools.*;
// Buscar la forma de agregar un parametro size que sea usado como referencia al optionList.size();
import java.util.List;

public class OptionsList {

    private List<Option> optionList = new ArrayList<>() ;
    
    public OptionsList(Option... options) 
    {
        
        for(Option option : options)
        {
            optionList.add(option) ;
        }
    }
    public void executeOption(int index) 
    {
        if (index >= 0 && index < optionList.size()) {
            optionList.get(index).callback.run();
        } else {
            System.out.println("Opción no válida");
        }
    }
    /// agregar un get y set
    public void displayOptions() 
    {
        System.out.println( Colors.ANSI_YELLOW +  "Selecciona una opción del menú" + Colors.ANSI_RESET);
        char initialChar = 'a'; //Este otorgará el inicio de la numeración de las opciones.
        for (char letra = initialChar; letra < initialChar + optionList.size(); letra++) {
            System.out.println( Colors.ANSI_GREEN+ letra + ") " + Colors.ANSI_WHITE + optionList.get( letra - initialChar ) +  Colors.ANSI_RESET);
        }
    }

    public int size()
    {
        return optionList.size() ;
    }
}
