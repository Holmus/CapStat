package capstat.model;

/**
 * Part of the statistics module of CapStat. This is an interface that will
 * be used to create all types of plots. getValue should return the value
 * that will be plotted on one axis, and getLabel should return an
 * appropriate String to describe the point. This may of course just be the
 * value, in String form.
 * @author hjorthjort
 */
public interface Plottable {
    double getValue();
    String getLabel();
}
