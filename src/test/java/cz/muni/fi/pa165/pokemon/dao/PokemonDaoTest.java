package cz.muni.fi.pa165.pokemon.dao;

import cz.muni.fi.pa165.pokemon.context.PersistenceConfiguration;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;

import java.sql.Date;
import junit.framework.Assert;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.validation.ConstraintViolationException;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test PokemonDao implementation
 * @author Milos Bartak
 */
@ContextConfiguration(classes = PersistenceConfiguration.class)
public class PokemonDaoTest extends AbstractTestNGSpringContextTests {
    
    @PersistenceUnit
    private EntityManagerFactory emf;
    
    private EntityManager entityManager;

    @Inject
    private PokemonDao pokemonDao;
    
    private Pokemon pokemon1;
    private Pokemon pokemon2;
    
    @BeforeMethod
    public void setUpMethod() throws Exception {
        entityManager = emf.createEntityManager();
        Trainer trainer = new Trainer();
        trainer.setName("Ash");
        trainer.setSurname("Ketchum");
        trainer.setDateOfBirth(new Date(959595));
        trainer.setStadium(null);
        
        pokemon1 = new Pokemon();
        pokemon1.setName("Squirtle");
        pokemon1.setNickname("Master blaster");
        pokemon1.setType(PokemonType.WATER);
        pokemon1.setLevel(7);
        pokemon1.setTrainer(trainer);
        
        trainer.addPokemon(pokemon1);
        
        entityManager.getTransaction().begin();
        entityManager.persist(trainer);
        //entityManager.flush();
        entityManager.getTransaction().commit();
        entityManager.close();
        
        pokemonDao.create(pokemon1);
        
        pokemon2 = new Pokemon();
        pokemon2.setName("Onix");
        pokemon2.setNickname("nyxnyx");
        pokemon2.setType(PokemonType.ROCK);
        pokemon2.setTrainer(trainer);
        pokemonDao.create(pokemon2);
    }
    
    @AfterMethod
    public void tearDown() throws Exception{
        entityManager = emf.createEntityManager();
        entityManager.getTransaction().begin();
        Pokemon pokemon = entityManager.find(Pokemon.class, pokemon1.getId());
        if(pokemon != null) {
            entityManager.remove(pokemon);
        }
        pokemon = entityManager.find(Pokemon.class, pokemon2.getId());
        if(pokemon != null) {
            entityManager.remove(pokemon);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }
    
    @Test(expectedExceptions=ConstraintViolationException.class)
    public void createNullTest() {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(null);
        pokemon.setNickname("nick");
        pokemon.setType(PokemonType.ICE);
        pokemonDao.create(pokemon);
    }
    
    @Test
    public void testCreate() {
        entityManager = emf.createEntityManager();
        
        Assert.assertNotNull("Persisting pokemon does not srt id.", pokemon1.getId());
        
        Pokemon found1 = entityManager.find(Pokemon.class, pokemon1.getId());
        Pokemon found2 = entityManager.find(Pokemon.class, pokemon2.getId());
        
        Assert.assertNotNull("Pokemon " + 
                pokemon1.getName() + 
                "with id: " +
                pokemon1.getId() + 
                "not found"
                , found1);
        
        Assert.assertNotNull("Pokemon " + 
                pokemon2.getName() + 
                "with id: " +
                pokemon2.getId() + 
                "not found"
                , found2);
        
        Assert.assertEquals("Persisted pokemon with id: " +
                pokemon1.getId() + 
                "does not match found pokemon with id: " +
                found1.getId()
                , found1, pokemon1);
        
        Assert.assertEquals("Persisted pokemon with id: " +
                pokemon2.getId() + 
                "does not match found pokemon with id: " +
                found2.getId()
                , found2, pokemon2);
        
        try {
            pokemonDao.create(pokemon1);
            Assert.fail("Pokemon was persisted twice");
        }catch(Exception ex) {
            
        }
    }
    
    @Test
    public void updateTest() {
        entityManager = emf.createEntityManager();
        
        Pokemon found1 = entityManager.find(Pokemon.class, pokemon1.getId());
        int level = found1.getLevel();
        found1.setLevel(level + 1);
        
        found1 = entityManager.find(Pokemon.class, pokemon1.getId());
        Assert.assertEquals("Level did not get updated", level + 1, found1.getLevel());
    }
    
    @Test
    public void deleteTest() {
        entityManager = emf.createEntityManager();
        pokemonDao.delete(pokemon1);
        
        Pokemon toBeDeleted = entityManager.find(Pokemon.class, pokemon1.getId());
        Assert.assertEquals("Pokemon" +
                pokemon1.getName() + 
                "with id: " +
                pokemon1.getId() + 
                "was not deleted."
                , null, toBeDeleted);
    }
    
    @Test
    public void findByIdTest() {
        entityManager = emf.createEntityManager();
        
        Pokemon found = entityManager.find(Pokemon.class, pokemon2.getId());
        Assert.assertEquals("Pokemon found by id:" +
                found.getId() + 
                "does not match expected pokemon with id" + 
                pokemon2.getId()
                ,pokemon2, found);
    }
}