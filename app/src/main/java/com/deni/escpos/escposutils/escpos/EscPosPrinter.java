package com.deni.escpos.escposutils.escpos;


import com.dantsu.escposprinter.EscPosPrinterSize;
import com.dantsu.escposprinter.connection.DeviceConnection;

/**
 * @author Deni Setiawan, 22-05-2023
 * @github https://www.github.com/denitiawan
 */

/**
 * this class for initialize
 * @DeviceConnection printer connection
 * @textToPrint
 * @printWithOpenCashDrawer printout receipt with open cash drawer
 *
 */

public class EscPosPrinter extends EscPosPrinterSize {
    private DeviceConnection printerConnection;
    private String textToPrint = "";
    private String printWithOpenCashDrawer = "";

    public EscPosPrinter(DeviceConnection printerConnection,
                         int printerDpi,
                         float printerWidthMM,
                         int printerNbrCharactersPerLine,
                         String printWithOpenCashDrawer) {
        super(printerDpi, printerWidthMM, printerNbrCharactersPerLine);
        this.printerConnection = printerConnection;
        this.printWithOpenCashDrawer = printWithOpenCashDrawer;
    }

    public DeviceConnection getPrinterConnection() {
        return this.printerConnection;
    }

    public EscPosPrinter setTextToPrint(String textToPrint) {
        this.textToPrint = textToPrint;
        return this;
    }

    public String getTextToPrint() {
        return this.textToPrint;
    }

    public String getPrintWithOpenCashDrawer() {
        return this.printWithOpenCashDrawer;
    }

    public void setPrintWithOpenCashDrawer(String printWithOpenCashDrawer) {
        this.printWithOpenCashDrawer = printWithOpenCashDrawer;
    }
}