package org.ndx.codingame.hypersonic.playground;

import org.ndx.codingame.hypersonic.entities.Bomb;
import org.ndx.codingame.hypersonic.entities.Content;
import org.ndx.codingame.hypersonic.entities.ContentAdapter;
import org.ndx.codingame.hypersonic.entities.ContentVisitor;
import org.ndx.codingame.lib2d.discrete.PlaygroundAdapter;

public class BombDelayFinder extends PlaygroundAdapter<Integer, Content> {
	private class BombDelayContentFinder extends ContentAdapter<Integer> {

		public BombDelayContentFinder() {
			super(0);
		}
		
		@Override
		public Integer visitBomb(final Bomb bomb) {
			if(bomb.owner==ownerId) {
				return bomb.delay;
			} else {
				return super.visitBomb(bomb);
			}
		}
	}
	final int ownerId; 
	private final ContentVisitor<Integer> contentVisitor;
	public BombDelayFinder(final int ownerId) {
		super(0);
		this.ownerId = ownerId;
		 contentVisitor = new BombDelayContentFinder();
	}
	@Override
	public void visit(final int x, final int y, final Content content) {
		final int cellValue = content.accept(contentVisitor);
		if(cellValue>0) {
			if(returned==0) {
				returned = cellValue;
			} else {
				returned = Math.min(cellValue, returned);
			}
		}
	}
}