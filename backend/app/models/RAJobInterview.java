package models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import io.ebean.Finder;
import io.ebean.Model;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = RAJobInterview.class)
@ToString
public class RAJobInterview extends Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "rajob_application_id", referencedColumnName = "id")
    private RAJobApplication rajobApplication;

    private String interviewDate;

    private String interviewTime;

    private String location;

    private String interviewType;

    private String notes;

    private String status;

    private String createdTime;

    public RAJobInterview() {}

    public static Finder<Long, RAJobInterview> find = new Finder<>(RAJobInterview.class);
}
