package org.ndx.codingame.labyrinth;

import org.junit.Ignore;
import org.junit.Test;
import org.ndx.codingame.lib2d.Geometry;
import org.ndx.codingame.lib2d.discrete.Direction;
import org.ndx.codingame.lib2d.discrete.DiscretePoint;

import static org.assertj.core.api.Assertions.assertThat;
import static org.ndx.codingame.lib2d.Geometry.at;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;

public class PlayerTest {

	public static PlayField from(String string) {
		String[] rows = string.split("\n");
		PlayField returned = new PlayField(rows[0].length(), rows.length);
		int i=0;
		for (String s : rows) {
			returned.setRow(i++, s);
		}
		return returned;
	}

	@Test
	public void can_find_control_center() {
		PlayField field = from(
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"???##########?????????????????\n"+
				"???##########?????????????????\n"+
				"???##T......C?????????????????\n"+
				"???##########?????????????????\n"+
				"???##########?????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"
				);
		Agent agent = new Agent();
		assertThat(agent.findControlCenterAround(field, at(6, 6))).isNull();
		assertThat(agent.findControlCenterAround(field, at(10, 6))).isEqualTo(at(12, 6));
	}

	@Test
	public void lookup_work_correctly_once() {
		PlayField field = from(
				"######?????????????###########\n"+
				"##.###?????????????###########\n"+
				"#...#.######.#.????.#......T##\n"+
				"###.#.######.#.######.########\n"+
				"##...........#.######.########\n"+
				"?##.#.######......###.##??????\n"+
				"?...#.###....#.##.###.##??????\n"+
				"?.####????####.##.....##??????\n"+
				"???????????????#########??????\n"+
				"???????????????####.....??????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n");
		Lookup lookup = new Lookup();
		Agent agent = new Agent();
		agent.putOn(3, 6);
		Direction direction = lookup.move(agent, field);
		assertThat(direction.name).isEqualTo("LEFT");
	}

	@Test
	public void lookup_work_correctly_twice() {
		PlayField field = from(
				"???????????????????###########\n"+
				"???????????????????###########\n"+
				"???????????????????.#......T##\n"+
				"???????????????????##.########\n"+
				"???????????????????##.########\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"
			);
		Lookup lookup = new Lookup();
		Agent agent = new Agent();
		agent.putOn(21, 2);
		Direction direction = lookup.move(agent, field);
		assertThat(direction.name).isEqualTo("DOWN");
	}

	@Test @Ignore
	public void lookup_work_correctly_thrice() {
		PlayField field = from(
				"##############################\n"+
				"#T...........................#\n"+
				"##...........................#\n"+
				"#............................#\n"+
				"????????????????????????.....#\n"+
				"????????????????????????.....#\n"+
				"????????????????????????.....#\n"+
				"????????????????????????.....#\n"+
				"????????????????????????.....?\n"+
				"????????????????????????.....?\n"+
				"????????????????????????.....?\n"+
				"????????????????????????.....?\n"+
				"????????????????????????.....?\n"+
				"????????????????????????....C?\n"+
				"????????????????????????#####?\n"
				);
		Lookup lookup = new Lookup();
		Agent agent = new Agent();
		agent.putOn(26, 12);
		Direction direction = lookup.move(agent, field);
		assertThat(direction.name).isIn("DOWN", "RIGHT");
	}

	@Test
	public void moveto_generate_correct_heatmap() {
		PlayField field = from(
				"######?????????????###########\n"+
				"##.###?????????????###########\n"+
				"#...#.######.#.????.#......T##\n"+
				"###.#.######.#.######.########\n"+
				"##...........#.######.########\n"+
				"?##.#.######......###.##??????\n"+
				"?...#.###....#.##.###.##??????\n"+
				"?.####????####.##.....##??????\n"+
				"???????????????#########??????\n"+
				"???????????????####.....??????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n");
		Agent agent = new Agent();
		agent.putOn(25, 2);
		MoveTo moveto = new MoveTo(field, at(27, 2));
		assertThat(moveto.heatmap.get(at(26, 2))).isEqualTo(1);
		assertThat(moveto.move(agent, field).name).isEqualTo("RIGHT");
	}

