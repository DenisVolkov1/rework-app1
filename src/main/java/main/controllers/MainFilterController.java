package main.controllers;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javax.servlet.http.HttpServletRequest;

import org.jooq.lambda.tuple.Tuple2;
import org.simpleflatmapper.util.ArrayListEnumerable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import main.dao.model.AddWho;
import main.dao.model.NewRework;
import main.dao.model.Rework;
import main.dao.model.ReworkDetail;
import main.dao.model.SearchFilter;
import main.dao.model.Server;
import main.dao.model.Status;
import main.dao.model.Project;
import main.dao.service.AddWhoService;
import main.dao.service.ServerService;
import main.dao.service.ReworkService;
import main.dao.service.StatusService;
import main.dao.service.ProjectService;
import main.dto.ReworkDetailDto;
import main.dto.ReworkDto;
import main.util.Util;

@Controller
public class MainFilterController {
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private ReworkService reworkService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private AddWhoService addWhoService;
	
	
	@PostMapping("/{project}")
	public String showMainPagePOST(
			@ModelAttribute("modelFilter") SearchFilter searchFilter ,
			@PathVariable("project") String project ,
			Model model,HttpServletRequest request) {
		
		main(searchFilter,model, request, project);
		
		return "main";
	}
	
	@GetMapping("/{project}")
	public String showMainPage(
			@ModelAttribute("modelFilter") SearchFilter searchFilter ,
			@PathVariable("project") String project ,
			Model model,HttpServletRequest request) {	
		
		main(searchFilter,model, request, project);
					
		return "main";
	}
	
	private void main(SearchFilter searchFilter,  Model model,
			HttpServletRequest request, String project) {
	
		List<Tuple2<Rework, List<ReworkDetail>>> mainListReworks = reworkService.findOnSearchParam(searchFilter.getSearch(), project);
		List<Tuple2<ReworkDto, List<ReworkDetailDto>>> mainListReworksDto = new ArrayList<>();
		List<ReworkDetailDto> serversNameForTitle = new ArrayList<>();
		
		if(mainListReworks.size() != 0) {
			
			for(Tuple2<Rework, List<ReworkDetail>> t : mainListReworks) {
				List<ReworkDetailDto> detailDtos ;
				int countServers = t.v2.size();
				//
				ReworkDto reworkDto = new ReworkDto(t.v1);
				ReworkDetailDto[] arrayDetailDtos = Util.getArrReworkDetailDto(countServers, t.v2);
							
				detailDtos = Arrays.asList(arrayDetailDtos); 
				
				Tuple2<ReworkDto, List<ReworkDetailDto>> tupleDto = new Tuple2<ReworkDto, List<ReworkDetailDto>>(reworkDto, detailDtos);
				mainListReworksDto.add(tupleDto);
			}
			serversNameForTitle.addAll((mainListReworksDto.get(0)).v2);
		}
		
		List<Status> allStatuses = statusService.findAll();
		List<AddWho> allWhoUpdates = addWhoService.findAll();
		List<Project> allProjects = projectService.findAll();
		Project projectByName = projectService.getProjectByPartURL(project);
			String header= projectByName.getName();
			String field1= projectByName.getField1();
			String field2= projectByName.getField2();
			String gradientforheader= projectByName.getGradientForHeader();

		for (Status rw : allStatuses) {
			rw.setStatus(Util.getUnicodeStatusWebApp(rw.getStatus()));
		}

		model.addAttribute("serversNameForTitle", serversNameForTitle);		
		model.addAttribute("mainListReworksDto", mainListReworksDto);
		model.addAttribute("allStatuses", allStatuses);
		model.addAttribute("allWhoUpdates", allWhoUpdates);
		model.addAttribute("projects", allProjects);
		model.addAttribute("header", header);
		model.addAttribute("project", project);
		model.addAttribute("field1", field1);
		model.addAttribute("field2", field2);
		model.addAttribute("gradientforheader", gradientforheader);
	}

	@GetMapping("/{project}/updatestatus")
	@ResponseBody
	public String updateStatus(
			@RequestParam("reworkNumber") String reworkNumber,
			@RequestParam("server") String server,
			@RequestParam("valueStatus")  String valueStatus,
			@RequestParam("whoUpdate")    String whoUpdate,
			@PathVariable("project") String project
			) {
				
			String response = statusService.updateStatus(reworkNumber, server, Util.getStatusSql(valueStatus), whoUpdate);
		return response;
	}
	
	@GetMapping("/{project}/updatedate")
	@ResponseBody
	public String updateDate(
			@RequestParam("reworkNumber") String reworkNumber,
			@RequestParam("server") String server,
			@RequestParam("date")  String date,
			@PathVariable("project") String project
			) {
				
			String response = statusService.updateDate(reworkNumber, server, date);
		return response;
	}

	/**
	 * like as  href="/showrework/reworknumber_2300 - 2300 is serialkey in dbo.REWORK
	 * */
	
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
