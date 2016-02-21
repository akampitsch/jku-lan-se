package at.jku.oeh.lan.laganizer.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import at.jku.oeh.lan.laganizer.model.Team;
import at.jku.oeh.lan.laganizer.model.TeamDAO;
import jkulan.software.dto.RESTDataWrapperDTO;
import jkulan.software.model.TournamentDAO;
import jkulan.software.model.User;
import jkulan.software.model.UserDAO;

@RestController
@RequestMapping("/teams/")
public class TeamController {

    @Autowired
    private TeamDAO teamDao;
    @Autowired
    private UserDAO userDao;
    @Autowired
    private TournamentDAO tournamentDao;

    @RequestMapping(value = "create", method = RequestMethod.POST)
    public RESTDataWrapperDTO<Team> create(@RequestParam long userId, @RequestParam long tournamentId){
    	RESTDataWrapperDTO<Team> result = new RESTDataWrapperDTO<>();
    	Team team = new Team();
    	team.setName(userDao.findOne(userId).getName());
    	
    	List<User> players = new ArrayList<>();
    	players.add(userDao.findOne(userId));
    	team.setPlayers(players);
    	team.setTournament(tournamentDao.findOne(tournamentId));
    	teamDao.save(team);
    	
    	result.setData(team);
    	result.setSuccess(true);
    	
    	return result;
    }
    
    @RequestMapping(value = "addPlayer", method = RequestMethod.POST)
    public RESTDataWrapperDTO<Team> addPlayer(@RequestParam long id, @RequestParam long userId){
    	RESTDataWrapperDTO<Team> result = new RESTDataWrapperDTO<>();
    	Team team = teamDao.findOne(id);
    	List<User> players = team.getPlayers();
    	
    	players.add(userDao.findOne(userId));
    	team.setPlayers(players);
    	
    	result.setData(team);
    	result.setSuccess(true);
    	
    	return result;
    }
    
    @RequestMapping(value = "delPlayer", method = RequestMethod.POST)
    public RESTDataWrapperDTO<Team> delPlayer(@RequestParam long id, @RequestParam long userId){
    	RESTDataWrapperDTO<Team> result = new RESTDataWrapperDTO<>();
    	Team team = teamDao.findOne(id);
    	List<User> players = team.getPlayers();
    	
    	players.remove(userDao.findOne(userId));
    	team.setPlayers(players);
    	
    	if(players.size() <= 0){
    		teamDao.delete(id);
    	}
    	
    	result.setData(team);
    	result.setSuccess(true);
    	
    	return result;
    }
    
    @RequestMapping(value = "changeName", method = RequestMethod.POST)
    public RESTDataWrapperDTO<Team> changeName(@RequestParam long id, @RequestParam String name){
    	RESTDataWrapperDTO<Team> result = new RESTDataWrapperDTO<>();
    	Team team = teamDao.findOne(id);
    	team.setName(name);
    	
    	result.setData(team);
    	result.setSuccess(true);
    	
    	return result;
    }
}
