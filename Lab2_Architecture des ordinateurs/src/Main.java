import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        // Liste des tickets
        List<Ticket> tickets = new ArrayList<>();

        // Creation d'utilisateurs
        User user1 = new User(1, "Fanta", "fssavane@etu.uqac.ca", "Developer");
        User user2 = new User(2, "Ines", "ilyagoubi@etu.uqac.ca", "Developer");
        // Creation d'un admin
        Admin admin = new Admin(1, "Fanta&Ines", "ilyagoubi&fssavane@etu.uqac.ca");







        boolean running = true;
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        while (running) {
            System.out.println("\n\nMenu Principal:");
            System.out.println("\n1. Je suis la developpeuse Fanta");
            System.out.println("\n2. Je suis la developpeuse Ines");
            System.out.println("\n3. Je suis un administrateaur");
            System.out.println("\n4. Quitter");
            System.out.print("\n\nChoisir une option: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1 -> {
                    System.out.println("\n\nOption 1 Choisie");
                    boolean fantaMenu = true;
                    while (fantaMenu) {
                        System.out.println("\n\nMenu Fanta:");
                        System.out.println("\n1. Je veux creer un Ticket");
                        System.out.println("\n2. Je veux consulter un Ticket");
                        System.out.println("\n3. Je veux mettre a jour un Ticket");
                        System.out.println("\n4. Retour au menu principal");
                        System.out.print("\n\nChoisir une option: ");
                        int choice1 = scanner.nextInt();

                        switch (choice1) {
                            case 1 -> {
                                System.out.println("\n\nVous avez choisi de creer un Ticket");
                                System.out.print("\nEntrez l'ID du ticket: ");
                                int id = scanner.nextInt();
                                scanner.nextLine(); // consume newline
                                System.out.print("\nEntrez le titre: ");
                                String titre = scanner.nextLine();
                                System.out.print("\nEntrez la description: ");
                                String desc = scanner.nextLine();
                                System.out.print("\nEntrez la priorite: ");
                                String priorite = scanner.nextLine();
                                Ticket newTicket = new Ticket(id, titre, desc, priorite, "Open");
                                user1.createTicket(newTicket);
                                tickets.add(newTicket); // Ajout a la liste
                            }
                            case 2 -> {
                                System.out.println("\n\nVous avez choisi de consulter un Ticket");
                                System.out.print("Entrez l'ID du ticket a consulter: ");
                                int consultId = scanner.nextInt();
                                scanner.nextLine();
                                Ticket found = null;
                                for (Ticket t : tickets) {
                                    if (t != null && t.getId() == consultId) {
                                        found = t;
                                        break;
                                    }
                                }
                                if (found != null) {
                                    user1.viewTicket(found);
                                } else {
                                    System.out.println("Ticket non trouve.");
                                }
                            }
                            case 3 -> {
                                System.out.println("\n\nVous avez choisi de mettre a jour un Ticket");
                                System.out.print("Entrez l'ID du ticket a mettre a jour: ");
                                int updateId = scanner.nextInt();
                                scanner.nextLine();
                                Ticket toUpdate = null;
                                for (Ticket t : tickets) {
                                    if (t.getId() == updateId) {
                                        toUpdate = t;
                                        break;
                                    }
                                }
                                if (toUpdate != null) {
                                    user1.updateTicket(toUpdate);
                                    System.out.print("Ajouter un commentaire: ");
                                    String comment = scanner.nextLine();
                                    toUpdate.addComment(comment);
                                } else {
                                    System.out.println("Ticket non trouve.");
                                }
                            }
                            case 4 -> {
                                System.out.println("\n\nRetour au menu principal");
                                fantaMenu = false;
                            }
                            default -> System.out.println("\n\nOption invalide. Reessayer.");
                        }
                    }
                }
                case 2 -> {
                    System.out.println("\n\nOption 2 Choisie");
                    boolean inesMenu = true;

                    while (inesMenu) {
                        System.out.println("\n\nMenu Ines:");
                        System.out.println("\n1. Je veux creer un Ticket");
                        System.out.println("\n2. Je veux consulter un Ticket");
                        System.out.println("\n3. Je veux mettre a jour un Ticket");
                        System.out.println("\n4. Retour au menu principal");
                        System.out.print("\n\nChoisir une option: ");
                        int choice2 = scanner.nextInt();
                        switch (choice2) {
                            case 1 -> {
                                System.out.println("\n\nVous avez choisi de creer un Ticket");
                                System.out.print("\nEntrez l'ID du ticket: ");
                                int id = scanner.nextInt();
                                scanner.nextLine(); // consume newline
                                System.out.print("\nEntrez le titre: ");
                                String titre = scanner.nextLine();
                                System.out.print("\nEntrez la description: ");
                                String desc = scanner.nextLine();
                                System.out.print("\nEntrez la priorite: ");
                                String priorite = scanner.nextLine();
                                Ticket newTicket = new Ticket(id, titre, desc, priorite, "Open");
                                user2.createTicket(newTicket);
                                tickets.add(newTicket); // Ajout a la liste
                            }
                            case 2 -> {
                                System.out.println("\n\nVous avez choisi de consulter un Ticket");
                                System.out.print("Entrez l'ID du ticket a consulter: ");
                                int consultId = scanner.nextInt();
                                scanner.nextLine();
                                Ticket found = null;
                                for (Ticket t : tickets) {
                                    if (t.getId() == consultId) {
                                        found = t;
                                        break;
                                    }
                                }
                                if (found != null) {
                                    user2.viewTicket(found);
                                } else {
                                    System.out.println("Ticket non trouve.");
                                }
                            }
                            case 3 -> {
                                System.out.println("\n\nVous avez choisi de mettre a jour un Ticket");
                                System.out.print("Entrez l'ID du ticket a mettre a jour: ");
                                int updateId = scanner.nextInt();
                                scanner.nextLine();
                                Ticket toUpdate = null;
                                for (Ticket t : tickets) {
                                    if (t.getId() == updateId) {
                                        toUpdate = t;
                                        break;
                                    }
                                }
                                if (toUpdate != null) {
                                    user2.updateTicket(toUpdate);
                                    System.out.print("Ajouter un commentaire: ");
                                    String comment = scanner.nextLine();
                                    toUpdate.addComment(comment);
                                } else {
                                    System.out.println("Ticket non trouve.");
                                }
                            }
                            case 4 -> {
                                System.out.println("\n\nRetour au menu principal");
                                inesMenu = false;
                            }
                            default -> System.out.println("\n\nOption invalide. Reessayer.");
                        }
                    }
                }
                case 3 -> {
                    System.out.println("\n\nOption 3 Choisie");

                    boolean adminMenu = true;
                    while (adminMenu) {
                        System.out.println("\n\nMenu Admin:");
                        System.out.println("\n1. Je veux assigner un Ticket");
                        System.out.println("\n2. Je veux fermer un Ticket");
                        System.out.println("\n3. Je veux voir tous les Tickets");
                        System.out.println("\n4. Retour au menu principal");
                        System.out.print("\n\nChoisir une option: ");
                        int choice3 = scanner.nextInt();

                        switch (choice3) {
                            case 1 -> {
                                System.out.println("\n\nVous avez choisi d'assigner un Ticket");
                                System.out.print("Entrez l'ID du ticket a assigner: ");
                                int assignId = scanner.nextInt();
                                scanner.nextLine();
                                Ticket toAssign = null;
                                for (Ticket t : tickets) {
                                    if (t.getId() == assignId) {
                                        toAssign = t;
                                        break;
                                    }
                                }
                                if (toAssign != null) {
                                    System.out.print("Entrez l'ID de l'utilisateur (1 pour Fanta, 2 pour Ines): ");
                                    int userId = scanner.nextInt();
                                    User assignee = (userId == 1) ? user1 : (userId == 2) ? user2 : null;
                                    if (assignee != null) {
                                        admin.assignTicket(toAssign, assignee);
                                    } else {
                                        System.out.println("Utilisateur non trouve.");
                                    }
                                } else {
                                    System.out.println("Ticket non trouve.");
                                }
                            }
                            case 2 -> {
                                System.out.println("\n\nVous avez choisi de fermer un Ticket");
                                System.out.print("Entrez l'ID du ticket a fermer: ");
                                int closeId = scanner.nextInt();
                                scanner.nextLine();
                                Ticket toClose = null;
                                for (Ticket t : tickets) {
                                    if (t.getId() == closeId) {
                                        toClose = t;
                                        break;
                                    }
                                }
                                if (toClose != null) {
                                    admin.closeTicket(toClose);
                                } else {
                                    System.out.println("Ticket non trouve.");
                                }
                            }
                            case 3 -> {
                                System.out.println("\n\nVous avez choisi de voir tous les Tickets");
                                admin.viewAllTickets(tickets);
                            }
                            case 4 -> {
                                System.out.println("\n\nRetour au menu principal");
                                adminMenu = false;
                            }
                            default -> System.out.println("\n\nOption invalide. Reessayer.");
                        }
                    }
                }
                case 4 -> {
                    System.out.println("\n\nQuitter...");
                    running = false;
                }
                default -> System.out.println("\n\nOption invalide. Reessayer.");
            }
        }
    }
}
