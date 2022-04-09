package iomicronaut.hotwired.demo.repositories;

import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;
import iomicronaut.hotwired.demo.models.Message;

@JdbcRepository(dialect = Dialect.MYSQL)
public interface MessageRepository extends CrudRepository<Message, Long> {
}
