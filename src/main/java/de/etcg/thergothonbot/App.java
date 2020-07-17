package de.etcg.thergothonbot;

import de.etcg.thergothonbot.control.Reader; 
/**
 * Hello world!
 *
 */
public class App{
    public static void main( String[] args )
    {
        Reader reader = Reader.getInstance();

        reader.read(Integer.parseInt(args[0]), args[1]); 
        reader.convertCardsToJSONFiles(); 
        
    }
}
