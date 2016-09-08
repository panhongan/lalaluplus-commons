package com.github.panhongan.util.control.workflow;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Workflow implements Workflowable {
	
	private static final Logger logger = LoggerFactory.getLogger(Workflow.class);
	
	private String workflow_name = null;
	
	private List<WorkflowNode> workflow = new ArrayList<WorkflowNode>();
	
	public Workflow(String workflow_name) {
		this.workflow_name = workflow_name;
	}
	
	public String getWorkflowName() {
		return workflow_name;
	}
	
	public void appendWorkflowNode(WorkflowNode workflow_node) {
		if (workflow_node != null) {
			workflow.add(workflow_node);
		} else {
			logger.warn("parameter workflow_node is null");
		}
	}

	/**
	 * The output field of the first stage in context should not be null.
	 */
	@Override
	public boolean process(Context context) {
		boolean ret = false;
		
		for (WorkflowNode node : workflow) {
			ret = node.process(context);
			if (!ret) {
				logger.warn("{} process failed", node.getNodeName());
				break;
			}
			
			logger.info("{} process succeed", node.getNodeName());
		}
		
		return ret;
	}

}
