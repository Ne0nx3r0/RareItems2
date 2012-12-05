package com.gmail.ne0nx3r0.rareitems.item;

public enum ItemRarities{
    COMMON(0),
    UNCOMMON(1),
    RARE(2),
    LEGENDARY(3),
    GODLY(4);

    public int id;
    public String name;

    ItemRarities(int id, String name){
        this.id = id;
        this.name = name;
    }

    ItemRarities(int id){
        this.id = id;
        this.name = this.name().toLowerCase();
    }

    public int getId(){
        return id;
    }
}