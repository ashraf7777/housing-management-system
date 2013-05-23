package model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * This class creates a PDF invoice for the customer. It contains the addresses,
 * duration and total costs of the booking.
 * 
 * @author D20018
 * 
 */

public class Invoice {

	// Attributes to store the invoice's information
	Document document;
	Image logo;
	Image logo_description;
	Image signature;
	Paragraph addresses;
	Paragraph textPara;
	Chunk ownAddress;
	Chunk customerAddress;
	Chunk subject;
	Chunk text;
	Booking booking;
	// Formatting the dates and numbers
	SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.YYYY");
	DecimalFormat df = new DecimalFormat("####.00");
	DecimalFormat dfbookingID = new DecimalFormat("00000");

	/**
	 * This function gets the file path where the PDF should be saved. It asks
	 * the user for the directory and provides a default filename. The default
	 * filename consists of the tenant's name.
	 * 
	 * @return path or null directory where the PDF should be saved or null if
	 *         you choose the abort operation of the UI dialog
	 */
	public String getFileDirectory() {
		// Create the UI dialog
		JFileChooser chooser = new JFileChooser("c:/");
		chooser.setMultiSelectionEnabled(false);
		// Set the accepted file extensions to PDF
		chooser.setFileFilter(new FileFilter() {
			public boolean accept(File f) {
				return f.isDirectory()
						|| f.getName().toLowerCase().endsWith(".pdf");
			}

			public String getDescription() {
				return "Adobe PDF-Data (*.pdf)";
			}
		});

		// Set the tenants name as the default filename of the invoice
		chooser.setSelectedFile(new File(booking.getLastNameOfBooker() + "_"
				+ booking.getFirstNameOfBooker() + "_"
				+ sdf.format(booking.getCheckOutDate())));
		// show the UI dialog
		int result = chooser.showSaveDialog(null);

		File file = null;
		// Get the file path
		if (result == JFileChooser.APPROVE_OPTION) // keine Leereingaben
													// erlauben
		{
			file = chooser.getSelectedFile();
			String path = file.getPath();
			// Add .pdf if necessary and not already added
			if (!path.toLowerCase().endsWith(".pdf"))
				path = path + ".pdf";
			return path;
		}
		return null;
	}

	/**
	 * This function stores the data from the booking into the invoice's
	 * attributes. Provide all the necessary data for the invoice so that it can
	 * be printed afterwards. The data are getting already structured and
	 * categoriced for the PDF.
	 * 
	 * @param b
	 *            represents the booking data element which stores all the
	 *            necessary data for the invoice
	 */
	public void writePdf(Booking b) {

		booking = b;
		// Elements to structure the PDF
		addresses = new Paragraph();
		textPara = new Paragraph();

		addresses.setSpacingAfter(100);
		addresses.setSpacingBefore(25);

		// Set the own address for the invoices
		ownAddress = new Chunk("UV\n2000 Oxford Ave.\nCA 92831\n\n\n\n");
		addresses.add(ownAddress);

		// Formatting the tenant's address
		String address = booking.getFirstNameOfBooker() + " "
				+ booking.getLastNameOfBooker() + "\n" + booking.getStreet()
				+ "\n" + booking.getZipCode() + " " + booking.getCity();
		// Put the formatted address into a structure item
		customerAddress = new Chunk(address);
		// Add the small structure item to the bigger one
		addresses.add(customerAddress);

		// Set the subject of the invoice
		subject = new Chunk("Invoice for your stay at UV with Booking ID: "
				+ dfbookingID.format(booking.getBookingID()) + "\n\n\n");
		// Formatting of the text and put it into a structure item
		text = new Chunk("Dear Mrs./Mr. " + booking.getLastNameOfBooker()
				+ ",\n\n" + "the total amount of your stay from "
				+ sdf.format(booking.getCheckInDate()) + " to "
				+ sdf.format(booking.getCheckOutDate()) + " is $"
				+ df.format(booking.getTotalCosts()) + "."
				+ "\n\n\n\nThank you for your stay,");
		// Add the small structure item to the bigger one
		textPara.add(text);
	}

	/**
	 * Create the PDF file out of the earlier setted information. The file will
	 * be saved to the choosen directory with the choosen or default filename
	 * depending of the user's choice.
	 * 
	 * @throws IOException
	 * @throws DocumentException
	 */
	public void createPDF() throws IOException, DocumentException {
		// Ask the user for a filename and directory to save the invoice
		String filename = getFileDirectory();

		// If you have aborted or closed the UI dialog of choosing a directory
		// you get a notice that you can print the invoice also it later
		if (null == filename) {
			JOptionPane
					.showMessageDialog(
							null,
							"Your checkout is completed. You can print the invoice later out of the invoice overview.",
							"Print invoice - aborted",
							JOptionPane.INFORMATION_MESSAGE);
		} else {
			// Create an empty PDF structure
			document = new Document();
			// Set the directory for the file output
			PdfWriter.getInstance(document, new FileOutputStream(filename));
			// Load a picture for the invoice's logo
			logo = Image.getInstance(this.getClass().getResource(
					"/home_big.png"));
			// Set the picture's position in the PDF file
			logo.setAbsolutePosition(450f, 720f);
			// Set the size of the logo
			logo.scalePercent(60);
			// Load the logo description as a picture
			logo_description = Image.getInstance(this.getClass().getResource(
					"/Logo_Description.png"));
			// Set the position
			logo_description.setAbsolutePosition(433f, 680f);
			// Set the size
			logo_description.scalePercent(70f);
			// Load a sample signature
			signature = Image.getInstance(this.getClass().getResource(
					"/signature.jpg"));
			// Set the position for the signature
			signature.setAbsolutePosition(30, 325);
			// Set the size
			signature.scalePercent(15);

			// Open the PDF structure for editing
			document.open();
			// Add all the earlier setted structual items with the booking
			// information
			document.add(logo);
			document.add(logo_description);
			document.add(addresses);
			document.add(subject);
			document.add(textPara);
			document.add(signature);

			// Finish the editing and save the file to the choosen directory
			document.close();
		}
	}

	/**
	 * Provides an object list for the invoice overview table
	 * 
	 * @return
	 */
	public Object[] returnForInvoices() {
		Object[] data = { this.booking.getBookingID(),
				this.booking.getLastNameOfBooker(),
				this.booking.getFirstNameOfBooker(),
				sdf.format(this.booking.getCheckInDate()),
				sdf.format(this.booking.getCheckOutDate()),
				this.booking.getTotalCosts() };
		return data;
	}
}
