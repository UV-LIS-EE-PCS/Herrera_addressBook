package tools;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OptionsList {

    private List<Option> optionsList = new ArrayList<>() ;
    public static final char OPTION_INITIAL = 'a' ;

    public OptionsList(Option... options){
          Collections.addAll(optionsList, options);
    }
    public void executeOption(int index){
        if (index >= 0 && index < optionsList.size()) {
            optionsList.get(index).execute();
        } else {
            System.out.println(Colors.CRITICAL_ERROR + "Opción no válida" + Colors.ANSI_RESET);
        }
    }
    
    public void displayOptions(){
        System.out.println( Colors.ANSI_YELLOW +  "Selecciona una opción del menú" + Colors.ANSI_RESET);
       
        for (char letra = OPTION_INITIAL; letra < OPTION_INITIAL + optionsList.size(); letra++){
            System.out.println( Colors.ANSI_GREEN+ letra + ") " + Colors.ANSI_WHITE + optionsList.get( letra - OPTION_INITIAL ) +  Colors.ANSI_RESET);
        }
    }

    public int size(){
        return optionsList.size() ;
    }
}
