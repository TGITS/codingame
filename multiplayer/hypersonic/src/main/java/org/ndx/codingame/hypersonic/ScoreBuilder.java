package org.ndx.codingame.hypersonic;

import java.util.ArrayList;
import java.util.List;

import org.ndx.codingame.hypersonic.content.Bomb;
import org.ndx.codingame.hypersonic.content.Box;
import org.ndx.codingame.hypersonic.content.ContentVisitor;
import org.ndx.codingame.hypersonic.content.Fire;
import org.ndx.codingame.hypersonic.content.FireThenItem;
import org.ndx.codingame.hypersonic.content.Item;
import org.ndx.codingame.hypersonic.content.Nothing;
import org.ndx.codingame.hypersonic.content.Wall;
import org.ndx.codingame.lib2d.discrete.Direction;
import org.ndx.codingame.lib2d.discrete.DiscretePoint;
import org.ndx.codingame.lib2d.discrete.Playground;
import org.ndx.codingame.lib2d.discrete.ScoredDirection;

public class ScoreBuilder {
	private class ScoreBuilderVisitor implements ContentVisitor<ScoredDirection<Score>>	 {
		private ScoredDirection<Score> buildScore(int value, boolean stopHere) {
			ScoredDirection<Score> returned;
			if(point instanceof ScoredDirection) {
				returned = (ScoredDirection<Score>) point;
			} else {
				returned = new ScoredDirection<>(point.x, point.y, ""); 
			}
			returned.setScore(new Score(value, iteration));
			if(!stopHere) {
				if(next!=null) {
					// Now add children scores
					for (Direction direction : directions(point)) {
						ScoredDirection<Score> move = direction.move(point);
						if(next.source.allow(move)) {
							returned.getScore().addChild(next.compute(move));
						}
					}
				}
			}
			returned.getScore().aggregate();
			return returned;
		}

		@Override
		public ScoredDirection<Score> visitNothing(Nothing nothing) {
			return buildScore(opportunities.get(point), false);
		}

		@Override
		public ScoredDirection<Score> visitBox(Box box) {
			return buildScore(EvolvableConstants.SCORE_VISIT_BOX, true);
		}

		@Override
		public ScoredDirection<Score> visitWall(Wall wall) {
			return buildScore(EvolvableConstants.SCORE_VISIT_WALL, true);
		}

		@Override
		public ScoredDirection<Score> visitGamer(Gamer gamer) {
			return buildScore(EvolvableConstants.SCORE_VISIT_GAMER, false);
		}

		@Override
		public ScoredDirection<Score> visitBomb(Bomb bomb) {
			return buildScore(EvolvableConstants.SCORE_VISIT_BOMB, !allowBomb);
		}

		@Override
		public ScoredDirection<Score> visitItem(Item item) {
			return buildScore(EvolvableConstants.SCORE_CATCHED_ITEM, false);
		}

		@Override
		public ScoredDirection<Score> visitFire(Fire fire) {
			return buildScore(EvolvableConstants.SCORE_SUICIDE, true);
		}

		@Override
		public ScoredDirection<Score> visitFireThenItem(FireThenItem fireThenItem) {
			return buildScore(EvolvableConstants.SCORE_SUICIDE, true);
		}
		
	}
	private Playground<ScoredDirection<Score>> cache;
	private Playground<List<Direction>> directions;
	private int iteration;
	private Playfield source;
	private ScoreBuilder next;

	private DiscretePoint point;
	private ScoreBuilderVisitor visitor;
	boolean allowBomb;
	private Playground<Integer> opportunities;
	public ScoreBuilder(Playfield playground, OpportunitesLoader opportunitiesLoader) {
		this(playground, opportunitiesLoader, 0);
	}

	public ScoreBuilder(Playfield playground, OpportunitesLoader opportunitiesLoader, int i) {
		visitor = new ScoreBuilderVisitor();
		cache = new Playground<>(playground.width, playground.height);
		iteration = i;
		source = playground;
		opportunities = opportunitiesLoader.findOpportunities(source);
		if(i<=EvolvableConstants.HORIZON) {
			next = new ScoreBuilder(playground.next(), opportunitiesLoader, i+1);
			directions = next.directions;
		} else {
			directions = new Playground<>(playground.width, playground.height);
		}
	}

	public ScoredDirection<Score> compute(ScoredDirection<Score> move) {
		if(source.contains(move)) {
			ScoredDirection<Score> returned = cache.get(move);
			if(returned==null) {
				point = move;
				returned = source.get(move).accept(visitor);
				cache.set(move, returned);
			}
			return returned;
		} else {
			return move.withScore(new Score(EvolvableConstants.SCORE_OUTSIDE));
		}
	}

	public ScoredDirection<Score> computeFor(DiscretePoint point) {
		this.point = point;
		this.allowBomb = true;
		ScoredDirection<Score> computed = source.get(point).accept(visitor);
		return computed.getScore().bestChild;
	}

	private List<Direction> directions(DiscretePoint point) {
		List<Direction> returned = directions.get(point);
		if(returned==null) {
			returned = new ArrayList<>();
			if(point.x<directions.width/2) {
				returned.add(Direction.RIGHT);
				returned.add(Direction.LEFT);
			} else {
				returned.add(Direction.LEFT);
				returned.add(Direction.RIGHT);
			}
			if(point.y<directions.height/2) {
				returned.add(Direction.DOWN);
				returned.add(Direction.UP);
			} else {
				returned.add(Direction.UP);
				returned.add(Direction.DOWN);
			}
			returned.add(Direction.STAY);
			directions.set(point, returned);
		}
		return returned;
	}
}