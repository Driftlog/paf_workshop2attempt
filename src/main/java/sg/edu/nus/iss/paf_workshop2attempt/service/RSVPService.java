package sg.edu.nus.iss.paf_workshop2attempt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.paf_workshop2attempt.model.RSVP;
import sg.edu.nus.iss.paf_workshop2attempt.repo.RSVPRepo;



@Service
public class RSVPService {
    
    @Autowired
    private RSVPRepo repo;

    public Optional<RSVP> getRSVP(String name) {
        return Optional.ofNullable(repo.getRSVP(name));
    }

    public List<RSVP> getAllRSVP() {
        return repo.getAllRSVP();
    }

    public int getCount() {
        return repo.getCount();
    }

    public boolean insertRSVP(RSVP rsvp) {
        return repo.insertRSVP(rsvp);
    }

    public boolean updateRSVP(RSVP rsvp) {
        return repo.updateRSVP(rsvp);
    }
}
