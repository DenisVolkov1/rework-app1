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
import main.dao.model.Wms;
import main.dao.service.AddWhoService;
import main.dao.service.ServerService;
import main.dao.service.ReworkService;
import main.dao.service.StatusService;
import main.dao.service.WmsService;
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
	private ServerService serverService;
	
	@Autowired
	private AddWhoService addWhoService;
	
	@PostMapping("/main")
	public String showMainPagePOST(
			@ModelAttribute("modelFilter") SearchFilter searchFilter ,
			@ModelAttribute("deleteRework_wms") String deleteRework_wms,
			@ModelAttribute("deleteRework_reworknumber") String deleteRework_reworknumber,
			Model model,HttpServletRequest request) {
		
		main(searchFilter, deleteRework_wms, deleteRework_reworknumber,model, request);
		
		return "main";
	}
	
	@GetMapping("/main")
	public String showMainPage(
			@ModelAttribute("modelFilter") SearchFilter searchFilter ,
			@ModelAttribute("deleteRework_wms") String deleteRework_wms,
			@ModelAttribute("deleteRework_reworknumber") String deleteRework_reworknumber,
			Model model,HttpServletRequest request) {	
		
		main(searchFilter, deleteRework_wms, deleteRework_reworknumber,model, request);
					
		return "main";
	}
	
	private void main(SearchFilter searchFilter, String deleteRework_wms, String deleteRework_reworknumber, Model model,
			HttpServletRequest request) {
	
		List<Tuple2<Rework, List<ReworkDetail>>> mainListReworks = reworkService.findOnSearchParam(searchFilter.getSearch());
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
		model.addAttribute("allStatuses", allStatuses);
		model.addAttribute("allWhoUpdates", allWhoUpdates);
	}

	@GetMapping("/main/updatestatus")
	@ResponseBody
	public String updateStatus(
			@RequestParam("reworkNumber") String reworkNumber,
			@RequestParam("server") String server,
			@RequestParam("valueStatus")  String valueStatus,
			@RequestParam("whoUpdate")    String whoUpdate
			) {
		
		System.out.println( reworkNumber+" "+ server+" "+ Util.getStatusSql(valueStatus)+" "+ whoUpdate);
			
			statusService.updateStatus( reworkNumber, server, Util.getStatusSql(valueStatus), whoUpdate);
		return "updateIsDone";
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
