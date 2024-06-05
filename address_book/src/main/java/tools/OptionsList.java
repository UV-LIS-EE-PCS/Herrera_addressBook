package tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase que representa una lista de opciones de un menú.
*/
public class OptionsList {

    /*
     * Estructura que permite guardar las múltiples {@link Option}.
    */
    private List<Option> optionsList = new ArrayList<>() ;

    /**
     * Carácter que determina el inicio de listado de opciones, usado en {@link #displayOptions()}.
    */
    public static final char OPTION_INITIAL = 'a' ;

    /**
     * Constructor de la clase OptionsList.
     * @param options Arreglo de opciones para inicializar la lista de opciones dentro de {@link optionsList}.
    */
    public OptionsList(Option... options){
          Collections.addAll(optionsList, options);
    }

    /**
     * Método que ejecuta la opción seleccionada por su índice.
     * @param index Índice de la opción a ejecutar.
    */
    public void executeOption(int index){
        if (index >= 0 && index < optionsList.size()) {
            optionsList.get(index).execute();
        } else {
            System.out.println(Colors.CRITICAL_ERROR + "Opción no válida" + Colors.ANSI_RESET);
        }
    }
    
    /**
     * Método que muestra en consola las opciones disponibles en el menú.
    */
    public void displayOptions(){
        System.out.println( Colors.ANSI_YELLOW +  "Selecciona una opción del menú" + Colors.ANSI_RESET);
       
        for (char letra = OPTION_INITIAL; letra < OPTION_INITIAL + optionsList.size(); letra++){
            System.out.println( Colors.ANSI_GREEN+ letra + ") " + Colors.ANSI_WHITE + optionsList.get( letra - OPTION_INITIAL ) +  Colors.ANSI_RESET);
        }
    }

    /**
     * Método que devuelve la cantidad de opciones en la lista.
     * @return La cantidad de opciones en la lista.
    */
    public int size(){
        return optionsList.size() ;
    }
}
