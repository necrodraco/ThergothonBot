package de.etcg.thergothonbot.control;

import de.etcg.thergothonbot.model.card.Card; 

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.List; 
import java.util.Map; 

import java.io.IOException; 

public class Checker{
    private static Checker checker; 
    private CardFactory cardFactory; 
    private FileHandler fileHandler; 
    private RequestHandler requestHandler; 
    private InteractionHandler interactionHandler; 
    private List<Card> cardList; 
    
    private Checker(){
        cardFactory = CardFactory.getInstance();
        fileHandler = FileHandler.getInstance(); 
        requestHandler = RequestHandler.getInstance();
        interactionHandler = InteractionHandler.getInstance();
    }

    public static Checker getInstance(){
        if(checker == null) 
            checker = new Checker();
        return checker; 
    }

    public void startCheck(){
        cardList = fileHandler.readJSONToCard(fileHandler.JSON); 
        Map<String, String> doubleList = fileHandler.getDoubleList();
        requestHandler.handleLogin();
        boolean skipReprintCheck = interactionHandler.askRarityCheck();              
        boolean addedDouble = false; 
        for(Card card : cardList){
            if(card.isReprint()){
                boolean temp = checkReprintCard(card, skipReprintCheck, doubleList);
                if(temp) addedDouble = temp; 
            }else{
                interactionHandler.printCard(card);
                if(interactionHandler.confirmation()){
                    fileHandler.moveCardFile(fileHandler.JSON, fileHandler.CHECKED, card);
                }else{
                    continue;
                }
            }
        }
        if(addedDouble) fileHandler.addDoubleList(doubleList); 
        cardList.clear();
    }

    private boolean checkReprintCard(Card card, boolean skipReprintCheck, Map<String, String> doubleList){
        String cardName = card.getEngName(); 
        boolean doubleAdded = false;     
        try{
            if(doubleList.containsKey(cardName)){
                card.setReprintURL(doubleList.get(cardName));
            }else{
                requestHandler.addToRequest("cardsearch", cardName);
                Document document = requestHandler.handlePostRequest(requestHandler.HOST + "/admin/ygo_card_index.php").parse();
                Elements printings = document.select("div.standard_content tr");
                //Wir durchsuchen alle Printings, und wenn ein Kartenname doppelt vorkommt, wird es nicht verarbeitet, und nur gemeldet
                for(int i = 1; i < printings.size(); i++){
                    Elements printing_row = printings.get(i).select("td"); 
                    if(printing_row.get(2).text().equals(cardName)){
                        String link = printing_row.get(0).select("a").attr("abs:href");
                        if(card.getReprintURL() != null){
                            interactionHandler.doubleFound(card, link);
                            doubleList.put(cardName, card.getReprintURL());
                            doubleAdded = true; 
                        }else{
                            card.setReprintURL(link);
                        }
                    }
                } 
            }
            if(card.getReprintURL() != null){
                if(!skipReprintCheck) interactionHandler.printCard(card);
                if(!skipReprintCheck && interactionHandler.confirmation()){
                    fileHandler.createCardFile(fileHandler.CHECKED, card);
                    fileHandler.deleteCardFile(fileHandler.JSON, card);
                } else if(skipReprintCheck){
                    fileHandler.createCardFile(fileHandler.CHECKED, card);
                    fileHandler.deleteCardFile(fileHandler.JSON, card);
                }
            }else{
                if(interactionHandler.cardNotFound(card)){
                    fileHandler.createCardFile(fileHandler.CHECKED, card);
                    fileHandler.deleteCardFile(fileHandler.JSON, card);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return doubleAdded;  
    }
}