	@Test
	public void can_find_fallback_points() {
		PlayField field = from(
				"######?????????????###########\n"+
				"##.###?????????????###########\n"+
				"#...#.######.#.????.#......T##\n"+
				"###.#.######.#.######.########\n"+
				"##...........#.######.########\n"+
				"?##.#.######......###.##??????\n"+
				"?...#.###....#.##.###.##??????\n"+
				"?.####????####.##.....##??????\n"+
				"???????????????#########??????\n"+
				"???????????????####.....??????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n");
		Agent agent = new Agent();
		agent.putOn(3, 6);
		Collection<DiscretePoint> points = agent.findFallbackPointsIn(field, at(3, 6), Collections.emptyList(), new ArrayDeque<>(), 0);
		assertThat(points).contains(at(1, 7));
	}

	@Test
	public void moveto_valid_location() {
		PlayField field = from(
				"???????????????????###########\n"+
				"???????????????????###########\n"+
				"???.#.######.#.????.#......T##\n"+
				"???.#.######.#.######.########\n"+
				"???..........#.######.########\n"+
				"???.#.######......###.##??????\n"+
				"???.#.###....#.##.###.##??????\n"+
				"??????????####.##.....##??????\n"+
				"???????????????#########??????\n"+
				"???????????????####.....??????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n");
		Agent agent = new Agent();
		agent.putOn(5, 4);
		Collection<DiscretePoint> points = agent.findFallbackPointsIn(field, at(5, 4), Collections.emptyList(), new ArrayDeque<>(), 0);
		DiscretePoint fallback = at(5, 2);
		assertThat(points).contains(fallback);
		MoveTo strategy = new MoveTo(field, fallback, (int) fallback.distance1To(agent.position));
		assertThat(strategy.move(agent, field).name).isEqualTo("UP");
	}

	@Test
	public void moveto_extend_on_all_frontiers_in_initial_case() {
		PlayField field = from(
				"???????????????????###########\n"+
				"???????????????????###########\n"+
				"???.#.######.#.????.#......T##\n"+
				"???.#.######.#.######.########\n"+
				"???..........#.######.########\n"+
				"???.#.######......###.##??????\n"+
				"???.#.###....#.##.###.##??????\n"+
				"??????????####.##.....##??????\n"+
				"???????????????#########??????\n"+
				"???????????????####.....??????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n");
		Agent agent = new Agent();
		agent.putOn(5, 4);
		Collection<DiscretePoint> points = agent.findFallbackPointsIn(field, at(5, 4), Collections.emptyList(), new ArrayDeque<>(), 0);
		DiscretePoint fallback = at(5, 2);
		assertThat(points).contains(fallback);
		MoveTo strategy = new MoveTo(field, fallback, (int) fallback.distance1To(agent.position));
		assertThat(strategy.move(agent, field).name).isEqualTo("UP");
		PlayField updated = from(
				"######?????????????###########\n"+
				"##.###?????????????###########\n"+
				"#...#.######.#.????.#......T##\n"+
				"###.#.######.#.######.########\n"+
				"##...........#.######.########\n"+
				"###.#.######......###.##??????\n"+
				"#...#.###....#.##.###.##??????\n"+
				"#.####????####.##.....##??????\n"+
				"#.....?????????#########??????\n"+
				"???????????????####.....??????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"
				);
		assertThat(strategy.heatmap.get(at(1, 8))).isEqualTo(strategy.heatmap.MAX_DISTANCE);
		strategy.extend(field);
		assertThat(strategy.heatmap.get(at(3, 6))).isLessThan(100);
	}
	@Test
	public void moveto_extend_on_all_frontiers_in_classic_case() {
		PlayField field = from(
				"????????????????????#.....##??\n"+
				"????????????????????########??\n"+
				"????????????????????.....T##??\n"+
				"????????????????????#######.??\n"+
				"????????????????????..#####.??\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"
				);
		MoveTo strategy = new MoveTo(field, at(25, 2), (int) at(25, 2).distance1To(at(19, 2)));
		assertThat(strategy.heatmap.frontier.size()).isEqualTo(1);
		field = from(
				"???????????????????##.....##??\n"+
				"???????????????????#########??\n"+
				"???????????????????......T##??\n"+
				"???????????????????.#######.??\n"+
				"???????????????????...#####.??\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"
				);
		strategy.extend(field);
		assertThat(strategy.heatmap.frontier.size()).isGreaterThanOrEqualTo(5);
		field = from(
				"??????????????????###.....##??\n"+
				"??????????????????##########??\n"+
				"??????????????????#......T##??\n"+
				"??????????????????#.#######.??\n"+
				"??????????????????#...#####.??\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"+
				"??????????????????????????????\n"
				);
		strategy.extend(field);
		assertThat(strategy.heatmap.frontier.size()).isGreaterThanOrEqualTo(1);
	}
}