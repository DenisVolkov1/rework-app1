package main.controllers;

import java.util.ArrayList;
import java.util.Arrays;
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

import main.dao.model.AddWho;
import main.dao.model.Archive;
import main.dao.model.Rework;
import main.dao.model.ReworkDetail;
import main.dao.model.SearchFilter;
import main.dao.model.Status;
import main.dao.service.AddWhoService;
import main.dao.service.ReworkService;
import main.dao.service.StatusService;
import main.dto.ArchiveDto;
import main.dto.ReworkDetailDto;
import main.dto.ReworkDto;
import main.util.Util;

@Controller
public class ArchiveReworkController {
	
	@Autowired
	private StatusService statusService;
	
	@Autowired
	private ReworkService reworkService;
	
	@Autowired
	private AddWhoService addWhoService;
	
	@GetMapping("/archive")
	public String showArchivePageGET(
			@ModelAttribute("modelFilter") SearchFilter searchFilter ,
			@RequestParam(value="project",required = false) String project,
			Model model,HttpServletRequest request) {
		
		archive(searchFilter,model, request);
		model.addAttribute("project",project);
					
		return "archive";
	}
	
	@PostMapping("/archive")
	public String showArchivePagePOST(
			@ModelAttribute("modelFilter") SearchFilter searchFilter ,
			@RequestParam(value="project",required = false) String project,
			Model model,HttpServletRequest request) {	
		
		archive(searchFilter,model, request);
		model.addAttribute("project",project);
					
		return "archive";
	}
	
	/**
	 * like as  href="/showrework/reworknumber_2300 - 2300 is serialkey in dbo.REWORK
	 * */
	@GetMapping("/showrework_archive/{reworknumber}")
	public String showrework(
							@PathVariable("reworknumber") String reworknumber,
							@ModelAttribute("modelFilter") SearchFilter searchFilter,
							@RequestParam(value="project",required = false) String project,
							Model model) {
		
		Integer serialkey = Integer.valueOf(reworknumber.replace("reworknumber_", ""));
		Rework rework = reworkService.getRework(serialkey);
		ReworkDto reworkDto = new ReworkDto(rework);
		//
		model.addAttribute("reworkDto",reworkDto);
		model.addAttribute("project",project);
		
		return "show_rework_archive";
	}
	
	private void archive(SearchFilter searchFilter, Model model,
			HttpServletRequest request) {
	
		List<Archive> listArchives = reworkService.findOnSearchParamInArchive(searchFilter.getSearch());
		List<ArchiveDto> listArchivesDto = new ArrayList<>();
		
		if(listArchives.size() != 0) {
			
			for(Archive arc : listArchives) {
				ArchiveDto archiveDto = new ArchiveDto(arc);
				listArchivesDto.add(archiveDto);
			}
		}
				
		model.addAttribute("listArchivesDto", listArchivesDto);
	}

}
