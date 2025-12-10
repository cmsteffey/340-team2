package com.csc340team2.mvc.deck;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ScryfallCard {

    @JsonProperty("id")
    private String uuid;

    @JsonProperty("color_identity")
    private String[] colorIdentity;

    private String name;

    @JsonProperty("image_uris")
    private ImageUris imageUris;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String[] getColorIdentity() {
        return colorIdentity;
    }

    public void setColorIdentity(String[] colorIdentity) {
        this.colorIdentity = colorIdentity;
    }

    public String getColorIdentityAsString() {
        if (colorIdentity == null || colorIdentity.length == 0) {
            return "";
        }
        return String.join("", colorIdentity);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ImageUris getImageUris() {
        return imageUris;
    }

    public void setImageUris(ImageUris imageUris) {
        this.imageUris = imageUris;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ImageUris {
        private String small;
        private String normal;
        private String large;

        @JsonProperty("art_crop")
        private String artCrop;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getNormal() {
            return normal;
        }

        public void setNormal(String normal) {
            this.normal = normal;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getArtCrop() {
            return artCrop;
        }

        public void setArtCrop(String artCrop) {
            this.artCrop = artCrop;
        }
    }
}