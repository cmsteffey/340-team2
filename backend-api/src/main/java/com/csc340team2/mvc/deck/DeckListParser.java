package com.csc340team2.mvc.deck;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class DeckListParser {

    public List<CardEntry> parseDeckList(String plainText) {
        List<CardEntry> entries = new ArrayList<>();

        if (plainText == null || plainText.trim().isEmpty()) {
            return entries;
        }

        String[] lines = plainText.split("\n");

        Pattern pattern = Pattern.compile("^\\s*(?:(\\d+)x?\\s+)?([^(\\n]+?)(?:\\s*\\(([A-Z0-9]+)\\)\\s*(\\d+[a-z]?)?)?\\s*$", Pattern.CASE_INSENSITIVE);

        for (String line : lines) {
            line = line.trim();

            if (line.isEmpty() || line.startsWith("//") || line.startsWith("#")) {
                continue;
            }

            Matcher matcher = pattern.matcher(line);
            if (matcher.matches()) {
                String quantityStr = matcher.group(1);
                String cardName = matcher.group(2).trim();
                String setCode = matcher.group(3);
                String collectorNumber = matcher.group(4);

                int quantity = quantityStr != null ? Integer.parseInt(quantityStr) : 1;

                CardEntry entry = new CardEntry();
                entry.setQuantity(quantity);
                entry.setCardName(cardName);
                entry.setSetCode(setCode);
                entry.setCollectorNumber(collectorNumber);

                entries.add(entry);
            }
        }

        return entries;
    }

    public static class CardEntry {
        private int quantity;
        private String cardName;
        private String setCode;
        private String collectorNumber;

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public String getCardName() {
            return cardName;
        }

        public void setCardName(String cardName) {
            this.cardName = cardName;
        }

        public String getSetCode() {
            return setCode;
        }

        public void setSetCode(String setCode) {
            this.setCode = setCode;
        }

        public String getCollectorNumber() {
            return collectorNumber;
        }

        public void setCollectorNumber(String collectorNumber) {
            this.collectorNumber = collectorNumber;
        }

        public boolean hasSetInfo() {
            return setCode != null && collectorNumber != null;
        }
    }
}