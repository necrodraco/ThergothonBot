package de.etcg.thergothonbot.control;

import de.etcg.thergothonbot.model.card.Card; 

import java.util.Scanner;

import java.io.Console;

public class InteractionHandler{
    private static InteractionHandler interactionHandler; 
    private Scanner sc; 

    private InteractionHandler(){
        sc = new Scanner(System.in); 
    }

    public static InteractionHandler getInstance(){
        if(interactionHandler == null)
            interactionHandler = new InteractionHandler(); 
        return interactionHandler; 
    }

    private boolean confirmationOnly(){
        String answer;
        while (true) {
            answer = ask();
            if (answer.equals("") || answer.equals("y") || answer.equals("j")) {
                return true;
            } else if (answer.equals("n")) {
                return false;
            } else {
                tryAgain(true);
            }
        }
    }

    public boolean confirmation(){
        System.out.println("Ist das Korrekt? (j/y/n)");
        return confirmationOnly();
    }

    public void printCard(Card card){
        System.out.println(card.toString());
    }

    public void doubleFound(Card card, String url){
        System.out.println("Doppelung gefunden für " + card.getEngName() + ": "); 
        System.out.println("alt: " + card.getReprintURL());
        System.out.println("neu: " + url);
        System.out.print("Soll die neue verwendet werden(j/y/n): ");
        String decision = ask(); 
        if(decision.equals("j") || decision.equals("y")) card.setReprintURL(url); 
    }

    public boolean cardNotFound(Card card){
        System.out.println("Keine Karte gefunden für " + card.getEngName());
        System.out.println("Bitte manuell suchen und hier eintragen oder auf 'Später' verschieben");
        System.out.print("Reprint-URL eingeben, als neu markieren('n') oder auf später Verschieben('d'): "); 
        String decision = askShort();
        if(decision.equals("d")){
            return false; 
        }else if(decision.equals("n") || decision.equals("")){
            card.setReprint(false); 
        }else{
            card.setReprintURL(decision);
        }
        return true; 
    }

    public String askLong(){
        System.out.print("Was soll gemacht werden?(read/check/write/end)");
        return ask();
    }

    public String ask(){
        return this.askShort().toLowerCase(); 
    }

    public String askURL(){
        System.out.print("Geben sie die URL von yugipedia an, die verarbeitet werden soll: ");
        return this.askShort();
    }

    private String askShort(){
        return sc.nextLine().trim(); 
    }

    public String askUserName(){
        System.out.println("Benutzernamen auf eTCG eingeben: ");
        String username = sc.next();
        return username; 
    }

    public String askPW(){ 
        Console console = System.console();
        if(console != null){
            return new String(console.readPassword("Passwort auf eTCG eingeben: "));
        }
        return null; 
    }

    public int askEtcgId(){
        System.out.print("Geben sie die eTCG-Set-ID an, zu welchem Set diese Daten gehören: ");
        int etcgId = sc.nextInt();
        sc.nextLine();
        return etcgId; 
    }

    public boolean askRarityCheck(){
        System.out.print("Handelt es sich um ein Set wo Rarity-Check nicht notwendig ist? ");
        return confirmationOnly(); 
    }

    public void tryAgain(boolean longAnswer){
        System.out.println("Eingabe nicht verstanden. Erneuter Versuch. ");
        if(longAnswer){
            System.out.print("(Tippe Ja{j,y oder keine Eingabe} oder Nein{n}): ");
        }
    }

    public void close(){
        sc.close();
    }

    public void errorNoCategoryFound(){
        System.out.println("Konnte Karte nicht einlesen: keine category-Spalte im gelesenen gefunden");
    }

    public void errorUrlReadFailure(String url){
        System.out.println("Fehler beim einlesen der Website " + url);
    }

    public void warnErrata(String id, String engName){
        //Bei Errata, wo der Effekt hart verändert wird, warnen
        System.out.println("ACHTUNG: bei der Karte " + id + ": " + engName + "gibt es einen Functional Errata. \nHier muss der Text manuell aktualisiert werden. ");
    }
}