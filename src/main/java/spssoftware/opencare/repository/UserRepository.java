package spssoftware.opencare.repository;

import org.jooq.DSLContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spssoftware.opencare.domain.User;
import spssoftware.opencare.generated.tables.records.UserRecord;

import java.util.List;
import java.util.UUID;

import static spssoftware.opencare.generated.tables.User.USER;

@Component
public class UserRepository {

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);

    private DSLContext connection;

    @Autowired
    public UserRepository(DSLContext connection) {
        this.connection = connection;
    }

    public List<User> list() {
        return connection.selectFrom(USER).fetchInto(User.class);
    }

    public User get(String id) {
        return connection.selectFrom(USER).where(USER.ID.eq(id)).fetchOneInto(User.class);
    }

    public String add(User user) {
        try {

            UserRecord record = new UserRecord();
            user.setId(UUID.randomUUID().toString());
            record.setTitle(user.getTitle());
            record.setFirstName(user.getFirstName());
            record.setMiddleNames(user.getMiddleNames());
            record.setSurname(user.getSurname());
            record.setDateOfBirth(user.getDateOfBirth());
            record.setUsername(user.getUsername());
            record.setPassword(user.getPassword());
            record.setStatus(user.getStatus());
            connection.newRecord(USER, record).store();
            return record.getId();
        } catch (Exception e) {
            logger.error("Failed to add User " + user, e);
            throw new RuntimeException("Failed to add User " + user, e);
        }
    }

    public void update(User user) {
        try {
            connection.update(USER)
                    .set(USER.TITLE, user.getTitle())
                    .set(USER.FIRST_NAME, user.getFirstName())
                    .set(USER.MIDDLE_NAMES, user.getMiddleNames())
                    .set(USER.SURNAME, user.getSurname())
                    .set(USER.DATE_OF_BIRTH, user.getDateOfBirth())
                    .set(USER.USERNAME, user.getUsername())
                    .set(USER.PASSWORD, user.getPassword())
                    .set(USER.STATUS, user.getStatus())
                    .where(USER.ID.eq(user.getId()));
        } catch (Exception e) {
            logger.error("Failed to update User " + user, e);
            throw new RuntimeException("Failed to update User " + user, e);
        }
    }

    public void delete(String id) {
        connection.deleteFrom(USER).where(USER.ID.eq(id));
    }
}
