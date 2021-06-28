package com.moroz.test_task.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;


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
    @ManyToMany
    @JoinTable(name = "basket_item_items",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "basket_item_id"))
    private List<BasketItem> basketItems;

    public Item(String name, String description, Tag[] tags) {
        this.name = name;
        this.description = description;
        List<Tag> tagSet = Arrays.asList(tags);
        for (Tag tag: tagSet){
            this.tags.add(tag);
        }
    }
}
