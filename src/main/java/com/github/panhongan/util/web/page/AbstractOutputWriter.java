package com.github.panhongan.util.web.page;


import com.github.panhongan.util.control.workflow.WorkflowNode;

public abstract class AbstractOutputWriter extends WorkflowNode {
	
	public AbstractOutputWriter(String node_name) {
		super(node_name);
	}

	public abstract Object processData(Object input);

}
