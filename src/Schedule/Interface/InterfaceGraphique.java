package Schedule.Interface;

import static javax.swing.BoxLayout.X_AXIS;
import static javax.swing.BoxLayout.Y_AXIS;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import Schedule.moteur.Simulation;
import Schedule.utilities.Docteur;
import Schedule.utilities.Patient;
import Schedule.utilities.Position;

/**
 * InterfaceGraphique
 */
public class InterfaceGraphique extends JFrame {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Simulation resteDuProjet = new Simulation();
    private optionJpanelDroite optionDroite = new optionJpanelDroite();
    private affichageMap map = new affichageMap();
    private Docteur docteurChoisit;

    public InterfaceGraphique() {
        // initialisation fenetre
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setSize(800, 800);
        this.setTitle("interface graphique");
        this.setFocusable(true);

        this.setVisible(true);

        // mise en forme fenetre

        this.setLayout(new BorderLayout());

        this.getContentPane().add(map, BorderLayout.CENTER);
        this.getContentPane().add(optionDroite, BorderLayout.EAST);

    }

    public static void main(String[] args) {
        new InterfaceGraphique();

    }

    /**
     * affichageMap
     */
    public class affichageMap extends JPanel {

        /**
         *
         */
        private LocalDate jourChoisit = LocalDate.now();
        private static final long serialVersionUID = 1L;

        public void paintComponent(Graphics g) {
            System.out.println("interfacegraphique : affichagemap : paintComponent()");
            g.setColor(Color.BLACK);
            // On le dessine de sorte qu'il occupe toute la surface
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
            Graphics2D g2d = (Graphics2D) g;
            g2d.translate(0, this.getHeight() / 2);

            if (resteDuProjet.retourPositionRdvSelonDateDocteur(docteurChoisit, jourChoisit) != null) {
                g.setColor(Color.BLUE); // point de depart du docteur
                Position precedentPosition = docteurChoisit.getLieuDeDepart();
                g.fillOval((int) precedentPosition.getX(), (int) precedentPosition.getY(), 10, 10);

                g.setColor(Color.WHITE);// affichage des points du trajets
                for (Position positionRdv : resteDuProjet.retourPositionRdvSelonDateDocteur(docteurChoisit,jourChoisit)) {
                    System.out.println("interfaceGraphique : affichageMap : paintComponent() : " + positionRdv);
                    g.fillOval((int) positionRdv.getX(), (int) positionRdv.getY(), 10, 10);
                    if (precedentPosition != null) {
                        g.drawLine((int) precedentPosition.getX(), (int) precedentPosition.getY(),
                                (int) positionRdv.getX(), (int) positionRdv.getY());
                    }
                    precedentPosition = positionRdv;
                }
            }
        }

        public void MAJdeLaMap() {
            this.repaint();
        }

        public LocalDate getJourChoisit() {
            return jourChoisit;
        }

        public void setJourChoisit(LocalDate jourChoisit) {
            this.jourChoisit = jourChoisit;
        }

    }

    private class optionJpanelDroite extends JPanel implements ActionListener {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        private int criticiteMax = 10;

        private JTabbedPane tabbedPane = new JTabbedPane();

        private JComboBox<Docteur> choixDocteurBox = new JComboBox<Docteur>();
        private JComboBox<Patient> creationNouvelleDemandePatientComboBox = new JComboBox<Patient>();
        private JTextArea affichageDonnees = new JTextArea();
        private JScrollPane affichageDonneesJScrollPane = new JScrollPane(affichageDonnees);

        private JButton selectionDateDocteurButton = new JButton("selection Date");

        private JPanel affichageJPanel = new JPanel();
        private LocalDate jourChoisit = LocalDate.now();
        private JTextField affichageDateJLabel = new JTextField();
        private JButton affichageDateJButton = new JButton("Date");
        private JPanel affichageDateJPanel = new JPanel();

        private JPanel ajoutJPanel = new JPanel();

        private TitledBorder titreAjoutDemande;
        private JPanel ajoutDemandeJPanel = new JPanel();
        private JLabel ajoutCriticiteDemandLabel = new JLabel("Criticite :");
        private Choice ajoutCriticiteDemande = new Choice();
        private JLabel ajoutDescriptionJLabel = new JLabel("Description :");
        private JTextField ajoutDescriptionJTexteField = new JTextField();
        private JButton confirmationCreationDemandeButton = new JButton("confirmer");

        private TitledBorder titreAjoutPatient;
        private JPanel ajoutPatientJPanel = new JPanel();
        private JLabel ajoutPatientNomJLabel = new JLabel("nom :");
        private JTextField ajoutPatientNomJTextField = new JTextField();
        private JLabel ajoutPatientPrenomJLabel = new JLabel("prenom :");
        private JTextField ajoutPatientPrenomJTextField = new JTextField();
        private JLabel ajoutPatientPositionJLabel = new JLabel("position :");
        private JTextField ajoutPatientPositionJTextField = new JTextField();
        private JButton confirmationCreationPatientJButton = new JButton("confirmer");

