package com.moroz.test_task.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Basket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private User user;
    @OneToMany(mappedBy = "basket")
    private List<BasketItem> basketItems;

    public Basket(User user, List<BasketItem> basketItems) {
        this.user = user;
        this.basketItems = basketItems;
    }
}

