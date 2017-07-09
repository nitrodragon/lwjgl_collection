package entities;

public interface Entity {
	void draw();
	void update(int delta);
	void setLocation(double x, double y);
	void setX(double x);
	void setY(double y);
	void setWidth(double width);
	void setHeight(double height);
	double getX();
	double getY();
	double getHeight();
	double getWidth();
	boolean intersects(Entity other);
}
