package at.jku.oeh.lan.laganizer.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public interface ClanDAO extends CrudRepository<Clan, Long> {
    Clan findClanByUser(User user);

    Clan findClanByName(String name);

    Clan findClanByClanID(long id);
}