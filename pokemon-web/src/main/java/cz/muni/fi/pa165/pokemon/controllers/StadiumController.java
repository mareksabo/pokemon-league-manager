package cz.muni.fi.pa165.pokemon.controllers;

import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.dto.StadiumDTO;
import cz.muni.fi.pa165.pokemon.dto.TrainerDTO;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import cz.muni.fi.pa165.pokemon.facade.PokemonFacade;
import cz.muni.fi.pa165.pokemon.facade.StadiumFacade;
import cz.muni.fi.pa165.pokemon.facade.TrainerFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;


/**
 * Controller for stadium administration
 * @author Dominika Talianova
 */
@Controller
@RequestMapping("/menu")
public class StadiumController {

    @Autowired
    private StadiumFacade stadiumFacade;

    @Autowired
    private TrainerFacade trainerFacade;

    private Map<Long, TrainerDTO> stadiumsAndTrainers;

    public void setStadiumFacade(StadiumFacade stadiumFacade){
        this.stadiumFacade = stadiumFacade;
    }

    @RequestMapping(value = "/stadium/stadiumList")
    public String showList(Model model){
        Collection<StadiumDTO> stadiumsDTO = stadiumFacade.findAll();
        stadiumsAndTrainers = new HashMap<>();
        for(StadiumDTO s:stadiumsDTO){
            System.out.println("DEBUG "+s);
            stadiumsAndTrainers.put(s.getId(),trainerFacade.findTrainerById(s.getStadiumLeaderId()));
        }
        model.addAttribute("stadiums", stadiumsDTO);
        model.addAttribute("stadiumsAndTrainers", stadiumsAndTrainers);
        return "/menu/stadium/stadiumList";
    }

    @RequestMapping(value = "/stadium/new", method = RequestMethod.GET)
    public String newProduct(Model model){
        Collection<PokemonType> typeList = new ArrayList<>();
        for(PokemonType type : PokemonType.values()){
            typeList.add(type);
        }

        /*Collection<TrainerDTO> trainersWithoutStadium = new ArrayList<>();
        for(StadiumDTO s:stadiums()){
            for (TrainerDTO t:trainers()){
                if(!(s.getStadiumLeaderId().equals(t.getId()))){
                    trainersWithoutStadium.add(t);
                }
            }
        }*/
        Collection<TrainerDTO> trainersWithoutStadium = new ArrayList<>();
        for(TrainerDTO t:trainers()){
            /*System.out.println("DEBUG trainer: " + t);
            System.out.println("DEBUG stadium: " + t.getStadium());*/
            if(t.getStadium()==null){
                trainersWithoutStadium.add(t);
            }
        }
        model.addAttribute("trainersWithoutStadium", trainersWithoutStadium);
        model.addAttribute("typeList", typeList);
        model.addAttribute("newStadium", new StadiumDTO());
        System.out.println("DEBUG stadium new called");
        return "/menu/stadium/newStadium";
    }

    @ModelAttribute("stadiums")
    public Collection<StadiumDTO> stadiums(){
        return stadiumFacade.findAll();
    }

    @ModelAttribute("trainers")
    public Collection<TrainerDTO> trainers(){
        return trainerFacade.findAllTrainers();
    }


    @RequestMapping(value = "/stadium/create", method = RequestMethod.POST)
    public String create(@ModelAttribute("newStadium") StadiumDTO formBean,BindingResult bindingResult, Model model,
                         RedirectAttributes redirectAttributes, UriComponentsBuilder uriComponentsBuilder){
        /*if(bindingResult.hasErrors()) {
            for (ObjectError ge : bindingResult.getGlobalErrors()) {
                System.out.println("ObjectError: {}" + ge);
            }
            for (FieldError fe : bindingResult.getFieldErrors()) {
                model.addAttribute(fe.getField() + "_error", true);
                System.out.println("FieldError: {}" + fe);
            }
            return "/stadium/newStadium";
            Long id = badgeFacade.assignBadge(formBean);
            System.out.println("DEBUG " + formBean.getId() + " " + formBean.getStadiumId());
            redirectAttributes.addFlashAttribute("alert_success", "Product " + id + " was created");
            return "redirect:" + uriBuilder.path("/menu/badge/view/{id}").buildAndExpand(id).encode().toUriString();
        }*/

        Long id = stadiumFacade.createStadium(formBean);
        System.out.println("create stadium " + formBean.toString());
        redirectAttributes.addFlashAttribute("alert_success", "Stadium was created");
        return "redirect:" + uriComponentsBuilder.path("/menu/stadium/viewStadium/{id}").buildAndExpand(id).encode().toUriString();

    }



    @RequestMapping(value = "/stadium/viewStadium/{id}", method = RequestMethod.GET)
    public String view(@PathVariable long id, Model model){
        //System.out.println("DEBUG " + id + ' ' + model);
        model.addAttribute("stadium", stadiumFacade.findById(id));
        return "/menu/stadium/viewStadium";

    }





}