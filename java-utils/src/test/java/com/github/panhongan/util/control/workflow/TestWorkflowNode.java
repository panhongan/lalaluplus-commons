package com.github.panhongan.util.control.workflow;

import com.alibaba.fastjson.JSONObject;
import com.github.panhongan.util.control.workflow.Context;
import com.github.panhongan.util.control.workflow.WorkflowNode;

public class TestWorkflowNode {
	
	public static void main(String [] args) {
		Context context = new Context();
		context.initInput(JSONObject.parseObject("{\"a\" : 1, \"b\" : \"bb\"}"));
		
		WorkflowNode node = new MyWorkflowNode("MyWorkflowNode");
		System.out.println(node.toString());
		System.out.println(node.getNodeName());
		System.out.println(node.process(context));
		
		System.out.println(context.toString());
	}

}

class MyWorkflowNode extends WorkflowNode {

	public MyWorkflowNode(String node_name) {
		super(node_name);
	}

	@Override
	public Object processData(Object input) {
		return input;
	}
	
}
