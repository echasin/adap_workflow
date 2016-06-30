package com.innvo;

import org.kie.api.runtime.process.WorkItem;
import org.kie.api.runtime.process.WorkItemHandler;
import org.kie.api.runtime.process.WorkItemManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SaveScore implements WorkItemHandler {
	public static ScoreModel scoreVal;

	public SaveScore() {
		// empty
	}

	public SaveScore(ScoreModel scoreVal) {
		SaveScore.scoreVal = scoreVal;
	}

	private final Logger log = LoggerFactory.getLogger(SaveScore.class);

	public void abortWorkItem(WorkItem workItem, WorkItemManager manager) {
		manager.abortWorkItem(workItem.getId());
		}

	public void executeWorkItem(WorkItem workItem, WorkItemManager manager) {
		String hostName = (String) workItem.getParameter("hostname");
		String gatewayHostName = (String) workItem.getParameter("gatewayhostname");
		SaveScoreExecutor saveScoreExecutor = new SaveScoreExecutor();
		try {
			saveScoreExecutor.saveScore(scoreVal, gatewayHostName);
		} catch (Exception e) {
			 log.error("Threw a Exception in SaveScore::executeWorkItem, full stack trace follows:", e);
		} 

	}

}
