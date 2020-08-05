package de.etcg.thergothonbot.control; 

import de.etcg.thergothonbot.model.card.Card; 
import de.etcg.thergothonbot.model.card.Monster; 
import de.etcg.thergothonbot.model.card.CardType;
import de.etcg.thergothonbot.model.card.MonsterType; 
import de.etcg.thergothonbot.model.card.EffectType; 
import de.etcg.thergothonbot.model.card.RarityType; 
import de.etcg.thergothonbot.model.card.LinkArrow;  

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.json.simple.JSONObject;

import java.io.IOException;

public class CardFactory{

    private static CardFactory cardFactory; 

    private CardFactory(){}

    public static CardFactory getInstance(){
        if(cardFactory == null)
            cardFactory = new CardFactory(); 
        return cardFactory; 
    }

    public Card buildCard(String id, int etcgId, String category, String engName, RarityType rarity, Element card_datas, boolean reprint) throws IOException{
        Card card = new Card(id, etcgId);
        card.setEngName(engName);
        card.setRarityType(rarity);

        //Extradeckmonster haben in ihrer ersten Zeile ihre beschwoerungsbedingung.
        //ohne convertierung wird diese sonst in die Effekttextzeile geworfen
        card_datas.select("br").append("\n");
        
        Element texts = card_datas.select("div.card-table-columns div.lore").get(0); 
        String engPendText = null;
        Elements texte = texts.select("dd"); //Texte aufteilen in Pendel und Monstereffekt
        //Oder bei Skill Cards in Skill und nicht-Skill

        //Englischen Text holen
        if(texts.text().contains("Pendulum Effect")){
            engPendText = texte.get(0).wholeText(); 
            card.setEngText(texte.get(1).wholeText());
        }else if(texte.size() == 2){//Skill Cards haben 2 dd: 1. ist activation requirement(die wir nicht einbauen) und 2. ist Skill
            card.setEngText(texte.get(1).wholeText());
        }else{
            card.setEngText(texts.wholeText());
        }
        
        //gba-code holen und parallel paar andere Dinge für später
        String monster_attributes = null; 
        String monster_types = null; 
        String atk_def = null; 
        String level = null; 
        String gerPendText = null; 
        String pendScale = null; 
        String linkArrows = null; 
        Elements card_specific_datas = card_datas.select("div.infocolumn tr"); 
        for(Element card_data : card_specific_datas){
            Elements headerColumn = card_data.select("th"); 
            if(headerColumn.size() > 0){
                String header = headerColumn.get(0).text(); 
                String text = card_data.select("td").get(0).text();
                if(header.equals("Password")){
                    card.setGBA(text);
                }else if(header.equals("Attribute")){
                    monster_attributes = text.toLowerCase();
                }else if(header.equals("Type") || header.equals("Types")){
                    monster_types = text.toLowerCase();
                }else if(header.equals("ATK / DEF") || header.equals("ATK / LINK")){
                    atk_def = text; 
                }else if(header.equals("Level") || header.equals("Rank")){
                    level = text; 
                }else if(header.equals("Pendulum Scale")){
                    pendScale = text; 
                }else if(header.equals("Link Arrows")){
                    linkArrows = text; 
                }
            }
        }

        //Deutschen Namen und Text holen
        Elements gerList = card_datas.select("table.wikitable tr td[lang=de]");
        card.setGerName(gerList.get(0).text()); 
        if(gerList.size() > 2){
            gerPendText = gerList.get(1).text();
            card.setGerText(gerList.get(2).wholeText());
        }else{
            card.setGerText(gerList.get(1).wholeText());
        }
        
        if(category.contains("monster")){//Monster haben wieder Sonderbehandlung... igitt
            card = new Monster(card, category + "_" + monster_types + "_"+ monster_attributes, atk_def, level, engPendText, gerPendText, pendScale, linkArrows);
        }else{//Für alle nicht-Monster den einfachen "Search" benutzen
            card.setType(CardType.getCardType(category)); 
        }
        card.setReprint(reprint);
        return card; 
    }

    public Card buildCard(JSONObject obj){
        String id = (String) obj.get("id");
        int etcgId = ((Long) obj.get("etcgId")).intValue();
        Card card = new Card(id, etcgId);
        card.setReprintURL((String) obj.get("reprintURL"));
        card.setReprint(((Boolean) obj.get("reprint")).booleanValue());
        card.setGerName((String) obj.get("gerName"));
        card.setGerText((String) obj.get("gerText")); 
        card.setEngName((String) obj.get("engName"));
        card.setEngText((String) obj.get("engText"));
        card.setGBA((String) obj.get("gba"));
        card.setRarityType(RarityType.getRarityType(((Long) obj.get("rarity")).intValue())); 
        CardType cardType = CardType.getCardType(((Long) obj.get("CardType")).intValue()); 
        card.setType(cardType);
        
        if( cardType.isMonster()){
            Monster monster = new Monster(card);
            monster.setMonsterType(MonsterType.getMonsterType(((Long) obj.get("MonsterType")).intValue()));
            monster.setEffectTypes(EffectType.getEffectTypes((String) obj.get("EffectTypes"))); 
            monster.setLevel(((Long) obj.get("level")).intValue());
            monster.setAtk((String) obj.get("atk"));
            monster.setDef((String) obj.get("def"));
            monster.setEngPendText((String) obj.get("engPendText"));
            monster.setGerPendText((String) obj.get("gerPendText"));
            monster.setPendScale(((Long) obj.get("pendScale")).intValue());
            monster.setLinkArrows(LinkArrow.getLinkArrows((String) obj.get("linkArrows")));
            card = monster; 
        }
        return card;
    }
}