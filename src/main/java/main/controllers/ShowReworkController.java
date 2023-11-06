package main.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import main.dao.model.Project;
import main.dao.model.Rework;
import main.dao.model.SearchFilter;
import main.dao.service.ReworkService;
import main.dao.service.StatusService;
import main.dao.service.ProjectService;
import main.dto.ReworkDto;

@Controller
public class ShowReworkController {
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private ReworkService reworkService;
	
	@Autowired
	private ProjectService projectService;	
	
	/**
	 * like as  href="/showrework/reworknumber_2300 - 2300 is serialkey in dbo.REWORK
	 * */
	@GetMapping("{project}/showrework/{reworknumber}")
	public String showrework(
							@RequestParam(value="isUpdate",required = false) String isUpdate,
							@PathVariable("reworknumber") String reworknumber,
							@ModelAttribute("modelFilter") SearchFilter searchFilter,
							@PathVariable("project") String project,
							Model model) {
		
		Integer serialkey = Integer.valueOf(reworknumber.replace("reworknumber_", ""));
		Rework rework = reworkService.getRework(serialkey);
		ReworkDto reworkDto = new ReworkDto(rework);
			//modelRework.toStringAll();
		if(isUpdate != null) model.addAttribute("isUpdate","true");
		
		Project projectByName = projectService.getProjectByPartURL(project);
			String gradientforheader= projectByName.getGradientForHeader();
		
		model.addAttribute("reworkDto",reworkDto);
		model.addAttribute("project",project);
		model.addAttribute("gradientforheader", gradientforheader);
		
		return "show_rework";
	}

	@PostMapping("/showrework/update/{reworknumber}")
	public String update(@ModelAttribute("modelRework") Rework rework, 
						 @PathVariable("reworknumber") String reworknumber, 
						 Model model) {
		
		Integer serialkey = Integer.valueOf(reworknumber.replace("reworknumber_", ""));
			reworkService.updateRework(serialkey, rework);
				model.addAttribute("isUpdate","true");
		return "redirect:/showrework/reworknumber_{reworknumber}";
	}
	
	@GetMapping("{project}/showrework/delete")
	public String delete(Model model, 
			@RequestParam(value="reworkNumber",required = false) String reworkNumber,
			@PathVariable("project") String project,
			RedirectAttributes attributes) {
		
			reworkService.hideRework(reworkNumber);			
			
			attributes.addAttribute("deleteRework_reworknumber", reworkNumber);
		return "redirect:/"+project;
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
