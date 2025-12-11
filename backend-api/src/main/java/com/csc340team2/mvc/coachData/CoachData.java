package com.csc340team2.mvc.coachData;

import com.csc340team2.mvc.account.Account;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name="coach_data")
public class CoachData {
    @Id
    private Long id;
    @MapsId
    @OneToOne(targetEntity = Account.class)
    @PrimaryKeyJoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;
    @Column(nullable = true)
    private UUID imageName;
    @Column(nullable = true)
    private String description;
    @Column(nullable = true)
    private Short favoriteColors = 0;
    @Column(nullable = true)
    private Integer pricePerMinute = 0;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public UUID getImageName() {
        return imageName;
    }

    public void setImageName(UUID imageName) {
        this.imageName = imageName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public short getFavoriteColors() {
        return favoriteColors;
    }

    public void setFavoriteColors(short favoriteColors) {
        this.favoriteColors = favoriteColors;
    }

    public int getPricePerMinute() {
        return pricePerMinute;
    }

    public void setPricePerMinute(int pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }
}
