import java.util.Date;

public class Ticket {

    public Ticket(Date creationDate, String description, String priority, int ticketID, String title) {
        this.creationDate = creationDate;
        this.description = description;
        this.priority = priority;
        this.ticketID = ticketID;
        this.title = title;
    }
    public int getId() {
        return ticketID;
    }
    private final int ticketID;
    private final String title;
    private final String description;
    private String status;
    private final String priority;
    private final Date creationDate;
    private Date updateDate;
    private String assignation;

    public Ticket(int ticketID, String title, String description, String priority, String assignation) {
        this.ticketID = ticketID;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.status = "OUVERT";
        this.creationDate = new Date();
        this.updateDate = new Date();
        this.assignation = assignation;
    }

    public void assignTo(String user) {
        this.status = "ASSIGNe";
        this.assignation = user;
        System.out.println("Ticket " + title + " assigne a " + user);
    }
    private final java.util.List<java.io.File> directories = new java.util.ArrayList<>();

    public void updateStatus(String status) {
        this.status = status;
        this.updateDate = new Date();
        System.out.println("Ticket " + title + " mis a jour au statut: " + status);
    }

    public void addDirectory(String path) {
        java.io.File dir = new java.io.File(path);
        directories.add(dir);
        this.updateDate = new Date();
        System.out.println("Dossier ajouté au ticket " + title + ": " + path);
    }

    public java.util.List<java.io.File> getDirectories() {
        return new java.util.ArrayList<>(directories);
    }

    public void addComment(String comment) {
        System.out.println("Commentaire ajoute au ticket " + title + ": " + comment);
    }

    // Getters et Setters utiles
    public String getTitle() {
        return title;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

   @Override
    public String toString() {
        return """
            
            ----- Ticket -----
            ID        : """ + ticketID +
            "\nTitre     : " + title +
            "\nDescription: " + description +
            "\nStatut    : " + status +
            "\nPriorite  : " + priority +
            "\nCree le   : " + creationDate +
            "\nMis a jour: " + updateDate +
            "\n------------------\n";
    }

    public String getAssignation() {
        return assignation;
    }



}
