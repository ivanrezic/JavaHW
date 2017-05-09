package hr.fer.zemris.java.raytracer;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * <code>RayCaster</code> uses rey casting which enables us to draw/color 3d
 * objects.
 *
 * @author Ivan Rezic
 */
public class RayCaster {

	/**
	 * The main method of this class, used for demonstration purposes.
	 *
	 * @param args
	 *            the arguments from command line, not used here
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * Method used for getting property <code>IRayTracerProducer</code>.
	 *
	 * @return i ray tracer producer
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];
				Point3D zAxis = view.sub(eye).normalize();
				Point3D yAxis = calculateYAxis(zAxis, viewUp);
				Point3D xAxis = zAxis.vectorProduct(yAxis);
				Point3D screenCorner = calculateCorner(view, horizontal, vertical, xAxis, yAxis);

				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {
						double xComp = horizontal * x / (width - 1.0);
						double yComp = vertical * y / (height - 1.0);
						Point3D screenPoint = calculateScreenPoint(yAxis, xAxis, screenCorner, xComp, yComp);

						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}

	/**
	 * Helper method which goes through scene and determines rgb color for each
	 * intersection.
	 *
	 * @param scene
	 *            the scene
	 * @param ray
	 *            the ray
	 * @param rgb
	 *            the rgb - color
	 */
	private static void tracer(Scene scene, Ray ray, short[] rgb) {
		RayIntersection intersection = closestIntersaction(scene, ray);
		if (intersection == null) {
			rgb[0] = 0;
			rgb[1] = 0;
			rgb[2] = 0;

		} else {
			short[] color = determineColorFor(intersection, ray, scene);
			for (int i = 0; i < 3; i++) {
				rgb[i] = color[i];
			}
		}
	}

	/**
	 * {@link #tracer(Scene, Ray, short[])} helper method which out of two
	 * intersections decides if one should be colored and which one.
	 *
	 * @param intersection
	 *            the intersection
	 * @param ray
	 *            the ray
	 * @param scene
	 *            the scene
	 * @return the short[]
	 */
	private static short[] determineColorFor(RayIntersection intersection, Ray ray, Scene scene) {
		short[] color = { 15, 15, 15 };

		for (LightSource ls : scene.getLights()) {
			Ray r = Ray.fromPoints(ls.getPoint(), intersection.getPoint());
			RayIntersection intersection2 = closestIntersaction(scene, r);

			if (intersection2 != null && distance(intersection, ls) < distance(intersection2, ls) + 1e-9) {
				reflection(ls, ray, color, intersection2);
				diffusion(ls, color, intersection2);
			}
		}

		return color;
	}

	/**
	 * Helper method used both in {@link #tracer(Scene, Ray, short[])} and
	 * {@link #determineColorFor(RayIntersection, Ray, Scene)} to find which
	 * intersection from light source is in front of point of view.
	 *
	 * @param scene
	 *            the scene
	 * @param ray
	 *            the ray
	 * @return the ray intersection
	 */
	private static RayIntersection closestIntersaction(Scene scene, Ray ray) {
		RayIntersection temporary = null;

		for (GraphicalObject s : scene.getObjects()) {
			RayIntersection intersection = s.findClosestRayIntersection(ray);
			if (intersection != null) {
				if (temporary == null || temporary.getDistance() > intersection.getDistance()) {
					temporary = intersection;
				}
			}
		}

		return temporary;
	}

	/**
	 * Helper method which checks distance between two points.
	 *
	 * @param intersection
	 *            the intersection
	 * @param ls
	 *            the light source
	 * @return the double
	 */
	private static double distance(RayIntersection intersection, LightSource ls) {
		return ls.getPoint().sub(intersection.getPoint()).norm();
	}

	/**
	 * Helper method which adds diffusive component to displayer geometric
	 * object.
	 *
	 * @param ls
	 *            the light source
	 * @param color
	 *            the color
	 * @param i
	 *            the intersection
	 */
	private static void diffusion(LightSource ls, short[] color, RayIntersection i) {
		Point3D normal = i.getNormal();
		Point3D light = ls.getPoint().sub(i.getPoint()).normalize();

		double angle1 = normal.scalarProduct(light) > 0 ? normal.scalarProduct(light) : 0;

		color[0] += (short) (ls.getR() * i.getKdr() * angle1);
		color[1] += (short) (ls.getG() * i.getKdg() * angle1);
		color[2] += (short) (ls.getB() * i.getKdb() * angle1);
	}

	/**
	 * Helper method which adds reflective component to displayer geometric
	 * object.
	 *
	 * @param ls
	 *            the light source
	 * @param ray
	 *            the ray
	 * @param color
	 *            the color
	 * @param i
	 *            the intersection
	 */
	private static void reflection(LightSource ls, Ray ray, short[] color, RayIntersection i) {
		Point3D normal = i.getNormal();
		Point3D light = ls.getPoint().sub(i.getPoint()).normalize();

		Point3D r = normal.normalize().scalarMultiply(2 * light.scalarProduct(normal) / normal.norm()).sub(light)
				.normalize();
		Point3D v = ray.start.sub(i.getPoint()).normalize();

		double vec = r.scalarProduct(v);
		double angle2 = Math.pow(vec, i.getKrn()) > 0 ? Math.pow(vec, i.getKrn()) : 0;

		if (vec >= 0) {
			color[0] += (short) (ls.getR() * i.getKrr() * angle2);
			color[1] += (short) (ls.getG() * i.getKrg() * angle2);
			color[2] += (short) (ls.getB() * i.getKrb() * angle2);
		}
	}

	/**
	 * Calculate corner.
	 *
	 * @param g
	 *            the g
	 * @param horizontal
	 *            the horizontal
	 * @param vertical
	 *            the vertical
	 * @param i
	 *            the i
	 * @param j
	 *            the j
	 * @return the point 3 D
	 */
	private static Point3D calculateCorner(Point3D g, double horizontal, double vertical, Point3D i, Point3D j) {
		double fraction1 = horizontal / 2;
		double fraction2 = vertical / 2;

		return g.sub(i.scalarMultiply(fraction1)).add(j.scalarMultiply(fraction2));
	}

	/**
	 * Calculate Y axis.
	 *
	 * @param og
	 *            the og
	 * @param viewUp
	 *            the view up
	 * @return the point 3 D
	 */
	private static Point3D calculateYAxis(Point3D og, Point3D viewUp) {
		Point3D vuv = viewUp.normalize();
		Point3D j = vuv.sub(og.scalarMultiply(og.scalarProduct(vuv)));

		return j.normalize();
	}

	/**
	 * Calculate screen point.
	 *
	 * @param yAxis
	 *            the y axis
	 * @param xAxis
	 *            the x axis
	 * @param screenCorner
	 *            the screen corner
	 * @param xComp
	 *            the x comp
	 * @param yComp
	 *            the y comp
	 * @return the point 3 D
	 */
	private static Point3D calculateScreenPoint(Point3D yAxis, Point3D xAxis, Point3D screenCorner, double xComp,
			double yComp) {
		return screenCorner.add(xAxis.scalarMultiply(xComp)).sub(yAxis.scalarMultiply(yComp));
	}
}
