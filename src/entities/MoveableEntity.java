package entities;

public interface MoveableEntity extends Entity {
	double getDX();
	double getDY();
	void setDX(double dx);
	void setDY(double dy);
}
