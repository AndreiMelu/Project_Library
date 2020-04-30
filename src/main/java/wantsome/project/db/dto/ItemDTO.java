package wantsome.project.db.dto;

import java.util.Objects;


public class ItemDTO {
    private int itemId;
    private String name;
    private String author;
    private State state;
    private Type type;

    public ItemDTO(int itemId, String name, String author, State state, Type type) {
        this.itemId = itemId;
        this.type = type;
        this.name = name;
        this.author = author;
        this.state = state;
    }

    public ItemDTO(int itemId, String name, State state, Type type) {
        this.itemId = itemId;
        this.type = type;
        this.name = name;
        this.state = state;
    }


    //Getters
    public int getItemId() {
        return itemId;
    }

    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public State getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemDTO)) return false;
        ItemDTO itemsDTO = (ItemDTO) o;
        return getItemId() == itemsDTO.getItemId() &&
                Objects.equals(getName(), itemsDTO.getName()) &&
                Objects.equals(getAuthor(), itemsDTO.getAuthor()) &&
                getState() == itemsDTO.getState() &&
                getType() == itemsDTO.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItemId(), getName(), getAuthor(), getState(), getType());
    }


    @Override
    public String toString() {
        return "ItemDTO{ " +
                "item_id=" + itemId +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", state=" + state +
                ", type=" + type +
                '}';
    }
}
