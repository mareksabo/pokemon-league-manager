package cz.muni.fi.pa165.pokemon.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Class representing a tournament in which trainers may participate
 * 
 * @author Milos Bartak
 */
@Entity
public class Tournament {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name of the tournament
     */
    @NotNull
    private String tournamentName;

    /**
     * Id of the stadium the tournament is played at
     */
    @NotNull
    private Long stadiumId;

    /**
     * Number representing how many pokemons each trainer must have
     */
    @Min(1)
    private int minimalPokemonCount;
    
    /**
     * Number representing minimal level that all pokemons must have
     */
    @Min(1)
    private int minimalPokemonLevel;
    
    /**
     * List of badges all trainers must have to participate
     */
    @Column
    @ElementCollection(targetClass=Badge.class)
    private List<Badge> badges = new ArrayList<>();
    
    /**
     * The list of participants
     */
    @Column
    @ElementCollection(targetClass=Trainer.class)
    private final List<Trainer> trainers = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }
    
    public Long getStadiumId() {
        return stadiumId;
    }

    public void setStadiumId(Long stadiumId) {
        this.stadiumId = stadiumId;
    }

    public int getMinimalPokemonCount() {
        return minimalPokemonCount;
    }

    public void setMinimalPokemonCount(int minimalPokemonCount) {
        this.minimalPokemonCount = minimalPokemonCount;
    }

    public int getMinimalPokemonLevel() {
        return minimalPokemonLevel;
    }

    public void setMinimalPokemonLevel(int minimalPokemonLevel) {
        this.minimalPokemonLevel = minimalPokemonLevel;
    }

    public List<Badge> getBadges() {
        return badges;
    }
    
    public void addBadge(Badge badge) {
        badges.add(badge);
    }

    public List<Trainer> getTrainers() {
        return trainers;
    }

    public void addTrainer(Trainer trainer) {
        trainers.add(trainer);
    }
    
    public void removeTrainer(Trainer trainer) {
        trainers.remove(trainer);
    }
    
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.tournamentName);
        hash = 43 * hash + Objects.hashCode(this.stadiumId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tournament other = (Tournament) obj;
        if (!Objects.equals(this.tournamentName, other.tournamentName)) {
            return false;
        }
        if (!Objects.equals(this.stadiumId, other.stadiumId)) {
            return false;
        }
        return true;
    }
    
    
}
