package com.somecomp.xyz.controller;

import java.util.concurrent.ForkJoinPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@RestController
public class SimpleXyzController {

	private static final Logger logger = LogManager.getLogger(SimpleXyzController.class);
	
	@GetMapping("/getResults")	
	public DeferredResult<ResponseEntity<?>> getDeferredResults(){
		final String METHOD_NAME = "getDeferredResults";
		logger.info("START : {}",METHOD_NAME);
		
		DeferredResult<ResponseEntity<?>> deferredOutput = new DeferredResult<>();
		
		ForkJoinPool.commonPool().submit(() -> {
			logger.info("processing in separate thread");
			try {
				Thread.sleep(13000);
			}catch(InterruptedException e) {
				
			}
			deferredOutput.setResult(ResponseEntity.ok("Hello world"));
		});
		logger.info("done");
		return deferredOutput;
	}

	


}
