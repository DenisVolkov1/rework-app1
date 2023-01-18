package main.controllers;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
	
	@GetMapping("/main")
	public String showMainPage(
			@ModelAttribute("modelFilter") SearchFilter searchFilter ,
			@ModelAttribute("deleteRework_wms") String deleteRework_wms,
			@ModelAttribute("deleteRework_reworknumber") String deleteRework_reworknumber,
			Model model,HttpServletRequest request) {	
		
//		List<Server> serversNameForTitle = serverService.findAll();
//		serversNameForTitle.clear();
		
		List<Tuple2<Rework, List<ReworkDetail>>> mainListReworks = reworkService.findOnSearchParam(searchFilter.getSearch());
		
		
//		for (int i = 0; i < mainListReworks.size(); i++) {
//			Tuple2<Rework, List<ReworkDetail>> it = mainListReworks.get(i);	
//			System.out.println(it.v1);
//			for (ReworkDetail tuple2 : it.v2) {
//				//System.out.println("----"+tuple2);
//			}
//		}

		List<Tuple2<ReworkDto, List<ReworkDetailDto>>> mainListReworksDto = new ArrayList<>();
		
		for(Tuple2<Rework, List<ReworkDetail>> t : mainListReworks) {
			List<ReworkDetailDto> detailDtos = new ArrayList<>(6);
			ReworkDto reworkDto = new ReworkDto(t.v1); 
			
			for(ReworkDetail listrd : t.v2) {
				switch (listrd.getServer()) {
					case "Дев сервер" :
						{
							detailDtos.add( new ReworkDetailDto(listrd));
						}
						break;
					case "ЕКБ ТЭЦ" :
						{
							detailDtos.add( new ReworkDetailDto(listrd));
						}
						break;
					case "ЕКБ РЦ Берёзовский" :
						{
							detailDtos.add( new ReworkDetailDto(listrd));
						}
						break;
					case "Нефтеюганск" :
						{
							detailDtos.add( new ReworkDetailDto(listrd));
						}
						break;
					case "Новосибирск" :
						{
							detailDtos.add( new ReworkDetailDto(listrd));
						}
					break;
					case "Уфа" :
						{
							detailDtos.add( new ReworkDetailDto(listrd));
						}
					break;
					default: {throw new IllegalArgumentException("Не существующий сервер! : " + listrd.getServer());}
				}
			}
			Tuple2<ReworkDto, List<ReworkDetailDto>> tupleDto = new Tuple2<ReworkDto, List<ReworkDetailDto>>(reworkDto, detailDtos);
			mainListReworksDto.add(tupleDto);
		}
		
		
		List<ReworkDetailDto> serversNameForTitle = (mainListReworksDto.get(0)).v2;
		List<Status> allStatuses = statusService.findAll();
		
		
//		for (var x : mainListReworks) {
//			for (ReworkDetail rw : x.v2) {
//				rw.setStatus(Util.getUnicodeStatusWebApp(rw.getStatus()));
//			}
//		}

		for (Status rw : allStatuses) {
			rw.setStatus(Util.getUnicodeStatusWebApp(rw.getStatus()));
		}
		model.addAttribute("serversNameForTitle", serversNameForTitle);		
		model.addAttribute("mainListReworksDto", mainListReworksDto);
//		model.addAttribute("deleteRework_wms", deleteRework_wms);
//		model.addAttribute("deleteRework_reworknumber", deleteRework_reworknumber);

		
		return "main";
	}
	
	
	@GetMapping("/mainfilter/updatestatus")
	@ResponseBody
	public String updateStatus(
			@RequestParam("wms")          String wms,
			@RequestParam("reworkNumber") String reworkNumber,
			@RequestParam("project")      String project,
			@RequestParam("valueStatus")  String valueStatus,
			@RequestParam("whoUpdate")    String whoUpdate
			) {
			
			statusService.updateStatus(wms, reworkNumber, project, Util.getStatusSql(valueStatus), whoUpdate);
		return "updateIsDone";
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
