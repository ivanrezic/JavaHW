package hr.fer.zemris.java.raytracer.model;

/**
 * <code>RayIntersectionSphere</code> is {@linkplain RayIntersection}
 * implementation and it represents ray intersection with sphere.
 *
 * @author Ivan Rezic
 */
public class RayIntersectionSphere extends RayIntersection {

	/** Sphere intersecting. */
	private Sphere sphere;

	/** Sphere norm in intersecting point. */
	private Point3D norm;

	/**
	 * Constructor which instantiates new ray intersection sphere.
	 *
	 * @param point
	 *            the point
	 * @param distance
	 *            the distance
	 * @param outer
	 *            the outer
	 * @param sphere
	 *            the sphere
	 */
	public RayIntersectionSphere(Point3D point, double distance, boolean outer, Sphere sphere) {
		super(point, distance, outer);

		if (sphere == null) {
			throw new NullPointerException("Sphere can not be null.");
		} else if (distance < 0) {
			throw new IllegalArgumentException("Distance can not be less than zero.");
		} else if (point == null) {
			throw new NullPointerException("Point can not be null.");
		}

		this.sphere = sphere;
		this.norm = getPoint().sub(sphere.getCenter()).normalize();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.raytracer.model.RayIntersection#getNormal()
	 */
	@Override
	public Point3D getNormal() {
		return norm;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.raytracer.model.RayIntersection#getKdr()
	 */
	@Override
	public double getKdr() {
		return sphere.getKdr();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.raytracer.model.RayIntersection#getKdg()
	 */
	@Override
	public double getKdg() {
		return sphere.getKdg();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.raytracer.model.RayIntersection#getKdb()
	 */
	@Override
	public double getKdb() {
		return sphere.getKdb();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.raytracer.model.RayIntersection#getKrr()
	 */
	@Override
	public double getKrr() {
		return sphere.getKrr();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.raytracer.model.RayIntersection#getKrg()
	 */
	@Override
	public double getKrg() {
		return sphere.getKrg();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.raytracer.model.RayIntersection#getKrb()
	 */
	@Override
	public double getKrb() {
		return sphere.getKrb();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see hr.fer.zemris.java.raytracer.model.RayIntersection#getKrn()
	 */
	@Override
	public double getKrn() {
		return sphere.getKrn();
	}

}
