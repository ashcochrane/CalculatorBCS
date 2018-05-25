package application.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Job implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String id;
    private String lmt;
    private LocalDate startDate;
    private LocalDate endDate;

    public Job() {
    }

    public Job(String id, String lmt, LocalDate startDate) {
        this.id = id;
        this.lmt = lmt;
        this.startDate = startDate;
    }

    public String getId() {
        return id;
    }

    public String getLmt() {
        return lmt;
    }

    public LocalDate getStartDate() {
        return startDate;

    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setLmt(String lmt) {
        this.lmt = lmt;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    
}
