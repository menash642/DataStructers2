import org.w3c.dom.Node;

//Don't change the class name
public class Container{
	private Point data;//Don't delete or change this field;
	private boolean axis;
	private Container brother;

	private Container next;
	private Container prev;

	public Container(Point point){
		this.data = point;
		this.next = null;
		this.prev = null;
		this.brother = null;
	}

	public void setAxis(boolean axis) {
		this.axis = axis;
	}
	public Container getBrother() {
		return brother;
	}
	public void setBrother(Container brother) {
		this.brother = brother;
	}
	public Container getNext() {
		return next;
	}
	public void setNext(Container next) {
		this.next = next;
	}
	public Container getPrev() {
		return prev;
	}
	public void setPrev(Container prev) {
		this.prev = prev;
	}
	//Don't delete or change this function
	public Point getData()
	{
		return data;
	}

	public int compareTo(boolean axis, Container other){
		if(axis){
			if(this.data.getX() < other.getData().getX())
				return -1;
			return 1;
		}
		if(this.data.getY() < other.getData().getY())
			return -1;
		return 1;
	}

}
