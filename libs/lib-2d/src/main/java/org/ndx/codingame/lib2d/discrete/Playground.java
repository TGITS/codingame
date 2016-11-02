package org.ndx.codingame.lib2d.discrete;

import org.ndx.codingame.lib2d.continuous.ContinuousPoint;

public class Playground<Content> {
	private Object[][] content;
	public final int height;
	public final int width;

	public Playground(int width, int height) {
		this(width, height, null);
	}
	public Playground(int width, int height, Content defaultValue) {
		this.width = width;
		this.height = height;
		this.content = new Object[height][];
		for (int y = 0; y < height; y++) {
			this.content[y] = new Object[width];
			if(defaultValue!=null) {
				for (int x = 0; x < this.content[y].length; x++) {
					this.content[y][x] = defaultValue;
				}
			}
		}
	}

	public void clear() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				this.content[y][x]=null;
			}
		}
	}

	public void set(DiscretePoint p, Content c) {
		this.content[p.y][p.x] = c;
	}

	@SuppressWarnings("unchecked")
	public Content get(DiscretePoint p) {
		return (Content) this.content[p.y][p.x];
	}

	public Content get(int x, int y) {
		return (Content) this.content[y][x];
	}

	public boolean contains(DiscretePoint point) {
		if(point.x<0 || point.x>=width)
			return false;
		if(point.y<0 || point.y>=height)
			return false;
		return true;
	}

	public boolean contains(int x, int y) {
		if(x<0 || x>=width)
			return false;
		if(y<0 || y>=height)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder returned = new StringBuilder();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				returned.append("\t").append(this.content[y][x]).append(',');
			}
			returned.append('\n');
		}
		return returned.toString();
	}
}
