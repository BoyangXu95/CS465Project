package edu.illinois.cs465.cs465project;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.lang.Object;
import 	android.os.CountDownTimer;

public class Event {
    private String name;
    private int numberOfPeople;
    List<String> hashtags = new ArrayList<>();
    private boolean friendsGoing;
    private boolean interetedEvent;
    private boolean privateEvent;
    private String description;
    private int durationHour;
    private String owner;
    private LatLng location;

    Event(String name, int numberOfPeople, String description, int durationHour, String owner){
        this.name = name;
        this.numberOfPeople = numberOfPeople;
        this.interetedEvent = false;
        this.friendsGoing = false;
        this.privateEvent = false;
        this.description = description;
        this.owner = owner;
        this.location = null;
        this.durationHour = durationHour;
    }

    Event(String name){
        this.name = name;
        this.interetedEvent = false;
        this.friendsGoing = false;
        this.privateEvent = false;
        this.owner = "UNKNOWN";
    }

    public LatLng getLocation() { return this.location; }
    public void setDuration(int hour){this.durationHour = hour;}
    public void setLocation(LatLng loc) { this.location = loc; }
    public void setNumberOfPeople(int nums) {this.numberOfPeople = nums;}
    public String getName(){return this.name;}
    public long getDuriation(){ return this.durationHour;}

    public String getDescription(){return this.description;}
    public String getNumberOfPeople(){return Integer.toString(this.numberOfPeople);}
    public List<String> getHashtags() {return this.hashtags;}
    public void setFriendsGoing(boolean bool) {this.friendsGoing = bool;}
    public void setInteretedEvent(boolean bool) {this.interetedEvent = bool;}
    public void setPrivateEvent(boolean bool) {this.privateEvent = bool;}

    public void addHashTag(String hashtag){
        if (!hashtags.contains(hashtag.toLowerCase())){
            hashtags.add(hashtag.toLowerCase());
        }
    }
    public boolean hasHashTag(String hashtag) {
        for (String curHashTag : hashtags)
            if (curHashTag.equals(hashtag.toLowerCase()))
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

    public boolean isPrivateEvent() { return this.privateEvent; }

    public boolean isFriendsGoing() { return this.friendsGoing; }

    public boolean isMine(String user) {
        return user.equals(this.owner);
    }

    public void setOwner(String user) { this.owner = user; }

}
