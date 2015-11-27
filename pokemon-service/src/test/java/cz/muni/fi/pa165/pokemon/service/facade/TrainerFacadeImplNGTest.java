package cz.muni.fi.pa165.pokemon.service.facade;

import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.facade.TrainerFacade;
import cz.muni.fi.pa165.pokemon.service.MappingService;
import cz.muni.fi.pa165.pokemon.service.PokemonService;
import cz.muni.fi.pa165.pokemon.service.StadiumService;
import cz.muni.fi.pa165.pokemon.service.TrainerService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.sql.Date;
import java.util.Collection;

import static org.testng.Assert.assertEquals;

/**
 * Tests correctness of TrainerFacadeImpl methods
 * 
 * @author Milos Bartak
 */
@ContextConfiguration(classes = {cz.muni.fi.pa165.pokemon.context.ServiceConfiguration.class})
public class TrainerFacadeImplNGTest extends AbstractTestNGSpringContextTests{
    
    @Inject
    private TrainerFacade trainerFacade;
    
    @Inject
    private TrainerService trainerService;
    
    @Inject
    private PokemonService pokemonService;

    @Inject
    private StadiumService stadiumService;
    
    @Inject
    private MappingService beanMappingService;
    
    private Trainer setUpTrainer;
    
    private Stadium stadium = null;
    
    @BeforeMethod
    public void setUpMethod() {
        
        setUpTrainer = new Trainer();
        setUpTrainer.setName("Brock");
        setUpTrainer.setSurname("Brokovnice");
        setUpTrainer.setDateOfBirth(Date.valueOf("1989-10-11"));
        trainerService.createTrainer(setUpTrainer);
    }
    
    @AfterMethod
    public void tearDownMethod() {
        Collection<Trainer> col = trainerService.findAllTrainers();
        for(Trainer t : col) {
            trainerService.deleteTrainer(t);
        }
    }
    
    @Test
    public void testCreateTrainer() {
        TrainerDTO trainerDTO = new TrainerDTO();
        trainerDTO.setName("Ash");
        trainerDTO.setSurname("Hsa");
        trainerDTO.setStadium(null);
        trainerDTO.setDateOfBirth(Date.valueOf("1990-10-11"));
        trainerFacade.createTrainer(trainerDTO);

        assertEquals(beanMappingService.map(trainerDTO, Trainer.class), trainerService.findTrainerById(trainerDTO.getId()), trainerDTO.toString() + " not created");
    }
    
    @Test
    public void testDeleteTrainer() {
        trainerFacade.deleteTrainer(beanMappingService.map(setUpTrainer, TrainerDTO.class));
        assertEquals(null, trainerService.findTrainerById(setUpTrainer.getId()), "Trainer was not deleted properly");
    }
    
    @Test
    public void testUpdateTrainer() {
        setUpTrainer.setName("Brockbrock");
        System.out.println("\n\n\n\n");
        System.out.println(setUpTrainer.toString());
        System.out.println(setUpTrainer.getId());
        System.out.println("\n\n\n\n");
        System.out.println(beanMappingService.map(setUpTrainer, TrainerDTO.class).toString());
        System.out.println(beanMappingService.map(setUpTrainer, TrainerDTO.class).getId());
        System.out.println("\n\n\n\nkonec vypisu");
        trainerFacade.updateTrainer(beanMappingService.map(setUpTrainer, TrainerDTO.class));
        assertEquals(setUpTrainer.getName(), trainerService.findTrainerById(setUpTrainer.getId()).getName());
    }
    
    @Test
    public void testIsLeaderOfTheStadium() {
        stadium = new Stadium();
        stadium.setCity("Pallet");
        stadium.setType(PokemonType.ROCK);
        stadium.setLeader(setUpTrainer);
        stadiumService.createStadium(stadium);
        setUpTrainer.setStadium(stadium);
        boolean result = trainerFacade.isLeaderOfTheStadium(beanMappingService.map(setUpTrainer, TrainerDTO.class), beanMappingService.map(stadium, StadiumDTO.class));
        
        assertEquals(result, true);
        
        stadiumService.deleteStadium(stadium);
    }
    
    /*@Test
    public void testAddPokemon() {
        Pokemon pokemon = new Pokemon();
        pokemon.setName("Pika");
        pokemon.setNickname("Chu");
        pokemon.setSkillLevel(77);
        pokemon.setType(PokemonType.ELECTRIC);
        pokemon.setTrainer(setUpTrainer);
        
        pokemonService.createPokemon(pokemon);
        trainerFacade.addPokemon(beanMappingService.map(setUpTrainer, TrainerDTO.class), beanMappingService.map(pokemon, PokemonDTO.class));
        //TODO asi se spravne nemapuje - trener ma pokemona v metode v DAO ale ma prazdny seznam v metode ve FACADEIMPL
        assertEquals(setUpTrainer.getPokemons().size(), 1, "Pokemon not added.");
        trainerFacade.removePokemon(beanMappingService.map(setUpTrainer, TrainerDTO.class), beanMappingService.map(pokemon, PokemonDTO.class));
        pokemonService.deletePokemon(pokemon);
    }*/
    
    /*@Test
    public void testRemovePokemon() {
    }*/
   
    /*@Test
    public void testAddBadge() {
    }*/
    
    @Test
    public void testFindTrainerById() {
        Long id = setUpTrainer.getId();
        
        TrainerDTO found = trainerFacade.findTrainerById(id);
        
        assertEquals(found, beanMappingService.map(setUpTrainer, TrainerDTO.class), setUpTrainer.toString() + " not found");
    }
    
    @Test
    public void testFindAllTrainers() {
        Trainer trainer = new Trainer();
        trainer.setName("Koren");
        trainer.setSurname("Borec");
        trainer.setStadium(null);
        trainer.setDateOfBirth(Date.valueOf("1949-10-11"));
        trainerService.createTrainer(trainer);
        
        assertEquals(trainerFacade.findAllTrainers().size(), 2);
    }
    
    @Test
    public void testFindAllTrainersWithName() {
        Trainer trainer = new Trainer();
        trainer.setName("Brock");
        trainer.setSurname("Borec");
        trainer.setStadium(null);
        trainer.setDateOfBirth(Date.valueOf("1949-10-11"));
        trainerService.createTrainer(trainer);
        
        assertEquals(trainerFacade.findAllTrainersWithName("Brock").size(), 2);
    }
    
    @Test
    public void testFindAllTrainersWithSurName() {
        Trainer trainer = new Trainer();
        trainer.setName("Borec");
        trainer.setSurname("Brokovnice");
        trainer.setStadium(null);
        trainer.setDateOfBirth(Date.valueOf("1949-10-11"));
        trainerService.createTrainer(trainer);
        
        assertEquals(trainerFacade.findAllTrainersWithSurname("Brokovnice").size(), 2);
    }
    
    /*@Test
    public void testFindAllTrainersWithPokemon() {
    }*/
    
    /*@Test
    public void testFindAllTrainersWithBadge() {
    }*/
}
