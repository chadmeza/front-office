package com.riteboiler.frontoffice.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.TabStop.Alignment;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.riteboiler.frontoffice.MainApp;
import com.riteboiler.frontoffice.model.BillOfLading;

/**
 * This class is a utility class which formats the data from an bill of lading
 * into a print-ready PDF.
 * 
 * @author Chad Meza
 */
public class BillOfLadingPDF {
	private BillOfLading billOfLading;
	private MainApp main;

	private Font font_12_bold = new Font(Font.FontFamily.HELVETICA, 12,
			Font.BOLD);
	private Font font_12_normal = new Font(Font.FontFamily.HELVETICA, 12,
			Font.NORMAL);
	private Font font_8_bold = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
	private Font font_8_normal = new Font(Font.FontFamily.HELVETICA, 8,
			Font.NORMAL);
	private Font font_6_bold = new Font(Font.FontFamily.HELVETICA, 8, Font.BOLD);
	private Font font_6_normal = new Font(Font.FontFamily.HELVETICA, 8,
			Font.NORMAL);
	private Font font_6_size = new Font(Font.FontFamily.HELVETICA, 6,
			Font.BOLD);
	private Font font_4_normal = new Font(Font.FontFamily.HELVETICA, 4,
			Font.NORMAL);
	private Font font_2_normal = new Font(Font.FontFamily.HELVETICA, 2,
			Font.NORMAL);

	/**
	 * Constructor with the reference to the bill of lading to use for this PDF.
	 * 
	 * @param billOfLading
	 *            BillOfLading to save
	 */
	public BillOfLadingPDF(BillOfLading billOfLading) {
		this.billOfLading = billOfLading;
	}

	/**
	 * Setter for reference to the main application.
	 * 
	 * @param main
	 *            Current MainApp instance
	 */
	public void setMain(MainApp main) {
		this.main = main;
	}

	/**
	 * Saves the bill of lading into a print-ready PDF.
	 */
	public void savePDF() throws FileNotFoundException {
		Document document = new Document(PageSize.LETTER, 20, 15, 15, 15);

		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(main.getPreferenceList().get(0)
							.getBillLocation()
							+ "\\"
							+ billOfLading.getAcknowledgmentId()
							+ " Bill of Lading.pdf"));

			document.open();

			document.add(new Paragraph(
					"UNIFORM STRAIGHT BILL OF LADING - Original - Not Negotiable - Domestic",
					font_12_bold));

			/**
			 * LINE 1 - HEADER
			 */

			PdfPTable headerTable = createHeader(font_8_normal, font_8_bold,
					font_6_bold, font_4_normal, font_6_size);

			document.add(headerTable);

			document.add(new Phrase("FROM  ", font_8_normal));
			document.add(new Phrase("RITE ENGINEERING & MANUFACTURING CORP.     ",
					font_8_bold));
			document.add(new Phrase("AT  ", font_8_normal));
			document.add(new Phrase("COMMERCE, CALIFORNIA     ", font_8_bold));
			document.add(new Phrase("DATE  ", font_8_normal));
			document.add(new Phrase(billOfLading.getDate(), font_8_bold));

			/**
			 * LINE 2 - SOLD TO
			 */

			PdfPTable soldToTable = createSoldTo(font_6_normal, font_4_normal);
			document.add(soldToTable);

			/**
			 * LINE 3 - DETAILS
			 */

			PdfPTable detailsTable = createDetails(font_8_normal, font_8_bold,
					font_6_normal, font_6_bold, font_4_normal);
			document.add(detailsTable);

			/**
			 * LINE 4 - FOOTER 1
			 */

			PdfPTable footer1Table = createFooter1(font_8_normal, font_8_bold,
					font_6_normal, font_6_bold, font_4_normal, font_6_size);
			document.add(footer1Table);

			/**
			 * LINE 5 - FOOTER 2
			 */

			PdfPTable footer2Table = createFooter2(font_6_normal);
			document.add(footer2Table);
			
			/**
			 * LINE 6 - FOOTER 3
			 */

			if (billOfLading.getIsAtmospheric()) {
				PdfPTable footer3Table = createFooter3(font_6_normal);
				document.add(footer3Table);
			}

