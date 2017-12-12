package com.riteboiler.frontoffice.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.riteboiler.frontoffice.MainApp;
import com.riteboiler.frontoffice.model.Acknowledgment;

/**
 * This class is a utility class which formats the data from an acknowledgment
 * into a print-ready PDF.
 * @author Chad Meza
 */
public class AcknowledgmentPDF {
	private Acknowledgment acknowledgment;
	private MainApp main;

	private Font font_16_bold = new Font(Font.FontFamily.HELVETICA, 16,
			Font.BOLD);
	private Font font_16_normal = new Font(Font.FontFamily.HELVETICA, 16,
			Font.NORMAL);
	private Font font_14_normal = new Font(Font.FontFamily.HELVETICA, 14,
			Font.NORMAL);
	private Font font_10_bold = new Font(Font.FontFamily.HELVETICA, 10,
			Font.BOLD);
	private Font font_10_normal = new Font(Font.FontFamily.HELVETICA, 10,
			Font.NORMAL);
	private Font font_6_normal = new Font(Font.FontFamily.HELVETICA, 6,
			Font.NORMAL);

	/**
	 * Constructor with the reference to the acknowledgment to use for this PDF.
	 * @param acknowledgment Acknowledgment to save
	 */
	public AcknowledgmentPDF(Acknowledgment acknowledgment) {
		this.acknowledgment = acknowledgment;
	}

	/**
	 * Setter for reference to the main application.
	 * @param main Current MainApp instance
	 */
	public void setMain(MainApp main) {
		this.main = main;
	}

	/**
	 * Saves the acknowledgment into a print-ready PDF.
	 */
	public void savePDF() {
		Document document = new Document(PageSize.LETTER, 15, 15, 15, 15);

		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(main.getPreferenceList().get(0)
							.getAckLocation()
							+ "\\"
							+ acknowledgment.getANumber()
							+ " Acknowledgment.pdf"));

			document.open();

			/**
			 * LINE 1 - HEADER
			 */

			PdfPTable headerTable = createHeader(font_16_bold, font_10_normal);
			document.add(headerTable);

			/**
			 * LINE 2 - CUSTOMER INFORMATION
			 */

			PdfPTable customerTable = createCustomer(font_10_normal,
					font_10_bold);
			document.add(customerTable);

			/**
			 * LINE 3 - SHIPPING / DETAILS
			 */

			PdfPTable shippingTable = createShipping(font_10_normal,
					font_6_normal);
			document.add(shippingTable);

			/**
			 * LINE 4 - PRODUCTS
			 */

			PdfPTable productsTable = createProducts(font_10_normal);
			document.add(productsTable);

