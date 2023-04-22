import java.util.Iterator;
import java.util.LinkedList;

public class DataStructure implements DT {

	private DoubleLinkedContainer ByX;
	private DoubleLinkedContainer ByY;


	//////////////// DON'T DELETE THIS CONSTRUCTOR ////////////////
	public DataStructure()
	{
		ByX = new DoubleLinkedContainer(true);
		ByY = new DoubleLinkedContainer(false);
	}

	@Override
	public void addPoint(Point point) {
		Container c1 = ByX.addContainer(new Container(point));
		Container c2 = ByY.addContainer(new Container(point));
		c1.setBrother(c2);
		c2.setBrother(c1);
	}


	@Override
	public Point[] getPointsInRangeRegAxis(int min, int max, Boolean axis) {
		if(axis)
			return ByX.getPointInRange(min,max);
		return ByY.getPointInRange(min,max);
	}

	@Override
	public Point[] getPointsInRangeOppAxis(int min, int max, Boolean axis) {
		if(axis)
			return ByY.getPointInRange(min,max);
		return ByX.getPointInRange(min,max);
	}

	@Override
	public double getDensity() {
		int xMin = ByX.getHead().getData().getX();
		int xMax = ByX.getTail().getData().getX();
		int yMin = ByY.getHead().getData().getY();
		int yMax = ByY.getTail().getData().getY();
		int n = ByX.getSize();
		return n / ((xMax - xMin) * (yMax - yMin));
	}

	@Override
	public void narrowRange(int min, int max, Boolean axis) {
		if(axis)
			ByX.narrowRange(min, max, ByY);
		else
			ByY.narrowRange(min, max, ByX);
	}

	@Override
	public Boolean getLargestAxis() {
		int xMin = ByX.getHead().getData().getX();
		int xMax = ByX.getTail().getData().getX();
		int yMin = ByY.getHead().getData().getY();
		int yMax = ByY.getTail().getData().getY();
		return (xMax - xMin) >= (yMax - yMin);
	}

	@Override
	public Container getMedian(Boolean axis) {
		if(axis)
			return ByX.getMedian();

		return ByY.getMedian();
	}

	@Override
	public Point[] nearestPairInStrip(Container container, double width,
			Boolean axis) {
		if(axis)
			return ByX.NearestPairInStrip(container, width);
		return ByY.NearestPairInStrip();
	}

	@Override
	public Point[] nearestPair() {
		// TODO Auto-generated method stub
		return null;
	}

	
	//TODO: add members, methods, etc.
	
}

