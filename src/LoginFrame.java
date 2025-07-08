import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {

    private UserManager userManager;

    public LoginFrame() {
        userManager = new UserManager(); 

        setTitle("Connexion");
        setSize(250, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(6, 1, 10, 10));

        
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        
        JButton loginBtn = new JButton("Se connecter");
        JButton registerBtn = new JButton("Créer un compte");

        
        add(new JLabel("Nom d'utilisateur :"));
        add(usernameField);
        add(new JLabel("Mot de passe :"));
        add(passwordField);
        add(loginBtn);
        add(registerBtn);

        
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
