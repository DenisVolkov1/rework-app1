package main.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.jooq.lambda.tuple.Tuple2;

import main.dao.model.Rework;
import main.dao.model.ReworkDetail;
import main.dao.model.Status;
import main.dao.service.ReworkService;
import main.dao.service.StatusService;


@Controller
@RequestMapping(produces = "text/plain;charset=UTF-8")
public class OneController {
	
	@Autowired
	private ReworkService reworkService;
	@Autowired
	private StatusService statusService;

	@GetMapping("/hello_test")
	public String twoPage(ServletRequest request) {
				
		
//		  for(Tuple2<Rework, List<ReworkDetail>> t : reworkService.findAll()) {
//			  System.out.println(t); 
//		} 
//		  System.out.println("Status===============");
//		  for(Status status : statusService.findAll()) { System.out.println(status); }
//		 System.out.println("Host = " + request.getServerName());
//		 System.out.println("Port = " + request.getServerPort());
		 
		 
//		Rework r =reworkService.getRework(2300);
		//System.out.println("test");
		return "hello_test";
	}

}
