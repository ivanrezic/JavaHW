package hr.fer.zemris.java.raytracer.model;

import static java.lang.Math.sqrt;

/**
 * A sphere is a shape in space that is like the surface of a ball. Most of the
 * time, the terms ball and sphere are used as the same. But in mathematics, the
 * precise (exact) definition only allows points in the 3 dimensional space
 * which are uniformly and symmetrically located at a fixed length called radius
 * of the sphere. Examples of these are basketballs, superballs, and playground
 * balls. A sphere is the 3 dimensional analogue of a circle. This class is
 * provides information which ray intersection is closes to the source of ray.
 *
 * @author Ivan Rezic
 */
public class Sphere extends GraphicalObject {

	/** Center point of sphere. */
	private Point3D center;

	/** Sphere radius. */
	private double radius;

	/** Sphere parameter for red diffuse component. */
	private double kdr;
	/** Sphere parameter for green diffuse component. */
	private double kdg;
	/** Sphere parameter for blue diffuse component. */
	private double kdb;
	/** Sphere parameter for red reflective component. */
	private double krr;
	/** Sphere parameter for green reflective component. */
	private double krg;
	/** Sphere parameter for blue reflective component. */
	private double krb;
	/** Sphere surface roughness index. */
	private double krn;

	/**
	 * Constructor which instantiates new sphere.
	 *
	 * @param center
	 *            {@link #center}
	 * @param radius
	 *            {@link #radius}
	 * @param kdr
	 *            {@link #kdr}
	 * @param kdg
	 *            {@link #kdg}
	 * @param kdb
	 *            {@link #kdb}
	 * @param krr
	 *            {@link #krr}
	 * @param krg
	 *            {@link #krg}
	 * @param krb
	 *            {@link #krb}
	 * @param krn
	 *            {@link #krn}
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		if (center == null) {
			throw new NumberFormatException("Center point can not be null.");
		}

		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		// algorithm used here can be found on following link
		// https://www.scratchapixel.com/lessons/3d-basic-rendering/minimal-ray-tracer-rendering-simple-shapes/ray-sphere-intersection
		Point3D l = center.sub(ray.start);
		double tca = l.scalarProduct(ray.direction);
		if (tca < 0)
			return null;

		double d2 = l.scalarProduct(l) - tca * tca;
		if (d2 > radius * radius)
			return null;

		double thc = sqrt(radius * radius - d2);
		double t0 = tca - thc;
		double t1 = tca + thc;

		if (t0 > t1) {
			double temp = t1;
			t0 = temp;
			t1 = t0;
		}

		if (t0 < 0) {
			t0 = t1; // if t0 is negative, let's use t1 instead
			if (t0 < 0) {
				return null; // both t0 and t1 are negative
			}
		}

		Point3D point = ray.start.add(ray.direction.scalarMultiply(t0));
		double distance = ray.start.sub(point).norm();

		return new RayIntersectionSphere(point, distance, true, this);// returns
																		// just
																		// outer
																		// intersection
	}

	/**
	 * Method used for getting property <code>Center</code>.
	 *
	 * @return center
	 */
	public Point3D getCenter() {
		return center;
	}

	/**
	 * Method used for getting property <code>Radius</code>.
	 *
	 * @return radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Method used for getting property <code>Kdr</code>.
	 *
	 * @return kdr
	 */
	public double getKdr() {
		return kdr;
	}

	/**
	 * Method used for getting property <code>Kdg</code>.
	 *
	 * @return kdg
	 */
	public double getKdg() {
		return kdg;
	}

	/**
	 * Method used for getting property <code>Kdb</code>.
	 *
	 * @return kdb
	 */
	public double getKdb() {
		return kdb;
	}

	/**
	 * Method used for getting property <code>Krr</code>.
	 *
	 * @return krr
	 */
	public double getKrr() {
		return krr;
	}

	/**
	 * Method used for getting property <code>Krg</code>.
	 *
	 * @return krg
	 */
	public double getKrg() {
		return krg;
	}

	/**
	 * Method used for getting property <code>Krb</code>.
	 *
	 * @return krb
	 */
	public double getKrb() {
		return krb;
	}

	/**
	 * Method used for getting property <code>Krn</code>.
	 *
	 * @return krn
	 */
	public double getKrn() {
		return krn;
	}

}
