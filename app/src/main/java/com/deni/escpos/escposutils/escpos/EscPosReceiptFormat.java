package com.deni.escpos.escposutils.escpos;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.DisplayMetrics;

import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.textparser.PrinterTextParserImg;
import com.deni.escpos.R;
import com.deni.escpos.escposutils.model.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Deni Setiawan, 22-05-2023
 * @github https://www.github.com/denitiawan
 */
public class EscPosReceiptFormat {


    @SuppressLint("SimpleDateFormat")
    public static EscPosPrinter doPrintWithEscPos(final Context context,
                                                  DeviceConnection printerConnection,
                                                  Transaction transaction) {


        /**
         * @printerConnection
         * @printerDpi
         * @printerWidthMM
         * @printerNbrCharacterPerline
         * @printWithOpenCashDrawer
         */
        String printWithOpenCashDrawer = "Y";
        EscPosPrinter printer = new EscPosPrinter(
                printerConnection,
                203,
                48f,
                32,
                printWithOpenCashDrawer);

        // using original format receipt by (escpos)
        //return printer.setTextToPrint(formatReceiptOriginalFromLibrarry(context, printer));

        // using custom format receipt by (https://github.com/denitiawan)
        return printer.setTextToPrint(formatReceiptCustom(context, printer, transaction));

    }

    /**
     * Customize receipt format [START]
     */
    // STYLE ------------------------------------------------------------------
    public static String left = "[L]";
    public static String right = "[R]";
    public static String center = "[C]";

    static String setEnter(String align) {
        return align + "\n";
    }

    static String setBorder() {
        return "[C]================================\n";
    }


    static String setBorderThin() {
        return "[C]--------------------------------\n";
    }

    static String setBorder(String align, int jumlahBorder, String customBorder) {
        String border = "";
        for (int i = 0; i < jumlahBorder; i++) {
            border += customBorder;
        }
        return align + border + "\n";
    }

    static String setBarcode(String align, String barcodeKey) {
        return align + "<barcode type='ean13' height='10'>" + barcodeKey + "</barcode>\n";
        //return "[C]<barcode type='ean13' height='10'>831254784551</barcode>\n";
    }

    static String setQRCode(String align, String url) {
        return align + "<qrcode size='20'>" + url + "</qrcode>\n";
    }


    static String showData(String settingValue, String data) {
        if (settingValue.equalsIgnoreCase("Y")) {
            return data;
        } else return "";
    }

    // HEADER ------------------------------------------------------------------
    static String setTitle(String align, String data) {
        return align + "<u><font size='big'>" + data + "°045</font></u>\n";
    }

    static String setTransactionDate(String align, String data) {
        return align + "<u type='double'>" + data + "</u>\n";
    }


    // ITEM ------------------------------------------------------------------
    static String setItemName(String alignName, String itemName, String alignPrice, String itemPrice) {
        return alignName + "<b>" + itemName + "</b>" + alignPrice + "" + itemPrice + "\n";
    }

    static String setItemValue(String align1, String text1, String text2) {
        return align1 + "  " + text1 + " pcs x " + text2 + "\n";
    }

    static String setItemDiskon(String align1, String text1) {
        return align1 + "  " + "Diskon  " + text1 + "\n";
    }

    // TOTAL ------------------------------------------------------------------
    static String setTotal(String align1, String text1, String align2, String text2) {
        return align1 + "<b>" + text1 + "</b>" + align2 + "" + text2 + "\n";
    }

    // CUSTOMER ------------------------------------------------------------------
    static String setText(String align1, String text1, String align2, String text2) {
        return align1 + "" + text1 + "" + align2 + "  " + text2 + "\n";
    }

    private static String setCustmerBlock(String align, String data) {
        return align + "<u><font color='bg-black' size='tall'>" + data + "</font></u>\n";
    }

    static String setText(String align1, String text1) {
        return align1 + "" + text1 + "\n";
    }

    // LOGO
    static String setOutletLogo(String align1, Context context, EscPosPrinter printer) {
        return align1 + "<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, context.getResources().getDrawableForDensity(R.drawable.logo, DisplayMetrics.DENSITY_MEDIUM)) + "</img>\n";
    }

    /**
     * Customize receipt format [END]
     */


