package com.github.panhongan.util.control.workflow;

import com.alibaba.fastjson.JSONObject;
import com.github.panhongan.util.control.workflow.Context;
import com.github.panhongan.util.control.workflow.Workflow;
import com.github.panhongan.util.control.workflow.WorkflowNode;

public class TestWorkflow {
	
	public static void main(String [] args) {
		Context context = new Context();
		context.initInput(JSONObject.parseObject("{\"a\" : 1, \"b\" : \"bb\"}"));
		
		Workflow workflow = new Workflow("MyWorkflow");
		workflow.appendWorkflowNode(new MyWorkflowNode1("WorkflowNode1"));
		workflow.appendWorkflowNode(new MyWorkflowNode2("WorkflowNode2"));
		workflow.process(context);
		
		System.out.println(context.toString());
	}

}

class MyWorkflowNode1 extends WorkflowNode {
	
	public MyWorkflowNode1(String node_name) {
		super(node_name);
	}
	
	@Override
	public Object processData(Object input) {
		return input;
	}
}

class MyWorkflowNode2 extends WorkflowNode {
	
	public MyWorkflowNode2(String node_name) {
		super(node_name);
	}
	
	@Override
	public Object processData(Object input) {
		return input;
	}
}

