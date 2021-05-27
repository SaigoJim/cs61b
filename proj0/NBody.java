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
		int numberOfPlantes = planets.length;

		String background = "images/starfield.jpg";
		StdDraw.enableDoubleBuffering();
		/** Sets up the universe so it goes from 
		  * -100, -100 up to 100, 100 */
		StdDraw.setScale(-radius, radius);

		/* Clears the drawing window. */
		StdDraw.clear();

		for (double time = 0; time < T; time += dt) {
			double[] xForces = new double[numberOfPlantes];
			double[] yForces = new double[numberOfPlantes];

			for (int i = 0; i < numberOfPlantes; i += 1) {
				double netForceX = planets[i].calcNetForceExertedByX(planets);
				double netForceY = planets[i].calcNetForceExertedByY(planets);
				xForces[i] = netForceX;
				yForces[i] = netForceY;
			}

			for (int i = 0; i < numberOfPlantes; i += 1) {
				planets[i].update(dt, xForces[i], yForces[i]);
			}

			/* Set background picture. */
			StdDraw.picture(0, 0, background);

			/* Draw out each planet in planets. */
			for (Planet p : planets) {
				p.draw();
			}

			/* Shows the drawing to the screen, and waits 2000 milliseconds. */
			StdDraw.show();
			StdDraw.pause(10);	
		}

		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                 	planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
                  	planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
    	}

	}
} 
