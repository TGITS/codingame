package org.ndx.codingame.ghostinthecell.playground;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.ndx.codingame.gaming.ToUnitTest;
import org.ndx.codingame.ghostinthecell.actions.Action;
import org.ndx.codingame.ghostinthecell.entities.Bomb;
import org.ndx.codingame.ghostinthecell.entities.Bombs;
import org.ndx.codingame.ghostinthecell.entities.Factory;
import org.ndx.codingame.ghostinthecell.entities.Transport;
import org.ndx.codingame.ghostinthecell.entities.Troop;
import org.ndx.codingame.libgraph.DirectedGraph;
import org.ndx.codingame.libgraph.Edge;
import org.ndx.codingame.libgraph.Graph;
import org.ndx.codingame.libgraph.GraphVisitor;
import org.ndx.codingame.libgraph.Navigator;
import org.ndx.codingame.libgraph.Vertex;

public class Playfield {
	private class ToDebugStringVisitor implements GraphVisitor<String> {
		private final StringBuilder returned = new StringBuilder();

		@Override
		public boolean startVisit(final Graph directedGraph) {
			returned.append(ToUnitTest.CONTENT_PREFIX+"Playfield p = new PlayfieldBuilder()")
				.append(".at(").append(turn).append(")")
				.append("\n");
			return true;
		}

		@Override
		public String endVisit(final Graph directedGraph) {
			returned.append(";\n");
			returned.append(ToUnitTest.CONTENT_PREFIX).append("assertThat(p.compute()).isNotNull();\n");
			return returned.toString();
		}

		@Override
		public void visit(final Vertex vertex) {
			final Factory f = vertex.getProperty(Factory.PROPERTY);
			returned.append(ToUnitTest.CONTENT_PREFIX).append(".i(")
				.append(vertex.id).append(", ")
				.append(f.owner).append(", ")
				.append(f.getCount()).append(", ")
				.append(f.production).append(")\n");
			returned.append(ToUnitTest.CONTENT_PREFIX).append(".f(").append(vertex.id).append(")");

			for(final Edge edge : vertex.getEdges(Navigator.DESTINATION)) {
				final Transport t = edge.getProperty(Transport.PROPERTY);
				returned
					.append(".t(").append(edge.destination.id).append(")")
					.append(".d(").append(t.distance).append(")");
			}
			returned.append("\n");
		}

		@Override
		public void visit(final Edge value) {
			for(final Troop t : value.getProperty(Transport.PROPERTY).troops) {
				returned.append(ToUnitTest.CONTENT_PREFIX).append(".t(")
					.append(value.source.id).append(",")
					.append(value.destination.id).append(",")
					.append(t.owner).append(",")
					.append(t.getCount()).append(",")
					.append(t.distance)
					.append(")\n");
				
			}
		}
	}

	public final Graph graph = new DirectedGraph();
	
	private final Bombs bombs;

	private int turn;
	
	public Playfield() {
		this(new Bombs(2));
	}
	
	public Playfield(final Bombs bombs) {
		this.bombs = bombs;
	}
	
	public void connect(final int factory1, final int factory2, final int distance) {
        graph.getOrCreateEdgeBetween(factory1, factory2)
        	.setProperty(Transport.PROPERTY, new Transport(distance));
        graph.getOrCreateEdgeBetween(factory2, factory1)
    	.setProperty(Transport.PROPERTY, new Transport(distance));
	}

	public void setFactoryInfos(final int factoryId, final int owner, final int cyborgs, final int production) {
    	graph.getOrCreateVertex(factoryId)
    		.setProperty(Factory.PROPERTY, new Factory(owner, cyborgs, production));
	}
	
	public void cleanup() {
		for(final Vertex v : graph.vertices()) {
			v.getProperty(Factory.PROPERTY).cleanup();
			for(final Edge edge : v.getEdges(Navigator.DESTINATION)) {
				edge.getProperty(Transport.PROPERTY).cleanup();
			}
		}
	}

	public void setTroop(final int from, final int to, final int owner, final int count, final int distance) {
    	final Edge link = graph.getOrCreateEdgeBetween(from, to);
    	final Troop t = new Troop(owner, count, distance);
    	Transport.of(link).add(t);
	}

	public String toDebugString() {
		final StringBuilder returned = new StringBuilder();
		
		returned.append(ToUnitTest.METHOD_PREFIX+"// @PerfTest(invocations = INVOCATION_COUNT, threads = THREAD_COUNT) @Required(percentile99=PERCENTILE)\n");
		returned.append(ToUnitTest.METHOD_PREFIX+"@Test public void can_find_move_")
			.append(System.currentTimeMillis()).append("() {\n");
		returned.append(graph.accept(new ToDebugStringVisitor()));
		returned.append(ToUnitTest.METHOD_PREFIX+"}\n\n");
		return returned.toString();
	}
	
	/** Just push troops to nearest non-owned factory */
	public String compute() {
		final Collection<Action> toPerform = new ArrayList<>();
		// TODO sort by difference of mean distance to my/enemy factories
		toPerform.addAll(graph.vertices().stream()
			.filter((v)->Factory.of(v).owner>0)
			.sorted(Factory.BY_DECREASING_DISTANCE_DIFFERENCE)
			.flatMap(this::computeMovesOf)
			.collect(Collectors.toList()));
		if(toPerform.isEmpty()) {
			return "WAIT";
		} else {
			return toPerform.stream()
					.map((action) -> action.toCommandString())
					.collect(Collectors.joining(";"));
		}
	}
	
	public Stream<Action> computeMovesOf(final Vertex vertex) {
		final Factory my = Factory.of(vertex);
		return my.computeMoves(bombs, vertex);
	}

	public void setTurn(final int i) {
		turn = i;
	}

	public void setMyBomb(final int from, final int to, final int distance) {
    	final Edge link = graph.getOrCreateEdgeBetween(from, to);
    	final Bomb b = new Bomb(distance);
    	Transport.of(link).setBomb(b);
	}

	public void setEnemyBomb(final int timer) {
		// TODO Auto-generated method stub
		
	}
}
