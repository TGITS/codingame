package org.ndx.codingame.lib2d;
import static org.assertj.core.api.Assertions.assertThat;
import static org.ndx.codingame.lib2d.Geometry.at;
import static org.ndx.codingame.lib2d.Geometry.from;

import org.assertj.core.data.Offset;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.Required;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.ndx.codingame.lib2d.continuous.ContinuousPoint;
import org.ndx.codingame.lib2d.continuous.shapes.bezier.PolynomialBezierCurve;

public class BezierPerformanceTest {
	@Rule public ContiPerfRule performance = new ContiPerfRule();
	@BeforeClass public static void hook_for_debugger() {
		System.err.println("Do not forget to break here for jvisualvm analysis");
	}

	@PerfTest(invocations = 100, threads = 1) @Required(percentile95=50)
	@Test public void can_handle_a_basic_bezier_curve() {
		final PolynomialBezierCurve curve = from(at(0, 0)).to(at(1.0, 0)).control(at(0.0, 1)).control(at(1.0, 1)).build(100);
		assertThat(curve.from).isEqualTo(at(0, 0));
		assertThat(curve.to).isEqualTo(at(1, 0));
		final ContinuousPoint curveCenter = curve.pointAtDistance(curve.length()/2);
		assertThat(curveCenter.x).isEqualTo(0.5, Offset.offset(Algebra.ZERO));
		assertThat(curveCenter.y).isGreaterThan(0.5);
	}
}
