public class Point {

    private int i;
    private int j;

    public Point(int i, int j) {

	this.i = i;
	this.j = j;

    }

    public int get_i() {

	return this.i;

    }

    public int get_j() {

	return this.j;

    }

    public void set_i(int valeur) {

	this.i = valeur;

    }

    public void set_j(int valeur) {

	this.j = valeur;

    }

    public boolean equals(Object point) {

	Point p = (Point)point;
	
	if( this.get_i() == p.get_i() && this.get_j() == p.get_j() )

	    return true;

	return false;

    }
	

}
