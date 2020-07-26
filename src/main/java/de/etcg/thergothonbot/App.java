package de.etcg.thergothonbot;

import de.etcg.thergothonbot.control.Reader;
import de.etcg.thergothonbot.control.Writer;  

import java.util.Scanner;

public class App{
    public static void main( String[] args )
    {
        Scanner sc = new Scanner(System.in);
        String answer;
        Reader reader = Reader.getInstance();
        Writer writer = Writer.getInstance(sc);       
        while (true) {
            System.out.print("Was soll gemacht werden?(read/write/end)");
            answer = sc.nextLine().trim().toLowerCase();
            if(answer.equals("read")){
                System.out.print("Geben sie die URL von yugipedia an, die verarbeitet werden soll: ");
                String url = sc.nextLine().trim();
                System.out.print("Geben sie die eTCG-Set-ID an, zu welchem Set diese Daten gehören: ");
                int etcgId = sc.nextInt();
                reader.read(etcgId, url); 
                reader.convertCardsToJSONFiles(); 
                sc.nextLine();
            }else if(answer.equals("write")){
                writer.readJSONToCard();
                writer.write(); 
            }else if(answer.equals("end")){
                break;
            }else{
                System.out.println("Eingabe nicht verstanden. Erneuter Versuch");
            }
        }
        sc.close();
        
    }
}
