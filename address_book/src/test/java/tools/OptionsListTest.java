package tools;


import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

public class OptionsListTest {


    OptionsList optionsList = new OptionsList(
        new Option("Esta es la primera opción", this::emptyFunction),
        new Option("Esta es la segunda opción", this::emptyFunction),
        new Option("Esta es la tercera opción", this::emptyFunction)
    );
    void emptyFunction()
    {}


    @Test
    public void testDisplayOption(){
       
        ByteArrayOutputStream outContent = new ByteArrayOutputStream(); 
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        optionsList.displayOptions();
        System.setOut(originalOut);
        
        String expectedOutput =
                    "Selecciona una opción del menú" + (char)13 +  '\n' +
                    "a) Esta es la primera opción"+ (char)13  + "\n" +
                    "b) Esta es la segunda opción"+  (char)13  + "\n" +
                    "c) Esta es la tercera opción"+   (char)13  +  "\n" 
                    
        ;
        String output = outContent.toString();
        String cleanOutput = removeAnsiCodes(output);
        assertEquals(expectedOutput, cleanOutput);
    }


    private String removeAnsiCodes(String input) { // 
        // Patrón para eliminar para regex.
        String ansiPattern = "\\u001B\\[[;\\d]*m";
        return input.replaceAll(ansiPattern, "");
    }
}
