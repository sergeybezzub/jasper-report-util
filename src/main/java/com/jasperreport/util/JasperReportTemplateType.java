package com.jasperreport.util;

public enum JasperReportTemplateType 
{
	DUMMY("DummyReport.jasper", "DummyReport.jrxml"), MAIN("MainReport.jasper", "MainReport.jrxml"), SUBREPORT1("Subreport1.jasper", "Subreport1.jrxml"), SUBREPORT2("Subreport2.jasper","Subreport2.jrxml");
	
	private String path;
	private String xmlPath;
	
	private JasperReportTemplateType(String path, String xmlPath)
	{
		this.path=path;
	}

	public String getPath()
	{
		return path;
	}

	public String getXmlPath() 
	{
		return xmlPath;
	}

}
