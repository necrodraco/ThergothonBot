package de.etcg.thergothonbot;

import de.etcg.thergothonbot.control.Reader;
import de.etcg.thergothonbot.control.Writer;  
/**
 * Hello world!
 *
 */
public class App{
    public static void main( String[] args )
    {
        System.out.println("0:" + args[0]);
        if(args[0].equals("read")){
            Reader reader = Reader.getInstance();
            reader.read(Integer.parseInt(args[1]), args[2]); 
            reader.convertCardsToJSONFiles(); 
        }else if(args[0].equals("write")){
            Writer writer = Writer.getInstance();
            writer.write(); 
        }
        
    }
}
