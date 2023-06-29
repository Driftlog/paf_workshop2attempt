package sg.edu.nus.iss.paf_workshop2attempt.repo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.paf_workshop2attempt.model.RSVP;

@Repository
public class RSVPRepo {
 
    @Autowired
    private JdbcTemplate template;

    private String getAllSQL = "select * from rsvp";

    private String getSQL = "select * from rsvp where full_name = ?";

    private String insertSQL = "insert into rsvp(full_name, email, phone, confirm_date, comment) values (?, ?, ?, ?, ?)";

    private String countRSVPs = "select count(distinct full_name) from rsvp";

    private String updateSQL = "update rsvp set email = ?, phone = ?, confirm_date = ?, comment = ? where email = ?";


    public List<RSVP> getAllRSVP() {
        List<RSVP> rsvps = new ArrayList<>();
        final SqlRowSet rs = template.queryForRowSet(getAllSQL);

        while (rs.next()) {
            RSVP rsvp = new RSVP();
            rsvp.setFullName(rs.getString("full_name"));
            rsvp.setEmail(rs.getString("email"));
            rsvp.setPhone(rs.getInt("phone"));
            rsvp.setConfirmDate(rs.getDate("confirm_date"));
            rsvp.setComment(rs.getString("comment"));

            rsvps.add(rsvp);
        }

        return rsvps;

    }

    public RSVP getRSVP(String name) {
        List<RSVP> rsvp = template.query(getSQL,BeanPropertyRowMapper.newInstance(RSVP.class), name);
        if (rsvp.isEmpty()) {
            return null;
        }

        return rsvp.get(0);
    }

    public boolean insertRSVP(final RSVP rsvp) {
        int status = template.update(insertSQL, rsvp.getFullName(), rsvp.getEmail(), rsvp.getPhone(), rsvp.getConfirmDate(), rsvp.getComment());
        return status > 0;
    }

    public boolean updateRSVP(final RSVP rsvp) { 
        int status = template.update(updateSQL, rsvp.getEmail(), rsvp.getPhone(), rsvp.getConfirmDate(), rsvp.getComment(), rsvp.getEmail());
        return status > 0;
    }

    public int getCount() {
        int count = template.queryForObject(countRSVPs, Integer.class);
        return count;
    }


}
