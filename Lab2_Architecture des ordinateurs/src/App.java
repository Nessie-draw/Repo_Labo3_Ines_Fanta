
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

// classe principale
public class App extends JFrame {
    // variables statiques, pas envie d'en faire trop
    private static final JLabel directionLabel1 = new JLabel("Choisissez un role: ");
    private static final JButton adminButton = new JButton("Admin");
    private static final JButton userButton = new JButton("Utilisateur");
    private static final List<Ticket> tickets = new ArrayList<>();

    // helper reflection setters/getters helpers to prefer using Ticket getters/setters if present
    private static void setStringProperty(Object obj, String value, String... methodNames) {
        if (obj == null || value == null) return;
        for (String name : methodNames) {
            try {
                java.lang.reflect.Method m = obj.getClass().getMethod(name, String.class);
                m.invoke(obj, value);
                return;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {
            }
        }
    }

    private static void setIntProperty(Object obj, int value, String... methodNames) {
        if (obj == null) return;
        for (String name : methodNames) {
            try {
                java.lang.reflect.Method m = obj.getClass().getMethod(name, int.class);
                m.invoke(obj, value);
                return;
            } catch (NoSuchMethodException ignored) {
                try {
                    java.lang.reflect.Method m2 = obj.getClass().getMethod(name, Integer.class);
                    m2.invoke(obj, value);
                    return;
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored2) {
                }
            } catch (IllegalAccessException | InvocationTargetException ignored) {
            }
        }
    }

    // main, lance la fenêtre, simple
    public static void main(String[] args) throws Exception{
        // création fenêtre principale
        App window = new App();
        window.setTitle("Menu : Gestion des Tickets");
        window.setSize(400, 150);
        window.setVisible(true);
        window.setLocationRelativeTo(null);

        // layout et ajout des composants de base
        window.setLayout(new FlowLayout());
        window.getContentPane().add(directionLabel1, BorderLayout.CENTER);
        window.getContentPane().add(adminButton);
        window.getContentPane().add(userButton);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // branchements des boutons, suffit comme ça
        adminButton.addActionListener(App::buttonClick);
        userButton.addActionListener(App::buttonClick);
    }

    // tout le comportement quand on clique sur un bouton, ici
    private static void buttonClick(ActionEvent e) {
        // si c'est l'admin, on demande mot de passe et on ouvre un sous-menu
        if (e.getSource() == adminButton) {
            // fenêtre admin basique
            App adminWindow = new App();
            JPasswordField passwordField = new JPasswordField(25);
            JButton loginButton = new JButton("Connexion");

            adminWindow.setTitle("Menu : Gestion des Tickets - Admin");
            adminWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            adminWindow.setSize(350, 180);
            adminWindow.setLocationRelativeTo(null);
            adminWindow.setLayout(new FlowLayout());
            adminWindow.getContentPane().add(new JLabel("Mot de passe:"));
            adminWindow.getContentPane().add(passwordField);
            adminWindow.getContentPane().add(loginButton);
            adminWindow.setVisible(true);

            // gestion de la connexion admin
            loginButton.addActionListener(ae -> {
                String password = new String(passwordField.getPassword());
                if ("admin123".equals(password)) {
                    // on nettoie et on met les actions admin
                    adminWindow.getContentPane().removeAll();
                    adminWindow.getContentPane().setLayout(new GridLayout(4, 1, 5, 5));

                    JButton btn1 = new JButton("Je veux assigner un Ticket");
                    JButton btn2 = new JButton("Je veux fermer un Ticket");
                    JButton btn3 = new JButton("Je veux voir tous les Tickets");
                    JButton btn4 = new JButton("Retour au menu principal");

                    adminWindow.getContentPane().add(btn1);
                    adminWindow.getContentPane().add(btn2);
                    adminWindow.getContentPane().add(btn3);
                    adminWindow.getContentPane().add(btn4);

                    adminWindow.revalidate();
                    adminWindow.repaint();

                    // assigner un ticket, rapide et sale
                    btn1.addActionListener(ae2 -> {
                        try {
                            String idStr = JOptionPane.showInputDialog(adminWindow, "Entrez l'ID du ticket a assigner:");
                            if (idStr == null) return;
                            int id = Integer.parseInt(idStr.trim());

                            String assignee = JOptionPane.showInputDialog(adminWindow, "Entrez le nom de la personne a qui assigner:");
                            if (assignee == null) return;

                            Ticket found = null;
                            for (Ticket t : App.tickets) {
                                if (t != null && t.getId() == id) {
                                    found = t;
                                    break;
                                }
                            }

                            if (found != null) {
                                // prefer setter if exists, otherwise fallback to adding a comment
                                boolean assignedViaSetter = false;
                                try {
                                    setStringProperty(found, assignee, "setAssignee", "setAssignedTo", "setOwner", "setPersonne");
                                    assignedViaSetter = true;
                                } catch (Exception ignored) {
                                }
                                if (!assignedViaSetter) {
                                    found.addComment("Assigned to: " + assignee);
                                }
                                JOptionPane.showMessageDialog(adminWindow, "Ticket " + id + " assigne a " + assignee, "Succes", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(adminWindow, "Ticket non trouve.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(adminWindow, "ID invalide. Veuillez entrer un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    });

                    // fermer un ticket, on met le statut via setter si possible sinon commentaire
                    btn2.addActionListener(ae2 -> {
                        try {
                            String idStr = JOptionPane.showInputDialog(adminWindow, "Entrez l'ID du ticket a fermer:");
                            if (idStr == null) return;
                            int id = Integer.parseInt(idStr.trim());

                            Ticket found = null;
                            for (Ticket t : App.tickets) {
                                if (t != null && t.getId() == id) {
                                    found = t;
                                    break;
                                }
                            }

                            if (found != null) {
                                boolean statusSet = false;
                                try {
                                    setStringProperty(found, "Closed", "setStatus", "setEtat", "setStatut");
                                    statusSet = true;
                                } catch (Exception ignored) {
                                }
                                if (!statusSet) {
                                    found.addComment("Status changed to: Closed");
                                }
                                JOptionPane.showMessageDialog(adminWindow, "Ticket " + id + " ferme.", "Succes", JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(adminWindow, "Ticket non trouve.", "Erreur", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(adminWindow, "ID invalide. Veuillez entrer un nombre entier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                        }
                    });

                    // voir tous les tickets
                    btn3.addActionListener(ae2 -> {
                        if (App.tickets.isEmpty()) {
                            JOptionPane.showMessageDialog(adminWindow, "Aucun ticket disponible.", "Tous les Tickets", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                        StringBuilder sb = new StringBuilder();
                        for (Ticket t : App.tickets) {
                            if (t != null) {
                                sb.append(t.toString()).append("\n\n");
                            }
                        }
                        JTextArea ta = new JTextArea(sb.toString());
                        ta.setEditable(false);
                        ta.setRows(15);
                        ta.setColumns(40);
                        JScrollPane sp = new JScrollPane(ta);
                        JOptionPane.showMessageDialog(adminWindow, sp, "Tous les Tickets", JOptionPane.INFORMATION_MESSAGE);
                    });

                    // on ferme la fenêtre admin
                    btn4.addActionListener(ae2 -> adminWindow.dispose());

                } else {
                    // mot de passe incorrect, effacer le champ
                    JOptionPane.showMessageDialog(adminWindow, "Mot de passe incorrect", "Erreur", JOptionPane.ERROR_MESSAGE);
                    passwordField.setText("");
                }
            });

        // si c'est un utilisateur normal, on ouvre le menu user
        } else if (e.getSource() == userButton) {
            // fenêtre user de base
            App userWindow = new App();
            userWindow.setTitle("Menu : Gestion des Tickets - Utilisateur");
            userWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            userWindow.setSize(350, 160);
            userWindow.setLocationRelativeTo(null);
            userWindow.setLayout(new FlowLayout());

            // utilisateurs pré-définis
            java.util.ArrayList<String> users = new java.util.ArrayList<>();
            users.add("Ines");
            users.add("Fanta");

            JComboBox<String> userCombo = new JComboBox<>(users.toArray(String[]::new));
            JButton selectButton = new JButton("Valider");

            userWindow.getContentPane().add(new JLabel("Selectionnez votre utilisateur:"));
            userWindow.getContentPane().add(userCombo);
            userWindow.getContentPane().add(selectButton);
            userWindow.setVisible(true);

            // validation de l'utilisateur sélectionné
            selectButton.addActionListener(ae -> {
                String selected = (String) userCombo.getSelectedItem();
                if (selected != null && !selected.isEmpty()) {

                    // on remplace par le menu utilisateur
                    userWindow.getContentPane().removeAll();
                    userWindow.getContentPane().setLayout(new GridLayout(4, 1, 5, 5));

                    JButton btn1 = new JButton("Creer un Ticket");
                    JButton btn2 = new JButton("Consulter un Ticket");
                    JButton btn3 = new JButton("Mettre a jour un Ticket");
                    JButton btn4 = new JButton("Retour au menu principal");

                    userWindow.getContentPane().add(btn1);
                    userWindow.getContentPane().add(btn2);
                    userWindow.getContentPane().add(btn3);
                    userWindow.getContentPane().add(btn4);

                    userWindow.revalidate();
                    userWindow.repaint();

                    // creation d'un ticket, on demande les champs
                    btn1.addActionListener(ae2 -> {
                        try {
                            String idStr = JOptionPane.showInputDialog(userWindow, "Entrez l'ID du ticket:");
                            if (idStr == null) return; // annulé
                            int id = Integer.parseInt(idStr.trim());

                            String titre = JOptionPane.showInputDialog(userWindow, "Entrez le titre:");
                            if (titre == null) return;

                            String desc = JOptionPane.showInputDialog(userWindow, "Entrez la description:");
                            if (desc == null) return;

                            String priorite = JOptionPane.showInputDialog(userWindow, "Entrez la priorite:");
                            if (priorite == null) return;

                            // Try to use setters from Ticket if available, otherwise fallback to constructor
                            try {
                                Object obj = Ticket.class.getDeclaredConstructor().newInstance();
                                setIntProperty(obj, id, "setId", "setID");
                                setStringProperty(obj, titre, "setTitle", "setTitre", "setNom");
                                setStringProperty(obj, desc, "setDescription", "setDesc", "setDescriptionFR");
                                setStringProperty(obj, priorite, "setPriority", "setPriorite");
                                setStringProperty(obj, "Open", "setStatus", "setEtat", "setStatut");
                                Ticket newTicket = (Ticket) obj;
                                App.tickets.add(newTicket);
                            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                                // fallback: use existing constructor if default not available
                                Ticket newTicket = new Ticket(id, titre, desc, priorite, "Open");
                                App.tickets.add(newTicket);
                            }

                            JOptionPane.showMessageDialog(userWindow,
                                    "Ticket cree avec succes (ID: " + id + ")",
                                    "Succes",
                                    JOptionPane.INFORMATION_MESSAGE);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(userWindow,
                                    "ID invalide. Veuillez entrer un nombre entier.",
                                    "Erreur",
                                    JOptionPane.ERROR_MESSAGE);
                        } catch (HeadlessException ex) {
                            JOptionPane.showMessageDialog(userWindow,
                                    "Erreur lors de la creation du ticket: " + ex.getMessage(),
                                    "Erreur",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    });

                    // consulter un ticket, on l'affiche et propose commentaires/attachments
                    btn2.addActionListener(ae2 -> {
                        System.out.println("\nConsulter un Ticket");
                        try {
                            String idStr = JOptionPane.showInputDialog(userWindow, "Entrez l'ID du ticket a consulter:");
                            if (idStr == null) return; // annulé
                            int consultId = Integer.parseInt(idStr.trim());

                            Ticket found = null;
                            for (Ticket t : App.tickets) {
                                if (t != null && t.getId() == consultId) {
                                    found = t;
                                    break;
                                }
                            }

                            if (found != null) {
                                JTextArea ta = new JTextArea(found.toString());
                                ta.setEditable(false);
                                ta.setRows(15);
                                ta.setColumns(40);
                                JScrollPane sp = new JScrollPane(ta);
                                JOptionPane.showMessageDialog(userWindow, sp, "Ticket " + consultId, JOptionPane.INFORMATION_MESSAGE);

                                int showComments = JOptionPane.showConfirmDialog(userWindow,
                                        "Voulez-vous afficher les commentaires du ticket ?",
                                        "Commentaires",
                                        JOptionPane.YES_NO_OPTION);
                                if (showComments == JOptionPane.YES_OPTION) {
                                    try {
                                        java.lang.reflect.Method m = found.getClass().getMethod("getComments");
                                        Object res = m.invoke(found);
                                        if (res instanceof java.util.List) {
                                            @SuppressWarnings("unchecked")
                                            java.util.List<Object> comments = (java.util.List<Object>) res;
                                            if (comments.isEmpty()) {
                                                JOptionPane.showMessageDialog(userWindow, "Aucun commentaire.", "Commentaires", JOptionPane.INFORMATION_MESSAGE);
                                            } else {
                                                StringBuilder sb = new StringBuilder();
                                                for (Object c : comments) {
                                                    sb.append(String.valueOf(c)).append("\n");
                                                }
                                                JTextArea taComments = new JTextArea(sb.toString());
                                                taComments.setEditable(false);
                                                taComments.setRows(12);
                                                taComments.setColumns(40);
                                                JOptionPane.showMessageDialog(userWindow, new JScrollPane(taComments), "Commentaires du ticket " + consultId, JOptionPane.INFORMATION_MESSAGE);
                                            }
                                        } else {
                                            JOptionPane.showMessageDialog(userWindow, "La methode getComments() ne renvoie pas une liste.", "Commentaires", JOptionPane.WARNING_MESSAGE);
                                        }
                                    } catch (NoSuchMethodException nsme) {
                                        JOptionPane.showMessageDialog(userWindow, "Pas de commentaire retrouvé.", "Commentaires", JOptionPane.INFORMATION_MESSAGE);
                                    } catch (HeadlessException | IllegalAccessException | IllegalArgumentException | SecurityException | InvocationTargetException ex) {
                                        JOptionPane.showMessageDialog(userWindow, "Impossible de recuperer les commentaires: " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                                    }
                                }

                                int openAttachments = JOptionPane.showConfirmDialog(userWindow,
                                        "Voulez-vous ouvrir le dossier des pieces jointes (attachments) ?",
                                        "Ouvrir dossier",
                                        JOptionPane.YES_NO_OPTION);
                                if (openAttachments == JOptionPane.YES_OPTION) {
                                    java.io.File attachmentsDir = new java.io.File("attachments");
                                    if (!attachmentsDir.exists() || !attachmentsDir.isDirectory()) {
                                        JOptionPane.showMessageDialog(userWindow, "Aucun dossier 'attachments' trouve.\nLes fichiers attaches sont copies dans un dossier 'attachments' depuis le repertoire courant lorsque vous joignez des fichiers.", "Dossier introuvable", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        try {
                                            if (java.awt.Desktop.isDesktopSupported()) {
                                                java.awt.Desktop.getDesktop().open(attachmentsDir);
                                            } else {
                                                JOptionPane.showMessageDialog(userWindow, "Ouvez manuellement ce dossier :\n" + attachmentsDir.getAbsolutePath(), "Ouvrir dossier", JOptionPane.INFORMATION_MESSAGE);
                                            }
                                        } catch (HeadlessException | IOException ex) {
                                            JOptionPane.showMessageDialog(userWindow, "Impossible d'ouvrir le dossier: " + ex.getMessage() + "\nChemin: " + attachmentsDir.getAbsolutePath(), "Erreur", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                }

                            } else {
                                JOptionPane.showMessageDialog(userWindow,
                                        "Ticket non trouve.",
                                        "Consulter",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(userWindow,
                                    "ID invalide. Veuillez entrer un nombre entier.",
                                    "Erreur",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    });

                    // mise à jour d'un ticket, on ajoute commentaire et attache si besoin
                    btn3.addActionListener(ae2 -> {
                        System.out.println("\nMettre a jour un Ticket");
                        try {
                            String idStr = JOptionPane.showInputDialog(userWindow, "Entrez l'ID du ticket a mettre a jour:");
                            if (idStr == null) return; // annule
                            int updateId = Integer.parseInt(idStr.trim());

                            Ticket toUpdate = null;
                            for (Ticket t : App.tickets) {
                                if (t != null && t.getId() == updateId) {
                                    toUpdate = t;
                                    break;
                                }
                            }

                            if (toUpdate != null) {
                                JOptionPane.showMessageDialog(userWindow,
                                        "Ticket trouve:\n" + toUpdate.toString(),
                                        "Mise a jour",
                                        JOptionPane.INFORMATION_MESSAGE);

                                String comment = JOptionPane.showInputDialog(userWindow, "Ajouter un commentaire (laisser vide pour ne pas ajouter) :");
                                if (comment != null && !comment.trim().isEmpty()) {
                                    toUpdate.addComment(comment.trim());
                                }

                                int attachChoice = JOptionPane.showConfirmDialog(userWindow,
                                        "Voulez-vous ajouter des fichiers au commentaire ?",
                                        "Joindre fichiers",
                                        JOptionPane.YES_NO_OPTION);
                                if (attachChoice == JOptionPane.YES_OPTION) {
                                    javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();
                                    chooser.setMultiSelectionEnabled(true);
                                    chooser.setFileSelectionMode(javax.swing.JFileChooser.FILES_ONLY);
                                    chooser.setCurrentDirectory(new java.io.File(System.getProperty("user.home")));
                                    int res = chooser.showOpenDialog(userWindow);
                                    if (res == javax.swing.JFileChooser.APPROVE_OPTION) {
                                        java.io.File[] files = chooser.getSelectedFiles();
                                        java.io.File attachmentsDir = new java.io.File("attachments");
                                        if (!attachmentsDir.exists()) {
                                            attachmentsDir.mkdirs();
                                        }
                                        StringBuilder attachedNames = new StringBuilder();
                                        for (java.io.File f : files) {
                                            java.nio.file.Path src = f.toPath();
                                            java.nio.file.Path dest = attachmentsDir.toPath().resolve(f.getName());
                                            try {
                                                java.nio.file.Files.copy(src, dest, java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                                                toUpdate.addComment("Attached file: " + dest.toString());
                                                if (attachedNames.length() > 0) attachedNames.append(", ");
                                                attachedNames.append(dest.getFileName().toString());
                                            } catch (java.io.IOException ex) {
                                                JOptionPane.showMessageDialog(userWindow,
                                                        "Erreur lors de la copie du fichier '" + f.getName() + "': " + ex.getMessage(),
                                                        "Erreur",
                                                        JOptionPane.ERROR_MESSAGE);
                                            }
                                        }
                                        if (attachedNames.length() > 0) {
                                            JOptionPane.showMessageDialog(userWindow,
                                                    "Fichiers attaches: " + attachedNames.toString(),
                                                    "Succes",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                        } else {
                                            JOptionPane.showMessageDialog(userWindow,
                                                    "Aucun fichier n'a ete attache.",
                                                    "Info",
                                                    JOptionPane.INFORMATION_MESSAGE);
                                        }
                                    }
                                }

                                // confirmation
                                JOptionPane.showMessageDialog(userWindow,
                                        "Ticket mis a jour avec succes.",
                                        "Succes",
                                        JOptionPane.INFORMATION_MESSAGE);
                            } else {
                                JOptionPane.showMessageDialog(userWindow,
                                        "Ticket non trouve.",
                                        "Erreur",
                                        JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(userWindow,
                                    "ID invalide. Veuillez entrer un nombre entier.",
                                    "Erreur",
                                    JOptionPane.ERROR_MESSAGE);
                        }

                    });

                    // retour au menu principal
                    btn4.addActionListener(ae2 -> {
                        System.out.println("\n4. Retour au menu principal");
                        userWindow.dispose();
                    });
                } else {
                    // aucun utilisateur sélectionné
                    JOptionPane.showMessageDialog(userWindow, "Veuillez selectionner un utilisateur.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            });

        }

    }
}