package de.etcg.thergothonbot.model.card;

import java.util.List; 
import java.util.ArrayList; 

public enum LinkArrow{
    TOP_LEFT("link_arrow_top_left"),
    TOP("link_arrow_top"),
    TOP_RIGHT("link_arrow_top_right"),
    LEFT("link_arrow_left"),
    RIGHT("link_arrow_right"),
    BOTTOM_LEFT("link_arrow_bottom_left"),
    BOTTOM("link_arrow_bottom"),
    BOTTOM_RIGHT("link_arrow_bottom_right")
    ;
    
    private String type; 

    private LinkArrow(String type){
        this.type = type; 
    }

    public static List<LinkArrow> getLinkArrows(String type){
        List<LinkArrow> linkArrows = new ArrayList<LinkArrow>();
        String[] arrows = type.split(", ");
        for(String arrow : arrows){
            if(arrow.equals("Top-Left") || arrow.equals("link_arrow_top_left")){
                linkArrows.add(LinkArrow.TOP_LEFT);
            }else if(arrow.equals("Top-Center") || arrow.equals("link_arrow_top") || arrow.equals("Top")){
                linkArrows.add(LinkArrow.TOP);
            }else if(arrow.equals("Top-Right") || arrow.equals("link_arrow_top_right")){
                linkArrows.add(LinkArrow.TOP_RIGHT);
            }else if(arrow.equals("Middle-Left") || arrow.equals("link_arrow_left") || arrow.equals("Left")){
                linkArrows.add(LinkArrow.LEFT);
            }else if(arrow.equals("Middle-Right") || arrow.equals("link_arrow_right") || arrow.equals("Right")){
                linkArrows.add(LinkArrow.RIGHT);
            }else if(arrow.equals("Bottom-Left") || arrow.equals("link_arrow_bottom_left")){
                linkArrows.add(LinkArrow.BOTTOM_LEFT);
            }else if(arrow.equals("Bottom-Center") || arrow.equals("link_arrow_bottom") || arrow.equals("Bottom")){
                linkArrows.add(LinkArrow.BOTTOM);
            }else if(arrow.equals("Bottom-Right") || arrow.equals("link_arrow_bottom_right")){
                linkArrows.add(LinkArrow.BOTTOM_RIGHT);
            }
        }
        return linkArrows; 
    }

    public String getType() {
       return this.type;
    }

    @Override
    public String toString(){
        return this.type;
    }
}