			document.close();
		} catch (FileNotFoundException | DocumentException e) {
			main.getDbHelper().insertError(e,
					main.getPreferenceList().get(0).getUsername());
		}
	}

	/**
	 * Sets up the header table.
	 * @param font_16_bold, font_10_normal Fonts to use
	 */
	private PdfPTable createHeader(Font font_16_bold, Font font_10_normal)
			throws DocumentException {
		PdfPTable headerTable = new PdfPTable(3);
		headerTable.setWidthPercentage(100);
		float[] columnWidths = { 1f, 4f, 1f };
		headerTable.setWidths(columnWidths);
		headerTable.setSpacingAfter(10f);

		PdfPCell headerCell_1 = new PdfPCell(new Phrase("", font_10_normal));
		headerCell_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell_1.setBorder(Rectangle.NO_BORDER);
		headerTable.addCell(headerCell_1);

		PdfPCell headerCell_2 = new PdfPCell(new Phrase(
				"RITE ENGINEERING & MANUFACTURING CORP.", font_16_bold));
		headerCell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
		headerCell_2.setBorder(Rectangle.NO_BORDER);
		headerTable.addCell(headerCell_2);

		PdfPCell headerCell_3 = new PdfPCell(new Phrase("" + acknowledgment.getANumber(),
				FontFactory.getFont(FontFactory.HELVETICA, 16, Font.NORMAL,
						new CMYKColor(0, 255, 255, 17))));
		headerCell_3.setHorizontalAlignment(Element.ALIGN_RIGHT);
		headerCell_3.setBorder(Rectangle.NO_BORDER);
		headerTable.addCell(headerCell_3);

		PdfPCell headerCell_4 = new PdfPCell(new Phrase("", font_10_normal));
		headerCell_4.setHorizontalAlignment(Element.ALIGN_LEFT);
		headerCell_4.setBorder(Rectangle.NO_BORDER);
		headerTable.addCell(headerCell_4);

		PdfPCell headerCell_5 = new PdfPCell(new Phrase(
				"TEL (562) 862-2135 - FAX (562) 861-9521", font_10_normal));
		headerCell_5.setHorizontalAlignment(Element.ALIGN_CENTER);
		headerCell_5.setBorder(Rectangle.NO_BORDER);
		headerTable.addCell(headerCell_5);

		PdfPCell headerCell_6 = new PdfPCell(new Phrase("DATE " + acknowledgment.getDate(),
				font_10_normal));
		headerCell_6.setHorizontalAlignment(Element.ALIGN_RIGHT);
		headerCell_6.setBorder(Rectangle.NO_BORDER);
		headerTable.addCell(headerCell_6);
		return headerTable;
	}

	/**
	 * Sets up the customer table.
	 * @param font_10_normal, font_10_bold Fonts to use
	 */
	private PdfPTable createCustomer(Font font_10_normal, Font font_10_bold)
			throws DocumentException {
		PdfPTable customerTable = new PdfPTable(4);
		customerTable.setWidthPercentage(100);
		float[] columnWidths2 = { 1f, 4f, 1f, 4F };
		customerTable.setWidths(columnWidths2);
		customerTable.setSpacingAfter(1f);

		PdfPCell customerCell_1 = new PdfPCell(new Phrase("SOLD TO",
				font_10_bold));
		customerCell_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		customerCell_1.setBorder(Rectangle.NO_BORDER);
		customerTable.addCell(customerCell_1);

		Paragraph soldToAddress = new Paragraph();
		soldToAddress.add(new Phrase(acknowledgment.getSoldToAddress().getName().toUpperCase() + "\n",
				font_10_normal));
		soldToAddress.add(new Phrase(acknowledgment.getSoldToAddress().getStreet1().toUpperCase() + "\n", font_10_normal));
		soldToAddress.add(new Phrase(acknowledgment.getSoldToAddress().getCity().toUpperCase() + ", ", font_10_normal));
		soldToAddress.add(new Phrase(acknowledgment.getSoldToAddress().getState().toUpperCase() + " ", font_10_normal));
		soldToAddress.add(new Phrase(acknowledgment.getSoldToAddress().getZip().toUpperCase() + "\n\n", font_10_normal));

		PdfPCell customerCell_2 = new PdfPCell(soldToAddress);
		customerCell_2.setHorizontalAlignment(Element.ALIGN_LEFT);
		customerCell_2.setBorder(Rectangle.NO_BORDER);
		customerTable.addCell(customerCell_2);

		PdfPCell customerCell_3 = new PdfPCell(new Phrase("SHIP TO",
				font_10_bold));
		customerCell_3.setHorizontalAlignment(Element.ALIGN_RIGHT);
		customerCell_3.setBorder(Rectangle.NO_BORDER);
		customerTable.addCell(customerCell_3);

		Paragraph shipToAddress = new Paragraph();
		shipToAddress.add(new Phrase(acknowledgment.getShipToAddress().getName().toUpperCase() + "\n",
				font_10_normal));
		shipToAddress.add(new Phrase(acknowledgment.getShipToAddress().getStreet1().toUpperCase() + "\n",
				font_10_normal));
		shipToAddress.add(new Phrase(acknowledgment.getShipToAddress().getCity().toUpperCase() + ", ", font_10_normal));
		shipToAddress.add(new Phrase(acknowledgment.getShipToAddress().getState().toUpperCase() + " ", font_10_normal));
		shipToAddress.add(new Phrase(acknowledgment.getShipToAddress().getZip().toUpperCase() + "\n\n", font_10_normal));

		PdfPCell customerCell_4 = new PdfPCell(shipToAddress);
		customerCell_4.setHorizontalAlignment(Element.ALIGN_LEFT);
		customerCell_4.setBorder(Rectangle.NO_BORDER);
		customerTable.addCell(customerCell_4);

		PdfPCell customerCell_5 = new PdfPCell(
				new Phrase(
						"\n*FREIGHT ESTIMATE\nSUBJECT TO CHANGE BY SHIP DATE",
						font_10_normal));
		customerCell_5.setHorizontalAlignment(Element.ALIGN_LEFT);
		customerCell_5.setBorder(Rectangle.NO_BORDER);
		customerCell_5.setColspan(2);
		customerTable.addCell(customerCell_5);

		PdfPCell customerCell_6 = new PdfPCell(new Phrase("NOTIFY BEFORE DEL.",
				font_10_bold));
		customerCell_6.setHorizontalAlignment(Element.ALIGN_RIGHT);
		customerCell_6.setBorder(Rectangle.NO_BORDER);
		customerTable.addCell(customerCell_6);

		if (acknowledgment.getNotify() == null) {
			PdfPCell customerCell_7 = new PdfPCell(
					new Phrase(
							"",
							font_10_normal));
			customerCell_7.setHorizontalAlignment(Element.ALIGN_LEFT);
			customerCell_7.setBorder(Rectangle.NO_BORDER);
			customerTable.addCell(customerCell_7);
		} else {
			PdfPCell customerCell_7 = new PdfPCell(
					new Phrase(
							acknowledgment.getNotify().toUpperCase(),
							font_10_normal));
			customerCell_7.setHorizontalAlignment(Element.ALIGN_LEFT);
			customerCell_7.setBorder(Rectangle.NO_BORDER);
			customerTable.addCell(customerCell_7);
		}

		PdfPCell customerCell_8 = new PdfPCell(new Phrase("", font_10_normal));
		customerCell_8.setHorizontalAlignment(Element.ALIGN_LEFT);
		customerCell_8.setBorder(Rectangle.NO_BORDER);
		customerCell_8.setColspan(2);
		customerTable.addCell(customerCell_8);

		PdfPCell customerCell_9 = new PdfPCell(
				new Phrase("\nTAG", font_10_bold));
		customerCell_9.setHorizontalAlignment(Element.ALIGN_RIGHT);
		customerCell_9.setBorder(Rectangle.NO_BORDER);
		customerTable.addCell(customerCell_9);

		if (acknowledgment.getTag() == null) {
			PdfPCell customerCell_10 = new PdfPCell(new Phrase("\n",
					font_10_normal));
			customerCell_10.setHorizontalAlignment(Element.ALIGN_LEFT);
			customerCell_10.setBorder(Rectangle.NO_BORDER);
			customerTable.addCell(customerCell_10);
		} else {
			PdfPCell customerCell_10 = new PdfPCell(new Phrase("\n" + acknowledgment.getTag().toUpperCase(),
					font_10_normal));
			customerCell_10.setHorizontalAlignment(Element.ALIGN_LEFT);
			customerCell_10.setBorder(Rectangle.NO_BORDER);
			customerTable.addCell(customerCell_10);
		}
		
		return customerTable;
	}

	/**
	 * Sets up the shipping table.
	 * @param font_10_normal, font_6_normal Fonts to use
	 */
	private PdfPTable createShipping(Font font_10_normal, Font font_6_normal)
			throws DocumentException {
		PdfPTable shippingTable = new PdfPTable(6);
		shippingTable.setWidthPercentage(100);
		float[] columnWidths3 = { 1f, 2f, 1f, 1f, 1f, 2f };
		shippingTable.setWidths(columnWidths3);
		shippingTable.setSpacingAfter(2f);

		PdfPCell shippingCell_1 = new PdfPCell(new Phrase("DATE ORDER REC'D",
				font_6_normal));
		shippingCell_1.setHorizontalAlignment(Element.ALIGN_LEFT);
		shippingCell_1.setBorderWidthBottom(0);
		shippingCell_1.setBorderWidthRight(0);
		shippingTable.addCell(shippingCell_1);

		PdfPCell shippingCell_2 = new PdfPCell(new Phrase("CUSTOMER ORDER NO.",
				font_6_normal));
		shippingCell_2.setHorizontalAlignment(Element.ALIGN_LEFT);
		shippingCell_2.setBorderWidthBottom(0);
		shippingCell_2.setBorderWidthRight(0);
		shippingTable.addCell(shippingCell_2);

		PdfPCell shippingCell_3 = new PdfPCell(new Phrase("APPROX. SHIP DATE",
				font_6_normal));
		shippingCell_3.setHorizontalAlignment(Element.ALIGN_LEFT);
		shippingCell_3.setBorderWidthBottom(0);
		shippingCell_3.setBorderWidthRight(0);
		shippingTable.addCell(shippingCell_3);

		PdfPCell shippingCell_4 = new PdfPCell(new Phrase("SHIPPING COST",
				font_6_normal));
		shippingCell_4.setHorizontalAlignment(Element.ALIGN_LEFT);
		shippingCell_4.setBorderWidthBottom(0);
		shippingCell_4.setBorderWidthRight(0);
		shippingTable.addCell(shippingCell_4);

		PdfPCell shippingCell_5 = new PdfPCell(new Phrase("FOB", font_6_normal));
		shippingCell_5.setHorizontalAlignment(Element.ALIGN_LEFT);
		shippingCell_5.setBorderWidthBottom(0);
		shippingCell_5.setBorderWidthRight(0);
		shippingTable.addCell(shippingCell_5);

		PdfPCell shippingCell_6 = new PdfPCell(new Phrase("VIA", font_6_normal));
		shippingCell_6.setHorizontalAlignment(Element.ALIGN_LEFT);
		shippingCell_6.setBorderWidthBottom(0);
		shippingTable.addCell(shippingCell_6);

		if (acknowledgment.getDateOrderReceived() == null) {
			PdfPCell shippingCell_7 = new PdfPCell(new Phrase("",
					font_10_normal));
			shippingCell_7.setHorizontalAlignment(Element.ALIGN_CENTER);
			shippingCell_7.setBorderWidthTop(0);
			shippingCell_7.setBorderWidthRight(0);
			shippingTable.addCell(shippingCell_7);
		} else {
			PdfPCell shippingCell_7 = new PdfPCell(new Phrase(acknowledgment.getDateOrderReceived().toUpperCase(),
					font_10_normal));
			shippingCell_7.setHorizontalAlignment(Element.ALIGN_CENTER);
			shippingCell_7.setBorderWidthTop(0);
			shippingCell_7.setBorderWidthRight(0);
			shippingTable.addCell(shippingCell_7);
		}

		if (acknowledgment.getCustomerOrderNumber() == null) {
			PdfPCell shippingCell_8 = new PdfPCell(new Phrase("",
					font_10_normal));
			shippingCell_8.setHorizontalAlignment(Element.ALIGN_LEFT);
			shippingCell_8.setBorderWidthTop(0);
			shippingCell_8.setBorderWidthRight(0);
			shippingTable.addCell(shippingCell_8);
		} else {
			PdfPCell shippingCell_8 = new PdfPCell(new Phrase(acknowledgment.getCustomerOrderNumber().toUpperCase(),
					font_10_normal));
			shippingCell_8.setHorizontalAlignment(Element.ALIGN_LEFT);
			shippingCell_8.setBorderWidthTop(0);
			shippingCell_8.setBorderWidthRight(0);
			shippingTable.addCell(shippingCell_8);
		}

		if (acknowledgment.getApproxShipDate() == null) {
			PdfPCell shippingCell_9 = new PdfPCell(new Phrase("",
					font_10_normal));
			shippingCell_9.setHorizontalAlignment(Element.ALIGN_CENTER);
			shippingCell_9.setBorderWidthTop(0);
			shippingCell_9.setBorderWidthRight(0);
			shippingTable.addCell(shippingCell_9);
		} else {
			PdfPCell shippingCell_9 = new PdfPCell(new Phrase(acknowledgment.getApproxShipDate().toUpperCase(),
					font_10_normal));
			shippingCell_9.setHorizontalAlignment(Element.ALIGN_CENTER);
			shippingCell_9.setBorderWidthTop(0);
			shippingCell_9.setBorderWidthRight(0);
			shippingTable.addCell(shippingCell_9);
		}

		if (acknowledgment.getShippingCost() == null) {
			PdfPCell shippingCell_10 = new PdfPCell(new Phrase("",
					font_10_normal));
			shippingCell_10.setHorizontalAlignment(Element.ALIGN_CENTER);
			shippingCell_10.setBorderWidthTop(0);
			shippingCell_10.setBorderWidthRight(0);
			shippingTable.addCell(shippingCell_10);
		} else {
			PdfPCell shippingCell_10 = new PdfPCell(new Phrase(acknowledgment.getShippingCost().toUpperCase(),
					font_10_normal));
			shippingCell_10.setHorizontalAlignment(Element.ALIGN_CENTER);
			shippingCell_10.setBorderWidthTop(0);
			shippingCell_10.setBorderWidthRight(0);
			shippingTable.addCell(shippingCell_10);
		}

		if (acknowledgment.getFob() == null) {
			PdfPCell shippingCell_11 = new PdfPCell(new Phrase("",
					font_10_normal));
			shippingCell_11.setHorizontalAlignment(Element.ALIGN_CENTER);
			shippingCell_11.setBorderWidthTop(0);
			shippingCell_11.setBorderWidthRight(0);
			shippingTable.addCell(shippingCell_11);
		} else {
			PdfPCell shippingCell_11 = new PdfPCell(new Phrase(acknowledgment.getFob().toUpperCase(),
					font_10_normal));
			shippingCell_11.setHorizontalAlignment(Element.ALIGN_CENTER);
			shippingCell_11.setBorderWidthTop(0);
			shippingCell_11.setBorderWidthRight(0);
			shippingTable.addCell(shippingCell_11);
		}

		if (acknowledgment.getShipVia() == null) {
			PdfPCell shippingCell_12 = new PdfPCell(new Phrase("",
					font_10_normal));
			shippingCell_12.setHorizontalAlignment(Element.ALIGN_LEFT);
			shippingCell_12.setBorderWidthTop(0);
			shippingTable.addCell(shippingCell_12);
		} else {
			PdfPCell shippingCell_12 = new PdfPCell(new Phrase(acknowledgment.getShipVia().toUpperCase(),
					font_10_normal));
			shippingCell_12.setHorizontalAlignment(Element.ALIGN_LEFT);
			shippingCell_12.setBorderWidthTop(0);
			shippingTable.addCell(shippingCell_12);
		}
		
		return shippingTable;
	}

	/**
	 * Sets up the products table.
	 * @param font_10_normal Font to use
	 */
	private PdfPTable createProducts(Font font_10_normal)
			throws DocumentException {
		PdfPTable productsTable = new PdfPTable(6);
		productsTable.setWidthPercentage(100);
		float[] columnWidths4 = { 1f, 2f, 1f, 1f, 1f, 2f };
		productsTable.setWidths(columnWidths4);

		PdfPCell productsCell_1 = new PdfPCell(new Phrase("QUANTITY",
				font_10_normal));
		productsCell_1.setHorizontalAlignment(Element.ALIGN_CENTER);
		productsTable.addCell(productsCell_1);

		PdfPCell productsCell_2 = new PdfPCell(new Phrase("DESCRIPTION",
				font_10_normal));
		productsCell_2.setHorizontalAlignment(Element.ALIGN_CENTER);
		productsCell_2.setColspan(5);
		productsTable.addCell(productsCell_2);
		
		for (int i = 0; i < acknowledgment.getProductData().size(); i++) {
			PdfPCell productsCell_3 = new PdfPCell(new Phrase("" + acknowledgment.getProductData().get(i).getQuantity(), font_10_normal));
			productsCell_3.setHorizontalAlignment(Element.ALIGN_CENTER);
			productsTable.addCell(productsCell_3);

			Paragraph description = new Paragraph(10);
			description
					.add(new Phrase(
							acknowledgment.getProductData().get(i).getDescription().toUpperCase() + "\n\n",
							font_10_normal));
			
			PdfPCell productsCell_4 = new PdfPCell();
			productsCell_4.addElement(description);
			productsCell_4.setHorizontalAlignment(Element.ALIGN_LEFT);
			productsCell_4.setColspan(5);
			productsTable.addCell(productsCell_4);
		}

		PdfPCell productsCell_5 = new PdfPCell(new Phrase("", font_10_normal));
		productsCell_5.setHorizontalAlignment(Element.ALIGN_CENTER);
		productsTable.addCell(productsCell_5);

		Paragraph literature = new Paragraph(10);
		
		if (acknowledgment.getDocuments() != null) {
			literature.add(new Phrase("\n\n", font_10_normal));
			literature.add(new Phrase(acknowledgment.getDocuments().toUpperCase(),
					font_10_normal));
		} else {
			literature.add(new Phrase("\n\n", font_10_normal));
			literature.add(new Phrase("\n\n", font_10_normal));
		}

		Paragraph price = new Paragraph(10);
		price.add(new Phrase("\n\n", font_10_normal));
		
		if (acknowledgment.getPrice() == null) {
			price.add(new Phrase(
					"YOUR NET, FOB COMMERCE, CA: __________ PLUS FREIGHT*",
					font_10_normal));
		} else {
			price.add(new Phrase(
					"YOUR NET, FOB COMMERCE, CA: " + acknowledgment.getPrice().toUpperCase() + " PLUS FREIGHT*",
					font_10_normal));
		}

		Paragraph acknowledgmentText = new Paragraph(10);
		acknowledgmentText.setAlignment(Element.ALIGN_CENTER);
		acknowledgmentText.add(new Phrase("\n\n", font_10_normal));
		acknowledgmentText.add(new Phrase("ACKNOWLEDGMENT", font_10_normal));

		PdfPCell productsCell_6 = new PdfPCell();
		productsCell_6.addElement(literature);
		productsCell_6.addElement(price);
		productsCell_6.addElement(acknowledgmentText);
		productsCell_6.setHorizontalAlignment(Element.ALIGN_LEFT);
		productsCell_6.setColspan(5);
		productsTable.addCell(productsCell_6);
		
		return productsTable;
	}
}
