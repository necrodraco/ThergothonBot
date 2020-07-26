package de.etcg.thergothonbot.control;

import de.etcg.thergothonbot.model.card.Card; 
import de.etcg.thergothonbot.model.card.Monster;
import de.etcg.thergothonbot.model.card.EffectType;
import de.etcg.thergothonbot.model.card.LinkArrow; 

import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.Connection.Method; 
import org.jsoup.Connection.Response;  
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.Console;  

import java.util.Scanner;
import java.util.concurrent.TimeUnit; 
import java.util.List; 
import java.util.ArrayList; 
import java.util.Map; 
import java.util.HashMap; 

import java.io.File; 
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException; 
//import java.lang.InterruptedException; 

public class Writer{
    private static Writer writer; 
    private CardFactory cardFactory; 
    JSONParser jsonParser; 
    private List<Card> cardList;

    private Map<String, String> mapRequest;
    private Map<String, String> mapCookies;

    private String host; 
    private Scanner sc; 

    private boolean skipReprintCheck; 

    private Writer(Scanner sc){
        cardFactory = CardFactory.getInstance();
        jsonParser = new JSONParser();
        cardList = new ArrayList<Card>(); 
        mapRequest = new HashMap<String, String>(); 
        mapCookies = null; 
        host = "https://www.etcg.de"; 
        this.sc = sc; 
    }

    public static Writer getInstance(Scanner sc){
        if(writer == null)
            writer = new Writer(sc); 
        return writer; 
    }

    public void readJSONToCard(){
        File directoryPath = new File("./JSON");
        FilenameFilter textFilefilter = new FilenameFilter(){
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (lowercaseName.endsWith(".json")) {
                    return true;
                } else {
                    return false;
                }
            }
        };
        String filesList[] = directoryPath.list(textFilefilter);
        
