package util;

import java.util.Comparator;

public class FloatComparator implements Comparator<Float> {
    @Override
    public int compare(Float o1, Float o2) {
        System.out.println(o1.compareTo(o2));
        return o1.compareTo(o2);
    }
}
