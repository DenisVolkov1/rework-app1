package main.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import main.dao.model.NewRework;
import main.dao.model.Project;
import main.dao.model.SearchFilter;
import main.dao.model.Status;
import main.dao.model.Wms;
import main.dao.service.ProjectService;
import main.dao.service.ReworkService;
import main.dao.service.StatusService;
import main.dao.service.WmsService;

@Controller
@RequestMapping(produces = "text/plain;charset=UTF-8")
public class NewReworkController {
	@Autowired
	private StatusService statusService;
	@Autowired
	private ReworkService reworkService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private WmsService wmsService;
	
	@GetMapping("/newrework")
	public String newReworkShowForm(
			@ModelAttribute("modelFilter") SearchFilter searchFilter , 
			@ModelAttribute("modelNewRework") NewRework rework, 
			Model model) {
		
		List<Status> allStatuses = statusService.findAll();
		List<Wms> allWms = wmsService.findAll();
		List<Project> allProjects = projectService.findAll();
		Boolean isAutoAssigment = false;
		
		allStatuses.removeIf(s -> s.getStatus().isEmpty());
		model.addAttribute("allProjects", allProjects);
		model.addAttribute("allStatuses", allStatuses);
		model.addAttribute("allWms", allWms);
		
		return "new_rework";
	}
	
	@PostMapping("/newrework/new")
	public String newRework(
			@ModelAttribute("modelFilter") SearchFilter searchFilter,
			@ModelAttribute("modelNewRework") NewRework newRework, 
			Model model) {
		System.out.println(newRework);
		reworkService.addNewRework(newRework);
	
		return "redirect:/mainfilter/start";
	}
	
	@GetMapping("/isAlreadyExistsRework")
	@ResponseBody
	public String isAlreadyExistsRework(
			@RequestParam("wms") String wms, 
			@RequestParam("reworkNumber") String reworkNumber) {
		
		boolean isAlreadyExistsRework = reworkService.isAlreadyExistsRework(wms, reworkNumber);
		
	    return isAlreadyExistsRework ? "true" : "false";

	}
	@ModelAttribute("server")	
	public String serverNameAttribute(HttpServletRequest servletRequest) {
		String server = servletRequest.getServerName().toString();
		return server;
	}
	@ModelAttribute("port")	
	public String serverPortAttribute(HttpServletRequest servletRequest) {
		Integer port = servletRequest.getServerPort();
		return port.toString();
	}
	
}
