public class User {
    private final int userID;
    private final String name;
    private final String email;
    private final String role;

    public User(int userID, String name, String email, String role) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.role = role;
    }

    public void createTicket(Ticket ticket) {
        System.out.println(name + " a créé le ticket: " + ticket.getTitle());
    }

    public void viewTicket(Ticket ticket) {
        System.out.println(name + " voit le ticket: " + ticket.getTitle());
        System.out.println(ticket);
    }

    public void updateTicket(Ticket ticket) {
        ticket.setUpdateDate(new java.util.Date());
        System.out.println(name + " a mis à jour le ticket: " + ticket.getTitle());
    }

    @Override
    public String toString() {
        return "User [  id=" + userID + "\n name=" + name + "\n email=" + email + "\n role=" + role + "]";
    }
}
