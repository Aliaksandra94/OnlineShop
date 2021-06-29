package com.moroz.test_task.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Getter @Setter @NoArgsConstructor
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;
    @Enumerated
    @ElementCollection(targetClass = Tag.class)
    private Set<Tag> tags = EnumSet.noneOf(Tag.class);
    //@JsonIgnore
    @ManyToMany
    @JoinTable(name = "basket_item_items",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "basket_item_id"))
    private List<BasketItem> basketItems;
    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItems;

    public Item(String name, String description, Tag[] tags) {
        this.name = name;
        this.description = description;
        List<Tag> tagSet = Arrays.asList(tags);
        for (Tag tag: tagSet){
            this.tags.add(tag);
        }
    }
}
