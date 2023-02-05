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
import main.dao.model.Rework;
import main.dao.model.ReworkDetail;
import main.dao.model.SearchFilter;
import main.dao.model.Status;
import main.dao.service.AddWhoService;
import main.dao.service.ReworkService;
import main.dao.service.StatusService;
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
			Model model,HttpServletRequest request) {	
		
		archive(searchFilter,model, request);
					
		return "archive";
	}
	
	@PostMapping("/archive")
	public String showArchivePagePOST(
			@ModelAttribute("modelFilter") SearchFilter searchFilter ,
			Model model,HttpServletRequest request) {	
		
		archive(searchFilter,model, request);
					
		return "archive";
	}
	
	/**
	 * like as  href="/showrework/reworknumber_2300 - 2300 is serialkey in dbo.REWORK
	 * */
	@GetMapping("/showrework_archive/{reworknumber}")
	public String showrework(
							@RequestParam(value="isUpdate",required = false) String isUpdate,
							@PathVariable("reworknumber") String reworknumber,
							@ModelAttribute("modelFilter") SearchFilter searchFilter,
							Model model) {
		
		Integer serialkey = Integer.valueOf(reworknumber.replace("reworknumber_", ""));
		Rework rework = reworkService.getRework(serialkey);
		ReworkDto reworkDto = new ReworkDto(rework);
			//modelRework.toStringAll();
		if(isUpdate != null) model.addAttribute("isUpdate","true");
		model.addAttribute("reworkDto",reworkDto);
		
		return "show_rework_archive";
	}
	
	private void archive(SearchFilter searchFilter, Model model,
			HttpServletRequest request) {
	
		List<Tuple2<Rework, List<ReworkDetail>>> mainListReworks = reworkService.findOnSearchParamInArchive(searchFilter.getSearch());
		List<Tuple2<ReworkDto, List<ReworkDetailDto>>> mainListReworksDto = new ArrayList<>();
		List<ReworkDetailDto> serversNameForTitle = new ArrayList<>();
		
		if(mainListReworks.size() != 0) {
			
			for(Tuple2<Rework, List<ReworkDetail>> t : mainListReworks) {
				List<ReworkDetailDto> detailDtos ;//= new ArrayList<>();
				int countServers = t.v2.size();
				ReworkDetailDto[] arrayDetailDtos = new ReworkDetailDto[ countServers ];
							
				ReworkDto reworkDto = new ReworkDto(t.v1); 
				
				for(ReworkDetail listrd : t.v2) {
					switch (listrd.getServer()) {
						case "Дев сервер" :
							{
								arrayDetailDtos[0] = new ReworkDetailDto(listrd);
							}
							break;
						case "ЕКБ ТЭЦ" :
							{
								arrayDetailDtos[1] = new ReworkDetailDto(listrd);
							}
							break;
						case "ЕКБ РЦ Берёзовский" :
							{
								arrayDetailDtos[2] = new ReworkDetailDto(listrd);
							}
							break;
						case "Нефтеюганск" :
							{
								arrayDetailDtos[3] = new ReworkDetailDto(listrd);
							}
							break;
						case "Новосибирск" :
							{
								arrayDetailDtos[4] = new ReworkDetailDto(listrd);
							}
						break;
						case "Уфа" :
							{
								arrayDetailDtos[5] = new ReworkDetailDto(listrd);
							}
						break;
						default: {throw new IllegalArgumentException("Не существующий сервер! : " + listrd.getServer());}
					}
				}
				detailDtos = Arrays.asList(arrayDetailDtos); 
				
				Tuple2<ReworkDto, List<ReworkDetailDto>> tupleDto = new Tuple2<ReworkDto, List<ReworkDetailDto>>(reworkDto, detailDtos);
				mainListReworksDto.add(tupleDto);
			}
			serversNameForTitle.addAll((mainListReworksDto.get(0)).v2);
		}
		
		List<Status> allStatuses = statusService.findAll();
		List<AddWho> allWhoUpdates = addWhoService.findAll();
		

		for (Status rw : allStatuses) {
			rw.setStatus(Util.getUnicodeStatusWebApp(rw.getStatus()));
		}

		model.addAttribute("serversNameForTitle", serversNameForTitle);		
		model.addAttribute("mainListReworksDto", mainListReworksDto);
		model.addAttribute("allWhoUpdates", allWhoUpdates);
	}

}
