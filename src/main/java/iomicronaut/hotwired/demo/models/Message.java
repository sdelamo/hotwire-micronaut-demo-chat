package iomicronaut.hotwired.demo.models;

import io.micronaut.core.annotation.Creator;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.data.annotation.DateCreated;
import io.micronaut.data.annotation.GeneratedValue;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.Relation;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@MappedEntity
public class Message {
    @Id
    @GeneratedValue(GeneratedValue.Type.AUTO)
    private Long id;

    @NonNull
    @NotBlank
    private String content;

    @Nullable
    @Relation(value = Relation.Kind.MANY_TO_ONE)
    private Room room;

    @DateCreated
    @Nullable
    private Instant dateCreated;

    @Creator
    public Message() {
    }

    public Message(@NonNull String content, @Nullable Room room) {
        this.content = content;
        this.room = room;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @NonNull
    public String getContent() {
        return content;
    }

    public void setContent(@NonNull String content) {
        this.content = content;
    }

    @Nullable
    public Instant getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(@Nullable Instant dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Nullable
    public Room getRoom() {
        return room;
    }

    public void setRoom(@Nullable Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", content='" + content + '\'' +
                '}';
    }
}
