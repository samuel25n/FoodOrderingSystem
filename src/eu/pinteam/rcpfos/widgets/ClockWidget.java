package eu.pinteam.rcpfos.widgets;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;


public class ClockWidget extends Canvas {
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private String currentTime = "";

    public ClockWidget(Composite parent, int style) {
        super(parent, style);

        setLayout(new FillLayout());

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            if(!parent.isDisposed())
                {getDisplay().asyncExec(new Runnable() {
                    @Override
                    public void run() {
                        currentTime = TIME_FORMAT.format(new Date());
                        redraw(); 
                    }
                });
            }}
           
        }, 0, 1000); 

       
        addPaintListener(new PaintListener() {
            @Override
            public void paintControl(PaintEvent event) {
                paintClock(event.gc);
            }
        });
    }

    private void paintClock(GC gc) {
        FontData[] fontData = getFont().getFontData();
        Font font = new Font(getDisplay(), fontData[0].getName(), 20, SWT.NONE);
        gc.setFont(font);
        gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_RED));

        Point size = getSize();
        int width = size.x;
        int height = size.y;

        Point textSize = gc.textExtent(currentTime);
        int x = (width - textSize.x) / 2;
        int y = (height - textSize.y) / 2;
        gc.drawText(currentTime, x, y);

        font.dispose();
    }
}
