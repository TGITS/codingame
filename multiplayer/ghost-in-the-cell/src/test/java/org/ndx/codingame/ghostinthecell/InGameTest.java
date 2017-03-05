package org.ndx.codingame.ghostinthecell;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.ndx.codingame.ghostinthecell.playground.Playfield;


public class InGameTest {
	// @PerfTest(invocations = INVOCATION_COUNT, threads = THREAD_COUNT) @Required(percentile99=PERCENTILE)
	@Test public void can_find_move_1488726591001() {
		final Playfield p = new PlayfieldBuilder().at(1)
		.i(0, 0, 0, 0)
		.f(0).t(1).d(2).t(2).d(2).t(3).d(2).t(4).d(2).t(5).d(5).t(6).d(5).t(7).d(3).t(8).d(3).t(9).d(6).t(10).d(6).t(11).d(2).t(12).d(2).t(13).d(7).t(14).d(7)
		.i(1, 1, 1, 1)
		.f(1).t(0).d(2).t(2).d(6).t(3).d(1).t(4).d(5).t(5).d(1).t(6).d(8).t(7).d(3).t(8).d(6).t(9).d(4).t(10).d(9).t(11).d(4).t(12).d(3).t(13).d(4).t(14).d(11)
		.i(2, -1, 1, 1)
		.f(2).t(0).d(2).t(1).d(6).t(3).d(5).t(4).d(1).t(5).d(8).t(6).d(1).t(7).d(6).t(8).d(3).t(9).d(9).t(10).d(4).t(11).d(3).t(12).d(4).t(13).d(11).t(14).d(4)
		.i(3, 0, 15, 3)
		.f(3).t(0).d(2).t(1).d(1).t(2).d(5).t(4).d(5).t(5).d(4).t(6).d(7).t(7).d(5).t(8).d(4).t(9).d(6).t(10).d(7).t(11).d(5).t(12).d(1).t(13).d(6).t(14).d(9)
		.i(4, 0, 15, 3)
		.f(4).t(0).d(2).t(1).d(5).t(2).d(1).t(3).d(5).t(5).d(7).t(6).d(4).t(7).d(4).t(8).d(5).t(9).d(7).t(10).d(6).t(11).d(1).t(12).d(5).t(13).d(9).t(14).d(6)
		.i(5, 0, 0, 0)
		.f(5).t(0).d(5).t(1).d(1).t(2).d(8).t(3).d(4).t(4).d(7).t(6).d(11).t(7).d(2).t(8).d(9).t(9).d(2).t(10).d(11).t(11).d(5).t(12).d(6).t(13).d(1).t(14).d(13)
		.i(6, 0, 0, 0)
		.f(6).t(0).d(5).t(1).d(8).t(2).d(1).t(3).d(7).t(4).d(4).t(5).d(11).t(7).d(9).t(8).d(2).t(9).d(11).t(10).d(2).t(11).d(6).t(12).d(5).t(13).d(13).t(14).d(1)
		.i(7, 0, 8, 3)
		.f(7).t(0).d(3).t(1).d(3).t(2).d(6).t(3).d(5).t(4).d(4).t(5).d(2).t(6).d(9).t(8).d(8).t(9).d(1).t(10).d(11).t(11).d(2).t(12).d(6).t(13).d(4).t(14).d(11)
		.i(8, 0, 8, 3)
		.f(8).t(0).d(3).t(1).d(6).t(2).d(3).t(3).d(4).t(4).d(5).t(5).d(9).t(6).d(2).t(7).d(8).t(9).d(11).t(10).d(1).t(11).d(6).t(12).d(2).t(13).d(11).t(14).d(4)
		.i(9, 0, 1, 1)
		.f(9).t(0).d(6).t(1).d(4).t(2).d(9).t(3).d(6).t(4).d(7).t(5).d(2).t(6).d(11).t(7).d(1).t(8).d(11).t(10).d(13).t(11).d(4).t(12).d(8).t(13).d(2).t(14).d(14)
		.i(10, 0, 1, 1)
		.f(10).t(0).d(6).t(1).d(9).t(2).d(4).t(3).d(7).t(4).d(6).t(5).d(11).t(6).d(2).t(7).d(11).t(8).d(1).t(9).d(13).t(11).d(8).t(12).d(4).t(13).d(14).t(14).d(2)
		.i(11, 0, 2, 2)
		.f(11).t(0).d(2).t(1).d(4).t(2).d(3).t(3).d(5).t(4).d(1).t(5).d(5).t(6).d(6).t(7).d(2).t(8).d(6).t(9).d(4).t(10).d(8).t(12).d(5).t(13).d(7).t(14).d(8)
		.i(12, 0, 2, 2)
		.f(12).t(0).d(2).t(1).d(3).t(2).d(4).t(3).d(1).t(4).d(5).t(5).d(6).t(6).d(5).t(7).d(6).t(8).d(2).t(9).d(8).t(10).d(4).t(11).d(5).t(13).d(8).t(14).d(7)
		.i(13, 0, 6, 2)
		.f(13).t(0).d(7).t(1).d(4).t(2).d(11).t(3).d(6).t(4).d(9).t(5).d(1).t(6).d(13).t(7).d(4).t(8).d(11).t(9).d(2).t(10).d(14).t(11).d(7).t(12).d(8).t(14).d(15)
		.i(14, 0, 6, 2)
		.f(14).t(0).d(7).t(1).d(11).t(2).d(4).t(3).d(9).t(4).d(6).t(5).d(13).t(6).d(1).t(7).d(11).t(8).d(4).t(9).d(14).t(10).d(2).t(11).d(8).t(12).d(7).t(13).d(15)
		.t(1,9,1,2,4)
		.t(1,10,1,2,9)
		.t(1,12,1,3,3)
		.t(1,13,1,7,4)
		.t(1,14,1,7,11)
		.t(2,4,-1,21,1)
;
		assertThat(p.compute()).isNotNull();
	}
}
