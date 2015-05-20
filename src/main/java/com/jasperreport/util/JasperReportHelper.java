package com.jasperreport.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.util.JRSaver;


public class JasperReportHelper
{
	final static Logger log = Logger.getLogger(JasperReportHelper.class);

	/**
	 * Compile JRXML to JasperReport
	 * 
	 * @param is JRXML string
	 * @return JasperReport
	 * @throws JasperReportHelperException
	 */
	public JasperReport compileJRXMLFile(final InputStream is) throws JasperReportHelperException
	{
		try
		{
			final JasperReport jr = JasperCompileManager.compileReport(is);
			if (jr == null)
			{
				log.error("Your report couldn't be compiled");
				throw new JasperReportHelperException("Your report couldn't be compiled.");
			}
			return jr;
		}
		catch (final JRException e)
		{
			log.error("JRXML compile has failed. " + e, e);
			throw new JasperReportHelperException("JRXML compile has failed. " + e, e);
		}
	}

	public JasperReport compileJRXMLFile(final JasperReportTemplateType type)
			throws JasperReportHelperException
	{
		try
		{
			final InputStream resourceAsStream = this.getClass().getResourceAsStream(
				type.getXmlPath());
			final JasperReport jr = JasperCompileManager.compileReport(resourceAsStream);
			if (jr == null)
			{
				log.error("Your report couldn't be compiled");
				throw new JasperReportHelperException("Your report couldn't be compiled.");
			}
			return jr;
		}
		catch (final JRException e)
		{
			log.error("JRXML compile has failed. " + e, e);
			throw new JasperReportHelperException("JRXML compile has failed. " + e, e);
		}
	}

	/**
	 * Create report by JasperReport object and input parameters
	 * 
	 * @param jasperReport
	 *            object compiled from JRXML
	 * @param Map
	 *            contains single input data
	 * @return object that could be used for export report to pdf,hml or xml
	 * @throws JasperReportHelperException
	 */
	public JasperPrint fillReport(final JasperReport jasperReport, final Map<String, Object> params)
			throws JasperReportHelperException

	{
		try
		{
			final Collection<Map<String, ?>> c = new ArrayList<Map<String, ?>>();
			c.add(params);

			final JRMapCollectionDataSource ds = new JRMapCollectionDataSource(c);

			final JasperPrint jp = JasperFillManager.fillReport(jasperReport, params, ds);
			if (jp == null)
			{
				log.error("Your report couldn't be filled from JRObject.");
				throw new JasperReportHelperException(
						"Your report couldn't be filled from JRObject.");
			}
			return jp;
		}
		catch (final JRException e)
		{
			log.error("Fill report has failed. " + e, e);
			throw new JasperReportHelperException("Fill report has failed. " + e, e);
		}
	}

	/**
	 * Create report by JasperReport object and input parameters
	 * 
	 * @param jasperReport
	 *            object compiled from JRXML
	 * @param Map
	 *            contains single input data
	 * @param Map
	 *            contains data retrieved from any QuerProcessor
	 * @return object that could be used for export report to pdf,hml or xml
	 * @throws JasperReportHelperException
	 */
	public JasperPrint fillReport(final JasperReport jasperReport,
			final Map<String, Object> params, final List<Object> paramList)
			throws JasperReportHelperException

	{
		try
		{
			final JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(paramList);

			final JasperPrint jp = JasperFillManager.fillReport(jasperReport, params, ds);
			if (jp == null)
			{
				log.error("Your report couldn't be filled from JRObject.");
				throw new JasperReportHelperException(
						"Your report couldn't be filled from JRObject.");
			}
			return jp;
		}
		catch (final JRException e)
		{
			log.error("Fill report has failed. " + e, e);
			throw new JasperReportHelperException("Fill report has failed. " + e, e);
		}
	}

	/**
	 * Create report by InputStream to JasperReport object and list of retrieved by QP objects
	 * 
	 * @param jasperReport
	 *            object compiled from JRXML
	 * @param Map
	 *            contains single input data
	 * @return object that could be used for export report to pdf,hml or xml
	 * @throws JasperReportHelperException
	 */
	public JasperPrint fillReport(final InputStream is, final Map<String, Object> params)
			throws JasperReportHelperException

