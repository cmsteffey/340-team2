package com.csc340team2.mvc.eventSubscription;

public class EventSubscriptionCreationData {
    private long accountId;
    private long eventId;

    //COME BACK TO THIS
    public long getAccountId() {
        return accountId;
    }
    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
    public long getEventId() { return eventId; }
    public void setEventId(long eventId) { this.eventId = eventId;}
}
