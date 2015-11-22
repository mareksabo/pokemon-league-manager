package cz.muni.fi.pa165.pokemon.facade;

import cz.muni.fi.pa165.pokemon.dto.PokemonCreateDTO;
import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import java.util.List;

/**
 * Facade interface defining the facade's contracts with outer world.
 *
 * @author Jaroslav Cechak
 */
public interface PokemonFacade {

    /**
     * Save the given pokemon into system.
     *
     * @param pokemon pokemon to be saved
     * @return saved pokemon
     */
    PokemonDTO createPokemon(PokemonCreateDTO pokemon);

    /**
     * Returns pokemon with given id
     *
     * @param id id of pokemon
     * @return pokemon with given id
     */
    PokemonDTO getPokemonById(Long id);

    /**
     * Changes the skill of the pokemon
     *
     * @param pokemon pokemon to be updated
     * @param newSkill new skill level of the pokemon
     */
    void changeSkill(PokemonDTO pokemon, int newSkill);

    /**
     * Changes the pokemon's trainer (owner)
     *
     * @param pokemon pokemon to be updated
     * @param newTrainer pokemon's new trainer
     */
    void changeTrainer(PokemonDTO pokemon, TrainerDTO newTrainer);

    /**
     * Deletes the given pokemon from system.
     *
     * @param pokemon pokemon to be deleted
     */
    void deletePokemon(PokemonDTO pokemon);

    /**
     * Returns {@link java.util.List List} of all pokemons present in the
     * systems.
     *
     * @return {@link java.util.List List} of all pokemons present in the
     * systems
     */
    List<PokemonDTO> getAllPokemons();

    /**
     * Returns {@link java.util.List List} of all pokemons that are being
     * trained by the given trainer in the system.
     *
     * @param trainerId the id of common trainer of the pokemons
     * @return {@link java.util.List List} of all pokemons that are being
     * trained by the given trainer
     */
    List<PokemonDTO> getAllPokemonsOfTrainerWithId(Long trainerId);

    /**
     * Returns {@link java.util.List List} of all pokemons that have the given
     * name in the system.
     *
     * @param name name of pokemon
     * @return {@link java.util.List List} of all pokemons that have the given
     * name
     */
    List<PokemonDTO> getAllPokemonsWithName(String name);

    /**
     * Returns {@link java.util.List List} of all pokemons of the given type in
     * the system.
     *
     * @param type type of pokemon
     * @return {@link java.util.List List} of all pokemons of the given type
     */
    List<PokemonDTO> getAllPokemonsWithType(PokemonType type);
}