	{
		try
		{
			final Collection<Map<String, ?>> c = new ArrayList<Map<String, ?>>();
			c.add(params);
			final JRMapCollectionDataSource ds = new JRMapCollectionDataSource(c);

			final JasperPrint jp = JasperFillManager.fillReport(is, params, ds);
			if (jp == null)
			{
				log.error("Your report couldn't be filled from InputStream.");
				throw new JasperReportHelperException(
						"Your report couldn't be filled from InputStream.");
			}
			return jp;
		}
		catch (final JRException e)
		{
			log.error("Fill report has failed. " + e, e);
			throw new JasperReportHelperException("Fill report has failed. " + e, e);
		}
	}

	/**
	 * Create report by InputStream to JasperReport object and list of retrieved by QP objects
	 * 
	 * @param jasperReport
	 *            object compiled from JRXML
	 * @param Map
	 *            contains input single data
	 * @param List
	 *            contains data retrieved from any QuerProcessor
	 * @return object that could be used for export report to pdf,hml or xml
	 * @throws JasperReportHelperException
	 */
	public JasperPrint fillReport(final InputStream is, final Map<String, Object> params,
			final List<Object> paramList) throws JasperReportHelperException

	{
		try
		{
			final JRBeanCollectionDataSource ds = new JRBeanCollectionDataSource(paramList);

			final JasperPrint jp = JasperFillManager.fillReport(is, params, ds);
			if (jp == null)
			{
				log.error("Your report couldn't be filled from InputStream.");
				throw new JasperReportHelperException(
						"Your report couldn't be filled from InputStream.");
			}
			return jp;
		}
		catch (final JRException e)
		{
			log.error("Fill report has failed. " + e, e);
			throw new JasperReportHelperException("Fill report has failed. " + e, e);
		}
	}

	public byte[] getBytes(final JasperPrint jasperPrint) throws JasperReportHelperException

	{
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		try
		{
			JRSaver.saveObject(jasperPrint, os);
		}
		catch (final JRException e)
		{
			log.error("error while serializing jasper print " + e);
			throw new JasperReportHelperException("Fill report has failed. " + e, e);
		}
		return os.toByteArray();
	}

	public JasperPrint loadJasperPrint(final byte[] bytes) throws JasperReportHelperException

	{
		final JasperPrint jasperPrint;
		final InputStream byteInputStream = new ByteArrayInputStream(bytes);
		try
		{
			jasperPrint = JRLoader.loadJasperPrint(byteInputStream, null);
		}
		catch (final JRException e)
		{
			log.error("error while loading jasper print " + e);
			throw new JasperReportHelperException("Fill report has failed. " + e, e);
		}

		return jasperPrint;
	}

	/**
	 * Export report to pdf bytes array
	 * 
	 * @param jPrint
	 * @return pdf bytes array
	 * @throws JasperReportHelperException
	 */
	public byte[] exportReportToPdf(final JasperPrint jPrint) throws JasperReportHelperException
	{
		try
		{
			final byte[] res = JasperExportManager.exportReportToPdf(jPrint);
			return res;
		}
		catch (final JRException e)
		{
			log.error("Export report to pdf-byte[] has failed. " + e, e);
			throw new JasperReportHelperException(
					"Export jasper report to pdf-byte[] has failed. " + e, e);
		}
	}

	/**
	 * Export report to html file
	 * 
	 * @param jasperPrint
	 * @return target html string
	 * @throws JasperReportHelperException
	 */
	public String exportReportToHtml(final JasperPrint jasperPrint)
			throws JasperReportHelperException
	{
		try
		{
			final File tempHtmReportFile = File.createTempFile(jasperPrint.getName(), ".tmp");
			final String destFileName = tempHtmReportFile.getAbsolutePath();

			JasperExportManager.exportReportToHtmlFile(jasperPrint, destFileName);

			final String res = new String(Files.readAllBytes(Paths.get(destFileName)));

			return res;

		}
		catch (final IOException | JRException e)
		{
			log.error("Export reprt as a html has failed. " + e, e);
			throw new JasperReportHelperException("Export jasper report to html-file has failed. "
					+ e, e);
		}
	}

