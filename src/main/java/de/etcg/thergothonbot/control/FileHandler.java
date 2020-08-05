package de.etcg.thergothonbot.control;

import de.etcg.thergothonbot.model.card.Card; 

import org.json.simple.parser.JSONParser;

import java.util.List;
import java.util.ArrayList; 
import java.util.Map;
import java.util.HashMap;  
import java.util.Iterator;

import org.json.simple.JSONObject;

import java.io.File; 
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.FileReader;

import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.IOException; 

public class FileHandler{
    private static FileHandler fileHandler; 
    
    private CardFactory cardFactory; 

    public static final String JSON = "./JSON"; 
    public static final String CHECKED = "./JSON_CHECKED"; 

    private static final String ACCOUNT = "account.json"; 
    private static final String DOUBLES = "double.json";

    private JSONParser jsonParser; 
    private List<Card> cardList;
       
    private FileHandler(){
        cardFactory = CardFactory.getInstance(); 
        jsonParser = new JSONParser();
        cardList = new ArrayList<Card>(); 
        //Erstelle die Ordner, wenn sie nicht existieren 
        new File(FileHandler.JSON).mkdirs();
        new File(FileHandler.CHECKED).mkdirs();   
    }

    public static FileHandler getInstance(){
        if(fileHandler == null)
            fileHandler = new FileHandler();
        return fileHandler; 
    }

    public void convertCardsToJSONFiles(){
      for(Card card : cardList){
        //Write JSON file
        createCardFile(this.JSON, card);
      }
      cardList.clear();
    }

    public List<Card> readJSONToCard(String path){
        File directoryPath = new File(path);
        if(directoryPath.exists()){
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
                try (FileReader reader = new FileReader("./" + path + "/" + fileName))
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
            return cardList;
        }
        return null; 
    }

    public void createCardFile(String path, Card card){
        try (FileWriter file = new FileWriter("./" + path + "/" + card.getId() + ".json")) {
          file.write(card.toJSON().toJSONString());
          file.flush();
        } catch (IOException e) {
          e.printStackTrace();
        }
    }

    public void moveCardFile(String currPath, String futurePath, Card card){
        new File(currPath + "/" + card.getId() + ".json")
            .renameTo(
                new File(futurePath + "/" + card.getId() + ".json")
            );
    }

    public void deleteCardFile(String path, Card card){
        new File(path + "/" + card.getId() + ".json").delete();
    }

    public void addCard(Card card){
        cardList.add(card);
    }

    public Map<String, String> readAccount(){
        if(new File(this.ACCOUNT).exists()){
            try (FileReader reader = new FileReader(this.ACCOUNT))
            {
                JSONObject obj = (JSONObject) jsonParser.parse(reader);
                Map<String, String> mapCookies = new HashMap<String, String>();
                mapCookies.put("wcf_cookieHash", (String) obj.get("wcf_cookieHash"));
                mapCookies.put("wcf_userID", (String) obj.get("wcf_userID"));
                mapCookies.put("wcf_password", (String) obj.get("wcf_password"));
                return mapCookies; 
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null; 
    }

    public void writeAccount(String wcf_hash, String wcf_id, String wcf_password){
        JSONObject account = new JSONObject(); 
        account.put("wcf_cookieHash", wcf_hash);
        account.put("wcf_userID", wcf_id);
        account.put("wcf_password", wcf_password);

        try (FileWriter file = new FileWriter(this.ACCOUNT)) {
            file.write(account.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Map<String, String> getDoubleList(){
        Map<String, String> doubleList = new HashMap<String, String>();
        if(new File(this.DOUBLES).exists()){
            try (FileReader reader = new FileReader(this.DOUBLES))
            {
                JSONObject obj = (JSONObject) jsonParser.parse(reader);
                for(Iterator iterator = obj.keySet().iterator(); iterator.hasNext();) {
                    String key = (String) iterator.next();
                    doubleList.put(key,(String) obj.get(key));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return doubleList;
    }

    public void addDoubleList(Map<String, String> doubleList){
        new File(this.DOUBLES).delete();
        JSONObject doubles = new JSONObject(); 
        for(Map.Entry<String,String> entry : doubleList.entrySet()){
            doubles.put(entry.getKey(), entry.getValue());
        }
        
        try (FileWriter file = new FileWriter(this.DOUBLES)) {
            file.write(doubles.toJSONString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}