			document.close();
		} catch (DocumentException | IOException e) {
			main.getDbHelper().insertError(e,
					main.getPreferenceList().get(0).getUsername());
		}
	}

	/**
	 * Sets up the header table.
	 * 
	 * @param font_8_normal
	 *            , font_8_bold, font_6_bold, font_4_normal Fonts to use
	 */
	private PdfPTable createHeader(Font font_8_normal, Font font_8_bold,
			Font font_6_bold, Font font_4_normal, Font font_6_size) throws DocumentException {
		PdfPTable headerTable = new PdfPTable(3);
		headerTable.setWidthPercentage(100);
		float[] columnWidths = { 3f, 1f, 1f };
		headerTable.setWidths(columnWidths);
		headerTable.setSpacingBefore(10f);

		PdfPCell headerCell_1 = new PdfPCell(new Phrase("", font_8_bold));
		headerCell_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell_1.setBorder(Rectangle.NO_BORDER);
		headerTable.addCell(headerCell_1);

		PdfPCell headerCell_2 = new PdfPCell(new Phrase("SHIPPER'S NO.",
				font_8_normal));
		headerCell_2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		headerCell_2.setBorder(Rectangle.NO_BORDER);
		headerTable.addCell(headerCell_2);

		PdfPCell headerCell_3 = new PdfPCell(new Phrase(""
				+ billOfLading.getAcknowledgmentId(), font_8_normal));
		headerCell_3.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell_3.setBorder(Rectangle.NO_BORDER);
		headerTable.addCell(headerCell_3);

		if (billOfLading.getCarrier() != null) {
			PdfPCell headerCell_4 = new PdfPCell(new Phrase("CARRIER     "
					+ billOfLading.getCarrier().toUpperCase(), font_8_bold));
			headerCell_4.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerCell_4.setBorderWidthTop(0);
			headerCell_4.setBorderWidthRight(0);
			headerCell_4.setBorderWidthLeft(0);
			headerCell_4.setBorderWidthBottom(1f);
			headerTable.addCell(headerCell_4);
		} else {
			PdfPCell headerCell_4 = new PdfPCell(new Phrase("CARRIER     ", font_8_bold));
			headerCell_4.setHorizontalAlignment(Element.ALIGN_CENTER);
			headerCell_4.setBorderWidthTop(0);
			headerCell_4.setBorderWidthRight(0);
			headerCell_4.setBorderWidthLeft(0);
			headerCell_4.setBorderWidthBottom(1f);
			headerTable.addCell(headerCell_4);
		}

		PdfPCell headerCell_5 = new PdfPCell(new Phrase("AGENT'S NO.",
				font_8_normal));
		headerCell_5.setHorizontalAlignment(Element.ALIGN_RIGHT);
		headerCell_5.setBorderWidthTop(0);
		headerCell_5.setBorderWidthRight(0);
		headerCell_5.setBorderWidthLeft(0);
		headerCell_5.setBorderWidthBottom(1f);
		headerTable.addCell(headerCell_5);

		if (billOfLading.getAgentNumber() != null) {
			PdfPCell headerCell_6 = new PdfPCell(new Phrase(
					billOfLading.getAgentNumber().toUpperCase(), font_8_normal));
			headerCell_6.setHorizontalAlignment(Element.ALIGN_LEFT);
			headerCell_6.setBorderWidthTop(0);
			headerCell_6.setBorderWidthRight(0);
			headerCell_6.setBorderWidthLeft(0);
			headerCell_6.setBorderWidthBottom(1f);
			headerTable.addCell(headerCell_6);
		} else {
			PdfPCell headerCell_6 = new PdfPCell(new Phrase(" ", font_8_normal));
			headerCell_6.setHorizontalAlignment(Element.ALIGN_LEFT);
			headerCell_6.setBorderWidthTop(0);
			headerCell_6.setBorderWidthRight(0);
			headerCell_6.setBorderWidthLeft(0);
			headerCell_6.setBorderWidthBottom(1f);
			headerTable.addCell(headerCell_6);
		}

		PdfPCell headerCell_7 = new PdfPCell();
		headerCell_7
				.addElement(new Chunk(
						"RECEIVED, subject to the classifications and tariffs in effect on the date of the receipt by the carrier of the property described in the Original Bill of Lading.",
						font_6_size));
		headerCell_7.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell_7.setColspan(3);
		headerCell_7.setBorder(Rectangle.NO_BORDER);
		headerTable.addCell(headerCell_7);

		return headerTable;
	}

	/**
	 * Sets up the sold to table.
	 * 
	 * @param font_6_normal
	 *            , font_4_normal Fonts to use
	 */
	private PdfPTable createSoldTo(Font font_6_normal, Font font_4_normal)
			throws DocumentException {
		PdfPTable soldToTable = new PdfPTable(8);
		soldToTable.setWidthPercentage(100);
		float[] columnWidths2 = { 1f, 2f, 1f, 2f, 1f, 2f, 1f, 1f };
		soldToTable.setWidths(columnWidths2);

		PdfPCell soldToCell_1 = new PdfPCell(
				new Phrase(
						"The property described below, in apparent good order, except as "
						+ "noted (contents and condition of contents of packages unknown) "
						+ "marked, consigned and destined as shown below, which said company "
						+ "(the word company being understood throughout this contract as meaning "
						+ "any person or corporation in possession of the property under the contract) "
						+ "agrees to carry to its usual place of delivery at said destination, if on "
						+ "its own railroad, water line, highway route or routes, or within the "
						+ "territory of its highway operations, otherwise to deliver to another "
						+ "carrier on the route to said destination. It is mutually agreed, as to "
						+ "each carrier of all or any of said property over all or any portion of "
						+ "said route to destination, and as to each party at any time interested in "
						+ "all or any of said property, that every service to be performed hereunder "
						+ "shall be subject to all the conditions not prohibited by law, whether "
						+ "printed or written, herein contained, including the conditions on back hereof, "
						+ "which are hereby agreed to by the shipper and accepted for himself and his assigns.",
						font_4_normal));
		soldToCell_1.setPaddingTop(5f);
		soldToCell_1.setPaddingBottom(5f);
		soldToCell_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		soldToCell_1.setColspan(8);
		soldToTable.addCell(soldToCell_1);

		PdfPCell soldToCell_2 = new PdfPCell(new Phrase("CONSIGNED TO:  " + billOfLading.getShipToAddress().getName().toUpperCase(),
				font_6_normal));
		soldToCell_2.setPaddingTop(5f);
		soldToCell_2.setPaddingBottom(5f);
		soldToCell_2.setHorizontalAlignment(Element.ALIGN_LEFT);
		soldToCell_2.setColspan(8);
		soldToTable.addCell(soldToCell_2);

		PdfPCell soldToCell_4 = new PdfPCell(new Phrase("DESTINATION:  " + billOfLading.getShipToAddress().getStreet1().toUpperCase() + 
				", " + billOfLading.getShipToAddress().getCity().toUpperCase() + ", " + billOfLading.getShipToAddress().getState().toUpperCase() + " " +
				billOfLading.getShipToAddress().getZip().toUpperCase(), 
				font_6_normal));
		soldToCell_4.setPaddingTop(5f);
		soldToCell_4.setPaddingBottom(5f);
		soldToCell_4.setHorizontalAlignment(Element.ALIGN_LEFT);
		soldToCell_4.setColspan(8);
		soldToTable.addCell(soldToCell_4);

		if (billOfLading.getContact() != null) {
			PdfPCell soldToCell_13 = new PdfPCell(new Phrase("CONTACT:  " + billOfLading.getContact().toUpperCase(), font_6_normal));
			soldToCell_13.setPaddingTop(5f);
			soldToCell_13.setPaddingBottom(5f);
			soldToCell_13.setHorizontalAlignment(Element.ALIGN_LEFT);
			soldToCell_13.setColspan(8);
			soldToTable.addCell(soldToCell_13);
		} else {
			PdfPCell soldToCell_13 = new PdfPCell(new Phrase("CONTACT:  " + " ", font_6_normal));
			soldToCell_13.setPaddingTop(5f);
			soldToCell_13.setPaddingBottom(5f);
			soldToCell_13.setHorizontalAlignment(Element.ALIGN_LEFT);
			soldToCell_13.setColspan(8);
			soldToTable.addCell(soldToCell_13);
		}

		PdfPCell soldToCell_14 = new PdfPCell(new Phrase(" ", font_6_normal));
		soldToCell_14.setPaddingTop(5f);
		soldToCell_14.setPaddingBottom(5f);
		soldToCell_14.setColspan(8);
		soldToTable.addCell(soldToCell_14);
		return soldToTable;
	}

	/**
	 * Sets up the details table.
	 * 
	 * @param font_8_normal
	 *            , font_8_bold, font_6_normal, font_6_bold, font_4_normal Fonts
	 *            to use
	 */
	private PdfPTable createDetails(Font font_8_normal, Font font_8_bold,
			Font font_6_normal, Font font_6_bold, Font font_4_normal)
			throws DocumentException {
		PdfPTable detailsTable = new PdfPTable(2);
		detailsTable.setWidthPercentage(100);
		float[] columnWidths3 = { 4f, 1f };
		detailsTable.setWidths(columnWidths3);
		detailsTable.setSpacingAfter(1f);

		PdfPCell detailsCell_1 = new PdfPCell();
		detailsCell_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		detailsCell_1.setPadding(0);

		PdfPTable detailsNestedTable_1 = createDetailsNested_1(font_6_bold);
		PdfPTable detailsNestedTable_2 = createDetailsNested_2(font_4_normal);
		PdfPTable detailsNestedTable_3 = createDetailsNested_3(font_6_bold);
		PdfPTable detailsNestedTable_4 = createDetailsNested_4(font_6_bold);
		PdfPTable detailsNestedTable_5 = createDetailsNested_5(font_8_normal);
		PdfPTable detailsNestedTable_6 = createDetailsNested_6(font_8_normal);
		PdfPTable detailsNestedTable_7 = createDetailsNested_7(font_4_normal);

		detailsCell_1.addElement(detailsNestedTable_1);
		detailsCell_1.addElement(detailsNestedTable_2);
		detailsCell_1.addElement(detailsNestedTable_3);
		detailsCell_1.addElement(detailsNestedTable_4);
		
		if (billOfLading.getBoilerData().size() > 0) {
			detailsCell_1.addElement(createBoilerHeader(font_6_normal));
			List<PdfPTable> tables = createBoilerDetails(font_6_normal);
			
			for (int i = 0; i < tables.size(); i++) {
				detailsCell_1.addElement(tables.get(i));
			}
		}
		
		if (billOfLading.getPalletData().size() > 0) {
			detailsCell_1.addElement(createPalletHeader(font_6_bold));
			List<PdfPTable> tables = createPalletDetails(font_6_normal);
			
			for (int i = 0; i < tables.size(); i++) {
				detailsCell_1.addElement(tables.get(i));
			}
		}
		
		if (billOfLading.getBoilerData().size() > 0 || billOfLading.getPalletData().size() > 0) {
			detailsCell_1.addElement(createTotalTable(font_6_bold));
			detailsCell_1.addElement(createDetailsNested_3(font_6_bold));
		}
		
		detailsCell_1.addElement(createNotesTable(font_6_normal));
		
		detailsCell_1.addElement(detailsNestedTable_5);
		detailsCell_1.addElement(detailsNestedTable_6);
		detailsCell_1.addElement(detailsNestedTable_7);
		detailsTable.addCell(detailsCell_1);

		PdfPCell detailsCell_2 = new PdfPCell();
		detailsCell_2.setHorizontalAlignment(Element.ALIGN_LEFT);
		detailsCell_2.setPadding(0);

		PdfPTable detailsNestedTable_8 = createDetailsNested_8(font_8_normal,
				font_6_bold);
		PdfPTable detailsNestedTable_9 = createDetailsNested_9(font_8_normal,
				font_6_bold);
		PdfPTable detailsNestedTable_10 = createDetailsNested_10(font_8_normal,
				font_6_bold);
		PdfPTable detailsNestedTable_11 = createDetailsNested_11(font_8_normal,
				font_6_bold);
		PdfPTable detailsNestedTable_12 = createDetailsNested_12(font_8_normal,
				font_6_bold);
		PdfPTable detailsNestedTable_13 = createDetailsNested_13(font_8_normal,
				font_6_bold);
		PdfPTable detailsNestedTable_14 = createDetailsNested_14(font_8_normal,
				font_6_bold);

		detailsCell_2.addElement(detailsNestedTable_8);
		detailsCell_2.addElement(detailsNestedTable_9);
		detailsCell_2.addElement(detailsNestedTable_10);
		detailsCell_2.addElement(detailsNestedTable_11);
		detailsCell_2.addElement(detailsNestedTable_12);
		detailsCell_2.addElement(detailsNestedTable_13);
		detailsCell_2.addElement(detailsNestedTable_14);
		detailsTable.addCell(detailsCell_2);
		return detailsTable;
	}

	/**
	 * Sets up the footer 1 table.
	 * 
	 * @param font_8_normal
	 *            , font_8_bold, font_6_normal, font_6_bold, font_4_normal Fonts
	 *            to use
	 */
	private PdfPTable createFooter1(Font font_8_normal, Font font_8_bold,
			Font font_6_normal, Font font_6_bold, Font font_4_normal, Font font_6_size)
			throws DocumentException {
		PdfPTable footer1Table = new PdfPTable(7);
		footer1Table.setWidthPercentage(100);
		float[] columnWidths = { 4f, 2f, 2f, 1F, 2f, 2f, 2f };
		footer1Table.setWidths(columnWidths);
		footer1Table.setSpacingBefore(10f);

		PdfPCell footer1Cell_1 = new PdfPCell(new Phrase(
				"RITE ENGINEERING & MANUFACTURING CORP    SHIPPER, PER", font_6_size));
		footer1Cell_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		footer1Cell_1.setBorderWidthTop(0);
		footer1Cell_1.setBorderWidthRight(0);
		footer1Cell_1.setBorderWidthLeft(0);
		footer1Cell_1.setColspan(3);
		footer1Table.addCell(footer1Cell_1);

		PdfPCell footer1Cell_4 = new PdfPCell(new Phrase(" ", font_6_normal));
		footer1Cell_4.setHorizontalAlignment(Element.ALIGN_LEFT);
		footer1Cell_4.setBorderWidthTop(0);
		footer1Cell_4.setBorderWidthRight(0);
		footer1Cell_4.setBorderWidthLeft(0);
		footer1Cell_4.setBorderWidthBottom(0);
		footer1Table.addCell(footer1Cell_4);

		PdfPCell footer1Cell_5 = new PdfPCell(new Phrase("AGENT, PER", font_6_size));
		footer1Cell_5.setHorizontalAlignment(Element.ALIGN_LEFT);
		footer1Cell_5.setBorderWidthTop(0);
		footer1Cell_5.setBorderWidthRight(0);
		footer1Cell_5.setBorderWidthLeft(0);
		footer1Cell_5.setColspan(2);
		footer1Table.addCell(footer1Cell_5);

		PdfPCell footer1Cell_7 = new PdfPCell(new Phrase(" ", font_6_normal));
		footer1Cell_7.setHorizontalAlignment(Element.ALIGN_LEFT);
		footer1Cell_7.setBorderWidthTop(0);
		footer1Cell_7.setBorderWidthRight(0);
		footer1Cell_7.setBorderWidthLeft(0);
		footer1Table.addCell(footer1Cell_7);

		return footer1Table;
	}

	/**
	 * Sets up the footer 2 table.
	 * 
	 * @param font_6_normal
	 *            Fonts to use
	 */
	private PdfPTable createFooter2(Font font_6_normal)
			throws DocumentException {
		PdfPTable footer2Table = new PdfPTable(7);
		footer2Table.setWidthPercentage(100);
		float[] columnWidths = { 4f, 2f, 2f, 1F, 2f, 2f, 2f };
		footer2Table.setWidths(columnWidths);

		PdfPCell footer2Cell_1 = new PdfPCell(
				new Phrase(
						"Permanent address of shipper, 5832 GARFIELD AVENUE, COMMERCE, CA 90040",
						font_6_normal));
		footer2Cell_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		footer2Cell_1.setBorderWidthTop(0);
		footer2Cell_1.setBorderWidthRight(0);
		footer2Cell_1.setBorderWidthLeft(0);
		footer2Cell_1.setColspan(7);
		footer2Table.addCell(footer2Cell_1);

		return footer2Table;
	}
	
	/**
	 * Sets up the footer 3 table.
	 * 
	 * @param font_6_normal
	 *            Fonts to use
	 * @throws IOException 
	 * @throws MalformedURLException 
	 */
	private PdfPTable createFooter3(Font font_6_normal)
			throws DocumentException, MalformedURLException, IOException {
		PdfPTable footer3Table = new PdfPTable(7);
		footer3Table.setWidthPercentage(100);
		float[] columnWidths = { 4f, 2f, 2f, 1F, 2f, 2f, 2f };
		footer3Table.setWidths(columnWidths);
		
		Image tag = Image.getInstance("resources/images/tag.jpg");
		tag.scalePercent(25);

		PdfPCell footer3Cell_1 = new PdfPCell(tag);
		footer3Cell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		footer3Cell_1.setPaddingTop(10f);
		footer3Cell_1.setBorderWidthTop(0);
		footer3Cell_1.setBorderWidthRight(0);
		footer3Cell_1.setBorderWidthLeft(0);
		footer3Cell_1.setBorderWidthBottom(0);
		footer3Cell_1.setColspan(7);
		footer3Table.addCell(footer3Cell_1);

		return footer3Table;
	}

	/**
	 * Sets up nested table containing collect on delivery.
	 * 
	 * @param font_6_normal
	 *            Fonts to use
	 */
	private PdfPTable createDetailsNested_1(Font font_6_bold)
			throws DocumentException {
		PdfPTable detailsNestedTable = new PdfPTable(5);
		detailsNestedTable.setWidthPercentage(100);
		float[] nestedColumnWidths = { 1f, 3f, 1f, 1f, 2f };
		detailsNestedTable.setWidths(nestedColumnWidths);

		PdfPCell detailsNestedCell_1 = new PdfPCell(new Phrase(
				"COLLECT ON DELIVERY", font_6_bold));
		detailsNestedCell_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		detailsNestedCell_1.setBorder(0);
		detailsNestedCell_1.setColspan(5);
		detailsNestedTable.addCell(detailsNestedCell_1);

		Phrase ph = new Phrase();
		ph.add(new Phrase("$                             ", font_6_bold));
		ph.add(new Phrase("and remit to: ", font_6_bold));
		ph.add(new Phrase("RITE ENG. & MFG. CORP.", font_6_bold));

		PdfPCell detailsNestedCell_2 = new PdfPCell();
		detailsNestedCell_2.addElement(ph);
		detailsNestedCell_2.setHorizontalAlignment(Element.ALIGN_LEFT);
		detailsNestedCell_2.setBorderWidthTop(0);
		detailsNestedCell_2.setBorderWidthRight(0);
		detailsNestedCell_2.setBorderWidthLeft(0);
		detailsNestedCell_2.setColspan(5);
		detailsNestedTable.addCell(detailsNestedCell_2);

		PdfPCell detailsNestedCell_3 = new PdfPCell(new Phrase("Street",
				font_6_bold));
		detailsNestedCell_3.setHorizontalAlignment(Element.ALIGN_RIGHT);
		detailsNestedCell_3.setBorder(0);
		detailsNestedCell_3.setColspan(2);
		detailsNestedTable.addCell(detailsNestedCell_3);

		PdfPCell detailsNestedCell_4 = new PdfPCell(new Phrase("City",
				font_6_bold));
		detailsNestedCell_4.setHorizontalAlignment(Element.ALIGN_RIGHT);
		detailsNestedCell_4.setBorder(0);
		detailsNestedCell_4.setColspan(2);
		detailsNestedTable.addCell(detailsNestedCell_4);

		PdfPCell detailsNestedCell_5 = new PdfPCell(new Phrase("State",
				font_6_bold));
		detailsNestedCell_5.setHorizontalAlignment(Element.ALIGN_RIGHT);
		detailsNestedCell_5.setBorder(0);
		detailsNestedTable.addCell(detailsNestedCell_5);
		return detailsNestedTable;
	}

	/**
	 * Sets up nested table containing package headers.
	 * 
	 * @param font_4_normal
	 *            Fonts to use
	 */
	private PdfPTable createDetailsNested_2(Font font_4_normal)
			throws DocumentException {
		PdfPTable detailsNestedTable = new PdfPTable(5);
		detailsNestedTable.setWidthPercentage(100);
		float[] nestedColumnWidths = { 1f, 3f, 1f, 1f, 2f };
		detailsNestedTable.setWidths(nestedColumnWidths);

		PdfPCell detailsNestedCell_1 = new PdfPCell(new Phrase("NO. PKGS.",
				font_4_normal));
		detailsNestedCell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_1);

		PdfPCell detailsNestedCell_2 = new PdfPCell(
				new Phrase(
						"KIND OF PACKAGE, DESCRIPTION OR ARTICLES, SPECIAL MARKS, AND EXCEPTIONS",
						font_4_normal));
		detailsNestedCell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_2);

		PdfPCell detailsNestedCell_3 = new PdfPCell(new Phrase(
				"* WEIGHT (SUB. TO COR.)", font_4_normal));
		detailsNestedCell_3.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_3);

		PdfPCell detailsNestedCell_4 = new PdfPCell(new Phrase("CLASS OR RATE",
				font_4_normal));
		detailsNestedCell_4.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_4);

		PdfPCell detailsNestedCell_5 = new PdfPCell(new Phrase("DIM. (LxWxH)",
				font_4_normal));
		detailsNestedCell_5.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_5);
		return detailsNestedTable;
	}

	/**
	 * Sets up nested table for package details.
	 * 
	 * @param font_6_bold
	 *            Fonts to use
	 */
	private PdfPTable createDetailsNested_3(Font font_6_bold)
			throws DocumentException {
		PdfPTable detailsNestedTable = new PdfPTable(5);
		detailsNestedTable.setWidthPercentage(100);
		float[] nestedColumnWidths = { 1f, 3f, 1f, 1f, 2f };
		detailsNestedTable.setWidths(nestedColumnWidths);

		PdfPCell detailsNestedCell_1 = new PdfPCell(
				new Phrase(" ", font_6_bold));
		detailsNestedCell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_1);

		PdfPCell detailsNestedCell_2 = new PdfPCell(
				new Phrase(" ", font_6_bold));
		detailsNestedCell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_2);

		PdfPCell detailsNestedCell_3 = new PdfPCell(
				new Phrase(" ", font_6_bold));
		detailsNestedCell_3.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_3);

		PdfPCell detailsNestedCell_4 = new PdfPCell(
				new Phrase(" ", font_6_bold));
		detailsNestedCell_4.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_4);

		PdfPCell detailsNestedCell_5 = new PdfPCell(
				new Phrase(" ", font_6_bold));
		detailsNestedCell_5.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_5);
		return detailsNestedTable;
	}

	/**
	 * Sets up nested table for package details.
	 * 
	 * @param font_6_bold
	 *            Fonts to use
	 */
	private PdfPTable createDetailsNested_4(Font font_6_bold)
			throws DocumentException {
		PdfPTable detailsNestedTable = new PdfPTable(5);
		detailsNestedTable.setWidthPercentage(100);
		float[] nestedColumnWidths = { 1f, 3f, 1f, 1f, 2f };
		detailsNestedTable.setWidths(nestedColumnWidths);
		
		int boilerQty = 0;
		
		if (billOfLading.getBoilerData().size() > 0) {
			for (int i = 0; i < billOfLading.getBoilerData().size(); i++) {
				boilerQty += billOfLading.getBoilerData().get(i).getQuantity();
			}
		}

		PdfPCell detailsNestedCell_1 = new PdfPCell(
				new Phrase(boilerQty > 0 ? "" + boilerQty : " ", font_6_bold));
		detailsNestedCell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_1);

		PdfPCell detailsNestedCell_2 = new PdfPCell(
				new Phrase(billOfLading.getIsBoilerNoWheels() ? "[ X ] BOILERS HEATING OR POWER IRON W / O WHEELS" : "[   ] BOILERS HEATING OR POWER IRON W / O WHEELS", font_6_bold));
		detailsNestedCell_2.setHorizontalAlignment(Element.ALIGN_LEFT);
		detailsNestedTable.addCell(detailsNestedCell_2);

		PdfPCell detailsNestedCell_3 = new PdfPCell(
				new Phrase(" ", font_6_bold));
		detailsNestedCell_3.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_3);

		PdfPCell detailsNestedCell_4 = new PdfPCell(
				new Phrase(" ", font_6_bold));
		detailsNestedCell_4.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_4);

		PdfPCell detailsNestedCell_5 = new PdfPCell(
				new Phrase(" ", font_6_bold));
		detailsNestedCell_5.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_5);
		return detailsNestedTable;
	}
	
	/**
	 * Sets up nested table for boiler header.
	 * 
	 * @param font_6_normal
	 *            Fonts to use
	 */
	private PdfPTable createBoilerHeader(Font font_6_normal)
			throws DocumentException {
		PdfPTable detailsNestedTable = new PdfPTable(5);
		detailsNestedTable.setWidthPercentage(100);
		float[] nestedColumnWidths = { 1f, 3f, 1f, 1f, 2f };
		detailsNestedTable.setWidths(nestedColumnWidths);

		PdfPCell detailsNestedCell_1 = new PdfPCell(
				new Phrase(" ", font_6_normal));
		detailsNestedCell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_1);

		PdfPCell detailsNestedCell_2 = new PdfPCell(
				new Phrase("          MODEL                    SERIAL", font_6_normal));
		detailsNestedCell_2.setHorizontalAlignment(Element.ALIGN_LEFT);
		detailsNestedTable.addCell(detailsNestedCell_2);

		PdfPCell detailsNestedCell_3 = new PdfPCell(
				new Phrase(" ", font_6_normal));
		detailsNestedCell_3.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_3);

		PdfPCell detailsNestedCell_4 = new PdfPCell(
				new Phrase(" ", font_6_normal));
		detailsNestedCell_4.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_4);

		PdfPCell detailsNestedCell_5 = new PdfPCell(
				new Phrase(" ", font_6_normal));
		detailsNestedCell_5.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_5);
		return detailsNestedTable;
	}
	
	/**
	 * Sets up nested table for boiler details.
	 * 
	 * @param font_6_normal
	 *            Fonts to use
	 */
	private List<PdfPTable> createBoilerDetails(Font font_6_normal)
			throws DocumentException {
		
		List<PdfPTable> tables = new ArrayList<PdfPTable>();
		
		for (int i = 0; i < billOfLading.getBoilerData().size(); i++) {
			PdfPTable detailsNestedTable = new PdfPTable(5);
			detailsNestedTable.setWidthPercentage(100);
			float[] nestedColumnWidths = { 1f, 3f, 1f, 1f, 2f };
			detailsNestedTable.setWidths(nestedColumnWidths);

			PdfPCell detailsNestedCell_1 = new PdfPCell(
					new Phrase(" ", font_6_normal));
			detailsNestedCell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
			detailsNestedTable.addCell(detailsNestedCell_1);

			PdfPCell detailsNestedCell_2 = new PdfPCell(
					new Phrase("(" + billOfLading.getBoilerData().get(i).getQuantity() + ")      " 
							+ billOfLading.getBoilerData().get(i).getModel().toUpperCase() + "                    " 
							+ billOfLading.getBoilerData().get(i).getSerial(), font_6_normal));
			detailsNestedCell_2.setHorizontalAlignment(Element.ALIGN_LEFT);
			detailsNestedTable.addCell(detailsNestedCell_2);

			PdfPCell detailsNestedCell_3 = new PdfPCell(
					new Phrase(billOfLading.getBoilerData().get(i).getWeight(), font_6_normal));
			detailsNestedCell_3.setHorizontalAlignment(Element.ALIGN_CENTER);
			detailsNestedTable.addCell(detailsNestedCell_3);

			PdfPCell detailsNestedCell_4 = new PdfPCell(
					new Phrase(" ", font_6_normal));
			detailsNestedCell_4.setHorizontalAlignment(Element.ALIGN_CENTER);
			detailsNestedTable.addCell(detailsNestedCell_4);

			PdfPCell detailsNestedCell_5 = new PdfPCell(
					new Phrase(billOfLading.getBoilerData().get(i).getDimensions(), font_6_normal));
			detailsNestedCell_5.setHorizontalAlignment(Element.ALIGN_CENTER);
			detailsNestedTable.addCell(detailsNestedCell_5);
			tables.add(detailsNestedTable);
		}
		
		tables.add(createDetailsNested_3(font_6_bold));
		return tables;
	}
	
	/**
	 * Sets up nested table for pallet header.
	 * 
	 * @param font_6_normal
	 *            Fonts to use
	 */
	private PdfPTable createPalletHeader(Font font_6_bold)
			throws DocumentException {
		PdfPTable detailsNestedTable = new PdfPTable(5);
		detailsNestedTable.setWidthPercentage(100);
		float[] nestedColumnWidths = { 1f, 3f, 1f, 1f, 2f };
		detailsNestedTable.setWidths(nestedColumnWidths);
		
		int palletQty = 0;
		
		if (billOfLading.getPalletData().size() > 0) {
			for (int i = 0; i < billOfLading.getPalletData().size(); i++) {
				palletQty += billOfLading.getPalletData().get(i).getQuantity();
			}
		}

		PdfPCell detailsNestedCell_1 = new PdfPCell(
				new Phrase(palletQty > 0 ? "" + palletQty : " ", font_6_bold));
		detailsNestedCell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_1);

		PdfPCell detailsNestedCell_2 = new PdfPCell(
				new Phrase(billOfLading.getIsBoilerPart() ? "CARTON / PALLET     [ X ] BOILER PART" : "CARTON / PALLET     [   ] BOILER PART", font_6_bold));
		detailsNestedCell_2.setHorizontalAlignment(Element.ALIGN_LEFT);
		detailsNestedTable.addCell(detailsNestedCell_2);

		PdfPCell detailsNestedCell_3 = new PdfPCell(
				new Phrase(" ", font_6_bold));
		detailsNestedCell_3.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_3);

		PdfPCell detailsNestedCell_4 = new PdfPCell(
				new Phrase(" ", font_6_bold));
		detailsNestedCell_4.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_4);

		PdfPCell detailsNestedCell_5 = new PdfPCell(
				new Phrase(" ", font_6_bold));
		detailsNestedCell_5.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_5);
		return detailsNestedTable;
	}
	
	/**
	 * Sets up nested table for pallet details.
	 * 
	 * @param font_6_normal
	 *            Fonts to use
	 */
	private List<PdfPTable> createPalletDetails(Font font_6_normal)
			throws DocumentException {
		
		List<PdfPTable> tables = new ArrayList<PdfPTable>();
		
		for (int i = 0; i < billOfLading.getPalletData().size(); i++) {
			PdfPTable detailsNestedTable = new PdfPTable(5);
			detailsNestedTable.setWidthPercentage(100);
			float[] nestedColumnWidths = { 1f, 3f, 1f, 1f, 2f };
			detailsNestedTable.setWidths(nestedColumnWidths);

			PdfPCell detailsNestedCell_1 = new PdfPCell(
					new Phrase(" ", font_6_normal));
			detailsNestedCell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
			detailsNestedTable.addCell(detailsNestedCell_1);

			PdfPCell detailsNestedCell_2 = new PdfPCell(
					new Phrase("(" + billOfLading.getPalletData().get(i).getQuantity() + ")      " 
							+ billOfLading.getPalletData().get(i).getDescription().toUpperCase(), font_6_normal));
			detailsNestedCell_2.setHorizontalAlignment(Element.ALIGN_LEFT);
			detailsNestedTable.addCell(detailsNestedCell_2);

			PdfPCell detailsNestedCell_3 = new PdfPCell(
					new Phrase(billOfLading.getPalletData().get(i).getWeight(), font_6_normal));
			detailsNestedCell_3.setHorizontalAlignment(Element.ALIGN_CENTER);
			detailsNestedTable.addCell(detailsNestedCell_3);

			PdfPCell detailsNestedCell_4 = new PdfPCell(
					new Phrase(" ", font_6_normal));
			detailsNestedCell_4.setHorizontalAlignment(Element.ALIGN_CENTER);
			detailsNestedTable.addCell(detailsNestedCell_4);

			PdfPCell detailsNestedCell_5 = new PdfPCell(
					new Phrase(billOfLading.getPalletData().get(i).getDimensions(), font_6_normal));
			detailsNestedCell_5.setHorizontalAlignment(Element.ALIGN_CENTER);
			detailsNestedTable.addCell(detailsNestedCell_5);
			tables.add(detailsNestedTable);
		}
		tables.add(createDetailsNested_3(font_6_bold));
		return tables;
	}
	
	/**
	 * Sets up nested table for totals table.
	 * 
	 * @param font_6_normal
	 *            Fonts to use
	 */
	private PdfPTable createTotalTable(Font font_6_bold)
			throws DocumentException {
		PdfPTable detailsNestedTable = new PdfPTable(5);
		detailsNestedTable.setWidthPercentage(100);
		float[] nestedColumnWidths = { 1f, 3f, 1f, 1f, 2f };
		detailsNestedTable.setWidths(nestedColumnWidths);

		int boilers = 0;
		int pallets = 0;
		
		if (billOfLading.getBoilerData().size() > 0) {
			for (int i = 0; i < billOfLading.getBoilerData().size(); i++) {
				boilers += billOfLading.getBoilerData().get(i).getQuantity();
			}
		}
		
		if (billOfLading.getPalletData().size() > 0) {
			for (int i = 0; i < billOfLading.getPalletData().size(); i++) {
				pallets += billOfLading.getPalletData().get(i).getQuantity();
			}
		}
		
		int weight = 0;
		
		for (int i = 0; i < billOfLading.getBoilerData().size(); i++) {
			if (billOfLading.getBoilerData().get(i).getWeight().equals("")) {
				weight += 0;
			} else {
				weight += Integer.parseInt(billOfLading.getBoilerData().get(i).getWeight());
			}
		}
		
		for (int i = 0; i < billOfLading.getPalletData().size(); i++) {
			if (billOfLading.getPalletData().get(i).getWeight().equals("")) {
				weight += 0;
			} else {
				weight += Integer.parseInt(billOfLading.getPalletData().get(i).getWeight());
			}
		}
		
		PdfPCell detailsNestedCell_1 = new PdfPCell(
				new Phrase(boilers + pallets > 0 ? "" + (boilers + pallets) : " ", font_6_bold));
		detailsNestedCell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_1);

		PdfPCell detailsNestedCell_2 = new PdfPCell(
				new Phrase("TOTAL", font_6_bold));
		detailsNestedCell_2.setHorizontalAlignment(Element.ALIGN_LEFT);
		detailsNestedTable.addCell(detailsNestedCell_2);

		PdfPCell detailsNestedCell_3 = new PdfPCell(
				new Phrase("" + weight, font_6_bold));
		detailsNestedCell_3.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_3);

		PdfPCell detailsNestedCell_4 = new PdfPCell(
				new Phrase(" ", font_6_bold));
		detailsNestedCell_4.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_4);

		PdfPCell detailsNestedCell_5 = new PdfPCell(
				new Phrase(" ", font_6_bold));
		detailsNestedCell_5.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_5);
		return detailsNestedTable;
	}
	
	/**
	 * Sets up nested table for notes table.
	 * 
	 * @param font_6_normal
	 *            Fonts to use
	 */
	private PdfPTable createNotesTable(Font font_6_normal)
			throws DocumentException {
		PdfPTable detailsNestedTable = new PdfPTable(5);
		detailsNestedTable.setWidthPercentage(100);
		float[] nestedColumnWidths = { 1f, 3f, 1f, 1f, 2f };
		detailsNestedTable.setWidths(nestedColumnWidths);
		
		PdfPCell detailsNestedCell_1 = new PdfPCell(
				new Phrase(" ", font_6_normal));
		detailsNestedCell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_1);

		PdfPCell detailsNestedCell_2 = new PdfPCell(
				new Phrase(billOfLading.getNotes(), font_6_normal));
		detailsNestedCell_2.setHorizontalAlignment(Element.ALIGN_LEFT);
		detailsNestedTable.addCell(detailsNestedCell_2);

		PdfPCell detailsNestedCell_3 = new PdfPCell(
				new Phrase(" ", font_6_normal));
		detailsNestedCell_3.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_3);

		PdfPCell detailsNestedCell_4 = new PdfPCell(
				new Phrase(" ", font_6_normal));
		detailsNestedCell_4.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_4);

		PdfPCell detailsNestedCell_5 = new PdfPCell(
				new Phrase(" ", font_6_normal));
		detailsNestedCell_5.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_5);
		return detailsNestedTable;
	}

	/**
	 * Sets up nested table containing air ride suspension checkbox.
	 * 
	 * @param font_8_normal
	 *            Fonts to use
	 */
	private PdfPTable createDetailsNested_5(Font font_8_normal)
			throws DocumentException {
		PdfPTable detailsNestedTable = new PdfPTable(6);
		detailsNestedTable.setWidthPercentage(100);
		float[] nestedColumnWidths = { 4f, 1f, 2f, 1f, 2f, 4f };
		detailsNestedTable.setWidths(nestedColumnWidths);

		PdfPCell detailsNestedCell_1 = new PdfPCell(new Phrase(
				billOfLading.getIsAirRideVerified() ? "[ X ] Air Ride Suspension Verified:" : "[   ] Air Ride Suspension Verified:", font_8_normal));
		detailsNestedCell_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		detailsNestedCell_1.setBorderWidthTop(0);
		detailsNestedCell_1.setBorderWidthRight(0);
		detailsNestedCell_1.setBorderWidthLeft(0);
		detailsNestedTable.addCell(detailsNestedCell_1);

		PdfPCell detailsNestedCell_2 = new PdfPCell(new Phrase("Yes",
				font_8_normal));
		detailsNestedCell_2.setHorizontalAlignment(Element.ALIGN_RIGHT);
		detailsNestedCell_2.setBorderWidthTop(0);
		detailsNestedCell_2.setBorderWidthRight(0);
		detailsNestedCell_2.setBorderWidthLeft(0);
		detailsNestedTable.addCell(detailsNestedCell_2);

		PdfPCell detailsNestedCell_3 = new PdfPCell(new Phrase("__________",
				font_8_normal));
		detailsNestedCell_3.setHorizontalAlignment(Element.ALIGN_LEFT);
		detailsNestedCell_3.setBorderWidthTop(0);
		detailsNestedCell_3.setBorderWidthRight(0);
		detailsNestedCell_3.setBorderWidthLeft(0);
		detailsNestedTable.addCell(detailsNestedCell_3);

		PdfPCell detailsNestedCell_4 = new PdfPCell(new Phrase("No",
				font_8_normal));
		detailsNestedCell_4.setHorizontalAlignment(Element.ALIGN_RIGHT);
		detailsNestedCell_4.setBorderWidthTop(0);
		detailsNestedCell_4.setBorderWidthRight(0);
		detailsNestedCell_4.setBorderWidthLeft(0);
		detailsNestedTable.addCell(detailsNestedCell_4);

		PdfPCell detailsNestedCell_5 = new PdfPCell(new Phrase("__________",
				font_8_normal));
		detailsNestedCell_5.setHorizontalAlignment(Element.ALIGN_LEFT);
		detailsNestedCell_5.setBorderWidthTop(0);
		detailsNestedCell_5.setBorderWidthRight(0);
		detailsNestedCell_5.setBorderWidthLeft(0);
		detailsNestedTable.addCell(detailsNestedCell_5);

		PdfPCell detailsNestedCell_6 = new PdfPCell(new Phrase(
				"(Driver's Initials)", font_8_normal));
		detailsNestedCell_6.setHorizontalAlignment(Element.ALIGN_LEFT);
		detailsNestedCell_6.setBorderWidthTop(0);
		detailsNestedCell_6.setBorderWidthRight(0);
		detailsNestedCell_6.setBorderWidthLeft(0);
		detailsNestedTable.addCell(detailsNestedCell_6);
		return detailsNestedTable;
	}

	/**
	 * Sets up nested table containing shipment tarped checkbox.
	 * 
	 * @param font_8_normal
	 *            Fonts to use
	 */
	private PdfPTable createDetailsNested_6(Font font_8_normal)
			throws DocumentException {
		PdfPTable detailsNestedTable = new PdfPTable(6);
		detailsNestedTable.setWidthPercentage(100);
		float[] nestedColumnWidths = { 4f, 1f, 2f, 1f, 2f, 4f };
		detailsNestedTable.setWidths(nestedColumnWidths);

		PdfPCell detailsNestedCell_1 = new PdfPCell(new Phrase(
				billOfLading.getIsTarped() ? "[ X ] Shipment must be tarped if inclement weather!!!" : "[   ] Shipment must be tarped if inclement weather!!!",
				font_8_normal));
		detailsNestedCell_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		detailsNestedCell_1.setBorderWidthTop(0);
		detailsNestedCell_1.setBorderWidthRight(0);
		detailsNestedCell_1.setBorderWidthLeft(0);
		detailsNestedCell_1.setColspan(6);
		detailsNestedTable.addCell(detailsNestedCell_1);
		return detailsNestedTable;
	}

	/**
	 * Sets up nested table containing small print.
	 * 
	 * @param font_4_normal
	 *            Fonts to use
	 */
	private PdfPTable createDetailsNested_7(Font font_4_normal)
			throws DocumentException {
		PdfPTable detailsNestedTable = new PdfPTable(2);
		detailsNestedTable.setWidthPercentage(100);
		float[] nestedColumnWidths = { 3f, 1f };
		detailsNestedTable.setWidths(nestedColumnWidths);

		PdfPCell detailsNestedCell_1 = new PdfPCell(
				new Phrase(
						"* If the shipment moves between two parts by a carrier by water, the law requires \nthat the bill of lading shall state whether it is \"carrier's or shipper's weight.\""
								+ "									\n\nNOTE - Where the rate is dependent on value, shippers are required to state specifically in writing the agreed or declared value of the property. \nThe agreed or declared value of the property is hereby specifically stated by the shipped to be not exceeding"
								+ "									\n\nper", font_4_normal));
		detailsNestedCell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_1);

		PdfPCell detailsNestedCell_2 = new PdfPCell(new Phrase(" ",
				font_4_normal));
		detailsNestedCell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedTable.addCell(detailsNestedCell_2);
		return detailsNestedTable;
	}

	/**
	 * Sets up nested table containing C.O.D.
	 * 
	 * @param font_8_normal
	 *            , font_6_bold Fonts to use
	 */
	private PdfPTable createDetailsNested_8(Font font_8_normal, Font font_6_bold)
			throws DocumentException {
		PdfPTable detailsNestedTable = new PdfPTable(4);
		detailsNestedTable.setWidthPercentage(100);
		float[] nestedColumnWidths2 = { 4f, 0.5f, 3.5f, 1f };
		detailsNestedTable.setWidths(nestedColumnWidths2);

		PdfPCell detailsNestedCell_1 = new PdfPCell(new Phrase(
				"C.O.D. CHARGE\nTO BE PAID BY\n\nSHIPPER [   ]\nCONSIGNEE [   ]", font_6_bold));
		detailsNestedCell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedCell_1.setBorderWidthTop(0);
		detailsNestedCell_1.setBorderWidthRight(0);
		detailsNestedCell_1.setBorderWidthLeft(0);
		detailsNestedCell_1.setColspan(4);
		detailsNestedTable.addCell(detailsNestedCell_1);
		
		return detailsNestedTable;
	}

	/**
	 * Sets up nested table containing section 7
	 * 
	 * @param font_8_normal
	 *            , font_6_bold Fonts to use
	 */
	private PdfPTable createDetailsNested_9(Font font_8_normal, Font font_6_bold)
			throws DocumentException {
		PdfPTable detailsNestedTable = new PdfPTable(4);
		detailsNestedTable.setWidthPercentage(100);
		float[] nestedColumnWidths2 = { 4f, 0.5f, 3.5f, 1f };
		detailsNestedTable.setWidths(nestedColumnWidths2);

		Paragraph paragraph = new Paragraph(
				"Subject to Section 7 of conditions, if this shipment is to be delivered to the consignee without recourse on the consignor, "
						+ "the consignor shall sign the following statements:\n\n",
				font_4_normal);
		paragraph
				.add(new Phrase(
						"The carrier shall not make delivery of this shipment without payment of freight and all other lawful charges.\n\n\n\n\n\n",
						font_4_normal));
		paragraph.add(new Phrase("RITE ENG. & MFG. CO.", font_6_bold));
		paragraph.setAlignment(Element.ALIGN_CENTER);

		PdfPCell detailsNestedCell_1 = new PdfPCell();
		detailsNestedCell_1.addElement(paragraph);
		detailsNestedCell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		// detailsNestedCell_1.setBorderWidthTop(0);
		// detailsNestedCell_1.setBorderWidthRight(0);
		// detailsNestedCell_1.setBorderWidthLeft(0);
		detailsNestedCell_1.setColspan(4);
		detailsNestedTable.addCell(detailsNestedCell_1);
		return detailsNestedTable;
	}

	/**
	 * Sets up nested table containing signature of consignor.
	 * 
	 * @param font_8_normal
	 *            , font_6_bold Fonts to use
	 */
	private PdfPTable createDetailsNested_10(Font font_8_normal,
			Font font_6_bold) throws DocumentException {
		PdfPTable detailsNestedTable = new PdfPTable(4);
		detailsNestedTable.setWidthPercentage(100);
		float[] nestedColumnWidths2 = { 4f, 0.5f, 3.5f, 1f };
		detailsNestedTable.setWidths(nestedColumnWidths2);

		Paragraph paragraph = new Paragraph(
				"(Signature of Consigner)\n\n\n\n\n\n", font_4_normal);
		paragraph.setAlignment(Element.ALIGN_CENTER);

		PdfPCell detailsNestedCell_1 = new PdfPCell();
		detailsNestedCell_1.addElement(paragraph);
		detailsNestedCell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		// detailsNestedCell_1.setBorderWidthTop(0);
		// detailsNestedCell_1.setBorderWidthRight(0);
		// detailsNestedCell_1.setBorderWidthLeft(0);
		detailsNestedCell_1.setColspan(4);
		detailsNestedTable.addCell(detailsNestedCell_1);
		return detailsNestedTable;
	}

	/**
	 * Sets up nested table containing prepaid information.
	 * 
	 * @param font_8_normal
	 *            , font_6_bold Fonts to use
	 */
	private PdfPTable createDetailsNested_11(Font font_8_normal,
			Font font_6_bold) throws DocumentException {
		PdfPTable detailsNestedTable = new PdfPTable(4);
		detailsNestedTable.setWidthPercentage(100);
		float[] nestedColumnWidths2 = { 4f, 0.5f, 3.5f, 1f };
		detailsNestedTable.setWidths(nestedColumnWidths2);

		Paragraph paragraph = new Paragraph(
				"If charges are to be prepaid, write or stamp here, 'To be Prepaid'\n\n",
				font_4_normal);
		
		if (billOfLading.getIsPrepaid()) {
			paragraph.add(new Phrase("PREPAID\n\n", font_6_bold));
		} else if (billOfLading.getOtherPayment() != null) {
			paragraph.add(new Phrase(billOfLading.getOtherPayment().toUpperCase() + "\n\n", font_6_bold));
		} else {
			paragraph.add(new Phrase(" ", font_6_bold));
		}
		
		paragraph.setAlignment(Element.ALIGN_CENTER);

		PdfPCell detailsNestedCell_1 = new PdfPCell();
		detailsNestedCell_1.addElement(paragraph);
		detailsNestedCell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		// detailsNestedCell_1.setBorderWidthTop(0);
		// detailsNestedCell_1.setBorderWidthRight(0);
		// detailsNestedCell_1.setBorderWidthLeft(0);
		detailsNestedCell_1.setColspan(4);
		detailsNestedTable.addCell(detailsNestedCell_1);
		return detailsNestedTable;
	}

	/**
	 * Sets up nested table containing prepayment.
	 * 
	 * @param font_8_normal
	 *            , font_6_bold Fonts to use
	 */
	private PdfPTable createDetailsNested_12(Font font_8_normal,
			Font font_6_bold) throws DocumentException {
		PdfPTable detailsNestedTable = new PdfPTable(4);
		detailsNestedTable.setWidthPercentage(100);
		float[] nestedColumnWidths2 = { 4f, 0.5f, 3.5f, 1f };
		detailsNestedTable.setWidths(nestedColumnWidths2);

		Phrase ph = new Phrase(
				"\nReceived $ _____________________________ to apply in prepayment of the charges on the property described hereon.\n\n",
				font_4_normal);

		PdfPCell detailsNestedCell_1 = new PdfPCell();
		detailsNestedCell_1.addElement(ph);
		detailsNestedCell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		// detailsNestedCell_1.setBorderWidthTop(0);
		// detailsNestedCell_1.setBorderWidthRight(0);
		// detailsNestedCell_1.setBorderWidthLeft(0);
		detailsNestedCell_1.setColspan(4);
		detailsNestedTable.addCell(detailsNestedCell_1);
		return detailsNestedTable;
	}

	/**
	 * Sets up nested table containing agent or cashier.
	 * 
	 * @param font_8_normal
	 *            , font_6_bold Fonts to use
	 */
	private PdfPTable createDetailsNested_13(Font font_8_normal,
			Font font_6_bold) throws DocumentException {
		PdfPTable detailsNestedTable = new PdfPTable(4);
		detailsNestedTable.setWidthPercentage(100);
		float[] nestedColumnWidths2 = { 4f, 0.5f, 3.5f, 1f };
		detailsNestedTable.setWidths(nestedColumnWidths2);

		Paragraph paragraph = new Paragraph("Agent or Cashier\n\n\n\n\n\n",
				font_4_normal);
		paragraph.setAlignment(Element.ALIGN_CENTER);

		Phrase ph = new Phrase(
				"PER _____________________________________________\n"
						+ "(The signature here acknowledges only the amount prepaid.)",
				font_4_normal);

		PdfPCell detailsNestedCell_1 = new PdfPCell();
		detailsNestedCell_1.addElement(paragraph);
		detailsNestedCell_1.addElement(ph);
		detailsNestedCell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		// detailsNestedCell_1.setBorderWidthTop(0);
		// detailsNestedCell_1.setBorderWidthRight(0);
		// detailsNestedCell_1.setBorderWidthLeft(0);
		detailsNestedCell_1.setColspan(4);
		detailsNestedTable.addCell(detailsNestedCell_1);
		return detailsNestedTable;
	}

	/**
	 * Sets up nested table containing charges advanced.
	 * 
	 * @param font_8_normal
	 *            , font_6_bold Fonts to use
	 */
	private PdfPTable createDetailsNested_14(Font font_8_normal,
			Font font_6_bold) throws DocumentException {
		PdfPTable detailsNestedTable = new PdfPTable(4);
		detailsNestedTable.setWidthPercentage(100);
		float[] nestedColumnWidths2 = { 4f, 0.5f, 3.5f, 1f };
		detailsNestedTable.setWidths(nestedColumnWidths2);

		Paragraph paragraph = new Paragraph("Charges Advanced:\n\n",
				font_4_normal);
		paragraph.add(new Phrase("$\n\n", font_6_bold));
		paragraph.setAlignment(Element.ALIGN_LEFT);

		PdfPCell detailsNestedCell_1 = new PdfPCell();
		detailsNestedCell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		detailsNestedCell_1.addElement(paragraph);
		detailsNestedCell_1.setBorderWidthTop(0);
		detailsNestedCell_1.setBorderWidthRight(0);
		detailsNestedCell_1.setBorderWidthLeft(0);
		detailsNestedCell_1.setColspan(4);
		detailsNestedTable.addCell(detailsNestedCell_1);
		return detailsNestedTable;
	}
}