        private JPanel testJPanel = new JPanel();

        private TitledBorder titreTest;
        private JButton testPatientJButton = new JButton("Creer Patient");
        private JButton testDemandeJButton = new JButton("Creer Demande");

        optionJpanelDroite() {
            creerInterfaceGraphique();
        }

        public void creerInterfaceGraphique() {
            BoxLayout boxlayout = new BoxLayout(this, BoxLayout.Y_AXIS);
            this.setLayout(boxlayout);

            BoxLayout affichageLayout = new BoxLayout(affichageJPanel, BoxLayout.Y_AXIS);
            affichageJPanel.setLayout(affichageLayout);

            BoxLayout ajoutLayout = new BoxLayout(ajoutJPanel, BoxLayout.Y_AXIS);
            ajoutJPanel.setLayout(ajoutLayout);

            titreAjoutDemande = BorderFactory.createTitledBorder("Ajout Demande");
            BoxLayout ajoutLayoutDemande = new BoxLayout(ajoutDemandeJPanel, Y_AXIS);
            ajoutDemandeJPanel.setLayout(ajoutLayoutDemande);
            ajoutDemandeJPanel.setBorder(titreAjoutDemande);

            titreAjoutPatient = BorderFactory.createTitledBorder("Ajout Patient");
            BoxLayout ajoutLayoutPatient = new BoxLayout(ajoutPatientJPanel, Y_AXIS);
            ajoutPatientJPanel.setLayout(ajoutLayoutPatient);
            ajoutPatientJPanel.setBorder(titreAjoutPatient);

            BoxLayout affichageDateLayout = new BoxLayout(affichageDateJPanel, X_AXIS);
            affichageDateJPanel.setLayout(affichageDateLayout);

            titreTest = BorderFactory.createTitledBorder("Test");
            BoxLayout testLayout = new BoxLayout(testJPanel, Y_AXIS);
            testJPanel.setLayout(testLayout);
            testJPanel.setBorder(titreTest);

            MAJdesBarres();

            for (int i = 0; i < criticiteMax; i++) {
                ajoutCriticiteDemande.add(Integer.toString(i));
            }

            choixDocteurBox.addActionListener(this);

            tabbedPane.addTab("Affichage", affichageJPanel);
            tabbedPane.addTab("Patient", ajoutJPanel);
            tabbedPane.addTab("Test", testJPanel);

            //affichageDateJPanel.setPreferredSize(new Dimension(203,150));
            affichageDateJLabel.setMaximumSize(new Dimension(340, 50));
            affichageDateJPanel.add(affichageDateJLabel);
            affichageDateJPanel.add(affichageDateJButton);
            affichageDateJButton.addActionListener(this);

            choixDocteurBox.setMaximumSize(new Dimension(340, 50));
            affichageJPanel.add(choixDocteurBox);
            affichageJPanel.add(affichageDateJPanel);
            affichageDonneesJScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            affichageJPanel.add(affichageDonneesJScrollPane);
            affichageJPanel.add(selectionDateDocteurButton);
            selectionDateDocteurButton.addActionListener(this);

            ajoutJPanel.add(ajoutDemandeJPanel);
            ajoutJPanel.add(ajoutPatientJPanel);

            ajoutDemandeJPanel.add(creationNouvelleDemandePatientComboBox);
            ajoutDemandeJPanel.add(ajoutCriticiteDemandLabel);
            ajoutDemandeJPanel.add(ajoutCriticiteDemande);
            ajoutDemandeJPanel.add(ajoutDescriptionJLabel);
            ajoutDemandeJPanel.add(ajoutDescriptionJTexteField);
            ajoutDemandeJPanel.add(confirmationCreationDemandeButton);
            confirmationCreationDemandeButton.addActionListener(this);

            ajoutPatientJPanel.add(ajoutPatientNomJLabel);
            ajoutPatientJPanel.add(ajoutPatientNomJTextField);
            ajoutPatientJPanel.add(ajoutPatientPrenomJLabel);
            ajoutPatientJPanel.add(ajoutPatientPrenomJTextField);
            ajoutPatientJPanel.add(ajoutPatientPositionJLabel);
            ajoutPatientJPanel.add(ajoutPatientPositionJTextField);
            ajoutPatientJPanel.add(confirmationCreationPatientJButton);
            confirmationCreationPatientJButton.addActionListener(this);

            testJPanel.add(testPatientJButton);
            testJPanel.add(testDemandeJButton);
            testPatientJButton.addActionListener(this);
            testDemandeJButton.addActionListener(this);

            this.add(tabbedPane);

        }

