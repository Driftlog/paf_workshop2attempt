package sg.edu.nus.iss.paf_workshop2attempt.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

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

    @PostMapping(path="/rsvp/{email}",
                consumes = "application/x-www-form-urlencoded",
                produces = "application/json")
        public ResponseEntity<Boolean> updateRSVP(Model model, @ModelAttribute(name="rsvp") RSVP rsvp, @PathVariable(name="email") String email) {
            RestTemplate rt = new RestTemplate();
            String url = UriComponentsBuilder
                .fromUriString("http://localhost:4000/api/rsvp/")
                .path(email)
                .toUriString();

            MultiValueMap<String, Object> form = new LinkedMultiValueMap<>();
            form.add("email", rsvp.getEmail());
            form.add("phone", rsvp.getPhone());
            form.add("confirmDate", rsvp.getConfirmDate());
            form.add("comment", rsvp.getComment());


            RequestEntity<MultiValueMap<String, Object>> req = RequestEntity
                                                            .put(url)
                                                            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                                                            .body(form, MultiValueMap.class);

            ResponseEntity<Boolean> resp = rt.exchange(req, Boolean.class);

            if (resp.getBody()) {
                return ResponseEntity.status(201).body(true);
            }

            return ResponseEntity.status(404).body(false);
    }

    @PutMapping(path="/rsvp/{email}",
                consumes = "application/x-www-form-urlencoded",
                produces = "application/json")
    public ResponseEntity<Boolean> updateRSVP(@ModelAttribute(name="rsvp") RSVP rsvp,  @PathVariable(name="email") String name, String email, Model model) {
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
