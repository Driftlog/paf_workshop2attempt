package sg.edu.nus.iss.paf_workshop2attempt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import sg.edu.nus.iss.paf_workshop2attempt.model.RSVP;

@Controller
@RequestMapping(path="/api")
public class RSVPUIController {

     @GetMapping()
    public String homePage(Model model) {
        RSVP rsvp = new RSVP();
        model.addAttribute("rsvp", rsvp);
        return "addRSVP";
    }

    @GetMapping(path="/updateRSVP") 
    public String updateRSVPPage(Model model){
        RSVP rsvp = new RSVP();
        model.addAttribute("rsvp", rsvp);
        return "updateRSVP";
    }

}
