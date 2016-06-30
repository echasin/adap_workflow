package com.innvo.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.innvo.RetrieveLocationByAssetId;
import com.innvo.SaveScore;

import org.drools.core.event.DebugProcessEventListener;
import org.jbpm.workflow.instance.WorkflowRuntimeException;
import org.json.JSONException;
import org.kie.api.KieServices;
import org.kie.api.event.rule.DebugAgendaEventListener;
import org.kie.api.event.rule.DebugRuleRuntimeEventListener;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for managing Score.
 */
@RestController
@RequestMapping("/api")
public class AssetResource {

	private final Logger log = LoggerFactory.getLogger(AssetResource.class);

	/**
	 * GET ->Start the workflow in turn fire the rules.
	 * 
	 * @return
	 * @throws JSONException
	 * @throws FileNotFoundException 
	 * @throws YamlException 
	 */
	@RequestMapping(value = "/workFlow/{assetId}/{assetRecordType}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed
	public String startWorkFlow(@PathVariable("assetId") long assetId, @PathVariable("assetRecordType") String assetRecordType,
			HttpServletRequest request, Principal principal)
			throws JSONException, FileNotFoundException, YamlException {
		String result = null;
		log.info("Pass Asset ID in Process to get started : " + assetId + "\t " + assetRecordType);
		result = "{\"Route Found\":\"SUCCESS\"}";
		String path = request.getSession().getServletContext()
				.getRealPath("/WEB-INF/classes/config/application-dev.yml");
		YamlReader reader = new YamlReader(new FileReader(path));
		Object fileContent = reader.read();
		Map map = (Map) fileContent;
		String hostName = map.get("hostname").toString();
		String gatewayHostName = map.get("gatewayhostname").toString();
		try {
			// load up the knowledge base
			KieServices ks = KieServices.Factory.get();
			KieContainer kContainer = ks.getKieClasspathContainer();
			KieSession kSession = kContainer.newKieSession("ksession-process");
			kSession.getWorkItemManager().registerWorkItemHandler("RetrieveLocationByAssetId", new RetrieveLocationByAssetId());
			kSession.getWorkItemManager().registerWorkItemHandler("SaveScore", new SaveScore());
			kSession.addEventListener(new DebugProcessEventListener());
			kSession.addEventListener(new DebugAgendaEventListener());
			kSession.addEventListener(new DebugRuleRuntimeEventListener());
			ks.getLoggers().newFileLogger(kSession, "./workflowlog");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("assetId", assetId);
			params.put("hostname", hostName);
			params.put("gatewayhostname", gatewayHostName);
			kSession.startProcess("innvoprocessassetid", params);
			kSession.fireAllRules();
			kSession.dispose();
		} catch (WorkflowRuntimeException wfre) {

			String msg = "An exception happened in "

					+ "process instance [" + wfre.getProcessInstanceId()

					+ "] of process [" + wfre.getProcessId()

					+ "] in node [id: " + wfre.getNodeId()

					+ ", name: " + wfre.getNodeName()

					+ "] and variable " + "Filter ID" + " had the value [" + wfre.getVariables().get("filterId")

					+ "]";

			log.warn("workflow runtime exception caught when passing filter id as " + msg);
			result = "{\"No Route Found\":\"" + msg + "\"}";
		}
		return result;
	}
	
	
	
}