    /**
     * Format receipt custom by https://github.com/denitiawan
     *
     * @param context from Activity
     * @param printer from AsyncEscPosPrinter
     * @return
     */
    private static String formatReceiptCustom(Context context, EscPosPrinter printer, Transaction transaction) {

        return
                setEnter(center) +
                        setOutletLogo(center, context, printer) +

                        setTitle(center, "Best Brand Shop") +

                        setText(center, "Scienta Bussines Park") +
                        setText(center, "Tangerang Selatan") +
                        setText(center, "Banten") +
                        setText(center, "Indonesia") +
                        setText(center, "+6221-580-6052") +
                        setBorder() +

                        setText(left, "NPWP", left, "12.0.12.029302") +
                        setText(left, "Tanggal", left, getSimpleDateFormat().format(new Date())) +
                        setText(left, "Cetak", left, getSimpleDateFormat().format(new Date())) +
                        setText(left, "Nomor Invoice", left, "INV-000001") +
                        setText(left, "Kasir", left, "Deni Setiawan") +
                        setBorder() +

                        setItemName(left, "Bread Choco Vanilla", right, "Rp. 95.000") +
                        setItemValue(left, "4", "Rp. 25.000") +
                        setItemDiskon(left, "5.000") +

                        setItemName(left, "Milk Shake Banana", right, "Rp. 40.000") +
                        setItemValue(left, "4", "Rp. 10.000") +

                        setBorderThin() +
                        setTotal(right, "Total", right, "Rp. 135.000") +
                        setTotal(right, "Pajak", right, "Rp. 13.500") +
                        setTotal(right, "Net", right, "Rp. 150.000") +

                        setBorderThin() +
                        setTotal(right, "Tunai", right, "Rp. 200.000") +
                        setTotal(right, "Donasi", right, "Rp. 10.000") +
                        setTotal(right, "Kembali", right, "Rp. 40.000") +


                        setBorder() +
                        setCustmerBlock(left, "Customer") +
                        setEnter(center) +
                        setText(left, "Pembeli  Setia") +
                        setText(left, "Point", left, "10 Point") +

                        setBorder() +
                        setText(center, "Terimakasih") +
                        setText(center, "Telah berbelanja di tempat kami") +
                        setEnter(left) +


                        setText(center, "Scan Me") +
                        setQRCode(center, "https://github.com/denitiawan") +
                        setEnter(left) +

                        setText(center, "https://github.com/denitiawan") +
                        setEnter(center) +

                        setBarcode(center, "831254784551") +
                        setEnter(left) +

                        setText(center, "[Custom Format]") +
                        setEnter(center) +
                        setEnter(center);


    }

    public static SimpleDateFormat getSimpleDateFormat() {
        return new SimpleDateFormat("dd-MM-yyyy");
    }

    /**
     * Format receipt original form ESCPOS librarry
     *
     * @param context from Activity
     * @param printer from AsyncEscPosPrinter
     * @return
     */
    private static String formatReceiptOriginalFromLibrarry(Context context, EscPosPrinter printer) {

        return
                "[L]\n" +
                        "[C]<img>" + PrinterTextParserImg.bitmapToHexadecimalString(printer, context.getResources().getDrawableForDensity(R.drawable.logo, DisplayMetrics.DENSITY_MEDIUM)) + "</img>\n" +
                        "[L]\n" +
                        "[C]<u><font size='big'>ORDER N°045</font></u>\n" +
                        "[L]\n" +
                        "[C]<u type='double'>" + getSimpleDateFormat().format(new Date()) + "</u>\n" +
                        "[C]\n" +
                        "[C]================================\n" +
                        "[L]\n" +
                        "[L]<b>BEAUTIFUL SHIRT</b>[R]9.99€\n" +
                        "[L]  + Size : S\n" +
                        "[L]\n" +
                        "[L]<b>AWESOME HAT</b>[R]24.99€\n" +
                        "[L]  + Size : 57/58\n" +
                        "[L]\n" +
                        "[C]--------------------------------\n" +
                        "[R]TOTAL PRICE :[R]34.98€\n" +
                        "[R]TAX :[R]4.23€\n" +
                        "[L]\n" +
                        "[C]================================\n" +
                        "[L]\n" +
                        "[L]<u><font color='bg-black' size='tall'>Customer :</font></u>\n" +
                        "[L]Raymond DUPONT\n" +
                        "[L]5 rue des girafes\n" +
                        "[L]31547 PERPETES\n" +
                        "[L]Tel : +33801201456\n" +

                        "[L]\n" +
                        "[C]<qrcode size='20'>https://github.com/denitiawan</qrcode>\n" +

                        "\n" +
                        "[C]<barcode type='ean13' height='10'>831254784551</barcode>\n";

    }


}
