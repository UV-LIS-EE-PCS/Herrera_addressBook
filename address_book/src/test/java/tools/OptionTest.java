package tools;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class OptionTest {
    
    @Test 
    public void testToString() {
        Option option = new Option("Esto es una cadena.", () ->{});

        assertEquals(option.toString(), "Esto es una cadena.");
    }

   @Test
    public void testExecute() {
        String[] callbackCalled = {"No se llamó Runnable"};
        Runnable mockCallback = () -> callbackCalled[0] = "Se llamó Runnable";

        Option option = new Option("Runnable Test", mockCallback);
        option.execute();

        assertEquals(callbackCalled[0], "Se llamó Runnable");
    }

}


