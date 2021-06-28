package com.moroz.test_task.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class BasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;
    @ManyToMany(mappedBy = "basketItems", fetch = FetchType.EAGER)
    private List<Item> items;


    public BasketItem(Basket basket) {
        this.basket = basket;
    }
}
