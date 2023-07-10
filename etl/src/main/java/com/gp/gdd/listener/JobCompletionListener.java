package com.gp.gdd.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

/**
 * Program to print information that the batch has completed
 */
public class JobCompletionListener extends JobExecutionListenerSupport {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	/** 
	 * @param jobExecution
	 */
	@Override
	public void afterJob(JobExecution jobExecution) {
		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			logger.info("BATCH JOB COMPLETED SUCCESSFULLY");
		}
	}

}
