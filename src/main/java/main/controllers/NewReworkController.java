package main.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import main.dao.model.Wms;
import main.dao.service.AddWhoService;
import main.dao.service.ServerService;
import main.dao.service.ReworkService;
import main.dao.service.StatusService;
import main.dao.service.WmsService;
import main.util.Util;

@Controller
@RequestMapping(produces = "text/plain;charset=UTF-8")
public class NewReworkController {
	@Autowired
	private StatusService statusService;
	@Autowired
	private AddWhoService addWhoService;
	@Autowired
	private ReworkService reworkService;
	@Autowired
	private ServerService projectService;
	@Autowired
	private WmsService wmsService;
	
	@GetMapping("/newrework")
	public String newReworkShowForm(
			@ModelAttribute("modelFilter") SearchFilter searchFilter , 
			@ModelAttribute("modelNewRework") NewRework rework, 
			Model model) {
		
//		List<Wms> allWms = wmsService.findAll();
//		List<Server> allServers = projectService.findAll();
//		List<Status> allStatuses = statusService.findAll();
		List<AddWho> allAddWho = addWhoService.findAll();
//		
//		allStatuses.removeIf(s -> s.getStatus().isEmpty());
//		
//		for (Status s : allStatuses) {
//			s.setStatus(Util.getUnicodeStatusWebApp(s.getStatus()));
//		}
//		model.addAttribute("allWms", allWms);
//		model.addAttribute("allProjects", allServers);
//		model.addAttribute("allStatuses", allStatuses);
		model.addAttribute("allAddWho", allAddWho);
		
		return "new_rework";
	}
	
	@PostMapping("/newrework/new")
	public String newRework(
			@ModelAttribute("modelFilter") 		   SearchFilter searchFilter,
			@ModelAttribute("modelNewRework")      NewRework newRework,
			@ModelAttribute("newInsertRework_wms") String newInsertRework_wms,
			@ModelAttribute("newInsertRework_reworknumber") String newInsertRework_description,
			@ModelAttribute("newInsertRework_status") String newInsertRework_status,	
			Model model,
			HttpServletRequest request,
			final RedirectAttributes redirectAttributes) {
		
		request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
		

			//newRework.setStatus(Util.getStatusSql(newRework.getStatus()));
		// Save rework to base	
		reworkService.addNewRework(newRework); 
		
			//newInsertRework_server = newRework.getServer();

			
			redirectAttributes.addFlashAttribute("modelFilter", searchFilter);
			//redirectAttributes.addFlashAttribute("newInsertRework_server", newInsertRework_server);
			redirectAttributes.addFlashAttribute("newInsertRework_status", newInsertRework_status);
		return "redirect:/main";
	}
	
	@GetMapping("/isAlreadyExistsRework")
	@ResponseBody
	public String isAlreadyExistsRework(
			@RequestParam("description") String description) {
		
		boolean isAlreadyExistsRework = reworkService.isAlreadyExistsRework(description);
		
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
