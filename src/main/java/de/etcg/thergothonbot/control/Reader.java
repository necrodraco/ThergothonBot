package de.etcg.thergothonbot.control;

import de.etcg.thergothonbot.model.card.Card; 
import de.etcg.thergothonbot.model.card.RarityType; 

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
 
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.List; 
import java.util.ArrayList; 

import java.io.IOException; 

public class Reader{
    
    private static Reader reader; 
    private CardFactory cardFactory; 
    private List<Card> cardList = new ArrayList<Card>();

    private Reader(){
      cardFactory = CardFactory.getInstance();
    }

    public static Reader getInstance(){
      if(reader == null)
        reader = new Reader(); 
      return reader; 
    }

    public void read(int etcgId, String url){
        System.out.println( "Liest: " + url );
        try{
            Document doc = Jsoup.connect(url).get();
            //System.out.println("Doc-result" + doc.toString());
            Elements tablerows = doc.select("div.tabbertab tr");
            for (Element row : tablerows) {
              //System.out.println("Row of Card: " + row.toString());
              Elements columns = row.select("td");
              if(columns.size() > 0){
                //Erste Spalte: ID der Karte im Pack
                String id = columns.get(0).select("a").get(0).text();
                String link = columns.get(0).select("a").get(0).attr("abs:href"); 
                //Zweite Spalte: Name der Karte
                String engName = columns.get(1).select("a").get(0).text();

                //Dritte Spalte: Raritaet
                RarityType rarity = RarityType.getRarityType(columns.get(2).select("a").get(0).text().toUpperCase()); 

                //letzte Spalte: Reprint oder neue Karte
                boolean reprint = true;
                String category = null;   
                //Schau nach ob es eine neue Karte oder ein Reprint ist
                for(int i = 3; i < columns.size(); i++){
                  Element column = columns.get(i);
                  String text = column.text();
                  //System.out.println("Column of Card: " + column.toString());
                  String textuc = text.toUpperCase();
                  if(
                    textuc.contains("MONSTER") 
                    || textuc.contains("SPELL") 
                    || textuc.contains("TRAP")
                    || textuc.contains("SKILL")
                  )
                    category = textuc; 

                  if(text.equals("New") || text.equals("Reprint") || text.equals("Speed Duel debut")){
                      if(text.equals("New"))//Damit Speed Duel debut unter reprint fällt
                          reprint = false; 
                      break; 
                  }
                }
                if(reprint){ 
                  cardList.add(cardFactory.buildCard(id, etcgId, engName, rarity));
                }else{
                  Document doc2 = Jsoup.connect(link).get();
                  Element card_datas = doc2.select("div#mw-content-text div.mw-parser-output").get(0); 
                  cardList.add(cardFactory.buildCard(id, etcgId, category, engName, rarity, card_datas));
                }
              }
            }
        } catch(IOException e) {
            System.out.println("Fehler beim einlesen der Website " + url);
        }
    }

    public void convertCardsToJSONFiles(){
      for(Card card : cardList){
        //Write JSON file
        try (FileWriter file = new FileWriter("./JSON/" + card.getId() + ".json")) {
          file.write(card.toJSON().toJSONString());
          file.flush();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
}