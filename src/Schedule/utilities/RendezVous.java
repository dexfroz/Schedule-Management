package Schedule.utilities;

import java.time.Duration;
import java.time.LocalDate;

import java.time.LocalTime;
import java.util.UUID;

import Marchant.Moteur.Noeud;

/**
 * RendezVous
 */
public class RendezVous {

    private final String IDRendezVous;
    private LocalDate date;
    private LocalTime heureDebut;
    private Duration dureeConsultation;
    private Position lieu;
    private Docteur medecinAffecte;
    private Patient malade;
    private Diagnostic diag;

    public RendezVous(LocalDate date, LocalTime heureDebut, Duration dureeConsultation, Docteur medecinAffecte,
            Patient malade, Diagnostic diag) {
        this.IDRendezVous = UUID.randomUUID().toString();
        this.date = date;
        this.heureDebut = heureDebut;
        this.dureeConsultation = dureeConsultation;
        this.medecinAffecte = medecinAffecte;
        this.malade = malade;
        this.diag = diag;
        this.lieu = malade.getLieuDeVie();
    }

    public RendezVous(LocalDate date, LocalTime heureDebut, Duration dureeConsultation, Docteur docteur,
            Diagnostic diag) {
        this.IDRendezVous = UUID.randomUUID().toString();
        this.date = date;
        this.heureDebut = heureDebut;
        this.dureeConsultation = dureeConsultation;
        this.diag = diag;
        this.malade = this.diag.getPatientConserne();
        this.lieu = this.malade.getLieuDeVie();
        this.medecinAffecte = docteur;

    }

    public RendezVous(RendezVous rdvSource) {
        this.IDRendezVous = rdvSource.getIDRendezVous();
        this.date = rdvSource.getDate();
        this.heureDebut = rdvSource.getHeureDebut();
        this.dureeConsultation = rdvSource.getDureeConsultation();
        this.lieu = rdvSource.getLieu();
        this.medecinAffecte = rdvSource.getMedecinAffecte();
        this.malade = rdvSource.getMalade();
        this.diag = rdvSource.getDiag();
    }

    public Position getLieu() {
        return lieu;
    }

    public void setLieu(Position lieu) {
        this.lieu = lieu;
    }

    public Docteur getMedecinAffecte() {
        return medecinAffecte;
    }

    public void setMedecinAffecte(Docteur medecinAffecte) {
        this.medecinAffecte = medecinAffecte;
    }

    public Patient getMalade() {
        return malade;
    }

    public void setMalade(Patient malade) {
        this.malade = malade;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public Duration getDureeConsultation() {
        return dureeConsultation;
    }

    public void setDureeConsultation(Duration dureeConsultation) {
        this.dureeConsultation = dureeConsultation;
    }

    public Diagnostic getDiag() {
        return diag;
    }

    public void setDiag(Diagnostic diag) {
        this.diag = diag;
    }

    public boolean equalsPosition(Noeud no) {
        if (no.getX() == this.lieu.getX() && no.getY() == this.lieu.getY()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "IDRendezVous = " + IDRendezVous + ",\n date=" + date + ",\n diag=" + diag + ",\n dureeConsultation="
                + dureeConsultation + ",\n heureDebut=" + heureDebut + ",\n malade=" + malade + "\n";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((IDRendezVous == null) ? 0 : IDRendezVous.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        RendezVous other = (RendezVous) obj;
        if (IDRendezVous == null) {
            if (other.IDRendezVous != null)
                return false;
        } else if (!IDRendezVous.equals(other.IDRendezVous))
            return false;
        return true;
    }

    public String getIDRendezVous() {
        return IDRendezVous;
    }

}