/*
 * Copyright 2012 AndroidPlot.com
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.androidplot.ui;

import android.graphics.Canvas;
import android.graphics.RectF;
import android.graphics.Region;
import com.androidplot.series.Series;
import com.androidplot.exception.PlotRenderException;
import com.androidplot.Plot;
//import com.androidplot.xy.ui.widget.renderer.XYRenderBundle;
import com.androidplot.series.XYSeries;
import com.androidplot.xy.XYRegionFormatter;

public abstract class DataRenderer<PlotType extends Plot, SeriesFormatterType extends Formatter> {
    private PlotType plot;

    public DataRenderer(PlotType plot) {
        this.plot = plot;
    }

    public PlotType getPlot() {
        return plot;
    }

    public void setPlot(PlotType plot) {
        this.plot = plot;
    }

    public SeriesAndFormatterList<XYSeries,SeriesFormatterType> getSeriesAndFormatterList() {
        return plot.getSeriesAndFormatterListForRenderer(getClass());
    }

    public SeriesFormatterType getFormatter(Series series) {
        return (SeriesFormatterType) plot.getFormatter(series, getClass());
    }

    public void render(Canvas canvas, RectF plotArea) throws PlotRenderException {
        //recalculateMinMaxVals();
        onRender(canvas, plotArea);
    }
    public abstract void onRender(Canvas canvas, RectF plotArea) throws PlotRenderException;

    /**
     * Draw the legend icon in the rect passed in.
     * @param canvas
     * @param rect
     */
    protected abstract void doDrawLegendIcon(Canvas canvas, RectF rect, SeriesFormatterType formatter);

    public void drawSeriesLegendIcon(Canvas canvas, RectF rect, SeriesFormatterType formatter) {
        //int state = canvas.save(Canvas.CLIP_SAVE_FLAG);
        try {
            canvas.save(Canvas.ALL_SAVE_FLAG);
            canvas.clipRect(rect, Region.Op.INTERSECT);
            doDrawLegendIcon(canvas, rect, formatter);
            //canvas.restoreToCount(state);
        } finally {
            canvas.restore();
        }
    }

    public void drawRegionLegendIcon(Canvas canvas, RectF rect, XYRegionFormatter formatter) {
        canvas.drawRect(rect, formatter.getPaint());
    }

}
