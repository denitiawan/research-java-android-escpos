package com.deni.escpos.escposutils.adapter;

import android.content.Context;

import com.dantsu.escposprinter.connection.DeviceConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.dantsu.escposprinter.exceptions.EscPosConnectionException;
import com.deni.escpos.escposutils.async.AsyncPrintEscPos;
import com.deni.escpos.escposutils.escpos.EscPosPrinter;
import com.deni.escpos.escposutils.escpos.PrintingResponse;
/**
 * @author Deni Setiawan, 22-05-2023
 * @github https://www.github.com/denitiawan
 */
public class BluetoothAdapterEscPos extends AsyncPrintEscPos {

    public BluetoothAdapterEscPos(Context context, PrintingResponse message) {
        super(context, message);
    }

    protected Integer doInBackground(EscPosPrinter... printersData) {
        if (printersData.length == 0) {
            return AsyncPrintEscPos.FINISH_NO_PRINTER;
        }

        EscPosPrinter printerData = printersData[0];
        DeviceConnection deviceConnection = printerData.getPrinterConnection();

        this.publishProgress(AsyncPrintEscPos.PROGRESS_CONNECTING);

        if (deviceConnection == null) {
            printersData[0] = new EscPosPrinter(
                    BluetoothPrintersConnections.selectFirstPaired(),
                    printerData.getPrinterDpi(),
                    printerData.getPrinterWidthMM(),
                    printerData.getPrinterNbrCharactersPerLine(),
                    printerData.getPrintWithOpenCashDrawer()
            );
            printersData[0].setTextToPrint(printerData.getTextToPrint());
        } else {
            try {
                deviceConnection.connect();
            } catch (EscPosConnectionException e) {
                e.printStackTrace();
            }
        }

        return super.doInBackground(printersData);
    }
}
