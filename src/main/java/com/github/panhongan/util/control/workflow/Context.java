package com.github.panhongan.util.control.workflow;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

public class Context {
	
	private static final Logger logger = LoggerFactory.getLogger(Context.class);
	
	private List<WorkflowStage> stages = new ArrayList<WorkflowStage>();
	
	private WorkflowNode curr_workflow_node = null;
	
	public void initInput(JSONObject input) {
		if (input != null) {
			// set global input as the output of the first stage. 
			WorkflowStage stage = new WorkflowStage(null, input, null);
			stages.add(stage);
		} else {
			logger.warn("parameter input is null");
		}
	}
	
	public void appendStage(WorkflowStage stage) {
		if (stage != null) {
			stages.add(stage);
		}
	}
	
	public WorkflowStage getLastWorkflowStage() {
		return this.getWorkflowStage(stages.size() - 1);
	}
	
	public WorkflowStage getWorkflowStage(int index) {
		WorkflowStage stage = null;
		if (index >= 0 && index < stages.size()) {
			stage = stages.get(index);
		}
		return stage;
	}
	
	public void clear() {
		stages.clear();
	}
	
	public void setCurrentWorkflowNode(WorkflowNode workflow_node) {
		this.curr_workflow_node = workflow_node;
	}
	
	public WorkflowNode getCurrentWorkflowNode() {
		return curr_workflow_node;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("WorkflowStages:[");
		for (WorkflowStage stage : stages) {
			sb.append(stage.toString());
			sb.append(", ");
		}
		sb.append("]");
		
		return sb.toString();
	}
	
}
