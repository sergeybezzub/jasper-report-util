package com.jasperreport.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRSaver;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class JasperReportHelperTest
{
	private static final String DUMMY_PARAM1_VALUE = "JReport test string";
	private final static String JRXML_DUMMY_PATH = "/DummyReport.jrxml";
	private final static String JASPER_PATH = "/DummyReport.jasper";
	private final static String DUMMY_PARAM1 = "someString";
	private final static String DUMMY_PARAM2 = "someBool";
	private final static String JASPER_REP_PATH = "/MainReport.jasper";
	private final static String JRXML_REP_PATH = "/MainReport.jrxml";
	private final static String JASPER_PAYMENT_PATH = "/Subreport1.jasper";
	private final static String JRXML_PAYMENT_PATH = "/Subreport1.jrxml";
	private final static String JASPER_CONTRAS_PATH = "/Subreport2.jasper";
	private final static String JRXML_CONTRAS_PATH = "/Subreport2.jrxml";

	private final LocalDate paymentDate = new LocalDate("1999-01-01");
	private final DateTime enteredDate = new DateTime();
	
	final Map<String, Object> params = new HashMap<String, Object>();

	final private JasperReportHelper wrapper = new JasperReportHelper();
	JasperReport report = null;
	JasperReport reportFromList = null;
	JasperReport reportA = null;
	JasperReport reportP = null;
	JasperReport reportC = null;

	final List<Object> plist = new ArrayList<Object>();
	final List<Object> clist = new ArrayList<Object>();
	final List<Object> slist = new ArrayList<Object>();

	final boolean isSaveToFile = false;

	@After
	public void clear()
	{
		plist.clear();
		clist.clear();
		slist.clear();
	}

	@Before
	public void init() throws JasperReportHelperException, JRException
	{
		final InputStream stream = this.getClass().getResourceAsStream(JRXML_DUMMY_PATH);
		report = wrapper.compileJRXMLFile(stream);
		Assert.assertNotNull(report);
		Assert.assertNotNull(report.getName());


		final InputStream stream3 = this.getClass().getResourceAsStream(JRXML_REP_PATH);
		reportA = wrapper.compileJRXMLFile(stream3);
		Assert.assertNotNull(reportA);
		Assert.assertNotNull(reportA.getName());

		final InputStream stream4 = this.getClass().getResourceAsStream(JRXML_PAYMENT_PATH);
		reportP = wrapper.compileJRXMLFile(stream4);
		Assert.assertNotNull(reportP);
		Assert.assertNotNull(reportP.getName());

		final InputStream stream5 = this.getClass().getResourceAsStream(JRXML_CONTRAS_PATH);
		reportC = wrapper.compileJRXMLFile(stream5);
		Assert.assertNotNull(reportC);
		Assert.assertNotNull(reportC.getName());

		if (isSaveToFile)
		{
			JRSaver.saveObject(reportP, JASPER_PAYMENT_PATH);
			JRSaver.saveObject(reportA, JASPER_REP_PATH);
			JRSaver.saveObject(reportC, JASPER_CONTRAS_PATH);
		}

		params.put(DUMMY_PARAM1, DUMMY_PARAM1_VALUE);
		params.put(DUMMY_PARAM2, Boolean.TRUE);
		params.put("id", new Long(1));
		params.put("applicationName", new Long(1));
		params.put("status", new Long(1));
		params.put("sun", new Long(1));

		/**
		 */
		final ReportObject1 payment1 = new ReportObject1();
		final ReportObject1 payment2 = new ReportObject1();
		final ReportObject1 payment3 = new ReportObject1();
		final ReportObject1 contras = new ReportObject1();

		final String sortCode = "123";
		final String accountNumber = "001";
		final String reference = "&LTPAY000";
		final String name = "John Doe";
		final String text = "SALARY JAN1999";
		final BigDecimal amount = new BigDecimal("0.23");

		payment1.setId(1l);
		payment1.setIndex(1);
		payment1.setSortCode(sortCode);
		payment1.setAccountNumber(accountNumber);
		payment1.setAmount(amount);
		payment1.setReference(reference);
		payment1.setName(name);
		payment1.setText(text);
		payment1.setPaymentDate(paymentDate);

		payment2.setId(2l);
		payment2.setIndex(2);
		payment2.setSortCode(sortCode);
		payment2.setAccountNumber(accountNumber);
		payment2.setAmount(amount);
		payment2.setReference(reference);
		payment2.setName(name + 2);
		payment2.setText(text);
		payment2.setPaymentDate(paymentDate);

		payment3.setId(3l);
		payment3.setIndex(3);
		payment3.setSortCode(sortCode);
		payment3.setAccountNumber(accountNumber);
		payment3.setAmount(amount);
		payment3.setReference(reference);
		payment3.setName(name + 3);
		payment3.setText(text);
		payment3.setPaymentDate(paymentDate);

		plist.add(payment1);
		plist.add(payment2);
		plist.add(payment3);

		contras.setId(4l);
		contras.setReference("Contra");
		contras.setPaymentDate(paymentDate);
		contras.setSortCode(sortCode);
		contras.setAccountNumber(accountNumber);
		contras.setAmount(amount);
		contras.setText(text);

		clist.add(contras);

		/**
		 */
		final ReportObject2 submission1 = new ReportObject2();

		submission1.setId(1l);
		submission1.setStatus("DR");
		submission1.setApplicationName("app1");
		submission1.setSun("146000");
		submission1.setPaymentType("Bacs");
		submission1.setPaymentDate(paymentDate);
		submission1.setDebitCount(10);
		submission1.setAuddisCount(1);
		submission1.setCreditTotal(BigDecimal.TEN);
		submission1.setDebitTotal(BigDecimal.ONE);
		submission1.setEnteredBy("ABC");
		submission1.setEnteredDate(enteredDate);

		slist.add(submission1);

	}

	@Test
	public void createPdfReportPositive() throws JasperReportHelperException
	{

		final JasperPrint print = wrapper.fillReport(report, params);
		Assert.assertNotNull(print);
		Assert.assertNotNull(print.getName());

		final byte[] bytes = wrapper.exportReportToPdf(print);
		Assert.assertNotNull(bytes);
		Assert.assertTrue("Bytes length should be > 0 but it length=" + bytes.length,
			bytes.length > 0);
	}

	@Test
	public void createXMLReportPositive() throws JasperReportHelperException
	{

		final JasperPrint print = wrapper.fillReport(report, params);
		Assert.assertNotNull(print);
		Assert.assertNotNull(print.getName());

		final String xml = wrapper.exportReportToXml(print);
		Assert.assertNotNull(xml);
		Assert.assertTrue("Xml size should be > 0 but it size=" + xml.length(), xml.length() > 0);
		Assert.assertTrue(xml.contains(DUMMY_PARAM1_VALUE));

	}

	@Test
	public void createHtmlReportPositive() throws JasperReportHelperException
	{

		final JasperPrint print = wrapper.fillReport(report, params);
		Assert.assertNotNull(print);
		Assert.assertNotNull(print.getName());

		final String html = wrapper.exportReportToHtml(print);
		Assert.assertNotNull(html);
		Assert
			.assertTrue("Html size should be > 0 but it size=" + html.length(), html.length() > 0);
		Assert.assertTrue(html.contains(DUMMY_PARAM1_VALUE));

	}

	@Test
	public void createHtmlReportFromJReportFilePositive() throws JasperReportHelperException,
			JRException
	{
		final InputStream stream = this.getClass().getResourceAsStream(JASPER_PATH);
		final JasperReport jasperReport = wrapper.loadJasperObject(stream);

		final JasperPrint print = wrapper.fillReport(jasperReport, params);
		Assert.assertNotNull(print);
		Assert.assertNotNull(print.getName());

		final String html = wrapper.exportReportToHtml(print);
		Assert.assertNotNull(html);
		Assert
			.assertTrue("Html size should be > 0 but it size=" + html.length(), html.length() > 0);
	}

	@Test
	public void createXmlReportFromJReportStreamPositive() throws JasperReportHelperException,
			JRException
	{
		final InputStream stream = this.getClass().getResourceAsStream(JASPER_PATH);

		final JasperPrint print = wrapper.fillReport(stream, params);
		Assert.assertNotNull(print);
		Assert.assertNotNull(print.getName());

		final String xml = wrapper.exportReportToXml(print);
		Assert.assertNotNull(xml);
		Assert.assertTrue("XML size should be > 0 but it size=" + xml.length(), xml.length() > 0);
		Assert.assertTrue(xml.contains(DUMMY_PARAM1_VALUE));
	}

	@Test
	public void fillReportAndGetBytes() throws JasperReportHelperException, JRException
	{
		final InputStream stream = this.getClass().getResourceAsStream(JASPER_PATH);

		final JasperPrint print = wrapper.fillReport(stream, params);

		final byte[] bytes = wrapper.getBytes(print);

		Assert.assertNotNull(bytes);

		final JasperPrint jasperPrint = wrapper.loadJasperPrint(bytes);

		Assert.assertNotNull(jasperPrint);

		final String xml = wrapper.exportReportToXml(jasperPrint);
		Assert.assertNotNull(xml);
		Assert.assertTrue("XML size should be > 0 but it size=" + xml.length(), xml.length() > 0);
		Assert.assertTrue(xml.contains(DUMMY_PARAM1_VALUE));

		final String xml1 = wrapper.exportReportToXml(print);

		Assert.assertEquals(xml, xml1);

	}

	@Test
	public void createReportComplexPositive() throws JasperReportHelperException, JRException
	{
		final byte[] bytes = wrapper.exportReport(JasperReportContentType.HTML,
			JasperReportTemplateType.DUMMY, params);
		Assert.assertNotNull(bytes);
		Assert.assertTrue(bytes.length > 0);

		final String myReport = new String(bytes);
		Assert.assertTrue(myReport.contains(DUMMY_PARAM1_VALUE));
	}

	@Test
	public void fillMainReportFromXMLPositive() throws JasperReportHelperException,
			URISyntaxException, IOException, JRException
	{
		final URL fileUrl = this.getClass().getResource("/");
		final File path = new File(fileUrl.toURI());

		final Map<String, Object> par = new HashMap<String, Object>();
		par.put("SUBREPORT_DIR", path.getPath() + File.separator);
		par.put("SUBREPORT_LIST_1", plist);
		par.put("SUBREPORT_LIST_2", clist);

		final JasperPrint print = wrapper.fillReport(reportA, par, slist);
		Assert.assertNotNull(print);
		final String html = wrapper.exportReportToHtml(print);
		Assert.assertNotNull(html);

		final byte[] pdfbuf = wrapper.exportReportToPdf(print);
		Assert.assertNotNull(pdfbuf);
		Assert.assertTrue(pdfbuf.length > 100);
	}

	@Test
	public void fillMainReportPositive() throws JasperReportHelperException,
			URISyntaxException, IOException, JRException
	{

		final InputStream stream = this.getClass().getResourceAsStream(JASPER_REP_PATH);

		final URL fileUrl = this.getClass().getResource("/");
		final File path = new File(fileUrl.toURI());

		final Map<String, Object> par = new HashMap<String, Object>();
		par.put("SUBREPORT_DIR", path.getPath() + File.separator);
		par.put("SUBREPORT_LIST_1", plist);
		par.put("SUBREPORT_LIST_2", clist);

		final JasperPrint print = wrapper.fillReport(stream, par, slist);
		Assert.assertNotNull(print);
		final String html = wrapper.exportReportToHtml(print);
		Assert.assertNotNull(html);

		final byte[] pdfbuf = wrapper.exportReportToPdf(print);

		if (isSaveToFile)
		{
			final FileOutputStream out = new FileOutputStream("/AuthorisationReport-new.pdf");
			out.write(pdfbuf);
			out.close();
		}

	}

	@Test
	public void fillSub1ReportPositive() throws JasperReportHelperException,
			URISyntaxException, IOException, JRException
	{
		final Map<String, Object> par = new HashMap<String, Object>();

		final JasperPrint print = wrapper.fillReport(reportP, par, plist);
		Assert.assertNotNull(print);
		final String html = wrapper.exportReportToHtml(print);
		Assert.assertNotNull(html);

	}

}
