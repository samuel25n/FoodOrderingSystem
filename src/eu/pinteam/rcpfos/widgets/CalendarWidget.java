package eu.pinteam.rcpfos.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarWidget extends Composite {

    private Canvas canvas;
    private Calendar calendar;

    public CalendarWidget(Composite parent, int style) {
        super(parent, style);
        calendar = Calendar.getInstance();
        createContents();
    }

    private void createContents() {
        canvas = new Canvas(this, SWT.NO_BACKGROUND);
        canvas.setBounds(new Rectangle(0, 0, 300, 200));
        canvas.addPaintListener(e -> {
            GC gc = e.gc;
            gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
            gc.fillRectangle(canvas.getBounds());
            gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
            gc.drawRectangle(canvas.getBounds());

            SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM yyyy");
            String monthName = monthFormat.format(calendar.getTime());
            gc.drawText(monthName, 10, 10);

            String[] days = {"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};
            for (int i=0; i<7; i++) {
                gc.drawText(days[i], 10+i*40, 30);
            }

            Calendar today = Calendar.getInstance();
            today.setTime(new Date());
            
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            int firstDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;

            int monthDays = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

            for (int i=1; i<=monthDays; i++) {
                int line = (firstDay+i-1)/7;
                int column = (firstDay+i-1)%7;
                int x = 10+column*40;
                int y = 50+line*30;
                
                if (i == today.get(Calendar.DAY_OF_MONTH)) {
                    gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_RED));
                    gc.fillRectangle(x, y, 40, 30);
                    gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
                } else {
                    gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
                    gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_BLACK));
                }
                
                gc.drawText(Integer.toString(i), x + 15, y + 5);
            }
        });
    }

}