	/**
	 * Export report to xml string
	 * 
	 * @param jasperPrint
	 * @return xml string
	 * @throws JasperReportHelperException
	 */
	public String exportReportToXml(final JasperPrint jasperPrint)
			throws JasperReportHelperException
	{
		try
		{
			final String res = JasperExportManager.exportReportToXml(jasperPrint);
			return res;
		}
		catch (final JRException e)
		{
			log.error("Export reprt as a html has failed. " + e, e);
			throw new JasperReportHelperException(
					"Export jasper report to xml-string has failed. " + e, e);
		}
	}

	/**
	 * Retrieving JasperReport object from InputStream
	 * 
	 * @param is
	 * @return JasperReport object
	 * @throws JasperReportHelperException
	 */
	public JasperReport loadJasperObject(final InputStream is) throws JasperReportHelperException
	{
		try
		{
			final JasperReport jasperReport = (JasperReport) JRLoader.loadObject(is);
			return jasperReport;
		}
		catch (final JRException e)
		{
			log.error("Load JasperReport object has failed. " + e, e);
			throw new JasperReportHelperException("Load JasperReport object has failed. " + e, e);
		}
	}

	/**
	 * Retrieving JasperReport object from InputStream
	 * 
	 * @param is
	 * @return JasperReport object
	 * @throws JasperReportHelperException
	 */
	public JasperReport loadJasperObject(final JasperReportTemplateType type)
			throws JasperReportHelperException
	{
		try
		{
			final InputStream stream = this.getClass().getResourceAsStream(type.getPath());
			final JasperReport jasperReport = (JasperReport) JRLoader.loadObject(stream);
			return jasperReport;
		}
		catch (final JRException e)
		{
			log.error("Load JasperReport object has failed. " + e, e);
			throw new JasperReportHelperException("Load JasperReport object has failed. " + e, e);
		}
	}

	public byte[] exportReport(final JasperReportContentType contentType,
			final JasperReportTemplateType type, final Map<String, Object> params)
			throws JasperReportHelperException
	{
		final JasperReport report = loadJasperObject(type);
		final JasperPrint print = fillReport(report, params);

		return exportToBytes(contentType, print);
	}

	public byte[] exportReport(final JasperReportContentType contentType,
			final JasperReportTemplateType type, final Map<String, Object> params,
			final List<Object> paramList) throws JasperReportHelperException
	{
		final JasperReport report = loadJasperObject(type);
		final JasperPrint print = fillReport(report, params, paramList);

		return exportToBytes(contentType, print);
	}

	public byte[] exportToBytes(final JasperReportContentType contentType, final JasperPrint print)
			throws JasperReportHelperException
	{
		final byte[] bytes;

		switch (contentType)
		{
			case HTML:
				bytes = exportReportToHtml(print).getBytes();
				break;
			case XML:
				bytes = exportReportToXml(print).getBytes();
				break;
			case PDF:
				bytes = exportReportToPdf(print);
				break;
			default:
				throw new JasperReportHelperException(
						"exportReport() has failed, unknown JasperReportContentType.");
		}
		return bytes;
	}

	/**
	 * Get png image for se;lected report page
	 * 
	 * @param print
	 *            genereted JasperPrint object
	 * @param pageNumber
	 *            of jasper report
	 * @return bytes of png image
	 * @throws JasperReportHelperException
	 */
	public byte[] getReportPagePngImage(final JasperPrint print, final int pageNumber)
			throws JasperReportHelperException
	{
		final float zoom = 1.6f;

		try
		{
			BufferedImage rendered_image = null;
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			rendered_image = (BufferedImage) JasperPrintManager.printPageToImage(print, pageNumber,
				zoom);
			ImageIO.write(rendered_image, "png", baos);
			return baos.toByteArray();
		}
		catch (final JRException | IOException e)
		{
			log.error("Load JasperReport object has failed. " + e, e);
			throw new JasperReportHelperException("Getting png page[" + pageNumber
					+ "] for report[" + print.getName() + "] has failed." + e, e);
		}

	}

}
