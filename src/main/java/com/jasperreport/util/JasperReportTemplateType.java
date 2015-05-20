package com.jasperreport.util;

public enum JasperReportTemplateType 
{
	DUMMY("DummyReport.jasper"), MAIN("MainReport.jasper"), SUBREPORT1("Subreport1.jasper"), SUBREPORT2("Subreport2.jasper");
	
	private String path;
	
	private JasperReportTemplateType(String path)
	{
		this.path=path;
	}

	public String getPath()
	{
		return path;
	}
}
