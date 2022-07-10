package util;

public class Interval {

    private float s;
    private float e;

    public Interval(float s, float e) {
        this.s = s;
        this.e = e;
        sort();
    }

    public void sort() {
        if(s < e) return;
        float z = s;
        s = e;
        e = z;
    }

    public boolean contains(float i) {
        return (s <= i && i <= e);
    }

    public boolean contains(Interval i) {
        boolean result = contains(i.getS());
        if(!result) result = contains(i.getE());
        return result;
    }

    public float getS() {
        return s;
    }

    public void setS(float s) {
        this.s = s;
        sort();
    }

    public float getE() {
        return e;
    }

    public void setE(float e) {
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
