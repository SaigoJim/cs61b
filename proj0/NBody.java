public class NBody {
	public static double readRadius(String fileName) {
		In in = new In(fileName);

		int firstItemInFile = in.readInt();
		double secondItemInFile = in.readDouble();

		return secondItemInFile;
	}

	public static Planet[] readPlanets(String fileName) {
		In in = new In(fileName);

		int numberOfPlantes = in.readInt();
		double radius = in.readDouble();

		Planet[] planets = new Planet[numberOfPlantes];

		for (int i = 0; i < numberOfPlantes; i += 1) {
			double xxPos = in.readDouble();
			double yyPos = in.readDouble();
			double xxVel = in.readDouble();
			double yyVel = in.readDouble();
			double mass = in.readDouble();
			String imgFileName = in.readString();
			planets[i] = new Planet(xxPos, yyPos, xxVel, yyVel, mass, imgFileName);
		}

		return planets;
	}

	public static void main(String[] args) {
		double T = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String fileName = args[2];

		double radius = NBody.readRadius(fileName);
		Planet[] planets = NBody.readPlanets(fileName);

		String background = "images/starfield.jpg";

		/** Sets up the universe so it goes from 
		  * -100, -100 up to 100, 100 */
		StdDraw.setScale(-radius, radius);

		/* Clears the drawing window. */
		StdDraw.clear();

		/* Set background picture. */
		StdDraw.picture(0, 0, background);

		/* Draw out each planet in planets. */
		for (Planet p : planets) {
			p.draw();
		}

		/* Shows the drawing to the screen, and waits 2000 milliseconds. */
		StdDraw.show();
		StdDraw.pause(2000);		
	}
} 