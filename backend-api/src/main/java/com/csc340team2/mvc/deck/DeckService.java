package com.csc340team2.mvc.deck;

import com.csc340team2.mvc.account.Account;
import com.csc340team2.mvc.card.Card;
import com.csc340team2.mvc.card.CardRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DeckService {

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private ScryfallClient scryfallClient;

    @Autowired
    private DeckListParser deckListParser;

    @Transactional
    public Deck importDeck(Account user, String scryfallUrl, String nickname) {
        String deckId = extractDeckIdFromUrl(scryfallUrl);
        if (deckId == null) throw new IllegalArgumentException("Invalid Scryfall deck URL");

        ScryfallDeck deckData = scryfallClient.fetchDeck(deckId);

        Deck deck = new Deck();
        deck.setAccount(user);
        deck.setNickname(nickname != null && !nickname.isEmpty() ? nickname : deckData.getName());
        deck.setScryfallUrl(scryfallUrl);
        deck.setColors(parseColors(deckData));

        if (deckData.getEntries() != null && !deckData.getEntries().isEmpty()) {
            deck.setCoverCardUUID(deckData.getEntries().get(0).getCardDigest().getId());

            for (ScryfallDeck.ScryfallCardEntry entry : deckData.getEntries()) {
                Card card = new Card();
                card.setScryfallId(entry.getCardDigest().getId());
                card.setName(entry.getCardDigest().getName());
                card.setQuantity(entry.getCount());

                String[] colors = entry.getCardDigest().getColorIdentity();
                card.setColorIdentity(colors != null ? String.join("", colors) : "");

                try {
                    ScryfallCard fullCard = scryfallClient.fetchCard(entry.getCardDigest().getId());
                    if (fullCard.getImageUris() != null) card.setImageUrl(fullCard.getImageUris().getNormal());
                } catch (Exception e) {
                    card.setImageUrl(null);
                }

                deck.addCard(card);
            }
        } else deck.setCoverCardUUID("");

        return deckRepository.save(deck);
    }

    @Transactional
    public Deck importDeckFromPlainText(Account user, String plainText, String nickname) {
        if (plainText == null || plainText.trim().isEmpty()) {
            throw new IllegalArgumentException("Deck list cannot be empty");
        }

        List<DeckListParser.CardEntry> cardEntries = deckListParser.parseDeckList(plainText);

        if (cardEntries.isEmpty()) {
            throw new IllegalArgumentException("No valid cards found in deck list");
        }

        Deck newDeck = new Deck();
        newDeck.setAccount(user);
        newDeck.setNickname(nickname);
        newDeck.setScryfallUrl("");

        short deckColors = 0;
        String firstCardId = null;

        for (DeckListParser.CardEntry entry : cardEntries) {
            try {
                ScryfallCard scryfallCard;

                if (entry.hasSetInfo()) {
                    scryfallCard = scryfallClient.fetchCardBySetAndNumber(
                            entry.getSetCode(),
                            entry.getCollectorNumber()
                    );
                } else {
                    scryfallCard = scryfallClient.searchCardByName(entry.getCardName());
                }

                Card card = new Card();
                card.setScryfallId(scryfallCard.getUuid());
                card.setName(scryfallCard.getName());
                card.setQuantity(entry.getQuantity());
                card.setColorIdentity(scryfallCard.getColorIdentityAsString());

                if (scryfallCard.getImageUris() != null) {
                    card.setImageUrl(scryfallCard.getImageUris().getNormal());
                }

                newDeck.addCard(card);

                if (scryfallCard.getColorIdentity() != null) {
                    for (String color : scryfallCard.getColorIdentity()) {
                        deckColors |= colorToShort(color);
                    }
                }

                if (firstCardId == null) {
                    firstCardId = scryfallCard.getUuid();
                }

                scryfallClient.respectRateLimit();

            } catch (HttpClientErrorException e) {
                System.err.println("Card not found: " + entry.getCardName() + " - " + e.getMessage());
            }
        }

        newDeck.setColors(deckColors);
        newDeck.setCoverCardUUID(firstCardId != null ? firstCardId : "");

        return deckRepository.save(newDeck);
    }

    @Transactional
    public Card addCardToDeck(Long deckId, Card card) {
        Deck deck = deckRepository.findById(deckId).orElseThrow(IllegalArgumentException::new);
        deck.addCard(card);
        return cardRepository.save(card);
    }

    private Short parseColors(ScryfallDeck deckData) {
        short colors = 0;
        if (deckData.getEntries() == null) return colors;

        for (ScryfallDeck.ScryfallCardEntry entry : deckData.getEntries()) {
            if (entry.getCardDigest() == null || entry.getCardDigest().getColorIdentity() == null) continue;
            for (String c : entry.getCardDigest().getColorIdentity()) {
                colors |= colorToShort(c);
            }
        }
        return colors;
    }

    private short colorToShort(String color) {
        return switch (color) {
            case "W" -> 16;
            case "U" -> 8;
            case "B" -> 4;
            case "R" -> 2;
            case "G" -> 1;
            default -> 0;
        };
    }

    private String extractDeckIdFromUrl(String url) {
        Pattern pattern = Pattern.compile("scryfall\\.com/@[^/]+/decks/([a-zA-Z0-9-]+)");
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) return matcher.group(1);
        if (url.matches("[a-zA-Z0-9-]+")) return url;
        return null;
    }

    public List<Deck> getAllDecksWithUsers() { return deckRepository.getAllWithUsers(); }
    public List<Deck> getAllOwnedBy(Account account) { return deckRepository.getAllByAccount(account); }
    public Optional<Deck> getDeckById(Long id) { return deckRepository.findById(id); }
    @Transactional
    public boolean deleteDeckById(Long id) { return deckRepository.deleteDecksById(id) == 1; }
    public Deck addDeck(Deck deck) { deck.setId(null); return deckRepository.save(deck); }
}