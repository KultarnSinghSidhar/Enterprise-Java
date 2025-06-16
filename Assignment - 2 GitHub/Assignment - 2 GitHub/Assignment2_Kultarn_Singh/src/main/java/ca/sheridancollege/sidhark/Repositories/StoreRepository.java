package ca.sheridancollege.sidhark.Repositories;

import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ca.sheridancollege.sidhark.Beans.AnimeStore;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class StoreRepository {

    private final NamedParameterJdbcTemplate jdbc;

    private static final String TABLE_NAME = "AnimeStore";
    private static final String STORE_INSERT = "INSERT INTO AnimeStore (Name, Manager, Location, Theme) VALUES (:name, :manager, :location, :theme)";
    private static final String STORE_UPDATE = "UPDATE AnimeStore SET Name = :name, Manager = :manager, Location = :location, Theme = :theme WHERE Id = :id";
    private static final String STORE_DELETE = "DELETE FROM AnimeStore WHERE Id = :id";
    private static final String STORE_SELECT_BY_ID = "SELECT * FROM AnimeStore WHERE Id = :id";

    public void addTestStore() {
        AnimeStore store = AnimeStore.builder()
        		.id(0)
                .name("Neo Tokyo Collectibles")
                .manager("Aiko Nakamura")
                .location("Shibuya, Tokyo")
                .theme("Cyberpunk")
                .build();

        addStore(store);
    }
    
    public ArrayList<String> getStoreNames() {
        ArrayList<AnimeStore> stores = getStores();
        ArrayList<String> names = new ArrayList<>();
        for (AnimeStore s : stores) {
        	names.add(s.getName());
        }
        return names;
    }
    
    private MapSqlParameterSource setParams(AnimeStore store) {
        return new MapSqlParameterSource()
                .addValue("id", store.getId())
                .addValue("name", store.getName())
                .addValue("manager", store.getManager())
                .addValue("location", store.getLocation())
                .addValue("theme", store.getTheme());
    }

    public void addStore(AnimeStore store) {
        jdbc.update(STORE_INSERT, setParams(store));
    }

    public ArrayList<AnimeStore> getStores() {
        String query = "SELECT * FROM " + TABLE_NAME;
        List<AnimeStore> stores = jdbc.query(
                query,
                new MapSqlParameterSource(),
                new BeanPropertyRowMapper<>(AnimeStore.class)
        );
        return new ArrayList<>(stores);
    }

    public void updateStore(AnimeStore store) {
        jdbc.update(STORE_UPDATE, setParams(store));
    }

    public void deleteStoreById(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        jdbc.update(STORE_DELETE, params);
    }

    public AnimeStore getStoreById(int id) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("id", id);
        return jdbc.queryForObject(
                STORE_SELECT_BY_ID,
                params,
                new BeanPropertyRowMapper<>(AnimeStore.class)
        );
    }
}