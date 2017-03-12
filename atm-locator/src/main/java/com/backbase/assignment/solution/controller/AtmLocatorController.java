package com.backbase.assignment.solution.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.backbase.assignment.solution.config.AbstractUserController;
import com.backbase.assignment.solution.service.AtmRestClient;
import com.backbase.assignment.solution.service.ProcessorFactory;
import com.backbase.assignment.solution.util.Processor;

@Controller
@RequestMapping(path="atm")
public class AtmLocatorController extends AbstractUserController {
	
	private static final Logger log = Logger.getLogger(AtmLocatorController.class);
	
	@Autowired
	private AtmRestClient atmRestClient; 
	
	@Autowired
	private ProcessorFactory processorFactory; 

    @RequestMapping(path = "/raw", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    @ResponseBody
    public ResponseEntity<String> getAllLoacationsInRawFormat(@RequestParam(name="city", required=false) String city){
    	final HttpHeaders httpHeaders= new HttpHeaders();
    	httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    	
    	log.info("User="+ getPrincipal()+", searching ATM by city="+city);
    	
    	return new ResponseEntity<String>(processorFactory.processor(Processor.RAW).filterByCity(atmRestClient.getAllLocations(), city), httpHeaders, HttpStatus.OK);
    }
	
	@RequestMapping(path = "/search", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> searchByCity(@RequestParam(name="city", required=false) String city){
    	final HttpHeaders httpHeaders= new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        
        log.info("User="+ getPrincipal()+", searching ATM by city="+city);
        
        String value = atmRestClient.getAllLocations();
		return new ResponseEntity<String>(processorFactory.processor(Processor.SEARCH).filterByCity(value, city), httpHeaders, HttpStatus.OK);
	}
	

}
