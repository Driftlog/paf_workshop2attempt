package sg.edu.nus.iss.paf_workshop2attempt.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sg.edu.nus.iss.paf_workshop2attempt.exception.RecordNotFoundException;
import sg.edu.nus.iss.paf_workshop2attempt.model.RSVP;
import sg.edu.nus.iss.paf_workshop2attempt.service.RSVPService;

@RestController
@RequestMapping(path="/api")
public class RSVPRestController {
    
    @Autowired
    private RSVPService svc;

    @GetMapping(path="/rsvps",
                produces = "application/json")
    public List<RSVP> getAll() {
        return svc.getAllRSVP();
    }

    @GetMapping(path="/rsvp",
                produces = "application/json")
    public ResponseEntity<RSVP> getRSVPByName(@RequestParam String name) {
        Optional<RSVP> rsvp = svc.getRSVP(name);

        if (rsvp.isEmpty()) {
            throw new RecordNotFoundException("Record not found");
        } else {
            return ResponseEntity.status(201)
                    .body(rsvp.get());
        }
    }

    @PostMapping(path="/rsvp",
                consumes = "application/x-www-form-urlencoded",
                produces = "application/json")
        public ResponseEntity<Boolean> insertRSVP(Model model, @ModelAttribute(name="rsvp") RSVP rsvp) {
            Boolean status =  svc.insertRSVP(rsvp);
            
            if (status) {
                return ResponseEntity.status(201).body(status);
            }

            return ResponseEntity.status(404).body(status);
    }

    @PutMapping(path="/rsvp/{email}",
                consumes = "application/x-www-form-urlencoded",
                produces = "application/json")
    public ResponseEntity<Boolean> updateRSVP(@ModelAttribute(name="rsvp") RSVP rsvp,  @RequestParam String name, String email, Model model) {
            if (svc.updateRSVP(rsvp)) {
                return ResponseEntity.status(201).body(true);
            }
            return ResponseEntity.status(404).body(false);
    }

    @GetMapping(path="/rsvps/count",
                produces = "application/json") 
    public ResponseEntity<Integer> getRSVPCount(){
        int count = svc.getCount();

        if (count != 0) {
            return ResponseEntity.status(201).body(count);
        } else {
            throw  new RecordNotFoundException("Record not found");
        }
    }
}
