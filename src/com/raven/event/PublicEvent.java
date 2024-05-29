package com.raven.event;

public class PublicEvent {

    private static PublicEvent instance;
    private EventMain eventMain;
    private EventImageView eventImageView;
    private EventChat eventChat;
    private EventLogin eventLogin;
    private EventMenuLeft eventMenuLeft;
    private EventDownFile eventDownFile;
    private EventBody eventBody;
    private EventProfile eventProfile;
    private EventViewProfile eventViewProfile;
    private EventFile eventFile;

    public EventFile getEventFile() {
        return eventFile;
    }

    public void addEventFile(EventFile eventFile) {
        this.eventFile = eventFile;
    }
    
    

    public EventViewProfile getEventViewProfile() {
        return eventViewProfile;
    }

    public void addEventViewProfile(EventViewProfile eventViewProfile) {
        this.eventViewProfile = eventViewProfile;
    }
    
    
    public EventProfile getEventProfile() {
        return eventProfile;
    }

    public void addEventProfile(EventProfile eventProfile) {
        this.eventProfile = eventProfile;
    }
     
    public EventBody getEventBody() {
        return eventBody;
    }

    public void addEventBody(EventBody eventBody) {
        this.eventBody = eventBody;
    }
   

    public static PublicEvent getInstance() {
        if (instance == null) {
            instance = new PublicEvent();
        }
        return instance;
    }

    private PublicEvent() {

    }
    public void addEventDownFile(EventDownFile event) {
        this.eventDownFile = event;
    }
    
    public void addEventMain(EventMain event) {
        this.eventMain = event;
    }

    public void addEventImageView(EventImageView event) {
        this.eventImageView = event;
    }

    public void addEventChat(EventChat event) {
        this.eventChat = event;
    }

    public void addEventLogin(EventLogin event) {
        this.eventLogin = event;
    }

    public EventMain getEventMain() {
        return eventMain;
    }

    public EventImageView getEventImageView() {
        return eventImageView;
    }

    public EventChat getEventChat() {
        return eventChat;
    }

    public EventLogin getEventLogin() {
        return eventLogin;
    }

    public EventMenuLeft getEventMenuLeft() {
        return eventMenuLeft;
    }

    public void addEventMenuLeft(EventMenuLeft eventMenuLeft) {
        this.eventMenuLeft = eventMenuLeft;
    }
    public EventDownFile getEventDownFile() {
        return eventDownFile;
    }
}
