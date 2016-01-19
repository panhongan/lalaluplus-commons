package com.github.panhongan.util.control.workflow;

import com.github.panhongan.util.StringUtil;

public class WorkflowStage {
	
	private Object input = null;
	
	private Object output = null;
	
	private WorkflowNode workflow_node = null;
	
	public WorkflowStage() {
		
	}
	
	public WorkflowStage(Object input, Object output, 
			WorkflowNode workflow_node) {
		this.input = input;
		this.output = output;
		this.workflow_node = workflow_node;
	}
	
	public void setInput(Object input) {
		this.input = input;
	}
	
	public Object getInput() {
		return input;
	}
	
	public void setOutput(Object output) {
		this.output = output;
	}
	
	public Object getOutput() {
		return output;
	}
	
	public void setWorkflowNode(WorkflowNode workflow_node) {
		this.workflow_node = workflow_node;
	}
	
	public Workflowable getWorkflowNode() {
		return workflow_node;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("WorkflowStage(");
		sb.append(StringUtil.toString(workflow_node));
		sb.append(", ");
		sb.append(StringUtil.toString(input));
		sb.append(", ");
		sb.append(StringUtil.toString(output));		
		sb.append(")");
		
		return sb.toString();
	}
}
