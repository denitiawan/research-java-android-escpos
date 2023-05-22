package com.deni.escpos.escposutils.adapter;

import android.content.Context;

import com.deni.escpos.escposutils.async.AsyncPrintEscPos;
import com.deni.escpos.escposutils.escpos.PrintingResponse;
/**
 * @author Deni Setiawan, 22-05-2023
 * @github https://www.github.com/denitiawan
 */
public class TcpAdapterEscPos extends AsyncPrintEscPos {
    public TcpAdapterEscPos(Context context, PrintingResponse message) {
        super(context,message);
    }
}
