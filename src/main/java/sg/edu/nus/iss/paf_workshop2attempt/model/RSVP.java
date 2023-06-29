package sg.edu.nus.iss.paf_workshop2attempt.model;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RSVP {
    
    private String name;
    private String email;
    private int phone;
    private Date confirmDate;
    private String comment;
}
