package com.github.panhongan.util.control.workflow;

import com.alibaba.fastjson.JSONObject;
import com.github.panhongan.util.control.workflow.Context;
import com.github.panhongan.util.control.workflow.WorkflowStage;

public class TestContext {
	
	public static void main(String [] args) {
		Context context = new Context();
		context.initInput(JSONObject.parseObject("{\"a\" : 1, \"b\" : \"bb\"}"));
		System.out.println(context.toString());
		
		WorkflowStage stage = new WorkflowStage(null, null, null);
		context.appendStage(stage);
		System.out.println(context.toString());
	}

}
