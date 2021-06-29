package com.moroz.test_task.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class BasketItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    //@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "basket_id")
    private Basket basket;

    @ManyToMany(mappedBy = "basketItems", fetch = FetchType.EAGER)
    private List<Item> items;


    public BasketItem(Basket basket) {
        this.basket = basket;
    }
}
