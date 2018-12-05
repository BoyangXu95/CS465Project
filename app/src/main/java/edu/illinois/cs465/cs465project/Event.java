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
    private String owner;

    Event(String name, int numberOfPeople, int category, String description, int startingHour, int startingMinute, String owner){
        this.name = name;
        this.numberOfPeople = numberOfPeople;
        this.interetedEvent = false;
        this.friendsGoing = false;
        this.privateEvent = false;
        this.description = description;
        this.startingHour = startingHour;
        this.startingMinute = startingMinute;
        this.owner = owner;
    }

    Event(String name){
        this.name = name;
        this.interetedEvent = false;
        this.friendsGoing = false;
        this.privateEvent = false;
        this.owner = "UNKNOWN";
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
        for (String curHashTag : hashtags)
            if (curHashTag.equals(hashtag))
                return true;
        return false;
    }

    public boolean hasHashTags(List<String> inputHashtags) {
        if (inputHashtags.isEmpty())
            return true;
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

    public boolean isMine(String user) {
        return user.equals(this.owner);
    }

    public void setOwner(String user) { this.owner = user; }

}
