import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskUserui extends JFrame {

    private List<Task> tasks;
    private JPanel taskListPanel;
    private JTextField taskInputField;
    private JTextField dateFinField;
    private String username;

    public TaskUserui(String username) {
        this.username = username;
        this.tasks = loadTasksFromFile();

        setTitle("Tâches de " + username);
        setSize(700, 550);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        
        JPanel topPanel = new JPanel();
        taskInputField = new JTextField(20);
        dateFinField = new JTextField(10);
        JButton addButton = new JButton("Ajouter une tâche");
        JButton showButton = new JButton("Afficher mes tâches");

        topPanel.add(new JLabel("Tâche :"));
        topPanel.add(taskInputField);
        topPanel.add(new JLabel("Date fin (yyyy-mm-dd):"));
        topPanel.add(dateFinField);
        topPanel.add(addButton);
        topPanel.add(showButton);

        add(topPanel, BorderLayout.NORTH);

        
        taskListPanel = new JPanel();
        taskListPanel.setLayout(new BoxLayout(taskListPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(taskListPanel);
        add(scrollPane, BorderLayout.CENTER);

        
        addButton.addActionListener(e -> {
            String description = taskInputField.getText().trim();
            String dateFinText = dateFinField.getText().trim();

            if (!description.isEmpty() && !dateFinText.isEmpty()) {
                try {
                    LocalDate dateFin = LocalDate.parse(dateFinText);
                    Task newTask = new Task(description, dateFin);
                    tasks.add(newTask);
                    saveTasksToFile();
                    taskInputField.setText("");
                    dateFinField.setText("");
                    refreshTaskList();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Date invalide (format attendu : yyyy-mm-dd)");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            }
        });

        
        showButton.addActionListener(e -> refreshTaskList());

        refreshTaskList();
        setVisible(true);
    }

    private void refreshTaskList() {
        taskListPanel.removeAll();

        if (tasks.isEmpty()) {
            taskListPanel.add(new JLabel("Aucune tâche enregistrée."));
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                JPanel panel = new JPanel(new GridLayout(2, 1));
                JPanel topRow = new JPanel(new BorderLayout());
                JPanel bottomRow = new JPanel(new FlowLayout(FlowLayout.LEFT));

                JLabel label = new JLabel("• " + task.getDescription());
                JButton deleteButton = new JButton("Effacer");

                JCheckBox doneCheckbox = new JCheckBox("Effectué");
                doneCheckbox.setSelected(task.isDone());
                int index = i;

                doneCheckbox.addActionListener(e -> {
                    task.setDone(doneCheckbox.isSelected());
                    saveTasksToFile();
                });

                deleteButton.addActionListener(e -> {
                    tasks.remove(index);
                    saveTasksToFile();
                    refreshTaskList();
                });

                topRow.add(label, BorderLayout.CENTER);
                topRow.add(deleteButton, BorderLayout.EAST);

                bottomRow.add(new JLabel("Ajoutée le : " + task.getDateAjout()));
                bottomRow.add(new JLabel(" | À finir pour : " + task.getDateFin()));
                bottomRow.add(doneCheckbox);

                panel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                panel.add(topRow);
                panel.add(bottomRow);

                taskListPanel.add(panel);
            }
        }

        taskListPanel.revalidate();
        taskListPanel.repaint();
    }

    private void saveTasksToFile() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("taches_" + username + ".dat"))) {
            out.writeObject(tasks);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la sauvegarde.");
        }
    }

    @SuppressWarnings("unchecked")
    private List<Task> loadTasksFromFile() {
        File file = new File("taches_" + username + ".dat");
        if (!file.exists()) return new ArrayList<>();

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Task>) in.readObject();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur lors du chargement des tâches.");
            return new ArrayList<>();
        }
    }
}
