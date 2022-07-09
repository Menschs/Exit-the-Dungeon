package util;

public class Interval {

    private double s;
    private double e;

    public Interval(double s, double e) {
        this.s = s;
        this.e = e;
        sort();
    }

    public void sort() {
        if(s < e) return;
        double z = s;
        s = e;
        e = z;
    }

    public boolean contains(double i) {
        return (s <= i && i <= e);
    }

    public boolean contains(Interval i) {
        boolean result = contains(i.getS());
        if(!result) result = contains(i.getE());
        return result;
    }

    public double getS() {
        return s;
    }

    public void setS(double s) {
        this.s = s;
        sort();
    }

    public double getE() {
        return e;
    }

    public void setE(double e) {
        this.e = e;
        sort();
    }

    @Override
    public String toString() {
        return "Interval{" +
                "s=" + s +
                ", e=" + e +
                '}';
    }
}
