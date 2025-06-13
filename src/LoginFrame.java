import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private UserManager userManager;

    public LoginFrame() {
        userManager = new UserManager(); // Assure-toi que cette classe existe

        setTitle("Connexion");
        setSize(250, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 10, 10));

        // Champs de saisie
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        // Boutons
        JButton loginBtn = new JButton("Se connecter");
        JButton registerBtn = new JButton("Créer un compte");

        // Ajout des composants à la fenêtre
        add(new JLabel("Nom d'utilisateur :"));
        add(usernameField);
        add(new JLabel("Mot de passe :"));
        add(passwordField);
        add(loginBtn);
        add(registerBtn);

        // Action bouton "Se connecter"
        loginBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (userManager.authenticate(username, password)) {
                JOptionPane.showMessageDialog(this, "Connécté(e)!");
                // Ouvre l'interface principale
                SwingUtilities.invokeLater(() -> new TaskUserui(username));
                dispose(); // ferme la fenêtre de connexion
            } else {
                JOptionPane.showMessageDialog(this, "Échec de la connexion. Vérifiez vos identifiants.");
            }
        });

        // Action bouton "Créer un compte"
        registerBtn.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (userManager.register(username, password)) {
                JOptionPane.showMessageDialog(this, "Compte créé avec succès !");
            } else {
                JOptionPane.showMessageDialog(this, "Nom d'utilisateur déjà utilisé.");
            }
        });

        setVisible(true);
    }
}
