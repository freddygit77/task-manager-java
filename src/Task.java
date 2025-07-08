import java.io.Serializable;
import java.time.LocalDate;

public class Task implements Serializable {
    private String description;
    private LocalDate dateAjout;
    private LocalDate dateFin;
    private boolean isDone;

    public Task(String description, LocalDate dateFin) {
        this.description = description;
        this.dateAjout = LocalDate.now(); 
        this.dateFin = dateFin;
        this.isDone = false;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
