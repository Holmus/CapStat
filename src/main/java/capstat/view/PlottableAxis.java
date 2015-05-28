package capstat.view;

import capstat.model.Plottable;
import javafx.scene.chart.Axis;

import java.util.List;

/**
 * @author hjorthjort
 */
public class PlottableAxis extends Axis<Plottable> {

    List<Plottable> values;

    public PlottableAxis(List<Plottable> data) {
        this.values = data;
    }

    @Override
    protected Object autoRange(final double length) {
        return null;
    }

    @Override
    protected void setRange(final Object range, final boolean animate) {

    }

    @Override
    protected Object getRange() {
        return null;
    }

    @Override
    public double getZeroPosition() {
        return 0;
    }

    @Override
    public double getDisplayPosition(final Plottable value) {
        return value.getValue();
    }

    @Override
    public Plottable getValueForDisplay(final double displayPosition) {
        double diff;
        for (Plottable value : values) {
            if (displayPosition == value.getValue())
                return value;
        }
        return null;
    }

    @Override
    public boolean isValueOnAxis(final Plottable value) {
        return values.contains(value);
    }

    @Override
    public double toNumericValue(final Plottable value) {
        return value.getValue();
    }

    @Override
    public Plottable toRealValue(final double value) {
        return this.getValueForDisplay(value);
    }

    @Override
    protected List<Plottable> calculateTickValues(final double length, final
    Object range) {
        return this.values;
    }

    @Override
    protected String getTickMarkLabel(final Plottable value) {
        return value.getLabel();
    }
}
