import java.util.List;

public class Admin {
    private final int adminID;
    private final String name;
    private final String email;

    public Admin(int adminID, String name, String email) {
        this.adminID = adminID;
        this.name = name;
        this.email = email;
    }

    public void assignTicket(Ticket ticket, User user) {
        ticket.assignTo(user.toString());
        System.out.println(name + " a assigné le ticket " + ticket.getTitle() + " à " + user.toString());
    }

    public void closeTicket(Ticket ticket) {
        ticket.updateStatus("FERMÉ");
        System.out.println(name + " a fermé le ticket: " + ticket.getTitle());
    }

    public List<Ticket> viewAllTickets(List<Ticket> tickets) {
        System.out.println(name + " consulte tous les tickets.");
        for (Ticket t : tickets) {
            System.out.println(t);
        }
        return tickets;
    }

    @Override
    public String toString() {
        return "Admin [id=" + adminID + ", name=" + name + ", email=" + email + "]";
    }
}
