package org.ndx.codingame.lib2d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.ndx.codingame.lib2d.continuous.ContinuousPoint;
import org.ndx.codingame.lib2d.discrete.DiscretePoint;

/**
 * Helper class containing builder patterns
 * @author ndelsaux
 *
 */
public class Geometry {
	public static class GeometryBuilder {
		public static class PolygonBuilder {
			private List<ContinuousPoint> points = new ArrayList<ContinuousPoint>();
			public PolygonBuilder through(ContinuousPoint p) {
				points.add(p);
				return this;
			}
			public Polygon build() {
				switch(points.size()) {
				case 3:
					return Triangle.from(points.get(0), points.get(1), points.get(2));
//				case 4:
//					return Quadrilater.from(points.get(0), points.get(1), points.get(2), points.get(3));
				default:
					throw new UnsupportedOperationException("there is no polygon with %d points");
				}
			}
		}

		private ContinuousPoint first;

		public GeometryBuilder(ContinuousPoint at) {
			this.first = at;
		}

		public Line lineTo(double x, double y) {
			return lineTo(at(x, y));
		}
		public Line lineTo(ContinuousPoint second) {
			return new Line(first, second);
		}
		
		public Line lineTo(DiscretePoint second) {
			return new Line(first, continuous(second));
		}
		
		public Circle cirleOf(double radius) {
			return new Circle(first, radius);
		}

		public Segment segmentTo(double x, double y) {
			return segmentTo(at(x, y));
		}

		public Segment segmentTo(DiscretePoint p) {
			return segmentTo(continuous(p));
		}
		public Segment segmentTo(ContinuousPoint p) {
			return new Segment(first, p);
		}
		
		public PolygonBuilder through(ContinuousPoint p) {
			return new PolygonBuilder().through(first).through(p);
		}
	}
	public static final ContinuousPoint at(double x, double y) {
		return new ContinuousPoint(x, y);
	}

	public static final DiscretePoint at(int x, int y) {
		return new DiscretePoint(x, y);
	}

	public static final double ZERO = 0.00001;

	public static GeometryBuilder from(double x, double y) {
		return from(at(x, y));
	}
	public static GeometryBuilder from(ContinuousPoint at) {
		return new GeometryBuilder(at);
	}
	
	public static GeometryBuilder from(DiscretePoint at) {
		return new GeometryBuilder(continuous(at));
	}
	
	public static ContinuousPoint continuous(DiscretePoint point) {
		return new ContinuousPoint(point.x, point.y);
	}

	public static ContinuousPoint barycenterOf(Collection<? extends ContinuousPoint> points) {
		double x = 0,
				y = 0;
		int size = points.size();
		for (ContinuousPoint point : points) {
			x+=point.x;
			y+=point.y;
		}
		return new ContinuousPoint(x/size, y/size);
	}
}