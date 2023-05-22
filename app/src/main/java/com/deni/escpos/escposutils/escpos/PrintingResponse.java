package com.deni.escpos.escposutils.escpos;

import android.content.Context;
/**
 * @author Deni Setiawan, 22-05-2023
 * @github https://www.github.com/denitiawan
 */

/**
 * this class  for initialize message for printing success
 */
public class PrintingResponse {
    String title;
    String message;

    public PrintingResponse() {

    }

    public PrintingResponse(String title, String message) {
        this.title = title;
        this.message = message;
    }

    public static PrintingResponse invoice(final Context context) {
        return new PrintingResponse("EscPos", "Print Success");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
