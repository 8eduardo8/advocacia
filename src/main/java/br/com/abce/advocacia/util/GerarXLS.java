package br.com.abce.advocacia.util;

import java.io.File;

import jxl.CellView;
import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class GerarXLS {
	private WritableWorkbook workbook;
	private WritableSheet sheet;

	private WritableCellFormat cfTitulo;
	private WritableCellFormat cf;
	private WritableCellFormat cfNumber;

	public GerarXLS(File file) throws Exception {
		workbook = Workbook.createWorkbook(file);
		sheet = workbook.createSheet("Planilha 1", 0);

		cfTitulo = new WritableCellFormat();
		cfTitulo.setBorder(Border.ALL, BorderLineStyle.THIN);
		cfTitulo.setBackground(Colour.PALETTE_BLACK);
		cfTitulo.setFont(new WritableFont(WritableFont.createFont("Arial"), WritableFont.DEFAULT_POINT_SIZE,
				WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE));

		cf = new WritableCellFormat();
		cf.setBorder(Border.ALL, BorderLineStyle.THIN);

		cfNumber = new WritableCellFormat(new NumberFormat("#,##0.00"));
		cfNumber.setBorder(Border.ALL, BorderLineStyle.THIN);
	}

	public void addTitle(int col, int row, Object content) throws Exception {
		sheet.addCell(new Label(col, row, (String) content, cfTitulo));

		CellView cv = sheet.getColumnView(col);
		cv.setAutosize(true);
		sheet.setColumnView(col, cv);

	}

	public void add(int col, int row, Object content) throws Exception {
		if (content instanceof String) {
			sheet.addCell(new Label(col, row, (String) content, cf));
		} else if (content instanceof Integer) {
			sheet.addCell(new jxl.write.Number(col, row, (int) content, cf));
		} else if (content instanceof Double) {
			sheet.addCell(new jxl.write.Number(col, row, (double) content, cfNumber));
		} else if (content instanceof Float) {
			sheet.addCell(new jxl.write.Number(col, row, (float) content, cfNumber));
		}

	}

	public void salvar() throws Exception {
		workbook.write();
		workbook.close();
	}

}