        public void MAJdesBarres() {

            choixDocteurBox.removeAllItems();
            creationNouvelleDemandePatientComboBox.removeAllItems();

            affichageDateJLabel.setText(jourChoisit.toString());

            for (Docteur var : resteDuProjet.getDoc()) {
                choixDocteurBox.addItem(var);
            }

            for (Patient Pati : resteDuProjet.getMalades()) {
                creationNouvelleDemandePatientComboBox.addItem(Pati);
            }
           

        }

        public void recuperationPourDemande() {
            resteDuProjet.nouveauDiagnostic(Integer.parseInt(ajoutCriticiteDemande.getSelectedItem()),
                    ajoutDescriptionJTexteField.getText(),
                    ((Patient) creationNouvelleDemandePatientComboBox.getSelectedItem()).getIDPatient());
            ajoutCriticiteDemande.select(0);
            ajoutDescriptionJTexteField.setText("");
            MAJdesBarres();
        }

        public void recuperationPourPatient() {
            try {
                if (ajoutPatientNomJTextField.getText() != "" && ajoutPatientPrenomJTextField.getText() != "") {
                    int positionCarre = Integer.parseInt(ajoutPatientPositionJTextField.getText());
                    Position prochainePositionPatient = new Position(positionCarre, positionCarre);
                    resteDuProjet.addPatient(new Patient(prochainePositionPatient, ajoutPatientNomJTextField.getText(),
                            ajoutPatientPrenomJTextField.getText()));
                    MAJdesBarres();
                } else {
                    System.out.println(
                            "interfaceGraphique : optionJpanelDroite : recuperationPourPatient() mauvaise saisie patient");
                }
            } catch (NumberFormatException e) {
                System.out.println(
                        "interfaceGraphique : optionJpanelDroite : recuperationPourPatient() (erreur) : la position du patient entree est pas correcte");
            }

            ajoutPatientNomJTextField.setText("");
            ajoutPatientPrenomJTextField.setText("");
            ajoutPatientPositionJTextField.setText("");

        }

        public void changementDateAffichage() {
            DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(new DatePicker().setPickedDate(), formatDate);
            this.jourChoisit = date;
            map.setJourChoisit(jourChoisit);
            MAJdesBarres();
        }

        public void chargementInfoEnfonctionDateDocteur() {
            docteurChoisit = (Docteur) choixDocteurBox.getSelectedItem();
            affichageDonnees.setText(resteDuProjet.retourStringRdvSelonDateDocteur(docteurChoisit, jourChoisit));
            map.MAJdeLaMap();
            System.out.println("interfaceGraphique : optionJpanelDroite : actionPerformed() : "
                    + resteDuProjet.retourStringDesRdvSelonDocteur(docteurChoisit));
            System.out.println("interfaceGraphique : optionJpanelDroite : actionPerformed() : debug : taille d'affichage Date docteur " +  affichageDateJPanel.getSize() );
        }

        public Patient nouveauxPatientAleatoire(){
            String nomAleatoire = "patient " + new Random().nextInt(100);
            String prenomAleatoire = "test ";
            Position positionAleatoire = new Position(new Random().nextInt(250), new Random().nextInt(250));
            return new Patient(positionAleatoire, nomAleatoire, prenomAleatoire);
        }

        public void testGraphique(ActionEvent e) {
            
            if (e.getSource() == testPatientJButton) {
               
                resteDuProjet.addPatient(nouveauxPatientAleatoire());
            } else if (e.getSource() == testDemandeJButton) {
                Patient pat = nouveauxPatientAleatoire();
                resteDuProjet.addPatient(pat);
                resteDuProjet.nouveauDiagnostic(new Random().nextInt(5), "maladie",
                        pat.getIDPatient());
            }
            MAJdesBarres();
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == confirmationCreationDemandeButton) {
                System.out.println("interfaceGraphique : optionJpanelDroite : actionPerformed() bouton demande");
                recuperationPourDemande();
            } else if (e.getSource() == selectionDateDocteurButton) {
                System.out.println(
                        "interfaceGraphique : optionJpanelDroite : actionPerformed() : changement date ou docteur");
                chargementInfoEnfonctionDateDocteur();
            } else if (e.getSource() == confirmationCreationPatientJButton) {
                System.out.println("interfaceGraphique : optionJpanelDroite : actionPerformed() : bouton patient");
                recuperationPourPatient();
            } else if (e.getSource() == affichageDateJButton) {
                changementDateAffichage();
            } else if (e.getSource() == testPatientJButton || e.getSource() == testDemandeJButton) {
                testGraphique(e);
            }

        }
    }

}