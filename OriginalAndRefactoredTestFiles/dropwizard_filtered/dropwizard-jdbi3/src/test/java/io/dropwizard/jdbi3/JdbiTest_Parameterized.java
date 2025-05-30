package io.dropwizard.jdbi3;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jdbi3.strategies.TimedAnnotationNameStrategy;
import io.dropwizard.core.setup.Environment;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.logging.common.BootstrapLogging;
import org.eclipse.jetty.util.component.LifeCycle;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class JdbiTest_Parameterized {

    static {
        BootstrapLogging.bootstrap();
    }

    private Environment environment;

    private Jdbi dbi;

    private GameDao dao;

    private MetricRegistry metricRegistry;

    @BeforeEach
    void setUp() throws Exception {
        environment = new Environment("test");
        metricRegistry = environment.metrics();
        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        dataSourceFactory.setUrl("jdbc:h2:mem:jdbi3-test");
        dataSourceFactory.setUser("sa");
        dataSourceFactory.setDriverClass("org.h2.Driver");
        dataSourceFactory.asSingleConnectionPool();
        dbi = new JdbiFactory(new TimedAnnotationNameStrategy()).build(environment, dataSourceFactory, "h2");
        dbi.useTransaction(h -> {
            h.createScript(new String(getClass().getResourceAsStream("/schema.sql").readAllBytes(), StandardCharsets.UTF_8)).execute();
            h.createScript(new String(getClass().getResourceAsStream("/data.sql").readAllBytes(), StandardCharsets.UTF_8)).execute();
        });
        dao = dbi.onDemand(GameDao.class);
        for (LifeCycle lc : environment.lifecycle().getManagedObjects()) {
            lc.start();
        }
    }

    @AfterEach
    void tearDown() throws Exception {
        for (LifeCycle lc : environment.lifecycle().getManagedObjects()) {
            lc.stop();
        }
    }

    @Test
    void canAcceptOptionalParams_1() {
        assertThat(dao.findHomeTeamByGameId(Optional.of(4))).contains("Dallas Stars");
    }

    @Test
    void canAcceptEmptyOptionalParams_1() {
        assertThat(dao.findHomeTeamByGameId(Optional.empty())).isEmpty();
    }

    @Test
    void canReturnImmutableLists_1() {
        assertThat(dao.findGameIds()).containsExactly(1, 2, 3, 4, 5);
    }

    @Test
    void canReturnImmutableSets_1() {
        assertThat(dao.findAllUniqueHomeTeams()).containsOnly("NY Rangers", "Toronto Maple Leafs", "Dallas Stars");
    }

    @Test
    void canReturnOptional_1() {
        assertThat(dao.findIdByTeamsAndDate("NY Rangers", "Vancouver Canucks", LocalDate.of(2016, 5, 14))).contains(2);
    }

    @Test
    void canReturnEmptyOptional_1() {
        assertThat(dao.findIdByTeamsAndDate("Vancouver Canucks", "NY Rangers", LocalDate.of(2016, 5, 14))).isEmpty();
    }

    @Test
    void worksWithDates_1() {
        assertThat(dao.getFirstPlayedSince(LocalDate.of(2016, 3, 1))).isEqualTo(LocalDate.of(2016, 2, 15));
    }

    @Test
    void worksWithOptionalDates_1() {
        Optional<LocalDate> date = dao.getLastPlayedDateByTeams("Toronto Maple Leafs", "Anaheim Ducks");
        assertThat(date).contains(LocalDate.of(2016, 2, 11));
    }

    @Test
    void worksWithAbsentOptionalDates_1() {
        assertThat(dao.getLastPlayedDateByTeams("Vancouver Canucks", "NY Rangers")).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("Provider_canAcceptOptionalParams_2_2_2_2_2_2_2_2_2")
    void canAcceptOptionalParams_2_2_2_2_2_2_2_2_2(int param1, String param2) {
        assertThat(metricRegistry.timer(param2).getCount()).isEqualTo(param1);
    }

    static public Stream<Arguments> Provider_canAcceptOptionalParams_2_2_2_2_2_2_2_2_2() {
        return Stream.of(arguments(1, "game-dao.findHomeTeamByGameId"), arguments(1, "game-dao.findHomeTeamByGameId"), arguments(1, "game-dao.findGameIds"), arguments(1, "game-dao.findAllUniqueHomeTeams"), arguments(1, "game-dao.findIdByTeamsAndDate"), arguments(1, "game-dao.findIdByTeamsAndDate"), arguments(1, "game-dao.getFirstPlayedSince"), arguments(1, "game-dao.last-played-date"), arguments(1, "game-dao.last-played-date"));
    }
}
