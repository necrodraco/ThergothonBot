package de.etcg.thergothonbot.control;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.Connection;
import org.jsoup.Connection.Method; 
import org.jsoup.Connection.Response;

import java.util.Map; 
import java.util.HashMap; 

import java.io.IOException;

public class RequestHandler{

    private static RequestHandler requestHandler; 

    private InteractionHandler interactionHandler; 
    private FileHandler fileHandler; 

    private Map<String, String> mapRequest;
    private Map<String, String> mapCookies;
    public static final String HOST = "https://www.etcg.de"; 
    
    private RequestHandler(){
        interactionHandler = InteractionHandler.getInstance();
        fileHandler = FileHandler.getInstance(); 
        mapRequest = new HashMap<String, String>(); 
        mapCookies = null;         
    }

    public static RequestHandler getInstance(){
        if(requestHandler == null)
            requestHandler = new RequestHandler(); 
        return requestHandler;  
    }

    public Document getRequest(String url){
        try{
            return Jsoup.connect(url).get();
        }catch(IOException e){

        }
        return null; 
    }

    public Response handlePostRequest(String url){
        try{
            Connection connect = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0")
                    .timeout(10 * 1000)
                    .method(Method.POST)
                    .data(mapRequest)
                    .followRedirects(true); 
            if(mapCookies != null) connect.cookies(mapCookies); 
            Response resp = connect.execute();
            mapRequest.clear(); 
            return resp;
        }catch(IOException ioe){
            System.out.println("Exception on Post: " + ioe);
        }
        return null; 
    }

    public void handleLogin(){
        if(mapCookies==null){
            mapCookies = fileHandler.readAccount();
            String username = null; 
            String password = null; 
            if(mapCookies == null){
                username = interactionHandler.askUserName();
                password = interactionHandler.askPW(); 
                
                this.addToRequest("login_nickname", username);  
                this.addToRequest("login_password", password);
                Response response = handlePostRequest(HOST + "/admin/index.php");
                mapCookies = response.cookies(); 
                fileHandler.writeAccount(
                    mapCookies.get("wcf_cookieHash"),
                    mapCookies.get("wcf_userID"), 
                    mapCookies.get("wcf_password")
                ); 
            }
        }
    }

    public void addToRequest(String key, String value){
        mapRequest.put(key, value);
    }
}