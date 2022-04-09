package io.micronaut.hotwired.demo.repositories;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import io.micronaut.hotwired.demo.models.Room;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface RoomRepository extends CrudRepository<Room, Long> {

    @NonNull
    Room save(@NonNull @NotBlank String name);

    void update(@Id Long id, @NonNull @NotBlank String name);

    @Join(value = "messages", type = Join.Type.LEFT_FETCH)
    Optional<Room> getById(@NonNull @NotNull Long id);
}
