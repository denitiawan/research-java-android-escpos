package com.deni.escpos;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * @author Deni Setiawan, 22-05-2023
 * @github https://www.github.com/denitiawan
 */
public class PrinterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);

        // Base Activity initialize
        PrinterActivityPresenter printerActivityPresenter = new PrinterActivityPresenter(this, this);

        // Button search printer
        Button btn_search_printer;
        btn_search_printer = findViewById(R.id.btn_search_printer);
        btn_search_printer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printerActivityPresenter.browseBluetoothDevice(btn_search_printer);
            }
        });


        // Button test printer
        Button btn_test_printer;
        btn_test_printer = findViewById(R.id.btn_test_printer);
        btn_test_printer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                printerActivityPresenter.printBluetooth();
            }
        });


    }


}