package iomicronaut.hotwired.demo.repositories;

import io.micronaut.core.annotation.NonNull;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import iomicronaut.hotwired.demo.models.Room;

import javax.validation.constraints.NotBlank;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface RoomRepository extends CrudRepository<Room, Long> {

    @NonNull
    Room save(@NonNull @NotBlank String name);
}
