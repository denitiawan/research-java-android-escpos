package com.deni.escpos.escposutils.async;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.dantsu.escposprinter.EscPosCharsetEncoding;
import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.exceptions.EscPosBarcodeException;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.dantsu.escposprinter.exceptions.EscPosEncodingException;
import com.dantsu.escposprinter.exceptions.EscPosParserException;
import com.deni.escpos.escposutils.escpos.EscPosPrinter;
import com.deni.escpos.escposutils.escpos.PrintingResponse;

import java.lang.ref.WeakReference;

/**
 * @author Deni Setiawan, 22-05-2023
 * @github https://www.github.com/denitiawan
 */


/**
 * this class is processing for printing, with Asyncronous process
 * this  class need initialize EscPosPrinter object before running the task
 */

/**
 * flow of escpos print
 * layout -> PrinterActivity -> PrinterActivityPresenter -> choose adapter (bluetooth) ->
 * BluetoothAdapter -> AsyncEscPosPrint -> EscPosReceiptFormat ->
 * PrintingResponse -> PrinterActivityPresenter -> PrinterActivity -> layout
 */
public abstract class AsyncPrintEscPos extends AsyncTask<EscPosPrinter, Integer, Integer> {
    public PrintingResponse message = new PrintingResponse();

    protected final static int FINISH_SUCCESS = 1;
    protected final static int FINISH_NO_PRINTER = 2;
    protected final static int FINISH_PRINTER_DISCONNECTED = 3;
    protected final static int FINISH_PARSER_ERROR = 4;
    protected final static int FINISH_ENCODING_ERROR = 5;
    protected final static int FINISH_BARCODE_ERROR = 6;

    protected final static int PROGRESS_CONNECTING = 1;
    protected final static int PROGRESS_CONNECTED = 2;
    protected final static int PROGRESS_PRINTING = 3;
    protected final static int PROGRESS_PRINTED = 4;

    protected ProgressDialog dialog;
    protected WeakReference<Context> weakContext;


    public AsyncPrintEscPos(Context context, PrintingResponse message) {
        this.weakContext = new WeakReference<>(context);
        this.message = message;
    }

    protected Integer doInBackground(EscPosPrinter... printersData) {
        if (printersData.length == 0) {
            return AsyncPrintEscPos.FINISH_NO_PRINTER;
        }

        this.publishProgress(AsyncPrintEscPos.PROGRESS_CONNECTING);

        EscPosPrinter printerData = printersData[0];

        try {
            DeviceConnection deviceConnection = printerData.getPrinterConnection();

            if (deviceConnection == null) {
                return AsyncPrintEscPos.FINISH_NO_PRINTER;
            }

            com.dantsu.escposprinter.EscPosPrinter printer = new com.dantsu.escposprinter.EscPosPrinter(
                    deviceConnection,
                    printerData.getPrinterDpi(),
                    printerData.getPrinterWidthMM(),
                    printerData.getPrinterNbrCharactersPerLine(),
                    new EscPosCharsetEncoding("windows-1252", 16)
            );

            this.publishProgress(AsyncPrintEscPos.PROGRESS_PRINTING);
            if (printerData.getPrintWithOpenCashDrawer().equalsIgnoreCase("Y")) {
                printer.printFormattedTextAndOpenCashBox(printerData.getTextToPrint(), 1);
            } else {
                printer.printFormattedText(printerData.getTextToPrint(), 1);
            }


            this.publishProgress(AsyncPrintEscPos.PROGRESS_PRINTED);

        } catch (EscPosConnectionException e) {
            e.printStackTrace();
            return AsyncPrintEscPos.FINISH_PRINTER_DISCONNECTED;
        } catch (EscPosParserException e) {
            e.printStackTrace();
            return AsyncPrintEscPos.FINISH_PARSER_ERROR;
        } catch (EscPosEncodingException e) {
            e.printStackTrace();
            return AsyncPrintEscPos.FINISH_ENCODING_ERROR;
        } catch (EscPosBarcodeException e) {
            e.printStackTrace();
            return AsyncPrintEscPos.FINISH_BARCODE_ERROR;
        }

        return AsyncPrintEscPos.FINISH_SUCCESS;
    }

    protected void onPreExecute() {
        if (this.dialog == null) {
            Context context = weakContext.get();

            if (context == null) {
                return;
            }

            this.dialog = new ProgressDialog(context);
            this.dialog.setTitle("Printing in progress...");
            this.dialog.setMessage("...");
            this.dialog.setProgressNumberFormat("%1d / %2d");
            this.dialog.setCancelable(false);
            this.dialog.setIndeterminate(false);
            this.dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            this.dialog.show();
        }
    }

    protected void onProgressUpdate(Integer... progress) {
        switch (progress[0]) {
            case AsyncPrintEscPos.PROGRESS_CONNECTING:
                this.dialog.setMessage("Connecting printer...");
                break;
            case AsyncPrintEscPos.PROGRESS_CONNECTED:
                this.dialog.setMessage("Printer is connected...");
                break;
            case AsyncPrintEscPos.PROGRESS_PRINTING:
                this.dialog.setMessage("Printer is printing...");
                break;
            case AsyncPrintEscPos.PROGRESS_PRINTED:
                this.dialog.setMessage("Printer has finished...");
                break;
        }
        this.dialog.setProgress(progress[0]);
        this.dialog.setMax(4);
    }

    protected void onPostExecute(Integer result) {
        this.dialog.dismiss();
        this.dialog = null;

        Context context = weakContext.get();

        if (context == null) {
            return;
        }

        switch (result) {
            case AsyncPrintEscPos.FINISH_SUCCESS:
                new AlertDialog.Builder(context)
                        .setTitle(message.getTitle())
                        .setMessage(message.getMessage())
                        .show();
                break;
            case AsyncPrintEscPos.FINISH_NO_PRINTER:
                new AlertDialog.Builder(context)
                        .setTitle("No printer")
                        .setMessage("The application can't find any printer connected.")
                        .show();
                break;
            case AsyncPrintEscPos.FINISH_PRINTER_DISCONNECTED:
                new AlertDialog.Builder(context)
                        .setTitle("Broken connection")
                        .setMessage("Unable to connect the printer.")
                        .show();
                break;
            case AsyncPrintEscPos.FINISH_PARSER_ERROR:
                new AlertDialog.Builder(context)
                        .setTitle("Invalid formatted text")
                        .setMessage("It seems to be an invalid syntax problem.")
                        .show();
                break;
            case AsyncPrintEscPos.FINISH_ENCODING_ERROR:
                new AlertDialog.Builder(context)
                        .setTitle("Bad selected encoding")
                        .setMessage("The selected encoding character returning an error.")
                        .show();
                break;
            case AsyncPrintEscPos.FINISH_BARCODE_ERROR:
                new AlertDialog.Builder(context)
                        .setTitle("Invalid barcode")
                        .setMessage("Data send to be converted to barcode or QR code seems to be invalid.")
                        .show();
                break;
        }
    }
}