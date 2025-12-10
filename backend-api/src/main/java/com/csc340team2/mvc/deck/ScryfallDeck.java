package com.csc340team2.mvc.deck;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScryfallDeck {

    private String id;
    private String name;

    @JsonProperty("uri")
    private String url;

    private List<ScryfallCardEntry> entries;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<ScryfallCardEntry> getEntries() {
        return entries;
    }

    public void setEntries(List<ScryfallCardEntry> entries) {
        this.entries = entries;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ScryfallCardEntry {
        private int count;

        @JsonProperty("card_digest")
        private CardDigest cardDigest;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public CardDigest getCardDigest() {
            return cardDigest;
        }

        public void setCardDigest(CardDigest cardDigest) {
            this.cardDigest = cardDigest;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CardDigest {
        private String id;
        private String name;

        @JsonProperty("color_identity")
        private String[] colorIdentity;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String[] getColorIdentity() {
            return colorIdentity;
        }

        public void setColorIdentity(String[] colorIdentity) {
            this.colorIdentity = colorIdentity;
        }
    }
}