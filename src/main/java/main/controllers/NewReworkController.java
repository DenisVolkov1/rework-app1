package main.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import main.dao.model.AddWho;
import main.dao.model.NewRework;
import main.dao.model.Server;
import main.dao.model.SearchFilter;
import main.dao.model.Status;
import main.dao.model.Project;
import main.dao.service.AddWhoService;
import main.dao.service.ServerService;
import main.dao.service.ReworkService;
import main.dao.service.StatusService;
import main.dao.service.ProjectService;
import main.util.Util;

@Controller
@RequestMapping(produces = "text/plain;charset=UTF-8")
public class NewReworkController {
	@Autowired
	private AddWhoService addWhoService;
	@Autowired
	private ReworkService reworkService;
	@Autowired
	private ProjectService projectService;

	@GetMapping("/{project}/newrework")
	public String newReworkShowForm(
			@ModelAttribute("modelFilter") SearchFilter searchFilter , 
			@ModelAttribute("modelNewRework") NewRework rework,
			@PathVariable("project") String project ,
			Model model) {
		
		List<AddWho> allAddWho = addWhoService.findAll();
		Project projectByName = projectService.getProjectByPartURL(project);
			String field1= projectByName.getField1();
			String field2= projectByName.getField2();
			String gradientforheader= projectByName.getGradientForHeader();

		model.addAttribute("allAddWho", allAddWho);
		model.addAttribute("field1", field1);
		model.addAttribute("field2", field2);
		model.addAttribute("project", project);
		model.addAttribute("gradientforheader", gradientforheader);
		
		return "new_rework";
	}
	
	@PostMapping("{project}/new")
	public String newRework(
			@ModelAttribute("modelFilter") 		   SearchFilter searchFilter,
			@ModelAttribute("modelNewRework")      NewRework newRework,
			@ModelAttribute("newInsertRework_wms") String newInsertRework_wms,
			@ModelAttribute("newInsertRework_reworknumber") String newInsertRework_description,
			@ModelAttribute("newInsertRework_status") String newInsertRework_status,
			@PathVariable("project") String project ,
			Model model,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes) {
		
		request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
		// Save rework to base	
		reworkService.addNewRework(newRework,project); 
		
		redirectAttributes.addFlashAttribute("modelFilter", searchFilter);
		redirectAttributes.addFlashAttribute("newInsertRework_status", newInsertRework_status);
		return "redirect:/"+project;
	}
	
	@GetMapping("/{project}/isAlreadyExistsRework")
	@ResponseBody
	public String isAlreadyExistsRework(
			@RequestParam("description") String description,
			@PathVariable("project") String project) {
		
		boolean isAlreadyExistsRework = reworkService.isAlreadyExistsRework(description, project);
		
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
