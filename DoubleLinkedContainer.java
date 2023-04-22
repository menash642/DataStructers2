public class DoubleLinkedContainer {
    private Container head;
    private Container tail;

    private int size;
    private boolean axis;

    public DoubleLinkedContainer(boolean axis) {
        this.head = null;
        this.tail = null;
        this.axis = axis;
        this.size = 0;
    }

    public DoubleLinkedContainer copyList(){
        DoubleLinkedContainer newList = new DoubleLinkedContainer(axis);
        Container current = this.head;

        while(current != null){
            newList.addLast(new Container(current.getData()));
            current = current.getNext();
        }

        return newList;
    }
    public Point[] getPointInRange(int min, int max) {
        DoubleLinkedContainer copy = this.copyList();
        copy.narrowRange(min,max, null);
        return copy.toPointArray();
    }

    public void addLast(Container c){
        if(head == null){
            this.head = c;
            this.tail = c;
            size++;
        }
        else {
            this.tail.setNext(c);
            c.setPrev(this.tail);
            this.tail = c;
            this.size++;
        }
    }
    public void narrowRange(int min, int max, DoubleLinkedContainer BrotherList) {
        this.narrowRangeFromFirst(min, BrotherList);
        this.narrowRangeFromLast(max, BrotherList);
    }

    public void narrowRangeFromLast(int max, DoubleLinkedContainer BrotherList) {
        Container fromLast = this.tail;

        if (axis) {
            while (fromLast != null & fromLast.getData().getX() > max) {
                if(BrotherList != null)
                    BrotherList.remove(fromLast.getBrother());
                fromLast = fromLast.getPrev();
                this.removeLast();
                size--;
            }
        } else {
            while ((fromLast != null & fromLast.getData().getY() > max)) {
                if(BrotherList != null)
                    BrotherList.remove(fromLast.getBrother());
                fromLast = fromLast.getPrev();
                this.removeLast();
                size--;

            }
        }
    }
    public void narrowRangeFromFirst(int min, DoubleLinkedContainer BrotherList) {
        Container fromFirst = this.head;

        if (axis) {
            while (fromFirst != null & fromFirst.getData().getX() < min) {
                if(BrotherList != null)
                    BrotherList.remove(fromFirst.getBrother());
                fromFirst = fromFirst.getNext();
                this.removeFirst();
            }
        } else {
            while ((fromFirst != null & fromFirst.getData().getY() < min)) {
                if(BrotherList != null)
                    BrotherList.remove(fromFirst.getBrother());
                fromFirst = fromFirst.getNext();
                this.removeFirst();
                size--;

            }
        }
    }

    public Container addContainer(Container c) {
        Container current = head;
        if(head == null){
            this.head = c;
            this.tail = this.head;
            size++;
            return c;
        }
        //Case first
        if (c.compareTo(this.axis, head) == -1) {
            c.setNext(this.head);
            this.head.setPrev(c);
            this.head = c;
            this.size++;
            return  c;
        }

        //Regular case
        while (current != null) {
            if (c.compareTo(this.axis, current) == -1) {
                c.setNext(current);
                c.setPrev(current.getPrev());
                current.getPrev().setNext(c);
                current.setPrev(c);
                this.size++;
                return  c;
            }
            current = current.getNext();

        }

        c.setPrev(this.tail);
        this.tail.setNext(c);
        this.tail = c;
        this.size++;
        return  c;
    }

    public void removeLast(){
        if(size == 1){
            this.head =null;
            this.tail = null;
            size--;
        }
        else {
            this.tail = this.tail.getPrev();
            this.tail.setNext(null);
            size--;
        }
    }

    public void removeFirst() {
        if(size == 1){
            this.head =null;
            this.tail = null;
            size--;
        }
        else {
            this.head = this.head.getNext();
            this.head.setPrev(null);
            this.size--;
        }
    }

    public Point[] toPointArray() {
        Point[] pArray = new Point[size];
        Container c = head;
        int index = 0;
        while (c != null) {
            pArray[index] = c.getData();
            c = c.getNext();
            index++;
        }
        return pArray;
    }

    public Container getHead() {
        return head;
    }

    public Container getTail() {
        return tail;
    }

    public int getSize() {
        return size;
    }

    public void remove(Container c){
        if(c.getNext() == null && c.getPrev() == null){
            this.head = null;
            this.tail = null;
        }
        else if(c.getNext() == null){
            this.tail = c.getPrev();
            c.getPrev().setNext(null);

        }
        else if(c.getPrev() == null){
            this.head = c.getNext();
            this.head.setPrev(null);

        }
        else {
            c.getPrev().setNext(c.getNext());
            c.getNext().setPrev(c.getPrev());
        }
        size--;
    }

    public Container getMedian(){
        int index = size / 2;
        Container current = this.head;
        while (index > 0){
            current = current.getNext();
            index--;
        }

        return current;
    }

    public Point[] nearestPairInStrip(Container c, double width) {
        DoubleLinkedContainer newList = this.copyList();
        Point[] points;
        if (axis) {
            int leftBorder = (int) Math.round(c.getData().getX() - width / 2);
            int rightBorder = (int) Math.round(c.getData().getX() + width / 2);
            points = newList.getPointInRange(leftBorder, rightBorder);
        } else {
            int bottomBorder = (int) Math.round(c.getData().getY() - width / 2);
            int upperBorder = (int) Math.round(c.getData().getY() + width / 2);
            points = newList.getPointInRange(bottomBorder, upperBorder);
        }



        return null;
    }

    public Point[] nearestPairInRange(int min, int max, Point[] points){
        if(max - min <= 3){
            if(max - min == 2){
                Point[] p1 = { points[min], points[min + 1]};
                Point[] p2 = { points[min + 1], points[max]};
                return minEuclidian(p1, p2);
            }
            Point[] p1 = {points[max - 1], points[max]};
            return minEuclidian(nearestPairInRange(min , min + 2 , points), p1);
        }
        return minEuclidian(nearestPairInRange(min,(max + 1) / 2,points), nearestPairInRange((max + 1) / 2 , max,points));

    }

    public Point[] minEuclidian(Point[] p1, Point[] p2) {
        double p1Dist = Math.sqrt((p1[0].getX() - p1[1].getX()) ^ 2 - (p1[0].getY() - p1[1].getY()) ^ 2);
        double p2Dist = Math.sqrt((p2[0].getX() - p2[1].getX()) ^ 2 - (p2[0].getY() - p2[1].getY()) ^ 2);
        if(p1Dist < p2Dist)
            return p1;
        return p2;
    }


}