        for(String fileName : filesList){
            try (FileReader reader = new FileReader("./JSON/" + fileName))
            {
                //Read JSON file
                cardList.add(cardFactory.buildCard((JSONObject) jsonParser.parse(reader)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    public void write(){
        System.out.print("Handelt es sich um ein Set wo Rarity-Check nicht notwendig ist? ");
        skipReprintCheck = confirmationOnly(); 
        handleLogin();
        
        for(Card card : cardList){
            boolean sucessful = uploadCard(card); 
            //System.out.println();

            mapRequest.clear(); 
            if(sucessful)
                new File("./JSON/" + card.getId() + ".json").delete();
            
            /*try{
                //Eine Verarbeitung alle 5 Sekunden, damit mich etcg nicht irgendwann sperrt
                TimeUnit.SECONDS.sleep(5);
            }catch(InterruptedException e){
                e.printStackTrace();
            }*/
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
        System.out.println(card.toString());
        mapRequest.put("formsent", "1");
        mapRequest.put("ygo_card_attribute_id", "" + card.getType().getType());
        mapRequest.put("name", card.getEngName());
        mapRequest.put("de_name", card.getGerName());
        mapRequest.put("beschreibung", card.getEngText());
        mapRequest.put("de_beschreibung", card.getGerText());
        mapRequest.put("gba", card.getGBA());
        mapRequest.put("printing_ygo_rarity_id", "" + card.getRarityType().getType());
        mapRequest.put("printing_en_edition_id", "" + card.getEtcgId());
        mapRequest.put("printing_kuerzel", "EN");
        mapRequest.put("printing_nummer", card.getNrId());
        mapRequest.put("printing_de_edition_id", "" + card.getEtcgId());
        mapRequest.put("printing_de_kuerzel", "DE");
        mapRequest.put("printing_de_nummer", card.getNrId());
        mapRequest.put("adv_list", "3");
        mapRequest.put("tra_list", "3");

        Map<String,Boolean> effectTypes = new HashMap<String, Boolean>();
        if(card.getType().isMonster()){
            Monster monster = (Monster) card; 
            
            mapRequest.put("ygo_card_type_id", "" + monster.getMonsterType().getType());
            int level = monster.getLevel(); 
            if(level > 0){//Link Monster haben kein level und sonst würde Level 0 eingetragen werden
                mapRequest.put("level", "" + level);
            }else{
                mapRequest.put("level", "");
            }
            mapRequest.put("atk", monster.getAtk());
            String def = monster.getDef();
            if(def != null ) mapRequest.put("def", def);
            for(EffectType type : monster.getEffectTypes()){
                effectTypes.put(type.getType().toLowerCase(), true);
            }
            if(monster.getPendScale() >= 0){
                mapRequest.put("pendelbereich", "" + monster.getPendScale());
                mapRequest.put("pendeleffekt", monster.getEngPendText());
                mapRequest.put("de_pendeleffekt", monster.getGerPendText());
            }else{
                mapRequest.put("pendelbereich", "-");
            }
            for(LinkArrow linkArrow : monster.getLinkArrows()){
                mapRequest.put(linkArrow.getType(), "1");
            }
        }else{
            //Die ganz alten Parameter müssen gesetzt werden, 
            //sonst wird wohl nicht verarbeitet bei ZFs und Skill Cards
            mapRequest.put("ygo_card_type_id", "");
            mapRequest.put("level", "");
            mapRequest.put("atk", "");
            mapRequest.put("def", "");
        }
        for(String effect : EffectType.listOfEffectTypes()){
            if(effectTypes.containsKey(effect)){
                mapRequest.put(effect, "1");
            }else{    
                mapRequest.put(effect, "0");
            }
        }
        if(confirmation()){
            handlePostRequest(host+"/admin/ygo_card_add.php");
            return true;
        }else{
            return false; 
        }
    }

    private boolean uploadReprintCard(Card card){
        String cardName = card.getEngName(); 
        mapRequest.put("cardsearch", cardName);
            
        try{
            Document document = handlePostRequest(host + "/admin/ygo_card_index.php").parse();
            Elements printings = document.select("div.standard_content tr");
            String url = null; 
            boolean doublePrint = false; 
            //Wir durchsuchen alle Printings, und wenn ein Kartenname doppelt vorkommt, wird es nicht verarbeitet, und nur gemeldet
            for(int i = 1; i < printings.size(); i++){
                Elements printing_row = printings.get(i).select("td"); 
                if(printing_row.get(2).text().equals(cardName)){
                    String link = printing_row.get(0).select("a").attr("abs:href");
                    if(url != null){
                        if(!doublePrint){
                            System.out.println("Doppelung gefunden für " + cardName + ": " + url);
                            doublePrint = true; 
                        }
                        System.out.println("Doppelung gefunden für " + cardName + ": " + link);
                    }else{
                        url = link;
                    }
                }
            } 
            if(!doublePrint && url != null){
                mapRequest.put("en_edition_id", "" + card.getEtcgId());
                mapRequest.put("de_kuerzel", "DE");
                mapRequest.put("de_nummer", card.getNrId());
                mapRequest.put("de_edition_id", "" + card.getEtcgId());
                mapRequest.put("kuerzel", "EN");
                mapRequest.put("nummer", card.getNrId());
                mapRequest.put("ygo_rarity_id", "" + card.getRarityType().getType());
                if(!skipReprintCheck) System.out.println(card.toStringAsReprint());
                if(skipReprintCheck || confirmation()){
                    handlePostRequest(url);
                    return true; 
                }
            }else if(!doublePrint){
                //System.out.println(card.toStringAsReprint());
                System.out.println("Keine Karte gefunden für " + cardName);
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        return false; 
    }

    private boolean confirmation(){
        System.out.println("Ist das Korrekt? J/n");
        return confirmationOnly();
    }

    private boolean confirmationOnly(){
        String answer;
        while (true) {
            answer = sc.nextLine().trim().toLowerCase();
            if (answer.equals("") || answer.equals("y") || answer.equals("j")) {
                return true;
            } else if (answer.equals("n")) {
                return false;
            } else {
                System.out.println("Eingabe nicht verstanden(Tippe Ja{J,Y oder keine Eingbae} oder Nein(N))");
            }
        }
    }

    private Response handlePostRequest(String url){
        try{
            Connection connect = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10 * 1000)
                    .method(Method.POST)
                    .data(mapRequest)
                    .followRedirects(true); 
            if(mapCookies != null) connect.cookies(mapCookies); 
            return connect.execute();
        }catch(IOException ioe){
            System.out.println("Exception on Post: " + ioe);
        }
        return null; 
    }

    private void handleLogin(){
        File fileExist = new File("account.json"); 
        String username = null; 
        String password = null; 
        if(fileExist.exists()){
            try (FileReader reader = new FileReader("account.json"))
            {
                JSONObject obj = (JSONObject) jsonParser.parse(reader);
                mapCookies = new HashMap<String, String>();
                mapCookies.put("wcf_cookieHash", (String) obj.get("wcf_cookieHash"));
                mapCookies.put("wcf_userID", (String) obj.get("wcf_userID"));
                mapCookies.put("wcf_password", (String) obj.get("wcf_password"));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            System.out.println("Benutzernamen auf eTCG eingeben: ");
            username = sc.next();
            Console console = System.console();
            if(console != null){
              password = new String(console.readPassword("Passwort auf eTCG eingeben: "));
            }
            
            mapRequest.put("login_nickname", username);  
            mapRequest.put("login_password", password);
            Response response = handlePostRequest(host + "/admin/index.php");
            mapCookies = response.cookies(); 

            JSONObject account = new JSONObject(); 
            account.put("wcf_cookieHash", mapCookies.get("wcf_cookieHash"));
            account.put("wcf_userID", mapCookies.get("wcf_userID"));
            account.put("wcf_password", mapCookies.get("wcf_password"));

            try (FileWriter file = new FileWriter("account.json")) {
                file.write(account.toJSONString());
                file.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mapRequest.clear();
        }
    }
}