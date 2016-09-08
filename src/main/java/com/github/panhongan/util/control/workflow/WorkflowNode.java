package com.github.panhongan.util.control.workflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class WorkflowNode implements Workflowable {
	
	private static final Logger logger = LoggerFactory.getLogger(WorkflowNode.class);
	
	private Context curr_context = null;
	
	private String node_name = "uknown";
	
	public WorkflowNode() {
		
	}
	
	public WorkflowNode(String node_name) {
		this.node_name = node_name;
	}
	
	public String getNodeName() {
		return node_name;
	}
	
	public Context getContext() {
		return curr_context;
	}

	@Override
	public boolean process(Context context) {
		boolean ret = false;
		
		if (context != null) {
			context.setCurrentWorkflowNode(this);
			this.curr_context = context;
			
			WorkflowStage stage = context.getLastWorkflowStage();
			if (stage != null) {
				// process input
				Object output = this.processData(stage.getOutput());
				
				// append current stage
				WorkflowStage new_stage = new WorkflowStage(stage.getOutput(), output, this);
				context.appendStage(new_stage);
				
				ret = (output != null);
			} else {
				logger.warn("No workflow stage need to be processed");
			}
		} else {
			logger.warn("parameter context is null");
		}
		
		return ret;
	}
	
	@Override
	public String toString() {
		return node_name;
	}
	
	abstract public Object processData(Object input);
}
