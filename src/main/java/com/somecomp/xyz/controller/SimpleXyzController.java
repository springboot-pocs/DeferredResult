package com.somecomp.xyz.controller;

import java.util.concurrent.ForkJoinPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
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
	
	@GetMapping("/getTimeoutResults")	
	public DeferredResult<ResponseEntity<?>> getDeferredResultsTimeout(){
		final String METHOD_NAME = "getDeferredResultsTimeout";
		logger.info("START : {}",METHOD_NAME);
		
		/** setting a timeout of 10 seconds - which is passed as a constructor parameter **/
		DeferredResult<ResponseEntity<?>> deferredOutput = new DeferredResult<>(10000l);
		
		
		ForkJoinPool.commonPool().submit(() -> {
			logger.info("processing in separate thread");
			try {
				/** ensuring that the task does not return for atleast 13 seconds which is greater than timeout value **/
				Thread.sleep(13000);
			}catch(InterruptedException e) {
				
			}
			logger.info("completed processing in separate thread");
			deferredOutput.setResult(ResponseEntity.ok("Hello world"));
		});
		
		deferredOutput.onTimeout(() -> {
			/** log the fact that we have timed out **/
			logger.warn("this method {} has timed out ",METHOD_NAME);
			deferredOutput.setErrorResult(ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT)
				      .body("Request timeout occurred."));
		});
		logger.info("done with main thread ");
		return deferredOutput;
	}

	


}
