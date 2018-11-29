package edu.illinois.cs465.cs465project;

import com.google.android.gms.maps.model.Marker;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Event {
    private String name;
    private int numberOfPeople;
    List<String> hashtags = new ArrayList<>();
    private boolean friendsGoing;
    private boolean interetedEvent;
    private boolean privateEvent;
    private String description;
    private int startingHour;
    private int startingMinute;

    Event(String name, int numberOfPeople, int category, String description, int startingHour, int startingMinute){
        this.name = name;
        this.numberOfPeople = numberOfPeople;
        this.interetedEvent = false;
        this.friendsGoing = false;
        this.privateEvent = false;
        this.description = description;
        this.startingHour = startingHour;
        this.startingMinute = startingMinute;
    }

    Event(String name){
        this.name = name;
        this.interetedEvent = false;
        this.friendsGoing = false;
        this.privateEvent = false;
    }

    public String getName(){return this.name;}

    public void setFriendsGoing(boolean bool) {this.friendsGoing = bool;}
    public void setInteretedEvent(boolean bool) {this.interetedEvent = bool;}
    public void setPrivateEvent(boolean bool) {this.privateEvent = bool;}

    public void addHashTag(String hashtag){
        if (!hashtags.contains(hashtag)){
            hashtags.add(hashtag);
        }
    }
    public boolean hasHashTag(String hashtag) {
        return hashtags.contains(hashtag);
    }

    public boolean hasHashTags(List<String> inputHashtags) {
        for (String hashtag : inputHashtags) {
            if (hasHashTag(hashtag))
                return true;
        }
        return false;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isInterested(){ return this.interetedEvent; }

}
