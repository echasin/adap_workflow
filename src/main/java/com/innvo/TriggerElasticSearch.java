package com.innvo;

import java.util.HashMap;
import java.util.Map;

import org.drools.core.event.DebugProcessEventListener;
import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TriggerElasticSearch {
	private final static Logger log = LoggerFactory.getLogger(TriggerElasticSearch.class);

	public static void restCall(long assetId,String assetName){
		log.debug("Asset ID in TriggerElasticSearch :" +assetId);
		log.debug("Asset Name in TriggerElasticSearch :" +assetName);
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks.getKieClasspathContainer();
		KieSession kSession = kContainer.newKieSession("ksession-process");
		kSession.getWorkItemManager().registerWorkItemHandler("GetResponseDetailByAssetId", new GetResponseDetailByAssetId());
		//kSession.getWorkItemManager().registerWorkItemHandler("SaveScore", new SaveScore());
		kSession.addEventListener(new DebugProcessEventListener());
		kSession.addEventListener(new DebugAgendaEventListener());
		kSession.addEventListener(new DebugRuleRuntimeEventListener());
		ks.getLoggers().newFileLogger(kSession, "./workflowlog");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("assetId", assetId);
		params.put("assetName", assetName);
		params.put("gatewayhostname", "localhost:8099");
		kSession.startProcess("elasticsearchdnsresponsedetail", params);
		kSession.fireAllRules();
		kSession.dispose();
	
	}
}
