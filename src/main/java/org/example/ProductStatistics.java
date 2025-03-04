import java.math.BigDecimal;

class ProductStatistics {
    private final BigDecimal sum;
    private final BigDecimal average;
    private final BigDecimal max;
    private final BigDecimal min;
    private final long count;

    public ProductStatistics(BigDecimal sum, BigDecimal average, BigDecimal max, BigDecimal min, long count) {
        this.sum = sum;
        this.average = average;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    public BigDecimal getSum() {
        return sum;
    }

    public BigDecimal getAverage() {
        return average;
    }

    public BigDecimal getMax() {
        return max;
    }

    public BigDecimal getMin() {
        return min;
    }

    public long getCount() {
        return count;
    }

    @Override
    public String toString() {
        return "ProductStatistics{" +
                "sum=" + sum +
                ", average=" + average +
                ", max=" + max +
                ", min=" + min +
                ", count=" + count +
                '}';
    }
}
//