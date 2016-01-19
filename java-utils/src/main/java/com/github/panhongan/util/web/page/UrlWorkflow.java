package com.github.panhongan.util.web.page;

import com.github.panhongan.util.control.workflow.Context;
import com.github.panhongan.util.control.workflow.Workflow;
import com.github.panhongan.util.control.workflow.Workflowable;

public class UrlWorkflow implements Workflowable {
	
	private Workflow workflow = new Workflow("UrlWorkflow");
	
	public void addCrawler(Crawler crawler) {
		if (crawler != null) {
			workflow.appendWorkflowNode(crawler);
		}
	}
	
	public void addWebPageParser(AbstractWebPageParser webpage_parser) {
		if (webpage_parser != null) {
			workflow.appendWorkflowNode(webpage_parser);
		}
	}
	
	public void addOutputWriter(AbstractOutputWriter output_writer) {
		if (output_writer != null) {
			workflow.appendWorkflowNode(output_writer);
		}
	}
	
	@Override
	public boolean process(Context context) {
		return workflow.process(context);
	}
	
	/*
	private Crawler webpage_crawler = null;
	
	private WebPageProcessor webpage_processor = null;
	
	public void setWebPageCrawler(Crawler crawler) {
		this.webpage_crawler = crawler; 
	}
	
	public void setWebPageProcessor(WebPageProcessor processor) {
		this.webpage_processor = processor;
	}
	
	public void get(String url) {
		if (webpage_crawler != null) {
			Document doc = webpage_crawler.get(url);
			if (webpage_processor != null) {
				webpage_processor.processWebPage(doc);
			}
		}
	}
	
	public void post(String url) {
		if (webpage_crawler != null) {
			Document doc = webpage_crawler.post(url);
			if (webpage_processor != null) {
				webpage_processor.processWebPage(doc);
			}
		}
	}
	*/

}
