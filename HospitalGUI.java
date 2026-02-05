package javaapplication12;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/* ================= PATIENT CLASS ================= */
class Patient {
    String name;
    int age;
    char gender;

    Patient(String name, int age, char gender) {
        this.name = name;
        this.age = age;
        this.gender = gender;
    }
}

/* ================= QUEUE CLASS (DSA) ================= */
class HospitalQueue {
    private int front = 0;
    private int rear = -1;
    private int size;
    private Patient[] arr;

    HospitalQueue(int size) {
        this.size = size;
        arr = new Patient[size];
    }

    boolean isEmpty() {
        return front > rear;
    }

    boolean isFull() {
        return rear == size - 1;
    }

    void addPatient(Patient p) {
        if (isFull()) {
            JOptionPane.showMessageDialog(null, "Queue is Full!");
        } else {
            arr[++rear] = p;
        }
    }

    Patient deletePatient() {
        if (isEmpty()) return null;
        return arr[front++];
    }

    String displayPatients() {
        if (isEmpty()) return "No patients in queue.";
        StringBuilder sb = new StringBuilder();
        for (int i = front; i <= rear; i++) {
            sb.append("Name: ").append(arr[i].name)
              .append(" | Age: ").append(arr[i].age)
              .append(" | Gender: ").append(arr[i].gender)
              .append("\n");
        }
        return sb.toString();
    }

    Patient search(String name) {
        for (int i = front; i <= rear; i++) {
            if (arr[i].name.equalsIgnoreCase(name)) {
                return arr[i];
            }
        }
        return null;
    }
}

/* ================= GUI CLASS ================= */
public class HospitalGUI extends JFrame implements ActionListener {

    HospitalQueue queue = new HospitalQueue(10);

    JTextField nameField, ageField;
    JComboBox<String> genderBox, diseaseBox;
    JTextArea displayArea;

    JButton addBtn, searchBtn, treatBtn, displayBtn;

    public HospitalGUI() {
        setTitle("🏥 Hospital Queue Management System");
        setSize(600, 500);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        /* ---------- TOP PANEL ---------- */
        JLabel title = new JLabel("Hospital Patient Queue", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(Color.BLUE);
        add(title, BorderLayout.NORTH);

        /* ---------- FORM PANEL ---------- */
        JPanel form = new JPanel(new GridLayout(5, 2, 5, 5));

        form.add(new JLabel("Patient Name:"));
        nameField = new JTextField();
        form.add(nameField);

        form.add(new JLabel("Age:"));
        ageField = new JTextField();
        form.add(ageField);

        form.add(new JLabel("Gender:"));
        genderBox = new JComboBox<>(new String[]{"M", "F"});
        form.add(genderBox);

        form.add(new JLabel("Disease Type:"));
        diseaseBox = new JComboBox<>(new String[]{
                "Heart", "Brain", "Kidney", "Cancer", "None"
        });
        form.add(diseaseBox);

        add(form, BorderLayout.WEST);

        /* ---------- BUTTON PANEL ---------- */
        JPanel buttons = new JPanel(new GridLayout(4, 1, 5, 5));

        addBtn = new JButton("Add Patient");
        searchBtn = new JButton("Search Patient");
        treatBtn = new JButton("Treat Patient");
        displayBtn = new JButton("Display Queue");

        buttons.add(addBtn);
        buttons.add(searchBtn);
        buttons.add(treatBtn);
        buttons.add(displayBtn);

        add(buttons, BorderLayout.EAST);

        /* ---------- DISPLAY AREA ---------- */
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        /* ---------- ACTION LISTENERS ---------- */
        addBtn.addActionListener(this);
        searchBtn.addActionListener(this);
        treatBtn.addActionListener(this);
        displayBtn.addActionListener(this);

        setVisible(true);
    }

    /* ================= BUTTON ACTIONS ================= */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == addBtn) {
            String name = nameField.getText();
            int age = Integer.parseInt(ageField.getText());
            char gender = genderBox.getSelectedItem().toString().charAt(0);

            queue.addPatient(new Patient(name, age, gender));
            displayArea.setText("Patient added successfully!");

        } else if (e.getSource() == searchBtn) {
            String name = nameField.getText();
            Patient p = queue.search(name);
            if (p != null) {
                displayArea.setText("Patient Found:\n" +
                        "Name: " + p.name +
                        "\nAge: " + p.age +
                        "\nGender: " + p.gender);
            } else {
                displayArea.setText("Patient not found!");
            }

        } else if (e.getSource() == treatBtn) {
            Patient p = queue.deletePatient();
            if (p == null) {
                displayArea.setText("No patients to treat.");
            } else {
                displayArea.setText("Patient Treated:\n" +
                        p.name + "\nDoctor Assigned: " +
                        getDoctor());
            }

        } else if (e.getSource() == displayBtn) {
            displayArea.setText(queue.displayPatients());
        }
    }

    private String getDoctor() {
        switch (diseaseBox.getSelectedIndex()) {
            case 0: return "Dr. Manoj (Heart)";
            case 1: return "Dr. Santosh (Brain)";
            case 2: return "Dr. Raj (Kidney)";
            case 3: return "Dr. Mehak (Cancer)";
            default: return "General Physician";
        }
    }

    public static void main(String[] args) {
        new HospitalGUI();
    }
}


