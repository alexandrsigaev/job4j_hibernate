package ru.job4j.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

public class Item {
    private int id;
    private String description;
    private LocalDateTime created;
    private boolean done;

    public Item() {
    }

    public Item(String description, LocalDateTime created, boolean done) {
        this.description = description;
        this.created = created;
        this.done = done;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(int id) {
        this.id = id;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    @JsonProperty("create")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    public LocalDateTime getCreated() {
        return created;
    }

    @JsonProperty("create")
    public void setCreated(LocalDateTime created) {
        if (created != null) {
            this.created = created;
        } else {
            this.created = LocalDateTime.now();
        }
    }

    @JsonProperty("done")
    public boolean isDone() {
        return done;
    }

    @JsonProperty("done")
    public void setDone(boolean done) {
        this.done = done;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id == item.id &&
                Objects.equals(description, item.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
}
