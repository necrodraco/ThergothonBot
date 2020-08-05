package de.etcg.thergothonbot;

import de.etcg.thergothonbot.control.InteractionHandler; 
import de.etcg.thergothonbot.control.Reader;
import de.etcg.thergothonbot.control.Checker; 
import de.etcg.thergothonbot.control.Writer;  

public class App{
    public static void main( String[] args )
    {
        InteractionHandler interactionHandler = InteractionHandler.getInstance(); 
        Reader reader = Reader.getInstance();
        Checker checker = Checker.getInstance();
        Writer writer = Writer.getInstance();       
        String answer;
        do{
            answer = interactionHandler.askLong();
            if(answer.equals("read")){
                reader.read();  
            }else if(answer.equals("check")){
                checker.startCheck();
            }else if(answer.equals("write")){
                writer.write(); 
            }else if(answer.equals("end")){
                answer = null;
            }else{
                interactionHandler.tryAgain(false);
            }
        }while (answer != null); 
        interactionHandler.close();
    }
}
