package com.deni.escpos;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.widget.Button;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.dantsu.escposprinter.connection.bluetooth.BluetoothConnection;
import com.dantsu.escposprinter.connection.bluetooth.BluetoothPrintersConnections;
import com.deni.escpos.escposutils.adapter.BluetoothAdapterEscPos;
import com.deni.escpos.escposutils.escpos.EscPosReceiptFormat;
import com.deni.escpos.escposutils.escpos.PrintingResponse;
import com.deni.escpos.escposutils.model.Transaction;

/**
 * @author Deni Setiawan, 22-05-2023
 * @github https://www.github.com/denitiawan
 */
public class PrinterActivityPresenter {
    Activity activity;
    Context context;

    public PrinterActivityPresenter(Activity activity, Context context) {
        this.activity = activity;
        this.context = context;
    }

    // BLUETOOTH PART ----------------
    public static final int PERMISSION_BLUETOOTH = 1;
    private BluetoothConnection selectedDevice;

    public BluetoothConnection getSelectedDevice() {
        return selectedDevice;
    }

    public void setSelectedDevice(BluetoothConnection selectedDevice) {
        this.selectedDevice = selectedDevice;
    }

    Transaction dummyFunctionGetTransaction(String invoiceNumber) {
        return new Transaction();
    }

    ;


    /**
     * print with Bluetooth Adapter
     */
    public void printBluetooth() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.BLUETOOTH}, PrinterActivityPresenter.PERMISSION_BLUETOOTH);
        } else {

            // do get transaction data, from sqlite or sharedpreferences
            Transaction transactionData = dummyFunctionGetTransaction("dummyInv001");


            // call print (bluetooth)
            new BluetoothAdapterEscPos(context, PrintingResponse.invoice(activity))
                    .execute(EscPosReceiptFormat.doPrintWithEscPos(
                            context,
                            getSelectedDevice(),
                            transactionData));
        }
    }

    /**
     * print with tcp Adapter
     */
    public void printTCP() {

    }

    /**
     * print with usb Adapter
     */
    public void printUSB() {

    }

    /**
     * @param button
     */
    public void browseBluetoothDevice(Button button) {
        final BluetoothConnection[] bluetoothDevicesList = (new BluetoothPrintersConnections()).getList();

        if (bluetoothDevicesList != null) {
            final String[] items = new String[bluetoothDevicesList.length + 1];
            items[0] = "Default printer";
            int i = 0;
            for (BluetoothConnection device : bluetoothDevicesList) {
                items[++i] = device.getDevice().getName();
            }

            AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
            alertDialog.setTitle("Bluetooth printer selection");
            alertDialog.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    int index = i - 1;
                    if (index == -1) {
                        setSelectedDevice(null);
                    } else {
                        setSelectedDevice(bluetoothDevicesList[index]);
                    }

                    button.setText(items[i]);
                }
            });

            AlertDialog alert = alertDialog.create();
            alert.setCanceledOnTouchOutside(false);
            alert.show();

        }
    }
    // BLUETOOTH PART ----------------

}
