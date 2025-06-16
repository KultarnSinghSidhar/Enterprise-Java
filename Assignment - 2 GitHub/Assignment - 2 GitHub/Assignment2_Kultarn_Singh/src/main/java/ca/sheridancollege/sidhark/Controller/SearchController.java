package ca.sheridancollege.sidhark.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ca.sheridancollege.sidhark.Beans.Query;

@Controller
public class SearchController {

    private final NamedParameterJdbcTemplate jdbc;

    public SearchController(NamedParameterJdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @GetMapping("/search")
    public String showPage(Model model) {
        model.addAttribute("Query", new Query());
        return "SearchPage";
    }

    @PostMapping("/search")
    public String executeQuery(@ModelAttribute("Query") Query query, Model model) {
        List<Map<String, Object>> results;
        try {
            String sql = query.getQuery().trim().toLowerCase();

            if (sql.startsWith("select")) {
                results = jdbc.queryForList(query.getQuery(), new MapSqlParameterSource());
            } else {
                int updatedRows = jdbc.update(query.getQuery(), new MapSqlParameterSource());
                Map<String, Object> msg = new HashMap<>();
                msg.put("message", updatedRows + " row(s) affected.");
                results = List.of(msg);
            }
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            results = List.of(error);
        }

        model.addAttribute("Query", query);
        model.addAttribute("results", results);
        return "SearchPage";
    }
}