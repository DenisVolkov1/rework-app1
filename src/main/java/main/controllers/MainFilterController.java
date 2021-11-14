package main.controllers;


import java.util.List;
import javax.servlet.http.HttpServletRequest;

import org.jooq.lambda.tuple.Tuple2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import main.dao.model.NewRework;
import main.dao.model.Rework;
import main.dao.model.ReworkDetail;
import main.dao.model.SearchFilter;
import main.dao.model.Status;
import main.dao.model.Wms;
import main.dao.service.ReworkService;
import main.dao.service.StatusService;
import main.dao.service.WmsService;

@Controller
public class MainFilterController {
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private ReworkService reworkService;
	
	@Autowired
	private WmsService wmsService;

	@GetMapping("/mainfilter")
	public String showMainFilterPage(Model model,HttpServletRequest request) {	
		List<Tuple2<Rework, List<ReworkDetail>>> modelsForRows = reworkService.findAll();
		Tuple2<Rework, List<ReworkDetail>> modelForTitle = modelsForRows.get(0);
		List<Status> allStatuses = statusService.findAll();
		List<Wms> allWms = wmsService.findAll();
		
		model.addAttribute("modelForTitle", modelForTitle);
		model.addAttribute("modelsForRows", modelsForRows);
		model.addAttribute("allStatuses", allStatuses);
		model.addAttribute("allWms", allWms);
		
		return "main_filter";
	}
	
	@GetMapping("/mainfilter/start")
	public String showMainFilterPage2(@ModelAttribute("modelFilter") SearchFilter searchFilter , Model model,HttpServletRequest request) {	

		List<Wms> allWms = wmsService.findAll();
		model.addAttribute("allWms", allWms);
		
		return "main_filter_start";
	}
	
	@PostMapping("/mainfilter/search")
	public String showMainFilterSearch(@ModelAttribute("modelFilter") SearchFilter searchFilter , Model model,HttpServletRequest request) {	
		if(searchFilter.getWms() != null ) {
			System.out.println(searchFilter);
			List<Tuple2<Rework, List<ReworkDetail>>> modelsForRows = null;
			if(searchFilter.getSearch().isEmpty()) {
				modelsForRows = reworkService.findOnWms(searchFilter.getWms());
			} else {
				System.out.println("search");
				modelsForRows = reworkService.findOnWmsAndSearchParams(searchFilter.getWms(), searchFilter.getSearch());
			}
			
			Tuple2<Rework, List<ReworkDetail>> modelForTitle = modelsForRows.get(0);
			List<Status> allStatuses = statusService.findAll();
			List<Wms> allWms = wmsService.findAll();
			
			model.addAttribute("modelForTitle", modelForTitle);
			model.addAttribute("modelsForRows", modelsForRows);
			model.addAttribute("allStatuses", allStatuses);
			model.addAttribute("allWms", allWms);
		}
		
		System.out.println(searchFilter);
		
		
		return "main_filter_search";//"main_filter_search";
	}
	
	@GetMapping("/mainfilter/updatestatus")
	@ResponseBody
	public String updateStatus(@RequestParam("wms") String wms,@RequestParam("reworkNumber") String reworkNumber,@RequestParam("project") String project,@RequestParam("valueStatus") String valueStatus) {
			statusService.updateStatus(wms, reworkNumber, project, valueStatus);
		return "updateIsDone";
	}
	/**
	 * like as  href="/showrework/serialkeyrework_2300 - 2300 is serialkey in dbo.REWORK
	 * */
	@GetMapping("/showrework/{serialkeyrework}")
	public String showrework(
							@RequestParam(value="isUpdate",required = false) String isUpdate,
							@PathVariable("serialkeyrework") String serialkeyrework,
							@ModelAttribute("modelFilter") SearchFilter searchFilter,
							Model model) {
		Integer serialkey = Integer.valueOf(serialkeyrework.replace("serialkeyrework_", ""));
		Rework modelRework = reworkService.getRework(serialkey);
		if(isUpdate != null) model.addAttribute("isUpdate","true");
		model.addAttribute("modelRework",modelRework);
		
		return "show_rework";
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
