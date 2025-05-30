package org.eclipse.collections.impl.jmh;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.eclipse.collections.api.map.primitive.ObjectDoubleMap;
import org.eclipse.collections.impl.jmh.domain.Account;
import org.eclipse.collections.impl.jmh.domain.Position;
import org.eclipse.collections.impl.jmh.domain.Positions;
import org.eclipse.collections.impl.jmh.domain.Product;
import org.eclipse.collections.impl.jmh.runner.AbstractJMHTestRunner;
import org.eclipse.collections.impl.parallel.ParallelIterate;
import org.junit.Assert;
import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

@State(Scope.Thread)
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(2)
@Warmup(iterations = 10, time = 2)
@Measurement(iterations = 10, time = 2)
public class SumByDoubleTest_Purified {

    private final Positions positions = new Positions().shuffle();

    @Benchmark
    public Map<Product, Double> sumByProduct_serial_lazy_jdk() {
        return this.positions.getJdkPositions().stream().collect(Collectors.groupingBy(Position::getProduct, Collectors.summingDouble(Position::getMarketValue)));
    }

    @Benchmark
    public Map<Product, Double> sumByProduct_serial_lazy_streams_ec() {
        return this.positions.getEcPositions().stream().collect(Collectors.groupingBy(Position::getProduct, Collectors.summingDouble(Position::getMarketValue)));
    }

    @Benchmark
    public Map<Account, Double> sumByAccount_serial_lazy_jdk() {
        return this.positions.getJdkPositions().stream().collect(Collectors.groupingBy(Position::getAccount, Collectors.summingDouble(Position::getMarketValue)));
    }

    @Benchmark
    public Map<Account, Double> sumByAccount_serial_lazy_streams_ec() {
        return this.positions.getEcPositions().stream().collect(Collectors.groupingBy(Position::getAccount, Collectors.summingDouble(Position::getMarketValue)));
    }

    @Benchmark
    public Map<String, Double> sumByCategory_serial_lazy_jdk() {
        return this.positions.getJdkPositions().stream().collect(Collectors.groupingBy(Position::getCategory, Collectors.summingDouble(Position::getMarketValue)));
    }

    @Benchmark
    public Map<String, Double> sumByCategory_serial_lazy_streams_ec() {
        return this.positions.getEcPositions().stream().collect(Collectors.groupingBy(Position::getCategory, Collectors.summingDouble(Position::getMarketValue)));
    }

    @Benchmark
    public Map<Product, Double> sumByProduct_parallel_lazy_jdk() {
        return this.positions.getJdkPositions().parallelStream().collect(Collectors.groupingBy(Position::getProduct, Collectors.summingDouble(Position::getMarketValue)));
    }

    @Benchmark
    public Map<Product, Double> sumByProduct_parallel_lazy_streams_ec() {
        return this.positions.getEcPositions().parallelStream().collect(Collectors.groupingBy(Position::getProduct, Collectors.summingDouble(Position::getMarketValue)));
    }

    @Benchmark
    public Map<Account, Double> sumByAccount_parallel_lazy_jdk() {
        return this.positions.getJdkPositions().parallelStream().collect(Collectors.groupingBy(Position::getAccount, Collectors.summingDouble(Position::getMarketValue)));
    }

    @Benchmark
    public Map<Account, Double> sumByAccount_parallel_lazy_streams_ec() {
        return this.positions.getEcPositions().parallelStream().collect(Collectors.groupingBy(Position::getAccount, Collectors.summingDouble(Position::getMarketValue)));
    }

    @Benchmark
    public Map<String, Double> sumByCategory_parallel_lazy_jdk() {
        return this.positions.getJdkPositions().parallelStream().collect(Collectors.groupingBy(Position::getCategory, Collectors.summingDouble(Position::getMarketValue)));
    }

    @Benchmark
    public Map<String, Double> sumByCategory_parallel_lazy_streams_ec() {
        return this.positions.getEcPositions().parallelStream().collect(Collectors.groupingBy(Position::getCategory, Collectors.summingDouble(Position::getMarketValue)));
    }

    @Benchmark
    public ObjectDoubleMap<Product> sumByProduct_serial_eager_ec() {
        return this.positions.getEcPositions().sumByDouble(Position::getProduct, Position::getMarketValue);
    }

    @Benchmark
    public ObjectDoubleMap<Product> sumByProduct_parallel_eager_ec() {
        return ParallelIterate.sumByDouble(this.positions.getEcPositions(), Position::getProduct, Position::getMarketValue);
    }

    @Benchmark
    public ObjectDoubleMap<Account> sumByAccount_serial_eager_ec() {
        return this.positions.getEcPositions().sumByDouble(Position::getAccount, Position::getMarketValue);
    }

    @Benchmark
    public ObjectDoubleMap<Account> sumByAccount_parallel_eager_ec() {
        return ParallelIterate.sumByDouble(this.positions.getEcPositions(), Position::getAccount, Position::getMarketValue);
    }

    @Benchmark
    public ObjectDoubleMap<String> sumByCategory_serial_eager_ec() {
        return this.positions.getEcPositions().sumByDouble(Position::getCategory, Position::getMarketValue);
    }

    @Benchmark
    public ObjectDoubleMap<String> sumByCategory_parallel_eager_ec() {
        return ParallelIterate.sumByDouble(this.positions.getEcPositions(), Position::getCategory, Position::getMarketValue);
    }

    @Test
    public void sumByProduct_ec_1() {
        Assert.assertArrayEquals(this.sumByProduct_parallel_eager_ec().values().toSortedArray(), this.sumByProduct_serial_eager_ec().values().toSortedArray(), 0.001);
    }

    @Test
    public void sumByProduct_ec_2() {
        Assert.assertEquals(this.sumByProduct_parallel_eager_ec(), this.sumByProduct_serial_eager_ec());
    }

    @Test
    public void sumByAccount_ec_1() {
        Assert.assertArrayEquals(this.sumByAccount_parallel_eager_ec().values().toSortedArray(), this.sumByAccount_serial_eager_ec().values().toSortedArray(), 0.001);
    }

    @Test
    public void sumByAccount_ec_2() {
        Assert.assertEquals(this.sumByAccount_parallel_eager_ec(), this.sumByAccount_serial_eager_ec());
    }

    @Test
    public void sumByCategory_ec_1() {
        Assert.assertArrayEquals(this.sumByCategory_parallel_eager_ec().values().toSortedArray(), this.sumByCategory_serial_eager_ec().values().toSortedArray(), 0.001);
    }

    @Test
    public void sumByCategory_ec_2() {
        Assert.assertEquals(this.sumByCategory_parallel_eager_ec(), this.sumByCategory_serial_eager_ec());
    }
}
