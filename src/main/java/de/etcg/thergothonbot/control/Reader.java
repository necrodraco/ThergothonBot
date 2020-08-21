package de.etcg.thergothonbot.control;

//import de.etcg.thergothonbot.model.card.Card; 
import de.etcg.thergothonbot.model.card.RarityType; 

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
 
//import java.util.List; 
//import java.util.ArrayList; 

import java.io.IOException; 

public class Reader{
    
    private static Reader reader; 
    private CardFactory cardFactory; 
    private FileHandler fileHandler; 
    private RequestHandler requestHandler; 
    private InteractionHandler interactionHandler; 
    
    private Reader(){
        cardFactory = CardFactory.getInstance();
        fileHandler = FileHandler.getInstance(); 
        requestHandler = RequestHandler.getInstance();
        interactionHandler = InteractionHandler.getInstance();
    }

    public static Reader getInstance(){
      if(reader == null)
        reader = new Reader(); 
      return reader; 
    }

    public void read(){
        String url = interactionHandler.askURL();
        int etcgId = interactionHandler.askEtcgId();
        try{
            Document doc = requestHandler.getRequest(url);
            Elements tablerows = doc.select("div.tabbertab tr");
            cards: for (Element row : tablerows) {
              Elements columns = row.select("td");
              if(columns.size() > 0){
                //wenn die erste Spalte keinen Link enthält, wird übersprungen
                if(columns.get(0).select("a").size() == 0)
                    continue cards;

                //Erste Spalte: ID der Karte im Pack
                String id = columns.get(0).select("a").get(0).text();
                String link = columns.get(0).select("a").get(0).attr("abs:href"); 
                //Zweite Spalte: Name der Karte
                String engName = columns.get(1).select("a").get(0).text();

                //Dritte Spalte: Raritaet
                RarityType rarity = RarityType.getRarityType(columns.get(2).select("a").get(0).text().toUpperCase()); 

                //letzte Spalte: Reprint oder neue Karte
                boolean reprint = false;
                String category = null;   
                //Schau nach ob es eine neue Karte oder ein Reprint ist
                for(int i = 3; i < columns.size(); i++){
                  String text = columns.get(i).text();
                  String textlc = text.toLowerCase();
                  if(
                    textlc.contains("monster") 
                    || textlc.contains("spell") 
                    || textlc.contains("trap")
                    || textlc.contains("skill")
                  )
                    category = textlc; 
                  if(textlc.equals("ticket card") || textlc.equals("token"))
                    continue cards;

                  if(text.equals("Reprint") || text.equals("Speed Duel debut") || text.equals("Functional errata")){
                    if(text.equals("Functional errata")) 
                      interactionHandler.warnErrata(id, engName); //Bei Errata, wo der Effekt hart verändert wird, warnen
                    reprint = true;
                  }
                }
                if(category != null){
                  Document doc2 = requestHandler.getRequest(link);
                  Element card_datas = doc2.select("div#mw-content-text div.mw-parser-output").get(0); 
                  fileHandler.addCard(cardFactory.buildCard(id, etcgId, category, engName, rarity, card_datas, reprint));
                }else{
                  interactionHandler.errorNoCategoryFound(); 
                }
              }
            }
        } catch(IOException e) {
            interactionHandler.errorUrlReadFailure(url);
        }
        fileHandler.convertCardsToJSONFiles();    
    }
}