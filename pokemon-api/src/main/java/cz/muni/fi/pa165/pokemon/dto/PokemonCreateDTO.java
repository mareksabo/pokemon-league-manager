package cz.muni.fi.pa165.pokemon.dto;

import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import java.util.Objects;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Data transfer object used for holding data from form or something similar.
 *
 * @author Jaroslav Cechak
 */
public class PokemonCreateDTO {

    /**
     * Name of a pokemon (e.g. Pikachu)
     */
    @NotNull
    @NotEmpty
    private String name;

    /**
     * Nickname of a pokemon (e.g. Karel)
     */
    @NotNull
    @NotEmpty
    private String nickname;

    /**
     * Type of a pokemon, this determines his abilities and effectiveness
     * against other pokemons.
     */
    @NotNull
    private PokemonType type;

    /**
     * The level of pokemon's skills, this determines his strength.
     */
    @DecimalMin(value = "0")
    private int skillLevel;

    /**
     * Id of a trainer (owner) of a pokemon
     */
    @NotNull
    private Long trainerId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
    }

    public int getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(int skillLevel) {
        this.skillLevel = skillLevel;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.nickname);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof PokemonCreateDTO)) {
            return false;
        }
        final PokemonCreateDTO other = (PokemonCreateDTO) obj;
        if (!Objects.equals(this.name, other.getName())) {
            return false;
        }
        if (!Objects.equals(this.nickname, other.getNickname())) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pokemon{"
                + ", name=" + name
                + ", nickname=" + nickname
                + ", type=" + type
                + ", skillLevel=" + skillLevel
                + ", trainerId=" + trainerId
                + '}';
    }

}
