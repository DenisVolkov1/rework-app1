package main.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import main.dao.model.ReworkDetail;
import main.dao.service.StatusService;

@RestController
public class ControllerReturnJSON {

	@Autowired
	private StatusService statusService;
	
	@RequestMapping(value = "/mainfilter/tooltip/cell")
    public ResponseEntity<ReworkDetail> generateSignKey(@RequestParam("wms") String wms,@RequestParam("reworkNumber") String reworkNumber,@RequestParam("project") String project)
    {
    	ReworkDetail tooltipData = statusService.getDateTooltip(wms, reworkNumber, project);

    		return new ResponseEntity<ReworkDetail>(tooltipData, HttpStatus.OK);
    }
}
