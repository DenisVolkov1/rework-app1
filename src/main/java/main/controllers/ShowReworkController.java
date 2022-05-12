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

import main.dao.model.Rework;
import main.dao.model.SearchFilter;
import main.dao.service.ReworkService;
import main.dao.service.StatusService;
import main.dao.service.WmsService;

@Controller
public class ShowReworkController {
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private ReworkService reworkService;
	
	@Autowired
	private WmsService wmsService;	

	@PostMapping("/showrework/update/{serialkeyrework}")
	public String update(@ModelAttribute("modelRework") Rework rework, 
						 @PathVariable("serialkeyrework") int serialkeyrework, 
						 Model model) {
		Integer serialkey = Integer.valueOf(serialkeyrework);
			reworkService.updateRework(serialkey, rework);
				model.addAttribute("isUpdate","true");
		return "redirect:/showrework/serialkeyrework_{serialkeyrework}";
	}
	
	@GetMapping("/showrework/delete")
	public String delete(Model model, 
			@RequestParam(value="reworkNumber",required = false) String reworkNumber,
			@RequestParam(value="wms",required = false)          String wms,
			RedirectAttributes attributes) {
		
			reworkService.deleteRework(wms, reworkNumber);			
			
			attributes.addAttribute("deleteRework_wms", wms);
			attributes.addAttribute("deleteRework_reworknumber", reworkNumber);
		return "redirect:/mainfilter/start";
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
