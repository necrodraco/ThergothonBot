package de.etcg.thergothonbot.control;

import de.etcg.thergothonbot.model.card.Card; 
import de.etcg.thergothonbot.model.card.Monster;
import de.etcg.thergothonbot.model.card.EffectType;
import de.etcg.thergothonbot.model.card.LinkArrow; 

import java.util.List; 
import java.util.Map; 
import java.util.HashMap; 

import java.io.IOException; 

public class Writer{
    private static Writer writer; 
    private CardFactory cardFactory; 
    private FileHandler fileHandler; 
    private RequestHandler requestHandler; 
    
    private boolean skipReprintCheck; 

    private Writer(){
        cardFactory = CardFactory.getInstance();
        fileHandler = FileHandler.getInstance(); 
        requestHandler = RequestHandler.getInstance();
    }

    public static Writer getInstance(){
        if(writer == null)
            writer = new Writer(); 
        return writer; 
    }

    public void write(){
        List<Card> cardList = fileHandler.readJSONToCard(fileHandler.CHECKED); 
        requestHandler.handleLogin();
        
        for(Card card : cardList){
            if(uploadCard(card))
                fileHandler.deleteCardFile(fileHandler.CHECKED,card);
        }
        cardList.clear();
    }

    private boolean uploadCard(Card card){
        if(card.isReprint()){
            return uploadReprintCard(card);
        }else{
            return uploadNewCard(card);
        }
    }

    private boolean uploadNewCard(Card card){
        requestHandler.addToRequest("formsent", "1");
        requestHandler.addToRequest("ygo_card_attribute_id", "" + card.getType().getType());
        requestHandler.addToRequest("name", card.getEngName());
        requestHandler.addToRequest("de_name", card.getGerName());
        requestHandler.addToRequest("beschreibung", card.getEngText());
        requestHandler.addToRequest("de_beschreibung", card.getGerText());
        requestHandler.addToRequest("gba", card.getGBA());
        requestHandler.addToRequest("printing_ygo_rarity_id", "" + card.getRarityType().getType());
        requestHandler.addToRequest("printing_en_edition_id", "" + card.getEtcgId());
        requestHandler.addToRequest("printing_kuerzel", "EN");
        requestHandler.addToRequest("printing_nummer", card.getNrId());
        requestHandler.addToRequest("printing_de_edition_id", "" + card.getEtcgId());
        requestHandler.addToRequest("printing_de_kuerzel", "DE");
        requestHandler.addToRequest("printing_de_nummer", card.getNrId());
        requestHandler.addToRequest("adv_list", "3");
        requestHandler.addToRequest("tra_list", "3");

        Map<String,Boolean> effectTypes = new HashMap<String, Boolean>();
        if(card.getType().isMonster()){
            Monster monster = (Monster) card; 
            
            requestHandler.addToRequest("ygo_card_type_id", "" + monster.getMonsterType().getType());
            int level = monster.getLevel(); 
            if(level > 0){//Link Monster haben kein level und sonst würde Level 0 eingetragen werden
                requestHandler.addToRequest("level", "" + level);
            }else{
                requestHandler.addToRequest("level", "");
            }
            requestHandler.addToRequest("atk", monster.getAtk());
            String def = monster.getDef();
            if(def != null ) requestHandler.addToRequest("def", def);
            for(EffectType type : monster.getEffectTypes()){
                effectTypes.put(type.getType().toLowerCase(), true);
            }
            if(monster.getPendScale() >= 0){
                requestHandler.addToRequest("pendelbereich", "" + monster.getPendScale());
                requestHandler.addToRequest("pendeleffekt", monster.getEngPendText());
                requestHandler.addToRequest("de_pendeleffekt", monster.getGerPendText());
            }else{
                requestHandler.addToRequest("pendelbereich", "-");
            }
            for(LinkArrow linkArrow : monster.getLinkArrows()){
                requestHandler.addToRequest(linkArrow.getType(), "1");
            }
        }else{
            //Die ganz alten Parameter müssen gesetzt werden, 
            //sonst wird wohl nicht verarbeitet bei ZFs und Skill Cards
            requestHandler.addToRequest("ygo_card_type_id", "");
            requestHandler.addToRequest("level", "");
            requestHandler.addToRequest("atk", "");
            requestHandler.addToRequest("def", "");
        }
        for(String effect : EffectType.listOfEffectTypes()){
            if(effectTypes.containsKey(effect)){
                requestHandler.addToRequest(effect, "1");
            }else{    
                requestHandler.addToRequest(effect, "0");
            }
        }
        requestHandler.handlePostRequest(requestHandler.HOST+"/admin/ygo_card_add.php");
        return true;
    }

    private boolean uploadReprintCard(Card card){
        if(card.getReprintURL() != null){
            requestHandler.addToRequest("en_edition_id", "" + card.getEtcgId());
            requestHandler.addToRequest("de_kuerzel", "DE");
            requestHandler.addToRequest("de_nummer", card.getNrId());
            requestHandler.addToRequest("de_edition_id", "" + card.getEtcgId());
            requestHandler.addToRequest("kuerzel", "EN");
            requestHandler.addToRequest("nummer", card.getNrId());
            requestHandler.addToRequest("ygo_rarity_id", "" + card.getRarityType().getType());
            requestHandler.handlePostRequest(card.getReprintURL());
            return true; 
        }
        return false; 
    }
    
}